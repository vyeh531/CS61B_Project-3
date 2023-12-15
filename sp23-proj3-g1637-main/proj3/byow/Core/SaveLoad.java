package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.In;

import java.io.FileWriter;
import java.util.Random;

import static byow.Core.Engine.*;
import static byow.Core.Room.*;


public class SaveLoad {
    static TERenderer ter = new TERenderer();
    private static String SAVE_FILE_NAME = "save.txt";
    static Out out;
    /**
     * source ChatGPT
    **/
    public static void save() {

        // Writing to a file
//
//        FileWriter fileout = new FileWriter("save.txt");
//
//        out = new Out(SAVE_FILE_NAME);
        out = new Out(SAVE_FILE_NAME);
        out.println(systemSeed);
        out.println(Player.getX());
        out.println(Player.getY());
        out.println(light);
        System.out.println("Program is Saved");
        out.close();
    }

    public static void load() {
        // Reading from a file
        if (!SAVE_FILE_NAME.isEmpty()){
            In in = new In(SAVE_FILE_NAME);
            if(in.hasNextLine()){
                systemSeed = Long.parseLong(in.readLine());
                int player_x = Integer.parseInt(in.readLine());
                int player_y = Integer.parseInt(in.readLine());
                boolean lig = Boolean.parseBoolean(in.readLine());
                Random random = new Random(systemSeed);
                TETile[][] newLoadWorldFrame = worldInitialize();
                world = Room.worldGenerator(random, newLoadWorldFrame);
                originalWorld =deepCopyWorld(world);
                if(lig){
                    light = true;
                    world=deepCopyWorld(renderworld);
                }
                player = new Player(player_x,player_y, Tileset.AVATAR,ter);
                world[player_x][player_y] = Tileset.AVATAR;
                ter.renderFrame(world);
            }
        }
        System.out.println("Program is Loading");
    }
    public static void load(String moves) {
        // Reading from a file
        if (!SAVE_FILE_NAME.isEmpty()){
            In in = new In(SAVE_FILE_NAME);
            if (in.hasNextLine()){
                systemSeed = Long.parseLong(in.readLine());
                int player_x = Integer.parseInt(in.readLine());
                int player_y = Integer.parseInt(in.readLine());
                Random random = new Random(systemSeed);
                TETile[][] newLoadWorldFrame = worldInitialize();
                world = Room.worldGenerator(random, newLoadWorldFrame);
                player = new Player(player_x,player_y, Tileset.AVATAR,ter);
                Player saved_player = new Player(player_x,player_y, Tileset.AVATAR,ter);
                originalWorld =deepCopyWorld(world);
                world[player_x][player_y] = Tileset.AVATAR;
                String StartOfX = moves.substring(1);
                saved_player.SystemInput(player, world, StartOfX);
                ter.renderFrame(world);
            }
        }
        System.out.println("Program is Loading");
    }
    public static void resetOut() {
        out = new Out(SAVE_FILE_NAME);
    }
}