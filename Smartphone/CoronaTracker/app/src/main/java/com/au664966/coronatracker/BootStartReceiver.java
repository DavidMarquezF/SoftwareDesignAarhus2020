package com.au664966.coronatracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.au664966.coronatracker.service.foreground.RefreshDataService;

public class BootStartReceiver extends BroadcastReceiver {
    private static final String TAG = "BootStartReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
            Log.d(TAG, "onReceive: Boot received");
            Intent it = new Intent(context.getApplicationContext(), RefreshDataService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(it);
            }
            else{
                context.startService(it);
            }
        }
    }
}
