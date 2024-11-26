package com.example.smartcard.Adapters.Parent.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartcard.Adapters.Parent.Adpater.Deposit_Adapter;
import com.example.smartcard.Adapters.Parent.Adpater.Withdrawal_Adapter;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Parent.Deposit_items;
import com.example.smartcard.Model.Parent.Withdrawal_items;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.Utils.BalanceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Deposits extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Deposit_items> withdrawal_items = new ArrayList<>();
    private Deposit_Adapter adapter;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.deposit, container, false);
        recyclerView = view.findViewById(R.id.depositList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        getStudent();
        return view;
    }


    private void getStudent() {
        final SessionManager sessionManager = new SessionManager(getActivity());
        final HashMap<String,String> user = sessionManager.getUserDetails();
        final String URL = AppInterfaces.BASE_URL+AppInterfaces.GETSTUDENT+user.get(SessionManager.KEY_STUDENT_ID);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(URL);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String sid = jsonObject1.getString("student_id");
                            System.out.println(user.get(SessionManager.KEY_STUDENT_ID));
                            progressDialog.show();
                            getWithdrawalList(sid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error1) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Please try Login again !!"+error1,Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(stringRequest);
    }

    private void getWithdrawalList(final String sid) {

        SessionManager sessionManager = new SessionManager(getActivity());
        final HashMap<String,String> user = sessionManager.getUserDetails();
        String URL = AppInterfaces.BASE_URL+AppInterfaces.DEPOSIT_HISTORY+user.get(SessionManager.CARD_ID);
        System.out.println(URL);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

                                    Deposit_items subCategory = new Deposit_items(
                                            jsonObject1.optString("h_id"),
                                            jsonObject1.optString("card_id"),
                                            jsonObject1.optString("amount"),
                                            jsonObject1.optString("date")

                                    );

                                    withdrawal_items.add(subCategory);
                                    adapter = new Deposit_Adapter(withdrawal_items, getActivity());
                                    recyclerView.setAdapter(adapter);
                                }
                            }else{
                                progressDialog.dismiss();
                                withdrawal_items.clear();
                                Toast.makeText(getContext(),"No Recent transfer are Available",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(),""+error1,Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(stringRequest);
    }
}