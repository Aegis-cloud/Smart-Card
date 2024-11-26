package com.example.smartcard.Adapters.Parent.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartcard.Activities.HomeActivity;
import com.example.smartcard.Activities.LoginActivity;
import com.example.smartcard.Activities.ParentHomeActivity;
import com.example.smartcard.Activities.SplashScreenActivity;
import com.example.smartcard.Activities.SuccessPage;
import com.example.smartcard.Adapters.Parent.Adpater.Deposit_Adapter;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.Model.Parent.Deposit_items;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class AddAmountFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private EditText amount_pay;
    private TextView error;
    private String amt_pay;
    private GifImageView success_pop;
    private LinearLayout par_lay;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.add_amount_fragment, container, false);
        floatingActionButton = view.findViewById(R.id.btn_pay);
        amount_pay = view.findViewById(R.id.amount_pay);
        error = view.findViewById(R.id.error);
        success_pop = view.findViewById(R.id.success_pop);
        par_lay = view.findViewById(R.id.par_lay);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt_pay = amount_pay.getText().toString();
                if (amt_pay.equals("")){
                    error.setVisibility(View.VISIBLE);
                }else{
                    error.setVisibility(View.GONE);
                    progressDialog.show();
                    add_amount(amt_pay);

                }
            }
        });
        return view;
    }

    private void add_amount(final String amt_pay) {
        final SessionManager sessionManager = new SessionManager(getActivity());
        final HashMap<String,String> user = sessionManager.getUserDetails();
        final String URL = AppInterfaces.BASE_URL+AppInterfaces.ADD_AMOUNT;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if (message.equals("success")){
                                amount_pay.setText("0");
                                startActivity(new Intent(getContext(), SuccessPage.class));
                                getActivity().finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error1) {
                Toast.makeText(getContext(),"Please try Login again !!"+error1,Toast.LENGTH_LONG).show();
                progressDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("amount",amt_pay);
                params.put("cid",user.get(SessionManager.CARD_ID));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}