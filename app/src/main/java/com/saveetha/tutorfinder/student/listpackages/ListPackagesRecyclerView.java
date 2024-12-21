package com.saveetha.tutorfinder.student.listpackages;

import static androidx.core.content.ContextCompat.startActivity;
import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.tutorfinder.R;
import com.saveetha.tutorfinder.student.PaymentActivity;

import java.util.ArrayList;

public class ListPackagesRecyclerView extends RecyclerView.Adapter<ListPackagesRecyclerView.ListPackageHolderClass> {

    private ArrayList<ListPackagesRecyclerViewData> lprv;
    private Context context;
    private String is_user_mail,is_user_name;
    public ListPackagesRecyclerView(ArrayList<ListPackagesRecyclerViewData> lprv,Context context)
    {
        this.context=context;
        this.lprv=lprv;
    }

    @NonNull
    @Override
    public ListPackageHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_package_layout, parent, false);
        return new ListPackageHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPackageHolderClass holder,@SuppressLint("RecyclerView") int position) {
        ListPackagesRecyclerViewData listPackagesRecyclerViewData = lprv.get(position);
        String pn=listPackagesRecyclerViewData.getPackageName();
        String crds=listPackagesRecyclerViewData.getCredits();
        String pc=listPackagesRecyclerViewData.getPackageCost();
        holder.packageName.setText(pn);
        holder.credits.setText(crds);
        holder.packageCost.setText("₹"+pc);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fall_down));
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PaymentActivity.class);
                intent.putExtra("package_name",pn);
                intent.putExtra("credits",crds);
                intent.putExtra("package_cost",pc);
                intent.putExtra("package_id",listPackagesRecyclerViewData.getListPackageId());
                intent.putExtra("user_id",pc);
                startActivity(context,intent, Bundle.EMPTY);
                getActivity(context).overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lprv.size();
    }
   public class ListPackageHolderClass extends RecyclerView.ViewHolder{
        private LinearLayout cardView;
        private TextView credits,packageName,packageCost;
        private Button buy;
        private LinearLayout linearLayout;
        public ListPackageHolderClass(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.list_packages);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.packageLayoutId);
            credits=itemView.findViewById(R.id.credits);
            packageName=itemView.findViewById(R.id.package_name);
            packageCost=itemView.findViewById(R.id.package_cost);
            buy=itemView.findViewById(R.id.buy);
        }
    }

}
