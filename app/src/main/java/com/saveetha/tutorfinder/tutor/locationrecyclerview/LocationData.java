package com.saveetha.tutorfinder.tutor.locationrecyclerview;

public class LocationData {

    String state;
    String[] cityName;
//    String[] ciryId;
    String[] status;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String[] getCityName() {
        return cityName;
    }

    public void setCityName(String[] cityName) {
        this.cityName = cityName;
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }
    String[] cityId;

    public String[] getCityId() {
        return cityId;
    }

    public void setCityId(String[] cityId) {
        this.cityId = cityId;
    }

    public LocationData(String state, String[] cityName, String[] status, String[] cityId) {
        this.state = state;
        this.cityName = cityName;
        this.status = status;
        this.cityId=cityId;
    }
}
