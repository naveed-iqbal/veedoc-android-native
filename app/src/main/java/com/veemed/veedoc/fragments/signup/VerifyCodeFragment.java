package com.veemed.veedoc.fragments.signup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.CompleteSignupActivity;
import com.veemed.veedoc.fragments.FragmentInteractionListener;
import com.veemed.veedoc.models.VerificationCode;
import com.veemed.veedoc.repositories.VeeDocUserRepository;
import com.veemed.veedoc.utils.AppPreferencesManager;
import com.veemed.veedoc.utils.Toast;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import com.veemed.veedoc.webservices.UserAPIResponse;
import com.veemed.veedoc.webservices.VeeDocRetrofitDataSource;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerifyCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerifyCodeFragment extends Fragment implements View.OnClickListener, RetrofitCallbackListener<UserAPIResponse> {

    public static final String TAG = VerifyCodeFragment.class.getName();

    private FragmentInteractionListener mListener;
    private Button btnVerify;
    private Button btnResend;
    private EditText etCode;

    public VerifyCodeFragment() {
        // Required empty public constructor
    }


    public static VerifyCodeFragment newInstance() {
        VerifyCodeFragment fragment = new VerifyCodeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_signup_verification, container, false);

        btnVerify = v.findViewById(R.id.btnVerify);
        btnResend = v.findViewById(R.id.btnResend);
        etCode = v.findViewById(R.id.etCode);


        btnVerify.setOnClickListener(this);
        btnResend.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnVerify:
                if(mListener!=null)
                    verifyCode();
                break;

            case R.id.btnResend:
                resendCode();
                break;
        }

    }

    private void verifyCode() {
        String code = etCode.getText().toString();

        if(code.isEmpty()) {
            Toast.makeText(getContext(), "Please enter verification code", Toast.LENGTH_LONG).show();
            return;
        }

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setVerificationKey(code);
        VeeDocUserRepository.getInstance().verifyCode(verificationCode, this, 0);
    }

    private void resendCode() {
        VeeDocUserRepository.getInstance().resendVerificationCode(resendCodeListener, 0);
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
    public void onResponse(Call<UserAPIResponse> call, Response<UserAPIResponse> response, int requestID) {
        if(response.isSuccessful()) {
            CompleteSignupActivity.userToCreate = response.body();
            Toast.makeText(getContext(), R.string.code_verified, Toast.LENGTH_LONG).show();
            AppPreferencesManager.getInstance(getContext()).addOrUpdateBooleanPreference(AppPreferencesManager.BOOLEAN_ENUM_KEY.ACCOUNT_VERIFIED, true);
            mListener.onFragmentInteraction(TAG, FragmentInteractionListener.ACTION_TYPE.GO_FORWARD);
        }
    }

    @Override
    public void onFailure(Call<UserAPIResponse> call, Throwable t, int requestID) {

    }

    private RetrofitCallbackListener<Void> resendCodeListener = new RetrofitCallbackListener<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response, int requestID) {
            Toast.makeText(getContext(), getResources().getString(R.string.code_resent), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t, int requestID) {

        }
    };
}
