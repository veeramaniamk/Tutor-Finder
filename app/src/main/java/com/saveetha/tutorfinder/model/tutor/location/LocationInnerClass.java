package com.saveetha.tutorfinder.model.tutor.location;

import java.util.ArrayList;

public class LocationInnerClass {
    public String state;
    public ArrayList<City> city;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<City> getCity() {
        return city;
    }

    public void setCity(ArrayList<City> city) {
        this.city = city;
    }
}
