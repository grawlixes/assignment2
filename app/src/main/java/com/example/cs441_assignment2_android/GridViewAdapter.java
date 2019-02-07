package com.example.cs441_assignment2_android;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.Random;
import java.lang.Math.*;

// I used this to learn how to use GridView and create adapters:
// https://www.youtube.com/watch?v=HbEHxkAEumU

public class GridViewAdapter extends BaseAdapter {
    private String[][] lst_source;
    private Context m_context;
    private int available_spaces = 16;
    private Random rng = new Random();

    private static final int NUM_ELEMENTS = 16;

    GridViewAdapter(String[][] lst_source, Context m_context) {
        this.lst_source = lst_source;
        this.m_context = m_context;
    }

    @Override
    public int getCount() {
        return NUM_ELEMENTS;
    }

    @Override
    public Object getItem(int position) {
        return lst_source[position/4][position%4];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convert_view, ViewGroup parent) {
        final Button button;
        if (convert_view == null) {
            button = new Button(m_context);
            button.setLayoutParams(new GridView.LayoutParams(150,150));
            button.setPadding(0,0,0,0);
            button.setText(lst_source[position/4][position%4]);
                // Empty spaces are black
            if (lst_source[position/4][position%4] == "-") {
                button.setBackgroundColor(Color.BLACK);
                button.setTextColor(Color.GREEN);
                // Winning spaces (2048) are purely green
            } else if (lst_source[position/4][position%4] == "2048") {
                button.setBackgroundColor(Color.GREEN);
                button.setTextColor(Color.BLACK);
                // All other spaces become darker as they go up
            } else {
                // Decide the color based on the number - lower means more green.
                int power = (int) (Math.log(Integer.parseInt(lst_source[position/4][position%4])) /
                        Math.log(2));
                int new_color = 0xFF - (power)*(0xFF/11);
                int actual = new_color << 8;

                // If it's too dark, make the text color green so it's easier to see.
                if (new_color < 128) {
                    button.setTextColor(Color.GREEN);
                } else {
                    button.setTextColor(Color.BLACK);
                }

                // Android uses 32-bit hex codes for color. This took me a long time to debug. >:(
                button.setBackgroundColor(0xFF000000 | actual);
            }
        } else {
            button = (Button) convert_view;
        }

        return button;
    }

    // Package-private method that generates a new number (2)
    // to a random available space. Ends the game if impossible
    // if it's impossible, return false - otherwise return true.
    boolean generateRandom2() {
        if (available_spaces == 0) {
            return false;
        } else {
            int index_to_use = Math.abs(rng.nextInt() % available_spaces);

            for (int i = 0; i < 4; i++) {
                int j;
                for (j = 0; j < 4; j++) {
                    if (index_to_use == 0 && lst_source[i][j] == "-") {
                        lst_source[i][j] = "2";
                        available_spaces--;
                        break;
                    } else if (lst_source[i][j] == "-") {
                        index_to_use--;
                    } else {
                        ;
                    }
                }

                if (j < 4) {
                    break;
                }
            }
        }

        return true;
    }
}
