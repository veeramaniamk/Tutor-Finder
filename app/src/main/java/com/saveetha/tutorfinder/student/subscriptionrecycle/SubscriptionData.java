package com.saveetha.tutorfinder.student.subscriptionrecycle;

public class SubscriptionData {
    String packageName;
    String subscriptionDate;
    String paymentType;
    String credits;
    String amountPaid;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public SubscriptionData(String packageName, String subscriptionDate, String paymentType, String credits, String amountPaid) {
        this.packageName = packageName;
        this.subscriptionDate = subscriptionDate;
        this.paymentType = paymentType;
        this.credits = credits;
        this.amountPaid = amountPaid;
    }
}
