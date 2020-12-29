package com.example.cityguide;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;

public class WeatherActivity extends AppCompatActivity {
    EditText CityName;
    Button searchButton;
    TextView result;

    class Weather extends AsyncTask<String, Void, String> {// first String means Url is in string, Void mean nothing, Third String means return type will be in string


        @Override
        protected String doInBackground(String... address) {
//String... means multiple address can be send. It acts as array
            try {
                URL url = new URL(address[0]);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        CityName = findViewById(R.id.etplaceName);
        CityName.setEnabled(true);
        searchButton = findViewById(R.id.searchButton);
        result = findViewById(R.id.result);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWeather();
            }
        });
    }

    private void searchWeather() {

        String cName = CityName.getText().toString();
        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q="+cName+"&appid=5baf7c79f6786dd8d786e4632d7e68dc").get();
            //First we will check data is retrieve successfully or not
            Log.i("contentData", content);

            //JSON
            JSONObject jsonObject = new JSONObject(content);
            String weatherDATA = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main");// it is not main array . it is seperate vrialble like weather

            String Location;

            double visibility;

            Log.i("weatherDATA", weatherDATA);

            //Weather Data is in Array

            JSONArray array = new JSONArray(weatherDATA);
            String main = "";
            String description = "";
            String temperature = "";
            String humidity = "";

            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");
            humidity = mainPart.getString("humidity");
            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            //by default visibility is in meter:
            int visibilityinKilometer = (int) visibility / 1000;
            double tempincelcius = Double.parseDouble(temperature) - 273.15 ;
            double tempincelfernheit = (Double.parseDouble(temperature) - 273.15) * 9/5 + 32 ;

            NumberFormat tfc= NumberFormat.getInstance();
            tfc.setMaximumFractionDigits(2);
            String tempfc = tfc.format(tempincelcius);
            String tempff = tfc.format(tempincelfernheit);

//            for location
            Location = jsonObject.getString("name");
            result.setText("Main : " + main +
                    "\n \n" + "Description : " + description +
                    "\n \n" + "Temperature : " + tempfc  + " °C | "+ tempff  +" °F" +
                    "\n \n" + "Visibility : " + visibilityinKilometer + "km" +
                    "\n \n" + "Humidity : " + humidity + "%"+
                    "\n \n" + "Location : " + Location);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}