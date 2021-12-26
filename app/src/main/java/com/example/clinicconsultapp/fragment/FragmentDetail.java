package com.example.clinicconsultapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.data.Doctor;

public class FragmentDetail extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.detail_fragment,container,false);
        assert getArguments() != null;
        Doctor doctor = (Doctor)getArguments().getSerializable("selectedDoctor");
        ImageView imageView = rootView.findViewById(R.id.detailDoctorImage);
        TextView docName = rootView.findViewById(R.id.detailDoctorName);
        TextView docType = rootView.findViewById(R.id.detailDoctorType);
        TextView docRating = rootView.findViewById(R.id.detailDoctorRating);
        RatingBar ratingBar = rootView.findViewById(R.id.detailRatingBar);
        TextView docDescription = rootView.findViewById(R.id.detailDoctorDescription);
        imageView.setImageResource(doctor.getDoctorImage());
        docName.setText(doctor.getDoctorName());
        docType.setText(doctor.getDoctorType());
        docDescription.setText(doctor.getDescription());
        docRating.setText(""+doctor.getRating());
        ratingBar.setRating((float) doctor.getRating());
        return rootView;
    }

}
