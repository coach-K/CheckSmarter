package com.andela.checksmarter.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by CodeKenn on 19/04/16.
 */
@RealmClass
public class CheckSmarterTaskJava extends RealmObject {
    private int id = 0;

    private String title = "Quick Task";
    private boolean check = false;

    public CheckSmarterTaskJava() {
    }

    public CheckSmarterTaskJava(int id) {
        this.id = id;
    }

    public CheckSmarterTaskJava(String title) {
        this.title = title;
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
}
