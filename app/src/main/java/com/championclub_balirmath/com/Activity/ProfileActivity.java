package com.championclub_balirmath.com.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.R;
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
        /*All database related work is done in database() function*/
        database();
        /*All button action is done in onclick function*/
        onClick();
        /*Fetching all data from database*/
        fetchData();
        /*Set button click*/
        clicked();
    }

    private void clicked() {
        //When clicked on buttons
        binding.addUserPhotoId.setOnClickListener(v -> {
            showBottomSheet();
        });
    }

    private void showBottomSheet() {
        final Dialog bottomSheet = new Dialog(this);
        bottomSheet.setContentView(R.layout.bottom_sheet);
        LinearLayout backgroundImage = bottomSheet.findViewById(R.id.backgroundImageId);
        backgroundImage.setOnClickListener(v -> {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            bottomSheet.dismiss();
        });
        LinearLayout profileImage = bottomSheet.findViewById(R.id.changeProfileImageId);
        profileImage.setOnClickListener(v -> {
            Toast.makeText(this, "Clicked Profile Image", Toast.LENGTH_SHORT).show();
            bottomSheet.dismiss();
        });
        bottomSheet.show();
        bottomSheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetStyle;
        bottomSheet.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void check(String userId) {
        if (!Objects.equals(mAuth.getUid(), userId)) {
            binding.changePhotoBackId.setVisibility(View.GONE);
            binding.addUserPhotoId.setVisibility(View.GONE);
        }
    }

    private void database() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void onClick() {
        // When click on logout btn
        binding.profileLogOutBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //When click on back button
        binding.profileBackId.setOnClickListener(v -> {
            finish();
        });

        //When click on wallet button
        binding.profileWalletBtnId.setOnClickListener(v -> {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
        });
    }


    private void fetchData() {
        Intent intent = getIntent();
        /*If we came form member activity class we will set user id if we came directly using home page we will get userId*/
        String userId = intent.getStringExtra("uId");
        if (userId == null) {
            userId = mAuth.getUid();
        }
        /*Checking user coming from which page*/
        check(userId);
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