package com.sayar.sila.bebetakvim.activities;

/**
 * Created by zisan on 20.09.2016.
 */
public class Diaper {

    private long id;
    private String diaper;
    private String time;
    public String date;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDiaper() {
        return diaper;
    }

    public String getTime() {
        return time;
    }

    public String getDate() { return date; }

    public void setDiaper(String diaper) { this.diaper = diaper;    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setDate(String date) { this.date = date; }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return diaper;
    }
}
