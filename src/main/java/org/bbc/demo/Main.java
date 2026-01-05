package org.bbc.demo;

import org.bbc.demo.controller.LoginController;

public class Main {
    public static void main(String[] args) {
        // App starten über Controller
        LoginController app = new LoginController();
        app.start();
    }
}