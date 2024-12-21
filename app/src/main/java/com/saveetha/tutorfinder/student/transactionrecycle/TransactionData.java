package com.saveetha.tutorfinder.student.transactionrecycle;

public class TransactionData {
    String credits;
    String transactionDate;
    String purpose;
    String type;

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
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

    public TransactionData(String credits, String transactionDate, String purpose, String type) {
        this.credits = credits;
        this.transactionDate = transactionDate;
        this.purpose = purpose;
        this.type = type;
    }
}
