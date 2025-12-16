package org.bbc.demo.view;

import java.util.Scanner;

public class ConsoleUi {
    private final Scanner scanner;

    public ConsoleUi() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("=========================================");
        System.out.println("   UNSICHERES LOGIN (ÜK106/ÜK223)      ");
        System.out.println("=========================================");
    }

    public String getUserName() {
        System.out.print("Benutzername: ");
        return scanner.nextLine();
    }

    public String getUserPassword() {
        System.out.print("Passwort:     ");
        return scanner.nextLine();
    }

    public void showResult(String result) {
        System.out.println("\n>> SYSTEM ANTWORT:");
        System.out.println(result);
    }
}