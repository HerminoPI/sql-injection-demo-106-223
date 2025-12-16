# ⚠️ Warum dein Login in 2 Sekunden gehackt wird

> **"SQL Injection ist seit Jahren eine der grössten Sicherheitslücken im Web."**
> ➡️[OWASP Top 10 (A03: Injection)](https://owasp.org/Top10/2021/de/A03_2021-Injection/)

Dieses Repo zeigt euch den tödlichen Fehler der **String-Concatenation*** und die professionelle Lösung.

*<small>* *String-Concatenation: Das einfache Zusammenkleben von Textbausteinen mit dem Plus-Zeichen (z.B. `"SELECT..." + userInput`).</small>

![Lehrablaufplan](assets/intro_image.png)

---

## 🛠️ Das Setup (Unsere Umgebung)

Damit wir das sicher testen können, ohne einen echten Server zu zerstören, nutzen wir eine isolierte Labor-Umgebung.

| Technologie                                                                                                    | Warum wir das nutzen                                                                    |
|:---------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------|
| ![Java](https://img.shields.io/badge/Java-18%2B-ED8B00?style=flat-square&logo=openjdk&logoColor=white)         | Aktuelle Java-Version für moderne Syntax.                                               |
| ![Maven](https://img.shields.io/badge/Maven-Project-C71A36?style=flat-square&logo=apachemaven&logoColor=white) | Lädt automatisch alle Bibliotheken (kein manueller Download).                           |
| **H2 Database**                                                                                                | Eine **In-Memory Datenbank**. Sie lebt im RAM. Nach dem Neustart ist sie wieder sauber. |
| **MVC Pattern**                                                                                                | **Model-View-Controller**. Wir trennen sauberen Code (Logik) von der Anzeige (Konsole). |

---

## 🧑‍💻 Das Szenario (Code & Daten)

Bevor wir hacken, müssen wir wissen, was wir angreifen.

### 1. Die Datenbank (Unsere Ziele)
Wir haben 3 User in unserer `users` Tabelle. Unser Ziel: **Login als Admin**, ohne das Passwort zu kennen.

| ID | Username  | Password    | Rolle       |
|:---|:----------|:------------|:------------|
| 1  | **admin** | `12345`     | 🎯 **Ziel** |
| 2  | marco     | `leet1337`  | User        |
| 3  | leandra   | `TopSecret` | User        |

### 2. Der Code (Die Schwachstelle)
Wie überprüft das System unser Passwort? Klickt unten, um den Code aus `DatabaseService.java` zu sehen.

<details>
<summary> <b>Code anzeigen (Unsicher)</b></summary>

```java
// ❌ UNSICHER: String Concatenation
// Das System klebt unseren Input einfach an den Befehl:
String sql = "SELECT * FROM users WHERE username = '" + inputUser + "' AND secret = '" + inputPass + "'";
// Das Problem: Java vertraut dem Input blind. Das + Zeichen ist hier das Einfallstor.
```
</details>