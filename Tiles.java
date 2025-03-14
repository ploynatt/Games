package uk.ac.nulondon;

import java.awt.Color;
import doodlepad.*;

public class Tiles extends Rectangle {

    private BattleShipV2 game;
    private static int countHit,turns;
    private boolean shipPart;


    public Tiles(double x, double y,double r, BattleShipV2 t) {
        super(x,y,r,r);
        game=t;

    }
    public void setAsShip() {
        shipPart=true;
    }


    public void onMouseClicked(double x, double y, int b) {
        turns++;
        if(shipPart){
            setText("H");
            this.setEventsEnabled(false);
            setFillColor(Color.RED);
            countHit++;
            if(countHit==3) game.gameOver("won");
            return;
        }
        else {
            setEventsEnabled(false);
            if(Math.random()<.3) {
                setFillColor(Color.BLACK);
                game.gameOver("hit a BOMB");
                return;
            }
            setText("M");
            setFillColor(Color.BLUE);
        }
        if(turns==7)
            game.gameOver("lost");

    }
}
