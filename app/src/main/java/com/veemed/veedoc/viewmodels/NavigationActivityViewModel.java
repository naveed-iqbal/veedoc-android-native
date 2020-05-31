package com.veemed.veedoc.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.veemed.veedoc.models.SpecialistInformation;
import com.veemed.veedoc.models.User;
import com.veemed.veedoc.repositories.VeeDocUserRepository;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import com.veemed.veedoc.webservices.UserAPIResponse;
import com.veemed.veedoc.webservices.VeeDocRetrofitDataSource;

import retrofit2.Call;
import retrofit2.Response;

public class NavigationActivityViewModel extends ViewModel {

    VeeDocUserRepository userRepo;
    MutableLiveData<Boolean> centerTextViewVisible = new MutableLiveData<>();
    MutableLiveData<String> toolbarTitle = new MutableLiveData<>();
    MutableLiveData<String> toolbarSubtitle = new MutableLiveData<>();
    MutableLiveData<ReturnResponse<User>> userData = new MutableLiveData<>();

    private static VeeDocUserRepository repoInstance;

    public NavigationActivityViewModel() {
        userRepo = VeeDocUserRepository.getInstance();
    } //TODO: Add constructor

    public MutableLiveData<ReturnResponse<User>> fetchUser() {
        userData = new MutableLiveData<>();

        RetrofitCallbackListener<UserAPIResponse> initRegistrationCallback = new RetrofitCallbackListener<UserAPIResponse>() {

            @Override
            public void onResponse(Call<UserAPIResponse> call, Response<UserAPIResponse> response, int requestID) {

                ReturnResponse<User> returnResponse = new ReturnResponse();
                if (response.isSuccessful()) {
                    returnResponse.setValid(true);
                    returnResponse.setReturnObject(response.body().convertoToUserModel());
                    returnResponse.setMessage("success");
                    getSpecialistInformation();
                } else {
                    returnResponse.setValid(false);
                    returnResponse.setReturnObject(null);
                    returnResponse.setMessage(response.errorBody().toString());
                }

                userData.setValue(returnResponse);
            }

            @Override
            public void onFailure(Call<UserAPIResponse> call, Throwable t, int requestID) {
                ReturnResponse<User> returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(t.getMessage());

                userData.setValue(returnResponse);
            }
        };

        // retrofitDataSource.initRegistration(user, initRegistrationCallback, 0);
        userRepo.getUserAPIResponse(initRegistrationCallback, 0);

        return userData;
    }

    public void getSpecialistInformation() {

        RetrofitCallbackListener<SpecialistInformation> initRegistrationCallback = new RetrofitCallbackListener<SpecialistInformation>() {

            @Override
            public void onResponse(Call<SpecialistInformation> call, Response<SpecialistInformation> response, int requestID) {
                ReturnResponse<User> returnResponse = userData.getValue();
                User user = returnResponse.getReturnObject();
                user.setSpecialistInformation(response.body());
                returnResponse.setReturnObject(user);

                userData.setValue(returnResponse);            }

            @Override
            public void onFailure(Call<SpecialistInformation> call, Throwable t, int requestID) {
                ReturnResponse<SpecialistInformation> returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(t.getMessage());
            }
        };

        // retrofitDataSource.initRegistration(user, initRegistrationCallback, 0);
        userRepo.getSpecialistInformation(initRegistrationCallback, 0);
    }




    public MutableLiveData<ReturnResponse<User>> getUser() {
        return userData;
    }


    public LiveData<Boolean> getCenterTextViewVisible() {
        return centerTextViewVisible;
    }

    public void setCenterTextViewVisible(boolean centerTextViewVisible) {
        this.centerTextViewVisible.setValue(centerTextViewVisible);
    }

    public LiveData<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle.setValue(toolbarTitle);
    }

    public LiveData<String> getToolbarSubtitle() {
        return toolbarSubtitle;
    }

    public void setToolbarSubtitle(String toolbarSubtitle) {
        this.toolbarSubtitle.setValue(toolbarSubtitle);
    }


    public void setUser(User user) {
        userRepo.setUser(user);
    }

    public void updateUser(String fullName, String email, String mobileNumber, String zipCode, String address,
                           String deaNumber, int npiNumber, String specialityName, String practiceGroup,
                           String state, String country, String city, String otherAddress) {
        userRepo.updateUser(fullName, email, mobileNumber, zipCode, address, deaNumber, npiNumber,
                specialityName, practiceGroup, state, country, city, otherAddress);
    }

}
