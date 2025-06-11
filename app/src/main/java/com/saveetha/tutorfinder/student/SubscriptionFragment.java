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
import android.widget.Toast;

import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.listpackages.ListPackageInner;
import com.saveetha.tutorfinder.model.subscriptionapi.SubscriptionResponse;
import com.saveetha.tutorfinder.model.subscriptionapi.SubsctiptioInnerResponse;
import com.saveetha.tutorfinder.student.listpackages.ListPackagesRecyclerView;
import com.saveetha.tutorfinder.student.listpackages.ListPackagesRecyclerViewData;
import com.saveetha.tutorfinder.student.subscriptionrecycle.SubscriptionData;
import com.saveetha.tutorfinder.student.subscriptionrecycle.SubscriptionViewHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<SubscriptionData> sd;
    View progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_subscription, container, false);
        progress    = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        SharedPreferences sf = getActivity().getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String user_type = sf.getString(AppConstants.KEY_TYPE, null);
        String is_user_logged = sf.getString(AppConstants.KEY_ID, null);

        retrofit2.Call<SubscriptionResponse> responseCall= RestClient.makeAPI().subscriptions(is_user_logged);
        responseCall.enqueue(new Callback<SubscriptionResponse>() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {
                if(response.isSuccessful())
                {
                    if(200==response.body().getStatus())
                    {
                        recyclerView= view.findViewById(R.id.id_list_subscriptions);
                        sd=new ArrayList<>();
                        ArrayList<SubsctiptioInnerResponse> datum=response.body().getData();
                        for(int i=0;i<response.body().getData().size();i++)
                            sd.add(new SubscriptionData(datum.get(i).getPackage_name(),datum.get(i).getSubscribe_date(),
                                    datum.get(i).getPayment_type(),datum.get(i).getCredits(),datum.get(i).getAmount_paid()));
                    try {
                        SubscriptionViewHolder adapter = new SubscriptionViewHolder(sd, requireActivity());
    //                    GridLayoutManager layoutManager=new GridLayoutManager(requireActivity(),2);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                        progress.setVisibility(View.GONE);
                    }catch(IllegalStateException e)
                    {
                        Log.e(TAG,"subscription fragment "+e.getMessage());
                    }

                }else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    }
                }else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"server busy",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
                Toast.makeText(getContext(),"check your internet connection",Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });
        return  view;
    }
}