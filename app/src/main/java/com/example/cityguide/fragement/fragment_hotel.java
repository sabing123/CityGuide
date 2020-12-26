package com.example.cityguide.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.API.HotelAPI;
import com.example.cityguide.Adapter.Adapter_Hotel;
import com.example.cityguide.R;
import com.example.cityguide.globalURL.URL;
import com.example.cityguide.model.hotelModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class fragment_hotel extends Fragment {

    Adapter_Hotel hotelAdapter;
    List<hotelModel> hotelList;
    private RecyclerView recyclerViewHotel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        recyclerViewHotel = view.findViewById(R.id.recycleviewhotel);
        hotel();

        return view;

    }

    private void hotel() {
        hotelList = new ArrayList<>();
        HotelAPI hotelAPI = URL.getInstance().create(HotelAPI.class);
        Call<List<hotelModel>> hotelModelListCall = hotelAPI.gethotel();
        try {
            Response<List<hotelModel>> hotelResponse = hotelModelListCall.execute();
            hotelList = hotelResponse.body();
            hotelAdapter = new Adapter_Hotel(getContext(), hotelList);
            recyclerViewHotel.setAdapter(hotelAdapter);
            recyclerViewHotel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}