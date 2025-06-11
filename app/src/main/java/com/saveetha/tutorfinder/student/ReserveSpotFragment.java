package com.saveetha.tutorfinder.student;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.razorpay.Checkout;
import com.saveetha.tutorfinder.API;
import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.LoginActivity;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.model.booktutor.BookTutorApi;
import com.saveetha.tutorfinder.model.booktutor.ResponseBookTutor;
import com.saveetha.tutorfinder.model.reservespot.ReserveSpotResponse;
import com.saveetha.tutorfinder.model.reservespot.UserService;
import com.saveetha.tutorfinder.model.slot.SlotData;

//import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReserveSpotFragment extends Fragment  {
    private AutoCompleteTextView spinoPreferedLocation;
    private Button requstTutor;
    private TextView cName,credits,dayOff,duration;
    private EditText dateEdt,messages;
    private ImageView profile;
    private ProgressBar progress;
    private List<RadioGroup> allradioGroup = new ArrayList<RadioGroup>();
    private RadioGroup radioGroup;
    private String tutorId,date;
    private String courseId;
    private String preferedLocation;
    private String[] addLocationInSpinno;
    private List<RadioButton> allRadio = new ArrayList<RadioButton>();
    private RadioButton radioButton;
    private LinearLayout linear;
    private boolean validation=false;
    private String slotDateAvailability;
    private LinearLayout.LayoutParams layoutParams;
    private SharedPreferences sf;
    private WebView webView;
    private AlertDialog.Builder builder;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reserve_spot, container, false);
//      spinner
        requstTutor = view.findViewById(R.id.request_tutor);
        webView=view.findViewById(R.id.webView);
        cName = view.findViewById(R.id.courseName);
        credits = view.findViewById(R.id.credites);
         duration = view.findViewById(R.id.duration);
        dayOff = view.findViewById(R.id.day_off);
        profile = view.findViewById(R.id.courseProfile);
        spinoPreferedLocation=view.findViewById(R.id.user_select_course_mode);
        messages=view.findViewById(R.id.message);
        progress    = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
         linear = (LinearLayout) view.findViewById(R.id.linear);
         layoutParams = new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        view.findViewById(R.id.request_tutor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(date!=null && preferedLocation!=null){

                if(validation)
                {
                    sf = getActivity().getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
                    String is_user_logged = sf.getString(AppConstants.KEY_ID, null);
                    if(is_user_logged!=null)
                    {
                        progress.setVisibility(View.VISIBLE);
                        bookTutorApi(is_user_logged,tutorId,courseId,date," ",preferedLocation,slotDateAvailability);
//                        Log.e(TAG,is_user_logged+" "+tutorId+" "+courseId+" "+date+" "+preferedLocation+" "+slotDateAvailability);
                    }
                    else{
                        Intent intent=new Intent(getContext(), LoginActivity.class);
                        intent.putExtra("value","forBooking");
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                    }
                }
                else{
                    Toast.makeText(getContext(),"Select slot date",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(),"fill all the filed",Toast.LENGTH_SHORT).show();
            }

            }
        });

            spinoPreferedLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    preferedLocation= addLocationInSpinno[position];
                    if(date!=null)
                    {
                        slotsApi(date,courseId,tutorId,preferedLocation);
                    }
                }
            });

        // date picker
        dateEdt = view.findViewById(R.id.idEdtDate);
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                date=year + "-" + (monthOfYear + 1) + "-" +  dayOfMonth;
                                dateEdt.setText(year + "-" + (monthOfYear + 1) + "-" +  dayOfMonth);
                                if(preferedLocation!=null)
                                {
                                    slotsApi(date,courseId,tutorId,preferedLocation);
                                }
                                else{
                                    Toast.makeText(getContext(),"Select Preferred Location",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        Bundle bundle=getArguments();
         tutorId=bundle.getString("tutorId");
         courseId=bundle.getString("courseId");
        Retrofit  retrofit=new Retrofit.Builder().baseUrl("https://tutor.zoop.me/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService= retrofit.create(UserService.class);
        Call<ReserveSpotResponse> call =userService.getReserveSpot(courseId,tutorId);
        call.enqueue(new Callback<ReserveSpotResponse>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call<ReserveSpotResponse> call, Response<ReserveSpotResponse> response) {
            if(response.isSuccessful()){

                if(200==response.body().getStatus())
                {
                    ReserveSpotResponse rsr=response.body();
                    cName.setText(rsr.getName());
                    credits.setText(rsr.getCredits());
                    duration.setText(rsr.getDuration());
                    dayOff.setText(rsr.getDaysOff());
                    Glide.with(getContext()).load(rsr.getImage()).apply(new RequestOptions().placeholder(R.drawable.error2) // Placeholder image
                                    .error(R.drawable.error1)) // Error image in case of loading failure
                            .into(profile);
                    addLocationInSpinno=rsr.getMode();
                    ArrayAdapter<String>  adapterItem = new ArrayAdapter<String>(getContext(), R.layout.login_as_item, rsr.getMode());
                    spinoPreferedLocation.setAdapter(adapterItem);
                    webView.loadData(rsr.getContent(),"text/html","utf-8");
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebChromeClient(new WebChromeClient());
                    progress.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getContext()," "+response.body().getMessage(),Toast.LENGTH_LONG).show();
                    progress.setVisibility(View.GONE);

                }
            }else {
                Toast.makeText(getContext(), "server busy", Toast.LENGTH_SHORT).show();
            }

            }
            @Override
            public void onFailure(Call<ReserveSpotResponse> call, Throwable t) {
                Toast.makeText(getContext(),"check your internet connection",Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });

        return view;
    }
    private void bookTutorApi(String student,String tutor,String course,String date,String message,String location,String slot)
    {
        retrofit2.Retrofit  retrofit=new retrofit2.Retrofit.Builder().baseUrl("https://tutor.zoop.me/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        BookTutorApi api= retrofit.create(BookTutorApi.class);
        retrofit2.Call<ResponseBookTutor> responseCall=api.bookTutor(student,tutor,course,date,message,location,slot);
        responseCall.enqueue(new retrofit2.Callback<ResponseBookTutor>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBookTutor> call, retrofit2.Response<ResponseBookTutor> response) {
               if(response.isSuccessful())
               {
                    if(200==response.body().getStatus())
                    {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    }
               }else {
                   Toast.makeText(getContext(), "server busy", Toast.LENGTH_SHORT).show();
                   progress.setVisibility(View.GONE);
               }

            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBookTutor> call,Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getContext(),"check your internet connection ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void slotsApi(String date,String course,String tutor,String location)
    {
        boolean c=date!=null && course!=null && tutor!=null && location!=null;
        if(c){

            Retrofit  retrofit=new Retrofit.Builder().baseUrl("https://tutor.zoop.me/api/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            API api= retrofit.create(API.class);
            Call<SlotData> responseCall = api.getSlots(date,course,tutor,location);
            responseCall.enqueue(new Callback<SlotData>() {
                @Override
                public void onResponse(Call<SlotData> call, Response<SlotData> response) {
                 if(response.isSuccessful())
                 {
                    if(200==response.body().getStatus())
                    {
                        String[] forSlots=response.body().getAvailable_slots();
                        for (int i = 0; i < 1; i++) {
                            /* Defining RadioGroup */
                            radioGroup = new RadioGroup(getContext());
                            radioGroup.setOrientation(RadioGroup.HORIZONTAL);
                            allradioGroup.add(radioGroup);
                            /* Displaying Radio Buttons */
                            for (int j = 0; j < forSlots.length; j++) {
                                radioButton = new RadioButton(getContext());
                                radioButton.setPadding(10,10,10,10);
                                //you can set your button ID here
                                radioButton.setId(j);
                                allRadio.add(radioButton);
                                radioButton.setText(forSlots[j]);
                                //set text for each ID

//                            if (allRadio.get(j).getId() == j) {
//                               }
//                            else if (allRadio.get(j).getId() == 1) {
//                                radioButton.setText(forSlots[j]);
//                            } else if (allRadio.get(j).getId() == 2) {
//                                radioButton.setText(forSlots[j]);
//                            } else if (allRadio.get(j).getId() == 3) {
//                                radioButton.setText(forSlots[j]);
//                            }

                                //number 4 is total radiobutton
                                allradioGroup.get(i).addView(allRadio.get(i*forSlots.length+j),j,layoutParams);
                            }
                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    View radioButton = radioGroup.findViewById(checkedId);
                                    int index = radioGroup.indexOfChild(radioButton);

                                    Toast.makeText(getContext(),  forSlots[index], Toast.LENGTH_SHORT).show();
                                    slotDateAvailability=forSlots[index];
                                    validation=true;
                                }
                            });

                            linear.addView(allradioGroup.get(i));

                        }
                    }
                    else{
                        Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                 }else Toast.makeText(getContext(),"server busy",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<SlotData> call, Throwable t) {
                    Toast.makeText(getContext(),"check your network connection",Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Toast.makeText(getContext()," fill all filed ",Toast.LENGTH_SHORT).show();
        }
    }




}