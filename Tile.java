package uk.ac.nulondon;

import doodlepad.Rectangle;

public class Tile extends Rectangle {
    private int value;

    public Tile(int x, int y, int s) {
        super((double)x, (double)y, (double)s, (double)s);
        this.setStrokeWidth((double)4.0F);
        this.setStrokeColor((double)0.0F, (double)0.0F, (double)0.0F);
        this.setFontSize(30);
    }

    public void setValue(int v) {
        this.value = v;
        this.setText("" + this.value);
    }

    public int getValue() {
        return this.value;
    }
}
