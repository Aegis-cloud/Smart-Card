package com.example.smartcard.Adapters.Parent.Adpater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcard.Adapters.Parent.Fragment.Withdrawal;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Canteen.Category_items;
import com.example.smartcard.Model.Parent.Withdrawal_items;
import com.example.smartcard.R;
import com.example.smartcard.Utils.CategoryListener;

import java.util.ArrayList;
import java.util.List;

public class Withdrawal_Adapter extends RecyclerView.Adapter <Withdrawal_Adapter.MyViewHolder>{
    List<Withdrawal_items> category_items = new ArrayList<>();
    Context mCtx;
    public static CategoryListener taskListener;


    public Withdrawal_Adapter(List<Withdrawal_items> category_items, Context mCtx) {
        this.category_items = category_items;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdrawal_items_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Withdrawal_items cat_items = category_items.get(position);
        holder.cat_name.setText(cat_items.getName());
        holder.root_cat.setText("₹"+cat_items.getAmount());
        holder.root_cat.setTextColor(Color.RED);
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
