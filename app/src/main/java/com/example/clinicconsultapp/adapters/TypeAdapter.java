package com.example.clinicconsultapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.data.Doctor_Category;

import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {
    @NonNull
    public static int selectedIndex = 0;
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
        if (selectedIndex ==position){
            holder.strikeThrough.setVisibility(View.VISIBLE);
        }else{
            holder.strikeThrough.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

      class TypeViewHolder extends RecyclerView.ViewHolder{
        TextView textViewType;
        ImageView strikeThrough;
        public TypeViewHolder(View itemView) {

            super(itemView);
            textViewType = itemView.findViewById(R.id.textViewType);
            strikeThrough = itemView.findViewById(R.id.strikeThrough);
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
