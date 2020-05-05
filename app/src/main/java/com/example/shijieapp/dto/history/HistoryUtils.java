package com.example.shijieapp.dto.history;

import android.app.Activity;

import com.example.shijieapp.dto.user.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryUtils {
    private Activity activity;
    private List<History> allInfo,historys;
    private History history;
    private SimpleDateFormat simpleDateFormat;
    private Date date;

    public HistoryUtils(){}
    public HistoryUtils(Activity activity){
        this.activity = activity;
    }

    public boolean queryHistory(long phone,String videoname) {
        return UserDatabase
                .getInstance(activity)
                .getHistoryDao()
                .queryHistory(phone,videoname);
    }

    public History queryHistorys(long phone,String videoname) {
        return UserDatabase
                .getInstance(activity)
                .getHistoryDao()
                .queryHistorys(phone,videoname);
    }

    public List<History> queryAllHistoryInfo(long phone) {
         allInfo = UserDatabase
                .getInstance(activity)
                .getHistoryDao()
                .getAllHistoryInfo(phone);
        return allInfo;
    }

    public void addHistory(String videoname, String videopath, String videoimagepath,long userphone) {
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        date = new Date(System.currentTimeMillis());

        history = new History();
        history.setVideoname(videoname);
        history.setVideopath(videopath);
        history.setVideoimagepath(videoimagepath);
        history.setUserphone(userphone);
        history.setClicktime(simpleDateFormat.format(date));

        UserDatabase
                .getInstance(activity)
                .getHistoryDao()
                .addHistory(history);
    }

    public void updateHistoryClickTime(long userphone,String videoname){

        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        date = new Date(System.currentTimeMillis());
        history = queryHistorys(userphone,videoname);
        history.setClicktime(simpleDateFormat.format(date));
        UserDatabase
                .getInstance(activity)
                .getHistoryDao()
                .updateHistory(history);

    }

    public void updateHistoryLastWatchTime(long userphone,String videoname,long lastwatchtime){

        history = queryHistorys(userphone,videoname);
        history.setLastwatchtime(lastwatchtime);
        UserDatabase
                .getInstance(activity)
                .getHistoryDao()
                .updateHistory(history);
    }

    public int deleteOneHistory(int hid,long userphone,String videoname){
        history = queryHistorys(userphone,videoname);
        return UserDatabase
                .getInstance(activity)
                .getHistoryDao()
                .deleteHistory(history);
    }

    public int deleteAllHistory(long userphone){
        historys = queryAllHistoryInfo(userphone);
        return UserDatabase
                .getInstance(activity)
                .getHistoryDao()
                .deleteAllHistory(historys);
    }
}
