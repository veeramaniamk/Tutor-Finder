package com.saveetha.tutorfinder.student;

import static android.content.Context.MODE_PRIVATE;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import com.saveetha.tutorfinder.model.subscriptionapi.SubsctiptioInnerResponse;
import com.saveetha.tutorfinder.model.transactionapi.TransactionInnerResponse;
import com.saveetha.tutorfinder.model.transactionapi.TransactionResponse;
import com.saveetha.tutorfinder.student.subscriptionrecycle.SubscriptionData;
import com.saveetha.tutorfinder.student.subscriptionrecycle.SubscriptionViewHolder;
import com.saveetha.tutorfinder.student.transactionrecycle.TransactionData;
import com.saveetha.tutorfinder.student.transactionrecycle.TransactionHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransactionFragment extends Fragment {

    RecyclerView recyclerView;
     ArrayList<TransactionData> sd;
    View progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_transaction, container, false);
        progress    = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        SharedPreferences sf = getActivity().getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);

        String is_user_logged = sf.getString(AppConstants.KEY_ID, null);

       retrofit2. Call<TransactionResponse> responseCall= RestClient.makeAPI().transactions(is_user_logged);
       responseCall.enqueue(new Callback<TransactionResponse>() {
           @Override
           public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
               if(response.isSuccessful())
               {
                if(response.body().getStatus()==200)
                {
                    recyclerView= view.findViewById(R.id.id_list_transaction);
                    sd=new ArrayList<>();
                    ArrayList<TransactionInnerResponse> datum=response.body().getData();
                    for(int i=0;i<response.body().getData().size();i++)
                        sd.add(new TransactionData(datum.get(i).getCredits(),datum.get(i).getDate_of_action(),
                                datum.get(i).getPurpose(),datum.get(i).getType()));
                    try {
                        TransactionHolder adapter = new TransactionHolder(sd, requireActivity());
//                    GridLayoutManager layoutManager=new GridLayoutManager(requireActivity(),2);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                        progress.setVisibility(View.GONE);
                    }catch(IllegalStateException e)
                    {
                        Log.e(TAG,"transaction fragment "+e.getMessage());
                    }
                }
                else{
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
               }
               else{
                   progress.setVisibility(View.GONE);
                   Toast.makeText(getContext(),"connection error",Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<TransactionResponse> call, Throwable t) {
               progress.setVisibility(View.GONE);
               Toast.makeText(getContext(),"check your internet connection",Toast.LENGTH_SHORT).show();
           }
       });
        return view;
    }
}
