package com.example.smartcard.Adapters.Stationary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Canteen.SubCategory;
import com.example.smartcard.Model.Stationary.Product_item;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.Utils.CategoryListener;

import java.util.ArrayList;
import java.util.List;

public class Stationary_Sub_Category_Adapter extends RecyclerView.Adapter<Stationary_Sub_Category_Adapter.MyViewHolder> {
    List<Product_item> category_items = new ArrayList<>();
    Context mCtx;
    public static CategoryListener taskListener;
    public static void setTaskListener(CategoryListener listener) {
        taskListener = listener;
    }

    public Stationary_Sub_Category_Adapter(List<Product_item> category_items, Context mCtx) {
        this.category_items = category_items;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationary_sub_cat_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final int c = 1;
        final Product_item cat_items = category_items.get(position);
        holder.cat_name.setText(cat_items.getItemName());
        holder.subcat_amt.setText(cat_items.getItemAmount());
        holder.addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(mCtx);
                sessionManager.Product_Canteen(cat_items.getItemId());
                taskListener.Calculation(cat_items.getItemAmount(),c);

                holder.count.setText("1");
                holder.addproduct.setVisibility(View.GONE);
                holder.add_root.setVisibility(View.VISIBLE);
                holder.plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int c = Integer.parseInt(holder.count.getText().toString());
                        c++;
                        holder.count.setText("" + c);
                        taskListener.Calculation(cat_items.getItemAmount(),c);
                    }
                });
                holder.minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.count.getText().equals("1")) {
                            taskListener.Calculation(cat_items.getItemAmount(),0);
                            holder.addproduct.setVisibility(View.VISIBLE);
                            holder.add_root.setVisibility(View.GONE);
                        } else {
                            int c = Integer.parseInt(holder.count.getText().toString());
                            c--;
                            holder.count.setText("" + c);
                            taskListener.Calculation(cat_items.getItemAmount(),c);
                        }
                    }
                });
            }
        });
        Glide.with(mCtx).load(AppInterfaces.IMAGE_URL + cat_items.getItemImage()).centerCrop().into(holder.cat_pic);
    }

    @Override
    public int getItemCount() {
        return category_items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_pic;
        LinearLayout add_root;
        TextView subcat_amt, cat_name, plus, minus, count;
        Button addproduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.sub_cat_name);
            cat_pic = itemView.findViewById(R.id.sub_cat_pic);
            subcat_amt = itemView.findViewById(R.id.subcat_amt);
            count = itemView.findViewById(R.id.count);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            add_root = itemView.findViewById(R.id.add_root);
            addproduct = itemView.findViewById(R.id.addproduct);
        }
    }
}
