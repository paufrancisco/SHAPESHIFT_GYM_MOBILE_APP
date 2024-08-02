package com.jtdev.shape_shift;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AccountService {

    public boolean forgotPassword(String email, FirebaseAuth firebaseAuth) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            return false;
        }

        Task<Void> task = firebaseAuth.sendPasswordResetEmail(email);
        return task.isSuccessful();
    }
}
