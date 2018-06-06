package com.drivewell.drivewell.ui.auth.login;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.drivewell.drivewell.R;
import com.drivewell.drivewell.ui.auth.signup.SignupFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginFragment extends Fragment implements Validator.ValidationListener {

    private ILoginPresenter mLoginPresenter;
    @NotEmpty
    @Email
    private TextInputEditText mLoginEmail;
    @NotEmpty
    private TextInputEditText mLoginPassword;
    private FloatingActionButton mLoginButton;
    private TextView mSignUpButton;
    private ProgressBar mLoginProgressbar;
    private CircleImageView mProfilePic;

    private Validator validator;

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
        validator = new Validator(this);
        validator.setValidationListener(this);


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
            mProfilePic=v.findViewById(R.id.civLoginProfilePicture);


            mLoginPresenter.init(getActivity(),mLoginProgressbar,mLoginButton);

        mLoginEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    Log.i("Email",mLoginEmail.getText().toString());
                    mLoginPresenter.getUserImage(mLoginEmail.getText().toString());
                }
            }
        });

        mLoginButton.setOnClickListener(e->{
            validator.validate();
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

    @Override
    public void onValidationSucceeded() {
        mLoginPresenter.signIn(mLoginEmail.getText().toString().toLowerCase(),mLoginPassword.getText().toString().trim());

    }



    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity().getApplicationContext());

            // Display error messages ;)
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setProfilePicture(String profilePictureUrl){
        Log.i("Image",profilePictureUrl);
        Picasso.get().load(profilePictureUrl)
                .into(mProfilePic);


    }
}
