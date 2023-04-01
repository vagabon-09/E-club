package com.championclub_balirmath.com.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private final int REQUEST_CODE = 100;
    private final int PROFILE_REQUEST_CODE = 101;
    private Uri filePath;
    private FirebaseStorage storage;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*Getting Firebase storage instance*/
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
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
        //Change profile background
        backgroundImage.setOnClickListener(v -> {
            Intent backgroundIntent = new Intent();
            backgroundIntent.setAction(Intent.ACTION_GET_CONTENT);
            backgroundIntent.setType("image/*");
            startActivityForResult(backgroundIntent, REQUEST_CODE);
            bottomSheet.dismiss();

        });
        LinearLayout profileImage = bottomSheet.findViewById(R.id.changeProfileImageId);
        profileImage.setOnClickListener(v -> {
            Intent profileIntent = new Intent();
            profileIntent.setAction(Intent.ACTION_GET_CONTENT);
            profileIntent.setType("image/*");
            startActivityForResult(profileIntent, PROFILE_REQUEST_CODE);
            bottomSheet.dismiss();
        });
        bottomSheet.show();
        bottomSheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        bottomSheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetStyle;
        bottomSheet.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null && resultCode == RESULT_OK && data.getData() != null) {
            filePath = data.getData();
            binding.backgroundImage.setImageURI(filePath);
            StorageReference storageReference = storage.getReference().child("cover_photo").child(Objects.requireNonNull(mAuth.getUid()));
            storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(mAuth.getUid()).child("coverPhoto").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProfileActivity.this, "Cover Photo changed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });

        } else if (requestCode == PROFILE_REQUEST_CODE && data != null && resultCode == RESULT_OK && data.getData() != null) {
            Uri profilePath = data.getData();
            binding.profileImageId.setImageURI(profilePath);
            StorageReference storageReference = storage.getReference().child("profile_pic").child(Objects.requireNonNull(mAuth.getUid()));
            storageReference.putFile(profilePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(mAuth.getUid()).child("profilePhoto").setValue(uri.toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProfileActivity.this, "Profile Photo is changed.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                }
            });
        }
    }

    private void check(String userId) {
        if (!Objects.equals(mAuth.getUid(), userId)) {
            binding.changePhotoBackId.setVisibility(View.GONE);
            binding.addUserPhotoId.setVisibility(View.GONE);
            binding.profileLogOutBtnId.setVisibility(View.GONE);
            binding.profilePrivacyBtnId.setVisibility(View.GONE);
            binding.profileSettingsBtnId.setVisibility(View.GONE);
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
                Picasso.get().load(model.getCoverPhoto()).into(binding.backgroundImage);
                Picasso.get().load(model.getProfilePhoto()).into(binding.profileImageId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}