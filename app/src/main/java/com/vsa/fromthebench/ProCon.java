package com.vsa.fromthebench;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.vsa.fromthebench.procon.Consumer;
import com.vsa.fromthebench.procon.Producer;
import com.vsa.fromthebench.procon.interfaces.ConsumerInterface;
import com.vsa.fromthebench.procon.interfaces.ProducerInterface;
import com.vsa.fromthebench.procon.ProConAdapter;

import java.util.Stack;


public class ProCon extends Activity implements ProducerInterface, ConsumerInterface {

    private ListView mListViewProducer;
    private ListView mListViewConsumer;
    private ProConAdapter mProducerAdapter;
    private ProConAdapter mConsumerAdapter;

    private volatile Stack<Integer> mProducerQueue = new Stack<Integer>();
    private volatile Stack<Integer> mConsumerQueue = new Stack<Integer>();

    private Producer mProducer = new Producer(this);
    private Consumer mConsumer = new Consumer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_con);

        mListViewProducer = (ListView) findViewById(R.id.producer_listview);
        mListViewConsumer = (ListView) findViewById(R.id.consumer_listview);

        mListViewProducer.setAdapter(mProducerAdapter);
        mListViewConsumer.setAdapter(mConsumerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mProducer.start();
        mConsumer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mProducer.interrupt();
        mConsumer.interrupt();
    }


    @Override
    public synchronized Integer consume() {
        if(mProducerQueue.size()>0 && mConsumerQueue.size()<10) {
            int num = mProducerQueue.pop();
            mConsumerQueue.push(num);
            mProducerAdapter = new ProConAdapter(ProCon.this, mProducerQueue, ProConAdapter.ProConAdapterStyle.PRODUCER);
            mConsumerAdapter = new ProConAdapter(ProCon.this, mConsumerQueue, ProConAdapter.ProConAdapterStyle.CONSUMER);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Why don´t you use notifyDataSetChanged method motherfucker? http://stackoverflow.com/questions/3132021/android-listview-illegalstateexception-the-content-of-the-adapter-has-changed
                    mListViewProducer.setAdapter(mProducerAdapter);
                    mListViewConsumer.setAdapter(mConsumerAdapter);

                }
            });
            return num;
        }
        return null;
    }

    @Override
    public synchronized void produce(Integer num) {
        if(mProducerQueue.size()<10) {
            mProducerQueue.push(num);
            mProducerAdapter = new ProConAdapter(ProCon.this, mProducerQueue, ProConAdapter.ProConAdapterStyle.PRODUCER);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Why don´t you use notifyDataSetChanged method motherfucker? http://stackoverflow.com/questions/3132021/android-listview-illegalstateexception-the-content-of-the-adapter-has-changed
                    mListViewProducer.setAdapter(mProducerAdapter);
                }
            });
        } else{
            if(mConsumerQueue.size() == 10){
                mProducer.interrupt();
                mConsumer.interrupt();
            }
        }
    }
}
