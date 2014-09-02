package com.vsa.fromthebench.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Alberto on 01/09/2014.
 */
public class PreferencesManager {

    private static final String TAG = "PreferencesManager";

    private static SharedPreferences mSharedPreferences = null;

    private static void getSharedPreferences(Context context){
        if(mSharedPreferences == null)
            mSharedPreferences = context.getSharedPreferences(Constants.KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void putString(Context context, String key, String value){
        getSharedPreferences(context);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key){
        getSharedPreferences(context);
        return mSharedPreferences.getString(key, "");
    }

    public static void delete(Context context, String... keys){
        getSharedPreferences(context);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for(String key:keys)
            editor.remove(key);
        editor.commit();
    }

    public static void storeCredentials(Context context, String user, String password){
        getSharedPreferences(context);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.KEY_USER, user);
        editor.putString(Constants.KEY_PASSWORD, password);
        editor.commit();
    }

    public static String getUser(Context context){
        Log.d(TAG, "USER:" + getString(context,Constants.KEY_USER));
        return getString(context,Constants.KEY_USER);
    }

    public static String getPassword(Context context){
        return getString(context, Constants.KEY_PASSWORD);
    }

    public class Constants{

        public static final String KEY_SHARED_PREFERENCES = "ftb_prefs";

        public static final String KEY_USER = "user";
        public static final String KEY_PASSWORD = "password";


    }


}
