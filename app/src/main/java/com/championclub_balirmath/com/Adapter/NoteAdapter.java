package com.championclub_balirmath.com.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Model.NoteModel;
import com.championclub_balirmath.com.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class NoteAdapter extends FirebaseRecyclerAdapter<NoteModel, NoteAdapter.myViewHolder> {


    public NoteAdapter(@NonNull FirebaseRecyclerOptions<NoteModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteAdapter.myViewHolder holder, int position, @NonNull NoteModel model) {
        holder.noteTitle.setText(model.getNoteTitle());
    }

    @NonNull
    @Override
    public NoteAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note_layout, parent, false);
        return new myViewHolder(view);
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle, noteCreator;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.single_note_titleId);
            noteCreator = itemView.findViewById(R.id.single_note_creator);
        }
    }
}
