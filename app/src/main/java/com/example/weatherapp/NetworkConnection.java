package com.example.weatherapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnection {

    private OkHttpClient client = null;
    public NetworkConnection() {
        client = new OkHttpClient();
    }

    // this method is to fetch current  weather forecast
    public String getTempByCity(Integer cityCode) {
        String path = "https://api.openweathermap.org/data/2.5/weather?id=" + cityCode + "&appid=b877d28bec2f7ba4a4d306e11af6e274";
        Request.Builder builder = new Request.Builder();
        builder.url(path);
        Request request = builder.build();
        String results = "kkk";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // this method is to fetch following three days weather forecast
    public String getFutureTempByCity(Double latitude, Double longitude) {
        String path ="https://api.openweathermap.org/data/2.5/onecall?lat="+latitude + "&lon="+longitude+"&exclude=hourly,minutely&units=metric&appid=b877d28bec2f7ba4a4d306e11af6e274" ;
        Request.Builder builder = new Request.Builder();
        builder.url(path);
        Request request = builder.build();
        String results = "kkk";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}


