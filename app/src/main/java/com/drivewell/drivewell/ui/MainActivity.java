package com.drivewell.drivewell.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.ui.profile.ProfileFragment;
import com.drivewell.drivewell.ui.ranking.DriverRankingFragment;
import com.drivewell.drivewell.ui.roadcondition.RoadConditionMapFragment;

public class MainActivity extends AppCompatActivity {

    private Button mProfile,mMap,mRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfile=findViewById(R.id.btProfile);
        mMap=findViewById(R.id.btMap);
        mRanking=findViewById(R.id.btRanking);

        mProfile.setOnClickListener(e->{
                loadFragment(new ProfileFragment());
        });

        mMap.setOnClickListener(e->{
            loadFragment(new RoadConditionMapFragment());
        });

        mRanking.setOnClickListener(e->{
            loadFragment(new DriverRankingFragment());
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer,fragment,fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
