package com.andela.checksmarter.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by CodeKenn on 19/04/16.
 */
@RealmClass
public class CheckSmarterJava extends RealmObject {
    @PrimaryKey
    private int id = 0;

    private String title = "Quick Check Smarter";
    private boolean check = true;
    private boolean alarm = true;
    private long alarmValue = 345;
    private long timeValue = 456;
    private RealmList<CheckSmarterTaskJava> tasks = new RealmList<>();

    public CheckSmarterJava() {
    }

    public CheckSmarterJava(int id) {
        this.id = id;
    }

    public CheckSmarterJava(int id, String title, boolean check, boolean alarm, long alarmValue, long timeValue, RealmList<CheckSmarterTaskJava> tasks) {
        this.id = id;
        this.title = title;
        this.check = check;
        this.alarm = alarm;
        this.alarmValue = alarmValue;
        this.timeValue = timeValue;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public long getAlarmValue() {
        return alarmValue;
    }

    public void setAlarmValue(long alarmValue) {
        this.alarmValue = alarmValue;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }

    public RealmList<CheckSmarterTaskJava> getTasks() {
        return tasks;
    }

    public void setTasks(RealmList<CheckSmarterTaskJava> tasks) {
        this.tasks = tasks;
    }
}
