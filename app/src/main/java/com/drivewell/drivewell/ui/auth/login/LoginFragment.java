package com.drivewell.drivewell.ui.auth.login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.drivewell.drivewell.R;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        ProgressBar progressBar=v.findViewById(R.id.pbLoginProgress);
        FloatingActionButton login=v.findViewById(R.id.fabLoginButton);


        login.setOnClickListener(e->{
            progressBar.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
        });
        return v;
    }
}
