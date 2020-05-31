package com.veemed.veedoc.fragments.signup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.CompleteSignupActivity;
import com.veemed.veedoc.databinding.FragmentSignupAddressBinding;
import com.veemed.veedoc.fragments.FragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = AddressFragment.class.getName();

    private FragmentSignupAddressBinding b;

    private FragmentInteractionListener mListener;

    public AddressFragment() {
        // Required empty public constructor
    }

    public static AddressFragment newInstance() {
        AddressFragment fragment = new AddressFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        b = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_address, container, false);
        b.btnNext.setOnClickListener(this);
        b.btnPrevious.setOnClickListener(this);
        return b.getRoot();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnNext:
                if(mListener!=null)
                    if(verifyData()) {
                        putData();
                        mListener.onFragmentInteraction(TAG, FragmentInteractionListener.ACTION_TYPE.GO_FORWARD);
                    } else {

                    }


                break;
            case R.id.btnPrevious:
                if(mListener!=null)
                    mListener.onFragmentInteraction(TAG, FragmentInteractionListener.ACTION_TYPE.GO_BACK);

                break;
        }

    }

    private void putData() {
        CompleteSignupActivity.userToCreate.setCountryId(1);
        CompleteSignupActivity.userToCreate.setZipCode(b.etZipCode.getText().toString());
        CompleteSignupActivity.userToCreate.setAddress(b.etAddress1.getText().toString());
        CompleteSignupActivity.userToCreate.setAddress1(b.etAddress2.getText().toString());
        CompleteSignupActivity.userToCreate.setStateName(b.spState.getSelectedItem().toString());
        CompleteSignupActivity.userToCreate.setCityName(b.etCity.getText().toString());
        CompleteSignupActivity.userToCreate.setEmployer(b.etEmployer.getText().toString());

    }

    private boolean verifyData() {
        boolean toReturn = true;



        return toReturn;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
