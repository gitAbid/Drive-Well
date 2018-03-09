package com.drivewell.drivewell.ui.profile;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.constants.InitialInformation;
import com.drivewell.drivewell.database.IDatabase;
import com.drivewell.drivewell.database.firestore.Firestore;
import com.drivewell.drivewell.model.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by abid on 1/22/18.
 */

public class ProfilePresenter implements IProfilePresenter {

    public static ProfilePresenter instance;
    private View view;
    private Activity activity;
    private IDatabase mFirestore;
    private User user;

    private TextView mProfileName;
    @Override
    public void init(View view, Activity activity) {
        this.view=view;
        this.activity=activity;
        mProfileName=view.findViewById(R.id.tvProfileName);
        mFirestore= Firestore.getInstance();
        user= InitialInformation.user;

    }

    @Override
    public void setupProfile() {
       try {
           mProfileName.setText(user.getName());
       }catch (Exception e){

       }
        Log.e("UserName",user.toString());
    }

    public static ProfilePresenter getInstance() {
        return instance==null?new ProfilePresenter():instance;
    }
}
