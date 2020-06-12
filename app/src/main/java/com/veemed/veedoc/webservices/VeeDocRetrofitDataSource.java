package com.veemed.veedoc.webservices;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.veemed.CallActionsModel;
import com.veemed.veedoc.models.CallAcceptAPIResponse;
import com.veemed.veedoc.models.ChangePassword;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.DeferResponseModel;
import com.veemed.veedoc.models.EndpointStatus;
import com.veemed.veedoc.models.Message;
import com.veemed.veedoc.models.NewMessageBody;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.models.UserAPIRequest;
import com.veemed.veedoc.models.event.OffDayModel;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.models.Speciality;
import com.veemed.veedoc.models.State;
import com.veemed.veedoc.models.VerificationCode;
import com.veemed.veedoc.models.event.ScheduledEvent;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.models.event.CalendarEvent;
import com.veemed.veedoc.models.Endpoint;
import com.veemed.veedoc.models.Session;
import com.veemed.veedoc.models.SpecialistInformation;
import com.veemed.veedoc.models.User;

import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class VeeDocRetrofitDataSource {

    private static final String TAG = "VeeDocUserRDS";

    private static VeeDocRetrofitDataSource rdsInstance;
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<List<Endpoint>> endpoints = new MutableLiveData<>();
    private MutableLiveData<List<Session>> lastYearSessions = new MutableLiveData<>();
    private MutableLiveData<List<Session>> lastWeekSessions = new MutableLiveData<>();
    private MutableLiveData<List<Session>> lastMonthSessions = new MutableLiveData<>();
    private MutableLiveData<LinkedHashMap<String, String>> licensedStateAndNumbersData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginSuccessful = new MutableLiveData<>();

    // Singleton
    public static VeeDocRetrofitDataSource getInstance(){
        if (rdsInstance == null){
            rdsInstance = new VeeDocRetrofitDataSource();
        }
        return rdsInstance;
    }



    public boolean accountWithEmailExists(String encodedEmail) {
        // TODO: API verification of email.
        String email = Utility.decodeText(encodedEmail);
        return email.equals("drsmith@veemed.com"); // place holder
    }

    public void updatePassword() {
        // TODO: do updating password here
    }

    public void signUserUp(String email, String mobileNumber, String lastName, String firstName, String credentials, String title, String password) {

        User userBeingSignedUp = new User(email, mobileNumber, lastName, firstName, credentials, title, password);

    }








    public void setUser(User user) {
        this.user.setValue(user);
    }

    public void updateUser(String fullName, String email, String mobileNumber, String zipCode, String address,
                           String deaNumber, int npiNumber, String specialityName, String practiceGroup,
                           String state, String country, String city, String otherAddress) {
        this.user.getValue().setFullName(fullName);
        this.user.getValue().setEmail(email);
        this.user.getValue().setMobileNumber(mobileNumber);
        this.user.getValue().getLocation().setZipCode(zipCode);
        this.user.getValue().getLocation().setAddress(address);
        this.user.getValue().getSpecialistInformation().setDeaNumber(deaNumber);
        this.user.getValue().getSpecialistInformation().setNpiNumber(npiNumber);
        this.user.getValue().getSpecialistInformation().setSpecialityName(specialityName);
        this.user.getValue().getSpecialistInformation().setPracticeGroup(practiceGroup);
        this.user.getValue().getLocation().setState(state);
        this.user.getValue().getLocation().setCountry(country);
        this.user.getValue().getLocation().setCity(city);
        this.user.getValue().getLocation().setOtherAddress(otherAddress);
        // TODO: Update user on server

    }

    public void updateUserState(String state) {
        this.user.getValue().getLocation().setState(state);
    }

    public void removeLicensedStateAndNumber(String state) {
        licensedStateAndNumbersData.getValue().remove(state);
    }

    public void updateUserSpecialistInfoLicensedStateAndNumber(){
        this.user.getValue().getSpecialistInformation().setStatesAndNumbers(licensedStateAndNumbersData.getValue());
    }


    public LiveData<LinkedHashMap<String, String>> getLicensedStatesAndNumbers() {
        return licensedStateAndNumbersData;
    }

    public void addLicensedStateStateAndNumber(String state, String number) {
            licensedStateAndNumbersData.getValue().put(state, number);
    }


    public void resetSpecialistInformationStateData() {
        LinkedHashMap<String, String> userStatesAndNumbersCopy = new LinkedHashMap<>();
        // MAPS PASSED BY REFERENCE
        Utility.deepCopyStatesAndNumbersMap(user.getValue().getSpecialistInformation().getStatesAndNumbers(), userStatesAndNumbersCopy);
        licensedStateAndNumbersData.setValue(userStatesAndNumbersCopy);
    }

    public List<CalendarEvent> getUserEvents() {
        return user.getValue().getEvents();
    }

    public void addUserEvent(CalendarEvent date) {
        user.getValue().addEvent(date);
    }

    public LiveData<Boolean> getLoginSuccessful() {
        return loginSuccessful;
    }

    public void resetLoginStatusBoolean() {
        // reset the value
        loginSuccessful.setValue(null);
    }

    public void getUserAPIResponse(RetrofitCallbackListener<UserAPIResponse> callbackListener, int requestID) {
        Call<UserAPIResponse> response =  RetrofitBuilder.getVeeDocAPI().getUserAPIResponse( Utility.bearerToken);
        response.enqueue(new Callback<UserAPIResponse>() {
            @Override
            public void onResponse(Call<UserAPIResponse> call, Response<UserAPIResponse> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<UserAPIResponse> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void putVerificationCode(VerificationCode verificationCode, RetrofitCallbackListener<UserAPIResponse> callbackListener, int requestID) {
        Call<UserAPIResponse> response =  RetrofitBuilder.getVeeDocAPI().putVerificationCode(verificationCode, Utility.bearerToken);
        response.enqueue(new Callback<UserAPIResponse>() {
            @Override
            public void onResponse(Call<UserAPIResponse> call, Response<UserAPIResponse> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<UserAPIResponse> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void resendVerificationCode(RetrofitCallbackListener<Void> callbackListener, int requestID) {
        Call<Void> response =  RetrofitBuilder.getVeeDocAPI().resendVerificationCode(Utility.bearerToken);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getSpecialistInformation(RetrofitCallbackListener<SpecialistInformation> callbackListener, int requestID) {
        Call<SpecialistInformation> response =  RetrofitBuilder.getVeeDocAPI().getSpecialistInfo( Utility.bearerToken);
        response.enqueue(new Callback<SpecialistInformation>() {
            @Override
            public void onResponse(Call<SpecialistInformation> call, Response<SpecialistInformation> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<SpecialistInformation> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getEndpoints(String status, String filter, int index, int size, RetrofitCallbackListener<List<Endpoint>> callbackListener, int requestID){

        Call<List<Endpoint>> response =  RetrofitBuilder.getVeeDocAPI().getEndPoints(status, filter, index, size, Utility.bearerToken);
        response.enqueue(new Callback<List<Endpoint>>() {
            @Override
            public void onResponse(Call<List<Endpoint>> call, Response<List<Endpoint>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<Endpoint>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getEndpointsStatus(RetrofitCallbackListener<List<EndpointStatus>> callbackListener, int requestID){

        Call<List<EndpointStatus>> response =  RetrofitBuilder.getVeeDocAPI().getEndPointStatus(Utility.bearerToken);
        response.enqueue(new Callback<List<EndpointStatus>>() {
            @Override
            public void onResponse(Call<List<EndpointStatus>> call, Response<List<EndpointStatus>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<EndpointStatus>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getSessionInfo(int id, RetrofitCallbackListener<SessionInfo> callbackListener, int requestID){

        Call<SessionInfo> response =  RetrofitBuilder.getVeeDocAPI().getSessionInfo(id, Utility.bearerToken);
        response.enqueue(new Callback<SessionInfo>() {
            @Override
            public void onResponse(Call<SessionInfo> call, Response<SessionInfo> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<SessionInfo> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getStates(RetrofitCallbackListener<List<State>> callbackListener, int requestID){

        Call<List<State>> response =  RetrofitBuilder.getVeeDocAPI().getStates(Utility.bearerToken);
        response.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getSpecialities(RetrofitCallbackListener<List<Speciality>> callbackListener, int requestID){

        Call<List<Speciality>> response =  RetrofitBuilder.getVeeDocAPI().getSpecialities(Utility.bearerToken);
        response.enqueue(new Callback<List<Speciality>>() {
            @Override
            public void onResponse(Call<List<Speciality>> call, Response<List<Speciality>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<Speciality>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getOffDays(int year, int month, RetrofitCallbackListener<List<OffDayModel>> callbackListener, int requestID){

        Call<List<OffDayModel>> response =  RetrofitBuilder.getVeeDocAPI().getOffDays(year, month, Utility.bearerToken);
        response.enqueue(new Callback<List<OffDayModel>>() {
            @Override
            public void onResponse(Call<List<OffDayModel>> call, Response<List<OffDayModel>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<OffDayModel>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getEvents(int year, int month, RetrofitCallbackListener<List<ScheduledEvent>> callbackListener, int requestID){

        Call<List<ScheduledEvent>> response =  RetrofitBuilder.getVeeDocAPI().getEvents(year, month, Utility.bearerToken);
        response.enqueue(new Callback<List<ScheduledEvent>>() {
            @Override
            public void onResponse(Call<List<ScheduledEvent>> call, Response<List<ScheduledEvent>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<ScheduledEvent>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getPendingSessions(RetrofitCallbackListener<List<PendingSession>> callbackListener, int requestID){

        Call<List<PendingSession>> response =  RetrofitBuilder.getVeeDocAPI().getPendingRequests(Utility.bearerToken);
        response.enqueue(new Callback<List<PendingSession>>() {
            @Override
            public void onResponse(Call<List<PendingSession>> call, Response<List<PendingSession>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<PendingSession>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void rejectCall(CallActionsModel actionsModel, RetrofitCallbackListener<Void> callbackListener, int requestID){

        Call<Void> response =  RetrofitBuilder.getVeeDocAPI().callActionReject(actionsModel, Utility.bearerToken);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void deferCall(CallActionsModel actionsModel, RetrofitCallbackListener<Conversation> callbackListener, int requestID){

        Call<Conversation> response =  RetrofitBuilder.getVeeDocAPI().callActionDefer(actionsModel, Utility.bearerToken);
        response.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void acceptCall(CallActionsModel actionsModel, RetrofitCallbackListener<SessionInfo> callbackListener, int requestID){

        Call<SessionInfo> response =  RetrofitBuilder.getVeeDocAPI().callActionAccept(actionsModel, Utility.bearerToken);
        response.enqueue(new Callback<SessionInfo>() {
            @Override
            public void onResponse(Call<SessionInfo> call, Response<SessionInfo> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<SessionInfo> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getConversations(RetrofitCallbackListener<List<Conversation>> callbackListener, int requestID){

        Call<List<Conversation>> response =  RetrofitBuilder.getVeeDocAPI().getConversations(Utility.bearerToken);
        response.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void getMessages(
            int messageSessionId,
            int pageIndex,
            int length,
            boolean unread,
            RetrofitCallbackListener<List<Message>> callbackListener, int requestID){

        Call<List<Message>> response =  RetrofitBuilder.getVeeDocAPI().getMessages(messageSessionId, pageIndex, length, unread, Utility.bearerToken);
        response.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void pingMessages(
            int messageSessionId,
            RetrofitCallbackListener<List<Message>> callbackListener, int requestID){

        Call<List<Message>> response =  RetrofitBuilder.getVeeDocAPI().pingMessages(messageSessionId, Utility.bearerToken);
        response.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void sendNewMessage(NewMessageBody messageBody, RetrofitCallbackListener<Void> callbackListener, int requestID){

        Call<Void> response =  RetrofitBuilder.getVeeDocAPI().sendNewMessage(messageBody, Utility.bearerToken);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void changePassword(ChangePassword changePassword, RetrofitCallbackListener<Void> callbackListener, int requestID){

        Call<Void> response =  RetrofitBuilder.getVeeDocAPI().changePassword(changePassword, Utility.bearerToken);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void completeRegistration(UserAPIResponse userAPIResponse, RetrofitCallbackListener<String> callbackListener, int requestID){

        UserAPIRequest user = userAPIResponse.convertoToUserAPIRequest();
        Call<String> response =  RetrofitBuilder.getVeeDocAPI().completeRegistration(user, Utility.bearerToken);
        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
            }
        });
    }

    public void initRegistration(UserAPIResponse user, RetrofitCallbackListener<String> callbackListener, int requestID) {
        Call<String> response =  RetrofitBuilder.getVeeDocAPI().initRegistration(user);
        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
                Log.e(TAG, "onFailureGettingUser: " + t.getMessage());
            }
        });
    }

    public void tryToLogin(String encodedEmail, String encodedPassword, RetrofitCallbackListener<TokenResponse> callbackListener, int requestId){
        String decodedEmail= Utility.decodeText(encodedEmail);
        String decodedPassword = Utility.decodeText(encodedPassword);

        // create user payload object
        UserPayload userPayload = new UserPayload("ro.ios.client", "dfc99587-9b8c-46f5-b449-5d7986f27178", decodedEmail, decodedPassword, "password");

        // get the response
        Call<TokenResponse> userAuthenticationResponse = RetrofitBuilder.getVeeDocAPI().authenticateUser(userPayload.getClient_id(), userPayload.getClient_secret(), decodedEmail, decodedPassword, userPayload.getGrant_type());
        userAuthenticationResponse.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                callbackListener.onResponse(call, response, requestId);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                callbackListener.onFailure(call, t, requestId);
            }
        });
    }

    public void getSessions(int year, int month, int day, int param, RetrofitCallbackListener callbackListener, int requestID) {
        Call<List<Session>> response =  RetrofitBuilder.getVeeDocAPI().getSessions(year, month, day, param, Utility.bearerToken);
        response.enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(Call<List<Session>> call, Response<List<Session>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<Session>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
                Log.e(TAG, "onFailureGettingUser: " + t.getMessage());
            }
        });
    }

    public void getSessions(String status, String filter, int index, int size, RetrofitCallbackListener callbackListener, int requestID) {
        Call<List<Endpoint>> response =  RetrofitBuilder.getVeeDocAPI().getEndPoints(status, filter, index, size, Utility.bearerToken);
        response.enqueue(new Callback<List<Endpoint>>() {
            @Override
            public void onResponse(Call<List<Endpoint>> call, Response<List<Endpoint>> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<List<Endpoint>> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
                Log.e(TAG, "onFailureGettingUser: " + t.getMessage());
            }
        });
    }


    public void getUser(RetrofitCallbackListener<UserAPIResponse> callbackListener, int requestID) {
        MutableLiveData<UserAPIResponse> mutableLiveData = new MutableLiveData<>();
        Call<UserAPIResponse> response =  RetrofitBuilder.getVeeDocAPI().getUserAPIResponse(Utility.bearerToken);
        response.enqueue(new Callback<UserAPIResponse>() {
            @Override
            public void onResponse(Call<UserAPIResponse> call, Response<UserAPIResponse> response) {
                callbackListener.onResponse(call, response, requestID);
            }

            @Override
            public void onFailure(Call<UserAPIResponse> call, Throwable t) {
                callbackListener.onFailure(call, t, requestID);
                Log.e(TAG, "onFailureGettingUser: " + t.getMessage());
            }
        });
    }
}
