package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Naga Krishna on 25-02-2016.
 */
public class CustomListAdapter extends BaseAdapter{
    Context context;
    private List<ForecastDay> forecastDay;
    LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, List<ForecastDay> forecastDays) {
        this.context = context;
        this.forecastDay = forecastDays;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.forecastDay.size();
    }

    public Object getItem(int position) {
        return this.forecastDay.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.layoutInflater == null) {
            this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.weather_listview, null);
        }
        TextView temperature = (TextView) convertView.findViewById(R.id.listTemperature);
        TextView temp_high = (TextView) convertView.findViewById(R.id.listTempHigh);
        TextView temp_low = (TextView) convertView.findViewById(R.id.listTempLow);
        TextView description = (TextView) convertView.findViewById(R.id.listDescription);
//        TextView main1 = (TextView) convertView.findViewById(R.id.listMain);
        TextView pressure = (TextView) convertView.findViewById(R.id.listPressure);
        TextView humidity = (TextView) convertView.findViewById(R.id.listHumidity);
        TextView windspeed = (TextView) convertView.findViewById(R.id.listWindSpeed);
        TextView clouds = (TextView) convertView.findViewById(R.id.listClouds);
        ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        TextView date = (TextView) convertView.findViewById(R.id.listDate);
        TextView time = (TextView) convertView.findViewById(R.id.listTime);
        ForecastDay fd = (ForecastDay) this.forecastDay.get(position);
        ((TextView) convertView.findViewById(R.id.listDate)).setText(String.valueOf(fd.dateTime));
        temperature.setText(fd.getTemperature());
        description.setText(fd.getDescription());
//        main1.setText(fd.getMain());
        date.setText(fd.getDate());
        time.setText(fd.getTime());
        pressure.setText(fd.getPressure());
        humidity.setText(fd.getHumidity());
        windspeed.setText(fd.getWindspeed());
        clouds.setText(fd.getClouds());
        temp_high.setText(fd.getTemp_high());
        temp_low.setText(fd.getTemp_low());
        imageViewIcon.setImageDrawable(this.context.getResources().getDrawable(this.context.getResources().getIdentifier("@drawable/" + fd.getIcon(), null, this.context.getPackageName())));
        return convertView;
    }

}
