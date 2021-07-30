package com.example.mytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mytask.ui.home.HomeFragment;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private EditText mName;
    private EditText mDescription;
    private TextView mDeadline;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.edit_task);

        Bundle bundle = getIntent().getExtras();

        mName = findViewById(R.id.judulTask);
        mDescription = findViewById(R.id.deskripsiTask);
        mDeadline = findViewById(R.id.deadlineTask);
        button = findViewById(R.id.btnUpdate);
//        button2 = findViewById(R.id.buttonSelesai);

        mName.setText(bundle.getString("name"));
        mDeadline.setText(bundle.getString("deadline"));
        mDescription.setText(bundle.getString("description"));

        if(getIntent().getExtras() != null) {
            mName.setText(bundle.getString("name"));
            mDescription.setText(bundle.getString("description"));
            mDeadline.setText(bundle.getString("deadline"));
        }

        mDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditTaskActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mDay = String.valueOf(dayOfMonth);
                String mMonth = String.valueOf(month + 1);

                if (Integer.parseInt(mMonth) <= 9) {
                    mMonth = "0" + mMonth;
                }

                if (Integer.parseInt(mDay) <= 9) {
                    mDay = "0" + mDay;
                }

                String deadline = mDay + "-" + mMonth + "-" + year;
                mDeadline.setText(deadline);
            }
        };

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent(EditTaskActivity.this, HomeFragment.class);

                replyIntent.putExtra("action", "update");
                replyIntent.putExtra("index", bundle.getString("index"));
                replyIntent.putExtra("name", mName.getText().toString());
                replyIntent.putExtra("deadline", mDeadline.getText().toString());
                replyIntent.putExtra("description", mDescription.getText().toString());

                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });

        findViewById(R.id.buttonSelesai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                replyIntent.putExtra("action", "delete");
                replyIntent.putExtra("index", bundle.getString("index"));

                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });
    }

}