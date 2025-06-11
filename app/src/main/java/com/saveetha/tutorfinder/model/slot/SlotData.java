package com.saveetha.tutorfinder.model.slot;

public class SlotData {
    private int status;
    private String message;
    private String[] available_slots;

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

    public String[] getAvailable_slots() {
        return available_slots;
    }

    public void setAvailable_slots(String[] available_slots) {
        this.available_slots = available_slots;
    }
}
