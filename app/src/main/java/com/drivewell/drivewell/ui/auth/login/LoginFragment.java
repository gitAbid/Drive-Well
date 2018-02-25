package com.drivewell.drivewell.ui.auth.login;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.drivewell.drivewell.R;
import com.drivewell.drivewell.ui.auth.signup.SignupFragment;

public class LoginFragment extends Fragment {

    private ILoginPresenter mLoginPresenter;
    private TextInputEditText mLoginEmail;
    private TextInputEditText mLoginPassword;
    private FloatingActionButton mLoginButton;
    private TextView mSignUpButton;
    private ProgressBar mLoginProgressbar;

    private static LoginFragment intstance;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter = LoginPresenter.getInstance();


    }

    public static LoginFragment getIntstance() {
        return intstance=(intstance==null)?new LoginFragment():intstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);


            mLoginEmail=v.findViewById(R.id.etLoginEmailAddress);
            mLoginPassword=v.findViewById(R.id.etLoginPassword);
            mLoginProgressbar=v.findViewById(R.id.pbLoginProgress);
            mLoginButton=v.findViewById(R.id.fabLoginButton);
            mSignUpButton=v.findViewById(R.id.tvLoginSignUp);

            mLoginPresenter.init(getActivity(),mLoginProgressbar,mLoginButton);

        mLoginButton.setOnClickListener(e->{
           mLoginPresenter.signIn(mLoginEmail.getText().toString().toLowerCase(),mLoginPassword.getText().toString());
        });

        mSignUpButton.setOnClickListener(e->{
            loadFragment(SignupFragment.getInstance());
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
