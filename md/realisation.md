# Realisierung

<!--
*   Beschreibung der Umsetzung des Lösungskonzepts
*   Darstellung der aufgetretenen Probleme sowie deren Lösung bzw. daraus resultierende Einschränkungen des Ergebnisses (falls keine Lösung)
*   Auswertung und Interpretation der Ergebnisse
*   Vergleich mit der ursprünglichen Zielsetzung (ausführlich): Was wurde erreicht, was nicht (und warum)? (inkl. Begründung/Nachweis)

geschätzer umfang ca 20%
-->

## Einleitung
- nur ausschnitte werden gezeigt 

## Projekt Konfiguration
- Build Script und targets
- github workflows/settings
- github actions
- package struktur

## Umsetzung GraphG

Abbildung \ref{grapgUML} zeigt ein reduziertes UML-Klassendiagramm für den Aufbau von GraphG. Der Baustein besteht aus drei Klassen. `GraphG` nimmt die Parameter entgegen, führt die Breitensuche druch und gibt die Lösungen zurück und liefert möglichkeiten zum Abspeichern und Laden der Graphen in und aus JSON-Datein. Die Klasse `Graph` hält eine Liste mit Knoten und bietet funktionen um zu prüfen ob Knoten miteinander verbunden werden können ohne gegen die Bedingungen zu verstoßen. Die Klasse `Node` stellt einen Knoten im Graphen dar und hält eine Liste mit den Nachbarknoten. Diese Liste speichert Integer Werte ab und nicht die Knoten selber. Die Werte entsprechen dem Index des Nachbarknoten in der Liste des Graphen. Jeder Knoten kennt seinen eigenen Index. Dies ermöglicht einen einfacheren Kopiervorgang der Graphen und Knoten. Beim Kopieren  der Graphen muss die Liste der Knoten kopiert werden. Dabei müssen auch Kopien der Knoten gemacht. Sollte nur Referenzen auf die Instanzen kopiert werden, würden neue Nachbarn die einen Knoten hinzugefügt werden in jeder Kopie des Graphen hinzugefügt, die Kopien der Graphen wären daher zu jedem Zeitpukt identisch, die Graphen sollen sich jedoch voneinander unterscheiden und seprat betrachtet werden können. Beim Kopieren der Knoten müssen auch Kanten, also die Liste der Nachbarn, kopiert werden. Würde in der Liste eine referenz auf die Knoten Instanzen gespeichert werden, müsste beim kopieren eines Knoten für jeden Nachbarn die Kopie gefunden und abgespeichert werden. Werden nur die Indexe der Nachbarn in der Liste des Graphen gespeichert, reicht eine exakte Kopie der Nachbarsliste. Die Indexe beziehen sich dann nicht mehr auf die Knotenliste des ursprünglichen Graphen sondern auf die Knotenliste der Kopie des Graphen. Codesnippet **TODO** zeigt den Kopiervorgang eines Graphen und dessen Knoten. 

```java
//in Graph
Graph copy(){
    List<Node> nodesCopy = new ArrayList<>();
        nodes.forEach(n -> nodesCopy.add(n.copy()));
        for (Node n : nodes) {
            for (Integer nb : n.getNeighbours()) {
                Node n1 = nodesCopy.get(n.getIndex());
                n1.connect(nodesCopy.get(nb));
            }
        }
        return new Graph(nodesCopy);
}
//in Node
Node copy(){
    Node copy = new Node();
        copy.setIndex(getIndex());
        return copy;
}
```

Codesnippet **TODO** zeigt wie geprüft wird, ob Knoten miteinander verbunden werden können oder gegen die Bedingung zu verstoßen. `Graph#canConnect(Node n)` prüft ob an den existierenden Knoten `n` ein neuer Knoten hinzugefügt werden kann. Dafür wird zuerst eine Liste `manyNeighbour` erstellt, in der jeder Knoten im Graphen hinzugefügt wird, der mehr als zwei Nachbarn hat. Dannach wird überprüft ob `manyNeighbours` die maximale größe von vier Knoten bereits erreicht hat, ist dies nicht der Fall kann eine Verbindung erstellt werden. Sollte die Liste voll sein und der Knoten `n` bereits in der Liste vorhanden sein, kann auch eine Verbindung erstellt werden. Ist die Liste voll und Knoten `n` ist noch nicht in der Liste, würde aber auch mit einen neuen Nachbarn nicht in die Liste aufgenomemn werden, kann auch eine Verbindung erstellt werden. Ansonsten kann keine Verbindung erstellt werden. 

`Graph#canConnect(Node n1, Node 2)` prüft ob zwei bereits existierende Knoten miteinander verbunden werden können ohne gegen die Bedingung zu verstoßen. Es wird wieder die Liste `manyNeighbours` mit allen Knoten die mehr als zwei Nachbarn haben angelegt. Jetzt wird für beide Knoten seperat geprüft ob diese mit einer neuen Verbindung in die Liste aufgenommen werden müssten, dies ist der Fall wenn sie mit der neuen Verbindung mehr als zwei Nachbarn hätten und noch nicht in der Liste eingetragen sind. Hat die Liste noch platz für die Knoten die hinzugefügt werden müssen, dann ist die Verbindung erlaubt, sollte die Liste überfüllt werden, kann keine Verbindung stattfinden. 

```java
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
    
```

Codesnippet *TODO* zeigt die Methode `GraphG#calculateTrees`. Die Funktionsweise wurde bereits im vorherigen Kapitel erläutert, daher soll hier der Fokus nur auf Zeile 7 und 8 liegen. Zeile 7 kopiert mithilfe der oben beschriebenen Methode den aktuell betrachteten Graphen und Zeile 8 nutzt die Methode `Graph#connectNewNode(int index)` um den betrachteten Knoten mit einen neuen Knoten zu verbinden. Die Methode gibt `true` zurück wenn eine Verbindung erstellt wurde und `false` wenn keine Verbindung erstellt werden kann. Kann eine Verbindung erstellt werden, wird der kopierte Graph in der neuen Liste aufgenommen und wird im nächsten durchlauf der Methode als neuer "orginal" Graph betrachtet. Anzumerken ist, dass in Zeile 8 der Index des Orginalen Knoten `n` genutzt wird, um die Kopie des Knoten in `newTree` anzusprechen. 

```java
private List<Graph> calculateTrees(List<Graph> trees, int nodesLeft) {
        if (nodesLeft <= 0) return trees;
        else {
            List<Graph> newTrees = new ArrayList<>();
            for (Graph t : trees)
                for (Node n : t.getNodes()) {
                    Graph newTree = t.copy();
                    if (newTree.connectNewNode(n.getIndex())) newTrees.add(newTree);
                }
            return calculateTrees(newTrees, nodesLeft - 1);
        }
    }
```
Codesnippet *TODO* zeigt die Funktionsweise der Methode `GraphG#calculateGraphs`.  Die Funktionsweise wurde bereits im vorherigen Kapitel erläutert, daher soll hier der Fokus nur auf Zeile 6 bis 11 liegen. In Zeile 6 wird über jeden Knoten im Graphen  `g` iteriert, `g` ist dabei keine Kopie eines Graphen sondern stammt direkt aus der Liste die übergeben wird. In Zeile 7 wird nun auch über jeden Knoten in `g` iteriert und in Zeile 8 wird `g` kopiert. In Zeile 9 wird geprüft ob `n1` und `n2` nicht identisch sind und in Zeile 10 wird versucht eine Verbindung zwischen den Kopien der Knoten `n1` und `n2` herzustellen, wenn dies gelingt wird in Zeile 11  der kopierte Graph in die neue Liste aufgenommen. 

```java
  private List<Graph> calculateGraphs(List<Graph> graphs, int edgesLeft) {
        if (edgesLeft <= 0) return graphs;
        else {
            List<Graph> newGraphs = new ArrayList<>();
            for (Graph g : graphs)
                for (Node n1 : g.getNodes())
                    for (Node n2 : g.getNodes()) {
                        Graph newGraph = g.copy();
                        if (n1.getIndex() != n2.getIndex()
                                && newGraph.connectNodes(n1.getIndex(), n2.getIndex())) {
                            newGraphs.add(newGraph);
                        }
                    }
            return calculateGraphs(newGraphs, edgesLeft - 1);
        }
    }
```



![UML-Klassendiagramm für GraphG mit den wichtigsten Methoden. \label{graphgUML}](figs/chapter4/graphgUML.png)

Da GraphG alle gültigen Lösungen für die Parameterkombination aus Knoten und Kanten sucht, kommt es bei Kombinationen die viele unterschiedliche Lösungen erlauben zu einem `java.lang.OutOfMemoryError: Java heap space` Fehler. Dieser tritt immer dann auf, wenn der, Java zur Verfügung gestellte, Heap keinen freien Speicher mehr hat. In diesem Konkreten Fall tritt er auf, wenn die Liste mit den (Teil)-Lösungen zu groß wird. Es gibt zwei Möglichkeiten diesen Fehler zu beheben ohne die funktionalität von GraphG zu beeinflussen.

Möglichkeit 1 ist es, den Java zur Verfügung gestellten Heap zu vergrößern. Dies ist jedoch eine locale Lösung, welche dann von jedem Anwender durchgeführt werden müssten und ist außerdem von der Umgebung auf der GraphG läuft abhängig (Betriebssystem und verfügbarer RAM). Außerdem kann der Bedarf an Speicher ins unendliche Skalieren, jenachdem wie groß die Graphen werden. 

Möglichkeit 2 wäre es den Algorithmus umzuschreiben. Aktuell werden auch zueinander Isomorphe Graphen als eigenständige Lösung betrachtet. Aus Strukturell gleichen Graphen werden wiederum strukturell gleiche neue Graphen generiert. Der Speicherbedarf könnte drastisch sinken, wenn zueinander isomorphe Graphen entdeckt werden und nur einer davon im weitern Verlauf betrachtet und gespeichert wird. Festzustellen ob zwei Graphen zueinander isomorph sind, ist jedoch ein NP, und vielleicht sogar ein NP-Vollständiges, Problem. **TODO QUELLE**  

Um den Zeitlichen Rahmen dieser Arbeit einhalten zu können, wird keiner der beiden Möglichkeiten umgesetzt. Es wird ein Grenzwert definiert der die maximal betrachteten Graphen zu einem Zeitpunkt angibt. Sollte die Liste mit den betrachteten Graphen den Schwellwert überschreiten, wird die Liste beim nächsten rekursiven Aufruf wieder verkleinert, indem solange zufällige Teillösungen aus der Liste entfernt werden bis der Schwellwert erreicht ist. Dies schränkt GraphG so ein, dass nicht mehr alle Lösungen gefunden werden. Für Suchräume mit wenigen gültigen (Teil-)Lösungen sollten dennoch eine Vielzahl an unterschiedlichen Graphen gefunden werden. Für Suchräumen mit vielen gültigen Graphen könnten sich, je nach Größe des Suchraumes und Anzahl der Lösungen, die gefunden Graphen stark ähneln. 

Abbildungen ... ,.. , ... ,... ,... ,... **TODO** zeigen verschiedene von GraphG generierte Graphen mit unterschiedlicher Knoten und Kanten Anzahl. **TODO** manche sehen besser aus, andere irgendwie nit.  

![Von GraphG erzeugter Graph mit 7 Knoten und 3 Extrakanten \label{ex1}](figs/chapter4/graphgsol/example1.PNG)
![Von GraphG erzeugter Graph mit 7 Knoten und 3 Extrakanten \label{ex2}](figs/chapter4/graphgsol/example2.PNG)
![Von GraphG erzeugter Graph mit 7 Knoten und 3 Extrakanten \label{ex3}](figs/chapter4/graphgsol/example3.PNG)
![Von GraphG erzeugter Graph mit 7 Knoten und 3 Extrakanten \label{ex4}](figs/chapter4/graphgsol/example4.PNG)


## Umsetzung RoomG

## Umsetzung LevelG

## Anbindung an das PM-Dungeon

## Implementieren der Schnittstellen

# Evaluierung 

- Zeigen von Ergebnissen

- Auswerten der Ergebnisse

  
  
  
  
  
  
  