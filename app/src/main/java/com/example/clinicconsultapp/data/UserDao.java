package com.example.clinicconsultapp.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE email==:userEmail")
    User getUserByEmail(String userEmail);

    @Query("DELETE FROM users")
    void deleteAllUsers();

    @Insert
    void insertUser(User user);
}

