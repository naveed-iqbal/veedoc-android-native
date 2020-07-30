package com.veemed.veedoc.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.loadingview.LoadingDialog;
import com.veemed.veedoc.R;
import com.veemed.veedoc.utils.AppPreferencesManager;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.viewmodels.LoginActivityViewModel;
import com.veemed.veedoc.webservices.TokenResponse;
import com.veemed.veedoc.webservices.UserAPIResponse;

public class LoginActivity extends AppCompatActivity {

    // instance of view model
    LoginActivityViewModel loginActivityViewModel;


    // widgets
    private TextView appNameTextView;
    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private TextView forgotPasswordTextView, noAccountSignUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize widgets
        initWidgets();

        // set colors of text
        initializeAppNameColor();
        initializeVariousTextColors();

        // setting view model
        loginActivityViewModel = ViewModelProviders.of(this).get(LoginActivityViewModel.class);

        // initialize clicking of various elements
        initializeClicking();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initWidgets(){
        appNameTextView = findViewById(R.id.titleVeeDocTextView_main);
        emailEditText = findViewById(R.id.emailEditText_main);
        passwordEditText = findViewById(R.id.passwordEditText_main);
        signInButton = findViewById(R.id.signInButton_main);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView_main);
        noAccountSignUpTextView = findViewById(R.id.noAccountTextView_main);

    }

    private void proceed() {
        loginActivityViewModel.getUserInfoLiveData().observe(this, new Observer<UserAPIResponse>() {
            @Override
            public void onChanged(UserAPIResponse userAPIResponse) {
                if(userAPIResponse.getUserStatus().equalsIgnoreCase("Completed")) {
                    Utility.user = userAPIResponse;
                    Intent intent = new Intent(LoginActivity.this, NavigationBarActivity.class);
                    startActivity(intent);
                } else if(userAPIResponse.getUserStatus().equalsIgnoreCase("PendingForApproval")) {
                    com.veemed.veedoc.utils.Toast.makeText(getApplicationContext(), "Your account is awaiting approval", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(LoginActivity.this, CompleteSignupActivity.class));
                }
            }
        });
        loginActivityViewModel.fetchUserInfo();


    }

    private void initializeAppNameColor(){
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(appNameTextView.getText().toString());
        // Set color to light blue theme color
        ForegroundColorSpan lightBlue = new ForegroundColorSpan(getResources().getColor(R.color.colorLightBlueTheme));
        // inclusive means whatever we add will be colored as well. exclusive means it will not be colored
        // first inclusive / exclusive refers to adding to start. second inclusive / exclusive refers to adding to end
        spannableStringBuilder.setSpan(lightBlue, 3, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // set the app name to this new colored spannableStringBuilder
        appNameTextView.setText(spannableStringBuilder);
    }

    private void initializeTextViewToColor(TextView textView, int color, int startIndex, int endIndex) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText().toString());
        // Set color to light blue theme color
        ForegroundColorSpan lightBlue = new ForegroundColorSpan(color);
        // inclusive means whatever we add will be colored as well. exclusive means it will not be colored
        // first inclusive / exclusive refers to adding to start. second inclusive / exclusive refers to adding to end
        spannableStringBuilder.setSpan(lightBlue, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // set the app name to this new colored spannableStringBuilder
        textView.setText(spannableStringBuilder);

    }

    private void initializeVariousTextColors() {

        initializeTextViewToColor(appNameTextView, getResources().getColor(R.color.colorLightBlueTheme), 3, 6);
        initializeTextViewToColor(noAccountSignUpTextView, getResources().getColor(R.color.colorLightBlueTheme), 22, 30);
    }

    private void tryToLogin(String email, String password) {

        if (loginActivityViewModel.errorLoggingIn(email, password)) {
            displayToast(loginActivityViewModel.getErrorToast(email, password));
        } else { // have entered password & possible valid email
            loginActivityViewModel.tryToLogin(email, password);
            // set observers
            setObservers();
        }

    }

    private void displayToast(String text){
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void initializeClicking() {

        // if sign in button is clicked
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get entered email and password
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                tryToLogin(email, password);
            }
        });

        // if forgot password is clicked
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CompleteSignupActivity.class); // ForgotPasswordActivity
                startActivity(intent);

            }
        });

        // if sign up is clicked
        noAccountSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setObservers() {
        loginActivityViewModel.resetLoginStatusBoolean(); // before setting up the observer, success login boolean to null, so that previous attempt has no impact on this attempt
        loginActivityViewModel.getLoginSuccessful().observe(this, new Observer<ReturnResponse<TokenResponse>>() {
            @Override
            public void onChanged(ReturnResponse<TokenResponse> loginSuccessful) {
                if (loginSuccessful != null) { // i.e. if there has been an attempt to login at least
                    if (loginSuccessful.isValid()) {
                        // Saving the API credentials into preferences
                        TokenResponse tokenResponse = loginSuccessful.getReturnObject();
                        AppPreferencesManager preferencesManager = AppPreferencesManager.getInstance(LoginActivity.this);
                        preferencesManager.addOrUpdateStringPreference(AppPreferencesManager.STRING_ENUM_KEY.ACCESS_TOKEN, tokenResponse.getAccess_token());
                        preferencesManager.addOrUpdateIntegerPreference(AppPreferencesManager.INT_ENUM_KEY.TOKEN_EXPIRY, tokenResponse.getExpires_in());
                        preferencesManager.addOrUpdateStringPreference(AppPreferencesManager.STRING_ENUM_KEY.REFRESH_TOKEN, tokenResponse.getRefresh_token());

                        String encodedEmail = Utility.encodeText(emailEditText.getText().toString());
                        String encodedPassword = Utility.encodeText(passwordEditText.getText().toString());
                        preferencesManager.addOrUpdateStringPreference(AppPreferencesManager.STRING_ENUM_KEY.USERNAME, encodedEmail);
                        preferencesManager.addOrUpdateStringPreference(AppPreferencesManager.STRING_ENUM_KEY.PASSWORD, encodedPassword);


                        // init global bearer token
                        Utility.initBearerToken(getApplicationContext());
                        // proceed to next screen
                        proceed();
                    } else {

                        displayToast("Failed\nInvalid Email or Password");
                    }
                }
            }
        });


    }


}
