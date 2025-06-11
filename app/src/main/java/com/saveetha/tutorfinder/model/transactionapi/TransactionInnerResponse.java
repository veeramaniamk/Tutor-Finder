package com.saveetha.tutorfinder.model.transactionapi;

public class TransactionInnerResponse {
    public String id;
    public String user_id;
    public String credits;
    public String date_of_action;
    public String purpose;
    public String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getDate_of_action() {
        return date_of_action;
    }

    public void setDate_of_action(String date_of_action) {
        this.date_of_action = date_of_action;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
