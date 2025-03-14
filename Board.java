package uk.ac.nulondon;

import doodlepad.Line;
import doodlepad.Pad;

import java.io.FileNotFoundException;

public class Board extends Pad {
    private static final int LINE_WIDTH = 8;
    private final int WIDTH;
    private final int HEIGHT;
    private static final int COL_WIDTH = 108;
    private int across;
    private int down;

    public Board(int x, int y) {
        super(x * 108, y * 108);
        this.WIDTH = x * 108;
        this.HEIGHT = y * 108;
        this.across = x;
        this.down = y;
        this.drawLines();
    }

    public int getColWidth() {
        return 108;
    }

    public int getPieceSize() {
        return 100;
    }

    private void drawLines() {
        int d = 104;

        for(int i = d; i < this.across * d; i += 108) {
            Line vLine = new Line((double)i, (double)0.0F, (double)i, (double)this.HEIGHT);
            vLine.setStrokeWidth((double)8.0F);
        }

        for(int i = d; i < this.down * d; i += 108) {
            Line hLine = new Line((double)0.0F, (double)i, (double)this.WIDTH, (double)i);
            hLine.setStrokeWidth((double)8.0F);
        }

    }

    public int getPosX(int c) {
        return c * 108;
    }

    public int getPosY(int r) {
        return r * 108;
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Board(3, 3);
    }
}
