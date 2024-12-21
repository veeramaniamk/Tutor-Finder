package com.saveetha.tutorfinder.model.profile;

public class ProfileResponse {
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

    public ProfileInnerResponse getData() {
        return data;
    }

    public void setData(ProfileInnerResponse data) {
        this.data = data;
    }

    public ProfileInnerResponse data;
}
