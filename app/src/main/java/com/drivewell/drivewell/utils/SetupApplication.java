package com.drivewell.drivewell.utils;

import com.drivewell.drivewell.database.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by abid on 3/9/18.
 */

public class SetupApplication {

    private static Firestore firestore=Firestore.getInstance();
    private static FirebaseAuth auth=FirebaseAuth.getInstance();

    public static void Initialize(){
        firestore.getUserInformation(auth.getCurrentUser().getUid());
    }
}
