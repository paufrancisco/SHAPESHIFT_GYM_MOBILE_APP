package com.jtdev.shape_shift.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.activity.act_viewWorkoutList;
import com.jtdev.shape_shift.model.model_Plan;

import java.util.ArrayList;

public class rcAdapter_WorkoutPlan extends RecyclerView.Adapter<rcAdapter_WorkoutPlan.RCViewholder> {

    Context context;
    ArrayList<model_Plan> modelPlanArrayList;

    public rcAdapter_WorkoutPlan(Context context, ArrayList<model_Plan> modelPlanArrayList) {
        this.context = context;
        this.modelPlanArrayList = modelPlanArrayList;
    }

    @NonNull
    @Override
    public RCViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_workout_plan, parent, false);
        return new RCViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewholder holder, int position) {
        model_Plan modelPlan = modelPlanArrayList.get(position);
        holder.date.setText(modelPlan.date);
        holder.mins.setText(modelPlan.totalMins);
        holder.exercise.setText(modelPlan.numberOfExercise);
        holder.constraintLayout.setBackgroundResource(modelPlan.background);
        holder.typeOfWorkout.setText(modelPlan.typeOfWorkout);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, act_viewWorkoutList.class);
                intent.putExtra("date", modelPlan.getDate());
                intent.putExtra("background", modelPlan.getBackground());
                intent.putExtra("totalMinutes", modelPlan.getTotalMins());
                intent.putExtra("numberOfWorkouts",modelPlan.numberOfExercise);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelPlanArrayList.size();
    }

    public class RCViewholder extends RecyclerView.ViewHolder {

        TextView date, mins, exercise, typeOfWorkout,tvNumberOfWorkouts;
        CardView cardView;
        ConstraintLayout constraintLayout;

        public RCViewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tvWorkNumber);
            mins = itemView.findViewById(R.id.tvDetails);
            exercise = itemView.findViewById(R.id.tvNumberOfExercise);
            cardView = itemView.findViewById(R.id.cvWorkList);
            constraintLayout = itemView.findViewById(R.id.layoutConstraint);
            typeOfWorkout = itemView.findViewById(R.id.tvWorkTitle);
            tvNumberOfWorkouts = itemView.findViewById(R.id.tvNumberOfWorkouts);
        }
    }
}
