package ru.study.crush.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserModel {
    private static final String TAG = UserModel.class.getSimpleName();
    private static UserDatabase database;

    public UserModel(Context context) {
        database = UserDatabase.getInstance(context);
    }

    public interface CompleteCallback {
        void onComplete();
    }

    public void add(User user, CompleteCallback callback) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                database.userDao().insert(user);
            }
        });
        thread.start();
        try {
            thread.join();
            callback.onComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<User>> callable = new Callable<List<User>>() {
            @Override
            public List<User> call() {
                Log.i(TAG, Thread.currentThread().toString());
                return database.userDao().getAll();
            }
        };
        Future<List<User>> future = executor.submit(callable);
        // future.get() returns 2 or raises an exception if the thread dies, so safer
        try {
            users = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return users;
    }

    public void deleteAll(CompleteCallback callback) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                database.userDao().deleteAll();
            }
        });
        thread.start();
        try {
            thread.join();
            callback.onComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
