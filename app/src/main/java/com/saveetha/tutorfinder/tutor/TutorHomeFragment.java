package com.saveetha.tutorfinder.tutor;

import static android.content.Context.CLIPBOARD_SERVICE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.Dataset;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuku.ambilwarna.AmbilWarnaDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.saveetha.tutorfinder.AppConstants;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.RestClient;
import com.saveetha.tutorfinder.model.course.CourseResponse;
import com.saveetha.tutorfinder.model.course.Datum;
import com.saveetha.tutorfinder.model.tutor.TutorHomeResponse;
import com.saveetha.tutorfinder.student.CourseTutorFragment;
import com.saveetha.tutorfinder.student.recyclerview.RecyclerStudentClass;
import com.saveetha.tutorfinder.student.recyclerview.RecyclerStudentData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TutorHomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private List<DataSetClass> recyclerDataArrayList;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tutor_home, container, false);
        recyclerView=view.findViewById(R.id.tutorHomeRecyclerView);
        progress=view.findViewById(R.id.progress);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        SharedPreferences sf=getActivity().getSharedPreferences(AppConstants.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String tutorId=sf.getString(AppConstants.KEY_ID,null);
        Call<TutorHomeResponse> responseCall = RestClient.makeAPI().tutorHome(tutorId);
        progress.setVisibility(View.VISIBLE);
//        Toast.makeText(getContext(),tutorId, Toast.LENGTH_LONG).show();
        responseCall.enqueue(new Callback<TutorHomeResponse>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<TutorHomeResponse> call, Response<TutorHomeResponse> response) {
                if (response.isSuccessful())
                {
                    if(response.body().getStatus()==200)
                    {
                        recyclerView= view.findViewById(R.id.tutorHomeRecyclerView);
                        recyclerDataArrayList=new ArrayList<>();
                        for(int i=0;i<response.body().getData().size();i++){
                            TutorHomeResponse.TutorHomeResponseInnerClass thri=response.body().getData().get(i);
                            recyclerDataArrayList.add(new DataSetClass(thri.getId(),thri.getCourse_id(),thri.getTutor_id(),
                                    thri.getImage(),thri.getName()));
                        }
                        try {
                            MyRecyclerView adapter = new MyRecyclerView(recyclerDataArrayList, requireActivity(),getActivity());
                            GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2);
//                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.slide_in);
//                            recyclerView.setLayoutAnimation(animation);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                            progress.setVisibility(View.GONE);
                        }catch(IllegalStateException e){
                            progress.setVisibility(View.GONE);
                            Log.e(TAG,"student home fragment "+ e.getMessage());
                        }catch(Exception e){
//                            Toast.makeText(getContext(),"something  wrong", Toast.LENGTH_LONG).show();
                            progress.setVisibility(View.GONE);
                            Log.e(TAG,"student home fragment "+ e.getMessage());
                        }
                    }
                    else{
                        Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progress.setVisibility(View.GONE);
                    }
                }else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"server busy",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TutorHomeResponse> call, Throwable t) {
                Toast.makeText(getContext(),"check your internet connection", Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });

        return view;
    }
    private class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.MyViewHolder>{

        List<DataSetClass> dsc;
        Context context;
        FragmentActivity activity;
        MyRecyclerView(List<DataSetClass> dsc,Context context,FragmentActivity activity){
            this.context=context;
            this.dsc=dsc;
            this.activity=activity;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            DataSetClass recyclerData = dsc.get(position);
            holder.textView.setText(recyclerData.getCourseName());
            Glide.with(context).load(recyclerData.getImage()).apply(new RequestOptions().placeholder(R.drawable.error2) // Placeholder image
                            .error(R.drawable.error1)) // Error image in case of loading failure
                    .into(holder.imageView);
            holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fall_down));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TutorCourseDetailsFragment courseTutorFragment= new TutorCourseDetailsFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("position"," "+position);
                    bundle.putString("id",recyclerData.getId());
                    courseTutorFragment.setArguments(bundle);
                    FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_left);
                    ft.replace(R.id.frameLayout,courseTutorFragment).addToBackStack("1");
                    ft.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dsc.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            private CardView cardView;
            private TextView textView;
            private ImageView imageView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView=itemView.findViewById(R.id.cardView1);
                textView=itemView.findViewById(R.id.idTVCourse);
                imageView=itemView.findViewById(R.id.idIVcourseIV);
            }
        }
    }
    private class DataSetClass{
        private String id;
        private String courseId;
        private String tutorId;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getTutorId() {
            return tutorId;
        }

        public void setTutorId(String tutorId) {
            this.tutorId = tutorId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public DataSetClass(String id, String courseId, String tutorId, String image, String courseName) {
            this.id = id;
            this.courseId = courseId;
            this.tutorId = tutorId;
            this.image = image;
            this.courseName = courseName;
        }
        private String courseName;
    }

}