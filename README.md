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

### 2. Die Schwachstelle im Code
Wie überprüft das System unser Passwort? Klickt unten, um den Code aus `DatabaseService.java` zu sehen.

<details>
<summary> <b>Unsichere Methode anzeigen</b></summary>

```java
// ❌ UNSICHER: String Concatenation
// Das System klebt unseren Input einfach an den Befehl:
String sql = "SELECT * FROM users WHERE username = '" + inputUser + "' AND secret = '" + inputPass + "'";
```
Das Problem: Java vertraut dem Input blind. Das + Zeichen ist hier das Einfallstor.
</details>

### 3. Der Schutz im Code

Wie verhindern wir den Einbruch? Wir müssen Code und Daten trennen. Dafür nutzen wir in Java das **PreparedStatement**.

<details>
  <summary> <b>Sichere Methode anzeigen✅</b></summary>

  ```java
  // ✅ SICHER: PreparedStatement
  // Wir nutzen Platzhalter (?) statt direkt Variablen einzufügen
  String sql = "SELECT * FROM users WHERE username = ? AND secret = ?";

  PreparedStatement preparedStatement = connection.prepareStatement(sql);
  
  // Die Lücken füllen wir sicher auf:
  preparedStatement.setString(1, inputUser); // Füllt das 1. Fragezeichen
  preparedStatement.setString(2, inputPass); // Füllt das 2. Fragezeichen
```
Warum das sicher ist: Der SQL-Befehl wird zuerst kompiliert. Die Datenbank weiss: "Da wo ? steht, kommen nur Daten hin". Selbst wenn ein Hacker ' OR '1'='1 eingibt, wird das nicht ausgeführt, sondern einfach als Text behandelt. Der Hack ist neutralisiert.

</details>

---

## 🎓 Fazit (Takeaway)

Was nehmen wir heute mit? Klickt hier für die Zusammenfassung.

<details>
  <summary>💡 <b>Die Goldene Regel anzeigen</b></summary>

### 1. Niemals Strings basteln
Benutze in produktiven Code **nie** das `+` Zeichen mit SQL, um User-Input einzufügen.

### 2. Vertraue keinem Input
Behandle alle Daten von ausserhalb als potenziell gefährlich. Egal ob User, Admin oder "internes System".

### 3. Nutze PreparedStatements
Das `?` ist dein Sicherheitsgurt. Es trennt **Code** (Befehl) von **Daten** (Input).

  ---

**🔗 Passendes MEME:**
* [Bobby Tables (Comic & Erklärung)](https://imgs.xkcd.com/comics/exploits_of_a_mom_2x.png)
</details>