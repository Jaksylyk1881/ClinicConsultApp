package com.example.clinicconsultapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.example.clinicconsultapp.data.Doctor_Category;
import com.example.clinicconsultapp.data.User;
import com.example.clinicconsultapp.fragment.FragmentHome;
import com.example.clinicconsultapp.fragment.FragmentNewEntry;
import com.example.clinicconsultapp.fragment.FragmentNotification;
import com.example.clinicconsultapp.fragment.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private  User user;
   private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        Intent intent = getIntent();
        if (intent.hasExtra("loggedUser")){
            user = (User) intent.getSerializableExtra("loggedUser");
        }


        bundle = new Bundle();
        bundle.putSerializable("user", user);
//        bundle.putParcelableArrayList("categories", (ArrayList<? extends Parcelable>) categories);
        FragmentHome fragInfo = new FragmentHome();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragInfo).commit();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.itemHome:
                        fragment= new FragmentHome();
                        fragment.setArguments(bundle);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("user","user");
//                        fragment.setArguments(bundle);
                        break;
                    case R.id.itemNewEntry:
                        fragment=new FragmentNewEntry();
                        break;
                    case R.id.itemNotification:
                        fragment=new FragmentNotification();
                        break;
                    case R.id.itemProfile:
                        fragment=new FragmentProfile();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                return true;
            }
        });
    }



}