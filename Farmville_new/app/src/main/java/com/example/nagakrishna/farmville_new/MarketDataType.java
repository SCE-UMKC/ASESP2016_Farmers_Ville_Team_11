package com.example.nagakrishna.farmville_new;

/**
 * Created by Naga Krishna on 10-03-2016.
 */
public class MarketDataType {

    private String name;
    private String marketID;
    private String distance;

    public MarketDataType(){

    }

    public MarketDataType(String name, String marketID){
        this.name = name;
        this.marketID = marketID;
    }
    public String getName() { return name; }

    public void setName(String name) {this.name = name; }

    public void setMarketID(String marketID) {
        this.marketID = marketID;
    }

    public String getMarketID() {
        return marketID;
    }

    public String getDistance() {return distance; }

    public void setDistance(String distance) {this.distance = distance; }


}
