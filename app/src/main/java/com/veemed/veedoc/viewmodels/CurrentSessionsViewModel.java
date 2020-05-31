package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CurrentSessionsViewModel extends NavigationActivityViewModel {

    public CurrentSessionsViewModel() {
        super();
    }

    public MutableLiveData<List<PendingSession>> pendingSessions = new MutableLiveData<>();

    public MutableLiveData<List<PendingSession>> getPendingSessions() {

        return pendingSessions;
    }

    public void fetchPendingSessions() {
        userRepo.getPendingSessions(new RetrofitCallbackListener<List<PendingSession>>() {
            @Override
            public void onResponse(Call<List<PendingSession>> call, Response<List<PendingSession>> response, int requestID) {
                if(response.isSuccessful()) {
                    pendingSessions.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PendingSession>> call, Throwable t, int requestID) {

            }
        }, 0);
    }


}
