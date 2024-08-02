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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.activity.act_viewWorkout;
import com.jtdev.shape_shift.activity.act_viewWorkoutList;
import com.jtdev.shape_shift.model.model_Plan;
import com.jtdev.shape_shift.model.model_Workout;

import java.util.ArrayList;

public class rcAdapter_WorkoutList extends RecyclerView.Adapter<rcAdapter_WorkoutList.RCViewholder> {

    Context context;
    ArrayList<model_Workout> modelWorkoutArrayList;

    public rcAdapter_WorkoutList(Context context, ArrayList<model_Workout> modelWorkoutArrayList) {
        this.context = context;
        this.modelWorkoutArrayList = modelWorkoutArrayList;
    }

    @NonNull
    @Override
    public RCViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_workout_list,parent,false);
        return new RCViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewholder holder, int position) {

        model_Workout modelWorkout = modelWorkoutArrayList.get(position);
        holder.title.setText(modelWorkout.workoutTitle);
        holder.details.setText(modelWorkout.workdetails);
        holder.giph.setImageResource(modelWorkout.workoutGIPH);
        holder.workNumber.setText(modelWorkout.workoutNumber);

        if (modelWorkout.workoutTitle.equalsIgnoreCase("Boost muscle repair") ||
                modelWorkout.workoutTitle.equalsIgnoreCase("Stretching") ||
                modelWorkout.workoutTitle.equalsIgnoreCase("Rest and Recover")) {

            holder.giph.setImageResource(modelWorkout.workoutGIPH);
        } else {
            Glide.with(context)
                    .asGif()
                    .load(modelWorkout.workoutGIPH)
                    .into(holder.giph);
        }


    }

    @Override
    public int getItemCount() {
        return modelWorkoutArrayList.size();
    }

    public class RCViewholder extends RecyclerView.ViewHolder {

        TextView title,details,workNumber;
        ImageView giph;
        CardView cardView;



        public RCViewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvWorkTitle);
            details = itemView.findViewById(R.id.tvDetails);
            giph = itemView.findViewById(R.id.imgHolder);
            cardView = itemView.findViewById(R.id.cvWorkList);
            workNumber = itemView.findViewById(R.id.tvWorkoutNumber);


        }
    }
}
