package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.championclub_balirmath.com.Adapter.MembersAdapter;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.ReusableCode.IsConnected;
import com.championclub_balirmath.com.databinding.ActivityMembersBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MembersActivity extends AppCompatActivity {
    private ActivityMembersBinding binding;
    private DatabaseReference reference;
    private MembersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMembersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.memberShimmerId.setVisibility(View.GONE);
        binding.memberRecView.setVisibility(View.VISIBLE);
        /*In this function we are going to perform all database operation work*/
        dataBase();
        /*In this function setRecyclerView() we are going to set all the operation of recyclerview*/
        setRecyclerView();
        /*all action when someone click any button in member activity onClick()*/
        onClick();
    }

    private void onClick() {
        binding.memberPageBack.setOnClickListener(v -> finish());
    }

    private void dataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
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

    private boolean isConnected() {
        IsConnected connected = new IsConnected();
        return connected.isConnected(getApplicationContext());
    }

    @Override
    protected void onResume() {
        boolean internet = isConnected();
        if (!internet) {
            Intent intent = new Intent(MembersActivity.this, ResponseActivity.class);
            startActivity(intent);
        }
        super.onResume();
    }
}