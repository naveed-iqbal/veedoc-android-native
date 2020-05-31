package com.veemed.veedoc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.veemed.veedoc.R;
import com.veemed.veedoc.utils.AppPreferencesManager;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.utils.Toast;
import com.veemed.veedoc.viewmodels.SignUpActivityViewModel;

public class SignUpActivity extends BaseActivity {

    private TextView appNameTextView;
    private Button signUpButton;
    private TextView haveAccountSignInLayout;
    private Spinner credentialsSpinner, titleSpinner;
    private EditText emailEditText, mobileNumberEditText, lastNameEditText, firstNameEditText, passwordEditText, confirmPasswordEditText;

    private SignUpActivityViewModel signUpActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeInstanceVars();
        initializeVariousTextColors();

        // setting view model
        signUpActivityViewModel = ViewModelProviders.of(this).get(SignUpActivityViewModel.class);

        initializeClicking();
        setSpinners();
    }

    private void initializeInstanceVars() {
        appNameTextView = findViewById(R.id.titleVeeDocTextView_forgotPassword);
        signUpButton = findViewById(R.id.signUpBtn_signUp);
        haveAccountSignInLayout = findViewById(R.id.haveAccountTextView_signUp);
        credentialsSpinner = findViewById(R.id.credentialsSpinner_signUp);
        titleSpinner = findViewById(R.id.titleSpinner_signUp);
        emailEditText = findViewById(R.id.emailEditText_signUp);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText_signUp);
        lastNameEditText = findViewById(R.id.LastNameEditText_signUp);
        firstNameEditText = findViewById(R.id.firstNameEditText_signUp);
        passwordEditText = findViewById(R.id.passwordEditText_signUp);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText_signUp);
    }

    private void initializeClicking() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Make intent to go to sign up page
                signUpActivityViewModel.setEmail(emailEditText.getText().toString());
                signUpActivityViewModel.setMobileNumber(mobileNumberEditText.getText().toString());
                signUpActivityViewModel.setLastName(lastNameEditText.getText().toString());
                signUpActivityViewModel.setFirstName(firstNameEditText.getText().toString());
                signUpActivityViewModel.setPassword(passwordEditText.getText().toString());
                signUpActivityViewModel.setConfirmPassword(confirmPasswordEditText.getText().toString());

                // move this check into sign up activity
                if (signUpActivityViewModel.infoCorrectToSignUp()){

                }
                if(signUpActivityViewModel.validateSignUpInit()) {


                    initiateRegistration();

                }
                /*Intent intent = new Intent(SignUpActivity.this, NavigationBarActivity.class);
                intent.putExtra("signUpActivityUserName", signUpActivityViewModel.getFullName());
                startActivity(intent);*/
            }
        });

        haveAccountSignInLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make intent to go to back to main page
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initiateRegistration() {
        signUpActivityViewModel.initRegistration();
        signUpActivityViewModel.signUpInitResponse.observe(SignUpActivity.this, new Observer<ReturnResponse<String>>() {
            @Override
            public void onChanged(ReturnResponse<String> stringReturnResponse) {
                if(stringReturnResponse.isValid()) {
                    AppPreferencesManager.getInstance(SignUpActivity.this).addOrUpdateBooleanPreference(AppPreferencesManager.BOOLEAN_ENUM_KEY.ACCOUNT_REG_INITIATED, true);
                    Log.e("INIT_SUCCESS", "onChanged: ", null);
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.check_email_verification), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.somrthing_went_wrong), Toast.LENGTH_LONG).show();
                }

                SignUpActivity.this.finish();
            }
        });
    }

    private void setSpinners() {
        ArrayAdapter<CharSequence> credentialsSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.credentials_array_signUp, android.R.layout.simple_spinner_item);
        credentialsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        credentialsSpinner.setAdapter(credentialsSpinnerAdapter);
        credentialsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                signUpActivityViewModel.setCredentials(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // NOTHING
            }
        });

        ArrayAdapter<CharSequence> titleSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.titles_array_signUp, android.R.layout.simple_spinner_item);
        titleSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(titleSpinnerAdapter);
        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                signUpActivityViewModel.setTitle(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // NOTHING
            }
        });

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
        initializeTextViewToColor(haveAccountSignInLayout, getResources().getColor(R.color.colorLightBlueTheme), 17,24 );
    }
}
