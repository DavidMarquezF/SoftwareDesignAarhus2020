package com.au664966.coronatracker.service.foreground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.au664966.coronatracker.model.Repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RefreshDataService extends Service {
    public static final String REFRESH_DATA_CHANNEL = "refreshDataChannel";
    public static final int NOTIFICATION_ID = 44;
    private ExecutorService executorService;
    Future<?> currentTask;
    public RefreshDataService() {
    }

    private Repository repo;
    @Override
    public void onCreate() {
        super.onCreate();
        repo = Repository.getInstance(this.getApplication());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(REFRESH_DATA_CHANNEL, "Refresh Data", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, REFRESH_DATA_CHANNEL)
                .setContentTitle("Test service")
                .build();
        startForeground(NOTIFICATION_ID, notification);
        updateNotificationAndData();
        return START_STICKY;
    }


    public void updateNotificationAndData(){
        if(executorService == null){
            executorService = Executors.newSingleThreadExecutor();
        }
        final Handler mainHandler = new Handler(getMainLooper());
        currentTask = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    // The thread has been interrupted so we have to finish the recursive work
                    // Returning here basically does that since the function will not be called again
                    return;
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                updateNotificationAndData();
            }
        });

    }

    @Override
    public void onDestroy() {
        if(currentTask != null){
            currentTask.cancel(true);
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}