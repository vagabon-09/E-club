package com.championclub_balirmath.com.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.championclub_balirmath.com.Adapter.ParticipatedAdapter;
import com.championclub_balirmath.com.Model.ParticipatedModel;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.Receiver.AlarmReceiver;
import com.championclub_balirmath.com.ReusableCode.DateTime;
import com.championclub_balirmath.com.databinding.ActivityKnowMoreBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class KnowMoreActivity extends AppCompatActivity {
    private ActivityKnowMoreBinding binding;
    static final int ALARM_REQUEST_CODE = 100;
    private AlarmManager alarmManager;
    private DateTime dateTime;
    private Intent intent;
    private DatabaseReference reference;
    private String keyValue;
    private String alarm;
    private FirebaseAuth mAuth;
    private ParticipatedAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKnowMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Setting Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        dateTime = new DateTime();
        //When click button set action
        setCLick();
        //Setting values in appropriate place
        setDate();
        //Setting RecyclerView
        recyclerViewSetUp();
        // Getting data from shared preferences
        isAlarmActive();

    }

    private void recyclerViewSetUp() { // setting data from data base and showing in recyclerview
        FirebaseRecyclerOptions<ParticipatedModel> options
                = new FirebaseRecyclerOptions.Builder<ParticipatedModel>()
                .setQuery(reference.child("Events").child(keyValue).child("participate"), ParticipatedModel.class)
                .build();
        adapter = new ParticipatedAdapter(options);
        binding.participateRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.participateRecView.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void setDate() { // Getting data from adapter class
        intent = getIntent();
        String eventName = intent.getStringExtra("event_name"); // Getting date from adapter class
        String eventOrganiser = intent.getStringExtra("event_organiser");// Getting date from adapter class
        keyValue = intent.getStringExtra("key_value");//Getting keyValue from database
//        alarm = intent.getBooleanExtra("alarm", false); // Getting boolean value from adapter
        long eventDate = intent.getLongExtra("event_date", 0);// Getting data from adapter class


        // Now setting data
        binding.eventNameId.setText(eventName);
        binding.organiserNameId.setText("Organiser:  " + eventOrganiser);
        binding.dateId.setText(dateTime.Date(eventDate));
    }

    private void isAlarmActive() { // Getting shared preferences data  if data is available here then perform this function
        Log.d("isAlarmActive", "isAlarmActive: " + alarm);
        reference.child("Events").child(keyValue).child("alarm").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean check = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                Log.d("DataSnapshot", "onDataChange: " + check);
                if (check) {
                    binding.eventReminderBtnId2.setVisibility(View.VISIBLE);
                    binding.eventReminderBtnId.setVisibility(View.GONE);
                } else {
                    binding.eventReminderBtnId2.setVisibility(View.GONE);
                    binding.eventReminderBtnId.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setCLick() { // All button is setting here
        //when click back button
        binding.eventBackId.setOnClickListener(view -> finish());

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

        binding.participateId.setOnClickListener(v -> { // When click on participate button
            participateNow();
        });

        binding.cancelEventBtnId.setOnClickListener(v -> { // When click on finished event button
            reference.child("Events").child(keyValue).removeValue();
            finish();
        });

    }

    private void participateNow() { // Participate users name sending to data base
        ProfileActivity profileActivity = new ProfileActivity();
        reference.child("Users").child(Objects.requireNonNull(mAuth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileModel model = snapshot.getValue(ProfileModel.class);
                assert model != null;
                ParticipatedModel participatedModel = new ParticipatedModel(model.getUserName());
                reference.child("Events").child(keyValue).child("participate").push().setValue(participatedModel);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profileActivity, "Trying again....", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void stopRemainder() { // This function is used to stop alarm remainder
        cancelAlarm();
        Intent i2 = new Intent(KnowMoreActivity.this, AlarmReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(KnowMoreActivity.this, ALARM_REQUEST_CODE, i2, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);

    }

    private void cancelAlarm() {
        reference.child("Events").child(keyValue).child("alarm").setValue(false);
    }

    private void startRemainder() { // This function is used to start remainder
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bell_1); // Setting alarm , you can change the alarm tone from here
        mediaPlayer.start();
        //Setting Alarm remainder
        setAlarm();
        //Setting Alarm
        long alarm_time = intent.getLongExtra("event_date", 0) - 86400000; // Getting one day before time 10:00 AM
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent1 = new Intent(KnowMoreActivity.this, AlarmReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(KnowMoreActivity.this, ALARM_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, pendingIntent);

    }

    private void setAlarm() { // Setting alarm true
        reference.child("Events").child(keyValue).child("alarm").setValue(true);
    }

    @Override
    protected void onStart() { // It's required for recyclerview
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() { // It's required for recyclerview
        super.onStop();
        adapter.stopListening();
    }
}