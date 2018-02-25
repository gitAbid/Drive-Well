package com.drivewell.drivewell.ui.auth.signup;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.drivewell.drivewell.R;
import com.drivewell.drivewell.ui.auth.login.LoginFragment;


public class SignupFragment extends Fragment {


    private static SignupFragment instance;
    private CardView mLoginBack;
    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance() {

        return new SignupFragment();
    }
    public static SignupFragment getInstance() {

        return instance=(instance==null)?new SignupFragment():instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment_signup, container, false);
         mLoginBack=v.findViewById(R.id.fabLoginBack);

         mLoginBack.setOnClickListener(e->{
             loadFragment(LoginFragment.getIntstance());
         });
         return v;
    }

    @SuppressLint("WrongConstant")
    private void loadFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout,fragment,fragment.toString())
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();
    }

}
