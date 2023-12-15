package byow.Core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Random;

import static byow.Core.Room.deepCopyWorld;

public class Engine {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int WIDTHK = 60;
    public static final int HEIGHTK = 60;
    static TETile[][] originalWorld = new TETile[WIDTH][HEIGHT];
    static long systemSeed;
    static Player player;
    static TETile[][] world = new TETile[WIDTH][HEIGHT];
    static boolean gameOver = false;
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        Engine engine = new Engine();

        KeyBoard board = new KeyBoard(WIDTHK, HEIGHTK, engine, ter);
        board.Express();
        board.UserInput();

        player.UserInput(player, world);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        world = worldInitialize();
        input = input.toLowerCase();
        if (input.charAt(0) == 'n') {
            int indexOfS = input.indexOf('s');
            if (indexOfS != -1) {
                String seedStr = input.substring(1, indexOfS);
                long seed = Long.parseLong(seedStr);
                systemSeed = seed;
                Random random = new Random(seed);
                Room worldRandom = new Room(random);


                this.world = worldRandom.worldGenerator(random, world);
                originalWorld = deepCopyWorld(world);
                ter.renderFrame(originalWorld);
                int xPosition = playerStart(world)[0];
                int yPosition = playerStart(world)[1];
                this.player = new Player(xPosition, yPosition, Tileset.AVATAR, ter);

                world[xPosition][yPosition] = player.getAvatarTile();


                if (indexOfS != input.length() - 1) {
                    String remainingInput = input.substring(indexOfS + 1);
                    player.SystemInput(player, world, remainingInput);
                }
                ter.renderFrame(world);
                //player.UserInput(player, world);
            }
        }
        if (input.charAt(0) == 'l') {
            SaveLoad.load(input);
            return world;
        }
        return world;

    }
    public static TETile[][] worldInitialize() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }
    public static Integer[] playerStart(TETile[][] worldPlayer) {
        Random random = new Random(systemSeed);
        while (true) {
            int valueX = random.nextInt(WIDTH);
            int valueY = random.nextInt(HEIGHT);
            if (worldPlayer[valueX][valueY].equals(Tileset.FLOOR)) {
                Integer[] position = {valueX, valueY};
                return position;
            }
        }
    }
    public static void drawFrame(TETile[][] worldFrame, int second) {
        world = worldFrame;
        if (!gameOver) {
            Mouse.classifyMouse(world, second);
        }
        StdDraw.show();
    }

    public static void drawTime(int second) {

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time string
        String dateTimeString = now.format(FORMATTER);

        // Draw the date and time string on the HUD
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.textRight(Engine.WIDTH - 1, Engine.HEIGHT - 1, dateTimeString);
        StdDraw.show();

        StdDraw.pause(second); // pause for a short time to avoid excessive updates

        //try {
        //    Thread.sleep(1000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
    }

    public static void main(String[] args) {

        System.out.println("World content starts here:");
        Engine engine = new Engine();

        engine.interactWithKeyboard();
        // engine.interactWithInputString("n2245553sasdwsaddsasd:q");
    }

}
