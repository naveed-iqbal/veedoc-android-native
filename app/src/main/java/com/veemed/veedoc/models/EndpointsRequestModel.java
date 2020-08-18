package com.veemed.veedoc.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndpointsRequestModel {

    @SerializedName("filter")
    @Expose
    private String filter;
    @SerializedName("sortBy")
    @Expose
    private String sortBy;
    @SerializedName("partnerSiteId")
    @Expose
    private Integer partnerSiteId;
    @SerializedName("facilityId")
    @Expose
    private Integer facilityId;
    @SerializedName("connectionStatus")
    @Expose
    private String connectionStatus;
    @SerializedName("pageIndex")
    @Expose
    private Integer pageIndex = 0;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize = Integer.MAX_VALUE;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Integer getPartnerSiteId() {
        return partnerSiteId;
    }

    public void setPartnerSiteId(Integer partnerSiteId) {
        this.partnerSiteId = partnerSiteId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
