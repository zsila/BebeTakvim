package com.sayar.sila.bebetakvim.activities;

/**
 * Created by zisan on 6.10.2016.
 */
public class Control {
    private long id;
    private String not;
    private String boy;
    private String kilo;
    private String time;
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNot() {
        return not;
    }
    public String getBoy() { return boy; }
    public String getKilo() { return kilo;  }

    public String getTime() {
        return time;
    }
    public String getDate() {
        return date;
    }

    public void setNot(String not) {
        this.not = not;
    }
    public void setBoy(String boy) {  this.boy = boy; }
    public void setKilo(String kilo) { this.kilo = kilo; }
    public void setTime(String time) {  this.time = time;    }
    public void setDate(String date) {  this.date = date;    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return not;
    }
}
