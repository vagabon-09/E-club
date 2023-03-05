package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.championclub_balirmath.com.Adapter.MembersAdapter;
import com.championclub_balirmath.com.Model.BalanceHistoryModal;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.databinding.ActivityMembersBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MembersActivity extends AppCompatActivity {
    private ActivityMembersBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private MembersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMembersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*In this function we are going to perform all database operation work*/
        dataBase();
        /*In this function setRecyclerView() we are going to set all the operation of recyclerview*/
        setRecyclerView();
        /*all action when someone click any button in member activity onClick()*/
        onClick();
    }

    private void onClick() {
        binding.memberPageBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void dataBase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void setRecyclerView() {
        FirebaseRecyclerOptions<ProfileModel> options
                = new FirebaseRecyclerOptions.Builder<ProfileModel>()
                .setQuery(reference.child("Users"), ProfileModel.class)
                .build();

        adapter = new MembersAdapter(options);
        binding.memberRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.memberRecView.setAdapter(adapter);

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