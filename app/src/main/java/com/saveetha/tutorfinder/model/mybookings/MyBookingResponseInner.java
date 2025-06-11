package com.saveetha.tutorfinder.model.mybookings;

public class MyBookingResponseInner {
    public String booking_id;
    public String student_id;
    public String tutor_id;
    public String course_id;
    public String tutor_name;
    public String course_name;
    public String duration;
    public String fee;
    public String commence_date;
    public String time_slot;
    public String location;
    public String prev_status;

    public String status;

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String student_name;

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(String tutor_id) {
        this.tutor_id = tutor_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getTutor_name() {
        return tutor_name;
    }

    public void setTutor_name(String tutor_name) {
        this.tutor_name = tutor_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCommence_date() {
        return commence_date;
    }

    public void setCommence_date(String commence_date) {
        this.commence_date = commence_date;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrev_status() {
        return prev_status;
    }

    public void setPrev_status(String prev_status) {
        this.prev_status = prev_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
