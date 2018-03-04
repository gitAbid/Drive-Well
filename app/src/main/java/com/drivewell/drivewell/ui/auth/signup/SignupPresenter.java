package com.drivewell.drivewell.ui.auth.signup;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Spinner;

import com.drivewell.drivewell.database.firebase.FirebaseAuthentication;
import com.drivewell.drivewell.model.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by abid on 2/23/18.
 */

public class SignupPresenter implements ISignupPresenter {

    private static SignupPresenter instance;
    private CardView mLoginBack;
    private FloatingActionButton mSignup;

    private TextInputEditText mEmail,mPassword,mConfirmPassword,mName,mAge,mHomeAddress,mContactNo;
    private Spinner mUserType;

    private Activity activity;

    @Override
    public void signUp(User user) {
        boolean valid=validateUser(user);
        if (valid){
            FirebaseAuthentication.getInstance().init(activity);
            FirebaseAuthentication.getInstance().FirebaseSignUp(user);
        }
    }

    private boolean validateUser(User user) {
        return true;
    }

    @Override
    public void init(Activity activity, CardView mLoginBack, FloatingActionButton mSignup, TextInputEditText mEmail, TextInputEditText mPassword, TextInputEditText mConfirmPassword, Spinner mUserType, TextInputEditText mName, TextInputEditText mAge, TextInputEditText mHomeAddress, TextInputEditText mContactNo) {
        this.mLoginBack=mLoginBack;
        this.mSignup=mSignup;
        this.mEmail=mEmail;
        this.mPassword=mPassword;
        this.mConfirmPassword=mConfirmPassword;
        this.mContactNo=mContactNo;
        this.mHomeAddress=mHomeAddress;
        this.mAge=mAge;
        this.mName=mName;
        this.mUserType=mUserType;
        this.activity=activity;
    }

    public static SignupPresenter getInstance() {
        return (instance==null)?new SignupPresenter():instance;
    }



}
