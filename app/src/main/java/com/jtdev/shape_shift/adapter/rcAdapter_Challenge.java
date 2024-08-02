package com.jtdev.shape_shift.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.activity.act_challengeInfo;
import com.jtdev.shape_shift.model.model_Challenge;

import java.util.ArrayList;

public class rcAdapter_Challenge extends RecyclerView.Adapter<rcAdapter_Challenge.RCViewholder> {

    ArrayList<model_Challenge> modelArrayList;
    Context context;

    public rcAdapter_Challenge(ArrayList<model_Challenge> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RCViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_challenge, parent, false);
        return new RCViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewholder holder, int position) {
        model_Challenge challengeModel = modelArrayList.get(position);
        holder.name.setText(challengeModel.name);
        holder.image.setImageResource(challengeModel.image);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), act_challengeInfo.class);
                intent.putExtra("name",challengeModel.name);
                intent.putExtra("image",challengeModel.image);
                intent.putExtra("difficulty",challengeModel.difficulty);
                context.startActivity(intent);
            }
        });

        int backgroundColor;
        switch (challengeModel.difficulty) {
            case 5:
                backgroundColor = ContextCompat.getColor(context, R.color.green); // Green for easy
                break;
            case 10:
                backgroundColor = ContextCompat.getColor(context, R.color.yelloworange); // Orange for medium
                break;
            case 15:
            default:
                backgroundColor = ContextCompat.getColor(context, R.color.light_red); // Red for hard
                break;
        }
        holder.itemView.setBackgroundColor(backgroundColor);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class RCViewholder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        CardView cv;

        public RCViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvAchievementTitle);
            image = itemView.findViewById(R.id.ivReward);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}

