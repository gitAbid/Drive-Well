package com.drivewell.drivewell.ui.workplace;



import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.adapter.ViewPagerAdapter;
import com.drivewell.drivewell.ui.MainActivity;
import com.drivewell.drivewell.ui.workplace.drivers.DriverRankingFragment;
import com.drivewell.drivewell.ui.workplace.jobs.JobsFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkPlaceFragment extends Fragment {

    private static WorkPlaceFragment instance;
    private TabLayout mWorkplaceTab;
    private TabItem mDriversTab,mJobsTab;


    public WorkPlaceFragment() {
        // Required empty public constructor
    }

    public static WorkPlaceFragment getInstance() {
        return instance=instance==null?newInstance():instance;
    }

    public static WorkPlaceFragment newInstance() {
        return new WorkPlaceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_work_place, container, false);

        ViewPager viewPager = v.findViewById(R.id.vpWorkplace);
        TabLayout tabLayout = v.findViewById(R.id.tblWorkplace);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);



        adapter.addFragment(JobsFragment.getInstance(), "Jobs");
        adapter.addFragment(DriverRankingFragment.getInstance(), "Drivers");

        adapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        return v;

    }



}




