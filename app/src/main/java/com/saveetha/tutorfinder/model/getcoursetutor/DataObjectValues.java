package com.saveetha.tutorfinder.model.getcoursetutor;

public class DataObjectValues {
    private String tutor_id;
    private String course_id;
    private String username;
    private String photo;
    private String teaches;

    public String getTeaches() {
        return teaches;
    }

    public void setTeaches(String teaches) {
        this.teaches = teaches;
    }

    private String experience;
    private String qualification;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
