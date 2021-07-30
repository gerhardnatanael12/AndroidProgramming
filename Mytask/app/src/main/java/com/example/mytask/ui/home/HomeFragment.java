package com.example.mytask.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytask.DetailTaskActivity;
import com.example.mytask.EditTaskActivity;
import com.example.mytask.NewTaskActivity;
import com.example.mytask.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment implements TaskListAdapter.OnNoteListener {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    private TaskViewModel mTasksViewModel;
    private TaskListAdapter adapter;
    private List<task> datas;
    private DetailTaskActivity detail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
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
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener( View -> {
            Intent intent = new Intent(getContext(), NewTaskActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
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
            Toast.makeText(getContext(), R.string.data_saved, Toast.LENGTH_LONG).show();
        }

        if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getStringExtra("action").equals("delete")) {
                mTasksViewModel.delete(datas.get(Integer.parseInt(data.getStringExtra("index"))));
                Toast.makeText(getContext(), "Task deleted!", Toast.LENGTH_LONG).show();
            }

            if (data.getStringExtra("action").equals("update")) {
                task old_task = datas.get(Integer.parseInt(data.getStringExtra("index")));

                task updated_task = new task(
                        data.getStringExtra("name"),
                        data.getStringExtra("deadline"),
                        data.getStringExtra("description")
                );
                updated_task.setId(datas.get(Integer.parseInt(data.getStringExtra("index"))).getId());

                if (
                        old_task.getId() != updated_task.getId() ||
                                !old_task.getName().equals(updated_task.getName()) ||
                                !old_task.getDeadline().equals(updated_task.getDeadline()) ||
                                !old_task.getDescription().equals(updated_task.getDescription())
                ) {
                    Toast.makeText(getContext(), "Tasks updated!", Toast.LENGTH_LONG).show();
                    mTasksViewModel.update(updated_task);
                }
                this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        }
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this.getContext(), EditTaskActivity.class);
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

        if (id == R.id.delete_all_button) {
            mTasksViewModel.deleteAll();
            Toast.makeText(getContext(), "All tasks has been deleted!", Toast.LENGTH_LONG).show();
        }

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