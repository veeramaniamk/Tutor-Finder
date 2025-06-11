package com.saveetha.tutorfinder.model.reservespot;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("get_course_details.php")
    Call<ReserveSpotResponse> getReserveSpot(@Field("course")String parm,@Field("tutor")String parm1);
}
