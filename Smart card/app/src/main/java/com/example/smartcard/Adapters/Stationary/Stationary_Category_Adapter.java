package com.example.smartcard.Adapters.Stationary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Canteen.Category_items;
import com.example.smartcard.Model.Stationary.Store_items;
import com.example.smartcard.R;
import com.example.smartcard.Utils.CategoryListener;

import java.util.ArrayList;
import java.util.List;

public class Stationary_Category_Adapter extends RecyclerView.Adapter <Stationary_Category_Adapter.MyViewHolder>{
    List<Store_items> category_items = new ArrayList<>();
    Context mCtx;
    public static CategoryListener taskListener;

    public static void setTaskListener(CategoryListener listener) {
        taskListener = listener;
    }

    public Stationary_Category_Adapter(List<Store_items> category_items, Context mCtx) {
        this.category_items = category_items;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationary_cat_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Store_items cat_items = category_items.get(position);
        holder.cat_name.setText(cat_items.getStoreCatName());
        holder.root_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskListener.subCategory(cat_items.getStoreCatId());
            }
        });

        Glide.with(mCtx).load(AppInterfaces.IMAGE_URL+cat_items.getStoreCatImage()).centerCrop().into(holder.cat_pic);
    }

    @Override
    public int getItemCount() {
        return category_items.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_pic;
        TextView cat_name;
        RelativeLayout root_cat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.cat_name);
            cat_pic = itemView.findViewById(R.id.cat_pic);
            root_cat = itemView.findViewById(R.id.root_cat);
        }
    }
}
