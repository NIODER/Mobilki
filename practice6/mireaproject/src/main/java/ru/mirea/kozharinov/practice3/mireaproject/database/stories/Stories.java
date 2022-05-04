package ru.mirea.kozharinov.practice3.mireaproject.database.stories;

import android.app.Application;

import androidx.room.Room;

public class Stories extends Application {

    private static Stories instance;
    private StoriesVideoDataBase dataBase;

    @Override
    public void onCreate() {
        instance = this;
        dataBase = Room.databaseBuilder(this, StoriesVideoDataBase.class, "database")
                .allowMainThreadQueries()
                .build();
        super.onCreate();
    }

    public static Stories getInstance() {
        return instance;
    }

    public StoriesVideoDataBase getDataBase() {
        return dataBase;
    }
}
