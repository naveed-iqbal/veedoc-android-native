package com.veemed.veedoc.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.veemed.CallActionsModel;
import com.veemed.veedoc.models.ChangePassword;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.DeferResponseModel;
import com.veemed.veedoc.models.MarkMessagesReadRequestModel;
import com.veemed.veedoc.models.Message;
import com.veemed.veedoc.models.NewMessageBody;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.models.SupportGroupModel;
import com.veemed.veedoc.models.UserAPIRequest;
import com.veemed.veedoc.models.event.CalendarEvent;
import com.veemed.veedoc.models.Endpoint;
import com.veemed.veedoc.models.EndpointStatus;
import com.veemed.veedoc.models.event.OffDayModel;
import com.veemed.veedoc.models.Session;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.models.SpecialistInformation;
import com.veemed.veedoc.models.Speciality;
import com.veemed.veedoc.models.State;
import com.veemed.veedoc.models.VerificationCode;
import com.veemed.veedoc.models.event.ScheduledEvent;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import com.veemed.veedoc.webservices.TokenResponse;
import com.veemed.veedoc.webservices.UserAPIResponse;
import com.veemed.veedoc.webservices.VeeDocRetrofitDataSource;
import com.veemed.veedoc.models.User;

import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class VeeDocRepository {

    private VeeDocRetrofitDataSource retrofitDataSource;
    private static VeeDocRepository repoInstance;

    public VeeDocRepository() {
        retrofitDataSource = VeeDocRetrofitDataSource.getInstance();
    }

    // Singleton
    public static synchronized VeeDocRepository getInstance() {
        if (repoInstance == null) {
            repoInstance = new VeeDocRepository();
        }

        return repoInstance;
    }

    public LiveData<ReturnResponse<TokenResponse>> tryToLogin(String encodedEmail, String encodedPassword) {
        MutableLiveData<ReturnResponse<TokenResponse>> toReturn = new MutableLiveData<>();
        RetrofitCallbackListener<TokenResponse> signInCallback = new RetrofitCallbackListener<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response, int requestID) {
                ReturnResponse<TokenResponse> returnResponse = new ReturnResponse();
                if (response.isSuccessful()) {
                    returnResponse.setValid(true);
                    returnResponse.setReturnObject(response.body());
                    returnResponse.setMessage("success");
                } else {
                    returnResponse.setValid(false);
                    returnResponse.setReturnObject(null);
                    returnResponse.setMessage(response.errorBody().toString());
                }

                toReturn.setValue(returnResponse);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t, int requestID) {
                ReturnResponse<TokenResponse> returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(t.getMessage());

                toReturn.setValue(returnResponse);
            }
        };

        retrofitDataSource.tryToLogin(encodedEmail, encodedPassword, signInCallback, 0);
        return toReturn;
    }

    public ReturnResponse<TokenResponse> refreshLogin(String encodedEmail, String encodedPassword) {
        final ReturnResponse<TokenResponse>[] toReturn = new ReturnResponse[1];
        RetrofitCallbackListener<TokenResponse> signInCallback = new RetrofitCallbackListener<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response, int requestID) {
                ReturnResponse<TokenResponse> returnResponse = new ReturnResponse();
                if (response.isSuccessful()) {
                    returnResponse.setValid(true);
                    returnResponse.setReturnObject(response.body());
                    returnResponse.setMessage("success");
                } else {
                    returnResponse.setValid(false);
                    returnResponse.setReturnObject(null);
                    returnResponse.setMessage(response.errorBody().toString());
                }

                toReturn[0] = returnResponse;
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t, int requestID) {
                ReturnResponse<TokenResponse> returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(t.getMessage());

                toReturn[0] = returnResponse;
            }
        };

        retrofitDataSource.tryToLogin(encodedEmail, encodedPassword, signInCallback, 0);
        return toReturn[0];
    }

    public boolean accountWithEmailExists(String encodedEmail) {

        return retrofitDataSource.accountWithEmailExists(encodedEmail);
    }

    public void updatePassword() {
        retrofitDataSource.updatePassword();
    }

    public MutableLiveData<ReturnResponse<String>> initRegistration(UserAPIResponse user) {
        MutableLiveData<ReturnResponse<String>> toReturn = new MutableLiveData<>();
        RetrofitCallbackListener<String> initRegistrationCallback = new RetrofitCallbackListener<String>() {

            @Override
            public void onResponse(Call call, Response response, int requestID) {

                ReturnResponse<String> returnResponse = new ReturnResponse();
                if (response.isSuccessful()) {
                    returnResponse.setValid(true);
                    returnResponse.setReturnObject(response.body().toString());
                    returnResponse.setMessage("success");
                } else {
                    returnResponse.setValid(false);
                    returnResponse.setReturnObject(null);
                    returnResponse.setMessage(response.errorBody().toString());
                }

                toReturn.setValue(returnResponse);
            }

            @Override
            public void onFailure(Call call, Throwable t, int requestID) {
                ReturnResponse<String> returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(t.getMessage());

                toReturn.setValue(returnResponse);
            }
        };

        retrofitDataSource.initRegistration(user, initRegistrationCallback, 0);

        return toReturn;
    }

    public void getUserAPIResponse(RetrofitCallbackListener<UserAPIResponse> initRegistrationCallback, int requestID) {
        retrofitDataSource.getUserAPIResponse(initRegistrationCallback, requestID);
    }

    public MutableLiveData<ReturnResponse<User>> getUserAPIResponse() {
        MutableLiveData<ReturnResponse<User>> toReturn = new MutableLiveData<>();

        RetrofitCallbackListener<UserAPIResponse> initRegistrationCallback = new RetrofitCallbackListener<UserAPIResponse>() {

            @Override
            public void onResponse(Call<UserAPIResponse> call, Response<UserAPIResponse> response, int requestID) {

                ReturnResponse<User> returnResponse = new ReturnResponse();
                if (response.isSuccessful()) {
                    returnResponse.setValid(true);
                    returnResponse.setReturnObject(response.body().convertoToUserModel());
                    returnResponse.setMessage("success");
                } else {
                    returnResponse.setValid(false);
                    returnResponse.setReturnObject(null);
                    returnResponse.setMessage(response.errorBody().toString());
                }

                toReturn.setValue(returnResponse);
            }

            @Override
            public void onFailure(Call<UserAPIResponse> call, Throwable t, int requestID) {
                ReturnResponse<User> returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(t.getMessage());

                toReturn.setValue(returnResponse);
            }
        };

        // retrofitDataSource.initRegistration(user, initRegistrationCallback, 0);
        retrofitDataSource.getUserAPIResponse(initRegistrationCallback, 0);

        return toReturn;
    }

    public void verifyCode(VerificationCode verificationCode, RetrofitCallbackListener<UserAPIResponse> callbackListener, int requestID) {
        retrofitDataSource.putVerificationCode(verificationCode, callbackListener, requestID);
    }

    public void resendVerificationCode(RetrofitCallbackListener<Void> callbackListener, int requestID) {
        retrofitDataSource.resendVerificationCode(callbackListener, requestID);
    }

    public void getSpecialistInformation(RetrofitCallbackListener<SpecialistInformation> callbackListener, int requestID) {
        retrofitDataSource.getSpecialistInformation(callbackListener, requestID);
    }

    public void getEndpoints(String status, String filter, int index, int size, RetrofitCallbackListener<List<Endpoint>> callbackListener, int requestID) {
        retrofitDataSource.getEndpoints(status, filter, index, size, callbackListener, requestID);
    }

    public void getEndpointStatus(RetrofitCallbackListener<List<EndpointStatus>> callbackListener, int requestID) {
        retrofitDataSource.getEndpointsStatus(callbackListener, requestID);
    }

    public void getSessionInfo(int id, RetrofitCallbackListener<SessionInfo> callbackListener, int requestID) {
        retrofitDataSource.getSessionInfo(id, callbackListener, requestID);
    }

    public void getStates(RetrofitCallbackListener<List<State>> callbackListener, int requestID) {
        retrofitDataSource.getStates(callbackListener, requestID);
    }

    public void getSpecialities(RetrofitCallbackListener<List<Speciality>> callbackListener, int requestID) {
        retrofitDataSource.getSpecialities(callbackListener, requestID);
    }

    public void getOffDays(int year, int month, RetrofitCallbackListener<List<OffDayModel>> callbackListener, int requestID) {
        retrofitDataSource.getOffDays(year, month, callbackListener, requestID);
    }

    public void getEvents(int year, int month, RetrofitCallbackListener<List<ScheduledEvent>> callbackListener, int requestID) {
        retrofitDataSource.getEvents(year, month, callbackListener, requestID);
    }

    public void getPendingSessions(RetrofitCallbackListener<List<PendingSession>> callbackListener, int requestID) {
        retrofitDataSource.getPendingSessions(callbackListener, requestID);
    }

    public void rejectCall(CallActionsModel actionsModel, RetrofitCallbackListener<Void> callbackListener, int requestID) {
        retrofitDataSource.rejectCall(actionsModel, callbackListener, requestID);
    }

    public void deferCall(CallActionsModel actionsModel, RetrofitCallbackListener<Conversation> callbackListener, int requestID) {
        retrofitDataSource.deferCall(actionsModel, callbackListener, requestID);
    }

    public void acceptCall(CallActionsModel actionsModel, RetrofitCallbackListener<SessionInfo> callbackListener, int requestID) {
        retrofitDataSource.acceptCall(actionsModel, callbackListener, requestID);
    }

    public void getConversations(RetrofitCallbackListener<List<Conversation>> callbackListener, int requestID) {
        retrofitDataSource.getConversations(callbackListener, requestID);
    }

    public void getMessages(int messageSessionId,
                           int pageIndex,
                           int length,
                           boolean unread,RetrofitCallbackListener<List<Message>> callbackListener, int requestID) {
        retrofitDataSource.getMessages(messageSessionId, pageIndex, length, unread, callbackListener, requestID);
    }

    public void pingMessages(int messageSessionId, RetrofitCallbackListener<List<Message>> callbackListener, int requestID) {
        retrofitDataSource.pingMessages(messageSessionId, callbackListener, requestID);
    }

    public void markMessageRead(MarkMessagesReadRequestModel paramMarkMessagesReadRequestModel, RetrofitCallbackListener<Void> paramRetrofitCallbackListener, int paramInt) {
        this.retrofitDataSource.markMessageRead(paramMarkMessagesReadRequestModel, paramRetrofitCallbackListener, paramInt);
    }

    public void sendNewMessage(NewMessageBody messageBody, RetrofitCallbackListener<Void> callbackListener, int requestID) {
        retrofitDataSource.sendNewMessage(messageBody, callbackListener, requestID);
    }

    public void changePassword(ChangePassword changePassword, RetrofitCallbackListener<Void> callbackListener, int requestID) {
        retrofitDataSource.changePassword(changePassword, callbackListener, requestID);
    }

    public void completeRegistration(UserAPIResponse userAPIResponse, RetrofitCallbackListener<String> callbackListener, int requestID) {
        retrofitDataSource.completeRegistration(userAPIResponse, callbackListener, requestID);
    }

    public MutableLiveData<ReturnResponse<List<Session>>> getSessions(int year, int month, int day, int param) {

        MutableLiveData<ReturnResponse<List<Session>>> toReturn = new MutableLiveData<>();
        RetrofitCallbackListener<List<Session>> initRegistrationCallback = new RetrofitCallbackListener<List<Session>>() {

            @Override
            public void onResponse(Call<List<Session>> call, Response<List<Session>> response, int requestID) {

                ReturnResponse<List<Session>> returnResponse = new ReturnResponse();
                if (response.isSuccessful()) {
                    returnResponse.setValid(true);
                    returnResponse.setReturnObject(response.body());
                    returnResponse.setMessage("success");
                } else {
                    returnResponse.setValid(false);
                    returnResponse.setReturnObject(null);
                    returnResponse.setMessage(response.errorBody().toString());
                }

                toReturn.setValue(returnResponse);
            }

            @Override
            public void onFailure(Call<List<Session>> call, Throwable t, int requestID) {
                ReturnResponse<List<Session>> returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(t.getMessage());

                toReturn.setValue(returnResponse);
            }
        };

        // retrofitDataSource.initRegistration(user, initRegistrationCallback, 0);
        retrofitDataSource.getSessions(year, month, day, param, initRegistrationCallback, 0);

        return toReturn;
    }

    public void setUser(User user) {
        retrofitDataSource.setUser(user);
    }

    public void updateUser(String fullName, String email, String mobileNumber, String zipCode, String address,
                           String deaNumber, int npiNumber, String specialityName, String practiceGroup,
                           String state, String country, String city, String otherAddress) {
        retrofitDataSource.updateUser(fullName, email, mobileNumber, zipCode, address, deaNumber, npiNumber,
                specialityName, practiceGroup, state, country, city, otherAddress);

    }

    public void updateUserState(String state) {
        retrofitDataSource.updateUserState(state);
    }

    public LiveData<LinkedHashMap<String, String>> getLicensedStatesAndNumbers() {
        return retrofitDataSource.getLicensedStatesAndNumbers();
    }

    public void removeLicensedStateAndNumber(String state) {
        retrofitDataSource.removeLicensedStateAndNumber(state);
    }

    public void updateUserSpecialistInfoLicensedStateAndNumber() {
        retrofitDataSource.updateUserSpecialistInfoLicensedStateAndNumber();
    }


    public void addLicensedStateStateAndNumber(String state, String number) {
        retrofitDataSource.addLicensedStateStateAndNumber(state, number);
    }

    public void resetSpecialistInformationStateData() {
        retrofitDataSource.resetSpecialistInformationStateData();
    }

    public List<CalendarEvent> getUserEvents() {
        return retrofitDataSource.getUserEvents();
    }

    public void addUserEvent(CalendarEvent date) {
        retrofitDataSource.addUserEvent(date);
    }

    public LiveData<Boolean> getLoginSuccessful() {
        return retrofitDataSource.getLoginSuccessful();
    }

    public void resetLoginStatusBoolean() {
        retrofitDataSource.resetLoginStatusBoolean();
    }

    public void getSupportGroups(RetrofitCallbackListener<List<SupportGroupModel>> paramRetrofitCallbackListener, int paramInt) {
        this.retrofitDataSource.getSupportGroups(paramRetrofitCallbackListener, paramInt);
    }

    public void getChatUsers(int paramInt1, int paramInt2, RetrofitCallbackListener<List<UserAPIRequest>> paramRetrofitCallbackListener, int paramInt3) {
        this.retrofitDataSource.getChatUsers(paramInt1, paramInt2, paramRetrofitCallbackListener, paramInt3);
    }

    public void getMessageSessionInfoBySessionId(int paramInt1, RetrofitCallbackListener<Conversation> paramRetrofitCallbackListener, int paramInt2) {
        this.retrofitDataSource.getMessageSessionInfoBySessionId(paramInt1, paramRetrofitCallbackListener, paramInt2);
    }

    public void reconnectCall(int paramInt1, RetrofitCallbackListener<SessionInfo> paramRetrofitCallbackListener, int paramInt2) {
        this.retrofitDataSource.reconnectCall(paramInt1, paramRetrofitCallbackListener, paramInt2);
    }
}
