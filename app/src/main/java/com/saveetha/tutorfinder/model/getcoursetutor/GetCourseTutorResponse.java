package com.saveetha.tutorfinder.model.getcoursetutor;

import java.util.ArrayList;

public class GetCourseTutorResponse {
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataObjectValues> getData() {
        return data;
    }

    public void setData(ArrayList<DataObjectValues> data) {
        this.data = data;
    }

    private String message;
    private ArrayList<DataObjectValues> data;
}
