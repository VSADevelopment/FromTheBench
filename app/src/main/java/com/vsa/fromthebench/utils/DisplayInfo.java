package com.vsa.fromthebench.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Alberto on 03/09/2014.
 */
public class DisplayInfo {

    public static Point getDimensions(Context context){

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
         int screenWidth = 0;
        int screenHeight = 0;
        if(android.os.Build.VERSION.SDK_INT < 10) {
            Display display = windowManager.getDefaultDisplay();
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            screenWidth = metrics.heightPixels;
            screenHeight = metrics.widthPixels;
        }
        return new Point(screenWidth, screenHeight);
    }

}
