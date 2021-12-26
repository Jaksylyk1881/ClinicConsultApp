package com.example.clinicconsultapp.autorithation;

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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.clinicconsultapp.MainActivity;
import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.data.MainViewModel;
import com.example.clinicconsultapp.data.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmailLogin;
    private  EditText editTextPasswordLogin;
    private AlertDialog.Builder builderEmail;
    private AlertDialog.Builder builderPassword;
    private AlertDialog.Builder builderBack;
    private MainViewModel viewModel;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        editTextEmailLogin = findViewById(R.id.loginEditTextEmail);
        editTextPasswordLogin = findViewById(R.id.loginEditTextPassword);
        builderEmail = new AlertDialog.Builder(this);
        builderPassword = new AlertDialog.Builder(this);
        builderBack = new AlertDialog.Builder(this);
        users = new ArrayList<>();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getData();
        editTextPasswordLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        editTextEmailLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void onClickSignIn(View view) {

        if (!validateEmail() | !validatePassword()){
            return;
        }
        String email = editTextEmailLogin.getText().toString().trim();
        String password = editTextPasswordLogin.getText().toString().trim();
        boolean flag = false;
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                flag = true;
            }
        }
        if (!flag) {
            builderEmail.setMessage("Email Address "+email+ " is not associated with any account")
                    .setCancelable(false)
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builderEmail.create();
            alertDialog.setTitle("Couldn't find account");
            alertDialog.show();
        } else {
            User user = viewModel.getUserByEmail(email);
            if (password.equals(user.getPassword())){
                SharedPreferences sharedPreferences = getSharedPreferences("logged",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Intent intent = new Intent(this, MainActivity.class);
                editor.putInt("userId",user.getId());
                editor.putString("userName",user.getFullName());
                editor.putString("userEmail",user.getEmail());
                editor.putString("userPassword",user.getPassword());
                editor.commit();
                startActivity(intent);
                overridePendingTransition(R.anim.animation2,R.anim.animation1);
            }else {
                builderPassword.setMessage("You entered an incorrect password, please try again")
                        .setCancelable(false)
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builderPassword.create();
                alertDialog.setTitle("Incorrect password");
                alertDialog.show();
            }

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
    public void onClickSignUpAgain(View view) {
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

    }

    private boolean validateEmail() {
        String email = editTextEmailLogin.getEditableText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            editTextEmailLogin.setError("This field can not be empty");
            return false;
        } else if (!email.matches(emailPattern)) {
            editTextEmailLogin.setError("Invalid email address");
            return false;
        } else {
            editTextEmailLogin.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String password = editTextPasswordLogin.getEditableText().toString().trim();
        if (password.isEmpty()) {
            editTextPasswordLogin.setError("This field can not be empty");
            return false;
        }else {
            editTextPasswordLogin.setError(null);
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}