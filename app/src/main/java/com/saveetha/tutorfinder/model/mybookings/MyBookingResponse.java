package com.saveetha.tutorfinder.model.mybookings;

import java.util.ArrayList;

public class MyBookingResponse {
    public int status;
    public String message;
    public ArrayList<MyBookingResponseInner> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<MyBookingResponseInner> getData() {
        return data;
    }

    public void setData(ArrayList<MyBookingResponseInner> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
