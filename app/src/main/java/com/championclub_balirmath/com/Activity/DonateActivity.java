package com.championclub_balirmath.com.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.championclub_balirmath.com.Adapter.BalanceHistoryAdapter;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.Model.BalanceHistoryModal;
import com.championclub_balirmath.com.Model.WalletModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.databinding.ActivityDonateBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private int amount = 0;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private BalanceHistoryAdapter adapter;
    private long balance = 0;
    private String upiId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        amount = 5000;

        fetchBalanceHistory();// All required data fetching here
        fetchWalletBalance();// Fetching all wallet balance and other details in this function
        clickButton();//All clickable button
    }

    private void fetchWalletBalance() {
        reference.child("Account").child("Wallet").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WalletModel model = snapshot.getValue(WalletModel.class);
                assert model != null;
                balance = model.getTotalAmount();
                upiId = model.getUpi();
                binding.donateWalletBalanceId.setText("" + balance);
                binding.donateWalletUpiId.setText("UPI: " + upiId);
                Log.d("Amount", "onDataChange: " + balance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clickButton() {
        binding.donatePayNowBtnId.setOnClickListener(v -> { //When click on Pay now Btn
            // Toast.makeText(this, "CLicked...", Toast.LENGTH_SHORT).show();
            makePayment();
        });
        binding.donateBackBtnId.setOnClickListener(v -> {//When pressed back button
            onBackPressed();
        });
        binding.ClipBordId.setOnClickListener(v -> { // Copy text by clicking clip bord btn
            String copy = binding.donateWalletUpiId.getText().toString();
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("lable", copy);
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(this, "UPI Copied.", Toast.LENGTH_SHORT).show();
        });


    }

    private void fetchBalanceHistory() {
        FirebaseRecyclerOptions<BalanceHistoryModal> options
                = new FirebaseRecyclerOptions.Builder<BalanceHistoryModal>()
                .setQuery(reference.child("Account").child("BalanceHistory"), BalanceHistoryModal.class)
                .build();

        binding.donateBalanceHistoryId.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new BalanceHistoryAdapter(options);
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
    public void onPaymentSuccess(String razorpayPaymentID) { // This function will call automatically when payment will success

        //Sending balance history to database
        reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("snapshot", "onDataChange: " + snapshot.toString());
                ProfileModel model = snapshot.getValue(ProfileModel.class);//Fetching login user data from database
                assert model != null;
                String name = model.getUserName();//Getting username for storing in database
                long timeStamp = new Date().getTime();//Getting time stamp
                BalanceHistoryModal balanceHistoryModal = new BalanceHistoryModal(name, razorpayPaymentID, timeStamp, amount);//Adding all data to wallet model to send in database
                reference.child("Account").child("BalanceHistory").push().setValue(balanceHistoryModal); // Sending all data to database for balance history

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DonateActivity.this, "Payment Cancelled...", Toast.LENGTH_SHORT).show();
            }
        });

        // Updating wallet balance
        reference.child("Account").child("Wallet").child("totalAmount").setValue(balance += (amount / 100)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Void unused) {
//                Log.d("balance", "onSuccess: "+balance);
//                Log.d("amount", "onSuccess: "+amount/100);
                binding.donateWalletBalanceId.setText("" + balance);
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