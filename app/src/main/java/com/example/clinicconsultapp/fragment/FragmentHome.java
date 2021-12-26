package com.example.clinicconsultapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.AboutUsActivity;
import com.example.clinicconsultapp.DocDetailActivity;
import com.example.clinicconsultapp.adapters.DoctorAdapter;
import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.adapters.TypeAdapter;
import com.example.clinicconsultapp.data.Doctor;
import com.example.clinicconsultapp.data.Doctor_Category;
import com.example.clinicconsultapp.data.User;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    @NonNull

    private ArrayList<Doctor_Category> categories;
    private ArrayList<Doctor> doctors;
    private ArrayList<Doctor> allDoctors;
    private ArrayViewModel viewModel;
    private ArrayList<String> docNames;
    private ArrayList<Integer> docCategory;
    private ArrayList<String> docCabinet;
    private ImageView imageViewProfile;
    private Button buttonReadMore;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_fragment,container,false);
        TextView textView = rootView.findViewById(R.id.fullName);
        RecyclerView categoryRecycler = rootView.findViewById(R.id.categoryRecycler);
        RecyclerView doctorRecycler = rootView.findViewById(R.id.doctorRecycler);
        buttonReadMore = rootView.findViewById(R.id.readMore);
        imageViewProfile = (ImageView) rootView.findViewById(R.id.profileImageInHomePage);
        String[] category = getResources().getStringArray(R.array.category);
        categories = new ArrayList<>();
        for (int i = 0; i<category.length;i++){
            categories.add(new Doctor_Category(i,category[i]));
        }
        doctors=new ArrayList<>();
        allDoctors= new ArrayList<>();
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        allDoctors.add(new Doctor(0,"Dr.Mosia Gloria","Sr.Psychologist",R.drawable.nurse1,description,4,0,"308"));
        allDoctors.add(new Doctor(1,"Dr.Kamila Karabay","Sr.Psychologist",R.drawable.nurse2,description,5,0,"308 a"));
        allDoctors.add(new Doctor(2,"Dr.Lisa Alex","Sr.Psychologist",R.drawable.nurse3,description,3,0,"308 b"));
        allDoctors.add(new Doctor(3,"Dr.Alexander Raymond","Sr.Cardiology",R.drawable.doc1,description,5,1,"309 a"));
        allDoctors.add(new Doctor(4,"Dr.James Mary","Sr.Cardiology",R.drawable.nurse2,description,4,1,"309 b"));
        allDoctors.add(new Doctor(5,"Dr.John Patrick","Sr.Cardiology",R.drawable.doc2,description,4,1,"309 c"));
        allDoctors.add(new Doctor(6,"Dr.Thomas Sarah","Sr.Gastrology",R.drawable.nurse1,description,4,2,"400"));
        allDoctors.add(new Doctor(7,"Dr.Charles Nancy","Sr.Gastrology",R.drawable.nurse2,description,5,2,"401"));
        allDoctors.add(new Doctor(8,"Dr.Scott Brandon","Sr.Surgeon",R.drawable.nurse3,description,3,4,"402"));
        allDoctors.add(new Doctor(9,"Dr.Benjamin Samuel","Sr.Surgeon",R.drawable.nurse3,description,5,4,"403"));
        allDoctors.add(new Doctor(10,"Dr.Nathan Victoria","Sr.Ophthalmologist",R.drawable.nurse2,description,6,5,"404"));
        allDoctors.add(new Doctor(11,"Dr.Douglas Olivia","Sr.Urologist",R.drawable.nurse1,description,4,6,"405"));
        docNames = new ArrayList<>();
        docCategory = new ArrayList<>();
        docCabinet = new ArrayList<>();
        for (int i=0; i<allDoctors.size(); i++) {
            docNames.add(allDoctors.get(i).getDoctorName());
            docCategory.add(allDoctors.get(i).getCategory());
            docCabinet.add(allDoctors.get(i).getCabinet());
        }

        doctors.add(allDoctors.get(0));
        doctors.add(allDoctors.get(1));
        doctors.add(allDoctors.get(2));
        assert getArguments() != null;
        User user = (User) getArguments().getSerializable("user");
        textView.setText(user.getFullName());
        TypeAdapter typeAdapter = new TypeAdapter(categories);
        DoctorAdapter doctorAdapter = new DoctorAdapter(doctors);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(),RecyclerView.HORIZONTAL,false));
        categoryRecycler.setAdapter(typeAdapter);
        doctorRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext(),RecyclerView.HORIZONTAL,false));
        doctorRecycler.setAdapter(doctorAdapter);
//        FragmentDetail fragmentDetail = new FragmentDetail();
        doctorAdapter.setOnDoctorClickListener(new DoctorAdapter.OnDoctorClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(requireContext(), DocDetailActivity.class);
                intent.putExtra("selectedDoctor",doctors.get(position));
                startActivity(intent);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("selectedDoctor",doctors.get(position));
//                fragmentDetail.setArguments(bundle);
//                AppCompatActivity activity = (AppCompatActivity)view.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragmentDetail).addToBackStack(null).commit();
            }
        });
        typeAdapter.setOnCategoryClickLListener(new TypeAdapter.OnCategoryClickLListener() {
            @Override
            public void onClick(int position) {
                if (TypeAdapter.selectedIndex!=position) TypeAdapter.selectedIndex=position;
                typeAdapter.notifyDataSetChanged();
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
        buttonReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });

        setImage();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ArrayViewModel.class);
        viewModel.setDocNames(docNames);
        viewModel.setDocCategory(docCategory);
        viewModel.setDocCabinets(docCabinet);
    }
    public void setImage(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("logged",0);
        String userEmail = sharedPreferences.getString("userEmail",null);
        String uri =sharedPreferences.getString(userEmail,null);
        if (uri!=null){
            Uri imageUri = Uri.parse(uri);
            imageViewProfile.setImageURI(imageUri);
        }

    }
}
