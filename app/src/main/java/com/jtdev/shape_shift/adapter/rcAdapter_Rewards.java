package com.jtdev.shape_shift.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.model.model_Rewards;

import java.util.ArrayList;

public class rcAdapter_Rewards extends RecyclerView.Adapter<rcAdapter_Rewards.RCViewholder> {

    ArrayList<model_Rewards> modelArrayList;
    Context context;

    public rcAdapter_Rewards(ArrayList<model_Rewards> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RCViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_rewards, parent, false);
        return new RCViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewholder holder, int position) {
        model_Rewards rewardsModel = modelArrayList.get(position);

        holder.name.setText(rewardsModel.title);
        holder.image.setImageResource(rewardsModel.img);

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class RCViewholder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;


        public RCViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvAchievementTitle);
            image = itemView.findViewById(R.id.ivReward);

        }
    }
}

