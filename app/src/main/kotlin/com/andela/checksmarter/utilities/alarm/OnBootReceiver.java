/***
 * Copyright (c) 2009 CommonsWare, LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andela.checksmarter.utilities.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.andela.checksmarter.activities.CheckSmarterApplication;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;

public class OnBootReceiver extends BroadcastReceiver {
    private static final int PERIOD = 300000;   // 5 minutes

    @Override
    public void onReceive(Context context, Intent intent) {
        String KEY = intent.getStringExtra(ReminderManager.KEY);
        Reminder reminder = intent.getParcelableExtra(ReminderManager.VALUE);

        ReminderManager rm = new ReminderManager(context);

        switch (KEY) {
            case ReminderManager.SET_REMINDER:
                rm.setReminder(
                        reminder.getIds(),
                        reminder.getTimes());
                Log.i("Inside", "I'm awake! I'm awake! (yawn) - Date: " + new Date().toString());
                break;
            case ReminderManager.CANCEL_REMINDER:
                rm.cancelReminder(reminder.getRemoveId());
                break;
        }
    }
}