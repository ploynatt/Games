package uk.ac.nulondon;

import java.awt.Color;
import doodlepad.Pad;
import doodlepad.Rectangle;
import doodlepad.Text;

public class BattleShipV2 extends Pad{
    private Tiles[][] b;
    private static final int DIM=400;

    public BattleShipV2() {
        super(DIM,DIM);

        int width=DIM/10;
        b= new Tiles[10][10];
        for(int i=0;i<10;i++)
            for(int k=0; k<10; k++)
                b[i][k]=new Tiles(i*width,k*width,width,this);

        int val1 = (int)(Math.random()*(b.length-2));
        int val2 = (int)(Math.random()*(b.length));

        if(Math.random()<.5)
            for(int i=val1; i<val1+3;i++)
                b[i][val2].setAsShip();
        else
            for(int i=val1; i<val1+3;i++)
                b[val2][i].setAsShip();


    }
    public void gameOver(String msg) {
        Text t = new Text("You "+msg+ " !!!",10,200);
        t.setFillColor(Color.BLUE);
        t.setFontSize(45);
        this.setEventsEnabled(false);
    }

    public static void main(String[] args) {
        BattleShipV2 b = new BattleShipV2();
    }

}
