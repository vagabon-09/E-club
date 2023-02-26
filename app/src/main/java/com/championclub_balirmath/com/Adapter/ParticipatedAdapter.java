package com.championclub_balirmath.com.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Model.ParticipatedModel;
import com.championclub_balirmath.com.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ParticipatedAdapter extends FirebaseRecyclerAdapter<ParticipatedModel, ParticipatedAdapter.MyViewHolder> {

    public ParticipatedAdapter(@NonNull FirebaseRecyclerOptions<ParticipatedModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ParticipatedAdapter.MyViewHolder holder, int position, @NonNull ParticipatedModel model) {
        holder.participateName.setText(model.getName());
    }

    @NonNull
    @Override
    public ParticipatedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_participate_layout, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView participateName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            participateName = itemView.findViewById(R.id.participateNameId);
        }
    }
}
