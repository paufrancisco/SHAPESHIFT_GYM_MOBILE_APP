package com.jtdev.shape_shift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.adapter.rcAdapter_WorkoutPlan;
import com.jtdev.shape_shift.model.model_Plan;

import java.util.ArrayList;

public class WorkoutPlan extends Fragment {

    RecyclerView recyclerView;
    rcAdapter_WorkoutPlan rcAdapterWorkout;
    ArrayList<model_Plan> modelPlanArrayList;
    TextView displayTragetWorkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_workout_plan, container, false);

        recyclerView = view.findViewById(R.id.rcWorkout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        displayTragetWorkout = view.findViewById(R.id.displayTragetWorkout);
        modelPlanArrayList = new ArrayList<>();
        rcAdapterWorkout = new rcAdapter_WorkoutPlan(getContext(), modelPlanArrayList);
        recyclerView.setAdapter(rcAdapterWorkout);

        fetchProgramData();


        return view;
    }

    private void fetchProgramData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userProgramRef = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(userId).child("user_data").child("program");

            userProgramRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String programValue = snapshot.getValue(String.class);
                        displayTragetWorkout.setText(programValue);
                        if (programValue.equalsIgnoreCase("Gain Weight")) {
                            fetchGainWeight();
                        }
                        else if(programValue.equalsIgnoreCase("Maintain Healthy")){
                            fetchMaintain();
                        } else if (programValue.equalsIgnoreCase("Lose Weight")) {
                            fetchLoseWeight();
                        }else if (programValue.equalsIgnoreCase("Significant Weight Lose")) {
                            fetchSignificantWeightLose();
                        }


                    } else {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void fetchGainWeight() {

        modelPlanArrayList.add(new model_Plan("MON", "CHEST / BACK ", R.drawable.bg_chest_workout, "10 mins", "• 5 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("TUE", "CENTER CORE / LEG", R.drawable.bg_uppercore, "45 mins", "• 5 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("WED", "FULL BODY", R.drawable.bg_squats, "60 mins", "• 3 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("THU", "UPPER BODY", R.drawable.bg_chest_workout, "50 mins", "• 5 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("FRI", "CHEST / LEG", R.drawable.bg_uppercore, "20 mins", "• 5 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("SAT", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));
        modelPlanArrayList.add(new model_Plan("SUN", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));

        if (rcAdapterWorkout != null) {
            rcAdapterWorkout.notifyDataSetChanged();
        }
    }

    public void fetchMaintain() {

        modelPlanArrayList.add(new model_Plan("MON", "CHEST / BACK", R.drawable.bg_chest_workout, "20 mins", "• 5 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("TUE", "UPPER CORE", R.drawable.bg_uppercore, "45 mins", "• 4 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("WED", "LOWER BODY", R.drawable.bg_legday, "60 mins", "• 4 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("THU", "UPPER CORE", R.drawable.bg_uppercore, "50 mins", "• 4 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("FRI", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));
        modelPlanArrayList.add(new model_Plan("SAT", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));
        modelPlanArrayList.add(new model_Plan("SUN", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));

        if (rcAdapterWorkout != null) {
            rcAdapterWorkout.notifyDataSetChanged();
        }
    }
    public void fetchLoseWeight() {

        modelPlanArrayList.add(new model_Plan("MON", "UPPER CORE", R.drawable.bg_chest_workout, "20 mins", "• 5 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("TUE", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));
        modelPlanArrayList.add(new model_Plan("WED", "UPPER BODY", R.drawable.bg_uppercore, "25 mins", "• 5 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("THU", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));
        modelPlanArrayList.add(new model_Plan("FRI", "CARDIO", R.drawable.bg_legday, "180 mins", "• 3 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("SAT", "UPPER BODY", R.drawable.bg_uppercore, "30 mins", "• 4 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("SUN", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));

        if (rcAdapterWorkout != null) {
            rcAdapterWorkout.notifyDataSetChanged();
        }
    }
    public void fetchSignificantWeightLose() {

        modelPlanArrayList.add(new model_Plan("MON", "CARDIO", R.drawable.bg_cardio, "1-2 hours", "• 3 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("TUE", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));
        modelPlanArrayList.add(new model_Plan("WED", "UPPER BODY", R.drawable.bg_uppercore, "40 mins", "• 6 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("THU", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));
        modelPlanArrayList.add(new model_Plan("FRI", "CARDIO", R.drawable.bg_cardio, "1 hour", "• 2 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("SAT", "UPPER BODY", R.drawable.bg_uppercore, "20 mins", "• 3 WORKOUTS"));
        modelPlanArrayList.add(new model_Plan("SUN", "REST DAY", R.drawable.bg_restday, "No Exercise Yet", "• 3 REMINDERS"));

        if (rcAdapterWorkout != null) {
            rcAdapterWorkout.notifyDataSetChanged();
        }
    }
}
