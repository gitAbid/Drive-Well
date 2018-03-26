package com.drivewell.drivewell.ui.workplace.jobs;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.ui.MainActivity;


public class JobsFragment extends Fragment {


    private static JobsFragment instance;
    IJobsPresenter jobsPresenter;

    public JobsFragment() {
        // Required empty public constructor
    }


    public static JobsFragment getInstance() {
        return instance=instance==null?newInstance():instance;
    }

    public static JobsFragment newInstance() {
        return new JobsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobsPresenter=JobsPresenter.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_jobs, container, false);

        jobsPresenter.init(v,getActivity());


        return v;
    }

}
