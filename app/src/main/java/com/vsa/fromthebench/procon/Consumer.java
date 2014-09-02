package com.vsa.fromthebench.procon;

import android.util.Log;

import com.vsa.fromthebench.procon.interfaces.ConsumerInterface;
import com.vsa.fromthebench.utils.HTTPUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alberto on 02/09/2014.
 */
public class Consumer extends Thread {

    private static final String TAG = "Consumer";

    private ConsumerInterface mConsumerInterface;

    public Consumer(ConsumerInterface consumerInterface){

        mConsumerInterface = consumerInterface;

    }

    @Override
    public void run() {
        super.run();

        while(!interrupted()) {
            try {
                Thread.sleep(getDelay());
                Log.d(TAG, "CONSUMED NUMBER: " + mConsumerInterface.consume());
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private int getDelay(){
        return 650 + (int)(Math.random()*1800);
    }

}
