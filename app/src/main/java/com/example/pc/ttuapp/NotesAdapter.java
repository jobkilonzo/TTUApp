package com.example.pc.ttuapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    public class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public NotesViewHolder(@NonNull TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }
    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
