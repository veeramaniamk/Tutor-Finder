package com.saveetha.tutorfinder.student.subscriptionrecycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.tutorfinder.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SubscriptionViewHolder extends RecyclerView.Adapter<SubscriptionViewHolder.SubscriptionInnerViewHolder> {

    ArrayList<SubscriptionData> subscriptionData;
    Context context;
    public SubscriptionViewHolder(ArrayList<SubscriptionData> sd,Context context)
    {
        this.subscriptionData=sd;
        this.context=context;
    }

    @NonNull
    @Override
    public SubscriptionInnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_layout, parent, false);
        return new SubscriptionInnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionInnerViewHolder holder, int position) {
        SubscriptionData sd=subscriptionData.get(position);
        holder.packageName.setText(sd.getPackageName());
        holder.subscriptionDate.setText(sd.getSubscriptionDate());
        holder.paymentType.setText(sd.getPaymentType());
        holder.credits.setText(sd.getCredits());
        holder.amountPaid.setText(sd.getAmountPaid());
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fall_down));

    }

    @Override
    public int getItemCount() {
        return subscriptionData.size();
    }

    class SubscriptionInnerViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView packageName,subscriptionDate,paymentType,credits,amountPaid;
        public SubscriptionInnerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.id_subscription_layout);
            packageName=itemView.findViewById(R.id.package_name);
            subscriptionDate=itemView.findViewById(R.id.subscription_date);
            paymentType=itemView.findViewById(R.id.payment_type);
            credits=itemView.findViewById(R.id.credits);
            amountPaid=itemView.findViewById(R.id.paid_amount);

        }
    }
}
