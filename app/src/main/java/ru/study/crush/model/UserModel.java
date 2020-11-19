package ru.study.crush.model;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.List;


public class UserModel {
    private static final String TAG = UserModel.class.getSimpleName();
    private static UserDatabase database;

    public UserModel(Context context) {
        database = UserDatabase.getInstance(context);
    }

    public interface LoadUserCallback {
        void onLoad(List<User> users);
    }

    public interface CompleteCallback {
        void onComplete();
    }

    public void add(User user, CompleteCallback callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(new Runnable() {
            public void run() {
                database.userDao().insert(user);
                try {
                    Thread.sleep(2000); // эмуляция задержки
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete();
                    }
                });
            }
        });
        thread.start();
    }

    public void delete(int id, CompleteCallback callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(new Runnable() {
            public void run() {
                database.userDao().delete(database.userDao().getById(id));
                try {
                    Thread.sleep(2000); // эмуляция задержки
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete();
                    }
                });
            }
        });
        thread.start();
    }

    public void getAll(LoadUserCallback callback) {
        HandlerThread handlerThread = new HandlerThread("DBThread");
        handlerThread.start();
        Handler handlerNew = new Handler(handlerThread.getLooper());
        handlerNew.post(new Runnable() {
            @Override
            public void run() {
                final List<User> users = database.userDao().getAll();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoad(users);
                    }
                });
            }
        });
    }

    public void deleteAll(CompleteCallback callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(new Runnable() {
            public void run() {
                database.userDao().deleteAll();
                try {
                    Thread.sleep(2000); // эмуляция задержки
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete();
                    }
                });
            }
        });
        thread.start();
    }
}