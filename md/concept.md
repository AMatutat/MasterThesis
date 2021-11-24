# Eigenes Konzept

<!--

*   Zielsetzung definieren, sowie Bewertungskriterien darstellen
*   Herausstellen der verwendeten Feature Elemente aus den anderen Algorithmen
*   Vergleich mit bekannten Lösungen: Worin unterscheiden sich die eigenen Ansätze von den bekannten? Wo liegen mögliche Vor- oder Nachteile?
*   Darstellen von Theoretischen Problemen der zusammenfügung
*   Beschreiben wie diese Probleme auf konzeptueller ebene behoben werden
*   Beschreibung des gesamten umsetzungs konzeptes 

geschätzter  ca. 20% ... 30% der Arbeit
-->

In diesem Kapitel wird ein eigenes Konzept zur Implementierung eines prozeduralen graphenbasierten 2D-Levelgenerator vorgestellt. Der konzeptionierte Generator soll nicht nur gute Level erzeugen, sondern auch in das PM-Dungeon integriert werden können und für die Studierenden nutzbar gemacht werden. Der Generator soll die in Kapitel 3 vorgestellten Algorithmen verwenden und miteinander kombinieren, um effektiv gute Level zu generieren. Im nächsten Abschnitt werden weitere spezifische Anforderungen für den Generator aufgestellt, die sich vor allem auf die Integration des Generators in das PM-Dungeon fokussieren. Um die Besonderheiten des in dieser Arbeit konzeptionierten Algorithmus hervorzuheben, findet in Abschnitt 3.2 eine Abgrenzung zu anderen prozeduralen Algorithmen, bevor im letzten Abschnitt das modulare Bausteinkonzept für den Generator vorgestellt und das Konzept zur Implementierung des Generators präsentiert wird. 

## Zielsetzung und Anforderungen an das Projekt 

- Fokussiert auf die Anforderungen an das Programm, nicht auf die Anforderungen der Level
- Schnittstellenanforderungen
- Was genau muss der Algo können?
- Was nicht?
- Welche Anforderungen muss der Algorithmus und das Framework erfüllen?

## Abgrenzung zu anderen Levelgeneratoren und prozeduralen Algorithmen 

- Warum ist dieser Algo besser für das PM-Dungeon als die anderen?
- Welche Grenzen wird dieser Algo haben im vergleich zu den anderen?

## Darstellung des Konzeptes

Der Dungeongenerator *DungeonG* wird mithilfe der im vorherigen Kapitel vorgestellten Algorithmen umgesetzt und besteht im Wesentlichen aus drei Bausteinen.
Baustein eins ist der Graphgenerator **GraphG**, welcher einen planaren Graphen generiert.
Baustein zwei ist der Raumgenerator **RoomG**, welcher verschiedene Räume aus vorgegebenen Templates erzeugt. 
Baustein drei ist der Levelgenerator **LevelG**, welcher den von GraphG erzeugten Graphen nimmt und diesen mithilfe der Räume von RoomG in ein spielbares Level verwandelt. 

Im Folgenden werden die konkreten Aufgaben und Konzepte zur Umsetzung der einzelnen Bausteine und deren Kombination miteinander beschrieben. Ebenso wird beschrieben, wie DungeonG in das PM-Dungeon integriert werden kann und welche Änderungen am bisherigen PM-Dungeon dafür notwendig sind. Da den Studierenden verschiedene Optionen zur Interaktion mit den erzeugten Level ermöglicht werden soll, wird auch eine umfangreiche Schnittstelle hierfür definiert. 

### GraphG

- Aufgaben und Anforderungen: 

  - Planaren graphen Generieren
    - durch einhalten des theorem

  - Anzahl der Knoten und Kanten soll teilweise bestimmbar sein
  - ausgabe nach dot zur visualisierung und abspeicherung
- Konzept:

  - GraphG bekommt Die gewünschte Anzahl der Knoten n und die gewünschet Anzahl an Extra kanten e(was ist das) übergeben
  - Prüfen der eingaben ob gültig und ob e<=3n-6 hält
  - GraphG erzeugt alle möglichen Trees aus der Knotenanzahl n die das Theorem einhalten

    - wie?
  - Jetzt müssen die Kanten eingezeichnet werden
  - Jede möglichkeit eine Kante im Tree einzuzeichen die das Theorem hält wird mithilfe einer Kopie des Trees umgesetzt. Das sind jetzt Graphen. 
  - Dieses verfahren wird so oft rekursiv ausgeführt bis die gewünschte anzahl an kanten eingezeichnet wird
  - Am ende kommt eine Liste mit allen möglichen Graphen die n Knoten mit e Extrakanten haben und das Theorem halten. 
  - von hier aus kann in zwei richtungen weitergearbeitet werden
  - entweder aus der Liste einen zufälligen Graphen picken und damit arbeiten
  - Die liste in einer json speichern

    - dann gibt es eine funktion welche die json einliest und daraus dann zufällig einen Graphen zurückgibt mit n Kanten und e Edge

![UML-Klassendiagramm für GraphG mit den wichtigsten Methoden. \label{graphgUML](figs/chapter4/graphgUML.png)

![UML-Sequenzdiagramm für GraphG ohne Einblick in die Rekursiven Methoden. \label{graphgSeq](figs/chapter4/graphgSeq.png)

### RoomG

### LevelG

### Integration in das PM-Dungeon

### Schnittstellen






