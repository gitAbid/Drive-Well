package com.drivewell.drivewell.ui.auth.signup;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.model.User;
import com.drivewell.drivewell.ui.auth.login.LoginFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.config.Retropicker;
import de.hdodenhof.circleimageview.CircleImageView;


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

    private CircleImageView mProfile;
    private Uri filePath;
    private String profileImageUrl="";
    private User user;
    Retropicker retropicker;


    public SignupFragment() {
        // Required empty public constructor
        user=new User();
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

        mProfile=v.findViewById(R.id.civSignUpProfilePicture);

        mProfile.setOnClickListener((View e) ->{
            Retropicker.Builder builder =  new Retropicker.Builder(getActivity())
                    .setTypeAction(Retropicker.GALLERY_PICKER) //Para abrir a galeria passe Retropicker.GALLERY_PICKER
                    .setImageName("profile_image.jpg") //Opicional
                    .checkPermission(true);

            builder.enquee(new CallbackPicker() {
                @Override
                public void onSuccess(Bitmap bitmap, String imagePath) {
                    signupPresenter.uploadProfilePicture(Uri.parse(imagePath)); //ImageView do seu aplicativo onde quer exibir a imagem final
                }

                @Override
                public void onFailure(Throwable error) {
                    Log.e("TAG", "error: " + error.getMessage());
                    Log.e("TAG", "error toString: " + error.toString());
                }
            });


            retropicker = builder.create();
            retropicker.open();
        });


        mSignup.setOnClickListener(e -> {
            validator.validate();
        });

        mLoginBack.setOnClickListener(e -> {
            loadFragment(LoginFragment.getIntstance());
        });


        return v;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Call this line fir manager RetroPicker Library
        retropicker.onRequesPermissionResult(requestCode, permissions, grantResults);

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

        user.setName(name);
        user.setAge(age);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);
        user.setContactNo(contactNo);
        user.setEmail(email);
        user.setHomeAddress(homeAddress);
        signupPresenter.signUp(user);


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



    public void setProfilePicture(Uri profilePictureUrl){
        user.setProfileImageUrl(profilePictureUrl.toString());
        Picasso.get()
                .load(profilePictureUrl)
                .into(mProfile);

    }
}
