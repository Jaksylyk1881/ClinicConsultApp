package com.example.clinicconsultapp.autorithation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.clinicconsultapp.MainActivity;
import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.data.MainViewModel;

public class StartActivity extends AppCompatActivity {
   private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("logged",0);
        String userName = sharedPreferences.getString("userName",null);
        String userEmail = sharedPreferences.getString("userEmail", null);
        String passWord = sharedPreferences.getString("userPassword", null);
        if (userName!=null&&userEmail!=null&&passWord!=null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onClickGetStarted(View view) {
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation2,R.anim.animation1);
    }
}