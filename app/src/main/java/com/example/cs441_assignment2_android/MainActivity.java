package com.example.cs441_assignment2_android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String lstSource[][] = new String[4][4];
    GridView gv;
    GridViewAdapter gva;
    ImageView iv;
    TextView game_over_text;
    TextView score;
    TextView best_score;

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

        game_over_text = findViewById(R.id.gom);
        score = findViewById(R.id.score);
        best_score = findViewById(R.id.best_score);

        // Generate the first random '2' block.
        gva.generateBlock();

        // Set the buttons' actions.
        FloatingActionButton fabC = findViewById(R.id.fabC);
        setOnClick(fabC, "C");

        FloatingActionButton fabCC = findViewById(R.id.fabCC);
        setOnClick(fabCC, "CC");

        FloatingActionButton fabF = findViewById(R.id.fabF);
        setOnClick(fabF, "F");
    }

    // This function wraps the setOnClickListener method so
    // that I can pick which adapter function I want to use
    // to alter the board.
    public void setOnClick(FloatingActionButton fab, final String option) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If it's not already black (a game over just occurred),
                // then set it back to black so it disappears.
                game_over_text.setTextColor(Color.BLACK);

                // Choose which way to flip/rotate based on what button was clicked.
                if (option.equals("C")) {
                    gva.rotateC();
                } else if (option.equals("CC")) {
                    gva.rotateCC();
                } else {
                    gva.flip();
                }

                // Update the view before allowing to fall down.
                gva.notifyDataSetChanged();
                gv.setAdapter(gva);

                // After rotating, let "gravity" pull everything down with "fallDown."
                // fallDown returns the score given by this move, which is added to the total.
                score.setText(String.valueOf(Integer.parseInt(String.valueOf(score.getText())) +
                        gva.fallDown()));

                // Generate the next 2. If it fails, then we quit because
                // the user has lost the game without getting to 2048.
                if (!gva.generateBlock()) {
                    // Alert the user that they suck and have lost the game.
                    game_over_text.setTextColor(Color.GREEN);

                    // Clear the board.
                    gva.gameOver();
                    // Start over.
                    gva.generateBlock();

                    // The amount of inner functions here is ridiculous,
                    // but I'm just changing the best score.
                    best_score.setText(String.valueOf(Math.max(
                            Integer.parseInt(String.valueOf(best_score.getText())),
                            Integer.parseInt(String.valueOf(score.getText())))));
                    score.setText("0");
                }
                gva.notifyDataSetChanged();
                gv.setAdapter(gva);
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
