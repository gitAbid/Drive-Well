package com.drivewell.drivewell.ui.auth.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.constants.FirebaseEndpoints;
import com.drivewell.drivewell.constants.InitialInformation;
import com.drivewell.drivewell.database.firestore.Firestore;
import com.drivewell.drivewell.model.User;
import com.drivewell.drivewell.ui.LandingActivity;
import com.drivewell.drivewell.ui.MainActivity;
import com.drivewell.drivewell.utils.SetupApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Timer;

/**
 * Created by abid on 2/23/18.
 */

public class LoginPresenter implements ILoginPresenter {
    private Activity activity;
    private static LoginPresenter instance;
    private FirebaseAuth mAuth;
    private ProgressBar mLoginProgressbar;
    private FloatingActionButton mLoginButton;
    private FirebaseFirestore firebaseFirestore;


    public LoginPresenter() {
        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
    }

    @Override
    public void init(Activity activity, ProgressBar progressBar, FloatingActionButton mLoginButton) {
        this.activity=activity;
        this.mLoginButton=mLoginButton;
        this.mLoginProgressbar=progressBar;
    }

    @Override
    public void signIn(String email, String password) {
        mLoginProgressbar.setVisibility(View.VISIBLE);
        mLoginButton.setVisibility(View.INVISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mLoginProgressbar.setVisibility(View.INVISIBLE);
                        mLoginProgressbar.setBackgroundColor(activity.getResources().getColor(R.color.colorStatusGreen));
                        SetupApplication.Initialize();
                        activity.startActivities(new Intent[] {new Intent(activity.getApplicationContext(), LandingActivity.class)});

                    }
                }).addOnFailureListener(activity,new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLoginProgressbar.setBackgroundColor(activity.getResources().getColor(R.color.colorStatusRed));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoginProgressbar.setVisibility(View.INVISIBLE);
                        mLoginButton.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });

    }

    @Override
    public void signOut() {

    }

    @Override
    public void getUserImage(String email) {
        CollectionReference users = firebaseFirestore.collection(FirebaseEndpoints.USERS);
        users.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> usersList = task.getResult().toObjects(User.class);
                           try {
                               LoginFragment.getIntstance().setProfilePicture(usersList.get(0).getProfileImageUrl());
                           }catch (Exception e){
                               e.printStackTrace();
                           }

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public static LoginPresenter getInstance() {
        return instance=(instance==null)?new LoginPresenter():instance;
    }
}

