package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import static byow.Core.Engine.*;
import static byow.Core.Room.*;
import static byow.Core.SaveLoad.resetOut;

public class Player {
    private static int x;
    private static int y;
    private TETile avatarTile;
    private static TERenderer ter;


    public Player(int x, int y, TETile avatarTile,TERenderer ter) {
        this.x = x;
        this.y = y;
        this.avatarTile = avatarTile;
        this.ter = ter;
    }

    // Getter和Setter方法
    public static int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public static int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public TETile getAvatarTile() {
        return avatarTile;
    }

    public static void UserInput(Player avatar, TETile[][] world) {
        boolean colonPressed = false;
        boolean exitLoop = false;
        while (!exitLoop) {
            drawFrame(world, 10);
            drawTime(10);
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                System.out.println("your input"+key);
                if (key == 'w' || key == 'W') {
                    moveAvatar(avatar, world, 0, 1);
                    colonPressed = false;
                } else if (key == 'a' || key == 'A') {
                    moveAvatar(avatar, world, -1, 0);
                    colonPressed = false;
                } else if (key == 's' || key == 'S') {
                    moveAvatar(avatar, world, 0, -1);
                    colonPressed = false;
                } else if (key == 'd' || key == 'D') {
                    moveAvatar(avatar, world, 1, 0);
                    colonPressed = false;
                }if (key == 'x' || key == 'X') {
                    exitLoop = true;
                } else if (key == 't' || key == 'T') {      // trun on off lights
                    auto(world);
                    colonPressed = false;

//                else if (key == 'q' || key == 'Q') {
//                    System.out.println("Program is terminating...");
//                    System.exit(0); // terminate program with exit status code 0

                } else if (key == ':') {
                    colonPressed = true;
                } else if ((key == 'q' || key == 'Q') && colonPressed) {
                    SaveLoad.save();
                    System.out.println("Program is terminating...");
                    System.exit(0);
                    // terminate program with exit status code 0
                } else {
                    colonPressed = false;
                }

                ter.renderFrame(world);// update the render
            }

        }
    }

    /**
     * source ChatGPT
     **/
    public static void moveAvatar(Player avatar, TETile[][] world, int dx, int dy) {
        int newX = avatar.getX() + dx;
        int newY = avatar.getY() + dy;

        // check if the new position is available
        if (world[newX][newY] != Tileset.WALL) {
            if(!light){
                world[avatar.getX()][avatar.getY()] = originalWorld[avatar.getX()][avatar.getY()];
            } ; // remove the old avatar
            if(light){
                world[avatar.getX()][avatar.getY()] = renderworld[avatar.getX()][avatar.getY()];
            } ; // remove the old avatar

            avatar.setX(newX);
            avatar.setY(newY);
            world[newX][newY] = avatar.getAvatarTile();// put avatar on the new position
        }
    }
    public static void SystemInput(Player avatar, TETile[][] world,String keys) {
        boolean colonPressed = false;
        for(int i = 0; i<keys.length(); i++){
            char key = keys.charAt(i);
            if (key == 'w' || key == 'W') {
                moveAvatar(avatar, world, 0, 1);
                colonPressed = false;
            } else if (key == 'a' || key == 'A') {
                moveAvatar(avatar, world, -1, 0);
                colonPressed = false;
            } else if (key == 's' || key == 'S') {
                moveAvatar(avatar, world, 0, -1);
                colonPressed = false;
            } else if (key == 'd' || key == 'D') {
                moveAvatar(avatar, world, 1, 0);
                colonPressed = false;
            } else if (key == ':') {
                colonPressed = true;
            } else if ((key == 'q' || key == 'Q') && (colonPressed == false)) {
                colonPressed = false;
                System.out.println("Program is terminating...");
                break;
            } else if ((key == 'q' || key == 'Q') && colonPressed) {
//                colonPressed = false;
                resetOut();
                SaveLoad.save();
                System.out.println("Program is terminating...");
                break;
//                System.exit(0); // terminate program with exit status code 0
            } else {
                colonPressed = false;
            }
        }
    }





}