package com.example.nagakrishna.farmersville;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naga Krishna on 18-02-2016.
 */
public class WeatherForecast {
    private List<ForecastDay> daysForecast = new ArrayList<ForecastDay>();

    public void addForecast(ForecastDay forecast) {
        daysForecast.add(forecast);
    }

    public ForecastDay getForecast(int dayNum) {
        return daysForecast.get(dayNum);
    }
}
