# gravi-2048

This is the second assignment from Prof. Madden's CS441 class at Binghamton University.

gravi-2048 plays the same as the popular game "2048," but you can't swipe in any of the cardinal directions. Instead, you can only rotate and flip the 4x4 grid that the blocks are contained in, causing everything to fall down!

Clicking the leftmost button will rotate the board 90 degrees clockwise. The middle button will cause the board to be mirror-flipped vertically. The rightmost button will roate the board 90 degrees counterclockwise. After each action, any blocks that would be floating in the air fall down as if you swiped downward in a regular 2048 game - the only way to move blocks is with gravity. New blocks that spawn will be white and unaffected by gravity until you click another button; they have a 90% chance of being a '2' and a 10% chance of being a '4'.

The game only ends if you lose - that is, there's no space for another block to spawn after a move. When this happens, the game will reset.

Now that you understand how it works, check this out for a short video demo: https://www.youtube.com/watch?v=NnH5-V1trD4

Fun facts about the app:

    - The entire game's UI was made with only green hues. However, if you get very far, you'll see some special colors; 
    after you get past a 2048 block, the blocks' colors get wild. This was originally due to a mistake with how I handled
    colors in the blocks, but it looks cool, so I changed it slightly and kept it in.
    - The only images (ImageView objects) I use in this game are for the buttons and background. The blocks themselves
    are all naturally generated GridView objects; the color that their background shows is programmatically decided by
    the number in the box. The higher the number, the less green hue is applied to the background.
    - The green "background"/green lines between blocks are actually just a big green square image. I placed that
    ImageView behind the GridView to make it look like a legit grid. Also, I know the margins are slightly off, and it
    annoys me too.
    - I originally wanted to do this because I wanted to ruin the classic 2048 strategy of keeping the largest block in 
    one of the corners. However, it's actually even easier to do that in my game than in original 2048, because you can
    just alternate rotations and never flip. Oops. I'm a programmer, not a game designer.
