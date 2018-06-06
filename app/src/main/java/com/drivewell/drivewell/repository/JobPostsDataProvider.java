package com.drivewell.drivewell.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.drivewell.drivewell.constants.FirebaseEndpoints;
import com.drivewell.drivewell.model.JobsDataModel;
import com.drivewell.drivewell.model.User;
import com.drivewell.drivewell.ui.workplace.jobs.JobsPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * Created by abid on 3/26/18.
 */

public class JobPostsDataProvider implements IDataProvider {

    private static JobPostsDataProvider instance;
    private static final String TAG ="JobPostsDataProvider" ;
    FirebaseFirestore store;

    public JobPostsDataProvider() {
        store=FirebaseFirestore.getInstance();
    }

    public static JobPostsDataProvider getInstance() {
        return instance==null?new JobPostsDataProvider():instance;
    }

    @Override
    public List<?> getListData() {



        return null;
    }


}
