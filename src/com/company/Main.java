package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> cardsAndDefinitions = new LinkedHashMap<>();
        String term;
        String definition;
        String key = "";
        String response = "";
        String logs = "";
        int count = 0;
        int countAdd = 0;
        int sumOfLoadedCards = 0;
        while (!Objects.equals(response, "exit")) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats): ");
            logs += "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats): \n";
            response = scanner.nextLine();
            logs += response + "\n";
            switch (response) {
                case "add" -> {
                    System.out.println("The card:");
                    logs += "The card:\n";
                    term = scanner.nextLine();
                    logs += term + "\n";
                    if (cardsAndDefinitions.containsKey(term)) {
                        System.out.println("The card \"" + term + "\" already exists.");
                        logs += "The card \"" + term + "\" already exists.\n";
                        System.out.println();
                        logs += "\n";
                        continue;
                    }
                    System.out.println("The definition of the card:");
                    logs += "The definition of the card:\n";
                    definition = scanner.nextLine();
                    logs += definition + "\n";
                    if (cardsAndDefinitions.containsValue(definition)) {
                        System.out.println("The definition \"" + definition + "\" already exists.");
                        logs += "The definition \"" + definition + "\" already exists.\n";
                    } else {
                        cardsAndDefinitions.put(term, definition);
                        countAdd++;
                        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
                        logs += "The pair (\"" + term + "\":\"" + definition + "\") has been added.\n";
                    }
                    System.out.println();
                    logs += "\n";
                }
                case "remove" -> {
                    System.out.println("Which card?");
                    String cardToBeRemoved = scanner.nextLine();
                    if(cardsAndDefinitions.containsKey(cardToBeRemoved)) {
                        cardsAndDefinitions.remove(cardToBeRemoved);
                        sumOfLoadedCards--;
                        System.out.println("The card has been removed.");
                        System.out.println();
                    } else {
                        System.out.println("Can't remove \"" + cardToBeRemoved + "\": there is no such card.");
                        System.out.println();
                    }
                }
                case "import" -> {
                    System.out.println("File name:");
                    File file = new File(scanner.nextLine());
                    try (Scanner myReader = new Scanner(file)) {
                        count = 0;
                        while (myReader.hasNext()) {
                            String s = myReader.nextLine();
                            String[] pair = s.split(":");
                            cardsAndDefinitions.put((pair[0]), pair[1]);
                            sumOfLoadedCards++;
                            count++;
                        }
                        myReader.close();
                        System.out.println(count + " cards have been loaded.");
                        System.out.println();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                        System.out.println();
                    }
                }
                case "export" -> {
                    System.out.println("File name:");
                    File exportFile = new File(scanner.nextLine());
                    PrintWriter writer = new PrintWriter(exportFile);
                    cardsAndDefinitions.forEach((k, v) -> writer.println(k + ":" + v));
                    writer.close();
                    System.out.println(countAdd + sumOfLoadedCards + " cards have been saved.");
                    System.out.println();
                }
                case "ask" -> {
                    System.out.println("How many times to ask?");
                    int times = Integer.parseInt(scanner.nextLine());
                    List<String> keysAsArray = new ArrayList<>(cardsAndDefinitions.keySet());
                    Random r = new Random();
                    for (int i = 0; i < times; i++) {
                        String entry = keysAsArray.get(r.nextInt(keysAsArray.size()));
                        System.out.println("Print the definition of " + "\"" + entry + "\":");
                        String answer = scanner.nextLine();
                        if (answer.equals(cardsAndDefinitions.get(entry))) {
                            System.out.println("Correct!");
                        } else if (cardsAndDefinitions.containsValue(answer)) {
                            for (var answerEntry : cardsAndDefinitions.entrySet()) {
                                if (answer.equals(answerEntry.getValue())) {
                                    key = answerEntry.getKey(); //This is not being used, why do you have it here?
                                    System.out.println("Wrong. The right answer is \"" +
                                            cardsAndDefinitions.get(entry)
                                            + "\", but your definition is correct for \"" + answerEntry.getKey() + "\".");
                                }
                            }
                        } else {
                            System.out.println("Wrong. The right answer is \"" +
                                    cardsAndDefinitions.get(entry) + "\"");
                        }
                        count++;
                    }
                }
                case "log" -> {
                    System.out.println("File name:");
                    logs += "File name:\n";
                    String fileName = scanner.nextLine();
                    logs += fileName + '\n';
                    PrintStream file = new PrintStream(fileName);
                    PrintStream console = System.out;
                    System.setOut(file);
                    file.print(logs);
                    file.close();
                    System.setOut(console);
                    console.println("This has been saved");
                    logs += "This has been saved";
                }
                default -> {
                }
            }
        }
        if (Objects.equals(response, "exit")) {
            System.out.println("Bye bye!");
        }
    }
}
