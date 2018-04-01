package com.drivewell.drivewell.ui.dashboard;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.coremodule.GForceMeterFragment;
import com.drivewell.drivewell.ui.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;


public class DashboardFragment extends Fragment {



    int i;
    public DashboardFragment() {
        // Required empty public constructor
    }


    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);


       /* loadFragment(GForceMeterFragment.getInstance());*/

        Button signOut=v.findViewById(R.id.btSignOut);

        signOut.setOnClickListener(e->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
        return v;
    }

    private void loadFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.dashboardContainer,fragment,fragment.toString())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


}
