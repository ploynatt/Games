package uk.ac.nulondon;

import doodlepad.Oval;
import doodlepad.Pad;

public class MancalaBoard extends Pad {
    private Oval[] spots;
    private int[] seeds;
    private boolean computerTurn;

    public MancalaBoard() {
        super(800, 250);
        this.setEventsEnabled(true);
        this.setUpBoard();
    }

    public void setUpBoard() {
        int x = 150;
        int y = 150;
        this.spots = new Oval[14];
        this.seeds = new int[14];
        this.spots[13] = new Oval();
        this.spots[13].setCenter((double)50.0F, (double)100.0F);
        this.spots[13].setHeight((double)125.0F);
        this.spots[13].setText("0");
        this.spots[6] = new Oval();
        this.spots[6].setCenter((double)750.0F, (double)100.0F);
        this.spots[6].setHeight((double)125.0F);
        this.spots[6].setText("0");

        for(int i = 0; i < 6; ++i) {
            this.seeds[i] = 4;
            this.spots[i] = new Oval();
            this.spots[i].setCenter((double)x, (double)y);
            x += 100;
            this.spots[i].setText("4");
            this.spots[i].setEventsEnabled(false);
        }

        for(int i = 7; i < 13; ++i) {
            x -= 100;
            this.seeds[i] = 4;
            this.spots[i] = new Oval();
            this.spots[i].setCenter((double)x, (double)(y - 100));
            this.spots[i].setText("4");
        }

    }

    public void capture(int end) {
        --end;
        int home = 6;
        if (this.computerTurn) {
            home = 13;
        }

        if (end != home) {
            if (this.seeds[end] == 1 && (this.computerTurn && end > 6 || !this.computerTurn && end < 6)) {
                int[] var10000 = this.seeds;
                var10000[home] += this.seeds[end] + this.seeds[12 - end];
                this.seeds[end] = 0;
                this.seeds[12 - end] = 0;
            }

        }
    }

    public void takeTurn(int index) {
        int skip = 13;
        if (this.computerTurn) {
            skip = 6;
        }

        int s = this.seeds[index];
        this.seeds[index] = 0;
        ++index;

        for(int i = 0; i < s; ++i) {
            if (index == 14) {
                index = 0;
            }

            if (index != skip) {
                int var10002 = this.seeds[index]++;
            } else {
                --i;
            }

            ++index;
        }

        this.capture(index);

        for(int i = 0; i < 14; ++i) {
            this.spots[i].setText("" + this.seeds[i]);
        }

        if (index - 1 != 6 && index - 1 != 13) {
            this.computerTurn = !this.computerTurn;
        }

        if (!this.gameOver()) {
            if (this.computerTurn) {
                this.goComputer();
            }

        }
    }

    public boolean gameOver() {
        if (this.computerTurn) {
            for(int i = 7; i < 13; ++i) {
                if (this.seeds[i] != 0) {
                    return false;
                }
            }
        } else {
            for(int i = 0; i < 6; ++i) {
                if (this.seeds[i] != 0) {
                    return false;
                }
            }
        }

        for(int i = 0; i < 6; ++i) {
            int[] var10000 = this.seeds;
            var10000[6] += this.seeds[i];
            this.spots[i].setText("0");
        }

        for(int i = 7; i < 13; ++i) {
            int[] var5 = this.seeds;
            var5[13] += this.seeds[i];
            this.spots[i].setText("0");
        }

        this.setEventsEnabled(false);
        if (this.seeds[13] > this.seeds[6]) {
            this.spots[6].setText("Loser");
            this.spots[13].setText("" + this.seeds[13]);
        } else if (this.seeds[13] < this.seeds[6]) {
            this.spots[13].setText("Loser");
            this.spots[6].setText("" + this.seeds[6]);
        } else {
            this.spots[6].setText("Tie");
            this.spots[13].setText("Game");
        }

        return true;
    }

    public int chooseSmart() {
        for(int i = 12; i > 6; --i) {
            if (this.seeds[i] + i == 13) {
                return i;
            }
        }

        return -1;
    }

    public void goComputer() {
        int x;
        for(x = this.chooseSmart(); x == -1 || this.seeds[x] == 0; x = (int)(Math.random() * (double)6.0F + (double)7.0F)) {
        }

        System.out.println(x);
        this.takeTurn(x);
    }

    public void onMouseClicked(double x, double y, int button) {
        if (!this.computerTurn) {
            int index = (int)x / 100 - 1;
            this.takeTurn(index);
        }

    }

    public static void main(String[] args) {
        new MancalaBoard();
    }
}
