package com.sayar.sila.bebetakvim.units;

/**
 * Created by zisan on 17.11.2016.
 */
public class Language {

    private long id;
    private String lang;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String language) {this.lang = language;  }

    @Override
    public String toString() {
        return lang;
    }
}
