package com.example.clinicconsultapp.data;

import java.io.Serializable;

public class Doctor implements Serializable {
    private int doctorId;
    private String doctorName;
    private String doctorType;
    private int doctorImage;
    private String description;
    private int rating;
    private int category;

    public Doctor(int doctorId, String doctorName, String doctorType, int doctorImage, String description, int rating, int category) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorType = doctorType;
        this.doctorImage=doctorImage;
        this.description = description;
        this.rating = rating;
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public int getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(int doctorImage) {
        this.doctorImage = doctorImage;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
