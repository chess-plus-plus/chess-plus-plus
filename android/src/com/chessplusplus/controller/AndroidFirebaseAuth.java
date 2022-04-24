package com.chessplusplus.controller;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class AndroidFirebaseAuth implements Executor {
    private FirebaseAuth mAuth;

    public AndroidFirebaseAuth () {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser loginAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("signInAnonymously:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("signInAnonymously:failure: " + task.getException());
                        }
                    }
                });
        return mAuth.getCurrentUser();
    }

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }
}
