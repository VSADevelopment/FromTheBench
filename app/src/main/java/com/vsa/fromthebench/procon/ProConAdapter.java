package com.vsa.fromthebench.procon;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vsa.fromthebench.R;

import java.util.List;

/**
 * Created by Alberto on 02/09/2014.
 */
public class ProConAdapter extends BaseAdapter {

    public enum ProConAdapterStyle{PRODUCER, CONSUMER};

    private Context mContext;

    private volatile List<Integer> mList;
    private ProConAdapterStyle mStyle;
    private LayoutInflater mInflater;

    public ProConAdapter(Context context, List<Integer> list, ProConAdapterStyle style){
        mContext = context;
        mList = list;
        mStyle = style;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();

    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = mInflater.inflate(R.layout.row_procon, null);

        LinearLayout layoutContainer = (LinearLayout) view.findViewById(R.id.procon_row_layout);
        TextView textViewNumber = (TextView) view.findViewById(R.id.number_textview);
        switch (mStyle){
            case PRODUCER:
                textViewNumber.setTextColor(Color.WHITE);
                layoutContainer.setBackgroundResource(R.drawable.caja_verde);
                break;
            case CONSUMER:
                textViewNumber.setTextColor(mContext.getResources().getColor(R.color.light_green));
                layoutContainer.setBackgroundResource(R.drawable.caja_gris);
                break;
        }

        textViewNumber.setText(Integer.toString(mList.get(i)));

        return view;
    }
}
