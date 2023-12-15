package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;

import static byow.Core.Engine.HEIGHT;
import static byow.Core.Engine.WIDTH;
import static byow.Core.SaveLoad.resetOut;
import static byow.Core.SaveLoad.ter;
import static com.google.common.truth.Truth.assertThat;

public class test {
    @Test
    public void agTest() {
        Engine e1 = new Engine();
        boolean a =true;
        Engine e2 = new Engine();
        TETile[][] result = e1.interactWithInputString("n8391172972297503990swswswwawadas");
       e2.interactWithInputString("n8391172972297503990swswswwawa:q");
        TETile[][] result2 = e2.interactWithInputString("ldas");
        while(!a){
            ter.renderFrame(result2);
            System.out.println("1");


            StdDraw.pause(100);

            ter.renderFrame(result);
            System.out.println("2");
            StdDraw.pause(1000);
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if(result2[x][y]!=result[x][y]) {
////                        System.out.println("result"+result2[x][y].toString());
////                        System.out.println("result"+result[x][y].toString());
////                        System.out.println(result2[x][y]==result[x][y]);
//                        result[x][y] = Tileset.FLOWER;
                        System.out.println("111111111111");
                    }
                }
            }
        }
        assertThat(result2).isEqualTo(result);
    }

    @Test
    public void agTest1() {
        Engine e1 = new Engine();
        Engine e2 = new Engine();
        TETile[][] result = e1.interactWithInputString("n2838278388919144292ss");
        e2.interactWithInputString("n2838278388919144292ss:q");
        boolean a = true;
        TETile[][] result2 = e2.interactWithInputString("l");
        while(!a){
            ter.renderFrame(result2);
            System.out.println("1");


            StdDraw.pause(100);

//            ter.renderFrame(result);
            System.out.println("2");
            StdDraw.pause(1000);
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if(result2[x][y]!=result[x][y]) {
////                        System.out.println("result"+result2[x][y].toString());
////                        System.out.println("result"+result[x][y].toString());
////                        System.out.println(result2[x][y]==result[x][y]);
//                        result[x][y] = Tileset.FLOWER;
                        System.out.println("111111111111");
                    }
                }
                }
            }

        assertThat(result2).isEqualTo(result);
    }
}
