package com.andela.checksmarter.utilities.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.andela.checksmarter.utilities.notification.NotificationBuilder;
import com.common.wakeup.WakefulIntentService;

import java.util.Date;

/**
 * Created by CodeKenn on 27/04/16.
 */
public class AppService extends WakefulIntentService {
    public AppService() {
        super("AppService");
    }

    @Override
    protected void doWakefulWork(Intent intent) {
        Log.i("AppService", "I'm awake! I'm awake! (yawn) - Date: " + new Date().toString());
        Context context = getApplicationContext();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationBuilder().getNotification(context);
        notificationManager.notify(0, notification);
    }
}
