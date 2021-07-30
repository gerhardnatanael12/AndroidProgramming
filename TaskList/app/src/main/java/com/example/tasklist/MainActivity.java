package com.example.tasklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager HomeFm = getSupportFragmentManager();
        HomeFm.beginTransaction()
                .replace(R.id.container, new HomeFragment())
                .commit();
    }
}