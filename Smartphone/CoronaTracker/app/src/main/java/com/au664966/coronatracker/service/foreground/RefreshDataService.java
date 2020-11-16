package com.au664966.coronatracker.service.foreground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import com.au664966.coronatracker.DetailsActivity;
import com.au664966.coronatracker.R;
import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.model.Repository;
import com.au664966.coronatracker.utility.Constants;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RefreshDataService extends Service {
    private static final String TAG = "RefreshDataService";
    public static final String REFRESH_DATA_CHANNEL = "refreshDataChannel";
    public static final int NOTIFICATION_ID = 44;
    public static final int INFO_NOTIF_ID = 43;
    private ExecutorService executorService;
    Future<?> currentTask;

    private boolean isServiceStarted = false;

    public RefreshDataService() {
    }

    private Repository repo;
    private Random rnd;

    @Override
    public void onCreate() {
        super.onCreate();
        repo = Repository.getInstance(this.getApplication());
        rnd = new Random();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Initializing service");
        // We check if the intent is null and if we have already started the service
        // In case it's already started we don't want to do anything
        if (intent != null && !isServiceStarted) {
            startForeground(NOTIFICATION_ID, createForegroundNotif());
            updateNotificationAndData();
            isServiceStarted = true;
        }

        return START_STICKY;
    }


    public void updateNotificationAndData() {

        // Prepare the executor that will be on charge of doing the async work
        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor();
        }

        // Get a hold of the mainHandler so that we can send notifications and act on the db
        final Handler mainHandler = new Handler(getMainLooper());
        if (currentTask != null && currentTask.isCancelled()) {
            return;
        }
        currentTask = executorService.submit(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: Updating countries data");
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        repo.updateCountries();
                        sendNotification();
                    }
                });

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    // The thread has been interrupted so we have to finish the recursive work
                    // Returning here basically does that since the function will not be called again
                    return;
                }
                updateNotificationAndData();
            }
        });

    }

    /**
     * Creates the notification for the foreground service, which will have an icon an its background
     * will be red
     * @return The created notification
     */
    private Notification createForegroundNotif() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(REFRESH_DATA_CHANNEL,
                    getResources().getString(R.string.notif_data_channel),
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        return new NotificationCompat.Builder(this, REFRESH_DATA_CHANNEL)
                .setContentTitle(getResources().getString(R.string.notif_updating))
                .setContentText(getResources().getString(R.string.notif_updating_descr))
                .setSmallIcon(R.drawable.ic_stat_outline_call_white_36pt_3x)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_outline_call_white_36pt_3x))
                .setColor(ContextCompat.getColor(this, R.color.primaryDarkColor))
                .setColorized(true)
                .build();

    }

    /**
     * Sends an informative notification about the latest information of a country
     * If pressed, this notification will redirect the user to the details page of the country
     */
    private void sendNotification() {
        List<Country> countries = repo.getCountries().getValue();
        if (countries == null || countries.size() <= 0)
            return;
        Country selectedCountry = countries.get(rnd.nextInt(countries.size()));

        // Inspired by https://developer.android.com/training/notify-user/navigation#build_a_pendingintent_with_a_back_stack
        Intent detailIntent = new Intent(this, DetailsActivity.class);
        detailIntent.putExtra(Constants.EXTRA_COUNTRY_CODE, selectedCountry.getCode());
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(detailIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent detailPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(getResources().getString(R.string.label_cases) + " " + selectedCountry.getCases());
        textBuilder.append("\n");
        textBuilder.append(getResources().getString(R.string.label_deaths) + " " + selectedCountry.getDeaths());
        textBuilder.append("\n");
        textBuilder.append(getResources().getString(R.string.label_new_cases) + " " + +selectedCountry.getNewCases());
        textBuilder.append("\n");
        textBuilder.append(getResources().getString(R.string.label_new_deaths) + " " + +selectedCountry.getNewDeaths());

        String summaryText = selectedCountry.getCases() + "/" + selectedCountry.getDeaths();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, REFRESH_DATA_CHANNEL)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(textBuilder.toString())
                        .setSummaryText(summaryText))
                .setSmallIcon(R.drawable.ic_baseline_outlined_flag_24)
                .setContentTitle(selectedCountry.getName() + getResources().getString(R.string.notif_latest_info))
                .setContentText(summaryText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(detailPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(INFO_NOTIF_ID, builder.build());

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: Destroying service");
        isServiceStarted = false;
        // Cancel the future so that InterruptException is thrown and it can stop the recursive work
        if (currentTask != null) {
            currentTask.cancel(true);
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}