package com.saveetha.tutorfinder.model.transactionapi;

import java.util.ArrayList;

public class TransactionResponse {
    public int status;
    public String message;
    public ArrayList<TransactionInnerResponse> data;

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

    public ArrayList<TransactionInnerResponse> getData() {
        return data;
    }

    public void setData(ArrayList<TransactionInnerResponse> data) {
        this.data = data;
    }
}
