package com.example.shijieapp.dto.user;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserUtils {
    private Activity activity;
    private List<User> allInfo;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String uuid;
    private User user;

    public UserUtils(Activity activity){
        this.activity = activity;
    }

    public boolean query(long phone,String pwd) {
        return UserDatabase
                .getInstance(activity)
                .getUserDao()
                .queryUserByPhonePwd(phone,pwd);
    }

    public List<User> queryUserInfo(long phone) {
         allInfo = UserDatabase
                .getInstance(activity)
                .getUserDao()
                .getAllInfo(phone);
        return allInfo;
    }


    public void insert(long phone, String pwd) {
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        date = new Date(System.currentTimeMillis());
        uuid = UUID.randomUUID().toString();

        user = new User();
        user.setUsername(uuid.replace("-",""));
        user.setPhone(phone);
        user.setPwd(pwd);
        user.setDate(simpleDateFormat.format(date));

        UserDatabase
                .getInstance(activity)
                .getUserDao()
                .addUser(user);

    }
    public boolean queryPhoneOnly(long phone) {
        return UserDatabase
                .getInstance(activity)
                .getUserDao()
                .searchPhoneOnly(phone);
    }

    public User queryUserByPhone(long phone) {
        return UserDatabase
                .getInstance(activity)
                .getUserDao()
                .searchUserByPhone(phone);
    }

    public void updateUserInfo(long phone,String username,String usersignature,String pwd){
        user = queryUserByPhone(phone);
        user.setUsername(username);
        user.setSignature(usersignature);
        user.setPwd(pwd);

        UserDatabase
                .getInstance(activity)
                .getUserDao()
                .updateUser(user);
    }
}
