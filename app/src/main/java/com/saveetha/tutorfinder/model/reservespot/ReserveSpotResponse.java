package com.saveetha.tutorfinder.model.reservespot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class ReserveSpotResponse {

    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tutor_id")
    @Expose
    private String tutorId;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("time_slots")
    @Expose
    private String timeSlots;
    @SerializedName("mode")
    @Expose
    private String[] mode;
    @SerializedName("credits")
    @Expose
    private String credits;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("days_off")
    @Expose
    private String daysOff;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(String timeSlots) {
        this.timeSlots = timeSlots;
    }

    public String[] getMode() {
        return mode;
    }

    public void setMode(String[] mode) {
        this.mode = mode;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(String daysOff) {
        this.daysOff = daysOff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}