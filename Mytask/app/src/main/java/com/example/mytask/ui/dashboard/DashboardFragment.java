package com.example.mytask.ui.dashboard;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytask.DetailTaskActivity;
import com.example.mytask.EditTaskActivity;
import com.example.mytask.NewTaskActivity;
import com.example.mytask.R;
import com.example.mytask.databinding.FragmentDashboardBinding;
import com.example.mytask.ui.home.HomeFragment;
import com.example.mytask.ui.home.TaskListAdapter;
import com.example.mytask.ui.home.TaskViewModel;
import com.example.mytask.ui.home.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DashboardFragment extends Fragment implements TaskListAdapter.OnNoteListener {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    private TaskViewModel mTasksViewModel;
    private TaskListAdapter adapter;
    private List<task> datas;
    private DetailTaskActivity detail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new TaskListAdapter(new TaskListAdapter.TasksDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTasksViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(TaskViewModel.class);
        mTasksViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            // Update the cached copy of the words in the adapter.
            datas = tasks;
            adapter.submitList(tasks);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            task tasks = new task(
                    data.getStringExtra("name"),
                    data.getStringExtra("deadline"),
                    data.getStringExtra("description")
            );
            mTasksViewModel.insert(tasks);
        }
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this.getContext(), DetailTaskActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("index", String.valueOf(position));
        bundle.putString("name", datas.get(position).getName());
        bundle.putString("deadline", datas.get(position).getDeadline());
        bundle.putString("description", datas.get(position).getDescription());
        intent.putExtras(bundle);

        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.task_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_button) {
            SearchView searchView = (SearchView) item.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getResults(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    getResults(newText);
                    return true;
                }

                private void getResults(String newText) {
                    String queryText = "%" + newText + "%";
                    mTasksViewModel.getFilteredTasks(queryText).observe(getViewLifecycleOwner(), tasks -> {
                        if (tasks == null) return;
                        datas = tasks;
                        adapter.submitList(tasks);
                    });
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

}