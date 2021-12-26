package com.example.clinicconsultapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.data.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    @NonNull
    ArrayList<Notification> notifications;

    public NotificationAdapter(@NonNull ArrayList<Notification> entries) {
        this.notifications = entries;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        Notification entry = notifications.get(position);
        holder.notiDocName.setText(entry.getDoctorName());
        holder.notiRecordingTime.setText(entry.getRecordingTime());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{
        TextView notiDocName;
        TextView notiRecordingTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notiDocName =itemView.findViewById(R.id.notificationDoctorName);
            notiRecordingTime = itemView.findViewById(R.id.notificationRecordingTime);
        }
    }
}
