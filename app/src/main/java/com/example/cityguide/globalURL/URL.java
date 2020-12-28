package com.example.cityguide.globalURL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class URL {
    //public static final String base_url ="http://10.1.13.74:3000/"; //=> pc id address
//    public static final String base_url = "http://10.1.13.77:3000/"; //colz ip address
    public static final String base_url = "http://10.0.2.2:3000/"; //localhost ip address
    // public static final String base_url = "http://10.1.1.69:3000/"; //localhost ip address


    public  static String token = "Bearer ";
    public static String imagePath = base_url + "uploads/" ;

    public URL(String urlString) {

    }

    public static Retrofit getInstance()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


}
