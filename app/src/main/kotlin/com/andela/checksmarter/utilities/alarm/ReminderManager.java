package com.andela.checksmarter.utilities.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.andela.checksmarter.utilities.DateFormatter;

import java.util.Calendar;

/**
 * Created by CodeKenn on 29/04/16.
 */
public class ReminderManager {
    public static final String KEY = "checksmarter_task_reminder";
    public static final String VALUE = "checksmarter_task_reminder_value";
    public static final String SET_REMINDER = "set reminder";
    public static final String CANCEL_REMINDER = "cancel reminder";
    private static final int PERIOD = 300000;   // 5 minutes

    private AlarmManager mgr;
    private Context context;

    public ReminderManager(Context context) {
        this.context = context;
        this.mgr = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setReminder(int id, long time) {
        Intent intent = new Intent(this.context, OnAlarmReceiver.class);

        mgr.setRepeating(AlarmManager.RTC_WAKEUP, time, PERIOD, getPendingIntent(intent, id));
    }

    public void setReminder(int id, long time, int period) {
        Intent intent = new Intent(this.context, OnAlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this.context, id, intent, 0);

        mgr.setRepeating(AlarmManager.RTC_WAKEUP, time, period, pIntent);
    }

    public void setReminder(int[] ids, long[] times) {
        Intent intent = new Intent(this.context, OnAlarmReceiver.class);

        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            long time = times[i];

            if (time > DateFormatter.INSTANCE.getCurrentTime()) {
                mgr.set(AlarmManager.RTC_WAKEUP, time, getPendingIntent(intent, id));
            }
        }
    }

    public void cancelReminder(int id) {
        Intent intent = new Intent(this.context, OnAlarmReceiver.class);
        mgr.cancel(getPendingIntent(intent, id));
    }

    private PendingIntent getPendingIntent(Intent intent, int id) {
        return PendingIntent.getBroadcast(this.context,
                id,
                intent,
                0);
    }
}
