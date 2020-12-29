package com.example.cityguide.fragement;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.cityguide.R;
import com.example.cityguide.RegisterActivity;
import com.example.cityguide.WeatherActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;


public class fragment_home extends Fragment implements LocationListener {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;

    protected LocationManager locationManager;
    protected Context context;

    TextView txttemp, txthumi, txtvisibility, txtdesc, txtlocation, btnsearchweather, ufname, uemail, uphone;
    String latitude, longitude;
    ImageView imageicon, img_logut;


    public class Weather extends AsyncTask<String, Void, String> {// first String means Url is in string, Void mean nothing, Third String means return type will be in string

        @Override
        protected String doInBackground(String... address) {
//String... means multiple address can be send. It acts as array
            try {
                java.net.URL url = new java.net.URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //Establish connection with address
                connection.connect();

                //retrieve data from url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                //Retrieve data and return it as String
                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                Log.i("Content", content);
                return content;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        txttemp = (TextView) view.findViewById(R.id.txtmaintemp);
        txtlocation = (TextView) view.findViewById(R.id.txtlocation);
        txtdesc = (TextView) view.findViewById(R.id.txtdesc);
        txthumi = (TextView) view.findViewById(R.id.txthumiditydata);
        txtvisibility = (TextView) view.findViewById(R.id.txtvisibilitydata);
        btnsearchweather = (TextView) view.findViewById(R.id.btnsearchweather);
        imageicon = (ImageView) view.findViewById(R.id.imgicon);


        ufname = (TextView) view.findViewById(R.id.ufname);
        uemail = (TextView) view.findViewById(R.id.uemail);
        uphone = (TextView) view.findViewById(R.id.uphone);
        img_logut = (ImageView) view.findViewById(R.id.imglogout);

        img_logut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                getActivity().finish();
            }
        });

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);


        btnsearchweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WeatherActivity.class);
                getActivity().startActivity(intent);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        DocumentReference docref = fstore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ufname.setText(documentSnapshot.getString("FirstName") + documentSnapshot.getString("LastName"));
                    uemail.setText(documentSnapshot.getString("Emailaddress"));
                    uphone.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
                }
            }
        });
        return view;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        // textview2.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        double lat = location.getLatitude();
        double longi = location.getLongitude();
        latitude = String.valueOf(lat);
        longitude = String.valueOf(longi);
        result(latitude, longitude);
    }

    private void result(String latitude, String longitude) {
        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=5baf7c79f6786dd8d786e4632d7e68dc").get();
            Log.i("contentData", content);
            //JSON
            JSONObject jsonObject = new JSONObject(content);
            String weatherDATA = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main");// it is not main array . it is seperate vrialble like weather
            String Location;
            double visibility;
            Log.i("weatherDATA", weatherDATA);

//            /Weather Data is in Array
            JSONArray array = new JSONArray(weatherDATA);
            String main = "";
            String description = "";
            String temperature = "";
            String humidity = "";
            String icon = "";

            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
                icon = weatherPart.getString("icon");
            }
            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");
            humidity = mainPart.getString("humidity");
            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            //by default visibility is in meter:
            int visibilityinKilometer = (int) visibility / 1000;

            double tempincelcius = Double.parseDouble(temperature) - 273.15;
            double tempincelfernheit = (Double.parseDouble(temperature) - 273.15) * 9 / 5 + 32;

//            for location
            Location = jsonObject.getString("name");
            txtlocation.setText(Location);
            txttemp.setText(tempincelcius + " °C | " + tempincelfernheit + " °F");
            txthumi.setText(humidity + "%");
            txtvisibility.setText(visibilityinKilometer + "km");
            txtdesc.setText(description);
            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(imageicon);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}