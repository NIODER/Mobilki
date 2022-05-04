package ru.mirea.kozharinov.practice3.mireaproject.database.stories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VideoDAO {

    @Query("SELECT * FROM video")
    List<Video> getAll();
    @Query("SELECT * FROM video WHERE id = :id")
    Video getById(long id);
    @Query("SELECT * FROM video WHERE name = :name")
    Video getByName(String name);
    @Insert
    void insert(Video video);
    @Update
    void update(Video video);
    @Delete
    void delete(Video video);
}
