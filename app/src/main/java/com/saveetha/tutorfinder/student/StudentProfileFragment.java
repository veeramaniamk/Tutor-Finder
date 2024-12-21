package com.saveetha.tutorfinder.student;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//import android.telecom.Call;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.saveetha.tutorfinder.API;
import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.MainActivity;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.imageresponse.ImageResponse;
import com.saveetha.tutorfinder.model.profile.ProfileInnerResponse;
import com.saveetha.tutorfinder.model.profile.ProfileResponse;
import com.saveetha.tutorfinder.model.reservespot.UserService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import okhttp3.RequestBody;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentProfileFragment extends Fragment {
    private String firstName,lastName,email,dob,gender,website,image,profile,qualification,profilePageTitle,userName
                    ,totalCredits,totalBookigs,totalPendingBookings;
    private TextView tvFirstName,tvLastName,tvEmail,tvDOB,tvGender,tvWebsite,tvProfile,tvProfilePageTitle,tvUserName,
                        tvTotalCredits,tvTotalBookings,tvTotalPendingBookings;
    private ImageView tvImage;
    private AppCompatButton bChagePassword;
    private View view;
    private ProgressBar progress;
    private String is_user_logged;
    private String user;
    private ArrayList<MultipartBody.Part> multy;
    Call<ProfileResponse> responseCall;
    SharedPreferences sf ;
    View nv;
    public StudentProfileFragment(View nv)
    {
        this.nv=nv;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         sf = getActivity().getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
         user=sf.getString(AppConstants.KEY_TYPE,null);
        is_user_logged = sf.getString(AppConstants.KEY_ID, null);
        if(user.equalsIgnoreCase("student")){
            view= inflater.inflate(R.layout.fragment_student_profile, container, false);
            responseCall= RestClient.makeAPI().stuentProfile(is_user_logged);
        }else if(user.equalsIgnoreCase("tutor")){
            view= inflater.inflate(R.layout.fragment_tutor_profile, container, false);
            responseCall= RestClient.makeAPI().tutorProfile(is_user_logged);
        }
        tvFirstName=view.findViewById(R.id.studentFirstName);
        tvLastName=view.findViewById(R.id.studentLastName);
        progress    = view.findViewById(R.id.progress);

        tvDOB=view.findViewById(R.id.dob);
        tvTotalCredits=view.findViewById(R.id.totalCredits);
//        tv=view.findViewById(R.id.fistName);
        tvTotalBookings=view.findViewById(R.id.totalBookings);
        tvImage=view.findViewById(R.id.studentProfile);
        tvEmail=view.findViewById(R.id.email);
        tvGender=view.findViewById(R.id.gender);
        tvTotalPendingBookings=view.findViewById(R.id.totalPendingBookings);
        bChagePassword=view.findViewById(R.id.changePassword);
        progress.setVisibility(View.VISIBLE);

        tvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // starting activity on below line.
                startActivityForResult(intent, 1);
            }
        });

        profileApiCall(is_user_logged);

        return view;
    }
    Bitmap bitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            android.net.Uri selectedImage = data.getData();
            progress.setVisibility(View.VISIBLE);
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Toast.makeText(getContext(), "file size "+filePathColumn.length , Toast.LENGTH_SHORT).show();
            android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                try {
                     bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                } catch (IOException e) {
                    Log.e(TAG,"io exception "+e.getMessage());
                }
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(filePath);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), is_user_logged);
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(httpLoggingInterceptor)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(AppConstants.BASE_URL)  //Change server URL
                        .client(client)
                        .build();

                API api=retrofit.create(API.class);
                Call<ImageResponse> call = api.postImage(name,body);
                call.enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor et=sf.edit();
                                et.putString(AppConstants.KEY_IMAGE, String.valueOf(response.body().getProfile_image()));
                                et.apply();
                                ShapeableImageView siv=nv.findViewById(R.id.imageView2);
                                Glide.with(getActivity()).load(response.body().getProfile_image()).apply(new RequestOptions().placeholder(R.drawable.error2) // Placeholder image
                                        .error(R.drawable.error1)) // Error image in case of loading failure
                                        .into(siv);
                                progress.setVisibility(View.GONE);
                                tvImage.setImageBitmap(bitmap);
                            } else {
                                progress.setVisibility(View.GONE);
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "server busy " , Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "check your internet connection ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private void profileApiCall(String userid)
    {
        responseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            ProfileInnerResponse pir = response.body().getData();
                            firstName = pir.getFirst_name();
                            lastName = pir.getLast_name();
                            email = pir.getEmail();
                            dob = pir.getDob();
                            gender = pir.getGender();
                            website = pir.getWebsite();
                            image = pir.getImage();
                            SharedPreferences.Editor et=sf.edit();
                            et.putString(AppConstants.KEY_IMAGE, String.valueOf(image));
                            et.apply();
                            profile = pir.getProfile();
                            qualification = pir.getQualification();
                            profilePageTitle = pir.getProfile_page_title();
                            userName = pir.getUsername();
                            totalCredits = pir.getTotal_credits();
                            totalBookigs = pir.getTotal_bookings();
                            totalPendingBookings = pir.getTotal_pending_bookings();
                            tvFirstName.setText(firstName);
                            tvLastName.setText(lastName);
                            tvDOB.setText(dob);
                            tvEmail.setText(email);
                            tvGender.setText(gender);
                            tvTotalCredits.setText(totalCredits);
                            tvTotalBookings.setText(totalBookigs);
                            tvTotalPendingBookings.setText(totalPendingBookings);

                            if(user.equalsIgnoreCase("tutor")){
                                TextView tvBookingCompleted=view.findViewById(R.id.bookingCompleted);
                                TextView tvBookingRunning=view.findViewById(R.id.bookingRunning);
                                TextView tvTutoringCourses=view.findViewById(R.id.tutoringCourses);
                                tvBookingCompleted.setText(pir.getTotal_completed_bookings());
                                tvBookingRunning.setText(pir.getTotal_running_bookings());
                                tvTutoringCourses.setText(pir.getTotal_tutoring_course());
                            }

                            Glide.with(getContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.error2) // Placeholder image
                                            .error(R.drawable.error1)) // Error image in case of loading failure
                                    .into(tvImage);

                            view.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent bundle = new Intent(getContext(), UpdateProfileFragment.class);
                                    bundle.putExtra("firstName", firstName);
                                    bundle.putExtra("lastName", lastName);
                                    bundle.putExtra("email", email);
                                    bundle.putExtra("dob", dob);
                                    bundle.putExtra("gender", gender);
                                    bundle.putExtra("website", website);
                                    bundle.putExtra("profile", profile);
                                    bundle.putExtra("qualification", qualification);
                                    bundle.putExtra("profilePageTitle", profilePageTitle);
                                    startActivity(bundle);
                                    getActivity().overridePendingTransition(R.anim.slide_in_right,
                                            R.anim.slide_out_left);
                                }
                            });

                            bChagePassword.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getContext(),ChangePasswordActivity.class);
                                    intent.putExtra("image",image);
                                    intent.putExtra("firstName",firstName);
                                    intent.putExtra("lastName",lastName);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.slide_in_right,
                                            R.anim.slide_out_left);
                                }
                            });
                            progress.setVisibility(View.GONE);

                        } else {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "server busy", Toast.LENGTH_SHORT).show();
                    }
                }catch (NullPointerException e)
                {
                    Log.e(TAG,"student profile fragment "+e.getMessage());
                    e.printStackTrace();
                    progress.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getContext(),"check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
}