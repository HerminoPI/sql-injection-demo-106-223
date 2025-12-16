package org.bbc.demo.model;

import java.sql.*;

public class DatabaseService {

    private static final String DB_URL = "jdbc:h2:mem:testdb";
    private Connection connection;

    public DatabaseService() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, "sa", "");
            prepareDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void prepareDatabase() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE users (id INT PRIMARY KEY, username VARCHAR(255), secret VARCHAR(255))");

        // Echte Passwörter simulieren
        stmt.execute("INSERT INTO users VALUES (1, 'admin', '12345')"); // Einfaches PW für Demo
        stmt.execute("INSERT INTO users VALUES (2, 'marco', 'leet1337')");
        stmt.execute("INSERT INTO users VALUES (3, 'leandra', 'TopSecret')");
    }

    public String login(String inputUser, String inputPass) {
//
//        // -----------------------------------------------------------
//        // VARIANTE A: UNSICHER (String Concatenation)
//        // -----------------------------------------------------------
        try {
            Statement stmt = connection.createStatement();

            // HIER IST DAS SICHERHEITSLOCH:
            // Wir kleben User und Passwort einfach in den String.
            String sql = "SELECT * FROM users WHERE username = '" + inputUser + "' AND secret = '" + inputPass + "'";

            System.out.println("[DB LOG] SQL: " + sql);

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return "SUCCESS! Eingeloggt als: " + rs.getString("username");
            }
        } catch (SQLException e) {
            return "SQL ERROR: " + e.getMessage();
        }
        return "DENIED. Falscher User oder falsches Passwort.";


        // -----------------------------------------------------------
        // VARIANTE B: SICHER (PreparedStatement)
        // -----------------------------------------------------------
//        try {
//            // Die Fragezeichen sind Platzhalter für BEIDE Werte
//            String sql = "SELECT * FROM users WHERE username = ? AND secret = ?";
//
//            PreparedStatement pstmt = connection.prepareStatement(sql);
//            pstmt.setString(1, inputUser); // Erstes ? ist User
//            pstmt.setString(2, inputPass); // Zweites ? ist Passwort
//
//            System.out.println("[DB LOG] SQL: " + sql + " (Params: " + inputUser + ", ******) ");
//
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return "SUCCESS! Eingeloggt als: " + rs.getString("username");
//            }
//        } catch (SQLException e) {
//            return "SQL ERROR: " + e.getMessage();
//        }
//        return "DENIED. Falscher User oder falsches Passwort.";
    }

}