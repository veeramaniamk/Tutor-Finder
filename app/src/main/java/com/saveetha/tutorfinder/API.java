package com.saveetha.tutorfinder;

import android.graphics.Bitmap;

import com.saveetha.tutorfinder.model.GetBookingStatsuResponse;
import com.saveetha.tutorfinder.model.LoginRequest;
import com.saveetha.tutorfinder.model.LoginResponse;
import com.saveetha.tutorfinder.model.SignupResponse;
import com.saveetha.tutorfinder.model.booktutor.ResponseBookTutor;
import com.saveetha.tutorfinder.model.changepassword.ChangePasswordResponse;
import com.saveetha.tutorfinder.model.course.CourseResponse;
import com.saveetha.tutorfinder.model.getcoursetutor.GetCourseTutorResponse;
import com.saveetha.tutorfinder.model.imageresponse.ImageRequest;
import com.saveetha.tutorfinder.model.imageresponse.ImageResponse;
import com.saveetha.tutorfinder.model.listpackages.ListPackageResponse;
import com.saveetha.tutorfinder.model.mybookings.MyBookingResponse;
import com.saveetha.tutorfinder.model.paymentapi.PaymentResponse;
import com.saveetha.tutorfinder.model.profile.ProfileResponse;
import com.saveetha.tutorfinder.model.slot.SlotData;
import com.saveetha.tutorfinder.model.subscriptionapi.SubscriptionResponse;
import com.saveetha.tutorfinder.model.transactionapi.TransactionResponse;
import com.saveetha.tutorfinder.model.tutor.TutorCourseDetailsResponse;
import com.saveetha.tutorfinder.model.tutor.TutorHomeResponse;
import com.saveetha.tutorfinder.model.tutor.location.LocationResponse;
import com.saveetha.tutorfinder.model.tutor.updatelocation.UpdateLocationResponse;
import com.saveetha.tutorfinder.model.updateprofile.UpdateProfileRequest;
import com.saveetha.tutorfinder.model.updateprofile.UpdateProfileResponse;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface API {
    @POST("user_login.php")
    Call<LoginResponse> authenticateUser(@Body LoginRequest authenticate);
    @POST("signup.php")
    Call<SignupResponse> signup(@QueryMap Map<String,String> params);

    @POST("courses.php")
    Call<CourseResponse> course();
    @FormUrlEncoded
    @POST("get_course_tutors.php")
    Call<GetCourseTutorResponse> getCourseTutor(@Field("course")String course);
    @FormUrlEncoded
    @POST("check_slot_availability.php")
    Call<SlotData> getSlots(@Field("date")String date,@Field("course")String course,
                            @Field("tutor")String tutor,@Field("location")String location);
    @FormUrlEncoded
    @POST("book_tutor.php")
    Call<ResponseBookTutor> bookTutor(@Field("student")String student,@Field("tutor")String tutor,
                                      @Field("course")String course,@Field("date")String date,
                                      @Field("message")String message,@Field("location")String location
                                      ,@Field("slot")String slot);
    @FormUrlEncoded
    @POST("list_packages.php")
    Call<ListPackageResponse> listPackages(@Field("type")String type);
    @FormUrlEncoded
    @POST("my_bookings.php")
    Call<MyBookingResponse> myBooking(@Field("student")String student);
    @FormUrlEncoded
    @POST("tutor_bookings.php")
    Call<MyBookingResponse> tutorBooking(@Field("tutor")String tutor);
    @FormUrlEncoded
    @POST("my_subscriptions.php")
    Call<SubscriptionResponse> subscriptions(@Field("id")String id);
    @FormUrlEncoded
    @POST("my_transactions.php")
    Call<TransactionResponse> transactions(@Field("id")String id);
    @FormUrlEncoded
    @POST("payments.php")
    Call<PaymentResponse> payment(@Field("user_id")String userId,@Field("package_id")String packageId,
                                  @Field("transaction_no")String transactionNo,@Field("amount")String amount);
    @FormUrlEncoded
    @POST("profile.php")
    Call<ProfileResponse> stuentProfile(@Field("user_id")String id);
    @FormUrlEncoded
    @POST("tutor_profile.php")
    Call<ProfileResponse> tutorProfile(@Field("user_id")String id);
    @FormUrlEncoded
    @POST("update_profile.php")
    Call<UpdateProfileResponse> updateProfile(@Field("user_id")String userId,@Field("first_name")String firstName,
                                              @Field("last_name")String lastName,@Field("email")String email,
                                              @Field("dob")String dob,@Field("gender")String gender,
                                              @Field("website")String website,@Field("profile")String profile,
                                              @Field("qualification")String qualification,
                                              @Field("profile_page_title")String profilePageTitle);
    @FormUrlEncoded
    @POST("change_password.php")
    Call<ChangePasswordResponse> changePassword(@Field("user_id")String userId,@Field("password")String currentPassword
                                                ,@Field("new_password")String newPassword);
    @Multipart
    @POST("update_profile_image.php")
    Call<ImageResponse> postImage(@Part("user_id") RequestBody name,@Part MultipartBody.Part image);
    @Multipart
    @POST("update_profile_image.php")
    Call<ImageResponse> postMultipleImages(@Part("content") RequestBody name, @Part ArrayList<MultipartBody.Part> image);
    @FormUrlEncoded
    @POST("get_locations.php")
    Call<LocationResponse> location(@Field("tutor_id")String id);
    @FormUrlEncoded
    @POST("update_locations.php")
    Call<UpdateLocationResponse> updateLocation(@Field("tutor_id")String id,@Field("location_id")int locationId,
                                                @Field("action")String action);
    @FormUrlEncoded
    @POST("tutor_courses.php")
    Call<TutorHomeResponse> tutorHome(@Field("tutor")String id);
    @FormUrlEncoded
    @POST("tutor_course_details.php")
    Call<TutorCourseDetailsResponse> tutorCourseDetails(@Field("id")String id);
    @FormUrlEncoded
    @POST("get_booking_status.php")
    Call<GetBookingStatsuResponse> getBookingStatus(@Field("id")long id, @Field("user_id")String userId,
                                                    @Field("type")String type);
    @FormUrlEncoded
    @POST("update_booking_status.php")
    Call<SignupResponse> updateBookingStatus(
            @Field("id")long id, @Field("user_id")String userId,
            @Field("type")String type,@Field("status")String status,
            @Field("description")String description
    );

}
