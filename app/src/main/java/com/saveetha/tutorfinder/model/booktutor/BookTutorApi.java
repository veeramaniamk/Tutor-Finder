package com.saveetha.tutorfinder.model.booktutor;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BookTutorApi {
    @FormUrlEncoded
    @POST("book_tutor.php")
    Call<ResponseBookTutor> bookTutor(@Field("student")String student, @Field("tutor")String tutor, @Field("course")String course,
                                      @Field("date")String date, @Field("message")String message, @Field("location")String location
            , @Field("slot")String slot);
}
