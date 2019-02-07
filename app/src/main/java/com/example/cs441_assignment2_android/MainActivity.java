package com.example.cs441_assignment2_android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String lstSource[][] = new String[4][4];
    GridView gv;
    GridViewAdapter gva;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Build the empty 2048 grid.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                lstSource[i][j] = "-";
            }
        }

        // Update the grid.
        gv = findViewById(R.id.gridview);
        gva = new GridViewAdapter(lstSource, this);
        gv.setAdapter(gva);

        // Put a background behind the grid.
        iv = findViewById(R.id.imageView);
        iv.setY(-25);

        // Generate the first random '2' block.

        gva.generateRandom2();

        FloatingActionButton fab = findViewById(R.id.fabC);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            // This code runs when you try flipping the board clockwise.
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fabCC);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            // This code runs when you try flipping the board counterclockwise.
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar also", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
