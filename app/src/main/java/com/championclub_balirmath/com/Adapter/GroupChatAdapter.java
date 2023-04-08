package com.championclub_balirmath.com.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Model.GroupChatModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.DateTime;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
        DateTime dateTime = new DateTime(); // Calling dateTime class for codeReusable
        if (holder.getClass().equals(ReciverViewHolder.class)) {
            String time = dateTime.Time(list.get(position).getTimestamp());
            ((ReciverViewHolder) holder).r_message.setText(list.get(position).getMessage());
            ((ReciverViewHolder) holder).r_time.setText(time);
            ((ReciverViewHolder) holder).r_name.setText(list.get(position).getName());
            ((ReciverViewHolder) holder).r_messageBubble.setOnLongClickListener(v -> {
//                Toast.makeText(context, "Receiver btn is Clicked....", Toast.LENGTH_SHORT).show();
                return false;
            });


        } else {
            String time = dateTime.Time(list.get(position).getTimestamp());
            ((SenderViewHolder) holder).s_message.setText(list.get(position).getMessage());
            ((SenderViewHolder) holder).s_time.setText(time);
            ((SenderViewHolder) holder).s_messageBubble.setOnLongClickListener(v -> {
                deleteDialog(position);
                return false;
            });
        }

        //Adding code for debugging purpose
    }

    private void deleteDialog(int position) {
        AlertDialog.Builder messageFeature = new AlertDialog.Builder(context);
        messageFeature.setMessage("Do you want to delete the message ?");
        messageFeature.setCancelable(true);
        messageFeature.setPositiveButton("Delete", (dialog, which) -> {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference().child("club_chat").orderByChild("message").equalTo(list.get(position).getMessage()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        snapshot1.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        //      Log.d("key", "onClick: "+database.getReference().child("club_chat").getKey());
        });
        messageFeature.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show());
        messageFeature.show();
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
        RelativeLayout s_messageBubble;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            s_message = itemView.findViewById(R.id.SenderMessageId);
            s_time = itemView.findViewById(R.id.SenderTimeId);
            s_messageBubble = itemView.findViewById(R.id.senderMessageTemplateId);
        }
    }


    public static class ReciverViewHolder extends RecyclerView.ViewHolder {
        TextView r_message, r_time, r_name;
        ConstraintLayout r_messageBubble;

        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            r_message = itemView.findViewById(R.id.ReceiverMessageId);
            r_time = itemView.findViewById(R.id.reciverTimeId);
            r_name = itemView.findViewById(R.id.ReciverNameId);
            r_messageBubble = itemView.findViewById(R.id.receiveMessageTemplateId);
        }
    }
}
