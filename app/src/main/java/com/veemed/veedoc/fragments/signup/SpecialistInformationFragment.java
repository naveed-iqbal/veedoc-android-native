package com.veemed.veedoc.fragments.signup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.CompleteSignupActivity;
import com.veemed.veedoc.activities.widgets.KeyValuePair;
import com.veemed.veedoc.databinding.FragmentSignupSpecialistInformationBinding;
import com.veemed.veedoc.fragments.FragmentInteractionListener;
import com.veemed.veedoc.models.LicencedState;
import com.veemed.veedoc.models.SpecialistDetail;
import com.veemed.veedoc.models.Speciality;
import com.veemed.veedoc.models.State;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpecialistInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecialistInformationFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SpecialistInformationFragment.class.getName();


    private List<Speciality> specialities;
    private List<State> states;

    private FragmentSignupSpecialistInformationBinding b;
    private FragmentInteractionListener mListener;

    public SpecialistInformationFragment() {
        // Required empty public constructor
    }

    public static SpecialistInformationFragment newInstance() {
        SpecialistInformationFragment fragment = new SpecialistInformationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_specialist_information, container, false);
        b.btnPrevious.setOnClickListener(this);
        b.btnComplete.setOnClickListener(this);

        VeeDocRepository.getInstance().getStates(statesListener, 0);
        VeeDocRepository.getInstance().getSpecialities(specialitiesListener, 0);

        return b.getRoot();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnPrevious:
                if(mListener!=null)
                    mListener.onFragmentInteraction(TAG, FragmentInteractionListener.ACTION_TYPE.GO_BACK);

                break;
            case R.id.btnComplete:
                if(mListener!=null)
                    if(verifyData()) {
                        putData();
                        mListener.onFragmentInteraction(TAG, FragmentInteractionListener.ACTION_TYPE.PROCESS_FINISH);
                    }
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean verifyData() {
        boolean toReturn = true;

        if(b.etDEANumber.getText().toString().isEmpty()) {
            b.etDEANumber.setError(getResources().getString(R.string.can_not_be_empty));
            toReturn = false;
        }

        if(b.etNPINumber.getText().toString().isEmpty()) {
            b.etNPINumber.setError(getResources().getString(R.string.can_not_be_empty));
            toReturn = false;
        }

        if(b.etPractice.getText().toString().isEmpty()) {
            b.etPractice.setError(getResources().getString(R.string.can_not_be_empty));
            toReturn = false;
        }

        if(b.searchSingleSpinner.getSelectedItems().size()==0) {
            b.searchSingleSpinner.setError(getResources().getString(R.string.select_one_state));
            toReturn = false;
        }

        if(b.etSpeciality.getText().toString().isEmpty() /*|| !specialities.contains(b.etSpeciality.getText().toString())*/) {
            b.etSpeciality.setError(getResources().getString(R.string.invalid_value));
            toReturn = false;
        }

        return  toReturn;
    }

    private void putData() {
        SpecialistDetail specialistDetail = new SpecialistDetail();
        specialistDetail.setDeaNumber(b.etDEANumber.getText().toString());
        specialistDetail.setNpiNumber(b.etNPINumber.getText().toString());
        specialistDetail.setPracticeGroup(b.etPractice.getText().toString());

        String speciality = b.etSpeciality.getText().toString();
        for(Speciality s: specialities) {
            if(s.getName().equalsIgnoreCase(speciality)) {
                specialistDetail.setSpecialityId(s.getId());
            }
        }

        List<KeyValuePair> states = b.searchSingleSpinner.getSelectedItems();
        List<LicencedState> licencedStates = new ArrayList<>();

        for(KeyValuePair p: states) {
            LicencedState licencedState = new LicencedState();
            licencedState.setId(0);
            licencedState.setSpecialistId(0);
            licencedState.setCreatedBy(0);
            licencedState.setIsActive(true);
            licencedState.setUpdatedBy(0);

            licencedState.setStateId(Integer.valueOf(p.getValue()));
            licencedState.setLicenceNumber(Integer.valueOf(p.getLicensedStateNumber()));
            licencedStates.add(licencedState);
        }

        specialistDetail.setLicencedStates(licencedStates);
        CompleteSignupActivity.userToCreate.setSpecialist(specialistDetail);

    }

    private RetrofitCallbackListener<List<State>> statesListener = new RetrofitCallbackListener<List<State>>() {
        @Override
        public void onResponse(Call<List<State>> call, Response<List<State>> response, int requestID) {
            if(response.isSuccessful()) {
                states = response.body();
                ArrayList<KeyValuePair> options = new ArrayList<>();
                for(State s: states) {
                    options.add(new KeyValuePair(s.getStateName(), s.getId()+""));
                }

                b.searchSingleSpinner.setItems(options);
            }
        }

        @Override
        public void onFailure(Call<List<State>> call, Throwable t, int requestID) {

        }
    };

    private RetrofitCallbackListener<List<Speciality>> specialitiesListener = new RetrofitCallbackListener<List<Speciality>>() {
        @Override
        public void onResponse(Call<List<Speciality>> call, Response<List<Speciality>> response, int requestID) {
            if(response.isSuccessful()) {
                specialities = response.body();
                b.etSpeciality.setAdapter(new ArrayAdapter<Speciality>(getContext(), android.R.layout.simple_list_item_1, specialities));
            }
        }

        @Override
        public void onFailure(Call<List<Speciality>> call, Throwable t, int requestID) {

        }
    };


}
