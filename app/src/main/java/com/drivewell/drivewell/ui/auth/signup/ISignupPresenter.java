package com.drivewell.drivewell.ui.auth.signup;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Spinner;

import com.drivewell.drivewell.model.User;

/**
 * Created by abid on 2/23/18.
 */

public interface ISignupPresenter {
    void signUp(User user);
    void init(Activity activity, CardView mLoginBack, FloatingActionButton mSignup, TextInputEditText mEmail, TextInputEditText mPassword, TextInputEditText mConfirmPassword, Spinner mUserType, TextInputEditText mName, TextInputEditText mAge, TextInputEditText mHomeAddress, TextInputEditText mContactNo);
}
