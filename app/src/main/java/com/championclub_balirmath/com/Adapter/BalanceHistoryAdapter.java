package com.championclub_balirmath.com.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Model.BalanceHistoryModal;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.DateTime;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class BalanceHistoryAdapter extends FirebaseRecyclerAdapter<BalanceHistoryModal, BalanceHistoryAdapter.MyViewHolder> {


    public BalanceHistoryAdapter(@NonNull FirebaseRecyclerOptions<BalanceHistoryModal> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull BalanceHistoryModal model) {
        DateTime dT = new DateTime();
        String dayTime = dT.DayTime(model.getSendingTime());
        holder.t_name.setText(model.getSenderName());
        holder.t_amount.setText("+ ₹" + model.getPayAmount() / 100);
        holder.t_time.setText(dayTime);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_balance_history, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name, t_time, t_amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.DonateHistoryNameId);
            t_time = itemView.findViewById(R.id.DonateTimeId);
            t_amount = itemView.findViewById(R.id.DonateAmountId);
        }
    }
}
