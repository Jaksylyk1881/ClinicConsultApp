package com.example.clinicconsultapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.clinicconsultapp.data.Entry;
import com.example.clinicconsultapp.data.EntryViewModel;
import com.example.clinicconsultapp.data.MainViewModel;
import com.example.clinicconsultapp.data.Notification;
import com.example.clinicconsultapp.data.NotificationViewModel;
import com.example.clinicconsultapp.fragment.FragmentNotification;

import java.util.ArrayList;
import java.util.Calendar;

import static android.telephony.AvailableNetworkInfo.PRIORITY_HIGH;

public class NewEntryActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID=1;
    private static final String CHANNEL_ID="CHANNEL_ID";

    TextView currentDateTime;
    Calendar dateAndTime = Calendar.getInstance();
    Calendar WeekDayAndTime = Calendar.getInstance();

    private Spinner spinnerCategory;
    private Spinner spinnerDoctors;
    private ArrayList<String> docNames;
    private ArrayList<Integer> docCategory;
    private ArrayList<String> docCabinets;
    private EntryViewModel viewModel;
    private NotificationViewModel notificationViewModel;
    private ArrayList<String> sortedDocNames;
    private String cabinet="";
    private String currentTime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();
        if (intent.hasExtra("docNames")&&intent.hasExtra("docCategory")&&intent.hasExtra("docCabinet")){
            docNames = intent.getStringArrayListExtra("docNames");
            docCategory = intent.getIntegerArrayListExtra("docCategory");
            docCabinets = intent.getStringArrayListExtra("docCabinet");
        }
        viewModel = ViewModelProviders.of(this).get(EntryViewModel.class);
        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        currentDateTime = findViewById(R.id.currentDateTime);
        setInitialDateTime();

        sortedDocNames = new ArrayList<>();


        spinnerCategory = findViewById(R.id.spinnerDocCategory);
        spinnerDoctors = findViewById(R.id.spinnerDoc);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.brand_dropdown,getResources().getStringArray(R.array.category));
        arrayAdapter.setDropDownViewResource(R.layout.brand_dropdown);
        spinnerCategory.setAdapter(arrayAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortDoctorByType(position);
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.brand_dropdown,sortedDocNames);
                arrayAdapter1.setDropDownViewResource(R.layout.brand_dropdown);
                spinnerDoctors.setAdapter(arrayAdapter1);
                spinnerDoctors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String docName = spinnerDoctors.getSelectedItem().toString();
                        setCabinetByDocName(docName);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void setCabinetByDocName(String name){
        for (int i = 0; i<docNames.size(); i++){
            if (docNames.get(i).equals(name)){
                cabinet=docCabinets.get(i);
            }
        }
    }

    private void sortDoctorByType(int selectedItemPos){
        sortedDocNames.clear();
        for (int i =0; i<docCategory.size();i++){
            if (selectedItemPos==docCategory.get(i)){
                sortedDocNames.add(docNames.get(i));
            }
        }
    }

    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    public void onClickAddEntry(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("logged",0);
        String userEmail = sharedPreferences.getString("userEmail", null);
        String docType = spinnerCategory.getSelectedItem().toString();
        String doc = spinnerDoctors.getSelectedItem().toString();
        String date = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME);

        currentTime = DateUtils.formatDateTime(this,WeekDayAndTime.getTimeInMillis(),DateUtils.FORMAT_SHOW_WEEKDAY|DateUtils.FORMAT_SHOW_TIME);
        Log.i("aaa",currentTime);
        viewModel.InsertEntry(new Entry(userEmail,docType,doc,date,cabinet+", cab.",currentTime));
        notificationViewModel.InsertNotification(new Notification(userEmail,docType,doc,currentTime));

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("openNotifications",0);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Congratulations, you have successfully signed up")
                        .setContentText("Touch to see more")
                        .setPriority(PRIORITY_HIGH);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID,notificationBuilder.build());

        onBackPressed();

    }
    public static void createChannelIfNeeded(NotificationManager notificationManager){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID,NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}