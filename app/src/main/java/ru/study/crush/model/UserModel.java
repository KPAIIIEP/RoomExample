package ru.study.crush.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


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
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        int id = (int) database.userDao().insert(user);
                        Position position = new Position();
                        position.setUserId(id);
                        position.setName("programmer");
                        database.positionDao().insert(position);
                    }
                });
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

    public void update(int id, User user, CompleteCallback callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(new Runnable() {
            public void run() {
                User userDB = database.userDao().getById(id);
                userDB.setName(user.getName());
                userDB.setAge(user.getAge());
                userDB.setLogged(user.isLogged());
                database.userDao().update(userDB);
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
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<User> users = database.userDao().getAll();
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