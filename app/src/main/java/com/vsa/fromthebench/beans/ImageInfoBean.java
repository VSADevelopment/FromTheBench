package com.vsa.fromthebench.beans;

import com.vsa.fromthebench.services.DownloadService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto on 01/09/2014.
 */
public class ImageInfoBean {

    private String mImageFileName = "";
    private URL mImageUrl = null;

    private List<String> mURLList = new ArrayList<String>();

    public ImageInfoBean(String imageFileName, String imageUrl){
        mImageFileName = imageFileName;
        try {
            mImageUrl = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getImageFileName(){
        return mImageFileName;
    }

    public URL getImageUrl(){
        return mImageUrl;
    }



}
