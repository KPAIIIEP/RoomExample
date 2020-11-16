package ru.study.crush.model;

import android.content.Context;

import java.util.List;

public class UserModel {
    private static UserDatabase database;

    public UserModel(Context context) {
        database = UserDatabase.getInstance(context);
    }

    public void add(User user) {
        new Thread(new Runnable() {
            public void run() {
                database.userDao().insert(user);
                List<User> users = database.userDao().getAll();
            }
        }).start();
    }
}
