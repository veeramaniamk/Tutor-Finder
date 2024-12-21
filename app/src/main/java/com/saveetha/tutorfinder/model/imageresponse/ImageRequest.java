package com.saveetha.tutorfinder.model.imageresponse;

import java.io.File;

public class ImageRequest {
    private File file;
    private String user_id;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
