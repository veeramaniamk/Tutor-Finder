package com.saveetha.tutorfinder;

import android.content.Context;
import android.widget.Toast;

public class AppConstants {
    private AppConstants() {}
    public static final String BASE_URL = "https://tutor.zoop.me/api/";
    public static final String PLATFORM = "mobile";
    public static final String SHARED_PREF_NAME = "tutorpref";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TYPE = "type";
    public static final String KEY_IMAGE="image";
    public static final String KEY_IS_LOGGED_IN = "is_logged_in";
    public static String[] LOGIN_TYPE_TEXT = {"Student", "Tutor"};
    public static final String Student = "Student";
    public static final String Tutor = "Tutor";
    public static final String[] COURSES = { "GRE","IELTS" };
    public void makeToastUpdateAvailable(Context context) {
        Toast.makeText(context, "Update Available", Toast.LENGTH_SHORT).show();
    }
}
