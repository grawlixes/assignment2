package com.example.cs441_assignment2_android;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private String[][] lstSource;
    private Context mContext;
    private static final int NUM_ELEMENTS = 16;

    GridViewAdapter(String[][] lstSource, Context mContext) {
        this.lstSource = lstSource;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return NUM_ELEMENTS;
    }

    @Override
    public Object getItem(int position) {
        return lstSource[position/4][position%4];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button button;
        if (convertView == null) {
            button = new Button(mContext);
            button.setLayoutParams(new GridView.LayoutParams(150,150));
            button.setPadding(0,0,0,0);
            button.setText(lstSource[position/4][position%4]);
            button.setBackgroundColor(Color.BLACK);
            button.setTextColor(Color.GREEN);
        } else {
            button = (Button) convertView;
        }

        return button;
    }
}
