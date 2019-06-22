import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
import java.util.concurrent.TimeUnit;
import java.lang.*;

public class PUCKMAN extends JFrame{
   private static final String UP="UP", DOWN="DOWN", LEFT="LEFT", RIGHT="RIGHT";
   private static final Color BACKGROUND=Color.black,
                              PUCK_COLOR=Color.yellow,
                              MAZE_COLOR=Color.blue;
   // each map is coded in which a 0 is an empty cell and a 1 is a wall.
   private static final int[][]
      ORIGINAL_PROTOTYPE={
       // 1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
         {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 }, // 1
         {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, // 2
         {0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, // 3
         {0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, // 4
         {0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, // 5
         {0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, // 6
         {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 }, // 7
         {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, // 8
         {1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 }, // 9
         {1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 }, // 10
         {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1 }, // 11
         {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1 }, // 12
         {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1 }, // 13
         {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1 }, // 14
         {1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 15
         {1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 16
         {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 }, // 17
         {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1 }, // 18
         {1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1 }, // 19
         {1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1 }, // 20
         {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 }, // 21
         {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 }, // 22
         {0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0 }, // 23
         {1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 24
         {1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 25
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 26
         {1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1 }, // 27
         {1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1 }, // 28
         {1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1 }, // 29
         {1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1 }, // 30
         {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 }, // 31
         {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 }  // 32
      },
      PACMAN_ARRAY={ // 28 x 31
       // 1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 1
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 2
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 3
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 4
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 5
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 6
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 7
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 8
         {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 }, // 9
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, // 10
         {0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 11
         {0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 12
         {0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 13
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, // 14
         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 15
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, // 16
         {0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 17
         {0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 18
         {0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 19
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, // 20
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 21
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 22
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 23
         {1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1 }, // 24
         {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 }, // 25
         {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 }, // 26
         {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 }, // 27
         {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 }, // 28
         {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 }, // 29
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 30
         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }  // 31
      },
      MS_PACMAN_1={ // 28 x 31
       // 1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28
         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 1
         {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 }, // 2
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 3
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 4
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 5
         {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 6
         {0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 7
         {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 8
         {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 }, // 9
         {1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, // 10
         {0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 11
         {0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 12
         {0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 13
         {0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, // 14
         {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 15
         {0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, // 16
         {1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 17
         {0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 18
         {1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // 19
         {0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, // 20
         {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 21
         {0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 22
         {1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 }, // 23
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1 }, // 24
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 }, // 25
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 }, // 26
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 }, // 27
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 }, // 28
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 }, // 29
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 30
         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }  // 31
      },
      TEST_ARRAY={
       // 1  2  3  4  5  6  7
         {1, 1, 1, 0, 1, 1, 1}, // 1
         {1, 0, 0, 0, 0, 0, 1}, // 2
         {1, 0, 1, 0, 1, 0, 1}, // 3
         {0, 0, 0, 0, 0, 0, 0}, // 4
         {1, 0, 1, 0, 1, 0, 1}, // 5
         {1, 0, 0, 0, 0, 0, 1}, // 6
         {1, 1, 1, 0, 1, 1, 1}  // 7
      },
      TEST_ARRAY_2={
       // 1  2  3  4  5  6  7
         {1, 0, 0, 0, 0, 0, 1}, // 1
         {0, 1, 0, 0, 0, 1, 0}, // 2
         {0, 0, 0, 1, 0, 0, 0}, // 3
         {0, 0, 1, 1, 1, 0, 0}, // 4
         {0, 0, 0, 1, 0, 0, 0}, // 5
         {0, 1, 0, 0, 0, 1, 0}, // 6
         {1, 0, 0, 0, 0, 0, 1}  // 7
      },
      GAME_ARRAY=PACMAN_ARRAY;
   
   private static final int ARRAY_WIDTH=GAME_ARRAY[0].length, 
                            ARRAY_HEIGHT=GAME_ARRAY.length, 
                            PIXEL_SIZE=18, //pixel size should be divisible by 6 for best visual results
                            PREF_WIDTH=ARRAY_WIDTH*PIXEL_SIZE+2*PIXEL_SIZE,
                            PREF_HEIGHT=ARRAY_HEIGHT*PIXEL_SIZE +2*PIXEL_SIZE+200;
   private static final Dimension PREF_SIZE=new Dimension(PREF_WIDTH,PREF_HEIGHT);
   private int x=15, y=24; //starting position
   private String direction=UP;
   
   public PUCKMAN(){
      this.setTitle("PUCKMAN");
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setSize(PREF_SIZE);
      this.setLocationRelativeTo(null);
      this.getContentPane().add(new GamePanel());
      this.setVisible(true);
   }
  
   ////MAIN////////MAIN////////MAIN////////MAIN////
   public static void main(String[]args){ new PUCKMAN(); }
   ////MAIN////////MAIN////////MAIN////////MAIN////
  
  
   public class GamePanel extends JPanel {
      boolean key_right,key_left,key_down,key_up;
      JLabel lbl1=new JLabel(),lbl2=new JLabel(),lbl3=new JLabel(),lbl4=new JLabel();
         
      public GamePanel() {
         this.setFocusable(true);
         addKeyListener(new GameInput());
         // these labels are for bug fixing
         this.add(lbl1);
         this.add(lbl2);
         this.add(lbl3);
         this.add(lbl4);
      }
     
      @Override
      public void paintComponent(Graphics g) {
      
         super.paintComponent(g);
         setBackground(BACKGROUND);
         for(int i=1;i<=ARRAY_WIDTH;i++){
            for(int j=1;j<=ARRAY_HEIGHT;j++){
               // draws where the user puck man is
               if(i==x&&j==y){
                  g.setColor(PUCK_COLOR); 
                  g.fillRect(PIXEL_SIZE*i+1,PIXEL_SIZE*j+1,PIXEL_SIZE-2,PIXEL_SIZE-2);
               }
               // if a pixel is a wall, draw a cool wall
               else if(GAME_ARRAY[j-1][i-1]==1) 
                  paintWall(g,GAME_ARRAY,j,i,j,i);
               // if a pixel is empty, draw a small square.
               else{
                  g.setColor(new Color(40,40,40));
                  g.fillRect(PIXEL_SIZE*i+5,PIXEL_SIZE*j+5,PIXEL_SIZE-10,PIXEL_SIZE-10);
               }
            }
         }
         
         // it was initially programmed so that the user
         // could not go backwards. it was working, but it
         // made it hard to bug fix, so i removed the feature.
         // might re-add it later.
         String temp=direction;
         
         // RIGHT
         if(key_right){
            direction=RIGHT;
            if(x==ARRAY_WIDTH) x=1; // if at the edge of the map, go to other side
            else if(GAME_ARRAY[y-1][x]!=1) x++;
            else{key_right=false;direction=temp;}
         } 
         // LEFT
         if(key_left){
            direction=LEFT;
            if(x==1) x=ARRAY_WIDTH; // if at the edge of the map, go to other side
            else if(GAME_ARRAY[y-1][x-2]!=1) x--;
            else{key_left=false;direction=temp;}
         } 
         // UP
         if(key_up){
            direction=UP;
            if(y==1) y=ARRAY_HEIGHT; // if at the edge of the map, go to other side
            else if(GAME_ARRAY[y-2][x-1]!=1) y--;
            else{key_up=false;direction=temp;}
         } 
         // DOWN
         if(key_down){
            direction=DOWN;
            if(y==ARRAY_HEIGHT) y=1; // if at the edge of the map, go to other side
            else if(GAME_ARRAY[y][x-1]!=1) y++;
            else{key_down=false;direction=temp;}
         } 
         // a delay. this is the bane of my existence
         try{TimeUnit.MILLISECONDS.sleep(400);} 
         catch(InterruptedException e){System.err.format("IOException: %s%n",e);}
         
         repaint();
      }
      
      private class GameInput implements KeyListener{
         public void keyTyped(KeyEvent e){}
         public void keyReleased(KeyEvent e){}
         public void keyPressed(KeyEvent e){
            // DOWN
            if(e.getKeyCode()==e.VK_DOWN){
               key_down=true;  key_up=false; key_right=false; key_left=false;
               if(!(y==ARRAY_HEIGHT || GAME_ARRAY[y][x-1]!=1)){key_down=false;}
            }
            // UP
            if(e.getKeyCode()==e.VK_UP){ 
               key_up=true;  key_down=false; key_right=false; key_left=false;
               if(!(y==1 || GAME_ARRAY[y-2][x-1]!=1)){key_up=false;}
            }
            // RIGHT
            if(e.getKeyCode()==e.VK_RIGHT){ 
               key_right=true;  key_left=false; key_up=false; key_down=false;
               if(!(x==ARRAY_WIDTH || GAME_ARRAY[y-1][x]!=1)){key_right=false;}
            }
            // LEFT
            if(e.getKeyCode()==e.VK_LEFT){ 
               key_left=true;  key_right=false; key_up=false; key_down=false;
               if(!(x==1||GAME_ARRAY[y-1][x-2]!=1)){key_left=false;}
            }
            // set the labels 
            lbl1.setText("RIGHT:"+Boolean.toString(key_right)+"  ");
            lbl2.setText("LEFT:"+Boolean.toString(key_left)+"  ");
            lbl3.setText("UP:"+Boolean.toString(key_up)+"  ");
            lbl4.setText("DOWN:"+Boolean.toString(key_down)+"  ");
         }
      }
      
      
      //METHOD paintWall DRAWNS THE MAZE GRAPHICS.
      public void paintWall(Graphics g,int[][]array,int x,int y,int xLoc,int yLoc){
         g.setColor(MAZE_COLOR);
         // calculate size of "pixels" based on 'pixel' size.
         int pixelSize=PIXEL_SIZE/6;
         
         // if not an edge piece
         if(x<array.length&&y<array[0].length && x>1&&y>1){
         
         // if all surrounding 'pixels' are walls
            if(array[x]  [y-1]==1 &&
               array[x-1][y]  ==1 &&
               array[x-2][y-1]==1 &&
               array[x-1][y-2]==1){
               // do nothing.. kind of a filler, in case i want to update the graphics
            }
            
            // if (exactly) =2= surrounding 'pixels' are walls (L-shape) ===> //   _____
            // if right and bottom 'pixels' are walls                         //  |  ___|
            else if(array[x]  [y-1]==1 &&                                     //  | |
                    array[x-1][y]  ==1 &&                                     //  |_|
                    array[x-2][y-1]==0 &&
                    array[x-1][y-2]==0){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize,   PIXEL_SIZE*xLoc+pixelSize, 
                              pixelSize,   pixelSize*5);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*2, PIXEL_SIZE*xLoc+pixelSize, 
                              pixelSize*4, pixelSize);
            }
            // if top and right 'pixels' are walls
            else if(array[x]  [y-1]==0 &&
                    array[x-1][y]  ==1 &&
                    array[x-2][y-1]==1 &&
                    array[x-1][y-2]==0){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize,   PIXEL_SIZE*xLoc,             
                              pixelSize,  pixelSize*5); 
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*2, PIXEL_SIZE*xLoc+pixelSize*4, 
                              pixelSize*4,pixelSize); 
            }
            // if left and top 'pixels' are walls
            else if(array[x]  [y-1]==0 &&
                    array[x-1][y]  ==0 &&
                    array[x-2][y-1]==1 &&
                    array[x-1][y-2]==1){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*4, PIXEL_SIZE*xLoc,             
                              pixelSize,   pixelSize*5); 
               g.fillRect(PIXEL_SIZE*yLoc,             PIXEL_SIZE*xLoc+pixelSize*4, 
                              pixelSize*4, pixelSize); 
            }
            // if bottom and left 'pixels' are walls
            else if(array[x]  [y-1]==1 &&
                    array[x-1][y]  ==0 &&
                    array[x-2][y-1]==0 &&
                    array[x-1][y-2]==1){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*4, PIXEL_SIZE*xLoc+pixelSize, 
                              pixelSize,   pixelSize*5); 
               g.fillRect(PIXEL_SIZE*yLoc,             PIXEL_SIZE*xLoc+pixelSize, 
                              pixelSize*4, pixelSize); 
            }
            
            // if (at least) =2= surrounding 'pixels' are walls (line-shape) ===> //   _____
            // if top and bottom 'pixels' are walls                               //  |_____|
            else if(array[x]  [y-1]==1 && 
                    array[x-2][y-1]==1){ 
               if(array[x-1][y-2]==0)
                  g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc, 
                                 pixelSize, pixelSize*6); 
               if(array[x-1][y]  ==0)
                  g.fillRect(PIXEL_SIZE*yLoc+pixelSize*4, PIXEL_SIZE*xLoc, 
                                 pixelSize, pixelSize*6); 
            }
            // if left and right 'pixels' are walls
            else if(array[x-1][y]  ==1 &&
                    array[x-1][y-2]==1){
               if(array[x]  [y-1]==0)
                  g.fillRect(PIXEL_SIZE*yLoc, PIXEL_SIZE*xLoc+pixelSize*4, 
                                 pixelSize*6, pixelSize); 
               if(array[x-2][y-1]==0)
                  g.fillRect(PIXEL_SIZE*yLoc, PIXEL_SIZE*xLoc+pixelSize, 
                                 pixelSize*6, pixelSize); 
            }
            // if =1= surrounding 'pixel' is a wall ===> //   ___
            // if right 'pixel' is a wall                //  |___
            else if(array[x-1][y]==1){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc+pixelSize,   
                              pixelSize*5, pixelSize);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc+pixelSize*4, 
                              pixelSize*5, pixelSize);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc+pixelSize*2, 
                              pixelSize, pixelSize*2);
            }
            // if left 'pixel' is a wall
            else if(array[x-1][y-2]==1){
               g.fillRect(PIXEL_SIZE*yLoc,             PIXEL_SIZE*xLoc+pixelSize,   
                              pixelSize*5, pixelSize);
               g.fillRect(PIXEL_SIZE*yLoc,             PIXEL_SIZE*xLoc+pixelSize*4, 
                              pixelSize*5, pixelSize);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*4, PIXEL_SIZE*xLoc+pixelSize*2, 
                              pixelSize, pixelSize*2);
            }
            // if bottom 'pixel' is a wall
            else if(array[x][y-1]==1){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc+pixelSize,   
                              pixelSize*4, pixelSize);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc+pixelSize*2, 
                              pixelSize, pixelSize*4);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*4, PIXEL_SIZE*xLoc+pixelSize*2, 
                              pixelSize, pixelSize*4);
            }
            // if top 'pixel' is a wall
            else if(array[x-2][y-1]==1){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc+pixelSize*4,   
                              pixelSize*4, pixelSize);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc, 
                              pixelSize, pixelSize*4);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*4, PIXEL_SIZE*xLoc, 
                              pixelSize, pixelSize*4);
            }
            
            // draw a weird edge thing. kinda hard to describe. like, interior edge thing.
            // it takes care of the three-edge pieces and the empty pieces
            // IMAGINE THE PIXEL SETUP: // [1, 2, 3]
                                        // [4, X, 5] <==LOOKING AT 'X' 'PIXEL'
                                        // [6, 7, 8]
            // if 8 is empty and 5, 7 are walls
            if(array[x]  [y]==0 &&
               array[x-1][y]==1 &&
               array[x][y-1]==1){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*4, PIXEL_SIZE*xLoc+pixelSize*4, 
                              pixelSize, pixelSize*2);
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*5, PIXEL_SIZE*xLoc+pixelSize*4, 
                              pixelSize, pixelSize);
            }
            // if 6 is empty and 7, 4 are walls
            if(array[x]  [y-2]==0 &&
               array[x-1][y-2]==1 &&
               array[x]  [y-1]==1){
               
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize, PIXEL_SIZE*xLoc+pixelSize*4, 
                              pixelSize, pixelSize*2); 
               g.fillRect(PIXEL_SIZE*yLoc,           PIXEL_SIZE*xLoc+pixelSize*4, 
                              pixelSize, pixelSize);
            }
            // if 3 is empty and 2, 5 are walls
            if(array[x-2][y]  ==0 &&
               array[x-1][y]  ==1 &&
               array[x-2][y-1]==1){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*4, PIXEL_SIZE*xLoc,           
                              pixelSize, pixelSize*2); 
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize*5, PIXEL_SIZE*xLoc+pixelSize, 
                              pixelSize, pixelSize); 
            }
            // if 1 is empty and 4, 2 are walls
            if(array[x-2][y-2]==0&&
               array[x-2][y-1]==1 &&
               array[x-1][y-2]==1){
               g.fillRect(PIXEL_SIZE*yLoc+pixelSize,PIXEL_SIZE*xLoc,           
                              pixelSize, pixelSize*2); 
               g.fillRect(PIXEL_SIZE*yLoc, PIXEL_SIZE*xLoc+pixelSize, 
                              pixelSize, pixelSize); 
            }
            
         }
         // i did a weird thing where if its an edge pixel, 
         // it recreates the array around that one pixel and does it again.
         // i imagine there's a better way to do this
         else {
            int[][]temp=new int[3][3];
            temp[1][1]=1;
            // if a bottom edge
            if(x>=array.length){
               temp[2][0]=0; temp[2][1]=0; temp[2][2]=0;
            }
            else{
               temp[2][1]=array[x][y-1];
               // if not a right edge
               if(y<array[0].length){
                  temp[2][2]=array[x][y];
               }
               // if not a left edge
               if(y>1){
                  temp[2][0]=array[x][y-2];
               }
            }
            // if a top edge
            if(x<2){
               temp[0][0]=0; temp[0][1]=0; temp[0][2]=0;
            }
            else{
               temp[0][1]=array[x-2][y-1];
               // if not a right edge
               if(y<array[0].length){
                  temp[0][2]=array[x-2][y];
               }
               // if not a left edge
               if(y>1){
                  temp[0][0]=array[x-2][y-2];
               }
            }
            // if a right edge
            if(y>=array[0].length){
               temp[0][2]=0; temp[1][2]=0; temp[2][2]=0;
            }
            else{
               temp[1][2]=array[x-1][y];
               // if not a bottom edge 
               if(x<array.length){
                  temp[2][2]=array[x][y];
               }
               // if not a top edge 
               if(x>1){
                  temp[0][2]=array[x-2][y];
               }
            }
            // if a left edge
            if(y<2){
               temp[0][0]=0; temp[1][0]=0; temp[2][0]=0;
            }
            else{
               temp[1][0]=array[x-1][y-2];
               // if not a bottom edge 
               if(x<array[0].length){
                  temp[2][0]=array[x][y-2];
               }
               // if not a top edge 
               if(x>1){
                  temp[0][0]=array[x-2][y-2];
               }
            }
            paintWall(g,temp,2,2,xLoc,yLoc);
         }
      }
      
   }
}


