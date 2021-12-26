package com.example.clinicconsultapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EntryViewModel extends AndroidViewModel {
    private static UsersDatabase database;
    private LiveData<List<Entry>> entries;
    public EntryViewModel(@NonNull Application application) {
        super(application);
        database = UsersDatabase.getInstance(getApplication());
        entries = database.entriesDao().getAllEntries();
    }

    public LiveData<List<Entry>> getEntries() {
        return entries;
    }

    public LiveData<List<Entry>> GetEntriesByEmail(String email){
        try {
            return new GetEntryTaskByEmail().execute(email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void InsertEntry(Entry entry){
        new InsertEntryTask().execute(entry);
    }
    public void DeleteAllEntries(){
        new DeleteAllEntryTask().execute();
    }
    public void DeleteEntry(Entry entry){
        new DeleteEntryTask().execute(entry);
    }
    private static class DeleteEntryTask extends AsyncTask<Entry,Void,Void>{

        @Override
        protected Void doInBackground(Entry... entries) {
            if (entries!=null&&entries.length>0){
                database.entriesDao().deleteEntry(entries[0]);
            }
            return null;
        }
    }
    private static class DeleteAllEntryTask extends  AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            database.entriesDao().deleteAllEntries();
            return null;
        }
    }
    private static class InsertEntryTask extends AsyncTask<Entry,Void,Void>{

        @Override
        protected Void doInBackground(Entry... entries) {
            if (entries!=null&&entries.length>0){
                database.entriesDao().insertEntry(entries[0]);
            }
            return null;
        }
    }
    private static class GetEntryTaskByEmail extends AsyncTask<String,Void,LiveData<List<Entry>>>{

        @Override
        protected LiveData<List<Entry>> doInBackground(String... strings) {
            if (strings!= null&&strings.length>0){
                database.entriesDao().getAllEntriesByEmail(strings[0]);
            }
            return null;
        }
    }
}
