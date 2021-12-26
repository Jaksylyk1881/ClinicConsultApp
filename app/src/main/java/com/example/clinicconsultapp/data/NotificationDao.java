package com.example.clinicconsultapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notifications")
    LiveData<List<Notification>> getAllNotifications();

    @Query("SELECT * FROM notifications WHERE userEmail==:email")
    LiveData<List<Notification>> getAllNotificationsByEmail(String email);

    @Insert
    void insertNotification(Notification notification);

    @Query("DELETE FROM notifications")
    void deleteAllNotifications();

    @Delete
    void deleteNotification(Notification notification);
}
