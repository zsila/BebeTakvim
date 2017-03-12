package com.sayar.sila.bebetakvim.activities;

/**
 * Created by zisan on 20.10.2016.
 */
public class Sleeptime {
    private long id;
    private String start;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {this.start = start;  }

    @Override
    public String toString() {
        return start;
    }
}
