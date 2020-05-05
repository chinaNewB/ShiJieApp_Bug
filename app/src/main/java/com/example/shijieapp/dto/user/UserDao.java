package com.example.shijieapp.dto.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface UserDao {

    @Query("SELECT * FROM users where users.Phone=:phone")
    boolean searchPhoneOnly(long phone);

    @Query("SELECT * FROM users where users.Phone=:phone and users.Pwd=:pwd")
    boolean queryUserByPhonePwd(long phone, String pwd);

    @Query("SELECT * FROM users where users.Phone=:phone")
    User searchUserByPhone(long phone);

    @Query("SELECT * FROM users where users.Phone=:phone")
    List<User> getAllInfo(long phone);

    @Insert
    void addUser(User user);


    @Update
    int updateUser(User user);



}

