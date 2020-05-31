package com.veemed.veedoc.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.databinding.ProfileManagementScrollableLayoutBinding;
import com.veemed.veedoc.models.User;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.veemed.veedoc.viewmodels.ProfileManagementViewModel;

public class ProfileManagementFragment extends Fragment implements RecyclerViewListener {

    private NavigationActivityViewModel navigationActivityViewModel;

    private ProfileManagementViewModel profileManagementViewModel;

    private EditText fullNameEditText, emailEditText, mobileNumberEditText, zipCodeEditText, addressEditText,
            deaNumberEditText, npiNumberEditText, practiceGroupEditText, cityEditText, otherAddressEditText;

    private TextView specialityNameAnswerText, countryTextView, licesnedStateTextView;

    private Button saveButton;

    private Spinner statesSpinner;

    private ProfileManagementScrollableLayoutBinding binding;
    public static ProfileManagementFragment newInstance() {
        return new ProfileManagementFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.profile_management_layout, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_management_scrollable_layout, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // passing getActivity() to allow communication with navigation activity class
        profileManagementViewModel = ViewModelProviders.of(getActivity()).get(ProfileManagementViewModel.class);
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        navigationActivityViewModel.setToolbarTitle("Profile Management");
        navigationActivityViewModel.setToolbarSubtitle(null);

        initWidgets();
        setObservers();
        setClicker();
    }

    private void setObservers() {
        profileManagementViewModel.fetchUser().observe(this, new Observer<ReturnResponse<User>>() {
            @Override
            public void onChanged(ReturnResponse<User> user) {
                binding.setUser(user.getReturnObject());
            }
        });

        // whenever you start profile management fragment, reset the specialist informaiton to just
        // that of what the user has saved, in case they did not save something that they checked earlier
        // TODO NVD profileManagementViewModel.resetSpecialistInformationStateData();

        profileManagementViewModel.getLicesnedStateText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String licensedStatesText) {
                licesnedStateTextView.setText(licensedStatesText);
            }
        });
    }

    private void initWidgets() {
        fullNameEditText = binding.fullNameEditTextProfileManagement;
        emailEditText = binding.emailEditTextProfileManagement;
        mobileNumberEditText = binding.mobileNumberEditTextProfileManagement;
        zipCodeEditText = binding.zipCodeEditTextProfileManagement;
        addressEditText = binding.addressEditTextProfileManagement;
        deaNumberEditText = binding.deaEditTextProfileManagement;
        npiNumberEditText = binding.npiEditTextProfileManagement;
        specialityNameAnswerText = binding.specialtyNameAnswerTextViewProfileManagement;
        practiceGroupEditText = binding.pracGroupEditTextProfileManagement;
        saveButton = binding.saveButtonProfileManagement;
        countryTextView = binding.countryTextViewProfileManagement;
        licesnedStateTextView = binding.licensedStateTextViewAnswerProfileManagement;
        cityEditText = binding.cityEditTextProfileManagement;
        otherAddressEditText = binding.otherAddressEditTextProfileManagement;

        setSpinners();
    }

    private void setClicker() {
        saveButton = binding.saveButtonProfileManagement;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserData();
            }
        });

        licesnedStateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LicensedStatesDialogFragment licensedStatesDialogFragment = LicensedStatesDialogFragment.newInstance();
                licensedStatesDialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });
    }

    private void updateUserData() {
        String fullName = fullNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String mobileNumber = mobileNumberEditText.getText().toString();
        String zipCode = zipCodeEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String deaNumber = deaNumberEditText.getText().toString();
        int npiNumber = Integer.parseInt(npiNumberEditText.getText().toString());
        String specialityName = specialityNameAnswerText.getText().toString();
        String practiceGroup = practiceGroupEditText.getText().toString();
        String state = statesSpinner.getSelectedItem().toString();
        String country = countryTextView.getText().toString();
        String city = cityEditText.getText().toString();
        String otherAddress = otherAddressEditText.getText().toString();

        profileManagementViewModel.updateUser(fullName, email, mobileNumber, zipCode, address,
                deaNumber, npiNumber, specialityName, practiceGroup, state, country, city, otherAddress);

    }

    private void setSpinners() {
        statesSpinner = binding.stateSpinnerProfileManagement;
        ArrayAdapter<CharSequence> statesSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.states_list, R.layout.profile_managment_custom_spinner_item);
        statesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statesSpinner.setAdapter(statesSpinnerAdapter);
        // set the selection to the person's current state
       // statesSpinner.setSelection(navigationActivityViewModel.findCurrentStateIndex(Arrays.asList(getResources().getStringArray(R.array.states_list))));
        // statesSpinner.setSelection(profileManagementViewModel.findCurrentStateIndex(Arrays.asList(getResources().getStringArray(R.array.states_list))));
    }

    @Override
    public void itemClicked(View view, int position) {
        ((CheckBox) view.findViewById(R.id.checkBox_licesnedStateDialog)).setChecked(true);
        view.findViewById(R.id.editTextNumber_licesnedStateDialog).setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        // clear shared preferences so that when we next open states dialog, there is only the ones the user has saved
        super.onPause();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LICENSED STATES DIALOG PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.apply();
    }
}
