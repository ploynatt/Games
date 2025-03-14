package uk.ac.nulondon;

import doodlepad.Text;


import doodlepad.Text;

public class Twenty48 extends Board {
    private Tile2048[][] tiles = new Tile2048[4][4];

    public Twenty48() {
        super(4, 4);
        this.addAnother();
        this.addAnother();
    }

    public Tile2048[][] getTiles() {
        return this.tiles;
    }

    public boolean moveRight() {
        boolean valid = false;

        for(int r = 0; r < 4; ++r) {
            for(int c = 2; c >= 0; --c) {
                if (this.tiles[r][c] != null) {
                    int col;
                    for(col = c + 1; col < 4 && this.tiles[r][col] == null; ++col) {
                    }

                    if (col != 4 && this.tiles[r][col].getValue() == this.tiles[r][c].getValue()) {
                        this.merge(r, c, r, col);
                        valid = true;
                    } else if (this.tiles[r][col - 1] == null) {
                        this.move(r, c, r, col - 1);
                        valid = true;
                    }
                }
            }
        }

        return valid;
    }

    public void move(int oRow, int oCol, int nRow, int nCol) {
        this.tiles[nRow][nCol] = this.tiles[oRow][oCol];
        int x = this.getPosX(nCol);
        int y = this.getPosY(nRow);
        this.tiles[nRow][nCol].setLocation((double)x, (double)y);
        this.tiles[oRow][oCol] = null;
    }

    public void merge(int r1, int c1, int r2, int c2) {
        this.getPosX(c2);
        this.getPosY(r2);
        this.tiles[r2][c2].doubleValue();
        Tile2048.decreaseTiles();
        this.removeShape(this.tiles[r1][c1]);
        this.tiles[r1][c1] = null;
    }

    public boolean moveUp() {
        boolean valid = false;

        for(int c = 0; c < 4; ++c) {
            for(int r = 1; r < 4; ++r) {
                if (this.tiles[r][c] != null) {
                    int row;
                    for(row = r - 1; row > -1 && this.tiles[row][c] == null; --row) {
                    }

                    if (row != -1 && this.tiles[row][c].getValue() == this.tiles[r][c].getValue()) {
                        this.merge(r, c, row, c);
                        valid = true;
                    } else if (this.tiles[row + 1][c] == null) {
                        this.move(r, c, row + 1, c);
                        valid = true;
                    }
                }
            }
        }

        return valid;
    }

    public void onKeyPressed(String keyText, String e) {
        boolean valid = false;
        if (Tile2048.getNumTiles() == 16 && this.noTurns()) {
            this.setEventsEnabled(false);
            new Text("GAME OVER", (double)15.0F, (double)80.0F, 80);
        } else {
            if (keyText.equalsIgnoreCase("→")) {
                valid = this.moveRight();
            }

            if (keyText.equalsIgnoreCase("←")) {
                valid = Twenty48Helper.moveLeft(this);
            }

            if (keyText.equalsIgnoreCase("↑")) {
                valid = this.moveUp();
            }

            if (keyText.equalsIgnoreCase("↓")) {
                valid = Twenty48Helper.moveDown(this);
            }

            if (valid) {
                this.addAnother();
            }

        }
    }

    public boolean noTurns() {
        for(int r = 0; r < 3; ++r) {
            for(int c = 0; c < 4; ++c) {
                if (this.tiles[r][c].getValue() == this.tiles[r + 1][c].getValue()) {
                    return false;
                }
            }
        }

        for(int c = 0; c < 3; ++c) {
            for(int r = 0; r < 4; ++r) {
                if (this.tiles[r][c].getValue() == this.tiles[r][c + 1].getValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void addAnother() {
        int c = (int)(Math.random() * (double)4.0F);

        int r;
        for(r = (int)(Math.random() * (double)4.0F); this.tiles[r][c] != null; r = (int)(Math.random() * (double)4.0F)) {
            c = (int)(Math.random() * (double)4.0F);
        }

        int x = this.getPosX(c);
        int y = this.getPosY(r);
        int size = this.getPieceSize();
        this.tiles[r][c] = new Tile2048(x, y, size);
    }

    public static void main(String[] args) {
        new Twenty48();
    }
}
