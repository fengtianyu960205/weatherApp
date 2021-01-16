package com.example.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityTempFragment extends Fragment {
    private Integer cityCode;
    private Double lng,log;
    private String name;
    private TextView currentTemperature,highLowTemperature,weatherCondition,cityName;
    private getCityTemperatureViewModel cityTempViewModel;
    private  GetCityfutureTempViewModel getCityfutureTempViewModel;
    private ArrayList<String> cityTemp = new ArrayList<>();
    private ArrayList<String[]> futureTemps = new ArrayList<>();
    FutureWeatherRecyclerAdapter futureWeatherRecyclerAdapter;

    public CityTempFragment(Integer cityCode, Double lng, Double log,String name){
        this.cityCode = cityCode;
        this.lng = lng;
        this.log = log;
        this.name = name;
    }

    public CityTempFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weatherfrgmant, container, false);
        currentTemperature = view.findViewById(R.id.currentTemperature);
        highLowTemperature = view.findViewById(R.id.highLowTemperature);
        weatherCondition = view.findViewById(R.id.weatherCondition);
        cityName = view.findViewById(R.id.cityName);
        cityTempViewModel = new ViewModelProvider(this).get(getCityTemperatureViewModel.class);
        cityTempViewModel.GetCurrentCitytempinfo(cityCode);
        cityTempViewModel.gettempInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
                cityTemp = arrayList;
                currentTemperature.setText(cityTemp.get(1));
                weatherCondition.setText(cityTemp.get(0));
                String highTemp = cityTemp.get(2);
                String lowTemp = cityTemp.get(3);
                highLowTemperature.setText(highTemp + " / " + lowTemp);
                cityName.setText(name);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recyclerWeather);
        futureWeatherRecyclerAdapter = new FutureWeatherRecyclerAdapter(getContext());
        recyclerView.setAdapter(futureWeatherRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        getCityfutureTempViewModel = new ViewModelProvider(this).get(GetCityfutureTempViewModel.class);
        getCityfutureTempViewModel.GetCurrentCitytempinfo(lng,log);
        getCityfutureTempViewModel.gettempInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
                futureTemps = arrayList;
                futureWeatherRecyclerAdapter.setWeathers(futureTemps);
            }
        });
        return view;
    }
}
