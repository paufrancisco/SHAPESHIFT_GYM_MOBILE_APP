package com.jtdev.shape_shift.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jtdev.shape_shift.activity.act_Login;
import com.jtdev.shape_shift.activity.act_dashBoard;
import com.jtdev.shape_shift.activity.act_forgotPassword;
import com.jtdev.shape_shift.R;

public class Login extends Fragment {

    public Button btnLogin;
    public EditText email, password;
    public TextView signup;

    public TextView forgot;

   public FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_login, container, false);


        forgot = view.findViewById(R.id.forgot);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        btnLogin = view.findViewById(R.id.btn_Login);
        signup = view.findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() || !validatePass()) {

                } else {
                    mAuth = FirebaseAuth.getInstance();
                    String userEmail = email.getText().toString().trim();
                    String userPass = password.getText().toString().trim();
                    checkUser(userEmail,userPass,mAuth,requireContext());
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), act_forgotPassword.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), act_Login.class);
                intent.putExtra("tab", 1);
                startActivity(intent);
            }
        });

        return view;
    }

    private boolean validateEmail() {
        String val = email.getText().toString();
        if (val.isEmpty()) {
            email.setError("Please enter your email");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePass() {
        String val = password.getText().toString();
        if (val.isEmpty()) {
            password.setError("Please enter your password");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public void checkUser(String userEmail,String userPass, FirebaseAuth mAuth, Context context) {
        mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(requireContext(), act_dashBoard.class);
                        requireContext().startActivity(intent);
                    } else {

                        Toast.makeText(requireContext(), "Authentication failed. Invalid email or password.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

