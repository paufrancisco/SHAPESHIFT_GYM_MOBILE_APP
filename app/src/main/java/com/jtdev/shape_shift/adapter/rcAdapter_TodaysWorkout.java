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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.activity.act_viewWorkout;
import com.jtdev.shape_shift.model.model_Workout;

import java.util.ArrayList;

public class rcAdapter_TodaysWorkout extends RecyclerView.Adapter<rcAdapter_TodaysWorkout.RCViewHolderHome>{

    Context context;
    ArrayList<model_Workout> modelArrayList;
    boolean isRestDay;

    public rcAdapter_TodaysWorkout(Context context, ArrayList<model_Workout> modelArrayList, boolean isRestDay) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.isRestDay = isRestDay;
    }

    @NonNull
    @Override
    public RCViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_today_workout, parent, false);
        return new RCViewHolderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewHolderHome holder, int position) {
        model_Workout model = modelArrayList.get(position);

        holder.number.setText(model.workoutNumber);
        holder.title.setText(model.workoutTitle);
        holder.des.setText(model.workoutDescription);
        Glide.with(context)
                .asGif()
                .load(model.workoutGIPH)
                .into(holder.imageView);

        if (isRestDay) {
            holder.cardView.setClickable(false);
        } else {
            checkWorkoutStatus(model, holder.cardView,holder.cvTodayWorkout);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), act_viewWorkout.class);
                    intent.putExtra("giph", model.workoutGIPH);
                    intent.putExtra("placeholder", model.workPlaceholder);
                    intent.putExtra("title", model.workoutTitle);
                    intent.putExtra("number", model.workoutNumber);
                    intent.putExtra("description", model.workoutDescription);
                    intent.putExtra("sets", model.workSets);
                    intent.putExtra("repetition", model.workRepetition);
                    intent.putExtra("timer", model.workTimer);
                    intent.putExtra("rest", model.rest);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class RCViewHolderHome extends RecyclerView.ViewHolder {
        TextView number, title, des;
        CardView cardView, cvTodayWorkout;
        ImageView imageView;

        public RCViewHolderHome(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.workoutNumber);
            title = itemView.findViewById(R.id.workoutTitle);
            des = itemView.findViewById(R.id.workoutDetails);
            cardView = itemView.findViewById(R.id.cvHome);
            imageView = itemView.findViewById(R.id.ivHome);
            cvTodayWorkout = itemView.findViewById(R.id.cvTodayWorkout);
        }
    }

    private void checkWorkoutStatus(model_Workout model, CardView cardView1, CardView cardView2) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userNode = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("doneWorkout");

            userNode.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean isWorkoutSaved = false;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        model_Workout workout = snapshot.getValue(model_Workout.class);
                        if (workout != null && workout.workoutNumber.equals(model.workoutNumber) && workout.workoutTitle.equals(model.workoutTitle)) {
                            isWorkoutSaved = true;
                            break;
                        }
                    }

                    if (isWorkoutSaved) {
                        cardView1.setClickable(false);
                        cardView2.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_orange));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors.
                }
            });
        }
    }
}
