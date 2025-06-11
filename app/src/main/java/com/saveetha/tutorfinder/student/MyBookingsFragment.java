package com.saveetha.tutorfinder.student;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.listpackages.ListPackageInner;
import com.saveetha.tutorfinder.model.mybookings.MyBookingResponse;
import com.saveetha.tutorfinder.model.mybookings.MyBookingResponseInner;
import com.saveetha.tutorfinder.student.listpackages.ListPackagesRecyclerView;
import com.saveetha.tutorfinder.student.listpackages.ListPackagesRecyclerViewData;
import com.saveetha.tutorfinder.student.mybooking.MyBookingData;
import com.saveetha.tutorfinder.student.mybooking.MyBookingHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<MyBookingData> lprv;
    private View view;
    private View progress;
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_my_bookings, container, false);
        progress    = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        Call<MyBookingResponse> responseCall=null;
        SharedPreferences sf = getActivity().getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String  type=sf.getString(AppConstants.KEY_TYPE,null);
        String is_user_logged = sf.getString(AppConstants.KEY_ID, null);
        if(type.equalsIgnoreCase("tutor"))
        {
            responseCall= RestClient.makeAPI().tutorBooking(is_user_logged);
        }
        else if(type.equalsIgnoreCase("student"))
        {
            responseCall= RestClient.makeAPI().myBooking(is_user_logged);
        }
        responseCall.enqueue(new Callback<MyBookingResponse>() {
            @Override
            public void onResponse(Call<MyBookingResponse> call, Response<MyBookingResponse> response) {
            if(response.isSuccessful())
            {
                if(response.body().getStatus()==200)
                {
                    recyclerView= view.findViewById(R.id.id_my_booking);
                    lprv=new ArrayList<>();
                    List<MyBookingResponseInner> datum=response.body().getData();
                    if(type.equalsIgnoreCase("tutor"))
                    {
                        for (int i = 0; i < response.body().getData().size(); i++)
                        lprv.add(new MyBookingData(datum.get(i).getStudent_name(), datum.get(i).getCourse_name(), datum.get(i).getDuration()
                                , datum.get(i).getFee(), datum.get(i).getCommence_date(), datum.get(i).getTime_slot(),
                                datum.get(i).getLocation(), datum.get(i).getStatus(),datum.get(i).getBooking_id()));
                    }else if(type.equalsIgnoreCase("student")) {
                        for (int i = 0; i < response.body().getData().size(); i++)
                        lprv.add(new MyBookingData(datum.get(i).getTutor_name(), datum.get(i).getCourse_name(), datum.get(i).getDuration()
                                , datum.get(i).getFee(), datum.get(i).getCommence_date(), datum.get(i).getTime_slot(),
                                datum.get(i).getLocation(), datum.get(i).getStatus(),datum.get(i).getBooking_id()));
                    }
                    try{
                        MyBookingHolder adapter=new MyBookingHolder(lprv,requireActivity(),view);
    //                    GridLayoutManager layoutManager=new GridLayoutManager(requireActivity(),2);

                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
                            recyclerView.setLayoutAnimation(animation);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                        progress.setVisibility(View.GONE);
                    }catch(Exception e)
                    {
                        Log.e(TAG,"my bookings fragment "+e.getMessage());
                    }
                }
                else{
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            }
            else {
                Toast.makeText(getContext(),"server busy",Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
            }
            @Override
            public void onFailure(Call<MyBookingResponse> call, Throwable t) {
               Toast.makeText(getContext(),"check your internet connection",Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });

        return  view;
    }
}