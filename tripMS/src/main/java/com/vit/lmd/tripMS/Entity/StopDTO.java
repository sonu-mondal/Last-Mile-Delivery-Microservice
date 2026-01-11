package com.vit.lmd.tripMS.Entity;


public class StopDTO {

    private int stopId;
    private int tripId;
    private String address;
    private String items;
    private String stopType;

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getStopType() {
        return stopType;
    }

    public void setStopType(String stopType) {
        this.stopType = stopType;
    }

    public StopDTO(int stopId, int tripId, String address, String items, String stopType) {
        this.stopId = stopId;
        this.tripId = tripId;
        this.address = address;
        this.items = items;
        this.stopType = stopType;
    }

}
