package com.saveetha.tutorfinder.student.transactionrecycle;

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
import com.saveetha.tutorfinder.student.subscriptionrecycle.SubscriptionData;
import com.saveetha.tutorfinder.student.subscriptionrecycle.SubscriptionViewHolder;

import java.util.ArrayList;

public class TransactionHolder extends RecyclerView.Adapter<TransactionHolder.TransactionInnerViewHolder> {
    ArrayList<TransactionData> transactionData;
    Context context;
    public TransactionHolder(ArrayList<TransactionData> sd,Context context)
    {
        this.transactionData=sd;
        this.context=context;
    }

    @NonNull
    @Override
    public TransactionInnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        return new TransactionInnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  TransactionInnerViewHolder holder, int position) {
        TransactionData sd=transactionData.get(position);
        holder.credits.setText(sd.getCredits());
        holder.purpose.setText(sd.getPurpose());
        holder.paymentType.setText(sd.getType());
        holder.subscriptionDate.setText(sd.getTransactionDate());
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fall_down));
    }

    @Override
    public int getItemCount() {
        return transactionData.size();
    }

    class TransactionInnerViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView purpose,subscriptionDate,paymentType,credits,amountPaid;
        public TransactionInnerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.transaction_cardview);
            purpose=itemView.findViewById(R.id.purpose);
            subscriptionDate=itemView.findViewById(R.id.transaction_date);
            paymentType=itemView.findViewById(R.id.payment_type);
            credits=itemView.findViewById(R.id.credites);
//            amountPaid=itemView.findViewById(R.id.paid_amount);

        }
    }
}
