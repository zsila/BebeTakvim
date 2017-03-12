package com.sayar.sila.bebetakvim.activities;

import java.sql.Time;

/**
 * Created by zisan on 9.08.2016.
 */
public class Food {
    private long id;
    private String comment;
    private String time;
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }
    public String getDate() {
        return date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setTime(String time) {  this.time = time;    }
    public void setDate(String date) {  this.date = date;    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return comment;
    }
}
