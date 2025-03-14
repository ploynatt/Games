package uk.ac.nulondon;

public class BattleShip extends Board {
    private BattlePiece[][] pieces;

    public BattleShip() {
        super(5, 5);
        int size = this.getPieceSize();
        this.pieces = new BattlePiece[5][5];

        for(int i = 0; i < 5; ++i) {
            for(int k = 0; k < 5; ++k) {
                int x = this.getPosX(k);
                int y = this.getPosY(i);
                this.pieces[i][k] = new BattlePiece(x, y, size);
            }
        }

        this.placeShip();
        this.placeBomb();
        this.placeBomb();
    }

    public void placeShip() {
        this.pieces[0][0].setShip();
        this.pieces[1][0].setShip();
        this.pieces[2][0].setShip();
    }

    public void placeBomb() {
        this.pieces[2][2].setBomb();
    }

    public static void main(String[] args) {
        new BattleShip();
    }
}
