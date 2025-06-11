package com.saveetha.tutorfinder.model.subscriptionapi;

import java.util.ArrayList;

public class SubscriptionResponse {
    public int status;
    public String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SubsctiptioInnerResponse> getData() {
        return data;
    }

    public void setData(ArrayList<SubsctiptioInnerResponse> data) {
        this.data = data;
    }

    public ArrayList<SubsctiptioInnerResponse> data;
}
