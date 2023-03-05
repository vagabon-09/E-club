package com.championclub_balirmath.com.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Model.NoteModel;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.DateTime;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NoteAdapter extends FirebaseRecyclerAdapter<NoteModel, NoteAdapter.myViewHolder> {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference reference = database.getReference();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final String uId = auth.getUid();


    public NoteAdapter(@NonNull FirebaseRecyclerOptions<NoteModel> options) {
        super(options);
    }


    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onBindViewHolder(@NonNull NoteAdapter.myViewHolder holder, int position, @NonNull NoteModel model) {
//        Log.d("Position", "onBindViewHolder: " + position);
        DateTime time = new DateTime();
        holder.createdTime.setText("Date: "+time.customDT(model.getTime(), "dd-MM-YY"));
        holder.noteTitle.setText(model.getNoteTitle());
        holder.expandableNoteView.setText(model.getNoteContent());
        reference.child("Users").child(model.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileModel m = snapshot.getValue(ProfileModel.class);
                assert m != null;
                holder.noteCreator.setText("~ by " + m.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", "onCancelled: " + error.getDetails());
            }
        });

        if (uId.equals(model.getUserId())) {
            holder.deleteIcon.setVisibility(View.VISIBLE);
            holder.deleteCircle.setVisibility(View.VISIBLE);
        }
        holder.deleteIcon.setOnClickListener(v -> {
            Log.d("IdPosition", "onBindViewHolder: " + getRef(position).getKey());
            reference.child("Notes").child(Objects.requireNonNull(getRef(position).getKey())).removeValue();
        });
        holder.cardView.setOnClickListener(v -> {
//            Dialog dialog = new Dialog(holder.cardView.getContext());
//            dialog.setContentView(R.layout.singel_note_create_pop);
//            setContent(model, dialog);
//            dialog.show();
            if (holder.expandableNoteView.getVisibility() == View.VISIBLE) {
                holder.expandableNoteView.setVisibility(View.GONE);
                holder.noteCreator.setVisibility(View.GONE);
            } else {
                holder.expandableNoteView.setVisibility(View.VISIBLE);
                holder.noteCreator.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setContent(NoteModel model, Dialog dialog) {
        TextView sTitle = dialog.findViewById(R.id.noteTItleInputId);
        TextView sContent = dialog.findViewById(R.id.noteContentId);
        sTitle.setText(model.getNoteTitle());
        sContent.setText(model.getNoteContent());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }


    @NonNull
    @Override
    public NoteAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note_layout, parent, false);
        return new myViewHolder(view);
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle, noteCreator, expandableNoteView, createdTime;
        View deleteCircle;
        ImageView deleteIcon;
        CardView cardView;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.single_note_titleId);
            noteCreator = itemView.findViewById(R.id.single_note_creator);
            deleteCircle = itemView.findViewById(R.id.SingleNotedeleteBtnId);
            deleteIcon = itemView.findViewById(R.id.deleteIconBtnId);
            cardView = itemView.findViewById(R.id.NoteSeeBtnId);
            expandableNoteView = itemView.findViewById(R.id.expandableContentId);
            createdTime = itemView.findViewById(R.id.notice_date);
        }
    }


}
