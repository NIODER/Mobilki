package ru.mirea.kozharinov.practice3.mireaproject.database.stories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Video.class}, version = 1)
public abstract class StoriesVideoDataBase extends RoomDatabase {

    public abstract VideoDAO videoDAO();
}
