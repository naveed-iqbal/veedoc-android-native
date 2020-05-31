package com.veemed.veedoc.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.viewmodels.ProfileManagementViewModel;

import java.util.ArrayList;

public class LicensedStateRecyclerViewAdapter extends RecyclerView.Adapter<LicensedStateRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<String> statesOptions = new ArrayList<>();
    private static RecyclerViewListener recyclerViewListener;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
   // private NavigationActivityViewModel navigationActivityViewModel;
    private ProfileManagementViewModel profileManagementViewModel;

    private static final String TAG = "LicensedStateRecyclerVi";

    public LicensedStateRecyclerViewAdapter(Context context, RecyclerViewListener recyclerViewListener , ArrayList<String> statesOptions) {
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
        this.statesOptions = statesOptions;
        // this.navigationActivityViewModel = ViewModelProviders.of((FragmentActivity) this.context).get(NavigationActivityViewModel.class);
        this.profileManagementViewModel = ViewModelProviders.of((FragmentActivity) this.context).get(ProfileManagementViewModel.class);
        this.sharedPreferences = context.getSharedPreferences("LICENSED STATES DIALOG PREFERENCES", Context.MODE_PRIVATE);
        this.sharedPreferencesEditor = this.sharedPreferences.edit();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.licensed_state_recyclerview_options, parent, false);
        Log.d(TAG, "onCreateViewHolder: called");
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called");
        holder.checkBox.setText(statesOptions.get(position));


        // if it has already been clicked (default is false)
        boolean needsToBeChecked = sharedPreferences.getBoolean(getItemId(position) + " is Checked", false);
        if (needsToBeChecked){
            // set it checked
            holder.checkBox.setChecked(true);
            // default is error
            String licenseNumberText = sharedPreferences.getString(getItemId(position) + " License Number", "ERROR");
            holder.licenseNumber.setVisibility(View.VISIBLE);
            holder.licenseNumber.setText(licenseNumberText);
        }


        // saved to shared preferences if need to check this
        // CHECK THIS BOX UNLESS WE SPECIFICALLY SAY NOT TO (THIS IS WHY DEFAULT IS TRUE AND I AM REDECLARING ALREADY DECLARED VARIABLE)
        needsToBeChecked = sharedPreferences.getBoolean(getItemId(position) + " is Checked", true);
        if (needsToBeChecked) {

            if (profileManagementViewModel.getUser().getValue().getReturnObject().getSpecialistInformation().getStatesAndNumbers().get(holder.checkBox.getText().toString()) != null) {
                holder.checkBox.setChecked(true);
                holder.licenseNumber.setVisibility(View.VISIBLE);
                holder.licenseNumber.setText(profileManagementViewModel.getUser().getValue().getReturnObject().getSpecialistInformation()
                        .getStatesAndNumbers().get(holder.checkBox.getText().toString()));
                sharedPreferencesEditor.putBoolean(getItemId(position) + " is Checked", true);
            }
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked){
                    holder.licenseNumber.setVisibility(View.VISIBLE);
                    sharedPreferencesEditor.putBoolean(getItemId(position) + " is Checked", true);
                    sharedPreferencesEditor.apply();
                } else {
                    holder.licenseNumber.setVisibility(View.GONE);
                    // sharedPreferencesEditor.remove(getItemId(position) + " is Checked");
                    // set the preferences to reflect that we don't want this checked
                    sharedPreferencesEditor.putBoolean(getItemId(position) + " is Checked", false);
                    sharedPreferencesEditor.apply();
                }
                recyclerViewListener.itemClicked(holder.itemView, position);
            }
        });

       holder.licenseNumber.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
               recyclerViewListener.itemClicked(holder.itemView, position);
               sharedPreferencesEditor.putString( getItemId(position) + " License Number", holder.licenseNumber.getText().toString());
               sharedPreferencesEditor.commit();

           }

       });



    }
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return statesOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ConstraintLayout licesnedStatesRecyclerViewOptions;
        CheckBox checkBox;
        EditText licenseNumber;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: created");
            licesnedStatesRecyclerViewOptions = itemView.findViewById(R.id.licensedStateRecyclerViewOption);
            checkBox = itemView.findViewById(R.id.checkBox_licesnedStateDialog);
            licenseNumber = itemView.findViewById(R.id.editTextNumber_licesnedStateDialog);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (!checkBox.isChecked()){
                checkBox.setChecked(true);
                licenseNumber.setVisibility(View.VISIBLE);
            } else {
                checkBox.setChecked(false);
                licenseNumber.setVisibility(View.GONE);
            }

            recyclerViewListener.itemClicked(view, getLayoutPosition());
        }
    }
}
