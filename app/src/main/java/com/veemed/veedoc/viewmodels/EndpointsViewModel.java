package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.veemed.veedoc.models.Endpoint;
import com.veemed.veedoc.models.EndpointStatus;
import com.veemed.veedoc.models.EndpointsRequestModel;
import com.veemed.veedoc.models.Facility;
import com.veemed.veedoc.models.PartnerSite;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class EndpointsViewModel extends NavigationActivityViewModel {

    public EndpointsViewModel() {
        super();
    }

    private int lastIndex = 0;
    private int pageSize = 10;
    public int partnerSiteId = 0;
    public int facilityId = 0;
    public String currentFilter = "";
    public String currentStatus = "online";
    private String currentSortBy = "name";
    MutableLiveData<List<Endpoint>> endPointsLiveData;
    MutableLiveData<List<EndpointStatus>> endPointStatusLiveData = new MutableLiveData<>();

    MutableLiveData<List<Facility>> facilitiesLiveData = new MutableLiveData<>();
    MutableLiveData<List<PartnerSite>> partnerSitesLiveData = new MutableLiveData<>();

    public LiveData<List<Endpoint>> initEndpoints(){
        endPointsLiveData = new MutableLiveData<>();
        lastIndex = 0;
        getEndPoints(facilityId, partnerSiteId, currentStatus, currentFilter, currentSortBy, 0, 10);
        return endPointsLiveData;
    }

    public void getEndPoints(int facilityId, int partnerSiteId, String status, String filter, String sortBy, int index, int size) {
        // below is assigning duplication, but not removing for now
        if (filter != null) currentFilter = filter;
        if (status != null) currentStatus = status;
        if (sortBy != null) currentSortBy = sortBy;
        this.facilityId = facilityId;
        this.partnerSiteId = partnerSiteId;
        pageSize = size;
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

        EndpointsRequestModel requestModel = new EndpointsRequestModel();
        requestModel.setConnectionStatus(status);
        requestModel.setFacilityId(facilityId);
        requestModel.setPartnerSiteId(partnerSiteId);
        requestModel.setFilter(filter);
        requestModel.setSortBy(sortBy);
        requestModel.setPageIndex(index);
        requestModel.setPageSize(size);

        // userRepo.getEndpoints(status, filter, index, size, callbackListener, 0);
        userRepo.getEndpoints(requestModel, callbackListener, 0);
    }

    public void loadMoreData() {
        getEndPoints(facilityId, partnerSiteId, currentStatus, currentFilter, currentSortBy, lastIndex+1, 10);
    }

    public MutableLiveData<List<EndpointStatus>> getStatusLiveData() {
        // refreshData();
        return endPointStatusLiveData;
    }

    public MutableLiveData<List<Facility>> getFacilitiesLiveData() {
        // refreshData();
        return facilitiesLiveData;
    }

    public void fetchFacilities(int partnerSiteId) {
        RetrofitCallbackListener<List<Facility>> callbackListener = new RetrofitCallbackListener<List<Facility>>() {
            @Override
            public void onResponse(Call<List<Facility>> call, Response<List<Facility>> response, int requestID) {
                if(response.isSuccessful()) {
                    facilitiesLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Facility>> call, Throwable t, int requestID) {

            }
        };
        userRepo.getFacilities(partnerSiteId, callbackListener, 0);
    }

    public MutableLiveData<List<PartnerSite>> getPartnerSitesLiveData() {
        // refreshData();
        return partnerSitesLiveData;
    }

    public void fetchPartnerSites() {
        RetrofitCallbackListener<List<PartnerSite>> callbackListener = new RetrofitCallbackListener<List<PartnerSite>>() {
            @Override
            public void onResponse(Call<List<PartnerSite>> call, Response<List<PartnerSite>> response, int requestID) {
                if(response.isSuccessful()) {
                    partnerSitesLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PartnerSite>> call, Throwable t, int requestID) {

            }
        };
        userRepo.getPartnerSites(callbackListener, 0);
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
