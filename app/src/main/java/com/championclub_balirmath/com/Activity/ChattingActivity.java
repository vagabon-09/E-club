package com.championclub_balirmath.com.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;


import com.championclub_balirmath.com.Adapter.GroupChatAdapter;
import com.championclub_balirmath.com.Model.GroupChatModel;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.databinding.ActivityChattingBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChattingActivity extends AppCompatActivity {
    ActivityChattingBinding binding;
    private FirebaseDatabase database;
    private GroupChatAdapter adapter;
    private String userName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Removing top status bar
        clickButton(); //all clickable button buttons
        settingRecView(); // all recyclerViewWork is done in this function
    }

    private void settingRecView() {
        database = FirebaseDatabase.getInstance(); // Fetching database instance
        final ArrayList<GroupChatModel> modelArrayList = new ArrayList<>();// Fetching all model array list
        adapter = new GroupChatAdapter(modelArrayList, this);//accessing adapter
        binding.messageChattingRecView.setAdapter(adapter);// Setting adapter in recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);// Declaring layout manager
        binding.messageChattingRecView.setLayoutManager(layoutManager);// Setting layout in recyclerview
        binding.messageChattingRecView.scrollToPosition(adapter.getItemCount() - 1);
        getValue(modelArrayList, adapter);// This function is used to fetch data form database
    }

    private void getValue(ArrayList<GroupChatModel> modelArrayList, GroupChatAdapter adapter) {
        database.getReference().child("club_chat").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelArrayList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    GroupChatModel model = data.getValue(GroupChatModel.class);
                    modelArrayList.add(model);
                    assert model != null;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clickButton() {
        binding.arrowBackId.setOnClickListener(v -> { // When click on back button in chatting activity
//            Toast.makeText(this, "Clickable", Toast.LENGTH_SHORT).show();
            finish();
        });
        binding.messageSendBtn.setOnClickListener(v -> { // send all data to database by clicking this button
            sendData();
        });
    }

    private void sendData() {

        String senderId = FirebaseAuth.getInstance().getUid();// Getting user id using firebase auth
        String message = binding.messageEditId.getText().toString();//getting text from edit text
        binding.messageEditId.setText("");//Setting edit text empty
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();//getting instance
        DatabaseReference databaseReference = database1.getReference();// getting database reference

        assert senderId != null;
        databaseReference.child("Users").child(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileModel model = snapshot.getValue(ProfileModel.class);
                assert model != null;
                userName = model.getUserName();
                if (!userName.isEmpty()) {//until we get user name we are not perform any work
                    GroupChatModel model1 = new GroupChatModel(message, senderId, userName);//sending all data to model class
                    model1.setTimestamp(new Date().getTime());// setting data&time
                    DatabaseReference reference = database.getReference();// getting database reference


                    database.getReference().child("club_chat").push().setValue(model1).addOnSuccessListener(unused -> { //sending value to firebase
                        binding.messageChattingRecView.scrollToPosition(adapter.getItemCount() - 1);// automatically scroll to new message
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChattingActivity.this, "Something is wrong try again la.", Toast.LENGTH_SHORT).show();

            }
        });


    }


}


