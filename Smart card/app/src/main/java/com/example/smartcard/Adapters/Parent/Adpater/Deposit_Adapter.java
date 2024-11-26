package com.example.smartcard.Adapters.Parent.Adpater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcard.Model.Parent.Deposit_items;
import com.example.smartcard.Model.Parent.Withdrawal_items;
import com.example.smartcard.R;
import com.example.smartcard.Utils.CategoryListener;

import java.util.ArrayList;
import java.util.List;

public class Deposit_Adapter extends RecyclerView.Adapter <Deposit_Adapter.MyViewHolder>{
    List<Deposit_items> category_items = new ArrayList<>();
    Context mCtx;
    public static CategoryListener taskListener;


    public Deposit_Adapter(List<Deposit_items> category_items, Context mCtx) {
        this.category_items = category_items;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_items_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Deposit_items cat_items = category_items.get(position);
        holder.cat_name.setText("Debited");
        holder.root_cat.setText("₹"+cat_items.getAmount());
        holder.root_cat.setTextColor(Color.GREEN);

        holder.cat_pic.setText(cat_items.getDate());
    }

    @Override
    public int getItemCount() {
        return category_items.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cat_name,cat_pic,root_cat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.w_name);
            cat_pic = itemView.findViewById(R.id.w_date);
            root_cat = itemView.findViewById(R.id.w_amount);
        }
    }
}
