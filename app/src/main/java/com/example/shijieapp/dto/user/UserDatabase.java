package com.example.shijieapp.dto.user;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.shijieapp.dto.history.History;
import com.example.shijieapp.dto.history.HistoryDao;

@Database(entities = {User.class, History.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DB_NAME = "shi_jie.db";
    private static volatile UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static UserDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                UserDatabase.class,
                DB_NAME)
                .addMigrations(MIGRATION_1_2)
//                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    //数据库变动添加Migration
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //数据库的具体变动，我是在之前的user表中添加了新的column，名字是age。
            //类型是integer，不为空，默认值是0
            database.execSQL("CREATE TABLE IF NOT EXISTS `History` (`HId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "                                                   `VideoName` TEXT, " +
                    "                                                   `VideoPath` TEXT, " +
                    "                                                   `VideoImagePath` TEXT," +
                    "                                                   `UserPhone` LONG," +
                    "                                                   `ClickTime` DATETIME," +
                    "                                                   `LastWatchTime` LONG)");
        }
    };


    public abstract UserDao getUserDao();
    public abstract HistoryDao getHistoryDao();

}