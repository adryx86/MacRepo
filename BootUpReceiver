package com.example.alarmns;

/*
 * Classe utilizzata per il boot automatico
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver {
	public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, MainActivity.class);  
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
    }
}
