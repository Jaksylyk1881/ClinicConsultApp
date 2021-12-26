package com.example.clinicconsultapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.adapters.NotificationAdapter;
import com.example.clinicconsultapp.data.Entry;
import com.example.clinicconsultapp.data.EntryViewModel;
import com.example.clinicconsultapp.data.Notification;
import com.example.clinicconsultapp.data.NotificationViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FragmentNotification extends Fragment {
    @NonNull
    private RecyclerView recyclerView;
    private NotificationViewModel viewModel;
    private ArrayList<Notification> notifications;
    private NotificationAdapter adapter;
    private Button buttonClear;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.notification_fragment,container,false);
        viewModel = ViewModelProviders.of((FragmentActivity) rootView.getContext()).get(NotificationViewModel.class);
        notifications = new ArrayList<>();
        getData();
        Collections.reverse(notifications);
        recyclerView = rootView.findViewById(R.id.notificationRecyclerView);
        buttonClear = rootView.findViewById(R.id.buttonClearNotifications);
        adapter = new NotificationAdapter(notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(adapter);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.DeleteAllNotifications();
            }
        });
        return rootView;
    }

    private void getData(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("logged",0);
        String email = sharedPreferences.getString("userEmail",null);
        LiveData<List<Notification>> notificationsFromDB = viewModel.getNotifications();
        notificationsFromDB.observe(requireActivity(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notificationsFromLiveData) {
                notifications.clear();
                for (Notification e: notificationsFromLiveData) {
                    if (email.equals(e.getUserEmail())){
                        notifications.add(e);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

    }
}
