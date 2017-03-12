package com.sayar.sila.bebetakvim.activities;

/**
 * Created by zisan on 31.08.2016.
 */
public class Sleep {
    private long id;
    private String duration;
    private String time;
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setSleep(String duration) { this.duration = duration;
    }
    public void setTime(String time) { this.time = time;    }
    public void setDate(String date) { this.date = date;    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return duration;
    }
}
