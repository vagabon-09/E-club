package com.championclub_balirmath.com.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Model.EventCardModel;
import com.championclub_balirmath.com.R;

import java.util.ArrayList;

public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.MyViewHolder> {

    ArrayList<EventCardModel> eventCardModels;

    @NonNull
    @Override
    public EventCardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCardAdapter.MyViewHolder holder, int position) {
        holder.EventName.setText(eventCardModels.get(position).getEventName());
        holder.EventOrganiserName.setText(eventCardModels.get(position).getEventOrganiserName());
        holder.EventDate.setText(eventCardModels.get(position).getEventDate());
    }

    @Override
    public int getItemCount() {
        return eventCardModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView EventName, EventOrganiserName, EventDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            EventName = itemView.findViewById(R.id.eventTitleId);
            EventDate = itemView.findViewById(R.id.eventDateId);
            EventOrganiserName = itemView.findViewById(R.id.eventOrganiserNameId);
        }
    }
}
