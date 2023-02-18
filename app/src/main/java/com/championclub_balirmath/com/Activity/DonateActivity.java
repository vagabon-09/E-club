package com.championclub_balirmath.com.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.championclub_balirmath.com.Adapter.WalletAdapter;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.Model.WalletModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.DateTime;
import com.championclub_balirmath.com.databinding.ActivityDonateBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public class DonateActivity extends AppCompatActivity implements PaymentResultListener {
    private Checkout checkout;
    ActivityDonateBinding binding;
    private int amount;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private WalletAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        amount = 100;
        binding.donatePayNowBtnId.setOnClickListener(v -> { //When click on Pay now Btn
//            Toast.makeText(this, "CLicked...", Toast.LENGTH_SHORT).show();
            makePayment();
        });
        fetchBalanceHistory();
        clickButton();
    }

    private void clickButton() {
        binding.donateBackBtnId.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void fetchBalanceHistory() {
        FirebaseRecyclerOptions<WalletModel> options
                = new FirebaseRecyclerOptions.Builder<WalletModel>()
                .setQuery(reference.child("BalanceHistory"), WalletModel.class)
                .build();

        binding.donateBalanceHistoryId.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        adapter = new WalletAdapter(options);
        binding.donateBalanceHistoryId.setAdapter(adapter);
    }

    private void makePayment() {
        checkout = new Checkout();
        Checkout.preload(this);
        checkout.setKeyID("rzp_test_76gfAQItSxgcgk");
        checkout.setImage(R.drawable.bank_black_512);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "Champion Club");
            jsonObject.put("description", "Event Payment");
            jsonObject.put("theme.color", "");
            jsonObject.put("amount", amount);
            // put mobile number
            jsonObject.put("prefill.contact", "8388071823");

            // put email
            jsonObject.put("prefill.email", "rajeshbhadra62@gmail.com");
            checkout.open(DonateActivity.this, jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("snapshot", "onDataChange: " + snapshot.toString());
                ProfileModel model = snapshot.getValue(ProfileModel.class);//Fetching login user data from database
                assert model != null;
                String name = model.getUserName();//Getting username for storing in database
                long timeStamp = new Date().getTime();//Getting time stamp
                WalletModel walletModel = new WalletModel(name, razorpayPaymentID, timeStamp, amount);//Adding all data to wallet model to send in database
                reference.child("BalanceHistory").push().setValue(walletModel); // Sending all data to database for balance history

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DonateActivity.this, "Payment Cancelled...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentError(int code, String description) {
        Log.d("Payment Error: ", "onPaymentError: " + description);
//        Toast.makeText(this, "Payment Error: " + description, Toast.LENGTH_LONG).show();
    }

    @Override
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