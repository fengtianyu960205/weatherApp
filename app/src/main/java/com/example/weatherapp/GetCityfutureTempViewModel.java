package com.example.weatherapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class GetCityfutureTempViewModel extends ViewModel {
    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList> tempInfo;
    private Context context;
    ArrayList<String[]> list = new ArrayList<>();

    public GetCityfutureTempViewModel(){
        tempInfo = new MutableLiveData<>();
    }

    public LiveData<ArrayList> gettempInfo(){
        return tempInfo;
    }

    public void settempInfo(ArrayList<String[]> temperature) {
        tempInfo.setValue(temperature);
    }

    public void GetCurrentCitytempinfo(Double latitude, Double longitude){
        GetCityfutureTempViewModel.GetTempInfoTask getcityTempInfo = new GetCityfutureTempViewModel.GetTempInfoTask();
        getcityTempInfo.execute(latitude,longitude);
    }

    private class GetTempInfoTask extends AsyncTask<Double, Void, String> {
        @Override
        protected String doInBackground(Double... params) {
            return networkConnection.getFutureTempByCity(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArrayWeather = jsonObject.getJSONArray("daily");

                list = new ArrayList<>();
                if (jsonArrayWeather != null && jsonArrayWeather.length() > 0 ){
                    for (int i = 1; i < jsonArrayWeather.length() ; i++) {
                        String[] weatherlist = new String[4];
                        JSONObject weatherInfo = jsonArrayWeather.getJSONObject(i);
                        Long UTCdate = weatherInfo.getLong("dt");
                        String dateStr = convertTimeMillionsToDate(UTCdate);
                        JSONObject temp = weatherInfo.getJSONObject("temp");
                        Double highTemp = temp.getDouble("max");
                        Integer highTempint = highTemp.intValue();
                        String hightempStr = highTempint.toString();
                        Double lowTemp = temp.getDouble("min");
                        Integer lowTempInt =  lowTemp.intValue();
                        String lowTempStr = lowTempInt.toString();
                        JSONArray weather = weatherInfo.getJSONArray("weather");
                        JSONObject weatherCondition = weather.getJSONObject(0);
                        String weatherConditionStr = weatherCondition.getString("icon");
                        weatherlist[0] = weatherConditionStr;
                        weatherlist[1] = hightempStr;
                        weatherlist[2] = lowTempStr;
                        weatherlist[3] = dateStr;
                        list.add(weatherlist);
                        if (i == 3){
                            break;
                        }

                    }

                }
                else{
                    list = new ArrayList<>();
                }

                tempInfo.setValue(list);
            } catch (Exception e) {
                tempInfo.setValue(list);
            }
        }
    }

    public String convertTimeMillionsToDate(Long timemillions ){

        ZonedDateTime z = Instant.ofEpochSecond(timemillions).atZone(ZoneId.of("Europe/London"));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'XX (zzzz)", Locale.ENGLISH);
        String  date =  fmt.format(z);
        String[] dates = date.split(" ");
        String correctDate = dates[0]+" "+dates[1]+" "+dates[2];
        return correctDate;
    }

}
