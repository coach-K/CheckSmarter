package com.andela.checksmarter.model;

import com.andela.checksmarter.utilities.alarm.ReminderManager;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by CodeKenn on 24/04/16.
 */
public class AutoIncrement {
    Realm realm = Realm.getDefaultInstance();
    AtomicLong primaryKeyValue;

    public AutoIncrement initialize(Class<? extends RealmObject> clazz) {


        Number currentMax = realm.where(clazz).max("id");
        long nextId = 0;

        if (currentMax != null) {
            nextId = currentMax.longValue() + 1;
        }
        primaryKeyValue = new AtomicLong(nextId);
        return this;
    }

    public long getNextId() {
        return primaryKeyValue.getAndIncrement();
    }
}
