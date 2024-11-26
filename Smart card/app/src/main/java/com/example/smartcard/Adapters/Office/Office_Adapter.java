package com.example.smartcard.Adapters.Office;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Office.Office_items;
import com.example.smartcard.R;
import com.example.smartcard.Utils.HistoryReturnListener;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Office_Adapter extends RecyclerView.Adapter<Office_Adapter.MyViewHolder> {
    List<Office_items> category_items = new ArrayList<>();
    Context mCtx;
    ProgressDialog progressDialog;
    public static HistoryReturnListener taskListener;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat;
    String date, date1;

    public static void setTaskListener(HistoryReturnListener listener) {
        taskListener = listener;
    }

    public Office_Adapter(List<Office_items> category_items, Context mCtx) {
        this.category_items = category_items;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.office_items, parent, false);
        progressDialog = new ProgressDialog(mCtx);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Office_items cat_items = category_items.get(position);
        holder.cat_name.setText(cat_items.getFeesName());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = dateFormat.format(calendar.getTime());

        holder.lend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Date last = dateFormat.parse(cat_items.getFeesLastDate());
                    Date first = dateFormat.parse(date);
                    long diff = 0;
                    diff = (first.getTime()-last.getTime()) / (24 * 60 * 60 * 1000);
                    taskListener.ReturnDialog(cat_items.getFeesLastDate(),cat_items.getFeesDate(),cat_items.getFeesName(),cat_items.getFeesAmount(),diff,cat_items.getFeesId());

                    if(diff >= 0){
                        long total = 0;
                        long payfine = 10;
                        System.out.println("result ::::::::::::::::" + diff);
                        Toast.makeText(mCtx, "result: "+diff, Toast.LENGTH_SHORT).show();
                        total = diff*payfine;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        if (!cat_items.getStatus().equals("paid")) {
            holder.lend.setText("Paid");
            holder.lend.setEnabled(false);
            holder.lend.setBackgroundColor(Color.WHITE);
        } else {
            holder.lend.setText("Due");
            holder.lend.setEnabled(true);
        }
//        Glide.with(mCtx).load(AppInterfaces.IMAGE_URL + cat_items.ge()).centerCrop().into(holder.cat_pic);

    }

    @Override
    public int getItemCount() {
        return category_items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_pic,pic;
        TextView cat_name,fdate,ldate,tfine,title,lend;
        MaterialButton pay;
        LinearLayout root_cat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.lib_sub_cat_name);
            cat_pic = itemView.findViewById(R.id.lib_sub_cat_pic);
            lend = itemView.findViewById(R.id.lend);
            root_cat = itemView.findViewById(R.id.root_cat);



        }
    }
}
