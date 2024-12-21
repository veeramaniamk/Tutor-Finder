package com.saveetha.tutorfinder.student;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.LoginResponse;
import com.saveetha.tutorfinder.model.course.Datum;
import com.saveetha.tutorfinder.model.listpackages.ListPackageInner;
import com.saveetha.tutorfinder.model.listpackages.ListPackageResponse;
import com.saveetha.tutorfinder.student.listpackages.ListPackagesRecyclerView;
import com.saveetha.tutorfinder.student.listpackages.ListPackagesRecyclerViewData;
import com.saveetha.tutorfinder.student.listview.HolderClass;
import com.saveetha.tutorfinder.student.recyclerview.RecyclerStudentClass;
import com.saveetha.tutorfinder.student.recyclerview.RecyclerStudentData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListPackagesFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<ListPackagesRecyclerViewData> lprv;
    View view;
    View progress;
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_list_packages, container, false);
        progress  = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        listPackages();
        return view;
    }
    private void listPackages()
    {
        SharedPreferences sf = getActivity().getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String is_user_type = sf.getString(AppConstants.KEY_TYPE, null);
        Call<ListPackageResponse> responseCall = RestClient.makeAPI().listPackages(is_user_type);
        responseCall.enqueue(new Callback<ListPackageResponse>() {
            @Override
            public void onResponse(Call<ListPackageResponse> call, Response<ListPackageResponse> response) {
                if(response.isSuccessful())
                {
                    if(200==response.body().getStatus())
                    {
                        recyclerView= view.findViewById(R.id.id_list_packages);
                        lprv=new ArrayList<>();
                        List<ListPackageInner> datum=response.body().getData();
                        for(int i=0;i<response.body().getData().size();i++)
                            lprv.add(new ListPackagesRecyclerViewData(datum.get(i).getId(),datum.get(i).getPackage_name(),
                                    datum.get(i).getCredits(),datum.get(i).getPackage_cost()));
                        try {
//                            GridLayoutManager gridLayoutManager=new GridLayoutManager(requireActivity(),2);
                            ListPackagesRecyclerView adapter = new ListPackagesRecyclerView(lprv, requireActivity());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_up);
                            recyclerView.setLayoutManager(linearLayoutManager);
//                            recyclerView.setLayoutAnimation(animation);
//                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            progress.setVisibility(View.GONE);
                        }catch (IllegalStateException e)
                        {
                            Log.e(TAG,"IllegalArgumentException "+e.getMessage());
                            progress.setVisibility(View.GONE);
                        }
                    }else{
                        progress.setVisibility(View.GONE);
                    }
                }else Toast.makeText(getContext(),"server busy",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ListPackageResponse> call, Throwable t) {
                 Toast.makeText(getContext(),"check your internet connection",Toast.LENGTH_SHORT).show();
                 progress.setVisibility(View.GONE);
            }
        });
    }

}