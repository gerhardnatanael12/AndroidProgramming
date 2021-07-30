package com.example.mytask.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {task.class}, version = 2, exportSchema = false)
public abstract class TaskRoomDatabase extends RoomDatabase {
    public abstract TaskDao tasksDao();

    private static volatile TaskRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TaskRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, "tasks_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more tasks, just add them.
                TaskDao dao = INSTANCE.tasksDao();
                dao.deleteAll();

                task tasks = new task("APSI", "01-04-2020", "Membuat DFD");
                dao.insert(tasks);

                tasks = new task("PPL", "04-02-2023", "Membuat IOT");
                dao.insert(tasks);

                tasks = new task("AndroidProgramming", "04-01-2000", "Membuat sistem berbasis Android");
                dao.insert(tasks);

                tasks = new task("PKN", "03-01-2000", "Membuat makalah tentang sejarah Indonesia");
                dao.insert(tasks);

                tasks = new task("MTK", "31-08-2001", "Membuat program Citra Digital");
                dao.insert(tasks);

                tasks = new task("OOP", "23-03-2012", "Membuat program ATM");
                dao.insert(tasks);
            });
        }
    };
}
