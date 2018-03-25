package com.drivewell.drivewell.ui.profile;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.constants.FirebaseEndpoints;
import com.drivewell.drivewell.database.IDatabase;
import com.drivewell.drivewell.database.firestore.Firestore;
import com.drivewell.drivewell.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by abid on 1/22/18.
 */

public class ProfilePresenter implements IProfilePresenter {

    private static final String TAG = "IProfilePresenter";
    public static ProfilePresenter instance;
    private View view;
    private View v;
    private Activity activity;
    private IDatabase mFirestore;
    private User user;
    FirebaseFirestore firebaseFirestore;


    private TextView mProfileName;
    private CircleImageView mProfileImage;
    private AppBarLayout appBarLayout;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    @Override
    public void init(View view, View v, Activity activity) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.v = v;
        this.view = view;
        this.activity = activity;
        mProfileName = view.findViewById(R.id.tvProfileName);
        mProfileImage = view.findViewById(R.id.civProfilePicture);
        appBarLayout=view.findViewById(R.id.ProfileAppbar);
        nestedScrollView=view.findViewById(R.id.nScrollView);
        progressBar=view.findViewById(R.id.pbSignupLoading);


        mFirestore = Firestore.getInstance();

    }

    @Override
    public void setupProfile() {
        appBarLayout.setVisibility(View.INVISIBLE);
        nestedScrollView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        CollectionReference users = firebaseFirestore.collection(FirebaseEndpoints.USERS);
        users.whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> usersList = task.getResult().toObjects(User.class);
                            user = usersList.get(0);
                            setInformation(user);
                            appBarLayout.setVisibility(View.VISIBLE);
                            nestedScrollView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void setInformation(User user) {


        if (user != null) {
            mProfileName.setText(user.getName());
            Picasso.get()
                    .load(user.getProfileImageUrl())
                    .placeholder(R.drawable.no_avatar)
                    .into(mProfileImage);

        }
    }

    public static ProfilePresenter getInstance() {
        return instance == null ? new ProfilePresenter() : instance;
    }
}



