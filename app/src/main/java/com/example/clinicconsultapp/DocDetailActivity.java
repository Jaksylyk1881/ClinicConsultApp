package com.example.clinicconsultapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.clinicconsultapp.data.Doctor;

public class DocDetailActivity extends AppCompatActivity {
    private Doctor doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        setContentView(R.layout.activity_doc_detail);
        Intent intent = getIntent();
        if (intent.hasExtra("selectedDoctor")){
             doctor = (Doctor) intent.getSerializableExtra("selectedDoctor");
        }
        ImageView imageView =findViewById(R.id.detailDoctorImage);
        TextView docName = findViewById(R.id.detailDoctorName);
        TextView docType = findViewById(R.id.detailDoctorType);
        TextView docRating = findViewById(R.id.detailDoctorRating);
        RatingBar ratingBar = findViewById(R.id.detailRatingBar);
        TextView docDescription = findViewById(R.id.detailDoctorDescription);
        imageView.setImageResource(doctor.getDoctorImage());
        docName.setText(doctor.getDoctorName());
        docType.setText(doctor.getDoctorType());
        docDescription.setText(doctor.getDescription());
        docRating.setText(""+doctor.getRating());
        ratingBar.setRating((float) doctor.getRating());
    }
}