package com.example.nagakrishna.farmersville;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Naga Krishna on 18-02-2016.
 */
public class ForecastDay {
//    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    public ForecastTemp forecastTemp = new ForecastTemp();
//    public WeatherDetails weatherDetails = new WeatherDetails();
//    public long timestamp;

    public ForecastDay(){

    }
    public String dateTime;
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String Temperature;
    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(double temperature) {
        double temp = temperature - 273;
        DecimalFormat df = new DecimalFormat("#.#");
        String tempc = df.format(temp);
        tempc = tempc + " °C";
        this.Temperature = tempc;
    }

    public String description;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public String getMain() {
        return main;
    }

    public String getIcon() {
        return icon;
    }

    public String getClouds() {
        return clouds;
    }

    public void setPressure(Double pressure) {
        String p = Double.toString(pressure);
        this.pressure = p + " hPa";
    }

    public void setHumidity(Double humidity) {
        String h = Double.toString(humidity);
        this.humidity = h + " %";
    }

    public void setWindspeed(Double windspeed) {
        String w = Double.toString(windspeed);
        this.windspeed = w + " m/s";
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setIcon(String icon) {
        this.icon = 'a' + icon;
    }

    public void setClouds(int clouds) {
        String c = Integer.toString(clouds);
        this.clouds = (c + " %");
    }

    public String pressure;
    public String  humidity;
    public String windspeed;
    public String main;
    public String icon;
    public String clouds;






//    public ForecastDay(String dateTime){
//        this.dateTime = dateTime;
//    }



//    public class ForecastTemp {
//        public float temp;
//        public float temp_min;
//        public float temp_max;
//        public float pressure;
//        public float humidity;
//    }
//
//    public class WeatherDetails{
//        public String description;
//        public String main;
//        public int id;
//        public String icon;
//
//    }
//    public String getStringDate(long timestamp) {
//        Calendar calendar = Calendar.getInstance();
//        TimeZone tz = TimeZone.getDefault();
//        calendar.setTimeInMillis(timestamp * 1000);
//        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
//        Date currenTimeZone = (Date) calendar.getTime();
//        return sdf.format(currenTimeZone);
////        return sdf.format(new Date(timestamp));
//    }
}
