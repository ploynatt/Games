package uk.ac.nulondon;

import doodlepad.Pad;
import doodlepad.Text;
import java.awt.Color;

public class Piano extends Pad {
    private Key[][] keys = new Key[4][4];
    private int score;
    private final int MAX = 4;
    private static final int PAD_WIDTH = 400;
    private static final int PAD_HEIGHT = 650;

    public Piano() {
        super(400, 650);
        this.createBoard();
    }

    public void createBoard() {
        int keyWidth = 100;
        int keyHeight = 162;

        for(int row = 0; row < this.keys.length; ++row) {
            int randCol = (int)(Math.random() * (double)this.keys[row].length);

            for(int col = 0; col < this.keys[row].length; ++col) {
                if (col != randCol) {
                    this.keys[row][col] = new Key((double)(col * keyWidth), (double)(row * keyHeight), true, keyWidth, keyHeight);
                } else {
                    this.keys[row][col] = new Key((double)(col * keyWidth), (double)(row * keyHeight), false, keyWidth, keyHeight);
                }
            }
        }

    }

    public void onKeyPressed(String keyText, String e) {
        String VALID_CHOICES = "DFJK";
        int c = VALID_CHOICES.indexOf(keyText);
        if (c == -1) {
            System.out.println("Not a valid key!!!!!!");
        } else if (this.gameOver(c)) {
            new Text("Piano isn't for you :( Score: " + this.score, (double)10.0F, (double)610.0F, 25);
            this.setEventsEnabled(false);
        } else {
            ++this.score;
            this.moveTiles(c);
        }

    }

    public void moveTiles(int c) {
        this.keys[3][c].setFillColor(Color.WHITE);

        for(int i = 3; i > 0; --i) {
            int black = this.findBlack(this.keys[i - 1]);
            this.keys[i][black].setFillColor(Color.BLACK);
            this.keys[i - 1][black].setFillColor(Color.WHITE);
        }

        this.keys[0][(int)(Math.random() * (double)this.keys[0].length)].setFillColor(Color.BLACK);
    }

    public int findBlack(Key[] a) {
        for(int col = 0; col < a.length; ++col) {
            if (a[col].getFillColor().equals(Color.BLACK)) {
                return col;
            }
        }

        return -1;
    }

    public boolean gameOver(int c) {
        if (this.keys[3][c].getFillColor().equals(Color.WHITE)) {
            this.keys[3][c].setFillColor(Color.RED);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        new Piano();
    }
}
