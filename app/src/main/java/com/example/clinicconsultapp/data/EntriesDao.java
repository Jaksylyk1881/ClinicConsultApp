package com.example.clinicconsultapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntriesDao {
    @Query("SELECT * FROM entries")
    LiveData<List<Entry>> getAllEntries();

    @Query("SELECT * FROM entries WHERE userEmail==:email")
    LiveData<List<Entry>> getAllEntriesByEmail(String email);

    @Insert
    void insertEntry(Entry entry);

    @Query("DELETE FROM entries")
    void deleteAllEntries();

    @Delete
    void deleteEntry(Entry entry);
}
