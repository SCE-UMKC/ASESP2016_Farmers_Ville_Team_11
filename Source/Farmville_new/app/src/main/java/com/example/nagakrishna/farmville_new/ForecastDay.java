package com.example.nagakrishna.farmville_new;

import java.text.DecimalFormat;

/**
 * Created by Naga Krishna on 25-02-2016.
 */
public class ForecastDay {
    public String Temperature;
    public String clouds;
    public String dateTime;
    public String description;
    public String humidity;
    public String icon;
    public String main;
    public String pressure;
    public String windspeed;
    public String date;
    public String time;
    public String temp_high;
    public String temp_low;

    public void setTemp_low(Double temp_low) {
        this.temp_low = new DecimalFormat("#.###").format(temp_low - 273.0d) + " \u00b0C";
    }

    public void setTemp_high(Double temp_high) {
        this.temp_high = new DecimalFormat("#.###").format(temp_high - 273.0d) + " \u00b0C";
    }

    public String getTemp_high() {
        return temp_high;
    }

    public String getTemp_low() {
        return temp_low;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTemperature() {
        return this.Temperature;
    }

    public void setTemperature(double temperature) {
        this.Temperature = new DecimalFormat("#.#").format(temperature - 273.0d) + " \u00b0C";
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        char[] charArray = description.toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[i] == ' ') {
                charArray[i + 1] = Character.toUpperCase(charArray[i + 1]);
            }
        }
        String result = new String(charArray);
        this.description = result;
    }


    public String getPressure() {
        return this.pressure;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public String getWindspeed() {
        return this.windspeed;
    }

    public String getMain() {
        return this.main;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getClouds() {
        return this.clouds;
    }

    public void setPressure(Double pressure) {
        this.pressure = Double.toString(pressure.doubleValue()) + " hPa";
    }

    public void setHumidity(Double humidity) {
        this.humidity = Double.toString(humidity.doubleValue()) + " %";
    }

    public void setWindspeed(Double windspeed) {
        this.windspeed = Double.toString(windspeed.doubleValue()) + " m/s";
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setIcon(String icon) {
        this.icon = 'a' + icon;
    }

    public void setClouds(int clouds) {
        this.clouds = Integer.toString(clouds) + " %";
    }
}
