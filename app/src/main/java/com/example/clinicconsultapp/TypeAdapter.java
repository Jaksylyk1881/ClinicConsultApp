package com.example.clinicconsultapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.data.Doctor_Category;

import java.util.ArrayList;
import java.util.Locale;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {
    @NonNull
     ArrayList<Doctor_Category> list;
    private OnCategoryClickLListener onCategoryClickLListener;
    public void setOnCategoryClickLListener(OnCategoryClickLListener onCategoryClickLListener) {
        this.onCategoryClickLListener = onCategoryClickLListener;
    }
    public interface OnCategoryClickLListener{
        void onClick(int position);
    }
    public TypeAdapter(ArrayList<Doctor_Category> list) {
        this.list = list;
    }

    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item,parent,false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeAdapter.TypeViewHolder holder, int position) {
        Doctor_Category category = list.get(position);
        holder.textViewType.setText(category.getCategoryTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

      class TypeViewHolder extends RecyclerView.ViewHolder{
        TextView textViewType;
        public TypeViewHolder(View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.textViewType);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCategoryClickLListener!=null){
                        onCategoryClickLListener.onClick(getPosition());
                    }
                }
            });
        }
    }

}
