package com.veemed.veedoc.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.LicensedStateRecyclerViewAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.veemed.veedoc.viewmodels.ProfileManagementViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class LicensedStatesDialogFragment extends DialogFragment implements RecyclerViewListener {
    //private NavigationActivityViewModel navigationActivityViewModel;
    private ProfileManagementViewModel profileManagementViewModel;
    private LicensedStateRecyclerViewAdapter licensedStateRecyclerViewAdapter;
    private RecyclerView licesnedStateRecyclerView;
    private CheckBox checkBox;
    private EditText licenseNumberEditText;

    public static LicensedStatesDialogFragment newInstance() {
        return new LicensedStatesDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertStyle);
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

        profileManagementViewModel = ViewModelProviders.of(getActivity()).get(ProfileManagementViewModel.class);

        View view = layoutInflater.inflate(R.layout.licesned_state_dialog, null);
       initializeRecyclerView(view);

        builder.setView(view);

        builder.setTitle("Select Licensed States")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // empty
                    }

                });

        return builder.create();
    }

    // Credit to: https://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked
    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null){
            Button saveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if (navigationActivityViewModel.notAllLicenseNumbersEntered()){
                        Toast.makeText(getContext(), "Failed\nEnter a " + navigationActivityViewModel.getStateWithoutLicenseNumber() + " License Number", Toast.LENGTH_SHORT).show();
                    } else {
                        navigationActivityViewModel.setLicensedStateText();
                        alertDialog.dismiss();
                    } */
                    if (profileManagementViewModel.notAllLicenseNumbersEntered()){
                        Toast.makeText(getContext(), "Failed\nEnter a " + profileManagementViewModel.getStateWithoutLicenseNumber() + " License Number", Toast.LENGTH_SHORT).show();
                    } else {
                        profileManagementViewModel.setLicensedStateText();
                        alertDialog.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void itemClicked(View view, int position) {
        // initialize items for use outside this function
         checkBox = view.findViewById(R.id.checkBox_licesnedStateDialog);
         licenseNumberEditText = view.findViewById(R.id.editTextNumber_licesnedStateDialog);


        if (checkBox.isChecked()){
            if (editTextEmpty(licenseNumberEditText)) {
                Toast.makeText(getActivity(), "Success\nEnter " + checkBox.getText().toString() + " License Number", Toast.LENGTH_SHORT).show();
                profileManagementViewModel.addLicensedStateStateAndNumber(checkBox.getText().toString(), licenseNumberEditText.getText().toString());
            } else {
                Toast.makeText(getActivity(), "Data entered", Toast.LENGTH_SHORT).show();
                profileManagementViewModel.addLicensedStateStateAndNumber(checkBox.getText().toString(), licenseNumberEditText.getText().toString());
            }
        } else {
            profileManagementViewModel.removeLicensedStateAndNumber(checkBox.getText().toString());
        }


    }

    private void initializeRecyclerView(View view) {

        licensedStateRecyclerViewAdapter = new LicensedStateRecyclerViewAdapter(getContext(), this, new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.states_list))));
        licesnedStateRecyclerView = view.findViewById(R.id.licensedState_recyclerView);
        licesnedStateRecyclerView.setAdapter(licensedStateRecyclerViewAdapter);
        licesnedStateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(licesnedStateRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        licesnedStateRecyclerView.addItemDecoration(dividerItemDecoration);

    }

    private boolean editTextEmpty(EditText editText){
        return editText.getText().toString().isEmpty();
    }


}
