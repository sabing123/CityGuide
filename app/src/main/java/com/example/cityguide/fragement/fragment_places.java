package com.example.cityguide.fragement;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cityguide.MapsActivity;
import com.example.cityguide.R;
import com.example.cityguide.StrictMode.strictmodeclass;
import com.example.cityguide.fragement.PlaceTabViewFragement.TrekkingPlaceFragment;
import com.example.cityguide.fragement.PlaceTabViewFragement.famousPlaceFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class fragment_places extends Fragment {
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    Geocoder geocoder;
    List<Address> addresses;
    
    Double latitude = 27.6999007;
    Double longitude = 85.2734998;

    TextView txtLat, txtadress;
    Button btnsearchplaces;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places, container, false);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);

        txtLat = (TextView) view.findViewById(R.id.currentLocation);
        btnsearchplaces =  view.findViewById(R.id.btnsearchplaces);

        txtadress = view.findViewById(R.id.currentaddress);

        btnsearchplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MapsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {

            addresses = geocoder.getFromLocation(latitude,longitude,1);
            String address = addresses.get(0).getLocality();
            String caddress = addresses.get(0).getFeatureName();
            txtadress.setText(caddress + address + addresses.get(0).getCountryName());

        }catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }



    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new famousPlaceFragment(), "Famous Places");
        adapter.addFragment(new TrekkingPlaceFragment(), "Trekking Places");
        viewPager.setAdapter(adapter);

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}