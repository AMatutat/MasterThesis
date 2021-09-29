# Analyse

<!--

*   Analysieren und vergleichen verschiedener Algorithmen
*   Ableiten von Algorithmen aus Spielen

*Für jeden Algo*rithmus

- Wie funktioniert der Algorithmus?
  - evtl. Technologie erklären (hier oder wo anders?)
- Zeigen von Beispielen
- Bewerten das Algorithmus anhand der Kriterien
  - Was macht der Algo gut?
  - Was macht der Algo schlecht?
  - Welche grenzen gibt es?



geschätzter Umfang ca: 40%
-->

In diesem Kapitel werden verschiedene Algorithmen zur prozeduralen Generierung vorgestellt und mithilfe der in Kapitel 2 präsentierten Bewertungskriterien analysiert und miteinander verglichen. Nicht alle vorgestellten Algorithmen sind speziell für die Generierung von Level vorgesehen, können aber mit Anpassungen oder durch Kombination dazu genutzt werden. Ziel dieses Kapitel ist es, Bausteine aus verschiedenen Algorithmen herauszufiltern, um im nächsten Kapitel daraus einen neuen Algorithmus zur Generierung von Level für das PM-Dungeon zu konzeptionieren.  

## Spelunky

Spelunky ist ein 2D-Rogue-Like Plattformer. Es verbindet das klassische Gameplay von Plattformern und erweitert sie um prozedural generierte Level und Permadeath aus dem Rogue-Like Genre. Derek Yu, der Entwickler von Spelunky beschreibt im gleichnamigen Buch "Spelunky" unter anderem wie die Level im Spiel generiert werden. [@Yu2016]

Die Aufgabe des Spielers in Spelunky ist es, vom Start des Levels bis zum Ausgang zu gelangen, dabei kann er zusätzlich Schätze sammeln, um Bonuspunkte zu erhalten. Auf dem Weg lauern verschiedene Gegner und Fallen. 

Alle Level von Spelunky werden auf einem 4x4 Grid-System erzeugt. Jedes Feld im Grid stellt einen 10x8 Felder großen Raum dar. Zu Beginn der Generierung wird ein zufälliger Raum aus der obersten Reihe ausgewählt und als Startraum markiert. Von diesem Startraum aus wird in eine zufällige Richtung gegangen, dazu wird eine Zufallszahl von 1 bis 5 genutzt. Bei 1 oder 2 wird der Raum links vom aktuellen Raum ausgewählt, bei 3 oder 4 der Raum rechts vom aktuellen Raum. Bei einer 5 oder wenn es nicht möglich ist, in die entsprechende Richtung (links oder rechts) zu gehen, weil das Ende des Grids erreicht wurde, wird stattdessen der Raum unterhalb des aktuellen Raums ausgewählt. Wenn die unterste Reihe erreicht ist und versucht wird eine weitere Reihe nach unten zu gehen, wird der aktuelle Raum als Ausgang markiert und der Algorithmus endet. 

Jeder Raum im Grid ist mit einer Nummer markiert. Der Initialstatus jeden Raums ist 0 und gibt an, dass der Raum beim Erzeugen des kritischen Pfades nicht betreten wurde, er also optional ist. Beim Durchlaufen des Algorithmus werden die Markierungen entsprechend angepasst. Wenn ein Raum nach links oder rechts verlassen wird, wird er mit der Nummer 1 markiert, dieser Raum braucht daher einen Ausgang nach links und rechts (Minus-Form). Wird ein Raum nach unten verlassen, wird er mit einer 2 markiert und benötigt einen Ausgang nach links, rechts und unten (T-Kreuzung nach unten). Ist der darüberliegende Raum auch mit einer 2 markiert, so wird auch ein Ausgang nach oben benötigt (Kreuzung).  Wird ein Raum von oben betreten und nach links oder rechts verlassen, wird er mit einer 3 markiert und braucht einen Ausgang nach links, rechts und nach oben (T-Kreuzung nach oben). 

Durch dieses System kann sichergestellt werden, dass der kritische Pfad zwischen Start und Ende eine durchgehende Verbindung besitzt und das Level somit lösbar ist. Es werden keine Items wie Bomben oder Seile benötigt, um zum Ende zu kommen. Alle optionalen (mit 0 markiert) Räume werden abschließend zufällig mit 1,2 oder 3 markiert und können daher auch direkt am kritischen Pfad angeschlossen sein oder auch nicht. Alle nicht angeschlossene Räume können dann nur mit bestimmten Items erreicht werden. Abbildung \ref{spelunkylevel} zeigt ein erzeugtes Level mit den jeweiligen Raumnummerierungen in den Ecken sowie den kritischen Pfad in Form von roten Blöcken. 

Abhängig von der Markierung eines Raums wird er mit einem von mehreren per Hand gebauten Templates gefüllt. Diese 10x8 großen Templates geben an, an welcher Stelle im Raum welcher Art von Block platziert ist. Um die Variation an Räumen möglichst groß zu gestalten und zeitgleich nicht hunderte unterschiedlicher Templates eigenständig zu bauen, werden die Templates modifiziert. 

Jedes Template lässt sich als String darstellen und kann als 8x10 Matrix verstanden werden. In jedem Feld der Matrix steht ein Wert, dieser Wert gibt an, welche Art von Block an der jeweiligen Stelle zu platzieren ist (vgl. Tabelle \ref{spelunkytable}). Einige Felder, sogenannte Chunks, ersetzten eine 5x3 große Fläche durch eines von zehn vorgefertigten Chunk-Templates. Durch die Veränderung der Templates, lassen sich viele unterschiedliche Räume generieren. 


| Wert | Ersetzen durch                               |
| ---- | -------------------------------------------- |
| 0    | Freie Fläche                                 |
| 1    | Solider Block                                |
| 2    | Zufallswahl: freie Fläche oder solider Block |
| 4    | bewegbarer Steinblock                        |
| 6    | 5x3 Chunk                                    |
| L    | Leiterteil                                   |
| P    | Leiterteil mit Fläche zum stehen             |

: Ersetzungstabelle für Spelunky \label{spelunkytable}

Um Monster und Items zu verteilen, wird zum Schluss für jedes als 1 gekennzeichnetes Feld entschieden, ob ein Monster oder ein Schatz darauf oder darunter platziert wird oder das Feld leer bleibt. Bei der Platzierung nehmen auch umliegende Felder Einfluss, so werden beispielsweise Truhen bevorzugt in Nischen platziert. Spelunky verwendet verschiedene Texturen, um die optische Abwechslung zu gewährleisten. Nach einer bestimmten Anzahl an Level wird ein neues Theme angewandt. Auf die Struktur und den Aufbau der Level hat dies keinen Einfluss, es werden lediglich Texturen ersetzt. 

Derek Yu schrieb in seinen Buch: 

> This system doesn‘t create the most natural-looking caves ever, and players will quickly begin to recognize certain repeating landmarks and perhaps even sense that the levels are generated on a grid. But with enough templates and random mutations, there’s still plenty of variability. More importantly, it creates fun and engaging levels that the player can’t easily get stuck in, something much more valuable than realism when it comes to making an immersive experience. [@Yu2016] 



![Beispiellevel aus Spelunky mit kritischem Pfad-Layout (rot) und Raum Nummerierung \label{spelunkylevel}[@Kezemi]](figs/chapter3/spelunky.png)





### Analyse 



# Edgar-DotNet 

Zwar kann das Layout eines Level mithilfe eines Graphen dargestellt werden, schlussendlich muss aus diesen Graphen aber eine physische Anordnung von Räumen und Strukturen generiert werden, die dann das eigentliche Level im Spiel ist. Die Arbeit von Ma et al stellt einen effizienten Algorithmus vor, um diese Umwandlung durchzuführen. [@Ma2014] 

- Als input gibt es einen planaren Graphen und ein set aus 2D polygonale blöcke/räume

- Aufteilen des Graphen in Subgraphen (Chains) da diese einfacher zu lösen sind

  - zerbricht das komplexe Problem in viele kleine

  - in der chain hat jeder knoten max zwei kinder

  - kreise werden zuerst aufgelöst, weil sie mehr Bedingungen haben => schwerer zu lösen, wenn die erst spät erstellt werden muss man vermutlich viel backtracken

  - Wie

    - faces (cylces) finden (Faces of a planar graph are **regions bounded by a set of edges and which contain no other vertex or edge**)
    - erste chain ist das kleinste face
    - dann weitere Faces in breadth-first search hinzufüge
    - Wenn keine faces mehr da, alle acyclical components hinzufügen

    

- Erste Chain lösen

  - ein Block wird fest positioniert, dann werden die nächsten blöcke mithilfe von bewegung angeselegt bis chain gelöst ist
    - "For a pair of polygons, one fixed and one free, a configuration space is a set of such positions of the free polygon that the two polygons do not overlap and can be connected by doors. "
      - Grafik mit den Roten Linien, wenn der referenz Punkt des bewegbaren Polygon auf der Linie ist, ist es gültig => configuration space
      - wenn mehrere Feste blöcke existieren, für jeden Blck den configuration space zeichnen die überlappungen sind gültige punkte
  - evtl. backtracking falls es keine lösung gibt
  - mehrere Lösungen erstellen

- Nächste chain anlegen (Incemental)

  - nicht chains einzeln lösen,
    -  weil das lange dauert und ggf. dann die einzelnen lösungen nicht kompatibel sind
    - im dungeon sind die konstrukte eh alle miteinander verbunden, daher gibt es auch keine vorteile die chains einzeln zu lösen
  - evtl. backtracking falls es keine lösung gibt und andere Lösung vom vorschritt nehmen
  - mehrere Lösungen erstellen

- Repeat until done

- Vorteil daran mehrere Lösungen zu bauen: 1. backtracking 2. es können schneller mehrere Lösungen generiert werden, ich änder einfach in der Mitte die Lösung der Chain und guck was passiert

- optimierierung mit simulated annealing (glaub das sprengt den rahmen aber erwähnen kann man das mal)





**Evtl brauch ich edgar gar nit**

Edgar-DotNet ist eine Implementierung und Weiterentwicklung dieses Algorithmus in DotNet. [@Nepozitek2018] Es verbessert  

Edagar

- Conference-Paper [@Nepozitek2018]
- Blog: [@Nepozitek2018a], [@Nepozitek2019]









## Cycle Dungeon

https://ctrl500.com/tech/handcrafted-feel-dungeon-generation-unexplored-explores-cyclic-dungeon-generation/

## Fast generation of planar graphs

https://users.cecs.anu.edu.au/~bdm/papers/plantri-full.pdf

## Graph of Game Worlds

https://folk.idi.ntnu.no/alfw/publications/gwg-acm-cie-2012.pdf

## Vergleich der Algorithmen

- als Matrix?

