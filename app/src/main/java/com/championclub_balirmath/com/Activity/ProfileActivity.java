package com.championclub_balirmath.com.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        binding.logoutCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        fetchData();
    }


    private void fetchData() {
        Intent intent = getIntent();
        /*If we came form member activity class we will set user id if we came directly using home page we will get userId*/
        String userId = intent.getStringExtra("uId");
        if (userId == null) {
            userId = mAuth.getUid();
        }
//        Log.d("UserId", "fetchData: " + userId);

        reference.child("Users").child(Objects.requireNonNull(userId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileModel model = snapshot.getValue(ProfileModel.class);
                assert model != null;
                String name;
                String email;
                name = model.getUserName();
                email = model.getUserEmail();
                binding.ProfileUserNameId.setText(name);
                binding.ProfileEmailId.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}