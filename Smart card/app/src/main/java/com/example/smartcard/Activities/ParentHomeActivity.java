package com.example.smartcard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartcard.Adapters.Parent.Adpater.TabAdapter;
import com.example.smartcard.Adapters.Parent.Fragment.BalanceFragment;
import com.example.smartcard.Adapters.Parent.Fragment.AddAmountFragment;
import com.example.smartcard.Adapters.Parent.Fragment.Withdrawal;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.Utils.BalanceListener;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParentHomeActivity extends AppCompatActivity implements BalanceListener {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SessionManager sessionManager;
    private TextView balance_amount,balance_ac_no;
    private Context context;
    private ImageView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);
        sessionManager = new SessionManager(ParentHomeActivity.this);
        sessionManager.checkLogin();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        balance_amount =  findViewById(R.id.balance_amount);
        logout =  findViewById(R.id.logout);
        balance_ac_no =  findViewById(R.id.balance_ac_no);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new BalanceFragment(), "STATEMENT");
        adapter.addFragment(new AddAmountFragment(), "NEW PAYMENT");
        viewPager.setAdapter(adapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
                finish();
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        Withdrawal.setTaskListener(this);

    }


    @Override
    public void Calculation(String amount, String count) {
        final SessionManager sessionManager = new SessionManager(getApplicationContext());
        final HashMap<String,String> user = sessionManager.getUserDetails();
        final String URL = AppInterfaces.BASE_URL+AppInterfaces.BALANCE+amount+"/"+count;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(URL);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String sid = jsonObject1.getString("card_id");
                            String amount = jsonObject1.getString("amount");
                            balance_amount.setText("₹"+amount);
                            String ac_no = jsonObject1.getString("account");
                            balance_ac_no.setText(ac_no);
                            sessionManager.card_id(sid);
                            System.out.println(user.get(SessionManager.CARD_ID));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error1) {
                Toast.makeText(getApplicationContext(),"Please try Login again !!"+error1,Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}