package com.drivewell.drivewell.ui.profile.information;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.constants.FirebaseEndpoints;
import com.drivewell.drivewell.model.User;
import com.drivewell.drivewell.ui.profile.IProfilePresenter;
import com.drivewell.drivewell.ui.profile.ProfilePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AboutFragment extends Fragment {

    private static final String TAG = "AboutFragment";
    private TextView mEmail,mAge,mPhone,mAddress,mExperience,mUserType;
    private static AboutFragment instance;
    IProfilePresenter profilePresenter;
    FirebaseFirestore firebaseFirestore;
    private ProgressBar mLoading;
    private LinearLayout mAboutSection;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();

        return fragment;
    }

    public static Fragment getInstance() {
        return instance==null?new AboutFragment():instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore=FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_about, container, false);
        mEmail=v.findViewById(R.id.tvAboutEmail);
        mAge=v.findViewById(R.id.tvAboutAge);
        mAddress=v.findViewById(R.id.tvAboutAddress);
        mExperience=v.findViewById(R.id.tvAboutExperience);
        mPhone=v.findViewById(R.id.tvAboutPhone);
        mUserType=v.findViewById(R.id.tvAboutUserType);
        mLoading=v.findViewById(R.id.pbAboutLoading);
        mAboutSection=v.findViewById(R.id.llAboutSection);

        mAboutSection.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.VISIBLE);
        CollectionReference users = firebaseFirestore.collection(FirebaseEndpoints.USERS);
        users.whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> usersList = task.getResult().toObjects(User.class);
                            User user = usersList.get(0);
                            setInformation(user);
                            mLoading.setVisibility(View.GONE);
                            mAboutSection.setVisibility(View.VISIBLE);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        return v;
    }

    private void setInformation(User user) {
        if (user!=null){
            mEmail.setText(user.getEmail());
            mPhone.setText(user.getContactNo());
            mAddress.setText(user.getHomeAddress());
            mAge.setText(user.getAge());
            mUserType.setText(user.getUserType());
        }

    }

}
