package com.saveetha.tutorfinder.student.listview;

public class DataSetClass {
    private String title;
    private String imgid;
    private String teaches;
    private String tutorId;
    private String courseId;

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

    public String getTeaches() {
        return teaches;
    }

    public void setTeaches(String teaches) {
        this.teaches = teaches;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImgid() {
        return imgid;
    }
    public void setImgid(String imgid) {
        this.imgid = imgid;
    }
    public DataSetClass(String title, String imgid,String teaches,String tutorId,String courseId) {
        this.title = title;
        this.imgid = imgid;
        this.teaches=teaches;
        this.courseId=courseId;
        this.tutorId=tutorId;
    }

}
