package com.example.smartcard.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.smartcard.Adapters.Canteen.Category_Adapter;
import com.example.smartcard.Adapters.Canteen.Sub_Category_Adapter;
import com.example.smartcard.Adapters.Stationary.Stationary_Category_Adapter;
import com.example.smartcard.Adapters.Stationary.Stationary_Sub_Category_Adapter;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Canteen.Category_items;
import com.example.smartcard.Model.Canteen.SubCategory;
import com.example.smartcard.Model.Stationary.Product_item;
import com.example.smartcard.Model.Stationary.Store_items;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.Utils.CategoryListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StationaryActivity extends AppCompatActivity implements CategoryListener{

    private RecyclerView categorylist,subcatlist;
    private RecyclerView.LayoutManager layoutManager_Category,layoutManager_SubCategory;
    private List<Store_items> mCategoryItems = new ArrayList<>();
    private List<Product_item> mSubCategory = new ArrayList<>();
    private Stationary_Category_Adapter category_adapter;
    private Stationary_Sub_Category_Adapter sub_category_adapter;
    private ProgressDialog progressDialog;
    private ImageView back,error;
    private RelativeLayout paylayout;
    private TextView totalpay,pay;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary);
        categorylist = findViewById(R.id.stationary_cat_list);
        subcatlist = findViewById(R.id.storesubcatlist);
        back = findViewById(R.id.back);
        paylayout = findViewById(R.id.paylayout);
        totalpay = findViewById(R.id.totalpay);
        error = findViewById(R.id.error);
        pay = findViewById(R.id.pay);

        layoutManager_Category = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, true);
        layoutManager_SubCategory = new GridLayoutManager(this,2);
        categorylist.setLayoutManager(layoutManager_Category);
        subcatlist.setLayoutManager(layoutManager_SubCategory);
        categorylist.setHasFixedSize(true);
        subcatlist.setHasFixedSize(true);
        categorylist.addOnScrollListener(new CenterScrollListener());

        sessionManager = new SessionManager(getApplicationContext());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        getCategory();
        Stationary_Category_Adapter.setTaskListener(this);
        Stationary_Sub_Category_Adapter.setTaskListener(this);

    }
    private void getCategory() {
        String URL = AppInterfaces.BASE_URL+AppInterfaces.STORE_CAT;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.optString("message");
                            if(message.equals("true")){
                                JSONArray jsonArray = jsonObject.optJSONArray("data");
                                System.out.println(jsonArray);
                                for(int i = 0; i < jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    Store_items category_items = new Store_items(
                                            jsonObject1.optString("store_cat_id"),
                                            jsonObject1.optString("store_cat_name"),
                                            jsonObject1.optString("store_cat_image"),
                                            jsonObject1.optString("store_cat_desc")
                                    );
                                    mCategoryItems.add(category_items);
                                    category_adapter = new Stationary_Category_Adapter(mCategoryItems, StationaryActivity.this);
                                    categorylist.setAdapter(category_adapter);
                                }
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"No Category are Available",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(stringRequest);
    }


    @Override
    public void subCategory(String task_id) {

        String URL = AppInterfaces.BASE_URL+AppInterfaces.STORE_PRODUCT+task_id;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        error.setVisibility(View.GONE);
                        subcatlist.setVisibility(View.VISIBLE);
                        mSubCategory.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.optString("message");
                            if(message.equals("true")){
                                JSONArray jsonArray = jsonObject.optJSONArray("data");
                                System.out.println(jsonArray);
                                for(int i = 0; i < jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    Product_item subCategory = new Product_item(
                                            jsonObject1.optString("item_id"),
                                            jsonObject1.optString("item_name"),
                                            jsonObject1.optString("item_amount"),
                                            jsonObject1.optString("item_category"),
                                            jsonObject1.optString("item_quantity"),
                                            jsonObject1.optString("item_image")
                                    );
                                    mSubCategory.add(subCategory);
                                    sub_category_adapter = new Stationary_Sub_Category_Adapter(mSubCategory, StationaryActivity.this);
                                    subcatlist.setAdapter(sub_category_adapter);
                                }
                            }else{
                                progressDialog.dismiss();
                                mSubCategory.clear();
                                error.setVisibility(View.VISIBLE);
                                subcatlist.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"No Category are Available",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error1) {
                progressDialog.dismiss();
                error.setVisibility(View.VISIBLE);
                subcatlist.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),""+error1,Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void Calculation(String amount, int count) {
        if(count==0){
            paylayout.setVisibility(View.GONE);
        }else{
            paylayout.setVisibility(View.VISIBLE);
            totalpay.setText("Total Amount :"+"₹"+Integer.parseInt(amount)*count);
            sessionManager.Total(String.valueOf(Integer.parseInt(amount)*count),String.valueOf(count));
            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.PutStatus("store");
                    startActivity(new Intent(getApplicationContext(), PaymentScannerActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
            });
        }


    }



}
