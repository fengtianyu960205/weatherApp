package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView currentTemperature,highLowTemperature,weatherCondition;
    private getCityTemperatureViewModel cityTempViewModel;
    private ArrayList<String> cityTemp = new ArrayList<>();
    private ArrayList<String> test = new ArrayList<>();
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewPager2);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(),getLifecycle());
        viewPageAdapter.addFragment(new CityTempFragment(7839805,-37.4713,144.7852,"Melbourne"));
        viewPageAdapter.addFragment(new CityTempFragment(2147714,-33.8688,151.2093,"Sydney"));
        viewPageAdapter.addFragment(new CityTempFragment(2153391,-31.933331,115.833328,"Perth"));
        viewPageAdapter.addFragment(new CityTempFragment(2163355,-42.87936,147.329407,"Hobart"));
        viewPager2.setAdapter(viewPageAdapter);
    }


}
