package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.IsConnected;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    private boolean isConnected() {
        IsConnected connected = new IsConnected();
        return connected.isConnected(getApplicationContext());
    }

    @Override
    protected void onResume() {
        boolean internet = isConnected();
        if (!internet) {
            Intent intent = new Intent(SettingsActivity.this, ResponseActivity.class);
            startActivity(intent);
        }
        super.onResume();
    }
}