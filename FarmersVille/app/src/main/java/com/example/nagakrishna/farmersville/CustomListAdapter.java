package com.example.nagakrishna.farmersville;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Naga Krishna on 19-02-2016.
 */
public class CustomListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private List<ForecastDay> forecastDay;


    public CustomListAdapter(Context context, List<ForecastDay> forecastDays){
        this.context = context;
        this.forecastDay = forecastDays;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return forecastDay.size();
    }

    @Override
    public Object getItem(int position) {
        return forecastDay.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null){
            layoutInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.weather_listview, null);
        TextView date = (TextView)convertView.findViewById(R.id.listDate);
        TextView temperature = (TextView)convertView.findViewById(R.id.listTemperature);
        TextView description = (TextView)convertView.findViewById(R.id.listDescription);
        TextView main1 = (TextView)convertView.findViewById(R.id.listMain);
        TextView pressure = (TextView)convertView.findViewById(R.id.listPressure);
        TextView humidity = (TextView)convertView.findViewById(R.id.listHumidity);
        TextView windspeed = (TextView)convertView.findViewById(R.id.listWindSpeed);
        TextView clouds = (TextView)convertView.findViewById(R.id.listClouds);
        ImageView imageViewIcon = (ImageView)convertView.findViewById(R.id.imageViewIcon);
        ForecastDay fd = forecastDay.get(position);
        date.setText(String.valueOf(fd.dateTime));
        temperature.setText(fd.getTemperature());
        description.setText(fd.getDescription());
        main1.setText(fd.getMain());
        pressure.setText(fd.getPressure());
        humidity.setText(fd.getHumidity());
        windspeed.setText(fd.getWindspeed());
        clouds.setText(fd.getClouds());
        String uri = fd.getIcon();
        uri = "@drawable/" + uri;
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        imageViewIcon.setImageDrawable(res);



        return convertView;

    }
}
