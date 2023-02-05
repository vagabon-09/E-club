package com.championclub_balirmath.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.championclub_balirmath.com.Adapter.EventCardAdapter;
import com.championclub_balirmath.com.Model.EventCardModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView eventCardView;
    EventCardAdapter adapter;
    ArrayList<EventCardModel> modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        eventCardView = findViewById(R.id.activeEventCardId);
        eventCardView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        modelArrayList = new ArrayList<>();
        modelArrayList.add(new EventCardModel("Blood Donation Camp", "Akash Bhadra", "10/12/23"));
        modelArrayList.add(new EventCardModel("Swrasati Puja", "Akash Bhadra", "10/12/23"));
        modelArrayList.add(new EventCardModel("Holi", "Akash Bhadra", "10/12/23"));
        modelArrayList.add(new EventCardModel("Sitala Puja", "Akash Bhadra", "10/12/23"));
        modelArrayList.add(new EventCardModel("Kali Puja", "Pabitra Biswas", "10/12/23"));
        adapter = new EventCardAdapter(modelArrayList);
        eventCardView.setAdapter(adapter);
    }
}