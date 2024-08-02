package com.jtdev.shape_shift.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jtdev.shape_shift.activity.act_SplashScreen;
import com.jtdev.shape_shift.activity.act_Login;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.model.model_UserProfile;

public class SignUp extends Fragment {

    private EditText edtName, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
    private Button btnCreateAccount;
    private TextView txtLogin;
    private static final String TAG = "SignUpFrag";
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private FirebaseAuth mAuth;

    //show password
    private ImageView ivTogglePw,ivTogglePw2;
    private boolean isPasswordVisible = false;
    private TextView passwordValidationTextView,passwordValidationTextView2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_signup, container, false);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        //id for widgets
        edtName = view.findViewById(R.id.edt_name);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPhone = view.findViewById(R.id.edt_phone);
        edtPassword = view.findViewById(R.id.edt_pw);
        edtConfirmPassword = view.findViewById(R.id.edt_confirmPass);
        passwordValidationTextView = view.findViewById(R.id.passwordValidationTextView);
        passwordValidationTextView2 = view.findViewById(R.id.passwordValidationTextView2);

        //toggle
        ivTogglePw = view.findViewById(R.id.iv_toggle_pw);
        ivTogglePw2 = view.findViewById(R.id.iv_toggle_pw2);

        ivTogglePw.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Hide password
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivTogglePw.setImageResource(R.drawable.ic_eye_off);
            } else {
                // Show password
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivTogglePw.setImageResource(R.drawable.ic_eye_on);

            }

            edtPassword.setSelection(edtPassword.length());
            isPasswordVisible = !isPasswordVisible;
        });

        //confirm
        ivTogglePw2.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Hide password
                edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivTogglePw2.setImageResource(R.drawable.ic_eye_off);
            } else {
                // Show password
                edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivTogglePw2.setImageResource(R.drawable.ic_eye_on);
            }

            edtConfirmPassword.setSelection(edtConfirmPassword.length());
            isPasswordVisible = !isPasswordVisible;
        });


        txtLogin = view.findViewById(R.id.textLogin);
        btnCreateAccount = view.findViewById(R.id.btn_create_account);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), act_Login.class));
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(requireContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(requireContext(), "Please enter your confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
               /*  if (password.length() < 8) {
                    passwordValidationTextView.setText("*Password must be at least 8 characters long");
                    passwordValidationTextView.setVisibility(View.VISIBLE);
                    edtPassword.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    return;
                }
                // Validating password complexity
               if (!isPasswordValid(password)) {
                    if (!password.matches(".*[A-Z].*")) {
                        passwordValidationTextView.setText("*Password must contain at least one uppercase letter");
                        passwordValidationTextView.setVisibility(View.VISIBLE);
                        edtPassword.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    } else if (!password.matches(".*[a-z].*")) {
                        passwordValidationTextView.setText("*Password must contain at least one lowercase letter");
                        passwordValidationTextView.setVisibility(View.VISIBLE);
                        edtPassword.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    } else if (!password.matches(".*\\d.*")) {
                        passwordValidationTextView.setText("*Password must contain at least one number");
                        passwordValidationTextView.setVisibility(View.VISIBLE);
                        edtPassword.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|].*")) {
                        passwordValidationTextView.setText("*Password must contain at least one special character");
                        passwordValidationTextView.setVisibility(View.VISIBLE);
                        edtPassword.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    }
                    return;
                }*/

                /*
                // Validating password length
                if (password.length() < 8) {
                    Toast.makeText(requireContext(), "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validating password complexity
                if (!isPasswordValid(password)) {
                    if (!password.matches(".*[A-Z].*")) {
                        Toast.makeText(requireContext(), "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
                    } else if (!password.matches(".*[a-z].*")) {
                        Toast.makeText(requireContext(), "Password must contain at least one lowercase letter", Toast.LENGTH_SHORT).show();
                    } else if (!password.matches(".*\\d.*")) {
                        Toast.makeText(requireContext(), "Password must contain at least one number", Toast.LENGTH_SHORT).show();
                    } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|].*")) {
                        Toast.makeText(requireContext(), "Password must contain at least one special character", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }*/

                if(password.equals(confirmPassword)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        model_UserProfile userProfile = new model_UserProfile(name, phone, email);
                                        //usersRef.child(mUser.getUid()).child("user_data").setValue(userData)
                                        reference.child(user.getUid()).child("profile").setValue(userProfile);


                                        startActivity(new Intent(requireContext(), act_SplashScreen.class));
                                    } else {


                                        Toast.makeText(requireContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else {
                    edtConfirmPassword.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    passwordValidationTextView2.setText("Password does not match");
                    Toast.makeText(requireContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public boolean isPasswordValid(String password) {
        // At least 8 characters
        boolean lengthRequirement = password.length() >= 8;

        // Contains at least one digit
        boolean digitRequirement = password.matches(".*\\d.*");

        // Contains at least one uppercase letter
        boolean uppercaseRequirement = password.matches(".*[A-Z].*");

        // Contains at least one lowercase letter
        boolean lowercaseRequirement = password.matches(".*[a-z].*");

        // Contains at least one special character
        boolean specialCharRequirement = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|].*");

        // All requirements must be met
        return lengthRequirement && digitRequirement && uppercaseRequirement && lowercaseRequirement && specialCharRequirement;
    }




}


