package com.saveetha.tutorfinder.student.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.student.CourseTutorFragment;
import com.saveetha.tutorfinder.student.ReserveSpotFragment;

import java.util.ArrayList;

public class HolderClass extends RecyclerView.Adapter<HolderClass.HolderInnerClass>{
    private Context context;
    private ArrayList<DataSetClass> recyclerDataa;
    public HolderClass(ArrayList<DataSetClass> recyclerData, Context context){
        this.context=context;
        this.recyclerDataa=recyclerData;
    }

    @NonNull
    @Override
    public HolderClass.HolderInnerClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.for_course_tutor, parent, false);
        return new HolderClass.HolderInnerClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderInnerClass holder, @SuppressLint("RecyclerView") int position) {
        DataSetClass recyclerData = recyclerDataa.get(position);
        holder.textView.setText(recyclerData.getTitle());
        Glide.with(context).load(recyclerData.getImgid()).apply(new RequestOptions().placeholder(R.drawable.error2) // Placeholder image
                        .error(R.drawable.error1)) // Error image in case of loading failure
                .into(holder.imageView);
        holder.teaches.setText(recyclerData.getTeaches());
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fall_down));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReserveSpotFragment reserveSpotFragment= new ReserveSpotFragment();
                Bundle bundle=new Bundle();
                bundle.putString("position"," "+position);
                bundle.putString("courseId",recyclerData.getCourseId());
                bundle.putString("tutorId",recyclerData.getTutorId());
                reserveSpotFragment.setArguments(bundle);
                FragmentManager fragmentManager= ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                       transaction .replace(R.id.frameLayout, reserveSpotFragment,null).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerDataa.size();
    }

    public class HolderInnerClass extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView textView,teaches;
        private ImageView imageView;
        private Button button;
        @SuppressLint("WrongViewCast")
        public HolderInnerClass(@NonNull View itemView) {
            super(itemView);
            teaches=itemView.findViewById(R.id.subject);
            cardView=itemView.findViewById(R.id.forList);
            textView=itemView.findViewById(R.id.teacherName);
            imageView=itemView.findViewById(R.id.teacherProfile);
            button=itemView.findViewById(R.id.bookNow);
        }
    }

}
