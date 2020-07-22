package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.MutableLiveData;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class CurrentSessionsViewModel extends NavigationActivityViewModel {
    public MutableLiveData<Conversation> deferredMessageSessionLiveData = new MutableLiveData();

    public MutableLiveData<List<PendingSession>> pendingSessions = new MutableLiveData();

    public MutableLiveData<SessionInfo> sessionInfoLiveData = new MutableLiveData();

    public void fetchDeferredMessageSession(int paramInt) {
        this.userRepo.getMessageSessionInfoBySessionId(paramInt, new RetrofitCallbackListener<Conversation>() {
            public void onFailure(Call<Conversation> param1Call, Throwable param1Throwable, int param1Int) {}

            public void onResponse(Call<Conversation> param1Call, Response<Conversation> param1Response, int param1Int) {
                if (param1Response.isSuccessful())
                    CurrentSessionsViewModel.this.deferredMessageSessionLiveData.setValue(param1Response.body());
            }
        },  0);
    }

    public void fetchPendingSessions() {
        this.userRepo.getPendingSessions(new RetrofitCallbackListener<List<PendingSession>>() {
            public void onFailure(Call<List<PendingSession>> param1Call, Throwable param1Throwable, int param1Int) {}

            public void onResponse(Call<List<PendingSession>> param1Call, Response<List<PendingSession>> param1Response, int param1Int) {
                if (param1Response.isSuccessful())
                    CurrentSessionsViewModel.this.pendingSessions.setValue(param1Response.body());
            }
        },  0);
    }

    public void fetchSessionInfo(int paramInt) {
        this.userRepo.reconnectCall(paramInt, new RetrofitCallbackListener<SessionInfo>() {
            public void onFailure(Call<SessionInfo> param1Call, Throwable param1Throwable, int param1Int) {}

            public void onResponse(Call<SessionInfo> param1Call, Response<SessionInfo> param1Response, int param1Int) {
                if (param1Response.isSuccessful())
                    CurrentSessionsViewModel.this.sessionInfoLiveData.setValue(param1Response.body());
            }
        },  0);
    }

    public MutableLiveData<Conversation> getDeferredMessageSession() {
        return this.deferredMessageSessionLiveData;
    }

    public MutableLiveData<List<PendingSession>> getPendingSessions() {
        return this.pendingSessions;
    }

    public MutableLiveData<SessionInfo> getSessionInfo() {
        return this.sessionInfoLiveData;
    }
}
