package com.saveetha.tutorfinder.model.tutor;

import java.util.ArrayList;

public class TutorHomeResponse {

    private int status;
    private String message;
    private ArrayList<TutorHomeResponseInnerClass> data;

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

    public ArrayList<TutorHomeResponseInnerClass> getData() {
        return data;
    }

    public void setData(ArrayList<TutorHomeResponseInnerClass> data) {
        this.data = data;
    }

    public class TutorHomeResponseInnerClass{
        private String tutor_id;
        private String image;
        private String name;
        private String id;
        private String course_id;


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
