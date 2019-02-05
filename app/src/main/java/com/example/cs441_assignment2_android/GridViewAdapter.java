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
    private List<String> lstSource;
    private Context mContext;

    GridViewAdapter(List<String> lstSource, Context mContext) {
        this.lstSource = lstSource;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return this.lstSource.size();
    }

    @Override
    public Object getItem(int position) {
        return lstSource.get(position);
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
            button.setText(lstSource.get(position));
            button.setBackgroundColor(Color.DKGRAY);
            button.setTextColor(Color.YELLOW);
        } else {
            button = (Button) convertView;
        }

        return button;
    }
}
