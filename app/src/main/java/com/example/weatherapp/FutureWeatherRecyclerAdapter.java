package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

// recyclerview adapter
public class FutureWeatherRecyclerAdapter extends RecyclerView.Adapter<FutureWeatherRecyclerAdapter.Holder> {

    private ArrayList<String[]> futurerWeathersList = new ArrayList<>();
    private Context context;
    RequestOptions option;

    public FutureWeatherRecyclerAdapter(Context context){
        this.context = context;
        option = new RequestOptions().centerCrop();

    }

    @NonNull
    @Override
    public FutureWeatherRecyclerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weatherrecycler,parent,false);
        Holder viewHolder = new Holder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FutureWeatherRecyclerAdapter.Holder holder, int position) {

        String[] weather = futurerWeathersList.get(position);
        holder.date.setText(weather[3]);
        String highLow = weather[1] + " / "+ weather[2];
        holder.highLowTemperatureInRecycler.setText(highLow);
        String url = "http://openweathermap.org/img/wn/"+weather[0]+"@2x.png";
        Glide.with(context).load(url).apply(option).into(holder.Image);

    }

    @Override
    public int getItemCount() {
        return futurerWeathersList.size();
    }

    public void setWeathers(ArrayList<String[]> futurerWeathers) {

        this.futurerWeathersList = futurerWeathers;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder  {

        private ImageView Image;
        private TextView date, highLowTemperatureInRecycler;



        public Holder(View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Image);
            date = itemView.findViewById(R.id.date);
            highLowTemperatureInRecycler = itemView.findViewById(R.id.highLowTemperatureInRecycler);

        }
    }
}
