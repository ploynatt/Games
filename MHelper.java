package uk.ac.nulondon;

import java.awt.Color;

public class MHelper {
    public MHelper() {
    }

    public static void sweep(int row, int col, MineSweeper board) {
        Cell[][] cells = board.getCells();

        for(int r = row - 1; r <= row + 1; ++r) {
            for(int c = col - 1; c <= col + 1; ++c) {
                if (board.inBounds(r, c) && !cells[r][c].getFillColor().equals(Color.WHITE)) {
                    cells[r][c].onMouseClicked((double)0.0F, (double)0.0F, 0);
                }
            }
        }

    }

    public static void setAdjacentCounts(int row, int col, MineSweeper board) {
        Cell[][] cells = board.getCells();

        for(int r = row - 1; r <= row + 1; ++r) {
            for(int c = col - 1; c <= col + 1; ++c) {
                if (board.inBounds(r, c) && !cells[r][c].isMine()) {
                    cells[r][c].incrementCount();
                }
            }
        }

    }
}
