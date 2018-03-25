package com.drivewell.drivewell.ui.workplace.jobs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.constants.FirebaseEndpoints;
import com.drivewell.drivewell.model.JobsDataModel;
import com.drivewell.drivewell.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * Created by abid on 3/26/18.
 */

public class DialogAddJob extends Dialog {

    private static final String TAG ="Dialog" ;
    private Button mSubmitJob;
    private Spinner mJobCategory;
    private EditText mTitle,mDescription,mLocation;
    private ProgressBar mLoading;
    private FirebaseFirestore firebaseFirestore;

    User user;

    public DialogAddJob(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_job_post);
        firebaseFirestore=FirebaseFirestore.getInstance();

        mSubmitJob=findViewById(R.id.btSubmitJob);
        mJobCategory=findViewById(R.id.spnrJobCategory);
        mTitle=findViewById(R.id.etJobTitle);
        mDescription=findViewById(R.id.etJobDescription);
        mLocation=findViewById(R.id.etJobLocation);
        mLoading=findViewById(R.id.pbLoading);


        CollectionReference users = firebaseFirestore.collection(FirebaseEndpoints.USERS);
        users.whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> usersList = task.getResult().toObjects(User.class);
                            user = usersList.get(0);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        mSubmitJob.setOnClickListener(e->{
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            firebaseFirestore.collection(FirebaseEndpoints.JOBS).add(
                    new JobsDataModel(mTitle.getText().toString(),
                            mDescription.getText().toString(),
                            mLocation.getText().toString(),
                            mJobCategory.getSelectedItem().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            user.getName(),
                            user.getProfileImageUrl(),
                            ts
                            )

            ).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    dismiss();
                }
            });


        });

    }
}
