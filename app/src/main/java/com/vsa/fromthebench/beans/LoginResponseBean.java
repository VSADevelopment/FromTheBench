package com.vsa.fromthebench.beans;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alberto on 01/09/2014.
 */
public class LoginResponseBean {

    private static final String TAG = "LoginResponseBean";

    private int mStatus;
    private String mMessage;

    public LoginResponseBean (String json){

        Log.d(TAG, "JSON: " + json);

        if(json == null || json.isEmpty()){
            mStatus = -3;
            mMessage = "Empty response";
        }else{
            try {
                JSONObject jsonObject = new JSONObject(json);
                mStatus = Integer.parseInt(jsonObject.get(Constants.KEY_STATUS).toString());
                if(jsonObject.has(Constants.KEY_MESSAGE))
                    mMessage = jsonObject.get(Constants.KEY_MESSAGE).toString();
                else
                    mMessage = "";
            } catch (JSONException e) {
                e.printStackTrace();
                mStatus = -4;
                mMessage = "Malformed response";
            }
        }

    }

    public int getStatus(){
        return mStatus;
    }

    public String getMessage(){
        return mMessage;
    }

    private class Constants{
        public static final String KEY_STATUS = "status";
        public static final String KEY_MESSAGE = "message";
    }

}
