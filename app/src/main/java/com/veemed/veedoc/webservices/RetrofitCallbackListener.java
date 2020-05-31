package com.veemed.veedoc.webservices;

import retrofit2.Call;
import retrofit2.Response;

public interface RetrofitCallbackListener<T> {
    void onResponse(Call<T> call, Response<T> response, int requestID);
    void onFailure(Call<T> call, Throwable t, int requestID);
}
