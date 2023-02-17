package com.championclub_balirmath.com.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Model.GroupChatModel;
import com.championclub_balirmath.com.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GroupChatAdapter extends RecyclerView.Adapter {

    ArrayList<GroupChatModel> list;
    Context context;
    final int SENDER_VIEW_TYPE = 1;
    final int RECEIVER_VIEW_TYPE = 2;

    public GroupChatAdapter(ArrayList<GroupChatModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == RECEIVER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.single_sender_message, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.single_message_recived, parent, false);
            return new ReciverViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass().equals(ReciverViewHolder.class)){
            ((ReciverViewHolder) holder).r_message.setText(list.get(position).getMessage());
        }else {
            ((SenderViewHolder)holder).s_message.setText(list.get(position).getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (list.get(position).getUserId().equals(FirebaseAuth.getInstance().getUid())) {
            return RECEIVER_VIEW_TYPE;
        }
        return SENDER_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView s_message, s_time;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            s_message = itemView.findViewById(R.id.SenderMessageId);
            s_time = itemView.findViewById(R.id.SenderTimeId);
        }
    }


    public static class ReciverViewHolder extends RecyclerView.ViewHolder {
        TextView r_message, r_time;

        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            r_message = itemView.findViewById(R.id.ReceiverMessageId);
            r_time = itemView.findViewById(R.id.reciverTimeId);
        }
    }
}
