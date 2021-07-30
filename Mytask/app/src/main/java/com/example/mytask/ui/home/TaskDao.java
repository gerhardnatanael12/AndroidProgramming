package com.example.mytask.ui.home;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(task tasks);

    @Delete
    void delete(task tasks);

    @Update
    void update(task task);

    @Query("DELETE FROM tasks_table")
    void deleteAll();

    @Query("SELECT * FROM tasks_table ORDER BY substr (deadline, 7, 10) ASC, substr (deadline, 1, 2) ASC, substr (deadline, 4, 5) ASC")
    LiveData<List<task>> getSortedTasks();

    @Query("SELECT * FROM tasks_table WHERE name LIKE :searchQuery ORDER BY substr (deadline, 7, 10) ASC, substr (deadline, 1, 2) ASC, substr (deadline, 4, 5) ASC")
    LiveData<List<task>> getSearchSortedTasks(String searchQuery);

}
