package byow.Core;
import java.util.ArrayList;
import java.util.List;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;

import static byow.Core.Engine.gameOver;
import static byow.Core.Engine.world;
import static byow.Core.Player.moveAvatar;

public class KeyBoard {
    private static TERenderer ter;
    private Engine engine;
    private int width;
    private int height;
    public KeyBoard(int width, int height,Engine engine,TERenderer ter) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.engine = engine;
        this.ter = ter;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }
    /**
     * source ChatGPT
     **/
    public void Express(){
        String Title = "CS61B: THE GAME";
        String subtitle = "New Game (N)";
        String subtitle2 = "Lord Game (L)";
        String subtitle3 = "Quit (Q)";
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height / 2,Title);
        Font fontSmall = new Font("Monaco", Font.BOLD, 10);
        StdDraw.setFont(fontSmall);
        StdDraw.text(this.width / 2, this.height / 2-7,subtitle);
        StdDraw.text(this.width / 2, this.height / 2-8,subtitle2);
        StdDraw.text(this.width / 2, this.height / 2-9,subtitle3);
        StdDraw.show();
    }
    public void UserInput() {
        char userInput = waitForUserInput();
        if (userInput == 'N' || userInput == 'n') {
            reminder();
        }
        if (userInput == 'L' || userInput == 'l'){
//            System.out.println("1111123");
            SaveLoad.load();
        }
        if (userInput == 'Q' || userInput == 'q'){
            SaveLoad.save();
            System.out.println("Program is terminating...");
            System.exit(0); // terminate program with exit status code 0
        }
    }
    public TETile[][] reminder(){
        String inputString ="";
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height / 2, "please input seeds");
        StdDraw.show();
        while (true) {
            char input = waitForUserInput();

            if (input == 's' || input == 'S') {
                inputString ='n'+inputString;
                inputString+="s";
                world = engine.interactWithInputString(inputString);
                ter.renderFrame(world);
                System.out.println(TETile.toString(world));
                return world;
            }
            StdDraw.clear(Color.BLACK);
            StdDraw.text(this.width / 2, this.height / 2, "please input seeds");
            inputString +=input;
            StdDraw.setFont(fontBig);
            StdDraw.text(this.width / 2, this.height / 2-5, inputString);
            StdDraw.show();
        }
    }
    public char waitForUserInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char userInput = StdDraw.nextKeyTyped();
                return userInput;
            }
        }
    }



}



