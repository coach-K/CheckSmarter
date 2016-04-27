package com.andela.checksmarter.utilities.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.andela.checksmarter.R;
import com.andela.checksmarter.activities.MainActivity;

/**
 * Created by CodeKenn on 27/04/16.
 */
public class NotificationBuilder {

    public NotificationBuilder() {
    }

    public Notification getNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Check Smarter");
        builder.setContentText("Awake Boy");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(getPendingIntent(context));
        builder.setAutoCancel(true);

        //setVibration(context, builder);
        //setSound(context, builder);

        return builder.build();
    }

    /*private void setSound(Context context, NotificationCompat.Builder builder) {
        Uri ringtone = Settings.getRingtone(context);
        if (ringtone != null) {
            builder.setSound(ringtone);
        }
    }

    private void setVibration(Context context, NotificationCompat.Builder builder) {
        if (Settings.getVibrate(context)) {
            builder.setVibrate(new long[]{500, 1000, 500, 1000});
        }
    }*/

    private PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
