package com.saveetha.tutorfinder.model.tutor;

import java.util.ArrayList;

public class TutorCourseDetailsResponse {

    private int status;
    private String message;

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

    public ArrayList<TutorCourseDetailsResponseInnerResponse> getData() {
        return data;
    }

    public void setData(ArrayList<TutorCourseDetailsResponseInnerResponse> data) {
        this.data = data;
    }

    private ArrayList<TutorCourseDetailsResponseInnerResponse> data;
    public class TutorCourseDetailsResponseInnerResponse{
        private String id;
        private String course_id;
        private String tutor_id;
        private String duration_value;
        private String duration_type;
        private String fee;
        private String content;
        private String time_slots;
        private String days_off;
        private String image;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getTutor_id() {
            return tutor_id;
        }

        public void setTutor_id(String tutor_id) {
            this.tutor_id = tutor_id;
        }

        public String getDuration_value() {
            return duration_value;
        }

        public void setDuration_value(String duration_value) {
            this.duration_value = duration_value;
        }

        public String getDuration_type() {
            return duration_type;
        }

        public void setDuration_type(String duration_type) {
            this.duration_type = duration_type;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime_slots() {
            return time_slots;
        }

        public void setTime_slots(String time_slots) {
            this.time_slots = time_slots;
        }

        public String getDays_off() {
            return days_off;
        }

        public void setDays_off(String days_off) {
            this.days_off = days_off;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
