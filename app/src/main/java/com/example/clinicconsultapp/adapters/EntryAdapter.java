package com.example.clinicconsultapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.data.Entry;

import java.util.ArrayList;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {
    @NonNull
    ArrayList<Entry> entries;
    private OnEntryClickListener onEntryClickListener;

    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
        this.onEntryClickListener = onEntryClickListener;
    }

    public interface OnEntryClickListener{
        void onEntryClick(int position);
    }
    public EntryAdapter(@NonNull ArrayList<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item,parent,false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryAdapter.EntryViewHolder holder, int position) {
        Entry entry = entries.get(position);
        holder.appointmentDoctorCategory.setText(entry.getEntryDocType());
        holder.appointmentDoctorName.setText(entry.getEntryDocName());
        holder.appointmentTime.setText(entry.getEntryDate());
        holder.appointmentCabinet.setText(entry.getEntryCabinet());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    class EntryViewHolder extends RecyclerView.ViewHolder{
        TextView appointmentDoctorCategory;
        TextView appointmentDoctorName;
        TextView appointmentTime;
        TextView appointmentCabinet;
        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmentDoctorCategory = itemView.findViewById(R.id.appointmentDoctorCategory);
            appointmentDoctorName = itemView.findViewById(R.id.appointmentDoctorName);
            appointmentTime = itemView.findViewById(R.id.appointmentTime);
            appointmentCabinet = itemView.findViewById(R.id.appointmentCabinet);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEntryClickListener!=null){
                        onEntryClickListener.onEntryClick(getPosition());
                    }
                }
            });

        }
    }
}
