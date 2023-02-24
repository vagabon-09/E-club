package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.Receiver.AlarmReceiver;
import com.championclub_balirmath.com.databinding.ActivityKnowMoreBinding;

public class KnowMoreActivity extends AppCompatActivity {
    ActivityKnowMoreBinding binding;
    static final int ALARM_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKnowMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Getting data from shared preferences
        gettingData();
        //when click back button
        binding.eventBackId.setOnClickListener(view -> finish());
        //When click button set action
        setCLick();

    }

    private void gettingData() {
        SharedPreferences sharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("remainder", false);
        if (check) {
            binding.eventReminderBtnId2.setVisibility(View.VISIBLE);
            binding.eventReminderBtnId.setVisibility(View.GONE);
        } else {
            binding.eventReminderBtnId2.setVisibility(View.GONE);
            binding.eventReminderBtnId.setVisibility(View.VISIBLE);
        }
    }

    private void setCLick() {
        binding.eventReminderBtnId.setOnClickListener(v -> { // When click on event Reminder Btn
            binding.eventReminderBtnId2.setVisibility(View.VISIBLE);
            binding.eventReminderBtnId.setVisibility(View.GONE);
            startRemainder();
        });
        binding.eventReminderBtnId2.setOnClickListener(v -> { // When click on Reminder Btn 2
            binding.eventReminderBtnId.setVisibility(View.VISIBLE);
            binding.eventReminderBtnId2.setVisibility(View.GONE);
            stopRemainder();
        });
    }

    private void stopRemainder() {
        SharedPreferences sharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remainder", false);
        editor.apply();
    }

    private void startRemainder() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bell_1);
        mediaPlayer.start();
        SharedPreferences sharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remainder", true);
        editor.apply();
        //Setting Alarm
        long alarm_time = System.currentTimeMillis() + (10 * 1000);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(KnowMoreActivity.this, AlarmReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(KnowMoreActivity.this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, pendingIntent);
    }
}