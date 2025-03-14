package uk.ac.nulondon;

import doodlepad.Image;
import doodlepad.Pad;
import doodlepad.Sound;

public class MineSweeper extends Pad{
    private Cell[][] cells;
    private final int MINES;
    private static int PAD_WIDTH=576;

    public MineSweeper(int x) {
        super(PAD_WIDTH,PAD_WIDTH);

        if(x==1) {
            MINES=10;
            cells= new Cell[9][9];
            setUp();
        } else {
            MINES=35;
            cells= new Cell[16][16];
            setUp();
        }
    }

    private void fillArray() {
        int w= PAD_WIDTH/cells.length;
        for(int r=0; r<cells.length; r++) {
            for(int c=0; c< cells[0].length; c++) {
                cells[r][c]= new Cell(w, r, c, this);
            }
        }
    }

    private void placeMines() {
        int minesPlaced=0;
        while(minesPlaced<MINES) {
            int r= (int)(Math.random()*cells.length);
            int c= (int)(Math.random()*cells[0].length);
            if(!cells[r][c].isMine()) {
                cells[r][c].setMine();
                MHelper.setAdjacentCounts(r,c, this);
                minesPlaced++;
            }
        }
    }
    private void setUp() {
        fillArray();
        placeMines();
    }

    public Cell[][] getCells(){
        return cells;
    }

    public int getNumSafeCells() {
        int c=0;
        for(Cell[] a: cells) {
            for(Cell b:a) {
                if(!b.isMine()) {
                    c++;
                }
            }
        }
        return c;

    }

    public boolean inBounds(int r, int c) {
        return r>=0 && r<cells.length && c>=0 && c< cells[0].length;
    }

    public void gameOver(boolean lost) {
        if(lost) {
            Sound s = new Sound("gameover.wav");
            s.play();

            Image mine = new Image("bomb.png",0,0,PAD_WIDTH,PAD_WIDTH);
        }
        else {
            Sound s = new Sound("winner.wav");
            s.play();
        }
    }

    public static void main(String[] args) {
        MineSweeper m = new MineSweeper(1);  //where x is the user’s input
    }
}