package com.ambongan.weassist;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@IgnoreExtraProperties
public class ItemModel implements Serializable {

    private String title;
    private String who;
    private String what;
    private String when;
    private String where;
    private String key;
    private String time;
    private String timeBackend;
    private String expireTime;
    private String expireTimeBackend;

    public ItemModel() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getWho() { return who; }
    public void setWho(String who) { this.who = who; }

    public String getWhat() { return what; }
    public void setWhat(String what) { this.what = what; }

    public String getWhen() { return when; }
    public void setWhen(String when) { this.when = when; }

    public String getWhere() { return where; }
    public void setWhere(String where) { this.where = where; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getTimeBackend() {
        return timeBackend;
    }

    public void setTimeBackend(String timeBackend) {
        this.timeBackend = timeBackend;
    }

    public String getExpireTimeBackend() {
        return expireTimeBackend;
    }

    public void setExpireTimeBackend(String expireTimeBackend) {
        this.expireTimeBackend = expireTimeBackend;
    }

    @Override
    public String toString() {
        return " " + title + "\n" +
                " " + who + "\n" +
                " " + what + "\n" +
                " " + when + "\n" +
                " " + where;
    }

    public ItemModel(String title, String who, String what, String when, String where, String time, String timeBackend, String expireTime,  String expireTimeBackend) {
        this.title    = title;
        this.who   = who;
        this.what   = what;
        this.when   = when;
        this.where   = where;
        this.time   = time;
        this.timeBackend   = timeBackend;
        this.expireTime   = expireTime;
        this.expireTimeBackend   = expireTimeBackend;
    }


}
