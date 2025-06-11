package com.saveetha.tutorfinder.tutor;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.tutor.TutorCourseDetailsResponse;
import com.saveetha.tutorfinder.model.tutor.TutorHomeResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TutorCourseDetailsFragment extends Fragment {
    private ProgressBar progress;
    private WebView webView;
    private TextView tvCourseName,tvDuration,tvDurationType,tvFees,tvTimeSlots,tvDaysOff,
            tvStatus,tvSortOrder,tvCreatedAt,tvUpdatedAt;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tutor_course_details, container, false);
        progress=view.findViewById(R.id.progress);
        webView=view.findViewById(R.id.webView);
        tvCourseName=view.findViewById(R.id.courseName);
        tvDuration=view.findViewById(R.id.durationValue);
        tvDurationType=view.findViewById(R.id.durationType);
        tvFees=view.findViewById(R.id.fees);
        tvTimeSlots=view.findViewById(R.id.timeSlot);
        tvDaysOff=view.findViewById(R.id.dayOff);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Course Details");

        Bundle bundle=getArguments();
        String id=bundle.getString("id");
        progress.setVisibility(View.VISIBLE);
        Call<TutorCourseDetailsResponse> responseCall = RestClient.makeAPI().tutorCourseDetails(id);
        responseCall.enqueue(new Callback<TutorCourseDetailsResponse>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<TutorCourseDetailsResponse> call, Response<TutorCourseDetailsResponse> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getStatus()==200)
                    {
                        TutorCourseDetailsResponse.TutorCourseDetailsResponseInnerResponse tcd=response.body().getData().get(0);
                        webView.loadData(tcd.getContent(),"text/html","utf-8");
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setWebChromeClient(new WebChromeClient());
                        tvCourseName.setText(tcd.getName());
                        tvDuration.setText(tcd.getDuration_value());
                        tvDurationType.setText(tcd.getDuration_type());
                        tvFees.setText(tcd.getFee());
                        tvTimeSlots.setText(tcd.getTime_slots());
                        tvDaysOff.setText(tcd.getDays_off());
                        progress.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progress.setVisibility(View.GONE);
                    }
                }else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"server busy",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TutorCourseDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(),"check your internet connection", Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
        return view;
    }
}