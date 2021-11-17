package com.example.clinicconsultapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.clinicconsultapp.data.MainViewModel;
import com.example.clinicconsultapp.data.User;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private MainViewModel viewModel;
    private List<User> users;
    private AlertDialog.Builder builderBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        builderBack = new AlertDialog.Builder(this);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextPassword = findViewById(R.id.EditTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        users = new ArrayList<>();
        getData();
        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        editTextConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        editTextFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    public void onClickSignUp(View view) {
        if (!validateFullName() | !validateEmail() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        boolean flag = false;
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                flag = true;
            }
        }
        if (flag) {
            editTextEmail.setError("Such account already exists!!!");
        } else {
            editTextEmail.setError(null);
            User user = new User(fullName, email, password);
            viewModel.insertUser(user);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.animation2, R.anim.animation1);
        }
    }

    private void getData() {
        LiveData<List<User>> notesFromDB = viewModel.getUsers();
        notesFromDB.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> notesFromLiveData) {
                users.clear();
                users.addAll(notesFromLiveData);
            }
        });

    }

    private boolean validateFullName() {
        String fullName = editTextFullName.getEditableText().toString().trim();
        if (fullName.isEmpty()) {
            editTextFullName.setError("This field can not be empty");
            return false;
        } else {
            editTextFullName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String email = editTextEmail.getEditableText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            editTextEmail.setError("This field can not be empty");
            return false;
        } else if (!email.matches(emailPattern)) {
            editTextEmail.setError("Invalid email address");
            return false;
        } else {
            editTextEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = editTextPassword.getEditableText().toString().trim();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (password.isEmpty()) {
            editTextPassword.setError("This field can not be empty");
            return false;
        } else if (!password.matches(passwordVal)) {
            editTextPassword.setError("Password is weak!");
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String password = editTextPassword.getEditableText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getEditableText().toString().trim();
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("This field can not be empty");
            return false;
        } else if (!confirmPassword.equals(password)) {
            editTextConfirmPassword.setError("Password does not match!");
            return false;
        } else {
            editTextConfirmPassword.setError(null);
            return true;
        }
    }


    @Override
    public void onBackPressed() {
        builderBack.setMessage("Do you really want log out?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builderBack.create();
        alertDialog.setTitle("Log off");
        alertDialog.show();
    }

    public void onClickSignInFromSignUp(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation2, R.anim.animation1);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}