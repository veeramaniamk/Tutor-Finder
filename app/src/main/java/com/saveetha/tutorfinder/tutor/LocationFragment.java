package com.saveetha.tutorfinder.tutor;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.tutor.location.City;
import com.saveetha.tutorfinder.model.tutor.location.LocationInnerClass;
import com.saveetha.tutorfinder.model.tutor.location.LocationResponse;
import com.saveetha.tutorfinder.tutor.locationrecyclerview.LocationAdapter;
import com.saveetha.tutorfinder.tutor.locationrecyclerview.LocationData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<LocationData> ld;
    ProgressBar progress;
    SharedPreferences sf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_location, container, false);
        progress    = view.findViewById(R.id.progress);
        sf = getActivity().getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String is_user_logged = sf.getString(AppConstants.KEY_ID, null);
        progress.setVisibility(View.VISIBLE);
        Call<LocationResponse> responseCall= RestClient.makeAPI().location(is_user_logged);
        responseCall.enqueue(new Callback<LocationResponse>() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if(response.isSuccessful())
                {
                    progress.setVisibility(View.GONE);
                    if(200==response.body().getStatus()){
                        recyclerView=view.findViewById(R.id.locationRecyclerView);
                        ld=new ArrayList<>();
                        ArrayList<City> c;
                        ArrayList<LocationInnerClass> lic=response.body().getData();
                        for(int i=0;i<lic.size();i++)
                        {
                            c=response.body().getData().get(i).getCity();
                            String[] city=new String[c.size()];
                            String[] status=new String[c.size()];
                            String[] cityId=new String[c.size()];
                            for(int j=0;j<c.size();j++)
                            {
                                city[j]=c.get(j).getCity_name();
                                status[j]=c.get(j).getCity_status();
                                cityId[j]=c.get(j).getCity_id();
                            }
                            ld.add(new LocationData(lic.get(i).getState(), city,status,cityId));
                        }

                        progress.setVisibility(View.GONE);
                        try {
                            LocationAdapter locationAdapter=new LocationAdapter(ld,getContext(),view,getActivity());
                            LinearLayoutManager linearLayout=new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(linearLayout);
                            recyclerView.setAdapter(locationAdapter);
                        }catch (IllegalStateException e)
                        {
                            e.printStackTrace();
                        }

                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"server busy",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e(TAG,"onFailure "+t.getMessage());
                Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}