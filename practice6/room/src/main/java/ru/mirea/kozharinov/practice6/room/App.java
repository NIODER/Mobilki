package ru.mirea.kozharinov.practice6.room;

import android.app.Application;

import androidx.room.Room;

import ru.mirea.kozharinov.practice6.room.db.AppDataBase;

public class App extends Application {

    private static App instance;
    private AppDataBase dataBase;

    @Override
    public void onCreate() {
        instance = this;
        dataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
                .allowMainThreadQueries()
                .build();
        super.onCreate();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDataBase() {
        return dataBase;
    }
}
