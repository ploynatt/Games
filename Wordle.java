package uk.ac.nulondon;

import doodlepad.Rectangle;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordle extends Board {
    private int currentCol = 0;
    private int currentRow = 0;
    private String word = this.pickWord();
    private String[] letters = new String[5];
    private ArrayList<String> list;

    public Wordle() throws FileNotFoundException {
        super(5, 6);
        System.out.println(this.word);
    }

    public String pickWord() throws FileNotFoundException {
        this.list = new ArrayList();
        File inputFile = new File("five.txt");
        Scanner input = new Scanner(inputFile);

        while(input.hasNextLine()) {
            this.list.add(input.nextLine().toUpperCase());
        }

        int r = (int)(Math.random() * (double)this.list.size());
        return (String)this.list.get(r);
    }

    public boolean isValid(String w) {
        return this.list.contains(w.toUpperCase());
    }

    public boolean keyChecker(String str) {
        if (str.equals("Backspace") && this.currentCol != 0) {
            return true;
        } else if (str.equals("Enter") && this.isRowFull()) {
            return true;
        } else {
            String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            return alpha.indexOf(str) != -1 && !this.isRowFull();
        }
    }

    public boolean isRowFull() {
        return this.currentCol == 5;
    }

    public void gameOver(String message) {
        this.setEventsEnabled(false);
        this.letters = message.split("");

        for(int i = 0; i < 5; ++i) {
            this.colorCodeBox(i, Color.RED);
        }

    }

    public void colorCodeBox(int col, Color c) {
        int size = this.getPieceSize();
        Rectangle rect = new Rectangle((double)this.getPosX(col), (double)this.getPosY(this.currentRow), (double)size, (double)size);
        rect.setFillColor(c);
        rect.setText(this.letters[col]);
        rect.setFontSize(size);
    }

    public void backspace() {
        this.letters[this.currentCol] = "";
        this.colorCodeBox(this.currentCol, Color.WHITE);
        this.letters[this.currentCol] = null;
    }

    public ArrayList<String> exactMatch() {
        ArrayList<String> noMatch = new ArrayList();

        for(int i = 0; i < 5; ++i) {
            String s = this.word.substring(i, i + 1);
            if (s.equals(this.letters[i])) {
                this.colorCodeBox(i, Color.GREEN);
                this.letters[i] = null;
            } else {
                noMatch.add(s);
            }
        }

        return noMatch;
    }

    public void letterChecker() {
        ArrayList<String> notMatched = this.exactMatch();
        if (notMatched.size() == 0) {
            this.gameOver("GREAT");
        } else {
            for(int c = 0; c < 5; ++c) {
                if (notMatched.contains(this.letters[c])) {
                    this.colorCodeBox(c, Color.YELLOW);
                    notMatched.remove(this.letters[c]);
                } else if (this.letters[c] != null) {
                    this.colorCodeBox(c, Color.GRAY);
                }
            }

            if (this.currentRow == 5) {
                this.gameOver(this.word);
            }

        }
    }

    public boolean isWord() {
        String s = "";

        String[] var5;
        for(String let : var5 = this.letters) {
            s = s + let;
        }

        if (this.isValid(s)) {
            return true;
        } else {
            for(int i = 0; i < 5; ++i) {
                --this.currentCol;
                this.backspace();
            }

            return false;
        }
    }

    public void onKeyPressed(String keyText, String keyModifiers) {
        System.out.println(keyText);
        if (this.keyChecker(keyText)) {
            if (keyText.equals("Enter")) {
                if (this.isWord()) {
                    this.letterChecker();
                    ++this.currentRow;
                    this.currentCol = 0;
                }
            } else if (keyText.equals("Backspace")) {
                --this.currentCol;
                this.backspace();
            } else {
                this.letters[this.currentCol] = keyText;
                this.colorCodeBox(this.currentCol, Color.WHITE);
                ++this.currentCol;
            }

        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Wordle();
    }
}
