package com.championclub_balirmath.com.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Activity.MembersActivity;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.makeramen.roundedimageview.RoundedImageView;

public class MembersAdapter extends FirebaseRecyclerAdapter<ProfileModel, MembersAdapter.MyViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MembersAdapter(@NonNull FirebaseRecyclerOptions<ProfileModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MembersAdapter.MyViewHolder holder, int position, @NonNull ProfileModel model) {
        holder.memberEmail.setText(model.getUserEmail());
        holder.memberName.setText(model.getUserName());
    }

    @NonNull
    @Override
    public MembersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_member_layout,parent,false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView memberImage;
        TextView memberName;
        TextView memberEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            memberImage = itemView.findViewById(R.id.memberProfileImageView);
            memberName = itemView.findViewById(R.id.memberNameId);
            memberEmail = itemView.findViewById(R.id.memberEmailId);
        }
    }
}
