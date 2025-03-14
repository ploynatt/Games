package uk.ac.nulondon;

import java.awt.Color;

public class Tile2048 extends Tile{
    private static int numTiles;

    public Tile2048(int x, int y, int s) {
        super(x,y,s);
        int value=0;
        if(numTiles< 7) {
            if((int)(Math.random()*10)<7) {
                value=2;
            } else {
                value= 4;
            }
        }else {
            if((int)(Math.random()*10)<3) {
                value= 2;
            }else {
                value =4;
            }
        }
        setValue(value);

        int r= (value-2)*20;
        int g=0;
        int b=255-r;
        setFillColor(new Color(r,g,b));
        setText(String.valueOf(value));
        numTiles++;
    }
    public void doubleValue() {
        int v= getValue()*2;
        setValue(v);
        int r= (v-2)*20;
        int g=0;
        int b=255-r;
        r = Math.min(Math.max(r, 0), 255);
        b = Math.min(Math.max(b, 0), 255);
        setFillColor(new Color(r,g,b));
        setText(String.valueOf(v));
    }
    public static int getNumTiles() { return numTiles;}
    public static void decreaseTiles() { numTiles--;}
}
