package com.example.mytask.ui.home;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks_table")
public class task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String deadline;

    public task(String name, String deadline, String description) {
        this.name = name;
        this.deadline = deadline;
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
