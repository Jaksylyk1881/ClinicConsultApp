package com.example.clinicconsultapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private static UsersDatabase database;
    private LiveData<List<User>> users;


    public MainViewModel(Application application) {
        super(application);
        database = UsersDatabase.getInstance(getApplication());
        users = database.userDao().getAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public User getUserByEmail(String email){
        try {
            return new GetUserTask().execute(email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllUsers(){
        new DeleteUsersTask().execute();
    }

    public void insertUser(User user){
        new InsertUserTask().execute(user);
    }

    private static class InsertUserTask extends AsyncTask<User, Void, Void>{

        @Override
        protected Void doInBackground(User... users) {
            if (users!= null&&users.length>0){
                database.userDao().insertUser(users[0]);
            }
            return null;
        }
    }

    private static class DeleteUsersTask extends  AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            database.userDao().deleteAllUsers();
            return null;
        }
    }


    private static class GetUserTask extends AsyncTask<String, Void, User>{

        @Override
        protected User doInBackground(String... strings) {
            if (strings!= null&&strings.length>0){
                return database.userDao().getUserByEmail(strings[0]);
            }
            return null;
        }
    }
}
