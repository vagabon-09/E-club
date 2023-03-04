package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.databinding.ActivityNotesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotesActivity extends AppCompatActivity {
    private ActivityNotesBinding binding;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

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
            dialog.findViewById(R.id.progressBarDoneBtn).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.doneBtnId).setVisibility(View.INVISIBLE);
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
        if (title.isEmpty()){
            Toast.makeText(this, "Enter note title", Toast.LENGTH_SHORT).show();
        }else {
            if (content.isEmpty()){
                Toast.makeText(this, "Enter note content.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Note Created.", Toast.LENGTH_SHORT).show();
                noteContent.setText("");
                noteTitle.setText("");
                dialog.dismiss();
            }
        }


    }
}