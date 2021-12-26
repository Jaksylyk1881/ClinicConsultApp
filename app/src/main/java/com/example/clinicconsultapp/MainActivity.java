package com.example.clinicconsultapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.example.clinicconsultapp.data.Doctor_Category;
import com.example.clinicconsultapp.data.User;
import com.example.clinicconsultapp.fragment.ArrayViewModel;
import com.example.clinicconsultapp.fragment.FragmentDetail;
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
    private Bundle bundle1;
    private ArrayViewModel arrayViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("logged",0);
        String userName = sharedPreferences.getString("userName",null);
        String userEmail = sharedPreferences.getString("userEmail", null);
        String passWord = sharedPreferences.getString("userPassword", null);


        int id = sharedPreferences.getInt("userId",1);
        user = new User(id,userName,userEmail,passWord);
        bundle = new Bundle();
        bundle.putSerializable("user", user);
//        bundle.putParcelableArrayList("categories", (ArrayList<? extends Parcelable>) categories);
        FragmentHome fragmentHome1 = new FragmentHome();
        FragmentNotification fragmentNotification = new FragmentNotification();
        Intent intent = getIntent();
        if (intent.hasExtra("openNotifications")){
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
            bottomNavigationView.setSelectedItemId(R.id.itemNotification);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragmentNotification).commit();
        }else {
            fragmentHome1.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragmentHome1).commit();
        }
        FragmentNewEntry fragmentNewEntry = new FragmentNewEntry();
        FragmentProfile fragmentProfile = new FragmentProfile();
        bundle1 = new Bundle();
        arrayViewModel = new ViewModelProvider(this).get(ArrayViewModel.class);
        arrayViewModel.getDocNames().observe(this,docName->{
            bundle1.putStringArrayList("docNames",docName);
        });
        arrayViewModel.getDocCategories().observe(this,docCategory->{
            bundle1.putIntegerArrayList("docCategory",docCategory);
        });
        arrayViewModel.getDocCabinets().observe(this,docCabinet->{
            bundle1.putStringArrayList("docCabinet",docCabinet);
        });


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.itemHome:
                        fragment= fragmentHome1;
                        fragment.setArguments(bundle);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("user","user");
//                        fragment.setArguments(bundle);
                        break;
                    case R.id.itemNewEntry:
                        fragment=fragmentNewEntry;
                        fragment.setArguments(bundle1);
                        break;
                    case R.id.itemNotification:
                        fragment = fragmentNotification;
                        break;
                    case R.id.itemProfile:
                        fragment=fragmentProfile;
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                return true;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment instanceof FragmentHome||fragment instanceof FragmentProfile|| fragment instanceof FragmentNewEntry||fragment instanceof FragmentNotification) {
            finishAffinity();
        }else {
            fm.popBackStack();
        }
        }

}