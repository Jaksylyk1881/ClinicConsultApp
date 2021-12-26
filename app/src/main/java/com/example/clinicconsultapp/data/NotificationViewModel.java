package com.example.clinicconsultapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationViewModel extends AndroidViewModel {
    private static UsersDatabase database;
    private LiveData<List<Notification>> notifications;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        database = UsersDatabase.getInstance(getApplication());
        notifications = database.notificationDao().getAllNotifications();
    }

    public LiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public LiveData<List<Notification>> GetNotificationsByEmail(String email){
        try {
            return new GetNotificationTaskByEmail().execute(email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void InsertNotification(Notification notification){
        new InsertNotificationTask().execute(notification);
    }
    public void DeleteNotification(Notification notification){
        new DeleteNotificationTask().execute(notification);
    }
    public void DeleteAllNotifications(){
        new DeleteAllNotificationsTask().execute();
    }
    private static class DeleteAllNotificationsTask extends  AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            database.notificationDao().deleteAllNotifications();
            return null;
        }
    }
    private static class DeleteNotificationTask extends AsyncTask<Notification,Void,Void>{


        @Override
        protected Void doInBackground(Notification... notifications) {
            if (notifications!=null&&notifications.length>0){
                database.notificationDao().deleteNotification(notifications[0]);
            }
            return null;
        }
    }
    private static class InsertNotificationTask extends AsyncTask<Notification,Void,Void>{

        @Override
        protected Void doInBackground(Notification... notifications) {
            if (notifications!=null&&notifications.length>0){
                database.notificationDao().insertNotification(notifications[0]);
            }
            return null;
        }
    }
    private static class GetNotificationTaskByEmail extends AsyncTask<String,Void,LiveData<List<Notification>>>{

        @Override
        protected LiveData<List<Notification>> doInBackground(String... strings) {
            if (strings!= null&&strings.length>0){
                database.notificationDao().getAllNotificationsByEmail(strings[0]);
            }
            return null;
        }
    }
}
