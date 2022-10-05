import processing.core.PApplet;

import processing.data.Table;
import processing.data.TableRow;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Cube extends PApplet {

    float x;
    float y;
    float x2;
    float y2;

    public Cube(float x, float y, float x2, float y2, float width){
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void draw() {
        Main.processing.beginShape();
        Main.processing.vertex(x, y);
        Main.processing.vertex(x2, y2);
        //vertex(x, y);
        //vertex(x2, y2);
        Main.processing.endShape();
    }
}
