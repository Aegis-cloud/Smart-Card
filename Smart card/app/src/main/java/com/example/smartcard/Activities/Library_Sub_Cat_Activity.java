package com.example.smartcard.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.example.smartcard.Adapters.Canteen.Category_Adapter;
import com.example.smartcard.Adapters.Canteen.Sub_Category_Adapter;
import com.example.smartcard.Adapters.Library.Library_Category_Adapter;
import com.example.smartcard.Adapters.Library.Library_Sub_Cat_Adapter;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Canteen.SubCategory;
import com.example.smartcard.Model.Library.Library_Items;
import com.example.smartcard.Model.Library.LibrarysubItem;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.Utils.CategoryListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library_Sub_Cat_Activity extends AppCompatActivity{

    private RecyclerView categorylist;
    private RecyclerView.LayoutManager layoutManager_Category;
    private List<LibrarysubItem> mSubCategory = new ArrayList<>();
    private Library_Sub_Cat_Adapter category_adapter;
    private ProgressDialog progressDialog;
    private ImageView back;
    private String catid;
    private  SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library__sub__cat_);
        categorylist = findViewById(R.id.library_sub_list);
        back = findViewById(R.id.back);
        layoutManager_Category = new LinearLayoutManager(this);
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
        sessionManager = new SessionManager(this);
        LibrarysubCategory();
    }






    private void LibrarysubCategory() {
        HashMap<String,String> user = sessionManager.getUserDetails();
        catid = user.get(SessionManager.BOOKS_CAT_ID);
        String URL = AppInterfaces.BASE_URL+AppInterfaces.LIBRARY_SUB_CAT+catid;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                                    LibrarysubItem subCategory = new LibrarysubItem(
                                            jsonObject1.optString("book_id"),
                                            jsonObject1.optString("book_name"),
                                            jsonObject1.optString("book_amount"),
                                            jsonObject1.optString("book_category"),
                                            jsonObject1.optString("book_quantity"),
                                            jsonObject1.optString("book_image")
                                    );
                                    mSubCategory.add(subCategory);
                                    category_adapter = new Library_Sub_Cat_Adapter(mSubCategory,Library_Sub_Cat_Activity.this);
                                    categorylist.setAdapter(category_adapter);
                                }
                            }else{
                                progressDialog.dismiss();
                                mSubCategory.clear();
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
}
