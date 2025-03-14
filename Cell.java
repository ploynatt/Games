package uk.ac.nulondon;

import java.awt.Color;

import doodlepad.Image;
import doodlepad.Oval;
import doodlepad.Rectangle;
import doodlepad.Sound;
import doodlepad.Text;


public class Cell extends Rectangle  {
    private int row, col, adjMines;
    private MineSweeper board;
    private boolean isMine;
    private static int safe;
    private Text a;

    public Cell(int w, int r, int c, MineSweeper b) {
        super(c*w, r*w, w, w );
        row= r;
        col=c;
        board=b;
        isMine=false;
        setFillColor(Color.BLUE);
        setStrokeColor(Color.BLACK);
    }

    public void setMine() {
        isMine=true;
    }

    public boolean isMine() {
        return isMine;
    }

    public void incrementCount() {
        adjMines++;
    }

    public void rightClick() {
        if(getFillColor().equals(Color.BLUE)) {
            setFillColor(Color.ORANGE);
            a= new Text("F", getX()+getWidth()/2,getY()+getWidth()/2);
        } else if(getFillColor().equals(Color.ORANGE)) {
            board.removeShape(a);
            setFillColor(Color.BLUE);

        }

    }

    public void padClick() {
        if(isMine) {
            board.gameOver(true);
        }else {
            if(getFillColor().equals(Color.BLUE)) {
                safe++;
                setFillColor(Color.WHITE);
            }
            if(safe == board.getNumSafeCells()) {
                board.gameOver(false);
            }else if(adjMines==0) {
                MHelper.sweep(row, col, board);
            }else {
                Text t= new Text(""+adjMines,getX()+getWidth()/2,getY()+getWidth()/2);
            }
        }
    }
    public void onMouseClicked(double x, double y, int b) {
        if (b==3) {
            rightClick();
        } else {
            padClick();
        }
    }

}
