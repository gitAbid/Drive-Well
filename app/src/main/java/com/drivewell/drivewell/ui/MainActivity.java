package com.drivewell.drivewell.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.database.IDatabase;
import com.drivewell.drivewell.database.firestore.Firestore;
import com.drivewell.drivewell.repository.IDataProvider;
import com.drivewell.drivewell.repository.MapConditionDataProvider;
import com.drivewell.drivewell.ui.profile.ProfileFragment;
import com.drivewell.drivewell.ui.ranking.DriverRankingFragment;
import com.drivewell.drivewell.ui.roadcondition.RoadConditionMapFragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mProfile,mMap,mRanking;

    IDatabase mFirestore=Firestore.getInstance();

    IDataProvider iDataProvider=new MapConditionDataProvider();

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


        Map<String,String> users1=new HashMap<>();

        users1.put("name","Md Abid Hasan");
        users1.put("Address","Dhaka 1215");
        users1.put("mobile","+8801674530458");

        Map<String,String> users2=new HashMap<>();

        users2.put("name","Md Rasik Hasan");
        users2.put("Address","Uttara 1215");
        users2.put("mobile","");
        Map<String,String> users=new HashMap<>();

        users.put("name","Muntasir Muzib");
        users.put("Address","Dhanmondi 1215");
        users.put("mobile","");


        mFirestore.insertdata(users);
        mFirestore.insertdata(users1);
        mFirestore.insertdata(users2);

        mFirestore.retrieveData(new Object());

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer,fragment,fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
