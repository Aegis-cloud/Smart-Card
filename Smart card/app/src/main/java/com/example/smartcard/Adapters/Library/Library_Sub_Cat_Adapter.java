package com.example.smartcard.Adapters.Library;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.smartcard.Activities.CanteenActivity;
import com.example.smartcard.Activities.SuccessPage;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Canteen.Category_items;
import com.example.smartcard.Model.Library.Library_Items;
import com.example.smartcard.Model.Library.LibrarysubItem;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.Utils.CategoryListener;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library_Sub_Cat_Adapter extends RecyclerView.Adapter <Library_Sub_Cat_Adapter.MyViewHolder>{
    List<LibrarysubItem> category_items = new ArrayList<>();
    Context mCtx;
    ProgressDialog progressDialog;
    public static CategoryListener taskListener;

    public static void setTaskListener(CategoryListener listener) {
        taskListener = listener;
    }

    public Library_Sub_Cat_Adapter(List<LibrarysubItem> category_items, Context mCtx) {
        this.category_items = category_items;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_sub_cat_items,parent,false);
        progressDialog = new ProgressDialog(mCtx);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final LibrarysubItem cat_items = category_items.get(position);
        holder.cat_name.setText(cat_items.getBookName());
        Glide.with(mCtx).load(AppInterfaces.IMAGE_URL+cat_items.getBookImage()).centerCrop().into(holder.cat_pic);
        holder.lend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String URL = AppInterfaces.BASE_URL+AppInterfaces.LIBRARY_ADD;
                RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(mCtx, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.optString("status");
                                    String message = jsonObject.optString("message");
                                    if(status.equals("true")){
                                        Toast.makeText(mCtx, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                                    }else if(status.equals(false)||message.equals("Already Exist")){
                                        Toast.makeText(mCtx, "Book Already Issued", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(mCtx, "Insertion Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mCtx, "Insertion Failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(mCtx, "Network failed ! Try again", Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        SessionManager sessionManager = new SessionManager(mCtx);
                        HashMap<String,String> user = sessionManager.getUserDetails();
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("student_id",user.get(SessionManager.KEY_STUDENT_ID));
                        params.put("book_id",cat_items.getBookId());
                        Log.d("params :",""+params);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return category_items.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_pic;
        TextView cat_name;
        TextView lend;
        LinearLayout root_cat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.lib_sub_cat_name);
            cat_pic = itemView.findViewById(R.id.lib_sub_cat_pic);
            lend = itemView.findViewById(R.id.lend);
        }
    }
}
