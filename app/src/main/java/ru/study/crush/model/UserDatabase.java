package ru.study.crush.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase database;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if (database == null) {
                database = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, "database").build();
        }
        return database;
    }
}
