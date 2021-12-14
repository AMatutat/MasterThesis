# Realisierung

## Einleitung
- nur ausschnitte werden gezeigt 

## Projekt Konfiguration

Um eine reibungslose Integration in das PM-Dungeon-Framework zu ermöglichen, wurde für das Projekt die Projektkonfigurationen des Frameworks übernommen. 

- Das Buildtool Gradle wird genutzt, um das Projekt zu bauen. Alle externen Libraries werden als dependencies hinzugefügt.
- Der google-java-formater wird genauso wie im Framework konfiguriert und genutzt, um eine einheitlichen Codestil zu gewährleisten.
- Die Package-Struktur des Frameworks wurde übernommen.
- Das für den Generator genutzte Git-Repository wird mithilfe von GitHub-Actions so konfiguriert, dass es dem PM-Dungeon-Repository gleicht. Dies bedeutet, dass Code nur dann gemerged werden kann, wenn der Codestil eingehalten ist, alle JUnit-Testfälle erfolgreich durchlaufen und das Tool Spot-Bugs keine Antipattern im Code finden kann oder die gefundenen Antipattern bewusst akzeptiert werden. Sollten Antipattern bewusst im Code gelassen werden, muss diese Entscheidung dokumentiert und nachvollziehbar begründe werden.

Die konkrete Konfiguration des GitHub-Actions kann im Repository auf GitHub **TODO footnote** oder im Anhang **todo** eingesehen werden. 

## Umsetzung GraphG

Abbildung \ref{graphgUML} zeigt ein reduziertes UML-Klassendiagramm für den Aufbau von GraphG. Der Baustein besteht aus drei Klassen. `GraphG` nimmt die Parameter entgegen, führt die Breitensuche durch und gibt die Lösungen zurück und liefert Möglichkeiten zum Abspeichern und Laden der Graphen in und aus JSON-Dateien. Die Klasse `Graph` hält eine Liste mit Knoten und bietet Funktionen, um zu prüfen, ob Knoten miteinander verbunden werden können, ohne gegen die Bedingungen zu verstoßen. Die Klasse `Node` stellt einen Knoten im Graphen dar und hält eine Liste mit den Nachbarknoten. Diese Liste speichert Integer Werte ab und nicht die Knoten selber. Die Werte entsprechen dem Index der Nachbarknoten in der Liste des Graphen. Jeder Knoten kennt seinen eigenen Index. Dies ermöglicht einen einfacheren Kopiervorgang der Graphen und Knoten. Beim Kopieren  der Graphen muss die Liste der Knoten kopiert werden. Dabei müssen auch Kopien der Knoten gemacht werden. Sollten nur Referenzen auf die Instanzen kopiert werden, würden neue Nachbarn, die einem Knoten hinzugefügt werden, in jeder Kopie des Knotens und somit des Graphen hinzugefügt werden. Die Kopien der Graphen wären daher zu jedem Zeitpunkt identisch, die Graphen sollen sich jedoch voneinander unterscheiden und separat betrachtet werden können. Beim Kopieren der Knoten müssen auch Kanten, also die Liste der Nachbarn, kopiert werden. Würde in der Liste eine Referenz auf die Knoten Instanzen gespeichert werden, müsste beim Kopieren eines Knotens für jeden Nachbarn die Kopie gefunden und abgespeichert werden. Werden nur die Indexe der Nachbarn in der Liste des Graphen gespeichert, reicht eine exakte Kopie der Nachbarliste. Die Indexe beziehen sich dann nicht mehr auf die Knotenliste des ursprünglichen Graphen, sondern auf die Knotenliste der Kopie des Graphen. Listing \ref{copy} zeigt den Kopiervorgang eines Graphen und dessen Knoten. 

\begin{lstlisting}[language=python, label=copy, caption={Kopieren von Graphen und Kanten.}  ]
//in Graph
Graph (Graph g){
        g.getNodes().forEach(n -> nodes.add(new Node(n)));
        for (Node n : g.getNodes()) {
            for (Integer nb : n.getNeighbours()) {
                Node n1 = nodes.get(n.getIndex());
                n1.connect(nodes.get(nb));
            }
        }
}
//in Node
Node (Node n){
   setIndex(n.getIndex());
}
\end{lstlisting}

Listing \ref{cc} zeigt wie geprüft wird, ob Knoten miteinander verbunden werden können oder gegen die Bedingung zu verstoßen. `Graph#canConnect(Node n)` prüft, ob an den existierenden Knoten `n` ein neuer Knoten hinzugefügt werden kann. Dafür wird zuerst eine Liste `manyNeighbour` erstellt, in der jeder Knoten im Graphen hinzugefügt wird, der mehr als zwei Nachbarn hat. Danach wird überprüft, ob `manyNeighbours` die maximale Größe von vier Knoten bereits erreicht hat, ist dies nicht der Fall kann eine Verbindung erstellt werden. Sollte die Liste voll sein und der Knoten `n` bereits in der Liste vorhanden sein, kann auch eine Verbindung erstellt werden. Ist die Liste voll und Knoten `n` ist noch nicht in der Liste, würde aber auch mit einem neuen Nachbarn nicht in die Liste aufgenommen werden, kann auch eine Verbindung erstellt werden. Ansonsten kann keine Verbindung erstellt werden. 

`Graph#canConnect(Node n1, Node 2)` prüft, ob zwei bereits existierende Knoten miteinander verbunden werden können, ohne gegen die Bedingung zu verstoßen. Es wird wieder die Liste `manyNeighbours` mit allen Knoten die mehr als zwei Nachbarn haben angelegt. Jetzt wird für beide Knoten separat geprüft, ob diese mit einer neuen Verbindung in die Liste aufgenommen werden müssten, dies ist der Fall, wenn sie mit der neuen Verbindung mehr als zwei Nachbarn hätten und noch nicht in der Liste eingetragen sind. Hat die Liste noch Platz für die Knoten, die hinzugefügt werden müssen, dann ist die Verbindung erlaubt, sollte die Liste überfüllt werden, kann keine Verbindung stattfinden. 

\begin{lstlisting}[language=python, label=cc, caption={Prüfen ob Knoten und Kanten hinzugefügt werden können.}]

	private boolean canConnect(Node n) {
	    List<Node> manyNeighbour = new ArrayList<>(nodes);
		//MAX_NEIGHBOURS=2
	    manyNeighbour.removeIf(node -> node.getNeighbours().size() <= MAX_NEIGHBOURS);
		//MAX_NODES=4
	    if (manyNeighbour.size() <= MAX_NODES
	            || manyNeighbour.contains(n)
	            || n.getNeighbours().size() + 1 <= MAX_NEIGHBOURS) return true;
	    else return false;
	}
	
	private boolean canConnect(Node n1, Node n2) {
	    List<Node> manyNeighbour = new ArrayList<>(nodes);
	    manyNeighbour.removeIf(node -> node.getNeighbours().size() <= MAX_NEIGHBOURS);
	    boolean addN1 =
	            (n1.getNeighbours().size() >= MAX_NEIGHBOURS && !manyNeighbour.contains(n1));
	    boolean addN2 =
	            (n2.getNeighbours().size() >= MAX_NEIGHBOURS && !manyNeighbour.contains(n2));
	
	    return (manyNeighbour.size() + (addN1 ? 1 : 0) + (addN2 ? 1 : 0) < MAX_NODES);
	}

\end{lstlisting}

Listing \ref{trees} zeigt die Methode `GraphG#calculateTrees`. Die Funktionsweise wurde bereits im vorherigen Kapitel erläutert, daher soll hier der Fokus nur auf Zeile 7 und 8 liegen. Zeile 7 kopiert mithilfe der oben beschriebenen Methode den aktuell betrachteten Graphen und Zeile 8 nutzt die Methode `Graph#connectNewNode(int index)`, um den betrachteten Knoten mit einem neuen Knoten zu verbinden. Die Methode gibt `true` zurück, wenn eine Verbindung erstellt wurde und `false` wenn keine Verbindung erstellt werden kann. Kann eine Verbindung erstellt werden, wird der kopierte Graph in der Liste der Teillösungen aufgenommen. Anzumerken ist, dass in Zeile 8 der Index des originalen Knotens `n` genutzt wird, um die Kopie des Knotens `n` in `newTree` anzusprechen. 

\begin{lstlisting}[language=python, label=trees, caption={Breitensuchen nach allen gültigen Bäumen}]
	private List<Graph> calculateTrees(List<Graph> trees, int nodesLeft) {
        if (nodesLeft <= 0) return trees;
        else {
            List<Graph> newTrees = new ArrayList<>();
            for (Graph t : trees)
                for (Node n : t.getNodes()) {
                    Graph newTree = new Graph(tree);
                    if (newTree.connectNewNode(n.getIndex())) newTrees.add(newTree);
                }
            return calculateTrees(newTrees, nodesLeft - 1);
        }
    }
\end{lstlisting}

Listing \ref{graphs} zeigt die Funktionsweise der Methode `GraphG#calculateGraphs`.  Die Funktionsweise wurde bereits im vorherigen Kapitel erläutert, daher soll hier der Fokus nur auf Zeile 6 bis 11 liegen. In Zeile 6 wird über jeden Knoten im Graphen  `g` iteriert, `g` ist dabei keine Kopie eines Graphen, sondern stammt direkt aus der Liste, die übergeben wird. In Zeile 7 wird nun auch über jeden Knoten in `g` iteriert und in Zeile 8 wird `g` kopiert. In Zeile 9 wird geprüft ob `n1` und `n2` nicht identisch sind und in Zeile 10 wird versucht eine Verbindung zwischen den Kopien der Knoten `n1` und `n2` herzustellen, wenn dies gelingt wird in Zeile 11  der kopierte Graph in die Liste der Teillösungen aufgenommen. 

\begin{lstlisting}[language=python, label=graphs, caption={Breitensuchen nach allen gültigen Graphen.}]
  private List<Graph> calculateGraphs(List<Graph> graphs, int edgesLeft) {
        if (edgesLeft <= 0) return graphs;
        else {
            List<Graph> newGraphs = new ArrayList<>();
            for (Graph g : graphs)
                for (Node n1 : g.getNodes())
                    for (Node n2 : g.getNodes()) {
                        Graph newGraph = new Graph(g);
                        if (n1.getIndex() != n2.getIndex()
                                && newGraph.connectNodes(n1.getIndex(), n2.getIndex())) {
                            newGraphs.add(newGraph);
                        }
                    }
            return calculateGraphs(newGraphs, edgesLeft - 1);
        }
    }
\end{lstlisting}


![UML-Klassendiagramm für GraphG mit den wichtigsten Methoden. \label{graphgUML}](figs/chapter4/graphgUML.png)

Da GraphG alle gültigen (Teil-)Lösungen für die Parameterkombination aus Knoten und Kanten sucht, kommt es bei Kombinationen, die viele unterschiedliche (Teil-)Lösungen erlauben, zu einem `java.lang.OutOfMemoryError: Java heap space` Fehler. Dieser tritt immer dann auf, wenn der, Java zur Verfügung gestellte, Heap keinen freien Speicher mehr hat. In diesem konkreten Fall tritt er auf, wenn die Liste mit den (Teil)-Lösungen zu groß wird. Es gibt zwei Möglichkeiten diesen Fehler zu beheben, ohne die Funktionalität von GraphG zu beeinflussen.

Möglichkeit 1 ist es, den Java zur Verfügung gestellten Heap zu vergrößern. Dies ist jedoch eine lokale Lösung, welche dann von jedem Anwender durchgeführt werden müssten und ist außerdem von der Umgebung auf der GraphG läuft abhängig (Betriebssystem und verfügbarer RAM). Außerdem kann der Bedarf an Speicher ins Unendliche skalieren, je nachdem wie groß die Graphen werden. 

Möglichkeit 2 wäre es den Algorithmus umzuschreiben. Aktuell werden auch zueinander isomorphe Graphen als eigenständige (Teil-)Lösung betrachtet. Aus strukturell gleichen Teillösungen werden wiederum strukturell gleiche neue (Teil)-Lösungen generiert. Der Speicherbedarf könnte drastisch sinken, wenn zueinander isomorphe Teillösungen entdeckt werden und nur einer davon im weiteren Verlauf betrachtet und gespeichert wird. Festzustellen, ob zwei Graphen zueinander isomorph sind, ist jedoch ein NP-Vollständiges Problem.[@Cook1971]

Um den zeitlichen Rahmen dieser Arbeit einhalten zu können, wird keiner der beiden Möglichkeiten umgesetzt. Es wird ein Grenzwert von 1000 definiert, der die maximal betrachteten Teillösungen zu einem Zeitpunkt angibt. Sollte die Liste mit den Teillösungen den Schwellwert überschreiten, wird die Liste beim nächsten rekursiven Aufruf wieder verkleinert, indem so lange zufällige Teillösungen aus der Liste entfernt werden, bis der Schwellwert erreicht ist. Dies schränkt GraphG so ein, dass nicht mehr alle Lösungen gefunden werden. Für Suchräume mit wenigen gültigen (Teil-)Lösungen sollten dennoch eine Vielzahl an unterschiedlichen Graphen gefunden werden. Für Suchräumen mit vielen gültigen Graphen könnten sich, je nach Größe des Suchraumes und Anzahl der (Teil-)Lösungen, die gefunden Graphen stark ähneln. 

Abbildungen \ref{ex1}, \ref{ex2}, \ref{ex3} und \ref{ex4} zeigen von GraphG generierte Graphen mit unterschiedlicher Kanten und Knotenanzahl. Die Graphen wurden zufällig ausgewählt, um die mögliche Variation zu zeigen. Im Kapitel Evaluierung werden die Graphen auf ihre Qualität als Level-Graph analysiert und bewertet. 

## Umsetzung RoomG

Abbildung \ref{roomgUML} zeigt ein reduziertes UML-Klassendiagramm für RoomG. 

Die Klasse `RoomLoader` lädt alle abgespeicherten Room-Templates aus einer Json und speichert diese ab. Mit der Methode `getRoomTemplate` kann eine Liste mit allen Templates abgefragt werden, die das übergebene Design-Label haben. `DesignLabel` ist ein Enum mit verschiedenen Designs. Sollen alle Templates abgefragt werden, kann `DesignLabel.ALL` verwendet werden.

Die Klasse `ReplacementLoader` lädt alle abgespeicherten Replacements aus einer Json und speichert diese ab. Dabei prüft der Loader, ob die `rotate`-Flag eines Replacments gesetzt ist, und wenn ja, erstellt er drei neue Replacments mit einem um jeweils 90,180 und 270 Grad rotierten Layout. So können Replacements auch in verschiedenen Positionen eingesetzt werden. Mit der Methode `getReplacements` kann eine Liste mit allen Replacements abgefragt werden, die das übergebene Design-Label haben. Sollen alle Replacements abgefragt werden, kann `DesignLabel.ALL` übergeben werden.

![UML-Klassendiagramm für RoomG mit den wichtigsten Methoden. \label{roomgUML}](figs/chapter4/roomg.png)

Die Klassen `Replacement` und `RoomTemplate` speichern jeweils ein Layout und ein Design-Label. Der Ersetzungsprozess findet in `RoomTemplate#replace` statt und wird in Listing **TODO** gezeigt. 

Da ein Template mehrfach verwendet werden soll, wird in Zeile 1 eine Kopie des Layouts erstellt. Die Ersetzung wird in dieser Kopie durchgeführt. In Zeile 2 und 3 wird die Größe des Layouts abgefragt, um späteres Iterieren zu vereinfachen. Genau wie das Template soll auch die Liste mit den möglichen Replacements mehrfach verwenden werden können, daher wird in Zeile 6 eine Kopie der übergebenen Liste erstellt. In Zeile 7 bis 10 werden alle Replacements aus der Liste gelöscht, die zu groß für das Layout sind, also über den Rand des Raumes herausragen würden. In Zeile 12 bis 24 findet der eigentliche Ersetzungsprozess statt. Die äußere Schleife in Zeile 14 bis 24 sorgt dafür, dass im Falle einer Änderung des Layouts der gesamte innere Prozess erneut durchgeführt wird. Der innere Prozess in Zeile 16 bis 23 iteriert für jedes Replacment über das Layout und such nach einer Wildcard. Dabei muss nicht das gesamte Layout betrachtet, sondern nur die Punkte, bei dem das Einfügen der Replacement nicht dafür sorgen würde, dass  das Replacement über den Raumrand hinausragt. Daher kann in Zeile 19 und 20 die Abbruchbedingung der Zählergesteuerten-Schleifen angepasst werden. In Zeile 21 wird geprüft, ob das aktuelle betrachtete Feld eine Wildcard ist und in Zeile 22 wird versucht diese Wildcard mit dem Replacement zu ersetzen. Die Funktionsweise von `placeIn` wird weiter unten erläutert. Ist der Ersetzungsprozess erfolgreich, muss nach Abschluss der foreach-Schleife in Zeile 16 der gesamte Prozess wiederholt werden, um möglicherweise neu eingesetzte Wildcards zu ersetzen. In Zeile 26 bis 30 werden alle verbliebenen Wildcards durch Bodenfelder ersetzt. In Zeile 30 erstellt die Methode ein neues Objekt der Klasse `Room` mit dem ersetzten Layout. Dieser Raum ist bereit, um im PM-Dungeon verwendet zu werden. 

```
0    public Room replace(List<Replacement> replacements) {
1		LevelElement[][] roomLayout = copy(this.layout);        		
2        int layoutWidth = getLayout()[0].length;
3        int layoutHeight = getLayout().length;
4
5       // remove all replacements that are too big
6        List<Replacement> replacementList = new ArrayList<>(replacements);
7        for (Replacement r : replacements) {
8            if (r.getLayout()[0].length <= layoutWidth && r.getLayout().length <= layoutHeight)
9                replacementList.add(r);
10        }
11    
12        // replace with replacements
13        boolean changes;
14        do {
15            changes = false;
16            for (Replacement r : replacementList) {
17                int rHeight = r.getLayout().length;
18                int rWidth = r.getLayout()[0].length;
19                for (int y = 0; y < layoutHeight - rHeight; y++)
20                    for (int x = 0; x < layoutWidth - rWidth; x++)
21                        if (roomLayout[y][x] == LevelElement.WILDCARD
22                                && placeIn(roomLayout, r, x, y)) changes = true;
23            } 
24        } while (changes);
25    
26        // replace all placeholder that are left with floor
27        for (int y = 0; y < layoutHeight; y++)
28            for (int x = 0; x < layoutWidth; x++)
29                if (roomLayout[y][x] == LevelElement.WILDCARD)
30                    roomLayout[y][x] = LevelElement.FLOOR;
31        return new levelg.Room(roomLayout, getDesign());
32    }
```

Listing **ToDo** zeigt die Methode `placIn`. Die Methode bekommt ein Layout übergeben in den das Replacment `r` so eingesetzt werden soll, dass die obere linke Ecke vom `r` in `layout[yCor][xCor]` eingesetzt wird. Ist der Prozess erfolgreich, gibt die Methode `true` zurück, ansonsten `false`. In Zeile 1 prüft die Methode mithilfe von `canReplaceIn` ob `r` in `layout` an der Stelle `[ycor][xcor]` passt. 

Listing **TODO** zeigt die Methode `canReplaceIn`. Die Methode iteriert durch die Zeilen 2 und 3 über das Layout ab der Stelle `[yCor][xCor]` bis zu der Stelle an der das Replacement enden würde, wenn es eingesetzt wird. Für jedes Feld, das dabei betrachtet wird, wird in Zeile 6 und 7 geprüft, ob das Replacement an der Stelle ein Feld ersetzen wollen würde und ob an dieser Stelle im Layout eine Wildcard ist. Möchte das Replacement ein Feld ersetzten an der im Layout keine Wildcard ist, kann keine Ersetzung durchgeführt werden und die Methode gibt `false` zurück. Sollte an jeder zu ersetzenden Stelle auch ein Placeholder sein, gibt die Methode `true` zurück. 

Ist eine Ersetzung möglich, iteriert `placeIn` genauso wie `canReplaceIn` durch Zeile 4 und 5 über das Layout und führt in Zeile 6 und 7 an den jeweiligen Stellen eine Ersetzung durch. 

```
  0  private boolean placeIn(final LevelElement[][] layout, final Replacement r, int xCor, int yCor) {
  1      if (!canReplaceIn(layout, r, xCor, yCor)) return false;
  2     else {
  3          LevelElement[][] rlayout = r.getLayout();
  4          for (int y = yCor; y < yCor + rlayout.length; y++)
  5              for (int x = xCor; x < xCor + rlayout[0].length; x++) {
  6                  if (rlayout[y - yCor][x - xCor] != LevelElement.SKIP)
  7                      layout[y][x] = rlayout[y - yCor][x - xCor];
  8              }
  9          return true;
  10      }
  11  }
```


```
 0   private boolean canReplaceIn(LevelElement[][] layout, final Replacement r, int xCor, int yCor) {
 1       LevelElement[][] rlayout = r.getLayout();
 2       for (int y = yCor; y < yCor + rlayout.length; y++)
 3           for (int x = xCor; x < xCor + rlayout[0].length; x++) {
 4               if (rlayout[y - yCor][x - xCor] != LevelElement.SKIP
 5                       && layout[y][x] != LevelElement.WILDCARD) return false;
 6           }
 7       return true;
 8   }
```



Für diese Arbeit wurden verschiedene Layouts für Room-Templates und Replacments erstellt. Abbildungen **TODO** zeigen  verschiedene, von RoomG, erzeugte Räume und die dafür verwendeten Templates und Replacments. Im Kapitel Evaluierung wird die Vielfalt und Qualität der Räume analysiert und bewertet. 

## Umsetzung LevelG

## Anbindung an das PM-Dungeon und Schnittstellen

Die Anbindung an das Framework wurde wie konzeptioniert durchgeführt und bietet keine nennenswerten Aspekte und wird daher in diesem Abschnitt nicht weiter erläutert. Ebenso wurde die Graph-Search wie beschrieben umgesetzt und wird in diesem Abschnitt auch nicht weiter erläutert. In diesem Abschnitt werden verschiedene Code-Snippets erläutert um zu präsentieren, wie die Studierenden die Schnittstellen nutzen können, um ihr Spiel zu gestalten. Alle Code-Snippets sind so abstrahiert, dass kein weiteres Verständnis für das PM-Dungeon-Framework von Nöten ist.

Listing **TODO** zeigt wie ein Monster zufällige im Level platziert werden kann. In Zeile 0 wird das aktuelle Level abgefragt. In Zeile 1 wird ein zufälliges Bodenfeld aus einem zufälligen Raum im Level ausgewählt. In Zeile 2 wird dem Monster `dino` die Globale-Position des Tiles zugewiesen.  

```java
0 Level currentLevel = levelAPI.getCurrentLevel();
1 Tile randomTile = currentLevel.getRandomRoom().getRandomFloorTile();
2 dino.setPosition(randomTile.getGlobalPosition());
```

Listing **TODO** zeigt wie der Held auf dem Startpunkt des Level gesetzt werden kann. In Zeile 0 wird das aktuelle Level abgefragt. In Zeile 1 wird das als Startpunkt markierte Tile abgefragt. In Zeile 2 wird dem Helden die Position des Tiles zugewiesen. 

```java
0 Level currentLevel = levelAPI.getCurrentLevel();
1 Tile startTile = currentLevel.getStartTile();
2 hero.setPosition(starTile.getGlobalPosition());
```

Listing **TODO** zeigt wie ein Monster in einem kritischen Raum platziert werden kann. In Zeile 0 wird das aktuelle Level abgefragt. In Zeile 1 werden alle kritischen Knoten abgefragt. In Zeile 2 wird ein zufälliger Knoten aus allen kritischen Knoten ausgewählt. In Zeile 3 wird der Raum gespeichert, der den Knoten im Level repräsentiert. Der Index des Knoten in `Level.rooms` ist identisch mit dem passenden Index des Knoten in `Level.nodes`. In Zeile 4 wird ein zufälliges Bodenfeld aus dem Raum ausgewählt. In Zeile 5 wird dem Monster `evilDuck` die Position des Tiles zugewiesen. 

```java
0 Level currentLevel = levelAPI.getCurrentLevel();
1 List <Node> criticalNodes = currentLevel.getCriticalNodes();
2 Node randomNode = criticalNodes.get(new Random(criticalNodes.size()));
3 Room randomRoom = currentLevel.getRoomToNode(randomNode);
4 Tile randomTile = randomRoom.getRandomFloorTile();
5 evilDuck.setPosition(randomTile.getGlobalPosition());
```

Listing **TODO** zeigt wie eine Schatztruhe in einen optionalen Raum mit platziert werden kann. In Zeile 0 wird das aktuelle Level abgefragt. In Zeile 1 werden alle optionalen Knoten abgefragt. In Zeile 2 wird ein zufälliger Knoten aus allen optionalen Knoten ausgewählt. In Zeile 3 wird der Raum gespeichert, der den Knoten im Level repräsentiert. In Zeile 4 wird ein zufälliges Bodenfeld aus dem Raum ausgewählt. In Zeile 5 wird der Schatzkiste `bigLoot` die Position des Tiles zugewiesen. 

```java
0 Level currentLevel = levelAPI.getCurrentLevel();
1 List <Node> optionalNodes = currentLevel.getOptionalNodes();
2 Node randomNode = optionalNodes.get(new Random(optionalNodes.size()));
3 Room randomRoom = currentLevel.getRoomToNode(randomNode);
4 Tile randomTile = randomRoom.getRandomFloorTile();
5 bigLoot.setPosition(randomTile.getGlobalPosition());
```

Listing **TODO** zeigt wie geprüft werden kann, ob ein bestimmter Raum umgangen werden kann. In Zeile 0 wird das aktuelle Level abgefragt. In Zeile 1 und 2 werden sowohl der Start- als auch der Endknoten des Levels abgefragt. In Zeile 3 wird der kürzeste Pfad vom Start- bis zum Endknoten abgefragt. In Zeile 4 wird ein zufälliger Knoten aus diesem Pfad ausgewählt, dieser Knoten dient zur Demonstration und gilt es zu vermeiden. In Zeile 5 wird geprüft ob der ausgewählte Knoten auf dem Weg vom Start- bis zum Endknoten umgangen werden kann. In diesem Beispiel würde das bedeuten, das es mehrere Wege zum Ziel gibt. In Zeile 6 und 7 finden Ausgaben entsprechend der Ergebnisse statt. Diese dienen nur der Demonstration und könnten durch spezifische Game-Design Entscheidungen ersetzt werden. 

```java
0 Level currentLevel = levelAPI.getCurrentLevel();
1 Node start = currentLevel.getStartNode();
2 Node end = currentLevel.getEndNode();   
3 List<Node> path = currentLevel.getShortestPath(start,end);
4 Node toAvoid = path.get(new Random(path.size()));
5 if (currentLevel.isRoomReachableWithout(start,end,toAvoid))
6 System.out.println("YES!")
7 else System.out.println("OH NO!"));   
```

# Evaluierung 

- GraphG
  - Zeigen von Graphen
  - was machen die einzelenn Graphen gut, was schlecht
  - vermerk das die qualität als Level stark von der gewählten Knoten und Kanten anzahl abhängt

  - RoomG

      - zeigen von Templates und daraus erzeugten Räume
      - Unterscheiden die sich gut? 
      - Gibt es Probleme?
      - Ist halt doof das die dinger eingelesen werden müssen

  - LevelG

    

    

    

    

    