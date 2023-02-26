package com.championclub_balirmath.com.Adapter;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.championclub_balirmath.com.Activity.KnowMoreActivity;
import com.championclub_balirmath.com.Model.EventCardModel;
import com.championclub_balirmath.com.R;
import com.championclub_balirmath.com.ReusableCode.DateTime;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class EventCardAdapter extends FirebaseRecyclerAdapter<EventCardModel, EventCardAdapter.MyViewHolder> {


    public EventCardAdapter(@NonNull FirebaseRecyclerOptions<EventCardModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull EventCardModel model) {

        DateTime dateTime = new DateTime();
        String date = dateTime.Date(model.getEventDate());
        holder.EventName.setText(model.getEventName());
        holder.EventDate.setText(date);
        holder.EventOrganiserName.setText(model.getEventOrganiserName());
        holder.knowMore.setOnClickListener(v -> {
            Intent i = new Intent(holder.EventDate.getContext(), KnowMoreActivity.class);
            i.putExtra("event_name", model.getEventName());
            i.putExtra("event_date", model.getEventDate());
            i.putExtra("event_organiser", model.getEventOrganiserName());
            i.putExtra("key_value",getRef(position).getKey());
            holder.knowMore.getContext().startActivity(i);
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event_card, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView EventName, EventOrganiserName, EventDate;
        CardView knowMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            EventName = itemView.findViewById(R.id.eventTitleId);
            EventDate = itemView.findViewById(R.id.eventDateId);
            EventOrganiserName = itemView.findViewById(R.id.eventOrganiserNameId);
            knowMore = itemView.findViewById(R.id.knowMoreBtnId);
        }
    }
}
