package uk.ac.nulondon;

public class Twenty48Helper {
    public Twenty48Helper() {
    }

    public static boolean moveDown(Twenty48 t) {
        Tile2048[][] tiles = t.getTiles();
        boolean valid = false;

        for(int c = 0; c < 4; ++c) {
            for(int r = 2; r >= 0; --r) {
                if (tiles[r][c] != null) {
                    int row;
                    for(row = r + 1; row < 4 && tiles[row][c] == null; ++row) {
                    }

                    if (row != 4 && tiles[row][c].getValue() == tiles[r][c].getValue()) {
                        t.merge(r, c, row, c);
                        valid = true;
                    } else if (tiles[row - 1][c] == null) {
                        t.move(r, c, row - 1, c);
                        valid = true;
                    }
                }
            }
        }

        return valid;
    }

    public static boolean moveLeft(Twenty48 t) {
        Tile2048[][] tiles = t.getTiles();
        boolean valid = false;

        for(int r = 0; r < 4; ++r) {
            for(int c = 1; c < 4; ++c) {
                if (tiles[r][c] != null) {
                    int col;
                    for(col = c - 1; col > -1 && tiles[r][col] == null; --col) {
                    }

                    if (col != -1 && tiles[r][col].getValue() == tiles[r][c].getValue()) {
                        t.merge(r, c, r, col);
                        valid = true;
                    } else if (tiles[r][col + 1] == null) {
                        t.move(r, c, r, col + 1);
                        valid = true;
                    }
                }
            }
        }

        return valid;
    }
}
