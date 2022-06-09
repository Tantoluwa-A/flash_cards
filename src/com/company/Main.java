package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Card:");
        System.out.println("purchase");
        System.out.println("Definition:");
        System.out.println("buy");
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        String b = scanner.nextLine();
        String c = scanner.nextLine();
        if (b.equals(c)) {
            System .out.println("Your answer is right!");
        } else {
            System.out.println("Your answer is wrong...");
        }
    }
}
