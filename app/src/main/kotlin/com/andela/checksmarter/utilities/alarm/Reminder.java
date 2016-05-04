package com.andela.checksmarter.utilities.alarm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CodeKenn on 29/04/16.
 */
public class Reminder implements Parcelable {
    private int removeId;
    private long[] times;
    private int[] ids;

    public Reminder() {
    }

    public Reminder(int removeId) {
        this.removeId = removeId;
    }

    public Reminder(int[] ids, long[] times) {
        this.times = times;
        this.ids = ids;
    }

    protected Reminder(Parcel in) {
        removeId = in.readInt();
        times = in.createLongArray();
        ids = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(removeId);
        dest.writeLongArray(times);
        dest.writeIntArray(ids);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public long[] getTimes() {
        return times;
    }

    public void setTimes(long[] times) {
        this.times = times;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public int getRemoveId() {
        return removeId;
    }

    public void setRemoveId(int removeId) {
        this.removeId = removeId;
    }
}
