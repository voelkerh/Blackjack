# README

*Blackjack-Microservice*

Modul: B42 Softwareengineering und Softwarearchitekturen
Studiengang: Informatik in Kultur und Gesundheit
Semester: Sommersemester 2025
Studierende: Johanna Menzler (S0591297), Henriette Voelker (S0589167)

## Projektbeschreibung

Dieses Repository enthält einen Microservice mit RESTful-API für das Spiel Blackjack.
Das Projekt ist mit Docker containerisiert.
Die Implementierung orientiert sich an den Prinzipien YAGNI, KISS, SOLID und DRY.

### Architektur

Die Implementierung folgt der hexagonalen Architektur (auch "Ports und Adapters").
Eine zentrale Referenz für die getroffenen Design-Entscheidungen war:
Tom Hombergs, Clean Architecture. Praxisbuch für saubere Software-Architektur und wartbaren Code, mitp 2024.

Die Implementierung umfasst einen REST-Controller mit differenzierten Controller-Klassen als Inbound-Adapter.
Der Outbound-Adapter basiert auf Jakarta Persistence und kommuniziert mit einer PostgreSQL-DB.
Im "Hexagon", also dem Applikationskern, sind vier Use Cases mit Service-Klassen implementiert.
Diese Struktur deckt die Anwendungsfälle Regeln abrufen, Spiel spielen, Chancen berechnen und Statistiken abrufen ab.

### Kollaboration

Die Zusammenarbeit haben wir über ein GitLab Repository organisiert.
Darin haben wir ein Kanban Board angelegt.
Die Issues haben wir wöchentlich gemeinsam definiert und priorisiert.
Wir haben mit Issue Branches und Merge Requests gearbeitet.

### Dokumentation

Für das Projekt liegen ein Zustandsdiagramm, Diagramme für alle API-Endpoints und ein Komponentendiagramm vor.
Die Diagramme sind im docs-Ordner hinterlegt.

Der Code ist mit JavaDoc dokumentiert.
Die Dokumentation für Methoden findet sich, wo anwendbar, in den Interfaces.

Die API ist über Swagger UI dokumentiert (siehe http://localhost:8080/swagger-ui/index.html).

### Verwendete Build Tools, Libraries und Frameworks

Das Projekt ist in Java 21 mit Maven als Spring Boot Application mit Jakarta Persistence umgesetzt.
Wo es möglich ist, nutzen wir Lombok-Annotationen für Getter, Setter und Konstruktoren.
Die Tests basieren auf JUnit 5 und Mockito als Mocking-Framework.
Die Diagramme wurden mit PlantUML erstellt.

### Test-Strategie

Das Projekt beschränkt sich auf Unit-Tests.
Bezüglich der Testabdeckung haben wir uns an Line Coverage und diversifizierten Testfällen orientiert.
Integrationstests werden den Anforderungen entsprechend nicht implementiert.
Für Record-Klassen sowie alle Utility-Klassen wurde nach Absprache auf Tests verzichtet.

## Installationsanweisung

1. Clone the repository.
2. cd into the project root.
3. Start docker container with `docker compose up --build`.
4. Access Swagger UI: http://localhost:8080/swagger-ui/index.html

## Bedienanleitung

Das Spiel basiert auf einer vereinfachten Version von Blackjack.
Es lässt sich mit HTTP-Requests über die API spielen.

### Spielregeln

Der Spieler versucht, den Dealer zu schlagen.
Er versucht, den Wert seines Blattes näher an 21 heranzubringen,
ohne ihn zu überschreiten oder den Dealer zum Überlaufen zu bringen.

Für die Karten gilt:
- Die Karten 2-10 entsprechen dem Kartenwert
- Buben, Damen und Könige zählen als 10
- Asse können 1 oder 11 sein (nach Wahl des Spielers)

Aufbau:
- 1 Spieler, 1 Geber
- 52-Karten-Deck ohne Joker, gemischt
- Starthand:
    - Spieler erhält zwei Karten
    - Dealer erhält eine offene und eine verdeckte Karte
- Wetten:
    - Spieler gibt seinen Einsatz ab

Spielablauf:
- Wenn der Spieler mit einem Blatt von 21 (Ass + Bube/Dame/König) beginnt, hat er einen "Blackjack".
- Der Spieler entscheidet sich für "Hit" oder "Stand"
    - Hit: eine weitere Karte ziehen
      -> Wenn die Hand des Spielers 21 übersteigt, ist er pleite und verliert.
    - Stehen: das aktuelle Blatt behalten

- Der Spieler wählt "Hit", bis er "Stand" wählt.

- Dealer deckt verdeckte Karte auf
    - Asse zählen als 11, wenn der Dealer nicht „bust“ ist
      --> Wenn die Hand des Dealers 21 überschreitet, ist er pleite und der Spieler gewinnt.
      --> Wenn der Geber einen Blackjack hat, verliert der Spieler, es sei denn, er hat selbst einen Blackjack.
    - Wenn die Hand des Dealers < 17 ist - "Hit"
    - Wenn die Hand des Dealers >= 17 ist, endet die Runde (außer es ist ein Ass - weiche 17, der Dealer schlägt weiter)

- Der Gewinner der Runde wird ermittelt:
    - Wer näher an 21 ist, gewinnt
    - Wenn die Hand des Spielers und die des Dealers den gleichen Wert haben - "Push"

Gewinnverteilung:
- Spieler und Dealer haben einen Blackjack: Spieler erhält seinen Einsatz zurück ("Push")
- Spieler gewinnt: wird im Verhältnis 1 : 1 ausgezahlt
- Spieler verliert: verliert seinen Einsatz
- Spieler hat einen Blackjack (und Dealer nicht): wird im Verhältnis 2 : 3 ausgezahlt

Vereinfachung: kein Split, kein Double, kein Surrender, kein Side-Betting (Insurance)

### Berechnung der Chancen

Für jeden Spielstand kann ausgegeben werden,
wie hoch die Wahrscheinlichkeit für einen Blackjack oder einen Bust beim Ziehen der nächsten Karte ist.

Verwendet wurden folgende Formeln:

m := 1 (Anzahl der genutzen CardDecks)
x (Wert der Karte, für die die Auftrittswahrscheinlichkeit im nächsten Zug bestimmt wird)
N = (Gesamtanzahl der bereits gezogenen Karten, die jetzt in Spieler- oder Dealerhand sind)
n(x) (Anzahl der Karten mit dem Wert x, die bereits gezogen wurden und jetzt in Spieler- oder Dealerhand sind)
P := Wahrscheinlichkeit

Wenn x != 10: P(x) = [4m - n(x)] / [52m - N]
Wenn x == 10: P(10) = [16m - n(10)] / [52m - N]

Wir bestimmen P(bj) und P(bu).
bj (x-Wert, der für einen Blackjack benötigt wird)
bu ({x | x > bj}, Kartenwerte, für die im nächsten Zug ein Bust eintreten würde)

bj = 21 - playerHandTotal
P(bj) lässt sich unmittelbar mit den obenstehenden Formeln bestimmen.

Für P(bu) summieren wir alle Wahrscheinlichkeiten für Karten, die einen Bust auslösen, auf.
P(bu) = P(x1) + ... + P(xi)

Über die Formeln haben wir uns auf dieser Seite informiert: https://probability.infarom.ro/blackjack.html.

### Berechnung der Statistik

TODO: Kurze Beschreibung

### Spielen über die API

Spielen:
- TODO: API-Endpunkte ergänzen

Regeln abrufen:
- Generelle Regeln: GET /api/blackjack/rules
- Aktionsspezifische Regeln: GET /api/blackjack/rules/{action}, mögliche Werte: HIT, STAND

Chancen für die aktuell möglichen Spielzüge in laufendem Spiel abrufen:
- GET /api/blackjack/chances/{gameId}

Statistik abrufen:
- Allgemeine Statistik: GET /api/blackjack/stats/overview
- User spezifische Statistiken: GET /api/blackjack/stats/user/{userId}

## Lizenzverweis

Blackjack Micro-Service by Johanna Menzler and Henriette Voelker is marked CC0 1.0.