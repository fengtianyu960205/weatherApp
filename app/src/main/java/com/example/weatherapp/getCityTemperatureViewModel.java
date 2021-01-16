package com.example.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// this view model is to get current weather forecast
public class getCityTemperatureViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList> tempInfo;
    private Context context;
    ArrayList<String> list = new ArrayList<>();

    public getCityTemperatureViewModel(){
        tempInfo = new MutableLiveData<>();
    }

    public LiveData<ArrayList> gettempInfo(){
        return tempInfo;
    }

    public void settempInfo(ArrayList<String> temperature) {
        tempInfo.setValue(temperature);
    }

    public void GetCurrentCitytempinfo(Integer cityCode){
        GetTempInfoTask getcityTempInfo = new GetTempInfoTask();
        getcityTempInfo.execute(cityCode);
    }

    private class GetTempInfoTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return networkConnection.getTempByCity(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                JSONObject jsonArrayTemperature = jsonObject.getJSONObject("main");
                list = new ArrayList<>();
                if (jsonArrayWeather != null && jsonArrayWeather.length() > 0 ){

                    // fetch data from open weather api and convert data to correct format
                    JSONObject weatherInfo = jsonArrayWeather.getJSONObject(0);
                    String weatherCondition = weatherInfo.getString("main");
                    Double currentTemp = jsonArrayTemperature.getDouble("temp");
                    Double highTemp = jsonArrayTemperature.getDouble("temp_max");
                    Double lowTemp = jsonArrayTemperature.getDouble("temp_min");
                    Double currentTempCelsius = currentTemp - 273.15;
                    Double highTempCelsius = highTemp - 273.15;
                    Double lowTempCelsius = lowTemp - 273.15;
                    Integer currentTempCelsiusInt = currentTempCelsius.intValue();
                    Integer highTempCelsiusInt = highTempCelsius.intValue();
                    Integer lowTempCelsiusInt = lowTempCelsius.intValue();


                    String currentTempCelsiusStr = currentTempCelsiusInt.toString();
                    String highTempCelsiusStr = highTempCelsiusInt.toString();
                    String lowTempCelsiusStr = lowTempCelsiusInt.toString();


                    list.add(weatherCondition);
                    list.add(currentTempCelsiusStr);
                    list.add(highTempCelsiusStr);
                    list.add(lowTempCelsiusStr);


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



}
