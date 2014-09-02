package com.vsa.fromthebench.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vsa.fromthebench.utils.HTTPUtils;
import com.vsa.fromthebench.view.CustomProgressDialog;

/**
 * Created by Alberto on 01/09/2014.
 */
public class LoginAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "LoginAsyncTask";


    private Context mContext;

    private String mUser;
    private String mPassword;
    private String mAction = "http://ftbsports.com/android/api/login.php?user=[user]&password=[password]";

    CustomProgressDialog mProgressDialog;

    public LoginAsyncTask (Context context, String user, String password){

        mContext = context;

        mUser = user;
        mPassword = password;
        mAction = mAction.replace("[user]", mUser);
        mAction = mAction.replace("[password]", mPassword);
        Log.d(TAG, "ACTION: " + mAction);

        mProgressDialog = new CustomProgressDialog(context);
        mProgressDialog.show();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return HTTPUtils.get(mAction);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mProgressDialog.dismiss();
        Log.d(TAG, "RESULT: " + s);
    }
}
