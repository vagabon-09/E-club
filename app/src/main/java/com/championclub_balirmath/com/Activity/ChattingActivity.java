package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.databinding.ActivityChattingBinding;

public class ChattingActivity extends AppCompatActivity {
    ActivityChattingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Removing top status bar
        clickButton(); //all clickable button buttons
    }

    private void clickButton() {
        binding.arrowBackId.setOnClickListener(v -> { // When click on back button in chatting activity
//            Toast.makeText(this, "Clickable", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}