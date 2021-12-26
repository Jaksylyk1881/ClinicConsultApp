package com.example.clinicconsultapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.data.Doctor;

import java.util.ArrayList;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    @NonNull
    ArrayList<Doctor> doctors;

    public DoctorAdapter(@NonNull ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    private OnDoctorClickListener onDoctorClickListener;

    public void setOnDoctorClickListener(OnDoctorClickListener onDoctorClickListener) {
        this.onDoctorClickListener = onDoctorClickListener;
    }


    public interface OnDoctorClickListener{
        void onClick(int position);
    }


    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DoctorViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);
        holder.docImageView.setImageResource(doctor.getDoctorImage());
        holder.textViewName.setText(doctor.getDoctorName());
        holder.textViewCategory.setText(doctor.getDoctorType());
        holder.rating.setRating((float) doctor.getRating());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView docImageView;
        TextView textViewName;
        TextView textViewCategory;
        RatingBar rating;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            docImageView = itemView.findViewById(R.id.docImageView);
            textViewName = itemView.findViewById(R.id.textDoctorName);
            textViewCategory = itemView.findViewById(R.id.textCategory);
            rating = itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDoctorClickListener!=null){
                        onDoctorClickListener.onClick(getPosition());
                    }
                }
            });
        }
    }
}
