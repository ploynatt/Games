package uk.ac.nulondon;

import doodlepad.Oval;
import java.awt.Color;

public class OHelper {
    public OHelper() {
    }

    public static void changeDN(int diff, int num, int col, int row, Color c, Oval[][] pieces) {
        for(int i = 0; i < num; ++i) {
            pieces[row + diff * i][col + diff * i].setFillColor(c);
        }

    }

    public static void changeDP(int diff, int num, int col, int row, Color c, Oval[][] pieces) {
        for(int i = 0; i < num; ++i) {
            pieces[row + diff * -i][col + diff * i].setFillColor(c);
        }

    }

    public static void diagonalN(int row, int col, Oval[][] pieces) {
        Color co = pieces[row][col].getFillColor();
        int c = col - 1;
        int r = row - 1;

        int WIDTH;
        for(WIDTH = pieces.length; c > -1 && r > -1 && pieces[r][c] != null && !pieces[r][c].getFillColor().equals(co); --r) {
            --c;
        }

        if (c != -1 && r != -1 && pieces[r][c] != null) {
            changeDN(-1, col - c, col, row, co, pieces);
        }

        c = col + 1;

        for(r = row + 1; c < WIDTH && r < WIDTH && pieces[r][c] != null && !pieces[r][c].getFillColor().equals(co); ++r) {
            ++c;
        }

        if (c != WIDTH && r != WIDTH && pieces[r][c] != null) {
            changeDN(1, c - col, col, row, co, pieces);
        }

    }

    public static void diagonalP(int row, int col, Oval[][] pieces) {
        Color co = pieces[row][col].getFillColor();
        int c = col - 1;
        int r = row + 1;

        int WIDTH;
        for(WIDTH = pieces.length; c > -1 && r < WIDTH && pieces[r][c] != null && !pieces[r][c].getFillColor().equals(co); ++r) {
            --c;
        }

        if (c != -1 && r != WIDTH && pieces[r][c] != null) {
            changeDP(-1, col - c, col, row, co, pieces);
        }

        c = col + 1;

        for(r = row - 1; c < WIDTH && r > -1 && pieces[r][c] != null && !pieces[r][c].getFillColor().equals(co); --r) {
            ++c;
        }

        if (c != WIDTH && r != -1 && pieces[r][c] != null) {
            changeDP(1, c - col, col, row, co, pieces);
        }

    }

    public static void diagonal(int row, int col, Oval[][] p) {
        diagonalN(row, col, p);
        diagonalP(row, col, p);
    }
}
