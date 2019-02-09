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
import java.util.concurrent.TimeUnit;

// I used this to learn how to use GridView and create adapters:
// https://www.youtube.com/watch?v=HbEHxkAEumU

public class GridViewAdapter extends BaseAdapter {
    // Holds the grid.
    private String[][] lst_source;
    // Unique Adapter context.
    private Context m_context;
    // Number of spaces that are free to spawn a block in.
    private int available_spaces = 16;
    // Random number generator.
    private Random rng = new Random();
    // The [i, j] tuple that keeps track of what block was
    // just added. (-1, -1) if none. I use this because I want
    // the most recent block to be colored white for contrast.
    private int[] most_recent = {-1, -1};
    // Did I win?
    private boolean winner = false;

    private static final int NUM_ELEMENTS = 16;

    GridViewAdapter(String[][] lst_source, Context m_context) {
        this.lst_source = lst_source;
        this.m_context = m_context;
    }

    // The count is always 16. This is the constant total block number
    // used by the Adapter interface, NOT the number of non-empty blocks.
    // Use available_spaces for the total number of empty blocks.
    @Override
    public int getCount() {
        return NUM_ELEMENTS;
    }

    /* Positioning system:
        1  2  3  4
        5  6  7  8
        9  10 11 12
        13 14 15 16
     */
    @Override
    public Object getItem(int position) {
        return lst_source[position/4][position%4];
    }

    // Thanks to the above system, we don't need to change the way
    // that the getItemId function works - just give what you have.
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
            button.setTextSize(20);
            if (lst_source[position/4][position%4].equals("-")) {
                // Empty spaces are black.
                button.setBackgroundColor(Color.BLACK);
                button.setTextColor(Color.GREEN);
            } else if (lst_source[position/4][position%4].equals("2048")) {
                // Winning spaces (2048) are purely green.
                button.setBackgroundColor(Color.GREEN);
                button.setTextColor(Color.BLACK);
                winner = true;
            } else if (position/4 == most_recent[0] && position%4 == most_recent[1]) {
                // Most recently added '2' blocks are white for contrast.
                button.setBackgroundColor(Color.WHITE);
                button.setTextColor(Color.BLACK);
                most_recent[0] = -1;
                most_recent[1] = -1;
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
            // Not really sure why this is here. I assume it's to reuse a view, but
            // even when I update the lst_source after it's been created, it satisfies
            // the "if" block. Shouldn't it always get here on an update? Ask about this.
            button = (Button) convert_view;
        }

        return button;
    }

    // Check this after each move. If it returns true, exit the game
    // with a victory message for the user - it stops at 2048.
    boolean isWinner() {
        return winner;
    }

    // Package-private method that generates a new number
    // to a random available space. Ends the game if impossible
    // if it's impossible, return false - otherwise return true.
    // There is about a 1/10 chance that it'll be a 4 instead of a 2.
    boolean generateBlock() {
        if (available_spaces == 0) {
            // There's no space left, the player loses.
            return false;
        } else {
            // The (index_to_use)th free space will be used, starting at 0.
            int index_to_use = Math.abs(rng.nextInt() % available_spaces);

            for (int i = 0; i < 4; i++) {
                int j;
                for (j = 0; j < 4; j++) {
                    if (index_to_use == 0 && lst_source[i][j].equals("-")) {
                        // If we've arrived at our index and the space is free, take it.
                        // 1 in 10 chance that it will be a 4. Otherwise, it'll be a 2.
                        if (rng.nextInt() % 10 == 0) {
                            lst_source[i][j] = "4";
                        } else {
                            lst_source[i][j] = "2";
                        }
                        most_recent[0] = i;
                        most_recent[1] = j;
                        available_spaces--;
                        break;
                    } else if (lst_source[i][j].equals("-")) {
                        // If we see a free space, decrement our index.
                        index_to_use--;
                    } else {
                        // Otherwise, do nothing.
                    }
                }

                // This indicates that we just did a "break."
                if (j < 4) {
                    break;
                }
            }
        }

        // Successfully placed a block, keep going.
        return true;
    }

    // Button 1 - rotate clockwise 90 degrees.
    void rotateC() {
        String[][] new_grid = new String[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                new_grid[i][j] = lst_source[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                lst_source[j][3-i] = new_grid[i][j];
            }
        }
    }

    // Button 2 - rotate counterclockwise 90 degrees.
    void rotateCC() {
        String[][] new_grid = new String[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                new_grid[i][j] = lst_source[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                lst_source[3-j][i] = new_grid[i][j];
            }
        }
    }

    // Button 3 - flip the box vertically.
    // (Note: this is NOT a 180 degree rotation
    // in any direction, it's a mirror-type flip.
    void flip() {
        String[][] new_grid = new String[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                new_grid[i][j] = lst_source[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                lst_source[3-i][j] = new_grid[i][j];
            }
        }
    }

    // After each button click, let the blocks fall down.
    // Return the score gained by this move.
    int fallDown() {
        int score = 0;

        for (int j = 0; j < 4; j++) {
            int bottom = 3;
            for (int i = 3; i >= 0; i--) {
                if (!lst_source[i][j].equals("-")) {
                    String cur = lst_source[i][j];
                    lst_source[i][j] = "-";
                    lst_source[bottom][j] = cur;
                    bottom--;
                }
            }

            // No need to sleep and update if there are no blocks.
            if (bottom == 3) {
                Log.d("BOTTOM", String.valueOf(j));
                continue;
            }

            // Keep track of whether we've actually changed anything.
            boolean unchanged = true;
            // If we combine blocks together, everything above the combined blocks
            // needs to be shifted down by one, regardless of whether they end up
            // getting combined themselves. So, each time blocks combine, increment this.
            int offset = 0;
            for (int i = 3; i >= 0; i--) {
                if (lst_source[i][j].equals("-")) {
                    // We're done - no numbers can exist above an empty space
                    // since we just dropped everything down, so exit the loop.
                    break;
                } else if (i != 0 && lst_source[i][j].equals(lst_source[i-1][j])) {
                    // Erase both blocks to combine, and then replace the bottom
                    // one with twice its original value to simulate combination.
                    String cur = lst_source[i][j];
                    lst_source[i][j] = "-";
                    lst_source[i-1][j] = "-";
                    lst_source[i+offset][j] = String.valueOf(2*Integer.parseInt(cur));
                    // Offset has increased by one.
                    offset++;
                    // Skip the new blank space.
                    i--;
                    // We changed something, so we have to sleep & update (see below).
                    unchanged = false;
                    // Add the value of the new block to the score.
                    score += 2*Integer.parseInt(cur);
                    // Also, a new space is free.
                    available_spaces++;
                } else {
                    // Does nothing if there's no offset, but if there is, it
                    // moves down as much as the offset says we need to.
                    String cur = lst_source[i][j];
                    lst_source[i][j] = "-";
                    lst_source[i+offset][j] = cur;
                }
            }

            // No need to sleep and update if nothing's changed.
            if (unchanged) {
                Log.d("UNCHANGED", String.valueOf(j));
                continue;
            }
        }

        return score;
    }
}
