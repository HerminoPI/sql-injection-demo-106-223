package org.bbc.demo.controller;

import org.bbc.demo.model.DatabaseService;
import org.bbc.demo.view.ConsoleUi;

public class LoginController {

    private final DatabaseService dbService;
    private final ConsoleUi view;

    public LoginController() {
        this.dbService = new DatabaseService();
        this.view = new ConsoleUi();
    }

    public void start() {
        view.showWelcome();

        // 1. Inputs holen (User UND Passwort)
        String user = view.getUserName();
        String pass = view.getUserPassword();

        // 2. Login versuchen
        String result = dbService.login(user, pass);

        // 3. Ergebnis anzeigen
        view.showResult(result);
    }
}