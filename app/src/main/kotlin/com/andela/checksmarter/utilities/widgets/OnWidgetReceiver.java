package com.andela.checksmarter.utilities.widgets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.util.Log;

import com.andela.checksmarter.activities.SingleTextToSpeechManager;
import com.andela.checksmarter.activities.SplashActivity;

/**
 * Created by CodeKenn on 04/05/16.
 */
public class OnWidgetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            SingleTextToSpeechManager.getInstance(context).talk();
        } catch (RuntimeException e) {
            Intent intent1 = new Intent(context, SplashActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
