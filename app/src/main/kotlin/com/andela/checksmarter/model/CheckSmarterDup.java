package com.andela.checksmarter.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeKenn on 21/04/16.
 */
public class CheckSmarterDup implements Parcelable {
    private int id = 0;

    private String title = "Quick Check Smarter";
    private boolean check = true;
    private boolean alarm = true;
    private long alarmValue = 345;
    private long timeValue = 456;
    private ArrayList<CheckSmarterTaskDup> tasks = new ArrayList<>();

    public CheckSmarterDup() {
    }

    public CheckSmarterDup(int id) {
        this.id = id;
    }

    public CheckSmarterDup(int id, String title, boolean check, boolean alarm, long alarmValue, long timeValue, ArrayList<CheckSmarterTaskDup> tasks) {
        this.id = id;
        this.title = title;
        this.check = check;
        this.alarm = alarm;
        this.alarmValue = alarmValue;
        this.timeValue = timeValue;
        this.tasks = tasks;
    }

    protected CheckSmarterDup(Parcel in) {
        id = in.readInt();
        title = in.readString();
        check = in.readByte() != 0;
        alarm = in.readByte() != 0;
        alarmValue = in.readLong();
        timeValue = in.readLong();
        tasks = in.createTypedArrayList(CheckSmarterTaskDup.CREATOR);
    }

    public static final Creator<CheckSmarterDup> CREATOR = new Creator<CheckSmarterDup>() {
        @Override
        public CheckSmarterDup createFromParcel(Parcel in) {
            return new CheckSmarterDup(in);
        }

        @Override
        public CheckSmarterDup[] newArray(int size) {
            return new CheckSmarterDup[size];
        }
    };

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

    public ArrayList<CheckSmarterTaskDup> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<CheckSmarterTaskDup> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeByte((byte) (check ? 1 : 0));
        dest.writeByte((byte) (alarm ? 1 : 0));
        dest.writeLong(alarmValue);
        dest.writeLong(timeValue);
        dest.writeTypedList(tasks);
    }
}
