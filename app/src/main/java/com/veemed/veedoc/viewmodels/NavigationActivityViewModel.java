package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.SpecialistInformation;
import com.veemed.veedoc.models.SupportGroupModel;
import com.veemed.veedoc.models.User;
import com.veemed.veedoc.models.UserAPIRequest;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import com.veemed.veedoc.webservices.UserAPIResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class NavigationActivityViewModel extends ViewModel {
    private static VeeDocRepository repoInstance;

    MutableLiveData<Boolean> centerTextViewVisible = new MutableLiveData();

    MutableLiveData<List<SupportGroupModel>> chatSupportGroups = new MutableLiveData();

    MutableLiveData<List<UserAPIRequest>> chatUsers = new MutableLiveData();

    MutableLiveData<List<Conversation>> conversations = new MutableLiveData();

    MutableLiveData<String> toolbarSubtitle = new MutableLiveData();

    MutableLiveData<String> toolbarTitle = new MutableLiveData();

    MutableLiveData<ReturnResponse<User>> userData = new MutableLiveData();

    VeeDocRepository userRepo = VeeDocRepository.getInstance();

    public void fetchChatSupportGroups() {
        this.userRepo.getSupportGroups(new RetrofitCallbackListener<List<SupportGroupModel>>() {
            public void onFailure(Call<List<SupportGroupModel>> param1Call, Throwable param1Throwable, int param1Int) {}

            public void onResponse(Call<List<SupportGroupModel>> param1Call, Response<List<SupportGroupModel>> param1Response, int param1Int) {
                if (param1Response.isSuccessful())
                    NavigationActivityViewModel.this.chatSupportGroups.setValue(param1Response.body());
            }
        },  0);
    }

    public void fetchChatUsers() {
        this.userRepo.getChatUsers(0, 0, new RetrofitCallbackListener<List<UserAPIRequest>>() {
            public void onFailure(Call<List<UserAPIRequest>> param1Call, Throwable param1Throwable, int param1Int) {}

            public void onResponse(Call<List<UserAPIRequest>> param1Call, Response<List<UserAPIRequest>> param1Response, int param1Int) {
                if (param1Response.isSuccessful())
                    NavigationActivityViewModel.this.chatUsers.setValue(param1Response.body());
            }
        },  0);
    }

    public void fetchConversations() {
        this.userRepo.getConversations(new RetrofitCallbackListener<List<Conversation>>() {
            public void onFailure(Call<List<Conversation>> param1Call, Throwable param1Throwable, int param1Int) {}

            public void onResponse(Call<List<Conversation>> param1Call, Response<List<Conversation>> param1Response, int param1Int) {
                if (param1Response.isSuccessful())
                    NavigationActivityViewModel.this.conversations.setValue(param1Response.body());
            }
        },  0);
    }

    public MutableLiveData<ReturnResponse<User>> fetchUser() {
        this.userData = new MutableLiveData();
        RetrofitCallbackListener<UserAPIResponse> retrofitCallbackListener = new RetrofitCallbackListener<UserAPIResponse>() {
            public void onFailure(Call<UserAPIResponse> param1Call, Throwable param1Throwable, int param1Int) {
                ReturnResponse returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(param1Throwable.getMessage());
                NavigationActivityViewModel.this.userData.setValue(returnResponse);
            }

            public void onResponse(Call<UserAPIResponse> param1Call, Response<UserAPIResponse> param1Response, int param1Int) {
                ReturnResponse returnResponse = new ReturnResponse();
                if (param1Response.isSuccessful()) {
                    returnResponse.setValid(true);
                    returnResponse.setReturnObject(((UserAPIResponse)param1Response.body()).convertoToUserModel());
                    returnResponse.setMessage("success");
                    NavigationActivityViewModel.this.getSpecialistInformation();
                } else {
                    returnResponse.setValid(false);
                    returnResponse.setReturnObject(null);
                    returnResponse.setMessage(param1Response.errorBody().toString());
                }
                NavigationActivityViewModel.this.userData.setValue(returnResponse);
            }
        };
        this.userRepo.getUserAPIResponse(retrofitCallbackListener, 0);
        return this.userData;
    }

    public LiveData<Boolean> getCenterTextViewVisible() {
        return (LiveData<Boolean>)this.centerTextViewVisible;
    }

    public MutableLiveData<List<SupportGroupModel>> getChatSupportGroups() {
        return this.chatSupportGroups;
    }

    public MutableLiveData<List<UserAPIRequest>> getChatUsers() {
        return this.chatUsers;
    }

    public MutableLiveData<List<Conversation>> getConversationsLiveData() {
        return this.conversations;
    }

    public void getSpecialistInformation() {
        RetrofitCallbackListener<SpecialistInformation> retrofitCallbackListener = new RetrofitCallbackListener<SpecialistInformation>() {
            public void onFailure(Call<SpecialistInformation> param1Call, Throwable param1Throwable, int param1Int) {
                ReturnResponse returnResponse = new ReturnResponse();
                returnResponse.setValid(false);
                returnResponse.setReturnObject(null);
                returnResponse.setMessage(param1Throwable.getMessage());
            }

            public void onResponse(Call<SpecialistInformation> param1Call, Response<SpecialistInformation> param1Response, int param1Int) {
                ReturnResponse returnResponse = (ReturnResponse)NavigationActivityViewModel.this.userData.getValue();
                User user = (User)returnResponse.getReturnObject();
                user.setSpecialistInformation((SpecialistInformation)param1Response.body());
                returnResponse.setReturnObject(user);
                NavigationActivityViewModel.this.userData.setValue(returnResponse);
            }
        };
        this.userRepo.getSpecialistInformation(retrofitCallbackListener, 0);
    }

    public LiveData<String> getToolbarSubtitle() {
        return (LiveData<String>)this.toolbarSubtitle;
    }

    public LiveData<String> getToolbarTitle() {
        return (LiveData<String>)this.toolbarTitle;
    }

    public MutableLiveData<ReturnResponse<User>> getUser() {
        return this.userData;
    }

    public void setCenterTextViewVisible(boolean paramBoolean) {
        this.centerTextViewVisible.setValue(Boolean.valueOf(paramBoolean));
    }

    public void setToolbarSubtitle(String paramString) {
        this.toolbarSubtitle.setValue(paramString);
    }

    public void setToolbarTitle(String paramString) {
        this.toolbarTitle.setValue(paramString);
    }

    public void setUser(User paramUser) {
        this.userRepo.setUser(paramUser);
    }

    public void updateUser(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12) {
        this.userRepo.updateUser(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramInt, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12);
    }
}
