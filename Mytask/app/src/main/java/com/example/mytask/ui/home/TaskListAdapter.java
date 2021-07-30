package com.example.mytask.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.mytask.MainActivity;

import java.util.List;

public class TaskListAdapter extends ListAdapter<task, TaskViewHolder> {
    private OnNoteListener onNoteListener;
    private List<task> datas;
    private MainActivity activity;
    private List<TaskViewModel> todoList;

    public TaskListAdapter(@NonNull DiffUtil.ItemCallback<task> diffCallback, OnNoteListener onNoteListener) {
        super(diffCallback);
        this.onNoteListener = onNoteListener;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        task current = getItem(position);
        holder.bind(current.getName(), current.getDescription(), current.getDeadline());
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TaskViewHolder.create(parent, onNoteListener);
    }

    public static class TasksDiff extends DiffUtil.ItemCallback<task> {
        @Override
        public boolean areItemsTheSame(@NonNull task oldItem, @NonNull task newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull task oldItem, @NonNull task newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    public Context getContext() {
        return activity;
    }


}
