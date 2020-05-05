package com.example.shijieapp.bean;

import java.io.Serializable;

public class NetVideoItem implements Serializable {
    /**
     *
     */

    private int id;
    private String title;
    private String name;
    private String data;
    private String artist;
    private String desc;
    private String imageUrl;
    private long size;
    private long duration;

    /**
     *
     */
    public NetVideoItem() {
        super();
    }


    public NetVideoItem(String imageUrl, long duration, String name, String desc, String artist) {
        super();
        this.imageUrl = imageUrl;
        this.duration = duration;
        this.name = name;
        this.desc = desc;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "NetVideoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", data='" + data + '\'' +
                ", artist='" + artist + '\'' +
                ", desc='" + desc + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }



}

