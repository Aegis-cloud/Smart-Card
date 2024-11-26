package com.example.smartcard.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.smartcard.Adapters.Office.Office_Adapter;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Office.Office_items;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.Utils.HistoryReturnListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficeActivity extends AppCompatActivity implements HistoryReturnListener {

    private RecyclerView historylist;
    private RecyclerView.LayoutManager layoutManager;
    private List<Office_items> mHistoryBooks = new ArrayList<>();
    private Office_Adapter category_adapter;
    private ProgressDialog progressDialog;
    private ImageView back,historyerror,pic;
    private Dialog dialog;
    private TextView title,fdate,ldate,fine;
    private TextView payBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);
        historylist = findViewById(R.id.office_list);
        back = findViewById(R.id.back);
        Office_Adapter.setTaskListener(this);

        historyerror = findViewById(R.id.error);
        layoutManager = new LinearLayoutManager(this);
        historylist.setLayoutManager(layoutManager);
        historylist.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.return_book_dialog);
        title = dialog.findViewById(R.id.titledialog);
        fdate = dialog.findViewById(R.id.issueddate);
        ldate = dialog.findViewById(R.id.lastdate);
        fine = dialog.findViewById(R.id.totalfine);
        pic = dialog.findViewById(R.id.picdialog);
        payBtn = dialog.findViewById(R.id.dialogbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        getHistory();

    }

    private void getHistory() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String,String> user = sessionManager.getUserDetails();
        String sid = user.get(SessionManager.KEY_STUDENT_ID);
        String URL = AppInterfaces.BASE_URL+AppInterfaces.OFFICE_GET+sid;
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

                                historyerror.setVisibility(View.GONE);
                                historylist.setVisibility(View.VISIBLE);
                                JSONArray jsonArray = jsonObject.optJSONArray("data");
                                System.out.println(jsonArray);
                                for(int i = 0; i<  jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    Office_items category_items = new Office_items(
                                            jsonObject1.optString("fees_id"),
                                            jsonObject1.optString("fees_name"),
                                            jsonObject1.optString("fees_desc"),
                                            jsonObject1.optString("fees_amount"),
                                            jsonObject1.optString("fees_date"),
                                            jsonObject1.optString("fees_last_date"),
                                            jsonObject1.optString("status")
                                    );
                                    System.out.println( jsonObject1.optString("book_cat_id")+
                                            jsonObject1.optString("book_cat_name")+
                                            jsonObject1.optString("book_cat_image")+
                                            jsonObject1.optString("book_cat_desc"));

                                    mHistoryBooks.add(category_items);
                                    category_adapter = new Office_Adapter(mHistoryBooks, getApplicationContext());
                                    historylist.setAdapter(category_adapter);
                                }
                            }else{
                                historylist.setVisibility(View.GONE);
                                historyerror.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"No Books are Available",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            historylist.setVisibility(View.GONE);
                            historyerror.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                historylist.setVisibility(View.GONE);
                historyerror.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"OOPS! Server  Error ",Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(stringRequest);
    }


    @Override
    public void ReturnDialog(String dateLast, String date, String bookName, final String bookImage, long diff, String bookId) {
        dialog.show();
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.LibId(bookId);
        sessionManager.Amount(bookImage);
        if(diff <=0){
            fine.setText("NO FINE");
            fine.setTextColor(Color.GREEN);
            title.setText(bookName);
            ldate.setText(dateLast);
            fdate.setText(date);
            sessionManager.Bookfine(""+0);
            payBtn.setText("Pay");
            payBtn.setBackgroundColor(Color.GREEN);
            payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    withfine();
                }
            });

        }else{
            long amt = 15;
            long totfine = amt * diff;
            fine.setText("Rs."+totfine);
            fine.setTextColor(Color.RED);
            title.setText(bookName);
            ldate.setText(dateLast);
            fdate.setText(date);
            sessionManager.Bookfine(""+totfine);
            payBtn.setText("Pay");
            sessionManager.PutStatus("office");
            payBtn.setBackgroundColor(Color.RED);
            payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    withfine();
                }
            });
        }
    }

    private void withfine() {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.PutStatus("office");
        startActivity(new Intent(getApplicationContext(),PaymentScannerActivity.class));

    }

    private void nofine(String bookImage) {
progressDialog.show();
        BookPayment();
    }
    private void BookPayment() {

        String URL = AppInterfaces.BASE_URL+AppInterfaces.FINE_POST;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.optString("status");
                            String message = jsonObject.optString("message");
                            if(message.equals("Insufficient")){
                                Toast.makeText(getApplicationContext(), "InSufficient Balance ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),CanteenActivity.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                            if (status.equals("false")){
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),CanteenActivity.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                Toast.makeText(getApplicationContext(), "Sorry unable to process payment", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),SuccessPage.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                HashMap<String,String> user = sessionManager.getUserDetails();
                Map<String,String> params = new HashMap<String, String>();
                params.put("student_id",user.get(SessionManager.KEY_STUDENT_ID));
                System.out.println(user.get(SessionManager.KEY_STUDENT_ID));
                params.put("fees_id",user.get(SessionManager.LIB_ID));
                params.put("fine",user.get(SessionManager.FINE));
                params.put("amount",user.get(SessionManager.AMOUNT));
                Log.d("params :",""+params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
