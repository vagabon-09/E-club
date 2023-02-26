package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;

import com.championclub_balirmath.com.Adapter.EventCardAdapter;
import com.championclub_balirmath.com.Model.EventCardModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.DateTime;
import com.championclub_balirmath.com.databinding.ActivityHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private DatabaseReference reference;
    private EventCardAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        cardRecView(); // This function is used for sending data to card recycler view in home page
        buttonCLicked();


    }

    private void buttonCLicked() {

        binding.messageBtnId.setOnClickListener(v -> { // This button for redirecting , Home page to Message page
            Intent intent = new Intent(HomeActivity.this, ChattingActivity.class);
            startActivity(intent);
        });

        binding.eventBtnId.setOnClickListener(v -> { // This on click listener is to redirect to event activity
            createEvents();
        });

        binding.proflleBtn.setOnClickListener(v -> { // This button for redirecting home page to Member profile page
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        binding.ClubUsers.setOnClickListener(v -> { // This button for redirecting homepage to ClubUsers page
            Intent intent = new Intent(HomeActivity.this, MembersActivity.class);
            startActivity(intent);
        });


        binding.NoteTakingBtn.setOnClickListener(v -> { // This button for redirecting homepage to Note page
            Intent intent = new Intent(HomeActivity.this, NotesActivity.class);
            startActivity(intent);
        });

        binding.SettingBtnId.setOnClickListener(v -> { // This button for redirecting homepage to Settings page
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        binding.DonateBtnId.setOnClickListener(v -> { // This button for redirecting homepage to Donate page
            Intent intent = new Intent(HomeActivity.this, DonateActivity.class);
            startActivity(intent);
//            Toast.makeText(this, "This option is not available now", Toast.LENGTH_SHORT).show();
        });


    }

    private void createEvents() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.event_dialog_box);
        getDetailsFromCalender(dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void getDetailsFromCalender(Dialog dialog) {
        final long[] convert_ms = {0};
        DateTime dateTime = new DateTime();
        Button calender = dialog.findViewById(R.id.calenderBtnId);
        long ms = System.currentTimeMillis();
        String year = dateTime.Year(ms);
        String month = dateTime.month(ms);
        String day = dateTime.day(ms);
        calender.setText(year + "-" + month + "-" + day);
        calender.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                calender.setText(year1 + "-" + month1 + "-" + dayOfMonth);
                Date date = new Date(year1 - 1900, month1 - 1, dayOfMonth, 10, 0, 0);
                convert_ms[0] = date.getTime();

            }, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            AppCompatButton create = dialog.findViewById(R.id.createBtnId);
            create.setOnClickListener(v1 -> {
                TextInputEditText eventName, organiserName;
                eventName = dialog.findViewById(R.id.getEventNameId);
                organiserName = dialog.findViewById(R.id.createOrganiserNameIdInput);
                EventCardModel model = new EventCardModel(Objects.requireNonNull(eventName.getText()).toString(), Objects.requireNonNull(organiserName.getText()).toString(), convert_ms[0]);
                reference.child("Events").push().setValue(model).addOnSuccessListener(unused -> Toast.makeText(HomeActivity.this, "Insert Done", Toast.LENGTH_SHORT).show());
                dialog.dismiss();
            });

            datePickerDialog.show();
        });
    }

    public void cardRecView() {
        long ms = System.currentTimeMillis();
        binding.activeEventCardId.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<EventCardModel> modelArrayList = new ArrayList<>();
        FirebaseRecyclerOptions<EventCardModel> options =
                new FirebaseRecyclerOptions.Builder<EventCardModel>()
                        .setQuery(reference.child("Events"), EventCardModel.class)
                        .build();
        adapter = new EventCardAdapter(options);
        binding.activeEventCardId.setAdapter(adapter);
    }

    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}