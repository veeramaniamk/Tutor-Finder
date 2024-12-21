package com.saveetha.tutorfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.saveetha.tutorfinder.model.LoginRequest;
import com.saveetha.tutorfinder.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AppCompatImageView back_image;
    AppCompatTextView topbar_text;
    AppCompatButton bt_login;
    TextInputEditText email;
    TextInputEditText password;
    ProgressBar progress;
    SharedPreferences sf;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        back_image  = findViewById(R.id.back_icon);
        topbar_text = findViewById(R.id.bar_text);
        email       = findViewById(R.id.login_email);
        password    = findViewById(R.id.login_password);
        bt_login    = findViewById(R.id.bt_login);
        progress    = findViewById(R.id.progress);

        topbar_text.setText(getApplicationContext().getResources().getString(R.string.login));

        sf = getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String is_user_logged = sf.getString(AppConstants.KEY_ID, null);

        if(is_user_logged != null)
        {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent inte=getIntent();
//                String value=inte.getStringExtra("value");
//                if(value==null){
//
//                }else finish();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
                {
                    progress.setVisibility(View.VISIBLE);
                    Login();
                }
                else if(email.getText().toString().isEmpty())email.setError("can not be empty");
                else if(password.getText().toString().isEmpty()) password.setError("can not be empty");
            }
        });
    }

    private void Login() {
        LoginRequest request = new LoginRequest();
        request.setEmail(String.valueOf(email.getText()));
        request.setPassword(String.valueOf(password.getText()));
        request.setPlatform(AppConstants.PLATFORM);

        Call<LoginResponse> responseCall = RestClient.makeAPI().authenticateUser(request);

        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful())
                 {
                     LoginResponse loginResponse= response.body();
                    if(loginResponse.getStatus()==200) {
                         SharedPreferences.Editor editor = sf.edit();
                         editor.putString(AppConstants.KEY_ID, String.valueOf(loginResponse.getUser_id()));
                         editor.putString(AppConstants.KEY_NAME, String.valueOf(loginResponse.getUser_name()));
                         editor.putString(AppConstants.KEY_EMAIL, String.valueOf(loginResponse.getUser_email()));
                         editor.putString(AppConstants.KEY_TYPE, String.valueOf(loginResponse.getUser_type()));
                         editor.putString(AppConstants.KEY_IMAGE, String.valueOf(loginResponse.getUser_profile_image()));
                         editor.putBoolean(AppConstants.KEY_IS_LOGGED_IN, loginResponse.getUser_logged_in());

                         editor.apply();
                         Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                         Intent i = new Intent(LoginActivity.this, MainActivity.class);
                         startActivity(i);
                         finish();
                        overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_right);
                         progress.setVisibility(View.GONE);
                     }
                     else {
                         progress.setVisibility(View.GONE);
                         Toast.makeText(LoginActivity.this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
            }
                else {
                    Toast.makeText(LoginActivity.this,"server busy",Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"check your internet connection", Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
    }
}