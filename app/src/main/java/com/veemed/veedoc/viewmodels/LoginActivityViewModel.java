package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import com.veemed.veedoc.webservices.TokenResponse;
import com.veemed.veedoc.webservices.UserAPIResponse;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivityViewModel extends ViewModel {

    private VeeDocRepository userRepo;
    private LiveData<ReturnResponse<TokenResponse>> loginSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<UserAPIResponse> userInfoLiveData;

    public LoginActivityViewModel() {
        userRepo = VeeDocRepository.getInstance();
    }

    public void fetchUserInfo() {

    }

    public MutableLiveData<UserAPIResponse> getUserInfoLiveData() {
        userInfoLiveData = new MutableLiveData<>();
        userRepo.getUserAPIResponse(userInfoCallback, 0);
        return userInfoLiveData;
    }

    public void tryToLogin(String email, String password){
        String encodedEmail = Utility.encodeText(email);
        String encodedPassword = Utility.encodeText(password);
        loginSuccessLiveData = userRepo.tryToLogin(encodedEmail, encodedPassword);
    }

    public boolean errorLoggingIn(String email, String password){

        return email.trim().isEmpty() || password.trim().isEmpty() || !Utility.isPossibleEmail(email);

    }

    public String getErrorToast(String email, String password) {
        // if email is empty
        if (email.isEmpty()){
            return "Failed\nEmail is required";
        } else if (password.isEmpty()){ // if password is empty
           return "Failed\nPassword is required";
        } else if (!Utility.isPossibleEmail(email)){ // if email is impossible to have
            return "Failed\nEmail is invalid";
        }

        return "ERROR";
    }

    public LiveData<ReturnResponse<TokenResponse>> getLoginSuccessful() {
        return loginSuccessLiveData;
    }

    private RetrofitCallbackListener<UserAPIResponse> userInfoCallback = new RetrofitCallbackListener<UserAPIResponse>() {
        @Override
        public void onResponse(Call<UserAPIResponse> call, Response<UserAPIResponse> response, int requestID) {
            if(response.isSuccessful()) {
                userInfoLiveData.setValue(response.body());
            }
        }

        @Override
        public void onFailure(Call<UserAPIResponse> call, Throwable t, int requestID) {

        }
    };
    public void resetLoginStatusBoolean() {
        userRepo.resetLoginStatusBoolean();
    }
}
