package com.example.mytask.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository mRepository;
    private final LiveData<List<task>> mAllTasks;

    public TaskViewModel (Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }

    public LiveData<List<task>> getAllTasks() { return mAllTasks; }

    public LiveData<List<task>> getFilteredTasks(String searchQuery) { return mRepository.getFilteredTasks(searchQuery); }

    public void insert(task tasks) { mRepository.insert(tasks); }

    public void delete(task tasks) { mRepository.delete(tasks); }

    public void update(task tasks) { mRepository.update(tasks); }

    public void deleteAll() { mRepository.deleteAll(); }
}
