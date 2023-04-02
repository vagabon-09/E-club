package com.championclub_balirmath.com.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.championclub_balirmath.com.Activity.ProfileActivity;
import com.championclub_balirmath.com.Model.ProfileModel;
import com.championclub_balirmath.com.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MembersAdapter extends FirebaseRecyclerAdapter<ProfileModel, MembersAdapter.MyViewHolder> {

    public MembersAdapter(@NonNull FirebaseRecyclerOptions<ProfileModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MembersAdapter.MyViewHolder holder, int position, @NonNull ProfileModel model) {
        holder.memberEmail.setText(model.getUserEmail());
        holder.memberName.setText(model.getUserName());
        holder.memberBtn.setOnClickListener(v -> {
            Intent intent = new Intent(holder.memberBtn.getContext(), ProfileActivity.class);
            intent.putExtra("uId", getRef(position).getKey());
            holder.memberBtn.getContext().startActivity(intent);
        });
        Picasso.get().load(model.getProfilePhoto()).into(holder.memberImage);


    }

    @NonNull
    @Override
    public MembersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_member_layout, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView memberImage;
        TextView memberName;
        TextView memberEmail;
        CardView memberBtn;
        ShimmerFrameLayout memberShimmer;
        RecyclerView memberRecView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            memberImage = itemView.findViewById(R.id.memberProfileImageView);
            memberName = itemView.findViewById(R.id.memberNameId);
            memberEmail = itemView.findViewById(R.id.memberEmailId);
            memberBtn = itemView.findViewById(R.id.memberCardBtnId);
            memberImage = itemView.findViewById(R.id.memberProfileImageView);
            memberShimmer = itemView.findViewById(R.id.memberShimmerId);
            memberRecView = itemView.findViewById(R.id.memberRecView);

        }
    }
}
