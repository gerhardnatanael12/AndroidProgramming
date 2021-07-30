package com.example.usingintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    public void onClick(View view) {
        Intent data = new Intent();
        EditText username = (EditText) findViewById(R.id.nama);
        data.setData(Uri.parse(username.getText().toString()));
        setResult(RESULT_OK,data);
        finish();
    }
}