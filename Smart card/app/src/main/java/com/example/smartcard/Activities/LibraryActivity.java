package com.example.smartcard.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartcard.Adapters.Canteen.Category_Adapter;
import com.example.smartcard.Adapters.Canteen.Sub_Category_Adapter;
import com.example.smartcard.Adapters.Library.Library_Category_Adapter;
import com.example.smartcard.Adapters.Library.Library_History_Adapter;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Canteen.Category_items;
import com.example.smartcard.Model.Canteen.SubCategory;
import com.example.smartcard.Model.Library.LibraryCategory;
import com.example.smartcard.Model.Library.Library_Items;
import com.example.smartcard.R;
import com.example.smartcard.Utils.CategoryListener;
import com.example.smartcard.Utils.HistoryReturnListener;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity  {

    private RecyclerView categorylist;
    private RecyclerView.LayoutManager layoutManager_Category;
    private List<Library_Items> mSubCategory = new ArrayList<>();
    private Library_Category_Adapter category_adapter;
    private ProgressDialog progressDialog;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_cat);

        categorylist = findViewById(R.id.library_cat_list);
        back = findViewById(R.id.back);
        layoutManager_Category = new GridLayoutManager (this,2);
        categorylist.setLayoutManager(layoutManager_Category);
        categorylist.setHasFixedSize(true);


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

    }

    private void getCategory() {
        String URL = AppInterfaces.BASE_URL+AppInterfaces.LIBRARY_CAT;
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
                                for(int i = 0; i<  jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    Library_Items category_items = new Library_Items(
                                            jsonObject1.optString("book_cat_id"),
                                            jsonObject1.optString("book_cat_name"),
                                            jsonObject1.optString("book_cat_image"),
                                            jsonObject1.optString("book_cat_desc")
                                    );
                                    System.out.println( jsonObject1.optString("book_cat_id")+
                                            jsonObject1.optString("book_cat_name")+
                                            jsonObject1.optString("book_cat_image")+
                                            jsonObject1.optString("book_cat_desc"));
                                    mSubCategory.add(category_items);
                                    category_adapter = new Library_Category_Adapter(mSubCategory, LibraryActivity.this);
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
                Toast.makeText(getApplicationContext(), "Network failed ! Try again later", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }



}
