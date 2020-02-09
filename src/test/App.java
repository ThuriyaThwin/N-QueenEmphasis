package test;

import java.util.Scanner;

public class App {
    static Scanner in = new Scanner(System.in);

    public static void main(String args[]) {
        System.out.println("Enter number of Queens");
        int queens = in.nextInt();

        NQueen nQueen = new NQueen(queens);
        nQueen.solveNqueen();
    }
}
