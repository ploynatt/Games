package uk.ac.nulondon;

import java.util.ArrayList;
import java.util.Scanner;

public class MagicTrick {
    public MagicTrick() {
    }

    public static void display(int[][] cards) {
        for(int[] r : cards) {
            for(int c : r) {
                System.out.print(c + "\t");
            }

            System.out.println("\n");
        }

    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList();

        for(int i = 1; i < 17; ++i) {
            list.add(i);
        }

        int[][] cards = new int[4][4];

        for(int r = 0; r < 4; ++r) {
            for(int c = 0; c < 4; ++c) {
                cards[r][c] = (Integer)list.remove((int)(Math.random() * (double)list.size()));
            }
        }

        display(cards);
        System.out.println("\n\nPick a card.  What row is it in?");
        Scanner kb = new Scanner(System.in);
        int n = kb.nextInt();
        ArrayList<Integer> choice = new ArrayList();
        int[] var9;
        int var8 = (var9 = cards[n]).length;

        for(int c = 0; c < var8; ++c) {
            Integer x = var9[c];
            choice.add(x);
        }

        for(int i = 0; i < 4; ++i) {
            int temp = cards[n][i];
            cards[n][i] = cards[i][i];
            cards[i][i] = temp;
        }

        for(int r = 0; r < 4; ++r) {
            list = new ArrayList();

            for(int c = 0; c < 4; ++c) {
                list.add(cards[r][c]);
            }

            for(int c = 0; c < 4; ++c) {
                cards[r][c] = (Integer)list.remove((int)(Math.random() * (double)list.size()));
            }
        }

        display(cards);
        System.out.println("What row is your card in now?");
        int m = kb.nextInt();
        System.out.println("Your card is " + choice.get(m));
    }
}
