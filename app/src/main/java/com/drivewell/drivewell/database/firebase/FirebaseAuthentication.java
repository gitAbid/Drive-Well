package com.drivewell.drivewell.database.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.drivewell.drivewell.model.User;
import com.drivewell.drivewell.ui.LandingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.net.UnknownServiceException;

/**
 * Created by abid on 3/4/18.
 */

public class FirebaseAuthentication {
    private static FirebaseAuthentication instance;
    private FirebaseAuth mAuth;
    private Activity activity;


    public FirebaseAuthentication() {
        mAuth=FirebaseAuth.getInstance();
    }

    public void init(Activity activity
    ){
        this.activity=activity;
    }

    public static FirebaseAuthentication getInstance() {
        return instance==null?new FirebaseAuthentication():instance;
    }

    public void FirebaseSignUp(User user){
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent=new Intent(activity, LandingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivities(new Intent[]{intent});
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Signup",e.getMessage());
            }
        });
    }

    public void FirebaseLogin(User user){
        mAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent=new Intent(activity, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivities(new Intent[]{intent});
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
