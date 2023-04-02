package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.IsConnected;
import com.championclub_balirmath.com.databinding.ActivityResponseBinding;

import java.util.zip.Inflater;

public class ResponseActivity extends AppCompatActivity {
    ActivityResponseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityResponseBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.recheckInterNetId.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        IsConnected connected = new IsConnected();
        if (connected.isConnected(getApplicationContext())) {
            finish();
        }
        super.onResume();
    }
}