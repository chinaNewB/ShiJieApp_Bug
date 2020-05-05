package com.example.shijieapp.dto.user;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users",indices = {@Index(value = {"Phone"},unique = true)})
public class User {
    @NonNull
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    @ColumnInfo(name = "UserId")
    private int id;

    @ColumnInfo(name = "UserName")
    private String username;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "Phone")
    private long phone;

    @ColumnInfo(name = "Pwd")
    private String pwd;

    @ColumnInfo(name = "Signature")
    private String signature;


    public User(){}

    @Ignore
    public User(String username, String date, int phone, String pwd, String signature) {
        this.username = username;
        this.date = date;
        this.phone = phone;
        this.pwd = pwd;
        this.signature = signature;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
