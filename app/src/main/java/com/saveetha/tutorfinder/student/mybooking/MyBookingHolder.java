package com.saveetha.tutorfinder.student.mybooking;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.LoginActivity;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.SignupActivity;
import com.saveetha.tutorfinder.model.GetBookingStatsuResponse;
import com.saveetha.tutorfinder.model.SignupResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingHolder extends RecyclerView.Adapter<MyBookingHolder.MyBookingInner> {
    ArrayList<MyBookingData> mData;
    Context context;
    View view;
    ProgressBar progress;
    public MyBookingHolder(ArrayList<MyBookingData> mData, Context context,View view)
    {
        this.context=context;
        this.mData=mData;
        this.view=view;
    }
    @NonNull
    @Override
    public MyBookingInner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_booking_layout, parent, false);
        return new MyBookingInner(view);
    }
    SharedPreferences sf;
    @Override
    public void onBindViewHolder(@NonNull MyBookingInner holder,@SuppressLint("RecyclerView") int position) {
        MyBookingData mbi=mData.get(position);
        holder.courseName.setText(mbi.getCourseName());
        holder.tutorName.setText(mbi.getTutorName());
        holder.duration.setText(mbi.getDuration());
        holder.fees.setText(mbi.getFees());
        holder.location.setText(mbi.getLocation());
        holder.commenceDate.setText(mbi.getCommenceDate());
        holder.timeSlot.setText(mbi.getTimeSlot());
        holder.status.setText(mbi.getStatus());
        holder.status.setSelected(true);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fall_down));
         sf = context.getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        String  type=sf.getString(AppConstants.KEY_TYPE,null);
        try{
            if(type.equalsIgnoreCase("tutor"))
            {
                holder.textView.setText("Student Name");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=sf.getString(AppConstants.KEY_ID,null);
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.bookig_dialog_layout);
                progress =dialog.findViewById(R.id.progress);
                progress.setVisibility(View.VISIBLE);
                dialog.show();
                apiCall(Long.parseLong(mbi.getBookingId()),id,type,dialog);
            }
        });
    }
    private void apiCall(long id1,String userId,String type,Dialog dialog)
    {
        Toast.makeText(context, "user id "+userId+" id "+id1+" "+type, Toast.LENGTH_LONG).show();
        Call<GetBookingStatsuResponse> responseCall=RestClient.makeAPI().getBookingStatus(id1,userId,type);
        try {
                responseCall.enqueue(new Callback<GetBookingStatsuResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<GetBookingStatsuResponse> call, Response<GetBookingStatsuResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            GetBookingStatsuResponse gbsr = response.body();
//                         statusOption=new String[gbsr.getStatus_options().length];
                            String currentStatus = gbsr.getCurrent_status();
                            ArrayList<String> statusOption = gbsr.getStatus_options();
                            AutoCompleteTextView dropDown = dialog.findViewById(R.id.selectOption);
                            AppCompatButton button = dialog.findViewById(R.id.update);
                            ArrayAdapter<String> adapterItem = new ArrayAdapter<String>(context, R.layout.login_as_item, statusOption);
                            dropDown.setText(currentStatus);
                            dropDown.setAdapter(adapterItem);
                            TextInputEditText text = dialog.findViewById(R.id.description);

                            progress.setVisibility(View.GONE);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String status = dropDown.getText().toString();
                                    String description = text.getText().toString();
                                    if (!status.isEmpty() && !description.isEmpty()) {
                                        progress.setVisibility(View.VISIBLE);
                                        Call<SignupResponse> signup_response = RestClient.makeAPI().updateBookingStatus(id1, userId, type, status, description);
                                        signup_response.enqueue(new Callback<SignupResponse>() {
                                            @Override
                                            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                                                if (response.isSuccessful()) {
                                                    if (response.body().getStatus() == 200) {
                                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                                        progress.setVisibility(View.GONE);
                                                        dialog.dismiss();
                                                    } else {
                                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                                        progress.setVisibility(View.GONE);
                                                    }
                                                } else {
                                                    Toast.makeText(context, "server busy", Toast.LENGTH_LONG).show();
                                                    progress.setVisibility(View.GONE);
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<SignupResponse> call, Throwable t) {
                                                Toast.makeText(context, "check your internet connection", Toast.LENGTH_LONG).show();
                                                progress.setVisibility(View.GONE);
                                            }
                                        });
                                    } else {
                                        Toast.makeText(context, "fill all the field", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            dialog.show();
                        }
                    } else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(context, "server busy", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<GetBookingStatsuResponse> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(context, "check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyBookingInner extends RecyclerView.ViewHolder{

        private CardView cardView;
        private TextView tutorName,textView,courseName,duration,fees,commenceDate,timeSlot,location,status;
        private LinearLayout linearLayout;
        AppCompatImageButton edit;
        public MyBookingInner(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.my_booing_layout);
            tutorName=itemView.findViewById(R.id.tutor_name);
            textView=itemView.findViewById(R.id.setStudentName);
            courseName=itemView.findViewById(R.id.course_name);
            duration=itemView.findViewById(R.id.duration);
            fees=itemView.findViewById(R.id.fees);
            edit=itemView.findViewById(R.id.edit);
            commenceDate=itemView.findViewById(R.id.commence_date);
            timeSlot=itemView.findViewById(R.id.time_slot);
            location=itemView.findViewById(R.id.location);
            status=itemView.findViewById(R.id.status);
        }
    }
}
