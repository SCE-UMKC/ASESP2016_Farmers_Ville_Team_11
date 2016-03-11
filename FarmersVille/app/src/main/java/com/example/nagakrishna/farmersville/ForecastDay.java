package com.example.nagakrishna.farmersville;

import java.text.DecimalFormat;

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
        this.description = description;
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
