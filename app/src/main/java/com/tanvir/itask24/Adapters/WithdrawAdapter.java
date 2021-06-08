package com.tanvir.itask24.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tanvir.itask24.Model.WithdrawModel;
import com.tanvir.itask24.R;

import java.util.ArrayList;
import java.util.List;

public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.MyViewHolder> {
   private Context context;
   private List<WithdrawModel> withdrawList=new ArrayList<>();
    int doller;
    public WithdrawAdapter(Context context, List<WithdrawModel> withdrawList,int doller) {
        this.context = context;
        this.withdrawList = withdrawList;
        this.doller=doller;
    }

    @NonNull
    @Override
    public WithdrawAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.transection_item_layout,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull WithdrawAdapter.MyViewHolder holder, int position) {
        WithdrawModel currentItem=withdrawList.get(position);
        holder.methodTv.setText(currentItem.getPayment());
        holder.timeTv.setText(currentItem.getTime());

        int doller1= (int) ((double)Integer.parseInt(currentItem.getCoins())/(double)doller);
        holder.coinTv.setText("$"+doller1);
        if(!currentItem.getState().equals("pending")){
            holder.imageView.setImageResource(R.drawable.paid);
        }else{
            holder.imageView.setImageResource(R.drawable.profile);
        }


    }

    @Override
    public int getItemCount() {
        return withdrawList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView methodTv,timeTv,coinTv;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            methodTv=itemView.findViewById(R.id.t_MethodTv);
            timeTv=itemView.findViewById(R.id.t_timeTv);
            coinTv=itemView.findViewById(R.id.t_coinTv);
            imageView=itemView.findViewById(R.id.t_ImageView);


        }
    }
}
