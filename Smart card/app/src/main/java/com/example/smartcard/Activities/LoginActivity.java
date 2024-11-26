package com.example.smartcard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import lal.adhish.gifprogressbar.GifView;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private MaterialButton Login;
    private EditText Username;
    private EditText Password;
    private CardView card;
    private TextView Reg;
    ImageView qrcode;
    private String uname, upass;
    private GifView pGif;
    private LinearLayout coordinatorLayout;
    private Bitmap bitmap ;
    private Dialog dialog;
    public final static int QRcodeWidth = 500;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        dialog = new Dialog(this);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        card = findViewById(R.id.card);
//        Reg = findViewById(R.id.reg);
        Login = findViewById(R.id.login);
        dialog.setContentView(R.layout.qr_layout);

        MaterialButton save = dialog.findViewById(R.id.saveqr);
        save.setOnClickListener(this);

        pGif = findViewById(R.id.progressBar);
        pGif.setImageResource(R.drawable.loaderlogin);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = Username.getText().toString();
                upass = Password.getText().toString();
                if (uname.equals("") || upass.equals("")) {

                    Username.setError("Enter your Username");
                    Password.setError("Enter your Password");

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Invalid Username or Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                  card.setVisibility(View.GONE);
                    Login.setVisibility(View.GONE);
                    postvalue();
                }
            }
        });


    }

    private void postvalue() {
        pGif.setVisibility(View.VISIBLE);

        String URL = AppInterfaces.BASE_URL + AppInterfaces.LOGIN;
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pGif.setVisibility(View.GONE);


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.optString("message");
                            if (message.equals("true")) {
                                JSONArray jsonArray = jsonObject.optJSONArray("data");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                String uid = jsonObject1.optString("uid");
                                String type = jsonObject1.optString("type");
                                sessionManager.createQRCode(uid);

                                if (type.equals("parent")){
                                    sessionManager.LoginStatus("parent");
                                    System.out.println("smart card :::::::::::"+uid);
                                    startActivity(new Intent(getApplicationContext(),ParentHomeActivity.class));
                                    finish();
                                }else{
                                    sessionManager.LoginStatus("student");
                                    System.out.println("smart card :::::::::::"+uid);
                                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                    finish();
                                }



                            }else{
                                card.setVisibility(View.VISIBLE);
                                Login.setVisibility(View.VISIBLE);
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, "Login failed, Invalid Username or Password", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            card.setVisibility(View.VISIBLE);
                            Login.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pGif.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Login Failed" + error, Toast.LENGTH_LONG).show();
                card.setVisibility(View.VISIBLE);
                Login.setVisibility(View.VISIBLE);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", uname);
                params.put("password", upass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    //Encode to QRCODE
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor) : getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.saveqr:
                if (isExternalStorageWritable()) {
                    saveImage(bitmap);
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }else{
                    //prompt the user or do something
                }
        }
    }



    private void saveImage(Bitmap finalBitmap) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Smart Card");
        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
        dialog.cancel();
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }else{
                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();

            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "SmartCard_"+ timeStamp +".jpeg";

        File file = new File(mediaStorageDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
