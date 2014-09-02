package com.vsa.fromthebench;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.vsa.fromthebench.procon.Consumer;
import com.vsa.fromthebench.procon.Producer;
import com.vsa.fromthebench.procon.interfaces.ConsumerInterface;
import com.vsa.fromthebench.procon.interfaces.ProducerInterface;
import com.vsa.fromthebench.procon.view.ProConAdapter;

import java.util.Stack;


public class ProCon extends Activity implements ProducerInterface, ConsumerInterface {

    private LinearLayout mLayoutProducer;
    private LinearLayout mLayoutConsumer;

    private ListView mListViewProducer;
    private ListView mListViewConsumer;
    private ProConAdapter mProducerAdapter;
    private ProConAdapter mConsumerAdapter;

    private volatile Stack<Integer> mProducerQueue = new Stack<Integer>();
    private Stack<Integer> mConsumerQueue = new Stack<Integer>();

    private Producer mProducer = new Producer(this);
    private Consumer mConsumer = new Consumer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_con);

        mLayoutProducer = (LinearLayout) findViewById(R.id.producer_layout);
        mLayoutConsumer = (LinearLayout) findViewById(R.id.consumer_layout);

        mListViewProducer = (ListView) findViewById(R.id.producer_listview);
        mListViewConsumer = (ListView) findViewById(R.id.consumer_listview);

        mProducerAdapter = new ProConAdapter(this, mProducerQueue, ProConAdapter.ProConAdapterStyle.PRODUCER);
        mConsumerAdapter = new ProConAdapter(this, mConsumerQueue, ProConAdapter.ProConAdapterStyle.CONSUMER);

        mListViewProducer.setAdapter(mProducerAdapter);
        mListViewConsumer.setAdapter(mConsumerAdapter);

        mProducer.start();
        mConsumer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mProducer.interrupt();
    }


    @Override
    public synchronized Integer consume() {
        if(mProducerQueue.size()>0 && mConsumerQueue.size()<10) {
            int num = mProducerQueue.pop();
            mConsumerQueue.push(num);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProducerAdapter.notifyDataSetChanged();
                    mConsumerAdapter.notifyDataSetChanged();
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProducerAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}