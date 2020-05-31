package com.veemed.veedoc.activities;

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

import androidx.lifecycle.ViewModelProviders;

import com.veemed.veedoc.R;
import com.veemed.veedoc.viewmodels.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends BaseActivity {

    private TextView appNameTextView;
    private Button resetPasswordButton;
    private TextView goBackSignInLayout;
    private EditText emailEditText;

    private ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initializeAllInstanceVars();
        initializeVariousTextColors();

        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);

        initializeClicking();
    }

    private void initializeAllInstanceVars(){
        appNameTextView = findViewById(R.id.titleVeeDocTextView_forgotPassword);
        resetPasswordButton = findViewById(R.id.resetButton_forgotPassword);
        goBackSignInLayout = findViewById(R.id.signInTextView_forgotPassword);
        emailEditText = findViewById(R.id.emailEditText_forgotPassword);

    }

    private void initializeClicking() {
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToResetPassword();
            }
        });

        goBackSignInLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent to go back to main activity
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void tryToResetPassword() {
        // get the entered email
        String emailEntered = emailEditText.getText().toString();
        // if email not entered / is not possible email address / account does not exist
        if (forgotPasswordViewModel.cannotResetPassword(emailEntered)){
            displayToast(forgotPasswordViewModel.getErrorToast(emailEntered));
        } else { // valid email that exists. update password
            forgotPasswordViewModel.updatePassword();
            // HERE FOR TESTING
            displayToast("Password Reset");
        }
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

    private void initializeVariousTextColors(){
        initializeTextViewToColor(appNameTextView, getResources().getColor(R.color.colorLightBlueTheme), 3, 6);
        initializeTextViewToColor(goBackSignInLayout, getResources().getColor(R.color.colorLightBlueTheme), 11,18);
    }

    private void displayToast(String text){
        Toast.makeText(ForgotPasswordActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
