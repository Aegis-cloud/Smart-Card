package com.example.smartcard.SessionManager;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.smartcard.Activities.LoginActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_STUDENT_ID = "id";

    // Email address (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String BOOKS_CAT_ID = "book_cat_id";
    public static final String FOOD_TOTAL_AMOUNT = "food_amount";
    public static final String PRODUCT_ID_FOOD = "pid";
    public static final String COUNT = "count";
    public static final String STATUS = "status";
    public static final String LIB_ID = "lib_id";
    public static final String FINE = "fine";
    public static final String AMOUNT = "amount";
    public static final String L_STATUS = "login_status";
    public static final String CARD_ID = "CARD_ID";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createQRCode(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_STUDENT_ID, name);

        // Storing email in pref

        // commit changes
        editor.commit();
    }
  public void Bookfine(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(FINE, name);

        // Storing email in pref

        // commit changes
        editor.commit();
    }
public void card_id(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(CARD_ID, name);

        // Storing email in pref

        // commit changes
        editor.commit();
    }
 public void LoginStatus(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(L_STATUS, name);

        // Storing email in pref

        // commit changes
        editor.commit();
    }

public void Amount(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(AMOUNT, name);
        // Storing email in pref
        // commit changes
        editor.commit();
    }
 public void LibId(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(LIB_ID, name);

        // Storing email in pref

        // commit changes
        editor.commit();
    }
   public void PutStatus(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(STATUS, name);

        // Storing email in pref

        // commit changes
        editor.commit();
    }
  public void LibraryBooks(String id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(BOOKS_CAT_ID, id);

        // Storing email in pref

        // commit changes
        editor.commit();
    }
public void Product_Canteen(String id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(PRODUCT_ID_FOOD, id);

        // Storing email in pref

        // commit changes
        editor.commit();
    }
public void Total(String amount,String count){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(FOOD_TOTAL_AMOUNT, amount);
        editor.putString(COUNT, count);

        // Storing email in pref

        // commit changes
        editor.commit();
    }
  public void createProfile(String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_STUDENT_ID, name);

        // Storing email in pref

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_STUDENT_ID, pref.getString(KEY_STUDENT_ID, null));

        // user email id
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        user.put(BOOKS_CAT_ID, pref.getString(BOOKS_CAT_ID, null));
        user.put(PRODUCT_ID_FOOD, pref.getString(PRODUCT_ID_FOOD, null));
        user.put(FOOD_TOTAL_AMOUNT, pref.getString(FOOD_TOTAL_AMOUNT, null));
        user.put(STATUS, pref.getString(STATUS, null));
        user.put(COUNT, pref.getString(COUNT, null));
        user.put(LIB_ID, pref.getString(LIB_ID, null));
        user.put(FINE, pref.getString(FINE, null));
        user.put(L_STATUS, pref.getString(L_STATUS, null));
        user.put(AMOUNT, pref.getString(AMOUNT, null));
        user.put(CARD_ID, pref.getString(CARD_ID, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
