package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.veemed.veedoc.models.Endpoint;
import com.veemed.veedoc.models.EndpointStatus;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class EndpointsViewModel extends NavigationActivityViewModel {

    public EndpointsViewModel() {
        super();
    }

    private int lastIndex = 0;
    private String currentFilter = "null";
    private String currentStatus = "online";
    MutableLiveData<List<Endpoint>> endPointsLiveData;
    MutableLiveData<List<EndpointStatus>> endPointStatusLiveData = new MutableLiveData<>();
    public LiveData<List<Endpoint>> initEndpoints(){
        endPointsLiveData = new MutableLiveData<>();
        lastIndex = 0;
        getEndPoints(currentStatus, currentFilter, 0, 10);
        return endPointsLiveData;
    }

    public void getEndPoints(String status, String filter, int index, int size) {
        if(filter!=null)currentFilter = filter;
        if(status!=null)currentStatus =  status;
        this.lastIndex = index;
        RetrofitCallbackListener<List<Endpoint>> callbackListener = new RetrofitCallbackListener<List<Endpoint>>() {
            @Override
            public void onResponse(Call<List<Endpoint>> call, Response<List<Endpoint>> response, int requestID) {
                if(response.isSuccessful()) {
                    List<Endpoint> data = endPointsLiveData.getValue();
                    if(index>0) data.addAll(response.body());
                    else data = response.body();
                    endPointsLiveData.setValue(data);
                }
            }

            @Override
            public void onFailure(Call<List<Endpoint>> call, Throwable t, int requestID) {

            }
        };

        userRepo.getEndpoints(status, filter, index, size, callbackListener, 0);
    }

    public void loadMoreData() {
        getEndPoints(currentStatus, currentFilter, lastIndex+1, 10);
    }

    public MutableLiveData<List<EndpointStatus>> getStatusLiveData() {
        // refreshData();
        return endPointStatusLiveData;
    }

    public void refreshData() {
        RetrofitCallbackListener<List<EndpointStatus>> callbackListener = new RetrofitCallbackListener<List<EndpointStatus>>() {
            @Override
            public void onResponse(Call<List<EndpointStatus>> call, Response<List<EndpointStatus>> response, int requestID) {
                if(response.isSuccessful()) {
                    endPointStatusLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EndpointStatus>> call, Throwable t, int requestID) {

            }
        };
        userRepo.getEndpointStatus(callbackListener, 0);
    }
}
