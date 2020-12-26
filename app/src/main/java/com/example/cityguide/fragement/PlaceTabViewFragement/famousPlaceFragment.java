package com.example.cityguide.fragement.PlaceTabViewFragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.API.placesAPI;
import com.example.cityguide.Adapter.places.AdapterFamousPlaces;
import com.example.cityguide.R;
import com.example.cityguide.globalURL.URL;
import com.example.cityguide.model.hotelModel;
import com.example.cityguide.model.places.famousPlaceModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class famousPlaceFragment extends Fragment {

    RecyclerView famousplacesRecycleView;

    List<famousPlaceModel> famousPlaceModelList;
    AdapterFamousPlaces adapterFamousPlaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_famous_place, container, false);

        famousplacesRecycleView = view.findViewById(R.id.famousplacesrecycleview);
        loadfamousPlaces();

        return view;
    }

    private void loadfamousPlaces() {
        placesAPI placesAPI = URL.getInstance().create(placesAPI.class);
        Call<List<famousPlaceModel>> famousplacesModelCall = placesAPI.getPlaceDetails();
        try {
            Response<List<famousPlaceModel>> placesModelResponse = famousplacesModelCall.execute();

            famousPlaceModelList = placesModelResponse.body();
            adapterFamousPlaces = new AdapterFamousPlaces(getContext(), famousPlaceModelList);
            famousplacesRecycleView.setAdapter(adapterFamousPlaces);
            famousplacesRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}