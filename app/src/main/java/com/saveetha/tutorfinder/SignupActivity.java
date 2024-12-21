package com.saveetha.tutorfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.saveetha.tutorfinder.model.LoginResponse;
import com.saveetha.tutorfinder.model.SignupResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    ArrayAdapter<String> adapterItem;
    AutoCompleteTextView user_type;
    AppCompatImageView back_image;
    ProgressBar progress;
    AppCompatTextView topbar_text;
    AppCompatButton buttonSignup;

    TextInputEditText fistName,lastName,email,etpassword,etconPassword;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        back_image = findViewById(R.id.back_icon);
        progress = findViewById(R.id.progress);
        topbar_text = findViewById(R.id.bar_text);
        fistName=findViewById(R.id.fistName);
        lastName=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        etpassword=findViewById(R.id.password);
        etconPassword=findViewById(R.id.conPassword);
        buttonSignup=findViewById(R.id.btn_signup);
        user_type = findViewById(R.id.user_select);

        adapterItem = new ArrayAdapter<String>(this, R.layout.login_as_item, AppConstants.LOGIN_TYPE_TEXT);
        user_type.setAdapter(adapterItem);

        topbar_text.setText(getApplicationContext().getResources().getString(R.string.signup));

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=0;
                String user=user_type.getText().toString();
                String fname=fistName.getText().toString();
                String lname=lastName.getText().toString();
                String mailid=email.getText().toString();
                String passwrod=etpassword.getText().toString();
                String con_passwrod=etconPassword.getText().toString();
                //login as Validation
                if (user.isEmpty()) {
                    user_type.setError("Field can't be empty");
                    count = 1;
                } else if (user.length() > 15) {
                    user_type.setError("Username too long");
                    count = 1;
                } else {
                    user_type.setError(null);
                    count = 0;
                }
                //fist name Validation
                if (fname.isEmpty()) {
                    fistName.setError("Field can't be empty");
                    count = 1;
                } else if (fname.length() > 15) {
                    fistName.setError("Username too long");
                    count = 1;
                } else {
                    fistName.setError(null);
                    count = 0;
                }
                //last name validation
                if (lname.isEmpty()) {
                    lastName.setError("Field can't be empty");
                    count = 1;
                } else if (lname.length() > 15) {
                    lastName.setError("Username too long");
                    count = 1;
                } else {
                    lastName.setError(null);
                    count = 0;
                }
                //Email validation
                if (mailid.isEmpty()) {
                    count = 1;
                    email.setError("Field can't be empty");
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(mailid).matches()) {
                        count = 1;
                        email.setError("Please enter a valid email address");
                    } else {
                        count = 0;
                        email.setError(null);
                    }
                }
                //Password Validation
                if (passwrod.isEmpty()) {
                    count = 1;
                    etpassword.setError("Field can't be empty");
                } else if (!PASSWORD_PATTERN.matcher(passwrod).matches()) {
                    count = 1;
                    etpassword.setError("Password too weak: must contain\nUppercase characters (A-Z)\n" +
                            "Lowercase characters (a-z)\n" +
                            "Digits (0-9)\n" +
                            "Special characters (~!@#$%&*_:;'.?/)");
                } else {
                    count = 0;
                    etpassword.setError(null);
                }
                if (!(passwrod.equals(con_passwrod))) {
                    count = 1;
                    Toast.makeText(getApplicationContext(), "Password don't match !", Toast.LENGTH_SHORT).show();
                }
                if(count==0)
                {
                    progress.setVisibility(View.VISIBLE);
                    Signup(user,fname,lname,mailid,passwrod);
                }

            }
        });

    }

    private void Signup(String type,String firstN,String lastN,String email,String password) {
        Map<String,String> params = new HashMap<String, String>();
        params.put("type", type);
        params.put("first_name", firstN);
        params.put("last_name",lastN);
        params.put("email",email);
        params.put("password",password);

        Call<SignupResponse> signup_response = RestClient.makeAPI().signup(params);
        signup_response.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
               if(response.isSuccessful())
               {
                   if(response.body().getStatus()==200) {
                       startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                       Toast.makeText(SignupActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                       progress.setVisibility(View.GONE);
                       finish();
                       overridePendingTransition(R.anim.slide_in_left,
                               R.anim.slide_out_right);
                   }
                   else{
                       Toast.makeText(SignupActivity.this,response.body().getMessage(), Toast.LENGTH_LONG).show();
                       progress.setVisibility(View.GONE);
                   }
               }
               else {
                   Toast.makeText(SignupActivity.this,"server busy", Toast.LENGTH_LONG).show();
                   progress.setVisibility(View.GONE);
               }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this,"check your internet connection", Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);

            }
        });

    }
}