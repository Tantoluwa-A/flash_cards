package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> cardsAndDefinitions = new LinkedHashMap<>();
        Map<String, Integer> errors = new LinkedHashMap<>();
        String term;
        String definition;
        String response = "";
        StringBuilder logs = new StringBuilder();
        int count = 0;
        int countAdd = 0;
        int sumOfLoadedCards = 0;
        int errorCount = 0;
        while (!Objects.equals(response, "exit")) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats): ");
            logs.append("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats): \n");
            response = scanner.nextLine();
            logs.append(response).append("\n");
            switch (response) {
                case "add" -> {
                    System.out.println("The card:");
                    logs.append("The card:\n");
                    term = scanner.nextLine();
                    logs.append(term).append("\n");
                    if (cardsAndDefinitions.containsKey(term)) {
                        System.out.println("The card \"" + term + "\" already exists.");
                        logs.append("The card \"").append(term).append("\" already exists.\n");
                        System.out.println();
                        logs.append("\n");
                        continue;
                    }
                    System.out.println("The definition of the card:");
                    logs.append("The definition of the card:\n");
                    definition = scanner.nextLine();
                    logs.append(definition).append("\n");
                    if (cardsAndDefinitions.containsValue(definition)) {
                        System.out.println("The definition \"" + definition + "\" already exists.");
                        logs.append("The definition \"").append(definition).append("\" already exists.\n");
                    } else {
                        cardsAndDefinitions.put(term, definition);
                        countAdd++;
                        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
                        logs.append("The pair (\"").append(term).append("\":\"").append(definition).append("\") has been added.\n");
                    }
                    System.out.println();
                    logs.append("\n");
                }
                case "remove" -> {
                    System.out.println("Which card?");
                    logs.append("Which card?\n");
                    String cardToBeRemoved = scanner.nextLine();
                    logs.append(cardToBeRemoved).append("\n");
                    errors.remove(cardToBeRemoved);
                    if (cardsAndDefinitions.containsKey(cardToBeRemoved)) {
                        cardsAndDefinitions.remove(cardToBeRemoved);
                        sumOfLoadedCards--;
                        System.out.println("The card has been removed.");
                        logs.append("The card has been removed.\n");
                    } else {
                        System.out.println("Can't remove \"" + cardToBeRemoved + "\": there is no such card.");
                        logs.append("Can't remove \"").append(cardToBeRemoved).append("\": there is no such card.\n");
                    }
                    System.out.println();
                    logs.append("\n");
                }
                case "import" -> {
                    System.out.println("File name:");
                    logs.append("File name:\n");
                    String importFileName = scanner.nextLine();
                    logs.append(importFileName).append("\n");
                    File file = new File(importFileName);
                    try (Scanner myReader = new Scanner(file)) {
                        count = 0;
                        while (myReader.hasNext()) {
                            term = myReader.nextLine();
                            definition = myReader.nextLine();
                            String testError = myReader.nextLine();
                            if (Objects.equals(testError, "null")) {
                                errorCount = 0;
                            } else {
                                errorCount = Integer.parseInt(testError);
                            }
                            cardsAndDefinitions.put(term, definition);
                            errors.put(term, errorCount);
                            sumOfLoadedCards++;
                            count++;
                        }
                        myReader.close();
                        System.out.println(count + " cards have been loaded.");
                        logs.append(count).append(" cards have been loaded.\n");
                        System.out.println();
                        logs.append("\n");
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                        logs.append("File not found.\n");
                        System.out.println();
                        logs.append("\n");
                    }
                }
                case "export" -> {
                    System.out.println("File name:");
                    logs.append("File name:\n");
                    String exportFileName = scanner.nextLine();
                    File exportFile = new File(exportFileName);
                    try (PrintWriter writer = new PrintWriter(exportFile)) {
                        for (var entry : cardsAndDefinitions.entrySet()) {
                            writer.println(entry.getKey());
                            writer.println(entry.getValue());
                            writer.println(errors.get(entry.getKey()));
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred " + e.getMessage());
                    }
                    System.out.println(cardsAndDefinitions.size() + " cards have been saved.");
                    logs.append(countAdd).append(sumOfLoadedCards).append(" cards have been saved.\n");
                    System.out.println();
                    logs.append("\n");
                }
                case "ask" -> {
                    System.out.println("How many times to ask?");
                    logs.append("How many times to ask?\n");
                    int times = Integer.parseInt(scanner.nextLine());
                    logs.append(times).append("\n");
                    List<String> keys = new ArrayList<>(cardsAndDefinitions.keySet());
                    Random r = new Random();
                    for (int i = 0; i < times; i++) {
                        String randomKey = keys.get(r.nextInt(keys.size()));
                        System.out.println("Print the definition of " + "\"" + randomKey + "\":");
                        logs.append("Print the definition of " + "\"").append(randomKey).append("\":\n");
                        String answer = scanner.nextLine();
                        logs.append(answer).append("\n");
                        if (answer.equals(cardsAndDefinitions.get(randomKey))) {
                            System.out.println("Correct!");
                            logs.append("Correct!\n");
                        } else if (cardsAndDefinitions.containsValue(answer)) {
                            for (var answerEntry : cardsAndDefinitions.entrySet()) {
                                if (answer.equals(answerEntry.getValue())) {
                                    System.out.println("Wrong. The right answer is \"" +
                                            cardsAndDefinitions.get(randomKey)
                                            + "\", but your definition is correct for \"" + answerEntry.getKey() + "\".");
                                    logs.append("Wrong. The right answer is \"").append(cardsAndDefinitions.get(randomKey)).append("\"," +
                                            " but your definition is correct for \"").append(answerEntry.getKey()).append("\".\n");
                                    if (errors.containsKey(randomKey)) {
                                        errorCount++;
                                        errors.put(randomKey, errorCount);
                                    } else {
                                        errorCount = 1;
                                        errors.put(randomKey, errorCount);
                                    }
                                }
                            }
                        } else {
                            System.out.println("Wrong. The right answer is \"" +
                                    cardsAndDefinitions.get(randomKey) + "\"");
                            logs.append("Wrong. The right answer is \"").append(cardsAndDefinitions.get(randomKey)).append("\"\n");
                            if (errors.containsKey(randomKey)) {
                                errorCount++;
                                errors.put(randomKey, errorCount);
                            } else {
                                errorCount = 1;
                                errors.put(randomKey, errorCount);
                            }
                        }
                        count++;
                    }
                }
                case "log" -> {
                    System.out.println("File name:");
                    logs.append("File name:\n");
                    String fileName = scanner.nextLine();
                    logs.append(fileName).append("\n");
                    PrintStream logFile = new PrintStream(fileName);
                    PrintStream console = System.out;
                    System.setOut(logFile);
                    logFile.print(logs);
                    logFile.close();
                    System.setOut(console);
                    console.println("The log has been saved.");
                    logs.append("The log has been saved.\n");
                }
                case "hardest card" -> {
                    if (errors.isEmpty()) {
                        System.out.println("There are no cards with errors.");
                        logs.append("There are no cards with errors.\n");
                    } else {
                        List<Integer> errorCountList = new ArrayList<>(errors.values());
                        List<String> hardestCards = new ArrayList<>();
                        int maxErrorCount = Collections.max(errorCountList);
                        for (var errorEntry : errors.entrySet()) {
                            if (maxErrorCount == errorEntry.getValue()) {
                                hardestCards.add(errorEntry.getKey());
                            }
                        }
                        if (hardestCards.size() == 1) {
                            System.out.println("The hardest card is \"" + hardestCards.get(0)
                                    + "\". You have " + maxErrorCount + " errors answering it.");
                            logs.append("The hardest card is \"").append(hardestCards.get(0))
                                    .append("\". You have ").append(maxErrorCount).append(" errors answering it.\n");
                        } else {
                            System.out.print("The hardest cards are ");
                            logs.append("The hardest cards are ");
                            for (String hardestCard : hardestCards) {
                                System.out.print("\"" + hardestCard + "\"");
                                logs.append(hardestCard.replace("", "\""));
                                if (!Objects.equals(hardestCard, hardestCards.get(hardestCards.size() - 1))) {
                                    System.out.print(",");
                                    logs.append(",");
                                }
                            }
                            System.out.println(". You have " + maxErrorCount + " errors answering them.");
                            logs.append(". You have ").append(maxErrorCount).append(" errors answering them.\n");
                        }
                    }
                    System.out.println();
                    logs.append("\n");
                }
                case "reset stats" -> {
                    errorCount = 0;
                    errors.clear();
                    System.out.println("Card statistics have been reset.");
                    logs.append("Card statistics have been reset.\n");
                    System.out.println();
                    logs.append("\n");
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
