package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.*;

import static byow.Core.Engine.*;
import static java.lang.Math.*;

public class Room {
    private final Random RANDOM;
    static boolean light = false;
    private int counter;
    public static TETile[][] renderworld = new TETile[WIDTH][HEIGHT];
    public static TreeMap<Integer,int[]> record;

    public Room(Random random) {
        this.RANDOM = random;
        this.counter = 0;
        this.record = new TreeMap<>();
    }
    public static TETile[][] worldGenerator(Random RANDOM, TETile[][] world) {
        Room room = new Room(RANDOM);

        int numRooms = RANDOM.nextInt(5) + 5; // form number from 1 to 20 rooms

        for (int i = 0; i < numRooms; i++) {
            room.addRoom(world,300);
        }

        room.addWalls(world); // add the boundery for the rooms
        room.connectRoom(room.record,world);
        room.ensureWallsAroundFloors(world);
        room.printRecord();
        room.addLightToAllRooms(world,record);
        renderworld = deepCopyWorld(world);
        room.TurnOffAllLight(world);
        return world;
    }

    /**
     * source ChatGPT
     **/
    public boolean addRoom(TETile[][] tiles, int maxAttempts) {
        if (maxAttempts <= 0) {
            return false;
        }

        int w = RANDOM.nextInt(4) + 4; // modify the width of room
        int h = RANDOM.nextInt(4) + 4; // modify the height of room
        int x = RANDOM.nextInt(tiles.length - w - 1) + 1;
        int y = RANDOM.nextInt(tiles[0].length - h - 1) + 1;

        if ((!hasRoom(x, y, tiles)) && (isWall(x, y, tiles)) && (!isOverlapping(x - 1, y - 1, w + 2, h + 2, tiles))) {

            int number = getCounter();
            incrementCounter();
            recordPosition(number, x, y, w, h, this.record);
            for (int i = 0; i < w; i++) {
                for (int b = 0; b < h; b++) {
                    tiles[x + i][y + b] = randomTile(2);
                }
            }
            return true;
        } else {
            return addRoom(tiles, maxAttempts - 1);
        }
    }


    private TETile randomTile(Integer n) {
        return switch (n) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.FLOWER;
            case 2 -> Tileset.FLOOR;
            default -> Tileset.NOTHING;
        };
    }

    private boolean hasRoom(int x, int y, TETile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j].equals(Tileset.FLOOR) && i == x && j == y) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addWalls(TETile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j].equals(Tileset.FLOOR)) {
                    if (isWall(i, j, tiles)) {
                        tiles[i][j] = Tileset.WALL;
                    }
                }
            }
        }
    }
    private boolean isWall(int x, int y, TETile[][] tiles) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] d : directions) {
            int dx = x + d[0];
            int dy = y + d[1];
            if (dx < 0 || dx >= tiles.length || dy < 0 || dy >= tiles[0].length) {
                return true;
            }
            if (tiles[dx][dy].equals(Tileset.NOTHING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * source ChatGPT
     **/
    private boolean isOverlapping(int x, int y, int w, int h, TETile[][] tiles) {
        if (x < 0 || y < 0 || x + w >= tiles.length || y + h >= tiles[0].length) {
            return true;
        }
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                if (tiles[i][j].equals(Tileset.FLOOR) || tiles[i][j].equals(Tileset.WALL)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * source ChatGPT
     **/
    public void connectRoom(TreeMap<Integer, int[]> info, TETile[][] tiles) {
        int length = info.size();
        HashSet<Integer> connectedRooms = new HashSet<>();
        for (int i = 0; i < length - 1; i++) {
            if (!connectedRooms.contains(i)) {
                int closestRoom = -1;
                double minDistance = Double.MAX_VALUE;

                for (int j = 0; j < length; j++) {
                    if (i != j && !connectedRooms.contains(j)) {
                        double distance = Math.sqrt(Math.pow(info.get(i)[0] - info.get(j)[0], 2) + Math.pow(info.get(i)[1] - info.get(j)[1], 2));
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestRoom = j;
                        }
                    }
                }

                int valueOfx = RANDOM.nextInt(info.get(i)[0] + 1, info.get(i)[0] + info.get(i)[2] - 1);
                int valueOfy = RANDOM.nextInt(info.get(i)[1] + 1, info.get(i)[1] + info.get(i)[3] - 1);
                int objectOfx = RANDOM.nextInt(info.get(closestRoom)[0] + 1, info.get(closestRoom)[0] + info.get(closestRoom)[2] - 1);
                int objectOfy = RANDOM.nextInt(info.get(closestRoom)[1] + 1, info.get(closestRoom)[1] + info.get(closestRoom)[3] - 1);

                addHallway(valueOfx, valueOfy, objectOfx, objectOfy, tiles);
                connectedRooms.add(i);
            }
        }
    }

    /**
     * source ChatGPT
     **/
    public void addHallwayHelper(int position, int x, int y, TETile[][] tiles) {
        // to left
        if (position == -1) {
            if (!tiles[x - 1][y].equals(Tileset.FLOOR)) {
                tiles[x - 1][y] = Tileset.FLOOR;
            }
            if (!tiles[x - 1][y+1].equals(Tileset.FLOOR)) {
                tiles[x - 1][y + 1] = Tileset.WALL;
            }
            if (!tiles[x - 1][y - 1].equals(Tileset.FLOOR)) {
                tiles[x - 1][y - 1] = Tileset.WALL;
            }

        }
        // to the bottom
        else if (position == 0) {
            if (!tiles[x][y - 1].equals(Tileset.FLOOR)) {
                tiles[x][y - 1] = Tileset.FLOOR;
            }
            if (!tiles[x - 1][y - 1].equals(Tileset.FLOOR)) {
                tiles[x - 1][y - 1] = Tileset.WALL;
            }
            if (!tiles[x + 1][y - 1].equals(Tileset.FLOOR)) {
                tiles[x + 1][y - 1] = Tileset.WALL;
            }

        }
        // to top
        else if (position == 1) {
            if (!tiles[x][y + 1].equals(Tileset.FLOOR)) {
                tiles[x][y + 1] = Tileset.FLOOR;
            }
            if (!tiles[x - 1][y + 1].equals(Tileset.FLOOR)) {
                tiles[x - 1][y + 1] = Tileset.WALL;
            }
            if (!tiles[x + 1][y + 1].equals(Tileset.FLOOR)) {
                tiles[x + 1][y + 1] = Tileset.WALL;
            }

        }
        // to right
        else if (position == 2) {
            if (!tiles[x + 1][y].equals(Tileset.FLOOR)) {
                tiles[x + 1][y] = Tileset.FLOOR;
            }
            if (!tiles[x + 1][y + 1].equals(Tileset.FLOOR)) {
                tiles[x + 1][y + 1] = Tileset.WALL;
            }
            if (!tiles[x + 1][y - 1].equals(Tileset.FLOOR)) {
                tiles[x + 1][y - 1] = Tileset.WALL;
            }

        }
    }
    public void addHallway(int x, int y, int x1, int y1,TETile[][] tiles){
        int x_diff = x1-x;
        int y_diff = y1-y;
        int step = 0;
        if(x_diff>=0){
            for(int i =0;i<x_diff;i++){
                    addHallwayHelper(2, x + step, y, tiles);
                    step++;
            }
            step =0;
            if(y_diff>0){
                for(int i =0;i<y_diff;i++){
                    addHallwayHelper(1,x1,y+step,tiles);
                    step++;
                }
            }
            else if(y_diff<0){
                for(int i =0;i<-y_diff;i++){

                    addHallwayHelper(0,x1,y-step,tiles);
                    step++;
                }
            }
        }
        if(x_diff<0){
            for(int i =0;i<-x_diff;i++){
                addHallwayHelper(-1,x-step,y,tiles);
                step++;
            }
            step =0;
            if(y_diff>0){
                for(int i =0;i<y_diff;i++){
                    addHallwayHelper(1,x1,y+step,tiles);
                    step++;
                }
            }
            else if(y_diff<0){
                for(int i =0;i<-y_diff;i++){
                    addHallwayHelper(0,x1,y-step,tiles);
                    step++;
                }
            }
        }
    }

    /**
     * source ChatGPT
     **/
    public TreeMap<Integer, int[]> recordPosition(int number, int x, int y, int w, int h, TreeMap record) {
        int[] position = new int[] {x, y, w, h};
        record.put(number,position);
        return record;
    }
    public void addLight(TETile[][] world, int x, int y, int w, int h) {
        int valueOfx = RANDOM.nextInt(x + 1, x + w - 1);
        int valueOfy = RANDOM.nextInt(y + 1, y + h - 1);

        RenderLight(world,x,y,w,h,valueOfx,valueOfy);
        world[valueOfx][valueOfy] = Tileset.LIGHT;
    }
    private void incrementCounter() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
    public void printRecord() {
        for (Integer roomNumber : record.keySet()) {
            int[] roomInfo = record.get(roomNumber);
            System.out.println("Room_" + roomNumber + ": x = " + roomInfo[0] + ", y = " + roomInfo[1] + ", width = " + roomInfo[2] + ", height = " + roomInfo[3]);
        }
    }
    /**
     * source ChatGPT
     **/
    public void ensureWallsAroundFloors(TETile[][] world) {
        for (int x = 1; x < world.length - 1; x++) {
            for (int y = 1; y < world[0].length - 1; y++) {
                if (world[x][y].equals(Tileset.FLOOR)) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (dx == 0 && dy == 0) continue;

                            int newX = x + dx;
                            int newY = y + dy;

                            if (world[newX][newY].equals(Tileset.NOTHING)) {
                                world[newX][newY] = Tileset.WALL;
                            }
                        }
                    }
                }
            }
        }
    }
    public void addLightToAllRooms(TETile[][] world, TreeMap<Integer, int[]> record) {
        for (Map.Entry<Integer, int[]> entry : record.entrySet()) {
            int[] roomInfo = entry.getValue();
            int x = roomInfo[0];
            int y = roomInfo[1];
            int w = roomInfo[2];
            int h = roomInfo[3];
            addLight(world, x, y, w, h);
        }

    }
    public void RenderLight(TETile[][] world, int x , int y, int w, int h,int light_x,int light_y){

        for(int i = 1; i < w-1; i++){
            for(int j = 1; j < h-1; j++){
                int distance = max(abs(x+i-light_x),abs(y+j-light_y));
                Color color = new Color(39, 200-20*distance, 230);
                world[x+i][y+j] = Tileset.setFloorColor(color);
            }
        }
    }
    /**
     * source ChatGPT
     **/
    static TETile[][] deepCopyWorld(TETile[][] world) {
        System.out.println("123123");
        TETile[][] originalWorld = new TETile[world.length][];
        for (int i = 0; i < world.length; i++) {
            originalWorld[i] = Arrays.copyOf(world[i], world[i].length);
        }
        return originalWorld;
    }
    public static void antiRenderLight(TETile[][] world, int x, int y, int w, int h){
        if(turnOnState(world,new int[]{x, y, w, h})) {// turn off the light
            light = false;
            for (int i = 1; i < w - 1; i++) {
                for (int j = 1; j < h - 1; j++) {
                    if(world[x+i][y+j].equals(Tileset.LIGHT)||world[x+i][y+j].equals(Tileset.AVATAR)) {
                        continue;
                    }
                    world[x + i][y + j] = Tileset.FLOOR;
                }
            }
        }
        else  { // turn on the light
            light = true;
            for (int i = 1; i < w - 1; i++) {
                for (int j = 1; j < h - 1; j++) {
                    if(world[x+i][y+j].equals(Tileset.LIGHT)||world[x+i][y+j].equals(Tileset.AVATAR)) {
                        System.out.println("light is " + light);
                        System.out.println("turn on");
                        continue;
                    }
                    world[x + i][y + j] = renderworld[x + i][y + j];
                }
            }
        }
    }

    public static boolean turnOnState(TETile[][] tiles, int[] roomInfo) {
        int dx = roomInfo[0] + 1;
        int dy = roomInfo[1] + 1;
        int dx2 = roomInfo[0] + 2;
        int dy2 = roomInfo[1] + 1;
        int dx3 = roomInfo[0] + 1;
        int dy3 = roomInfo[1] + 2;

        if (tiles[dx][dy].description() == "BrightFloor" || tiles[dx2][dy2].description() == "BrightFloor" || tiles[dx3][dy3].description() == "BrightFloor") {
            return true;
        }
        return false;
    }

    public static void auto(TETile[][] world){
        for (Map.Entry<Integer, int[]> entry : record.entrySet()) {
            int[] roomInfo = entry.getValue();
            int x = roomInfo[0];
            int y = roomInfo[1];
            int w = roomInfo[2];
            int h = roomInfo[3];
            antiRenderLight(world, x, y, w, h);
        }
    }
    public static TETile[][] TurnOffAllLight(TETile[][] world){
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                if(world[x][y].description() == "BrightFloor") {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
        return world;
    }
}









