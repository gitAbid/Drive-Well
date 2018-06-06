package com.drivewell.drivewell.ui.auth.signup;
import android.app.Activity;
import android.net.Uri;

import com.drivewell.drivewell.model.User;

/**
 * Created by abid on 2/23/18.
 */

public interface ISignupPresenter {
    void init(Activity activity);
    void signUp(User user);
    void uploadProfilePicture(Uri filepath);
}
