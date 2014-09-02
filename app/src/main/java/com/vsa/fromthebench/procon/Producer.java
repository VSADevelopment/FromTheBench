package com.vsa.fromthebench.procon;

import android.util.Log;

import com.vsa.fromthebench.procon.interfaces.ProducerInterface;
import com.vsa.fromthebench.utils.HTTPUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alberto on 02/09/2014.
 */
public class Producer extends Thread {

    private static final String TAG = "Producer";

    private String ACTION_GET_NUMER = "http://ftbsports.com/android/api/get_rand.php";
    private String KEY_NUMBER = "num";

    private ProducerInterface mProducerInterface;

    public Producer (ProducerInterface producerInterface){

        mProducerInterface = producerInterface;

    }

    @Override
    public void run() {
        super.run();

        while(!interrupted()) {
            try {
                Thread.sleep(getDelay());
                int producedNumber = new JSONObject(HTTPUtils.get(ACTION_GET_NUMER)).getInt(KEY_NUMBER);
                mProducerInterface.produce(producedNumber);
                Log.d(TAG, "PROUCED NUMBER: " + producedNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private int getDelay(){
        return 750 + (int)(Math.random()*2000);
    }
}
