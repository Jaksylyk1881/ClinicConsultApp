package com.example.clinicconsultapp.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "entries")
public class Entry {
    @PrimaryKey(autoGenerate = true)
    private int entryId;
    private String userEmail;

    public Entry(Integer integer, String tmpUserEmail, Object entryDocType, Object entryDocName, Object entryDate, Object entryCabinet, Object recordingTime) {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    private String entryDocType;
    private String entryDocName;
    private String entryDate;
    private String entryCabinet;
    private String recordingTime;

    public Entry(int entryId, String userEmail, String entryDocType, String entryDocName, String entryDate, String entryCabinet,String recordingTime) {
        this.entryId = entryId;
        this.userEmail = userEmail;
        this.entryDocType = entryDocType;
        this.entryDocName = entryDocName;
        this.entryDate = entryDate;
        this.entryCabinet = entryCabinet;
        this.recordingTime = recordingTime;
    }
    @Ignore
    public Entry(String userEmail , String entryDocType, String entryDocName, String entryDate, String entryCabinet,String recordingTime) {
        this.userEmail = userEmail;
        this.entryDocType = entryDocType;
        this.entryDocName = entryDocName;
        this.entryDate = entryDate;
        this.entryCabinet = entryCabinet;
        this.recordingTime = recordingTime;
    }

    public String getRecordingTime() {
        return recordingTime;
    }

    public void setRecordingTime(String recordingTime) {
        this.recordingTime = recordingTime;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getEntryDocType() {
        return entryDocType;
    }

    public void setEntryDocType(String entryDocType) {
        this.entryDocType = entryDocType;
    }

    public String getEntryDocName() {
        return entryDocName;
    }

    public void setEntryDocName(String entryDocName) {
        this.entryDocName = entryDocName;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryCabinet() {
        return entryCabinet;
    }

    public void setEntryCabinet(String entryCabinet) {
        this.entryCabinet = entryCabinet;
    }
}
