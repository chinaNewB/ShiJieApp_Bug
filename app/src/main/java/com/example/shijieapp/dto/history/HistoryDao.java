package com.example.shijieapp.dto.history;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void addHistory(History history);

    @Delete
    int deleteHistory(History history);

    @Delete
    int deleteAllHistory(List<History> history);

    @Update
    int updateHistory(History history);

    @Query("SELECT * FROM historyies where historyies.UserPhone=:userphone order by historyies.ClickTime DESC")
    List<History> getAllHistoryInfo(long userphone);

    @Query("SELECT * FROM historyies where historyies.UserPhone=:userphone and historyies.VideoName=:videoname ")
    boolean queryHistory(long userphone, String videoname);

    @Query("SELECT * FROM historyies where historyies.UserPhone=:userphone and historyies.VideoName=:videoname ")
    History queryHistorys(long userphone, String videoname);

}
