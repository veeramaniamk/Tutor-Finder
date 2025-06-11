package com.saveetha.tutorfinder.student;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.course.CourseResponse;
import com.saveetha.tutorfinder.model.course.Datum;
import com.saveetha.tutorfinder.student.recyclerview.RecyclerStudentClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.saveetha.tutorfinder.student.recyclerview.RecyclerStudentData;
import com.saveetha.tutorfinder.tutor.TutorHomeFragment;

public class StudentHomeFragment extends Fragment {
    private ArrayList<RecyclerStudentData> recyclerDataArrayList;
    private RecyclerView recyclerView;
    View view;
    ProgressBar progress;
    SharedPreferences sf;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_student_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        progress    = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        studentCourse();
        return view;
    }
    private void studentCourse() {

        Call<CourseResponse> responseCall = RestClient.makeAPI().course();

        responseCall.enqueue(new Callback<CourseResponse>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                CourseResponse loginResponse = response.body();
                if (response.isSuccessful())
                {

                    if(loginResponse.getStatus()==200)
                    {
                        recyclerView= view.findViewById(R.id.idCourseRV);
                        recyclerDataArrayList=new ArrayList<>();
                        List<Datum> datum=response.body().getData();
                        for(int i=0;i<response.body().getData().size();i++)
                        recyclerDataArrayList.add(new RecyclerStudentData(datum.get(i).getName(),datum.get(i).getImage(),datum.get(i).getId()));
                        try {
                            RecyclerStudentClass adapter = new RecyclerStudentClass(recyclerDataArrayList, requireActivity());
                            GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                            progress.setVisibility(View.GONE);
                        }catch(Exception e){
                            e.printStackTrace();
                            Log.e(TAG,"student home fragment "+ e.getMessage());
                        }
                    }
                    else{
                        Toast.makeText(getContext(),loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                        progress.setVisibility(View.GONE);
                    }
                }else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"server busy",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                Toast.makeText(getContext(),"check your internet connection", Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
    }
}