package com.example.mytask.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mytask.R;

public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView mName;
    public final TextView mDescription;
    public final TextView mDeadline;
    TaskListAdapter.OnNoteListener onNoteListener;

    private TaskViewHolder(View view, TaskListAdapter.OnNoteListener onNoteListener) {
        super(view);
        mName = view.findViewById(R.id.taskname);
        mDescription = view.findViewById(R.id.description);
        mDeadline = view.findViewById(R.id.deadline);
        this.onNoteListener = onNoteListener;

        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onNoteListener.onNoteClick(getAdapterPosition());
    }

    public void bind(String name, String description, String deadline) {
        mName.setText(name);
        mDescription.setText(description);
        mDeadline.setText(deadline);
    }

    static TaskViewHolder create(ViewGroup parent, TaskListAdapter.OnNoteListener onNoteListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder, parent, false);
        return new TaskViewHolder(view, onNoteListener);
    }
}
