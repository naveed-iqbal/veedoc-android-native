package com.veemed.veedoc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.NavigationBarActivity;
import com.veemed.veedoc.models.ChangePassword;
import com.veemed.veedoc.utils.AppPreferencesManager;
import com.veemed.veedoc.utils.Toast;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;

public class ChangePasswordFragment extends Fragment {

    private NavigationActivityViewModel navigationActivityViewModel;
    View v;
    private EditText tvCurrentPassword, tvNewPassword, tvConfirmPassword;
    private Button button;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return v = inflater.inflate(R.layout.change_password_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);

        navigationActivityViewModel.setToolbarTitle("Change Password");
        navigationActivityViewModel.setToolbarSubtitle(null);
        init();
        initObservers();
    }

    private void initObservers() {
        navigationActivityViewModel.getChangePasswordViewModel().observe(this, new Observer<ChangePassword>() {
            @Override
            public void onChanged(ChangePassword changePassword) {
                if(changePassword!=null) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.password_changed), Toast.LENGTH_LONG).show();
                    ((NavigationBarActivity)getContext()).goBackToLogin();
                } else {
                    Toast.makeText(getContext(), "Some error occurred!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        tvCurrentPassword = v.findViewById(R.id.currentPasswordEditText);
        tvNewPassword = v.findViewById(R.id.newPasswordEditText);
        tvConfirmPassword = v.findViewById(R.id.confirmPasswordEditText);

        button = v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

    }

    private void changePassword() {
        String encodedSavedPassword = AppPreferencesManager.getInstance(getContext()).findStringPrferenceValue(AppPreferencesManager.STRING_ENUM_KEY.PASSWORD, "");

        ChangePassword changePassword = new ChangePassword();

        String currentPass = tvCurrentPassword.getText().toString();
        String newPassword = tvNewPassword.getText().toString();
        String confirmPassword = tvConfirmPassword.getText().toString();

        if(Utility.encodeText(currentPass).equals(encodedSavedPassword)) {
            if (newPassword.equals(confirmPassword)) {
                if(!newPassword.isEmpty()) {
                    changePassword.setVerificationKey(currentPass);
                    changePassword.setCurrentPassword(currentPass);
                    changePassword.setNewPassword(newPassword);
                    navigationActivityViewModel.changePassword(changePassword);
                } else {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.error_empty_password), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.error_not_similar_password), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.incorrent_password), Toast.LENGTH_LONG).show();
        }
    }
}
