package com.saveetha.tutorfinder.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.LoginActivity;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.changepassword.ChangePasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    TextView topbar_text,tvCurrentPassword,tvPassword1,tvPassword2,tvStudentFirstName,tvStudentLastName;
    AppCompatImageView back_image;
    AppCompatButton bsave;
    ShapeableImageView imageStudentProfile;
    ProgressBar progress;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        progress    = findViewById(R.id.progress);
        back_image  = findViewById(R.id.back_icon);
        topbar_text = findViewById(R.id.bar_text);
        tvCurrentPassword=findViewById(R.id.currentPassword);
        tvPassword1=findViewById(R.id.newPassword1);
        tvPassword2=findViewById(R.id.newPassword2);
        bsave=findViewById(R.id.save);
        imageStudentProfile=findViewById(R.id.studentProfile);
        tvStudentFirstName=findViewById(R.id.studentFirstName);
        tvStudentLastName=findViewById(R.id.studentLastName);
        topbar_text.setText("Change Password");

        Intent intent=getIntent();
        tvStudentFirstName.setText(intent.getStringExtra("firstName"));
        tvStudentLastName.setText(intent.getStringExtra("lastName"));
        Glide.with(ChangePasswordActivity.this).load(intent.getStringExtra("image").toString()).apply(new RequestOptions().placeholder(R.drawable.error2) // Placeholder image
                        .error(R.drawable.error1)) // Error image in case of loading failure
                .into(imageStudentProfile);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });
        SharedPreferences sf = getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String is_user_logged = sf.getString(AppConstants.KEY_ID, null);
        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scpassword=tvCurrentPassword.getText().toString();
                String spassword1=tvPassword1.getText().toString();
                String spassword2=tvPassword2.getText().toString(); 
               if(scpassword.isEmpty())
               {
                   tvCurrentPassword.setError("Enter Current Password");
               }
               else
               {
                   tvCurrentPassword.setError(null);
               }

               if(spassword1.isEmpty()) {
                   tvPassword1.setError("Enter New Password");
               } else {
                       tvPassword1.setError(null);
               }

               if(spassword2.isEmpty()) {
                   tvPassword2.setError("Enter Confirm Password");
               }else {
                   tvPassword2.setError(null);
               }

               if(spassword2.isEmpty() || spassword1.isEmpty() || scpassword.isEmpty())
               {
                   Toast.makeText(ChangePasswordActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
               }else {
                   tvPassword1.setError(null);
                   tvPassword2.setError(null);
                   tvCurrentPassword.setError(null);
                   if (spassword2.equals(spassword1))
                   {
                       changePasswordApi(is_user_logged, scpassword, spassword1);
                       progress.setVisibility(View.VISIBLE);

                   }
                   else
                   {
                       Toast.makeText(ChangePasswordActivity.this, "Password and Confirm Password Are Not Matching", Toast.LENGTH_SHORT).show();
                   }
               }

            }
        });

    }
    private void changePasswordApi(String userId,String currentPassword,String newPassword)
    {
        retrofit2. Call<ChangePasswordResponse> responseCall= RestClient.makeAPI().changePassword(userId,currentPassword,newPassword);
        responseCall.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getStatus()==200)
                    {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    }
                }
                else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(ChangePasswordActivity.this, "Server Busy", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(ChangePasswordActivity.this,"check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

}