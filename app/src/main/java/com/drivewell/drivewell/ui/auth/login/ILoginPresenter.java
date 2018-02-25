package com.drivewell.drivewell.ui.auth.login;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by abid on 2/23/18.
 */

public interface ILoginPresenter {
    void init(Activity activity, ProgressBar progressBar, FloatingActionButton mLoginButton);
    void signIn(String email, String password);
    void signOut();

}
