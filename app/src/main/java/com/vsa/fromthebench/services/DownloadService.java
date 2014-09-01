package com.vsa.fromthebench.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


import com.vsa.fromthebench.beans.ImageInfoBean;
import com.vsa.fromthebench.utils.Constants;
import com.vsa.fromthebench.utils.HTTPUtils;
import com.vsa.fromthebench.utils.PreferencesManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Alberto on 01/09/2014.
 */
public class DownloadService extends IntentService {

    private static final String TAG = "DownloadService";

    private File mApplicationFolder = new File(com.vsa.fromthebench.utils.Constants.APPLICATION_FOLDER);
    private String mJSONRequest;

    private List<ImageInfoBean> mImagesToDownload = new ArrayList<ImageInfoBean>();

    public DownloadService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mApplicationFolder.mkdir();

        File[] files = mApplicationFolder.listFiles() == null ? new File[0] : mApplicationFolder.listFiles();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.KEY_COUNT, files.length);

            JSONArray jsonArray = new JSONArray();
            for(File file: files)
                jsonArray.put(file.getName());
            jsonObject.put(Constants.KEY_LIST, jsonArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "DATA: " + jsonObject.toString());

        BasicNameValuePair param = new BasicNameValuePair(Constants.KEY_DATA, jsonObject.toString());
        ArrayList<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(param);

        mImagesToDownload = parseResponse(HTTPUtils.post(Constants.ACTION_IMAGES_REQUEST, paramsList));

        for(ImageInfoBean imageInfo:mImagesToDownload) {
            Log.d(TAG, "IMAGES URL: " + imageInfo.getImageFileName());
            try {
                HTTPUtils.downloadFile(com.vsa.fromthebench.utils.Constants.APPLICATION_FOLDER + imageInfo.getImageFileName(), imageInfo.getImageUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private List<ImageInfoBean> parseResponse(String json){
        List<ImageInfoBean> imageInfoList = new ArrayList<ImageInfoBean>();
        Log.d(TAG, "RESPONSE: " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(DownloadService.Constants.KEY_LIST);

            for(int x=0;x<jsonArray.length();x++) {
                JSONObject listItem = jsonArray.getJSONObject(x);
                imageInfoList.add(new ImageInfoBean(listItem.getString(Constants.KEY_NAME), listItem.getString(Constants.KEY_URL)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imageInfoList;
    }

    public class Constants{

        public static final String ACTION_IMAGES_REQUEST = "http://ftbsports.com/android/api/get_images_cache.php";

        /**** JSON REQUEST PARAMS ****/
        public static final String KEY_COUNT = "count";
        public static final String KEY_LIST = "list";

        /**** JSON RESPONSE PARAMS ****/
        public static final String KEY_NAME = "nombre";
        public static final String KEY_URL = "url";

        /****  POST PARAMS  ****/
        public static final String KEY_DATA = "data";

    }
}
