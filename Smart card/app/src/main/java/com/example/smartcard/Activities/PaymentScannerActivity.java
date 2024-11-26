package com.example.smartcard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartcard.AppConfig.AppInterfaces;
import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PaymentScannerActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    private ProgressDialog progressDialog;
    String intentData = "";
    boolean isEmail = false;
    String type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_scanner);
        initViews();


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing Payment");

    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetails();
        type = user.get(SessionManager.STATUS);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("fine")){
                    if (intentData.length() > 0) {
                        if (isEmail)
                        {
                            progressDialog.show();
                            BookPayment(intentData);
                        }
                        else {
                            progressDialog.show();
                            BookPayment(intentData);
                        }
                    }
                }
             else if(type.equals("office")){
                    if (intentData.length() > 0) {
                        if (isEmail)
                        {
                            progressDialog.show();
                            OfficePayment(intentData);
                        }
                        else {
                            progressDialog.show();
                            OfficePayment(intentData);
                        }
                    }
                }
                else{
                    if (intentData.length() > 0) {
                        if (isEmail)
                        {
                            progressDialog.show();
                            Payment(intentData);
                        }
                        else {
                            progressDialog.show();
                            Payment(intentData);
                        }
                    }
                }

            }
        });
    }

    private void OfficePayment(final String intentData) {
        String URL = AppInterfaces.BASE_URL+AppInterfaces.OFFICE_PAY;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.optString("status");
                            String message = jsonObject.optString("message");
                            if(message.equals("Insufficient")){
                                Toast.makeText(getApplicationContext(), "InSufficient Balance ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HistoryBooks.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                            if (status.equals("false")){
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),HistoryBooks.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                Toast.makeText(getApplicationContext(), "Sorry unable to process payment", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),SuccessPage.class));
                                finish();
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
                params.put("student_id",intentData);
                System.out.println(intentData);
                params.put("fees_id",user.get(SessionManager.LIB_ID));
                params.put("fine",user.get(SessionManager.FINE));
                params.put("amount",user.get(SessionManager.AMOUNT));
                Log.d("params :",""+params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void BookPayment(final String intentData) {
        String URL = AppInterfaces.BASE_URL+AppInterfaces.FINE_POST;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.optString("status");
                            String message = jsonObject.optString("message");
                            if(message.equals("Insufficient")){
                                Toast.makeText(getApplicationContext(), "InSufficient Balance ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HistoryBooks.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                            if (status.equals("false")){
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),HistoryBooks.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                Toast.makeText(getApplicationContext(), "Sorry unable to process payment", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),SuccessPage.class));
                                finish();
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
                params.put("student_id",intentData);
                System.out.println(intentData);
                params.put("lib_id",user.get(SessionManager.LIB_ID));
                params.put("fine",user.get(SessionManager.FINE));
                Log.d("params :",""+params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(PaymentScannerActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "Please Restart your app and try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("ADD CONTENT TO THE MAIL");

                            } else {
                                isEmail = false;
                                btnAction.setVisibility(View.VISIBLE);
                                btnAction.setText("PROCEED");
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);
                            }
                        }
                    });
                }
            }
        });
    }

    private void Payment(final String intentData) {
        String URL = AppInterfaces.BASE_URL+AppInterfaces.PAYMENT_FOOD;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                                finish();
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
                params.put("pid",user.get(SessionManager.PRODUCT_ID_FOOD));
                params.put("sid",intentData);
                System.out.println(intentData);
                params.put("amount",user.get(SessionManager.FOOD_TOTAL_AMOUNT));
                params.put("quantity",user.get(SessionManager.COUNT));
                params.put("type",user.get(SessionManager.STATUS));
                Log.d("params :",""+params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

}
