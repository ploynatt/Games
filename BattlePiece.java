package uk.ac.nulondon;

import doodlepad.Rectangle;
import doodlepad.Text;
import java.awt.Color;

public class BattlePiece extends Rectangle {
    private boolean ship;
    private boolean bomb;
    private static int hits;
    private static int turns;

    public BattlePiece(int x, int y, int size) {
        super((double)x, (double)y, (double)size, (double)size);
        this.setFillColor(Color.WHITE);
        this.setEventsEnabled(true);
    }

    public void setShip() {
        this.ship = true;
    }

    public void setBomb() {
        this.bomb = true;
    }

    public boolean isShip() {
        return this.ship;
    }

    public boolean isBomb() {
        return this.bomb;
    }

    public void onMouseClicked(double x, double y, int button) {
        if (turns < 10) {
            ++turns;
            if (this.isShip()) {
                this.setFillColor(Color.BLUE);
                ++hits;
                if (hits == 3) {
                    new Text("YOU SUNK MY SHIP!!", (double)40.0F, (double)53.0F, 40);
                    turns = 11;
                }
            } else if (this.isBomb()) {
                this.setFillColor(Color.RED);
                new Text("KABOOM!!", (double)53.0F, (double)50.0F, 80);
                turns = 11;
            } else {
                this.setFillColor(Color.YELLOW);
            }

            this.setEventsEnabled(false);
            if (turns == 10) {
                new Text("You lost!", (double)53.0F, (double)54.0F, 80);
            }

        }
    }
}
