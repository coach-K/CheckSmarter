package com.andela.checksmarter.utilities.alarm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by CodeKenn on 03/05/16.
 */
@RealmClass
public class ReminderSet extends RealmObject {
    @PrimaryKey
    private int id;

    private long time;

    public ReminderSet() {
    }

    public ReminderSet(int id, long time) {
        this.time = time;
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
