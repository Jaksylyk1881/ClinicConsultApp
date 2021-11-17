package com.example.clinicconsultapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.DoctorAdapter;
import com.example.clinicconsultapp.MainActivity;
import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.TypeAdapter;
import com.example.clinicconsultapp.data.Doctor;
import com.example.clinicconsultapp.data.Doctor_Category;
import com.example.clinicconsultapp.data.User;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    @NonNull

    private ArrayList<Doctor_Category> categories;
    private ArrayList<Doctor> doctors;
    private ArrayList<Doctor> allDoctors;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_fragment,container,false);
        TextView textView = rootView.findViewById(R.id.fullName);
        RecyclerView categoryRecycler = rootView.findViewById(R.id.categoryRecycler);
        RecyclerView doctorRecycler = rootView.findViewById(R.id.doctorRecycler);
        categories = new ArrayList<>();
        categories.add(new Doctor_Category(0,"Psychology"));
        categories.add(new Doctor_Category(1,"Cardiology"));
        categories.add(new Doctor_Category(2,"Gastrology"));
        categories.add(new Doctor_Category(3,"Therapist"));
        categories.add(new Doctor_Category(4,"Surgeon"));
        categories.add(new Doctor_Category(5,"Ophthalmologist"));
        categories.add(new Doctor_Category(6,"Urologist"));
        categories.add(new Doctor_Category(7,"Obstetrician-gynecologist"));
        doctors=new ArrayList<>();
        allDoctors= new ArrayList<>();
        allDoctors.add(new Doctor(0,"Dr.Mosia Gloria","Sr.Psychologist",R.drawable.nurse1,"dj",4,0));
        allDoctors.add(new Doctor(0,"Dr.Mosia Gloria","Sr.Psychologist",R.drawable.nurse1,"dj",5,0));
        allDoctors.add(new Doctor(0,"Dr.Mosia Gloria","Sr.Psychologist",R.drawable.nurse1,"dj",3,0));
        allDoctors.add(new Doctor(0,"Dr.Mosia Gloria","Sr.Cardiology",R.drawable.nurse1,"dj",4,1));
        allDoctors.add(new Doctor(0,"Dr.Mosia Gloria","Sr.Cardiology",R.drawable.nurse1,"dj",4,1));
        allDoctors.add(new Doctor(0,"Dr.Mosia Gloria","Sr.Cardiology",R.drawable.nurse1,"dj",4,1));
        doctors.addAll(allDoctors);
        assert getArguments() != null;
        User user = (User) getArguments().getSerializable("user");
        textView.setText(user.getFullName());
        TypeAdapter typeAdapter = new TypeAdapter(categories);
        DoctorAdapter doctorAdapter = new DoctorAdapter(doctors);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(),RecyclerView.HORIZONTAL,false));
        categoryRecycler.setAdapter(typeAdapter);
        doctorRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(),RecyclerView.HORIZONTAL,false));
        doctorRecycler.setAdapter(doctorAdapter);
        typeAdapter.setOnCategoryClickLListener(new TypeAdapter.OnCategoryClickLListener() {
            @Override
            public void onClick(int position) {
                doctors.clear();
                ArrayList<Doctor> filteredDoctors = new ArrayList<>();
                for (Doctor i:allDoctors
                     ) {
                    if (position==i.getCategory()){
                        filteredDoctors.add(i);
                    }
                }
                doctors.addAll(filteredDoctors);
                doctorAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }
}
