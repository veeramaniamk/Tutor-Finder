package com.saveetha.tutorfinder.model.listpackages;

import java.util.ArrayList;

public class ListPackageResponse {
    public int status;
    public String message;
    public ArrayList<ListPackageInner> data;


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

    public ArrayList<ListPackageInner> getData() {
        return data;
    }

    public void setData(ArrayList<ListPackageInner> data) {
        this.data = data;
    }

}
