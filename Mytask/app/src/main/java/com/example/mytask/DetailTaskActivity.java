package com.example.mytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mytask.ui.home.TaskListAdapter;
import com.example.mytask.ui.home.TaskViewHolder;
import com.example.mytask.ui.home.TaskViewModel;
import com.example.mytask.ui.home.task;

import java.io.DataInput;
import java.util.List;

public class DetailTaskActivity extends AppCompatActivity {

    TextView s1,s2,s3;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    private List<task> datas;

    String data1,data2,data3;
    private TaskViewModel mTugasViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        s1 = findViewById(R.id.taskName);
        s3 = findViewById(R.id.deadlineTask);
        s2 = findViewById(R.id.descriptionTask);

        getData();
        setData();

    }

    private void getData(){
        data1 = getIntent().getStringExtra("name");
        data2 = getIntent().getStringExtra("description");
        data3 = getIntent().getStringExtra("deadline");
    }

    private void setData(){
        s1.setText(data1);
        s2.setText(data2);
        s3.setText(data3);
    }

}