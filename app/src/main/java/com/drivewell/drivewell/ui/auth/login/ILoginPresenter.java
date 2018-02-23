package com.drivewell.drivewell.ui.auth.login;

import android.app.Activity;
import android.content.Context;

/**
 * Created by abid on 2/23/18.
 */

public interface ILoginPresenter {
    void init(Activity activity);
    void signIn(String email, String password);
    void signOut();

}
