package com.championclub_balirmath.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.championclub_balirmath.com.Adapter.EventCardAdapter;
import com.championclub_balirmath.com.Model.EventCardModel;
import com.championclub_balirmath.com.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    EventCardAdapter adapter;
    ArrayList<EventCardModel> modelArrayList;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cardRecView(); // This function is used for sending data to card recycler view in home page
        buttonCLicked();

    }

    private void buttonCLicked() {
        binding.messageBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChattingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void cardRecView() {
        binding.activeEventCardId.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        modelArrayList = new ArrayList<>();
        modelArrayList.add(new EventCardModel("Blood Donation Camp", "Akash Bhadra", "10/12/23"));
        modelArrayList.add(new EventCardModel("Swrasati Puja", "Akash Bhadra", "10/12/23"));
        modelArrayList.add(new EventCardModel("Holi", "Akash Bhadra", "10/12/23"));
        modelArrayList.add(new EventCardModel("Sitala Puja", "Akash Bhadra", "10/12/23"));
        modelArrayList.add(new EventCardModel("Kali Puja", "Pabitra Biswas", "10/12/23"));
        adapter = new EventCardAdapter(modelArrayList);
        binding.activeEventCardId.setAdapter(adapter);
    }


}