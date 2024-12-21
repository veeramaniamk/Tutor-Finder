package com.saveetha.tutorfinder.student;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.LoginActivity;
import com.saveetha.tutorfinder.MainActivity;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.LoginRequest;
import com.saveetha.tutorfinder.model.LoginResponse;
import com.saveetha.tutorfinder.model.getcoursetutor.DataObjectValues;
import com.saveetha.tutorfinder.model.getcoursetutor.GetCourseTutorResponse;
import com.saveetha.tutorfinder.model.getcoursetutor.RequestGetCourseTutor;
import com.saveetha.tutorfinder.student.listview.DataSetClass;
import com.saveetha.tutorfinder.student.listview.HolderClass;
import com.saveetha.tutorfinder.student.recyclerview.RecyclerStudentData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseTutorFragment extends Fragment {
    private ArrayList<DataSetClass> recyclerDataArrayList;
    private RecyclerView recyclerView;
    ProgressBar progress;
    ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_course_tutor, container, false);
        imageView=view.findViewById(R.id.notAvailable);
        Bundle bundle=getArguments();
        // created new array list..
        // added data to array list
        String a=bundle.getString("position");
        String s=bundle.getString("id").toString();
        String subject=bundle.getString("subject");
        progress  = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Book Tutor");
        Call<GetCourseTutorResponse> responseCall = RestClient.makeAPI().getCourseTutor(s);
        responseCall.enqueue(new Callback<GetCourseTutorResponse>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<GetCourseTutorResponse> call, Response<GetCourseTutorResponse> response) {
               if(response.isSuccessful())
               {
                if(response.body().getStatus()==200)
                {
//                    Toast.makeText(getContext(),"status code "+ response.body().getStatus(), Toast.LENGTH_LONG).show();
                    recyclerView= view.findViewById(R.id.idCourseRVblank);
                    recyclerDataArrayList=new ArrayList<>();
                    ArrayList<DataObjectValues> d=response.body().getData();

                    for(int i=0;i<response.body().getData().size();i++)
                    recyclerDataArrayList.add(new DataSetClass(d.get(i).getUsername(),d.get(i).getPhoto(),d.get(i).getTeaches(),d.get(i).getTutor_id(),d.get(i).getCourse_id()));
                    try {
                        HolderClass adapter = new HolderClass(recyclerDataArrayList, requireActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                        progress.setVisibility(View.GONE);
                    }catch(Exception e)
                    {
                        Log.e(TAG,"course tutor fragment "+e.getMessage());
                        e.printStackTrace();
                    }
                }
                else
                {
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(R.drawable.notfoundgif).into(imageView);
                    Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progress.setVisibility(View.GONE);
                }
               }else {
                   progress.setVisibility(View.GONE);
                   Toast.makeText(getContext(),"server busy", Toast.LENGTH_LONG).show();
               }


            }
            @Override
            public void onFailure(Call<GetCourseTutorResponse> call, Throwable t) {
                Toast.makeText(getContext(),"check your internet connection", Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });

        // added data from arraylist to adapter class.

        return view;
    }
}