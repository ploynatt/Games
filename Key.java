package uk.ac.nulondon;

import java.awt.Color;
import doodlepad.Pad;
import doodlepad.Rectangle;

public class Key extends Rectangle{
    public Key(double x, double y, boolean isWhite,int width, int height) {
        super(x,y, width, height);
        if(isWhite) {
            setFillColor(Color.white);
        } else {
            setFillColor(Color.black);
        }

        //complete this method
        //What information does this constructor need to pass
        //to its parent constructor?? Look at the documentation for
        //Rectangle in doodlePad.
        //REMEMBER: any method that a Rectangle has, a Key has too

    }
}
