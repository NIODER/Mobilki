package ru.mirea.kozharinov.practice3.mireaproject.database.stories;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Video {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String uri;
}
