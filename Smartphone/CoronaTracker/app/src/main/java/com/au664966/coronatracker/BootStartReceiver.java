package com.au664966.coronatracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.au664966.coronatracker.service.foreground.RefreshDataService;

/**
 * Class used to restart the service when the mobile phone restarts. Some permissions were required
 * (have a look at the manifest)
 */
public class BootStartReceiver extends BroadcastReceiver {
    private static final String TAG = "BootStartReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
            Log.d(TAG, "onReceive: Boot received");
            Intent it = new Intent(context.getApplicationContext(), RefreshDataService.class);

            // From version Oreo, you can only start foreground service from the bootStart
            // if you don't do this it will crash and therefore won't do anything
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(it);
            }
            else{
                context.startService(it);
            }
        }
    }
}
