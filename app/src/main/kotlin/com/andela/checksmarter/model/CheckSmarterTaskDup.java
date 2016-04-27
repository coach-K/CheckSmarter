package com.andela.checksmarter.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CodeKenn on 21/04/16.
 */
public class CheckSmarterTaskDup implements Parcelable {
    private int id = 0;

    private String title = "Quick Task";
    private boolean check = false;

    public CheckSmarterTaskDup() {
    }

    public CheckSmarterTaskDup(int id) {
        this.id = id;
    }

    public CheckSmarterTaskDup(String title) {
        this.title = title;
    }

    protected CheckSmarterTaskDup(Parcel in) {
        id = in.readInt();
        title = in.readString();
        check = in.readByte() != 0;
    }

    public static final Creator<CheckSmarterTaskDup> CREATOR = new Creator<CheckSmarterTaskDup>() {
        @Override
        public CheckSmarterTaskDup createFromParcel(Parcel in) {
            return new CheckSmarterTaskDup(in);
        }

        @Override
        public CheckSmarterTaskDup[] newArray(int size) {
            return new CheckSmarterTaskDup[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeByte((byte) (check ? 1 : 0));
    }
}
