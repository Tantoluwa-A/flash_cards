package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the number of cards: ");
        int n = scanner.nextInt();
        String o = scanner.nextLine();
        String term = "";
        String definition = "";
        String key = "";

        Map<String, String> cardsAndDefinitions = new LinkedHashMap<>();

        for (int i = 1; i < n + 1; i++) {
            System.out.println("Card #" + i + ":");
            term = scanner.nextLine();
            while (cardsAndDefinitions.containsKey(term)) {
                System.out.println("The term \""+ term +"\" already exists. Try again:");
                term = scanner.nextLine();
            }
            System.out.println("The definition for card #" + i + ":");
            definition = scanner.nextLine();
            while (cardsAndDefinitions.containsValue(definition)) {
                System.out.println("The definition \""+ definition +"\" already exists. Try again:");
                definition = scanner.nextLine();
            }
            cardsAndDefinitions.put(term, definition);
        }

        for (var entry: cardsAndDefinitions.entrySet()
             ) {
            System.out.println("Print the definition of " + "\"" + entry.getKey() + "\":");
            String answer = scanner.nextLine();
            if (answer.equals(entry.getValue())) {
                System.out.println("Correct!");
            } else if (cardsAndDefinitions.containsValue(answer)) {
                for (var answerEntry : cardsAndDefinitions.entrySet()
                     ) {
                    if (answer.equals(answerEntry.getValue())) {
                        key = answerEntry.getKey();
                        System.out.println("Wrong. The right answer is \"" + entry.getValue() + "\", but your definition is correct for \"" + answerEntry.getKey() + "\".");
                    }
                }
            }
            else {
                System.out.println("Wrong. The right answer is \"" + entry.getValue() + "\"");
            }
        }
    }
}
