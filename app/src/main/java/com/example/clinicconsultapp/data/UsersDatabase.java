package com.example.clinicconsultapp.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class, Entry.class, Notification.class},version = 5,exportSchema = false)
public abstract class UsersDatabase extends RoomDatabase {
    private static UsersDatabase database;
    private static final String DB_NAME = "users2.db";
    private static final Object LOCK = new Object();

    public static UsersDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, UsersDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }

    public abstract UserDao userDao();
    public abstract EntriesDao entriesDao();
    public abstract NotificationDao notificationDao();
}
