package com.example.clinicconsultapp.data;

import android.os.Parcelable;

import java.io.Serializable;

public class Doctor_Category  {
    private int categoryId;
    private String categoryTitle;

    public Doctor_Category(int categoryId, String categoryTitle) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
