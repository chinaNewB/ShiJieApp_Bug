package com.example.shijieapp.dto.history;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "historyies")
public class History {

    @NonNull
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    @ColumnInfo(name = "HId")
    private int id;

    @ColumnInfo(name = "VideoName")
    private String videoname;

    @ColumnInfo(name = "VideoPath")
    private String videopath;

    @ColumnInfo(name = "VideoImagePath")
    private String videoimagepath;

    @ColumnInfo(name = "ClickTime")
    private String clicktime;

    @ColumnInfo(name = "LastWatchTime")
    private long lastwatchtime;

    @ColumnInfo(name = "UserPhone")
    private long userphone;

    public History() {
    }

    @Ignore
    public History(String videoname, String videopath, String videoimagepath, String clicktime, long userphone) {
        this.videoname = videoname;
        this.videopath = videopath;
        this.videoimagepath = videoimagepath;
        this.clicktime = clicktime;
        this.userphone = userphone;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }

    public String getVideoimagepath() {
        return videoimagepath;
    }

    public void setVideoimagepath(String videoimagepath) {
        this.videoimagepath = videoimagepath;
    }

    public String getClicktime() {
        return clicktime;
    }

    public void setClicktime(String clicktime) {
        this.clicktime = clicktime;
    }

    public long getLastwatchtime() {
        return lastwatchtime;
    }

    public void setLastwatchtime(long lastwatchtime) {
        this.lastwatchtime = lastwatchtime;
    }

    public long getUserphone() {
        return userphone;
    }

    public void setUserphone(long userphone) {
        this.userphone = userphone;
    }
}
