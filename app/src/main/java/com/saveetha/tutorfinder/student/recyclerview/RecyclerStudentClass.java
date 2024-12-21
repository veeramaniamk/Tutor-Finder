package com.saveetha.tutorfinder.student.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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

import java.util.ArrayList;

public class RecyclerStudentClass extends RecyclerView.Adapter<RecyclerStudentClass.RecyclerViewHolder> {
    private Context context;
    private ArrayList<RecyclerStudentData> recyclerDataa;
    public RecyclerStudentClass(ArrayList<RecyclerStudentData> recyclerData, Context context){
        this.context=context;
        this.recyclerDataa=recyclerData;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RecyclerStudentData recyclerData = recyclerDataa.get(position);
        holder.textView.setText(recyclerData.getTitle());
        Glide.with(context).load(recyclerData.getImgid()).apply(new RequestOptions().placeholder(R.drawable.error2) // Placeholder image
                        .error(R.drawable.error1)) // Error image in case of loading failure
                .into(holder.imageView);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fall_down));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseTutorFragment courseTutorFragment= new CourseTutorFragment();
                Bundle bundle=new Bundle();
                bundle.putString("position"," "+position);
                bundle.putString("id",recyclerData.getId());
                bundle.putString("subject",recyclerData.getSubject());
                courseTutorFragment.setArguments(bundle);
                FragmentManager fragmentManager= ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, courseTutorFragment,null).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerDataa.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textView;
        private ImageView imageView;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardView1);
            textView=itemView.findViewById(R.id.idTVCourse);
            imageView=itemView.findViewById(R.id.idIVcourseIV);
        }
    }
}
