package byow.Core;
import java.awt.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.time.format.DateTimeFormatter;

public class Mouse {
    static Point point = MouseInfo.getPointerInfo().getLocation();
    private TETile[][] world;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Mouse(TETile[][] world, Point mousePoint) {
        this.point = mousePoint;
        this.world = world;
    }
    public static void classifyMouse(TETile[][] world, int second){

        int gridX = (int) StdDraw.mouseX();
        int gridY = (int) StdDraw.mouseY();

        if (gridY >= Engine.HEIGHT){
//                System.out.println("yoverbound:"+gridY);
            gridY = Engine.HEIGHT-1;
        }

        // Check the tile at the mouse pointer's position and display its classification
        TETile tile = world[gridX][gridY];

        // clear the previous file
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.0, Engine.HEIGHT - 0.5, Engine.WIDTH, 1);

//
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setPenColor(StdDraw.WHITE);
//        StdDraw.textLeft(0.0, Engine.HEIGHT - 1, gridX + "  " + gridY);
//

//            StdDraw.enableDoubleBuffering();
//            StdDraw.setPenColor(StdDraw.WHITE);
//            StdDraw.textLeft(0.0, Engine.HEIGHT - 1, "Round: " + gridX + "  " + gridY);
//
        if (tile.equals(Tileset.WALL)){
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(0.0, Engine.HEIGHT - 1, "Tile: Wall");

        }
        if (tile.equals(Tileset.NOTHING)){
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(0.0, Engine.HEIGHT - 1, "Tile: Nothing");
        }
        if (tile.equals(Tileset.FLOOR)){
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(0.0, Engine.HEIGHT - 1, "Tile: Floor");

        }
        if (tile.description()=="BrightFloor"){
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(0.0, Engine.HEIGHT - 1, "Tile: Floor");
        }
        if (tile.equals(Tileset.LIGHT)){
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(0.0, Engine.HEIGHT - 1, "Tile: Light");
        }
        if (tile.equals(Tileset.AVATAR)){
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(0.0, Engine.HEIGHT - 1, "Tile: AVATAR");
        }

        StdDraw.show();

        // Pause for a short time to avoid excessive updates
        // Note: the duration of this pause is shorter than that of drawTime
        // so that classifyMouse is updated every 10 milliseconds
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        StdDraw.pause(second); // pause for a short time to avoid excessive updates
    }

//    }

}