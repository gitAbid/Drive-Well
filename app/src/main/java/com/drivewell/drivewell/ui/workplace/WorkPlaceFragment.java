package com.drivewell.drivewell.ui.workplace;



import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.adapter.ViewPagerAdapter;
import com.drivewell.drivewell.ui.MainActivity;
import com.drivewell.drivewell.ui.workplace.drivers.DriverRankingFragment;
import com.drivewell.drivewell.ui.workplace.jobs.DialogAddJob;
import com.drivewell.drivewell.ui.workplace.jobs.JobsFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkPlaceFragment extends Fragment {

    private static WorkPlaceFragment instance;
    private TabLayout mWorkplaceTab;
    private TabItem mDriversTab,mJobsTab;
    private FloatingActionButton mAddJobs;


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
        mAddJobs=v.findViewById(R.id.fabAddJobs);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        adapter.addFragment(JobsFragment.getInstance(), "Jobs");
        adapter.addFragment(DriverRankingFragment.getInstance(), "Drivers");


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAddJobs.getLayoutParams();
        params.setBehavior(null);
        mAddJobs.requestLayout();

        mAddJobs.setOnClickListener(e->{

            DialogAddJob dialogAddJob=new DialogAddJob(getContext());
            dialogAddJob.show();

            Toast.makeText(getContext(),"Clicked ",Toast.LENGTH_SHORT).show();
        });

       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {

               if (position==0){
                   mAddJobs.setVisibility(View.VISIBLE);
               }else {
                   mAddJobs.setVisibility(View.GONE);

               }
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });

        adapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        return v;

    }



}




