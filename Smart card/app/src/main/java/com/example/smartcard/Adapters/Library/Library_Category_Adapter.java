package com.example.smartcard.Adapters.Library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcard.Activities.Library_Sub_Cat_Activity;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Canteen.SubCategory;
import com.example.smartcard.Model.Library.Library_Items;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.Utils.CategoryListener;

import java.util.ArrayList;
import java.util.List;

public class Library_Category_Adapter extends RecyclerView.Adapter<Library_Category_Adapter.MyViewHolder> {
    List<Library_Items> category_items = new ArrayList<>();
    Context mCtx;

    public Library_Category_Adapter(List<Library_Items> category_items, Context mCtx) {
        this.category_items = category_items;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_cat_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Library_Items cat_items = category_items.get(position);
        holder.cat_name.setText(cat_items.getBookCatName());
        holder.subcat_amt.setText(cat_items.getBookCatDesc());

        Glide.with(mCtx).load(AppInterfaces.IMAGE_URL + cat_items.getBookCatImage()).centerCrop().into(holder.cat_pic);
        System.out.println(AppInterfaces.IMAGE_URL + cat_items.getBookCatImage());
        holder.cat_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) mCtx;
                SessionManager sessionManager = new SessionManager(mCtx);
                sessionManager.LibraryBooks(cat_items.getBookCatId());
                mCtx.startActivity(new Intent(mCtx.getApplicationContext(), Library_Sub_Cat_Activity.class));
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return category_items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_pic;
        TextView subcat_amt, cat_name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.lib_cat_name);
            cat_pic = itemView.findViewById(R.id.lib_cat_image);
            subcat_amt = itemView.findViewById(R.id.lib_cat_desc);

        }
    }
}
