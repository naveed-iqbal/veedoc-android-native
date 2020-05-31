package com.veemed.veedoc.fragments.signup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.CompleteSignupActivity;
import com.veemed.veedoc.databinding.FragmentSignupSecretQuestionsBinding;
import com.veemed.veedoc.fragments.FragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnActionsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecretQuestionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecretQuestionsFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SecretQuestionsFragment.class.getName();
    private FragmentSignupSecretQuestionsBinding b;
    private FragmentInteractionListener mListener;

    public SecretQuestionsFragment() {
        // Required empty public constructor
    }

    public static SecretQuestionsFragment newInstance() {
        SecretQuestionsFragment fragment = new SecretQuestionsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_secret_questions, container, false);
        b.btnNext.setOnClickListener(this);
        b.btnPrevious.setOnClickListener(this);

        b.cbSpecialist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) {
                    b.btnNext.setVisibility(View.VISIBLE);
                    b.btnComplete.setVisibility(View.GONE);
                } else {
                    b.btnNext.setVisibility(View.GONE);
                    b.btnComplete.setVisibility(View.VISIBLE);
                }
            }
        });

        return b.getRoot();
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
            case R.id.btnComplete:
                if(mListener!=null)
                    mListener.onFragmentInteraction(TAG, FragmentInteractionListener.ACTION_TYPE.PROCESS_FINISH);
                break;
        }

    }

    private boolean verifyData() {
        boolean toReturn = true;

        if(b.etSecretQuestion1.getText().toString().isEmpty()) {
            b.etSecretQuestion1.setError(getResources().getString(R.string.can_not_be_empty));
            toReturn = false;
        }

        if(b.etSecretAnswer1.getText().toString().isEmpty()) {
            b.etSecretAnswer1.setError(getResources().getString(R.string.can_not_be_empty));
            toReturn = false;
        }

        if(b.etSecretQuestion2.getText().toString().isEmpty()) {
            b.etSecretQuestion2.setError(getResources().getString(R.string.can_not_be_empty));
            toReturn = false;
        }

        if(b.etSecretAnswer2.getText().toString().isEmpty()) {
            b.etSecretAnswer2.setError(getResources().getString(R.string.can_not_be_empty));
            toReturn = false;
        }

        return  toReturn;
    }

    private void putData() {
        CompleteSignupActivity.userToCreate.setSecretQuestion1(b.etSecretQuestion1.getText().toString());
        CompleteSignupActivity.userToCreate.setSecretAnswer1(b.etSecretAnswer1.getText().toString());
        CompleteSignupActivity.userToCreate.setSecretQuestion2(b.etSecretQuestion2.getText().toString());
        CompleteSignupActivity.userToCreate.setSecretAnswer2(b.etSecretAnswer2.getText().toString());
    }

}
