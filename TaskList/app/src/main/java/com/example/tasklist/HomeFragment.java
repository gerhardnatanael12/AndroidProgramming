package com.example.tasklist;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }

    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listTask[] myListData = new listTask[]{
                new listTask("Recycle View", "04/04/2021"),
                new listTask("MindMap", "04/04/2021"),
                new listTask("Statistika ", "07/04/2021"),
                new listTask("AI", "08/04/2021"),
                new listTask("Arduino UNO", "10/04/2021"),
                new listTask("DB", "11/04/2021"),
                new listTask("Apsi", "13/04/2021"),
                new listTask("DB", "11/04/2021"),
                new listTask("Apsi", "13/04/2021"),
                new listTask("DB", "11/04/2021"),
                new listTask("Apsi", "13/04/2021"),
        };
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.ListHome);
        adapter adapter = new adapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


    }
}
