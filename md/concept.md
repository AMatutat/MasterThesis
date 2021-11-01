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
  - Knoten solle gelabelt werden können
  - ausgabe nach dot zur visualisierung und abspeicherung

- Konzept:

  - Class Edge:

    - gibt die verbindungen an
    - warum eigene Klasse für die Kanten und nicht einfach eine Liste Nachbarn <Knoten> in Class Knoten
    - Hashvalue: WAIT theortisch könnte die SUmme beider Hashwerte ja nochmal auftreten. Idee also okay aber anders berechnen
  - Class Knoten

    - name des Knoten

    - anzahl der Nachbarn
  - Graph
    - Bekommt die Parameter übergeben
    - Erzeugt die Knoten
    - ConnectUnconnected
      - 1. Nimm die erten beiden Knoten und verbinde diese
        2. Nimm einen unverbunden Knoten und verbinde ihn mit einen zufälligen bereits verbunden Knoten
           1. achte darauf, dass nicht zuviele Knoten mit zuvielen Kanten erzeugt werden (Liste führen)
        3. repeat
    - addExtraEdges
      - solange noch edges erstellt werden sollen
        - nimm zwei zufällige Knoten
        - prüfe ob die Knoten gleich sein
          - ja: nächster durchlauf
          - nein: weiter
        - prüfe ob die Knoten bereits verbunden sind:
          - ja: nächter durchlauf
          - nein: weiter
        - prüfe ob das Theorem hält, wenn die Knoten verbunden werden
          - Prüfe für beide Knoten
            - würde eine weitere Verbindung die Anzahl der Nachbarn über den Schwellwert bringen?
              - nein: verbinde
              - ja: ist der Knoten bereits in der Liste?
                - ja: verbinde
                - nein: wäre die Liste überfüllt wenn er hinzugefügt wird:
                  - ja: nächster durchlauf
                  - nein: müsste der nächste Knoten auch in die Liste hinzugefügt werden 
                    - ja: wäre die liste dann überfüllt?
                      - ja: nächster durchlauf
                      - nein: verbinden 
                    - nein: verbinde

  ​    

### RoomG

### LevelG

### Integration in das PM-Dungeon

### Schnittstellen






