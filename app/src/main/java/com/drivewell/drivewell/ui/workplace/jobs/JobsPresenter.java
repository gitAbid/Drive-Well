package com.drivewell.drivewell.ui.workplace.jobs;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.adapter.AdapterJobPosts;
import com.drivewell.drivewell.constants.FirebaseEndpoints;
import com.drivewell.drivewell.model.JobsDataModel;
import com.drivewell.drivewell.repository.IDataProvider;
import com.drivewell.drivewell.repository.JobPostsDataProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * Created by abid on 2/25/18.
 */

public class JobsPresenter implements IJobsPresenter {

    private static final String TAG = "JobsPresenter";
    public static JobsPresenter instance;
    private RecyclerView mJobPostsRecyclerView;
    private SwipeRefreshLayout mRefreshJobs;
    private AdapterJobPosts adapterJobPosts;
    Activity activity;
    IDataProvider dataProvider;

    FirebaseFirestore store;
    @Override
    public void init(View view, Activity activity) {
        store=FirebaseFirestore.getInstance();
        dataProvider= JobPostsDataProvider.getInstance();
        this.activity=activity;
        mJobPostsRecyclerView=view.findViewById(R.id.rcvJobPost);
        mRefreshJobs=view.findViewById(R.id.srlRefreshJobs);
        setupJobPosts();


        mRefreshJobs.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshJobs.setRefreshing(true);
                setupJobPosts();
                mRefreshJobs.setRefreshing(false);
            }
        });
    }

    private void setupJobPosts() {
        CollectionReference users=store.collection(FirebaseEndpoints.JOBS);
        users.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<JobsDataModel> jobs= task.getResult().toObjects(JobsDataModel.class);
                            adapterJobPosts=new AdapterJobPosts(activity.getApplicationContext(),jobs);
                            mJobPostsRecyclerView.setHasFixedSize(true);
                            mJobPostsRecyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
                            mJobPostsRecyclerView.setAdapter(adapterJobPosts);
                            Log.i(TAG,jobs.toString());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    public void setAdapterData(List<JobsDataModel> jobs){
        adapterJobPosts=new AdapterJobPosts(activity.getApplicationContext(),jobs);
    }


    public static JobsPresenter getInstance() {
        return instance==null?new JobsPresenter():instance;
    }


}
