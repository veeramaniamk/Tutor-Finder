package com.saveetha.tutorfinder.student.mybooking;

public class MyBookingData {
        private String tutorName;
        private String courseName;
        private String duration;
        private String fees;
        private String commenceDate;
        private String timeSlot;
        private String location;
        private String status;

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getCommenceDate() {
        return commenceDate;
    }

    public void setCommenceDate(String commenceDate) {
        this.commenceDate = commenceDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    String bookingId;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public MyBookingData(String tutorName, String courseName, String duration, String fees, String commenceDate,
                         String timeSlot, String location, String status, String bookingId) {
        this.tutorName = tutorName;
        this.courseName = courseName;
        this.duration = duration;
        this.fees = fees;
        this.commenceDate = commenceDate;
        this.timeSlot = timeSlot;
        this.location = location;
        this.status = status;
        this.bookingId=bookingId;
    }
}
