package com.drivewell.drivewell.ui.auth.signup;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.model.User;
import com.drivewell.drivewell.ui.auth.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;


public class SignupFragment extends Fragment implements Validator.ValidationListener {


    private ISignupPresenter signupPresenter;
    private static SignupFragment instance;
    private CardView mLoginBack;
    private FloatingActionButton mSignup;

    @NotEmpty
    @Email
    private TextInputEditText mEmail;
    @NotEmpty
    @Password(min = 6)
    private TextInputEditText mPassword;
    @ConfirmPassword
    private TextInputEditText mConfirmPassword;
    @NotEmpty
    private TextInputEditText mName;
    @NotEmpty
    private TextInputEditText mAge;
    @NotEmpty
    private TextInputEditText mHomeAddress;
    @NotEmpty
    private TextInputEditText mContactNo;
    private Spinner mUserType;

    private String name, age, email, password, confirmPassword, homeAddress, contactNo, userType="Client";
    private Validator validator;

    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance() {

        return new SignupFragment();
    }

    public static SignupFragment getInstance() {

        return instance = (instance == null) ? new SignupFragment() : instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signupPresenter = SignupPresenter.getInstance();
        signupPresenter.init(getActivity());
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signup, container, false);


        mLoginBack = v.findViewById(R.id.fabLoginBack);
        mSignup = v.findViewById(R.id.fabSignupButton);

        mEmail = v.findViewById(R.id.etSignUpEmailAddress);
        mPassword = v.findViewById(R.id.etSignPassword);
        mConfirmPassword = v.findViewById(R.id.etSignUpConfirmPassword);
        mUserType = v.findViewById(R.id.spnrSignUpJobStatus);
        mName = v.findViewById(R.id.etSignUpName);
        mAge = v.findViewById(R.id.etSignUpAge);
        mHomeAddress = v.findViewById(R.id.etSignUpHomeAddress);
        mContactNo = v.findViewById(R.id.etContactNo);


        mSignup.setOnClickListener(e -> {
            validator.validate();
        });

        mLoginBack.setOnClickListener(e -> {
            loadFragment(LoginFragment.getIntstance());
        });


        return v;
    }

    @SuppressLint("WrongConstant")
    private void loadFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, fragment, fragment.toString())
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();
    }


    @Override
    public void onValidationSucceeded() {

        name = mName.getText().toString();
        age = mAge.getText().toString();
        homeAddress = mHomeAddress.getText().toString();
        contactNo = mContactNo.getText().toString();
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();
        //userType=mUserType.getSelectedItem().toString();

        signupPresenter.signUp(new User(FirebaseAuth.getInstance().getUid(),name,age,email,password,confirmPassword,homeAddress,contactNo,userType));


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

}
