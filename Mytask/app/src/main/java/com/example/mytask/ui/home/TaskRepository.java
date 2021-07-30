package com.example.mytask.ui.home;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDao mTasksDao;
    private LiveData<List<task>> mAllTasks;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTasksDao = db.tasksDao();
        mAllTasks = mTasksDao.getSortedTasks();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<task>> getAllTasks() {
        return mAllTasks;
    }

    LiveData<List<task>> getFilteredTasks(String searchQuery) {
        return mTasksDao.getSearchSortedTasks(searchQuery);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(task tasks) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTasksDao.insert(tasks);
        });
    }

    void delete(task tasks) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTasksDao.delete(tasks);
        });
    }

    void update(task tasks) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTasksDao.update(tasks);
        });
    }

    void deleteAll() {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTasksDao.deleteAll();
        });
    }

}
