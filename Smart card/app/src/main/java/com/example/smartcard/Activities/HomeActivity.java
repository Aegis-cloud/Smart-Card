package com.example.smartcard.Activities;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;
import com.example.smartcard.menu.DrawerAdapter;
import com.example.smartcard.menu.DrawerItem;
import com.example.smartcard.menu.SimpleItem;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by yarolegovich on 25.03.2017.
 */

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, View.OnClickListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_GEN_CODE = 2;
    private static final int POS_LOGOUT = 3;
    private Bitmap bitmap;
    private ImageView qrcode;
    private Dialog dialog;
    public final static int QRcodeWidth = 500;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    private String uid;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private ImageView library,canteen,office,stationary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        dialog.setContentView(R.layout.qr_layout);

        MaterialButton save = dialog.findViewById(R.id.saveqr);
        save.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("While Generating your QR COde");
        progressDialog.setTitle("Please wait a sec");
        progressDialog.setCancelable(false);

        canteen = findViewById(R.id.canteen_icon);
        library = findViewById(R.id.library_icon);
        stationary = findViewById(R.id.stationary_icon);
        office = findViewById(R.id.office_icon);

        canteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CanteenActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LibraryActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        stationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StationaryActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
       office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OfficeActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_GEN_CODE),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            sessionManager.logoutUser();
            finish();

        }
      if (position == POS_DASHBOARD) {



      }
        if (position == POS_ACCOUNT) {
            startActivity(new Intent(getApplicationContext(),HistoryBooks.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if (position == POS_GEN_CODE) {

            progressDialog.show();
            HashMap<String,String> user = sessionManager.getUserDetails();
            uid = user.get(SessionManager.KEY_STUDENT_ID);
            qrcode = dialog.findViewById(R.id.image);
            dialog.setTitle("Click to save your QRCode");
            System.out.println("smart card :::::::::::" + uid);
            try {
                progressDialog.show();
                bitmap = TextToImageEncode(uid);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            qrcode.setImageBitmap(bitmap);
            progressDialog.dismiss();
            dialog.show();
            dialog.setCancelable(false);
        }

        slidingRootNav.closeMenu();

    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }


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
    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
