package com.drivewell.drivewell.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.ui.profile.ProfileFragment;
import com.drivewell.drivewell.ui.ranking.DriverRankingFragment;
import com.drivewell.drivewell.ui.roadcondition.RoadConditionMapFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoadConditionMapFragment roadConditionMapFragment=new RoadConditionMapFragment();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer,roadConditionMapFragment,roadConditionMapFragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
