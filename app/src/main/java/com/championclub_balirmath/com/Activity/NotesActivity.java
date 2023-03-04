package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.championclub_balirmath.com.Adapter.NoteAdapter;
import com.championclub_balirmath.com.Model.BalanceHistoryModal;
import com.championclub_balirmath.com.Model.NoteModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.databinding.ActivityNotesBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotesActivity extends AppCompatActivity {
    private ActivityNotesBinding binding;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /* all firebase operation is done in firebaseDb() function */
        firebaseDb();

        /*
            When someone clicked in any button we will perform some action ,
            all action required action is in buttonCLicked() function
         */

        buttonClicked();
        /* Setting all recyclerview operation in recView() function */
        recView();
    }

    private void recView() {
        FirebaseRecyclerOptions<NoteModel> options
                = new FirebaseRecyclerOptions.Builder<NoteModel>()
                .setQuery(reference.child("Notes"), NoteModel.class)
                .build();
        binding.notesRecViewId.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(options);
        binding.notesRecViewId.setAdapter(adapter);

    }

    /* Firebase all operation is done here*/
    private void firebaseDb() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    /*Doing all button clicked operation*/
    private void buttonClicked() {
        // When clicked on back button in notes page
        binding.noteBackBtn.setOnClickListener(v -> finish());
        // When clicked on add note button
        binding.addNoteBtn.setOnClickListener(v -> createNoteDialog());
    }

    /*Creating note dialog*/
    private void createNoteDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.singel_note_create_pop);
        dialog.setCanceledOnTouchOutside(false);
        setAction(dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
    }

    private void setAction(Dialog dialog) {
        //When clicking on done button
        dialog.findViewById(R.id.doneBtnId).setOnClickListener(v -> {
            /* Sending data to database */
            sendData(dialog);
        });

        //Cancel button
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void sendData(Dialog dialog) {
        EditText noteTitle = dialog.findViewById(R.id.noteTItleInputId);
        EditText noteContent = dialog.findViewById(R.id.noteContentId);
        String title = noteTitle.getText().toString();
        String content = noteContent.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Enter note title", Toast.LENGTH_SHORT).show();
        } else {
            if (content.isEmpty()) {
                Toast.makeText(this, "Enter note content.", Toast.LENGTH_SHORT).show();
            } else {
                dialog.findViewById(R.id.progressBarDoneBtn).setVisibility(View.VISIBLE);
                dialog.findViewById(R.id.doneBtnId).setVisibility(View.INVISIBLE);
                NoteModel noteModel = new NoteModel(mAuth.getUid(), title, content);
                reference.child("Notes").push().setValue(noteModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        noteContent.setText("");
                        noteTitle.setText("");
                        dialog.findViewById(R.id.progressBarDoneBtn).setVisibility(View.GONE);
                        dialog.findViewById(R.id.doneBtnId).setVisibility(View.VISIBLE);
                    }
                });

            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}