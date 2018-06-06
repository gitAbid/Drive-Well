package com.drivewell.drivewell.ui.auth.signup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.Spinner;

import com.drivewell.drivewell.database.firestore.Firestore;
import com.drivewell.drivewell.model.User;
import com.drivewell.drivewell.ui.LandingActivity;
import com.drivewell.drivewell.utils.SetupApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

/**
 * Created by abid on 2/23/18.
 */

public class SignupPresenter implements ISignupPresenter {

    private static SignupPresenter instance;
    private CardView mLoginBack;
    private FloatingActionButton mSignup;

    private TextInputEditText mEmail, mPassword, mConfirmPassword, mName, mAge, mHomeAddress, mContactNo;
    private Spinner mUserType;

    private Activity activity;
    FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    public SignupPresenter()
    {
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }


    @Override
    public void init(Activity activity) {
        this.activity=activity;
    }

    @Override
    public void signUp(User user) {

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        user.setUserId(mAuth.getCurrentUser().getUid());
                        Firestore.getInstance().createUser(user);
                        SetupApplication.Initialize();
                        activity.startActivities(new Intent[]{new Intent(activity.getApplicationContext(), LandingActivity.class)});

                    }
                }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public void uploadProfilePicture(Uri filepath) {

        StorageReference riversRef = mStorageRef.child("images/"+ UUID.randomUUID()+".jpg");

        riversRef.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        SignupFragment.getInstance().setProfilePicture(downloadUrl);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...

                        Log.e("Failure",exception.getMessage());
                    }
                });

    }


    public static SignupPresenter getInstance() {
        return (instance == null) ? new SignupPresenter() : instance;
    }



}
