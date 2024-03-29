package com.championclub_balirmath.com.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.IsConnected;
import com.championclub_balirmath.com.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//Removing top bar
        binding.cardBackgroundId.setBackgroundResource(R.drawable.login_card_background_shape);// Adding custom card background in card view
        mAuth = FirebaseAuth.getInstance();
        binding.joinRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Clicked on joining request", Toast.LENGTH_SHORT).show();
            }
        });
        binding.loginBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseLogin();
            }
        });
    }

    private void FirebaseLogin() {
        String email = binding.LoginEmailId.getText().toString();
        String password = binding.loginPasswordId.getText().toString();
        if (!email.isEmpty()) {
            if (!password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please enter your password..", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter your email id...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isConnected() {
        IsConnected connected = new IsConnected();
        return connected.isConnected(getApplicationContext());
    }

    @Override
    protected void onResume() {
        boolean internet = isConnected();
        if (!internet) {
            Intent intent = new Intent(LoginActivity.this, ResponseActivity.class);
            startActivity(intent);
        }
        super.onResume();
    }
}