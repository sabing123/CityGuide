package com.example.cityguide.API;

import com.example.cityguide.model.hotelModel;
import com.example.cityguide.model.places.famousPlaceModel;
import com.example.cityguide.model.places.trekkingplaceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface placesAPI {
    @GET("famousplace")
    Call<List<famousPlaceModel>> getPlaceDetails();

    @GET("trekkingplace")
    Call<List<trekkingplaceModel>> getTrekkingPlaceDetails();

}
