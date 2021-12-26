package com.example.clinicconsultapp.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int notificationId;
    private String userEmail;
    private String doctorType;
    private String doctorName;
    private String recordingTime;

    public Notification() {
        this.notificationId = notificationId;
        this.userEmail = userEmail;
        this.doctorType = doctorType;
        this.doctorName = doctorName;
        this.recordingTime = recordingTime;
    }

    @Ignore
    public Notification(String userEmail, String doctorType, String doctorName, String recordingTime) {
        this.userEmail = userEmail;
        this.doctorType = doctorType;
        this.doctorName = doctorName;
        this.recordingTime = recordingTime;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getRecordingTime() {
        return recordingTime;
    }

    public void setRecordingTime(String recordingTime) {
        this.recordingTime = recordingTime;
    }
}
