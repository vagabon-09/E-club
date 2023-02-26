package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.Receiver.AlarmReceiver;
import com.championclub_balirmath.com.ReusableCode.DateTime;
import com.championclub_balirmath.com.databinding.ActivityKnowMoreBinding;

public class KnowMoreActivity extends AppCompatActivity {
    private ActivityKnowMoreBinding binding;
    static final int ALARM_REQUEST_CODE = 100;
    private AlarmManager alarmManager;
    private DateTime dateTime;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKnowMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dateTime = new DateTime();
        // Getting data from shared preferences
        gettingData();
        //when click back button
        binding.eventBackId.setOnClickListener(view -> finish());
        //When click button set action
        setCLick();
        //Setting values in appropriate place
        setDate();

    }

    @SuppressLint("SetTextI18n")
    private void setDate() {
        intent = getIntent();
        String eventName = intent.getStringExtra("event_name"); // Getting date from adapter class
        String eventOrganiser = intent.getStringExtra("event_organiser");// Getting date from adapter class
        long eventDate = intent.getLongExtra("event_date", 0);// Getting data from adapter class
        // Now setting data
        binding.eventNameId.setText(eventName);
        binding.organiserNameId.setText("Organiser:  " + eventOrganiser);
        binding.dateId.setText(dateTime.Date(eventDate));
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
            Toast.makeText(this, "We will remained you " + dateTime.DayTime(intent.getLongExtra("event_date", 0) - 86400000),
                    Toast.LENGTH_LONG).show();
        });
        binding.eventReminderBtnId2.setOnClickListener(v -> { // When click on Reminder Btn 2
            binding.eventReminderBtnId.setVisibility(View.VISIBLE);
            binding.eventReminderBtnId2.setVisibility(View.GONE);
            stopRemainder();
            Toast.makeText(this, "Remainder canceled.", Toast.LENGTH_SHORT).show();
        });
    }

    private void stopRemainder() {
        SharedPreferences sharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remainder", false);
        editor.apply();
        Intent i2 = new Intent(KnowMoreActivity.this, AlarmReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(KnowMoreActivity.this, ALARM_REQUEST_CODE, i2, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);

    }

    private void startRemainder() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bell_1); // Setting alarm , you can change the alarm tone from here
        mediaPlayer.start();
        SharedPreferences sharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remainder", true);
        editor.apply();
        //Setting Alarm
        long alarm_time = intent.getLongExtra("event_date", 0) - 86400000; // Getting one day before time 10:00 AM
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent1 = new Intent(KnowMoreActivity.this, AlarmReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(KnowMoreActivity.this, ALARM_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, pendingIntent);

    }
}