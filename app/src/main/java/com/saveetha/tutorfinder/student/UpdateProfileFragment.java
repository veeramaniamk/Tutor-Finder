package com.saveetha.tutorfinder.student;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.updateprofile.UpdateProfileResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateProfileFragment extends AppCompatActivity {

    TextInputEditText tvFirstName,tvLastName,tvEmail,tvDOB,tvGender,tvWebsite,tvProfile,tvQualification,tvPageTitle;
    AppCompatButton btUpdate;
    private String firstName,lastName,email,dob,gender,website,image,profile,qualification,profilePageTitle,userName
            ,totalCredits,totalBookigs,totalPendingBookings;
    AppCompatImageView back_image;
    RadioGroup radioGroup;
    RadioButton radioButtonMale,radioButtonFeMale;
    AppCompatTextView topbar_text;
    ProgressBar progress;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_profile);
        topbar_text = findViewById(R.id.bar_text);
        back_image  = findViewById(R.id.back_icon);
        progress    = findViewById(R.id.progress);


        topbar_text.setText("Update Profile");

        // Inflate the layout for this fragment
        tvFirstName=findViewById(R.id.firstName);
        tvLastName=findViewById(R.id.lastName);
        tvEmail=findViewById(R.id.email);
        tvDOB=findViewById(R.id.dob);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });
        tvWebsite=findViewById(R.id.website);
        tvQualification=findViewById(R.id.qualification);
        btUpdate=findViewById(R.id.update);
        tvProfile=findViewById(R.id.profile);
        tvPageTitle=findViewById(R.id.updatePageTitle);
        radioGroup=findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonMale=findViewById(checkedId);
                gender=radioButtonMale.getText().toString();
            }
        });

        tvDOB.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            // on below line we are getting
            // our day, month and year.
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // on below line we are setting date to our edit text.
//                            date=year + "-" + (monthOfYear + 1) + "-" +  dayOfMonth;
                            tvDOB.setText(year + "-" + (monthOfYear + 1) + "-" +  dayOfMonth);
                        }
                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
        });
        Intent b=getIntent();
        firstName=b.getStringExtra("firstName");
        lastName=b.getStringExtra("lastName");
        email=b.getStringExtra("email");
        dob=b.getStringExtra("dob");
        gender=b.getStringExtra("gender");
        if(gender!=null)
        {
            if(gender.equalsIgnoreCase("male"))
            {
                radioButtonMale=findViewById(R.id.radioMale);
                radioButtonMale.setChecked(true);
            }else if(gender.equalsIgnoreCase("female")){
                radioButtonFeMale=findViewById(R.id.radioFemale);
                radioButtonFeMale.setChecked(true);
            }
        }
        website=b.getStringExtra("website");
        profile=b.getStringExtra("profile");
        qualification=b.getStringExtra("qualification");
        profilePageTitle=b.getStringExtra("profilePageTitle");
        tvFirstName.setText(firstName);
        tvLastName.setText(lastName);
        tvEmail.setText(email);
        tvDOB.setText(dob);
        tvWebsite.setText(website);
        tvProfile.setText(profile);
        tvQualification.setText(qualification);
        tvPageTitle.setText(profilePageTitle);

        btUpdate.setOnClickListener(v -> {
            SharedPreferences sf=getSharedPreferences(AppConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String userId=sf.getString(AppConstants.KEY_ID,null);
            String ufn,uln,ue,udob,ug,up,uw,uq,uppt;
            ufn=tvFirstName.getText().toString();
            uln=tvLastName.getText().toString();
            ue=tvEmail.getText().toString();
            udob=tvDOB.getText().toString();
            ug=gender;
            uw=tvWebsite.getText().toString();
            up=tvProfile.getText().toString();
            uq=tvQualification.getText().toString();
            uppt=tvPageTitle.getText().toString();
            int count=0;
            if(ufn.isEmpty())
            {
                count=1;
                tvFirstName.setError("Enter First Name");
            } else
            {
                count=0;
                tvFirstName.setError(null);
            }
            if(uln.isEmpty())
            {
                count=1;
                tvLastName.setError("Enter Last Name");
            } else
            {
                count=0;
                tvLastName.setError(null);
            }
             if(ue.isEmpty()) {
                 count=1;
                 tvEmail.setError("Enter Mail");
             }
             else{
                 count=0;
                 tvEmail.setError(null);

             }
             if(!Patterns.EMAIL_ADDRESS.matcher(ue).matches()) {
                 count=1;
                 tvEmail.setError("Enter Valid Mail Id");
             } else{
                 count=0;
                 tvEmail.setError(null);

             }
             if(udob.isEmpty()) {
                 count=1;
                 tvDOB.setError("Enter Date Of Birth");
             } else{
                 tvDOB.setError(null);
                 count=0;

             }
             if(ug==null) {
                 count=1;
                 Toast.makeText(this, "select gender", Toast.LENGTH_SHORT).show();
             } else{
                 count=0;

             }
             if(uw.isEmpty()) {
                 count=1;
                 tvWebsite.setError("Enter Website Name");
             } else{
                 count=0;
                 tvWebsite.setError(null);

             }
             if(up.isEmpty()) {
                 count=1;
                 tvProfile.setError("Enter Profile Description");
             } else{
                 count=0;
                 tvProfile.setError(null);

             }
             if(uq.isEmpty()){
                 count=1;
                 tvQualification.setError("Enter Qualification");
             } else{
                 count=0;
                 tvQualification.setError(null);

             }
             if(uppt.isEmpty()) {
                 count=1;
                 tvPageTitle.setError("Enter Profile Title");
             } else{
                 count=0;
                 tvPageTitle.setError(null);

             }
             if(uppt.isEmpty() ||uq.isEmpty()||up.isEmpty()||uw.isEmpty() ||ug==null||udob.isEmpty()||ue.isEmpty()
             ||uln.isEmpty()||ufn.isEmpty())
                 Toast.makeText(this,"fill all details",Toast.LENGTH_SHORT).show();
             else if(count==0)
             {
                 updateApiCall(userId,ufn,uln,ue,udob,ug,uw,up,uq,uppt);
                 progress.setVisibility(View.VISIBLE);

             }

        });
    }
    private void updateApiCall(String userId,String firstName,String lastName,String email,String dob,String gender,String profile,
                               String website,String qualification,String profilePageTitle)
    {
        Call<UpdateProfileResponse> responseCall= RestClient.makeAPI().updateProfile(userId, firstName, lastName, email, dob, gender, profile, website, qualification, profilePageTitle);
        responseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getStatus()==200){
                        progress.setVisibility(View.GONE);
                        Toast.makeText(UpdateProfileFragment.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(UpdateProfileFragment.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UpdateProfileFragment.this,"server busy", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(UpdateProfileFragment.this,"check your internet connection", Toast.LENGTH_SHORT).show();
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