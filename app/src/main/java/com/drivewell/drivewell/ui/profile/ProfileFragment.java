package com.drivewell.drivewell.ui.profile;



import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.adapter.ViewPagerAdapter;
import com.drivewell.drivewell.ui.profile.information.AboutFragment;
import com.drivewell.drivewell.ui.profile.information.HistoryFragment;
import com.drivewell.drivewell.ui.profile.information.ReviewFragment;
import com.drivewell.drivewell.ui.workplace.drivers.DriverRankingFragment;
import com.drivewell.drivewell.ui.workplace.jobs.JobsFragment;

public class ProfileFragment extends Fragment {


    private static ProfileFragment instance;
    private IProfilePresenter profilePresenter;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {

        return new ProfileFragment();
    }

    public static Fragment getInstance() {
        return instance=(instance==null)?newInstance():instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilePresenter=ProfilePresenter.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        profilePresenter.init(v,getActivity());
        profilePresenter.setupProfile();

        ViewPager viewPager = v.findViewById(R.id.vpProfileInformations);
        TabLayout tabLayout = v.findViewById(R.id.tblProfileInformations);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);



        adapter.addFragment(AboutFragment.getInstance(), "About");
        adapter.addFragment(HistoryFragment.getInstance(), "History");
        adapter.addFragment(ReviewFragment.getInstance(), "Review");

        adapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }

}
