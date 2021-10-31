# Eigene Ideen, Konzepte, Methoden

<!--
*   Zielsetzung definieren, sowie Bewertungskriterien darstellen
*   Herausstellen der verwendeten Feature Elemente aus den anderen Algorithmen
*   Vergleich mit bekannten Lösungen: Worin unterscheiden sich die eigenen Ansätze von den bekannten? Wo liegen mögliche Vor- oder Nachteile?
*   Darstellen von Theoretischen Problemen der zusammenfügung
*   Beschreiben wie diese Probleme auf konzeptueller ebene behoben werden
*   Beschreibung des gesamten umsetzungs konzeptes 

geschätzter  ca. 20% ... 30% der Arbeit
-->

## Zielsetzung und Anforderungen an das Projekt

- Fokussiert auf die Anforderungen an das Programm, nicht auf die Anforderungen der Level
- Schnittstellenanforderungen
- Was genau muss der Algo können?
- Was nicht?
- Welche Anforderungen muss der Algorithmus und das Framework erfüllen?

## Bewertungsverfahren

- Kriterien aus Kapitel 2

  - Manuelle Analyse 

- Umfrage bei Studenten (Zeitplanung?)

## Abgrenzung zu den anderen Algorithmen

- Warum ist dieser Algo besser für das PM-Dungeon als die anderen?
- Welche Grenzen wird dieser Algo haben im vergleich zu den anderen?

## Darstellung des Konzeptes

Der Dungeongenerator *DungeonG* wird mithilfe der im vorherigen Kapitel vorgestellten Algorithmen umgesetzt und besteht im Wesentlichen aus drei Bausteinen.
Baustein eins ist der Graphgenerator **GraphG**, welcher einen planaren Graphen generiert.
Baustein zwei ist der Raumgenerator **RoomG**, welcher verschiedene Räume aus vorgegebenen Templates erzeugt. 
Baustein drei ist der Levelgenerator **LevelG**, welcher den von GraphG erzeugten Graphen nimmt und diesen mithilfe der Räume von RoomG in ein spielbares Level verwandelt. 

Im Folgenden werden die konkreten Konzepte zur Umsetzung der einzelnen Bausteine und deren Kombination miteinander beschrieben. Ebenso wird beschrieben, wie DungeonG in das PM-Dungeon integriert werden kann und welche Änderungen am bisherigen PM-Dungeon dafür notwendig sind. Da den Studierenden verschiedene Optionen zur Interaktion mit den erzeugten Level ermöglicht werden soll, wird auch eine umfangreiche Schnittstelle hierfür definiert. 

### GraphG

### RoomG

### LevelG

### Integration in das PM-Dungeon

### Schnittstellen






