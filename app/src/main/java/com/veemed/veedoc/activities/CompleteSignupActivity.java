package com.veemed.veedoc.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.veemed.veedoc.R;
import com.veemed.veedoc.fragments.FragmentInteractionListener;
import com.veemed.veedoc.fragments.signup.AddressFragment;
import com.veemed.veedoc.fragments.signup.SecretQuestionsFragment;
import com.veemed.veedoc.fragments.signup.SpecialistInformationFragment;
import com.veemed.veedoc.fragments.signup.VerifyCodeFragment;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.utils.AppPreferencesManager;
import com.veemed.veedoc.utils.Toast;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import com.veemed.veedoc.webservices.UserAPIResponse;

import retrofit2.Call;
import retrofit2.Response;

public class CompleteSignupActivity extends AppCompatActivity implements FragmentInteractionListener {

    private SpringDotsIndicator springDotsIndicator;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private VerifyCodeFragment verifyCodeFragment;
    private AddressFragment addressFragment;
    private SpecialistInformationFragment specialistInformationFragment;
    private SecretQuestionsFragment secretQuestionsFragment;

    public static UserAPIResponse userToCreate = new UserAPIResponse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_signup);

        springDotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        viewPager = (ViewPager) findViewById(R.id.vpScreens);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        springDotsIndicator.setViewPager(viewPager);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        // get current status of user
        VeeDocRepository.getInstance().getUserAPIResponse(retrofitCallbackListener, 0);

    }

    private RetrofitCallbackListener<UserAPIResponse> retrofitCallbackListener = new RetrofitCallbackListener<UserAPIResponse>() {
        @Override
        public void onResponse(Call<UserAPIResponse> call, Response<UserAPIResponse> response, int requestID) {
            if (response.isSuccessful()) {
                userToCreate = response.body();
                if (userToCreate.getUserStatus().equalsIgnoreCase("Verified"))
                    viewPager.setCurrentItem(1);
                else
                    viewPager.setCurrentItem(0);
            }
        }

        @Override
        public void onFailure(Call<UserAPIResponse> call, Throwable t, int requestID) {

        }
    };

    @Override
    public void onFragmentInteraction(String tag, ACTION_TYPE actionType) {
        if (tag.equals(VerifyCodeFragment.class.getName())) {
            if (actionType == ACTION_TYPE.GO_FORWARD) {
                if (AppPreferencesManager.getInstance(this).findBooleanPrferenceValue(AppPreferencesManager.BOOLEAN_ENUM_KEY.ACCOUNT_VERIFIED, false))
                    viewPager.setCurrentItem(1);
            }
        }
        if (tag.equals(AddressFragment.class.getName())) {
            if (actionType == ACTION_TYPE.GO_BACK) {
                if (!AppPreferencesManager.getInstance(this).findBooleanPrferenceValue(AppPreferencesManager.BOOLEAN_ENUM_KEY.ACCOUNT_VERIFIED, false)) {
                    viewPager.setCurrentItem(0);
                } else {
                    Toast.makeText(this, "Account already verified", Toast.LENGTH_LONG).show();
                }
            } else if (actionType == ACTION_TYPE.GO_FORWARD) {
                viewPager.setCurrentItem(2);
            }
        }
        if (tag.equals(SecretQuestionsFragment.class.getName())) {
            if (actionType == ACTION_TYPE.GO_BACK) {
                viewPager.setCurrentItem(1);
            } else if (actionType == ACTION_TYPE.GO_FORWARD) {
                viewPager.setCurrentItem(3);
            } else if (actionType == ACTION_TYPE.PROCESS_FINISH) {
                uploadData();
            }
        }
        if (tag.equals(SpecialistInformationFragment.class.getName())) {
            if (actionType == ACTION_TYPE.GO_BACK) {
                viewPager.setCurrentItem(2);
            } else if (actionType == ACTION_TYPE.PROCESS_FINISH) {
                uploadData();
            }
        }
    }

    private void uploadData() {
        VeeDocRepository.getInstance().completeRegistration(userToCreate, new RetrofitCallbackListener<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response, int requestID) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_LONG).show();
                    CompleteSignupActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t, int requestID) {

            }
        }, 0);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (verifyCodeFragment == null) verifyCodeFragment = new VerifyCodeFragment();
                    return verifyCodeFragment;
                case 1:
                    if (addressFragment == null) addressFragment = AddressFragment.newInstance();
                    return addressFragment;
                case 2:
                    if (secretQuestionsFragment == null)
                        secretQuestionsFragment = SecretQuestionsFragment.newInstance();
                    return secretQuestionsFragment;
                case 3:
                    if (specialistInformationFragment == null)
                        specialistInformationFragment = SpecialistInformationFragment.newInstance();
                    return specialistInformationFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

    }
}
