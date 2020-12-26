package com.example.cityguide.API;

import com.example.cityguide.model.hotelModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HotelAPI {

    @GET("hotel")
    Call<List<hotelModel>> gethotel();
}