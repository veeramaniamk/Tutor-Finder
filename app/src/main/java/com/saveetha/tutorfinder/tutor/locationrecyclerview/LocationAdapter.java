package com.saveetha.tutorfinder.tutor.locationrecyclerview;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Type;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.tutor.updatelocation.UpdateLocationResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    Context context;
    ArrayList<LocationData> ld;
    Activity activity;
    View view;

    public LocationAdapter(ArrayList<LocationData> locationData, Context context, View view,Activity activity)
    {
        ld=locationData;
        this.context=context;
        this.view=view;
        this.activity=activity;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.location_layout,parent,false);
        return new LocationViewHolder(view);
    }

    CheckBox geekBox=null;
    SharedPreferences sf;
    ProgressBar progress;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.showState.setText(ld.get(position).getState());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        String[] city=ld.get(position).getCityName();
        String[] status=ld.get(position).getStatus();
        String[] cityId=ld.get(position).getCityId();
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fall_down));
        ArrayList<CheckBox> alc=new ArrayList<>();
        sf =context.getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String is_user_logged = sf.getString(AppConstants.KEY_ID, null);
        progress=view.findViewById(R.id.progress);


//        Log.e("tag",""+cityId[0]+" "+cityId[1]+" "+cityId[2]+" "+cityId[3]+" ");
        for(int i=0;i<city.length;i++) {
            geekBox=new CheckBox(context);
            geekBox.setId(Integer.parseInt(cityId[i]));
            geekBox.setText(city[i]);
            geekBox.setLayoutParams(layoutParams);
            alc.add(geekBox);
            holder.linearLayout.addView(alc.get(i));
            CheckBox checkBox=alc.get(i);
            if(status[i].equalsIgnoreCase("y")) checkBox.setChecked(true);
            if(status[i].equalsIgnoreCase("n")) checkBox.setChecked(false);

            geekBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Dialog dialog=new Dialog(context);
//                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
//                    layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
//                    layoutParams.x = 200; // Horizontal offset
//                    layoutParams.y = 200; // Vertical offset
                    dialog.setContentView(R.layout.location_loader_dialog);
                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.getWindow().setAttributes(layoutParams);
                    dialog.show();
                    if(isChecked) {
//                        progress.setVisibility(View.VISIBLE);
                        updateApi(is_user_logged,buttonView.getId(),"add",view,dialog);
                    }else {
//                        progress.setVisibility(View.VISIBLE);
                        updateApi(is_user_logged,buttonView.getId(),"delete",view,dialog);
                    }

                }
            });

        }

    }
    private void updateApi(String tutorId,int locatinId,String action,View view,Dialog dialog){
        Call<UpdateLocationResponse> responseCall= RestClient.makeAPI().updateLocation(tutorId,locatinId,action);
        responseCall.enqueue(new Callback<UpdateLocationResponse>() {
            @Override
            public void onResponse(Call<UpdateLocationResponse> call, Response<UpdateLocationResponse> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getStatus()==200)
                    {
                        dialog.dismiss();
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        dialog.dismiss();
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }else {
                    progress.setVisibility(View.GONE);
                    dialog.dismiss();
                    Toast.makeText(context,"server busy",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateLocationResponse> call, Throwable t) {
                progress.setVisibility(View.GONE);
                dialog.dismiss();
                Toast.makeText(context,"check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ld.size();
    }
    class LocationViewHolder extends RecyclerView.ViewHolder{
        TextView showState;
        LinearLayout linearLayout;
        LinearLayout cardView;
        View view1;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            view1=itemView;
            showState=itemView.findViewById(R.id.state);
            linearLayout=itemView.findViewById(R.id.showCheckBox);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
