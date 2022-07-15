package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> terms = new ArrayList<>();
        ArrayList<String> definitions = new ArrayList<>();

        System.out.println("Input the number of cards: ");
        int n = scanner.nextInt();
        String o = scanner.nextLine();
        String term = "";
        String definition = "";

        for (int i = 1; i < n + 1; i++) {
            System.out.println("Card #" + i + ":");
            term = scanner.nextLine();
            terms.add(term);
            System.out.println("The definition for card #" + i + ":");
            definition = scanner.nextLine();
            definitions.add(definition);
        }
        for(int i = 0; i < terms.size(); i++) {
            System.out.println("Print the definition of " + "\"" + terms.get(i) + "\":");
            String answer = scanner.nextLine();
            if (answer.equals(definitions.get(i))) {
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong. The right answer is \"" + definitions.get(i) + "\"");
            }
        }
    }
}
