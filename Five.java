package uk.ac.nulondon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Five {
    private ArrayList<String> words = new ArrayList();

    public Five() throws FileNotFoundException {
        File inputFile = new File("five.txt");
        Scanner input = new Scanner(inputFile);

        while(input.hasNextLine()) {
            this.words.add(input.nextLine());
        }

    }

    public String pickWord() {
        int r = (int)(Math.random() * (double)this.words.size());
        return ((String)this.words.get(r)).toUpperCase();
    }

    public boolean isValid(String w) {
        return this.words.contains(w.toLowerCase());
    }
}
