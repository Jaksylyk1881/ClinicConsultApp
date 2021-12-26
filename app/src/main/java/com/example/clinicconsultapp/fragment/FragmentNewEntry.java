package com.example.clinicconsultapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicconsultapp.DirectionsActivity;
import com.example.clinicconsultapp.MainActivity;
import com.example.clinicconsultapp.NewEntryActivity;
import com.example.clinicconsultapp.R;
import com.example.clinicconsultapp.RecipesActivity;
import com.example.clinicconsultapp.adapters.EntryAdapter;
import com.example.clinicconsultapp.data.Entry;
import com.example.clinicconsultapp.data.EntryViewModel;
import com.example.clinicconsultapp.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentNewEntry extends Fragment {
    @Nullable
    private RecyclerView currentRecyclerView;
    private EntryAdapter entryAdapter;
    private EntryViewModel viewModel;
    private ArrayList<Entry> entries;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.new_entry_fragment,container,false);
        entries = new ArrayList<>();
        ArrayList<String> docNames;
        ArrayList<Integer> docCategory;
        ArrayList<String> docCabinet;
        assert getArguments() != null;
        docNames = getArguments().getStringArrayList("docNames");
        docCategory = getArguments().getIntegerArrayList("docCategory");
        docCabinet = getArguments().getStringArrayList("docCabinet");
        Button button = (Button)rootView.findViewById(R.id.newEntryButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(rootView.getContext(), NewEntryActivity.class);
                intent.putStringArrayListExtra("docNames",docNames);
                intent.putIntegerArrayListExtra("docCategory",docCategory);
                intent.putStringArrayListExtra("docCabinet",docCabinet);
                startActivity(intent);
            }
        });


         currentRecyclerView = rootView.findViewById(R.id.currentEntriesRecyclerView);
         viewModel = ViewModelProviders.of((FragmentActivity) rootView.getContext()).get(EntryViewModel.class);
         getData();
         entryAdapter = new EntryAdapter(entries);
        assert currentRecyclerView != null;
        currentRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
         currentRecyclerView.setAdapter(entryAdapter);
         entryAdapter.setOnEntryClickListener(new EntryAdapter.OnEntryClickListener() {
             @Override
             public void onEntryClick(int position) {

             }
         });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(currentRecyclerView);
        CardView cardViewDirections = (CardView) rootView.findViewById(R.id.cardViewDirections);
        CardView cardViewRecipes = (CardView) rootView.findViewById(R.id.cardViewRecipes);
        cardViewDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), DirectionsActivity.class);
                startActivity(intent);
            }
        });
        cardViewRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), RecipesActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


    private   void  remove(int position){
        Entry entry = entries.get(position);
        viewModel.DeleteEntry(entry);
    }
    private void getData(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("logged",0);
        String email = sharedPreferences.getString("userEmail",null);
        LiveData<List<Entry>> notesFromDB = viewModel.getEntries();
        notesFromDB.observe(requireActivity(), new Observer<List<Entry>>() {
            @Override
            public void onChanged(List<Entry> entriesFromLiveData) {
                entries.clear();
                for (Entry e: entriesFromLiveData) {
                    if (email.equals(e.getUserEmail())){
                        entries.add(e);
                    }
                }
                entryAdapter.notifyDataSetChanged();
            }
        });

    }
}
