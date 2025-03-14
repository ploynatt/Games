package uk.ac.nulondon;

import doodlepad.Oval;
import doodlepad.Text;
import java.awt.Color;

public class Othello extends Board {
    private Oval[][] pieces = new Oval[6][6];
    private int num;
    private static final int WIDTH = 6;

    public Othello() {
        super(6, 6);
        getPad().setBackground((double)26.0F, (double)141.0F, (double)13.0F);
        int mid = 2;
        this.placePiece(mid, mid);
        this.placePiece(mid + 1, mid);
        this.placePiece(mid + 1, mid + 1);
        this.placePiece(mid, mid + 1);
    }

    public void onMouseClicked(double x, double y, int button) {
        int col = (int)x / this.getColWidth();
        int row = (int)y / this.getColWidth();
        this.placePiece(col, row);
    }

    public void placePiece(int col, int row) {
        if (this.pieces[row][col] == null) {
            this.pieces[row][col] = new Oval((double)(col * this.getColWidth()), (double)(row * this.getColWidth()), (double)this.getPieceSize(), (double)this.getPieceSize());
            ++this.num;
            if (this.num % 2 == 0) {
                this.pieces[row][col].setFillColor(Color.BLACK);
            } else {
                this.pieces[row][col].setFillColor(Color.WHITE);
            }

            if (this.num > 4) {
                this.flip(row, col);
            }

            if (this.num == 36) {
                this.gameOver();
            }

        }
    }

    public void gameOver() {
        this.setEventsEnabled(false);
        int count = 0;

        Oval[][] var6;
        for(Oval[] row : var6 = this.pieces) {
            for(Oval p : row) {
                if (p.getFillColor().equals(Color.BLACK)) {
                    ++count;
                }
            }
        }

        String winner;
        if ((double)count > (double)18.0F) {
            winner = "BLACK WON";
        } else if ((double)count == (double)18.0F) {
            winner = "TIE GAME";
        } else {
            winner = "WHITE WON";
        }

        Text t = new Text(winner, (double)10.0F, (double)200.0F);
        t.setFontSize(100);
        t.setFillColor(Color.RED);
    }

    public void changeR(int start, int end, int col, Color c) {
        for(int i = start; i < end; ++i) {
            this.pieces[i][col].setFillColor(c);
        }

    }

    public void changeC(int start, int end, int row, Color c) {
        for(int i = start; i < end; ++i) {
            this.pieces[row][i].setFillColor(c);
        }

    }

    public void vertical(int row, int col) {
        Color c = this.pieces[row][col].getFillColor();
        int r = row - 1;
        int start = row;

        int end;
        for(end = row; r > -1 && this.pieces[r][col] != null && !this.pieces[r][col].getFillColor().equals(c); --r) {
        }

        if (r != -1 && this.pieces[r][col] != null) {
            start = r;
        }

        for(r = row + 1; r < 6 && this.pieces[r][col] != null && !this.pieces[r][col].getFillColor().equals(c); ++r) {
        }

        if (r != 6 && this.pieces[r][col] != null) {
            end = r + 1;
        }

        this.changeR(start, end, col, c);
    }

    public void horizontal(int row, int col) {
        Color co = this.pieces[row][col].getFillColor();
        int c = col - 1;
        int start = col;

        int end;
        for(end = col; c > -1 && this.pieces[row][c] != null && !this.pieces[row][c].getFillColor().equals(co); --c) {
        }

        if (c != -1 && this.pieces[row][c] != null) {
            start = c;
        }

        for(c = col + 1; c < 6 && this.pieces[row][c] != null && !this.pieces[row][c].getFillColor().equals(co); ++c) {
        }

        if (c != 6 && this.pieces[row][c] != null) {
            end = c + 1;
        }

        this.changeC(start, end, row, co);
    }

    public void flip(int row, int col) {
        this.vertical(row, col);
        this.horizontal(row, col);
        OHelper.diagonal(row, col, this.pieces);
    }

    public static void main(String[] args) {
        new Othello();
    }
}
