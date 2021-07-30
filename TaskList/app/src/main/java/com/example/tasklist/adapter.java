package com.example.tasklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {
    private listTask[] listdata;

    // RecyclerView recyclerView;
    public adapter(listTask[] listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final listTask myListTugas = listdata[position];
        holder.tv_taskNote.setText(listdata[position].getTaskNote());
        holder.tv_tanggalPengumpulan.setText(listdata[position].getTanggalPengumpulan());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + myListTugas.getTaskNote(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_taskNote;
        public TextView tv_tanggalPengumpulan;
        public RelativeLayout relativeLayout;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_taskNote = (TextView) itemView.findViewById(R.id.TaskNote);
            this.tv_tanggalPengumpulan = (TextView) itemView.findViewById(R.id.TanggalPengumpulan);
            cardView = (CardView) itemView.findViewById(R.id.TaskCard);
        }
    }
}
