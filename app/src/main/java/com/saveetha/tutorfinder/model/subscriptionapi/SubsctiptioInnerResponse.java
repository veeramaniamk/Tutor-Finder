package com.saveetha.tutorfinder.model.subscriptionapi;

public class SubsctiptioInnerResponse {
    public String id;
    public String user_id;
    public String package_name;
    public String subscribe_date;
    public String payment_type;
    public String credits;
    public String amount_paid;

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

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getSubscribe_date() {
        return subscribe_date;
    }

    public void setSubscribe_date(String subscribe_date) {
        this.subscribe_date = subscribe_date;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }
}
