# Realisierung

In diesem Kapitel wird die konkrete, in Java umgesetzte, Implementierung des im vorherigen Kapitel dargestelltem Konzeptes vorgestellt. Der Aufbau des Kapitels orientiert sich am Aufbau des vorherigen Kapitels. Daher beginnt es mit einer Erläuterung der Projekt-Konfiguration, um eine reibungslose Integration in das Framework zu ermöglichen. Danach folgt die Vorstellung der Umsetzung der einzelnen Bausteine GraphG, RoomG und LevelG. Das Kapitel endet mit einem kurzen Überblick über die Integration in das Framework und die bereitgestellte Schnittstelle. Im Rahmen dieses Kapitels kann nicht auf jeden Aspekt der Umsetzung eingegangen werden, daher werden nur auf, vom Autor als besonders wichtig oder interessante eingeschätzte Aspekte Eingange. Es sollte dennoch ein guter Einblick über den Aufbau und Umsetzung von DungeonG entstehen. 

## Projekt Konfiguration

Um eine reibungslose Integration in das PM-Dungeon-Framework zu ermöglichen, wurde für das Projekt die Projektkonfigurationen des Frameworks übernommen. 

- Das Buildtool Gradle wird genutzt, um das Projekt zu bauen. Alle externen Libraries werden als Dependencies hinzugefügt.
- Der Google-Java-Formater wird genauso wie im Framework konfiguriert und genutzt, um einen einheitlichen Codestil zu gewährleisten.
- Die Package-Struktur des Frameworks wurde übernommen.
- Das Projekt wurde in in einen Branch des PM-Dungeon-Framework Repository implementiert. Dadurch konnte die selbe Toolchain und die selben GitHub-Actions wie das Framework verwendet werden und sichergestellt werden, dass diese immer auf den aktuellsten stand sind. Dies bedeutet, dass der Code nur dann gemerged werden kann, wenn der Codestil eingehalten ist, alle JUnit-Testfälle erfolgreich durchlaufen und das Tool Spot-Bugs keine Antipattern im Code finden kann oder die gefundenen Antipattern bewusst akzeptiert werden. Sollten Antipattern bewusst im Code gelassen werden, muss diese Entscheidung dokumentiert und nachvollziehbar begründet werden.

Die konkrete Konfiguration der GitHub-Actions kann im Anhang \ref{workflows} eingesehen werden. 

## Umsetzung GraphG

Abbildung \ref{graphgUML} zeigt ein reduziertes UML-Klassendiagramm für den Aufbau von GraphG. Der Baustein besteht aus drei Klassen. `GraphG` nimmt die Parameter entgegen, führt die Breitensuche durch und gibt die Lösungen zurück und liefert Möglichkeiten zum Abspeichern und Laden der Graphen in und aus JSON-Dateien. Die Klasse `Graph` hält eine Liste mit Knoten und bietet Funktionen, um zu prüfen, ob Knoten miteinander verbunden werden können, ohne gegen die Bedingungen zu verstoßen. Die Klasse `Node` stellt einen Knoten im Graphen dar und hält eine Liste mit den Nachbarknoten. Diese Liste speichert Integer Werte ab und nicht die Knoten selber. Die Werte entsprechen dem Index der Nachbarknoten in der Liste des Graphen. Jeder Knoten kennt seinen eigenen Index. Dies ermöglicht einen einfacheren Kopiervorgang der Graphen und Knoten. Beim Kopieren  der Graphen muss die Liste der Knoten kopiert werden. Dabei müssen auch Kopien der Knoten gemacht werden. Sollten nur Referenzen auf die Instanzen kopiert werden, würden neue Nachbarn, die einem Knoten hinzugefügt werden, in jeder Kopie des Knotens und somit des Graphen hinzugefügt werden. Die Kopien der Graphen wären daher zu jedem Zeitpunkt identisch, die Graphen sollen sich jedoch voneinander unterscheiden und separat betrachtet werden können. Beim Kopieren der Knoten müssen auch Kanten, also die Liste der Nachbarn, kopiert werden. Würde in der Liste eine Referenz auf die Knoten Instanzen gespeichert werden, müsste beim Kopieren eines Knotens für jeden Nachbarn die Kopie gefunden und abgespeichert werden. Werden nur die Indexe der Nachbarn in der Liste des Graphen gespeichert, reicht eine exakte Kopie der Nachbarliste. Die Indexe beziehen sich dann nicht mehr auf die Knotenliste des ursprünglichen Graphen, sondern auf die Knotenliste der Kopie des Graphen. Listing \ref{copy} zeigt den Kopiervorgang eines Graphen und dessen Knoten. 

\begin{lstlisting}[label=copy, caption={Kopieren von Graphen und Kanten.}  ]
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

Listing \ref{trees} zeigt die Methode `GraphG#calculateTrees`. Die Funktionsweise wurde bereits in Kapitel 4.3.1 erläutert, daher soll hier der Fokus nur auf Zeile 7 und 8 liegen. Zeile 7 kopiert mithilfe der oben beschriebenen Methode den aktuell betrachteten Graphen und Zeile 8 nutzt die Methode `Graph#connectNewNode(int index)`, um den betrachteten Knoten mit einem neuen Knoten zu verbinden. Die Methode gibt `true` zurück, wenn eine Verbindung erstellt wurde und `false` wenn keine Verbindung erstellt werden kann. Kann eine Verbindung erstellt werden, wird der kopierte Graph in der Liste der Teillösungen aufgenommen. Anzumerken ist, dass in Zeile 8 der Index des originalen Knotens `n` genutzt wird, um die Kopie des Knotens `n` in `newTree` anzusprechen. 

\begin{lstlisting}[language=python, label=trees, caption={Breitensuchen nach allen gültigen Bäumen.}]
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

Listing \ref{graphs} zeigt die Funktionsweise der Methode `GraphG#calculateGraphs`.  Die Funktionsweise wurde bereits in Kapitel 4.3.1 erläutert, daher soll hier der Fokus nur auf Zeile 6 bis 11 liegen. In Zeile 6 wird über jeden Knoten im Graphen  `g` iteriert, `g` ist dabei keine Kopie eines Graphen, sondern stammt direkt aus der Liste, die übergeben wird. In Zeile 7 wird nun auch über jeden Knoten in `g` iteriert und in Zeile 8 wird `g` kopiert. In Zeile 9 wird geprüft ob `n1` und `n2` nicht identisch sind und in Zeile 10 wird versucht eine Verbindung zwischen den Kopien der Knoten `n1` und `n2` herzustellen, wenn dies gelingt wird in Zeile 11  der kopierte Graph in die Liste der Teillösungen aufgenommen. 

\begin{lstlisting}[label=graphs, caption={Breitensuchen nach allen gültigen Graphen.}]
List<Graph> calculateGraphs(List<Graph> graphs, int edgesLeft) {
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

Möglichkeit 1 ist es, den Java zur Verfügung gestellten Heap zu vergrößern. Dies ist jedoch eine lokale Lösung, welche dann von jedem Anwender durchgeführt werden müsste und ist außerdem von der Umgebung auf der GraphG läuft abhängig (Betriebssystem und verfügbarer RAM). Außerdem kann der Bedarf an Speicher ins Unendliche skalieren, je nachdem wie groß die Graphen werden. 

Möglichkeit 2 wäre es den Algorithmus umzuschreiben. Aktuell werden auch zueinander isomorphe Graphen als eigenständige (Teil-)Lösung betrachtet. Aus strukturell gleichen Teillösungen werden wiederum strukturell gleiche neue (Teil)-Lösungen generiert. Der Speicherbedarf könnte drastisch sinken, wenn zueinander isomorphe Teillösungen entdeckt werden und nur einer davon im weiteren Verlauf betrachtet und gespeichert wird. Festzustellen, ob zwei Graphen zueinander isomorph sind, ist jedoch ein NP-Vollständiges Problem.[@Cook1971]

Um den zeitlichen Rahmen dieser Arbeit einhalten zu können, wird keiner der beiden Möglichkeiten umgesetzt. Es wird ein Grenzwert von 1000 definiert, der die maximal betrachteten Teillösungen zu einem Zeitpunkt angibt. Sollte die Liste mit den Teillösungen den Schwellwert überschreiten, wird die Liste beim nächsten rekursiven Aufruf wieder verkleinert, indem so lange zufällige Teillösungen aus der Liste entfernt werden, bis der Schwellwert erreicht ist. Dies schränkt GraphG so ein, dass nicht mehr alle Lösungen gefunden werden. Für Suchräume mit wenigen gültigen (Teil-)Lösungen sollten dennoch eine Vielzahl an unterschiedlichen Graphen gefunden werden. Für Suchräumen mit vielen gültigen Graphen könnten sich, je nach Größe des Suchraumes und Anzahl der (Teil-)Lösungen, die gefunden Graphen stark ähneln. 

Abbildungen \ref{graphex1}, \ref{graphex2}, \ref{graphex3} und \ref{graphex4} zeigen von GraphG generierte Graphen mit unterschiedlicher Kanten und Knotenanzahl. Die Graphen wurden zufällig ausgewählt, um die mögliche Variation zu zeigen. In Kapitel 6 werden die Graphen auf ihre Qualität als Level-Graph analysiert und bewertet. 

## Umsetzung RoomG

Abbildung \ref{roomgUML} zeigt ein reduziertes UML-Klassendiagramm für RoomG. 

Die Klasse `RoomLoader` lädt alle abgespeicherten Room-Templates aus einer JSON-Datei und speichert diese ab. Mit der Methode `getRoomTemplate` kann eine Liste mit allen Templates abgefragt werden, die das übergebene Design-Label haben. `DesignLabel` ist ein Enum mit verschiedenen Designs. Sollen alle Templates abgefragt werden, kann `DesignLabel.ALL` verwendet werden.

Die Klasse `ReplacementLoader` lädt alle abgespeicherten Replacements aus einer JSON-Datei und speichert diese ab. Dabei prüft der Loader, ob die `rotate`-Flag eines Replacments gesetzt ist, und wenn ja, erstellt er drei neue Replacments mit einem um jeweils 90,180 und 270 Grad rotierten Layout. So können Replacements auch in verschiedenen Positionen eingesetzt werden. Mit der Methode `getReplacements` kann eine Liste mit allen Replacements abgefragt werden, die das übergebene Design-Label haben. Sollen alle Replacements abgefragt werden, kann `DesignLabel.ALL` übergeben werden.

![UML-Klassendiagramm für RoomG mit den wichtigsten Methoden. \label{roomgUML}](figs/chapter4/roomg.png)

Die Klassen `Replacement` und `RoomTemplate` speichern jeweils ein Layout und ein Design-Label. Der Ersetzungsprozess findet in `RoomTemplate#replace` statt und wird in Listing \ref{replace_code} gezeigt. 

Da ein Template mehrfach verwendet werden soll, wird in Zeile 2 eine Kopie des Layouts erstellt. Die Ersetzung wird in dieser Kopie durchgeführt. In Zeile 3 und 4 wird die Größe des Layouts abgefragt, um späteres Iterieren zu vereinfachen. Genau wie das Template soll auch die Liste mit den möglichen Replacements mehrfach verwenden werden können, daher wird in Zeile 7 eine Kopie der übergebenen Liste erstellt. In Zeile 8 bis 11 werden alle Replacements aus der Liste gelöscht, die zu groß für das Layout sind, also über den Rand des Raumes herausragen würden. In Zeile 13 bis 25 findet der eigentliche Ersetzungsprozess statt. Die äußere Schleife in Zeile 15 bis 25 sorgt dafür, dass im Falle einer Änderung des Layouts der gesamte innere Prozess erneut durchgeführt wird. Der innere Prozess in Zeile 17 bis 24 iteriert für jedes Replacment über das Layout und such nach einer Wildcard. Dabei muss nicht das gesamte Layout betrachtet, sondern nur die Punkte, bei dem das Einfügen der Replacement nicht dafür sorgen würde, dass  das Replacement über den Raumrand hinausragt. Daher kann in Zeile 20 und 21 die Abbruchbedingung der Zählergesteuerten-Schleifen angepasst werden. In Zeile 22 wird geprüft, ob das aktuelle betrachtete Feld eine Wildcard ist und in Zeile 23 wird versucht diese Wildcard mit dem Replacement zu ersetzen. Die Funktionsweise von `placeIn` wird weiter unten erläutert. Ist der Ersetzungsprozess erfolgreich, muss nach Abschluss der foreach-Schleife in Zeile 17 der gesamte Prozess wiederholt werden, um möglicherweise neu eingesetzte Wildcards zu ersetzen. In Zeile 27 bis 30 werden alle verbliebenen Wildcards durch Bodenfelder ersetzt. In Zeile 31 erstellt die Methode ein neues Objekt der Klasse `Room` mit dem ersetzten Layout. Dieser Raum ist bereit, um im PM-Dungeon verwendet zu werden. 

\begin{lstlisting}[language=java, label=replace_code, caption={Ersetzten von Wildcards in einen Room-Layout.}  ]
public Room replace(List<Replacement> replacements) {
    LevelElement[][] roomLayout = copy(this.layout);        		
    int layoutWidth = getLayout()[0].length;
    int layoutHeight = getLayout().length;

     // remove all replacements that are too big
     List<Replacement> replacementList = new ArrayList<>(replacements);
     for (Replacement r : replacements) {
         if (r.getLayout()[0].length <= layoutWidth && r.getLayout().length <= layoutHeight)
    	      replacementList.add(r);
     }
    
     // replace with replacements
     boolean changes;
     do {
     	changes = false;
     	for (Replacement r : replacementList) {
     		int rHeight = r.getLayout().length;
     		int rWidth = r.getLayout()[0].length;
     		for (int y = 0; y < layoutHeight - rHeight; y++)
     			for (int x = 0; x < layoutWidth - rWidth; x++)
     				if (roomLayout[y][x] == LevelElement.WILDCARD && placeIn(roomLayout, r, x, y)) 
     				changes = true;
     	} 
     } while (changes);
    
     // replace all placeholder that are left with floor
     for (int y = 0; y < layoutHeight; y++)
     	for (int x = 0; x < layoutWidth; x++)
     		if (roomLayout[y][x] == LevelElement.WILDCARD)
     			roomLayout[y][x] = LevelElement.FLOOR;
    return new levelg.Room(roomLayout, getDesign());
}
\end{lstlisting}

Listing \ref{placein_code} zeigt die Methode `placIn`. Die Methode bekommt ein Layout übergeben in den das Replacment `r` so eingesetzt werden soll, dass die obere linke Ecke vom `r` in `layout[yCor][xCor]` eingesetzt wird. Ist der Prozess erfolgreich, gibt die Methode `true` zurück, ansonsten `false`. In Zeile 2 prüft die Methode mithilfe von `canReplaceIn` ob `r` in `layout` an der Stelle `[ycor][xcor]` passt. 

Listing \ref{canplacein_code} zeigt die Methode `canReplaceIn`. Die Methode iteriert durch die Zeilen 3 und 4 über das Layout ab der Stelle `[yCor][xCor]` bis zu der Stelle an der das Replacement enden würde, wenn es eingesetzt wird. Für jedes Feld, das dabei betrachtet wird, wird in Zeile 7 und 8 geprüft, ob das Replacement an der Stelle ein Feld ersetzen wollen würde und ob an dieser Stelle im Layout eine Wildcard ist. Möchte das Replacement ein Feld ersetzten an der im Layout keine Wildcard ist, kann keine Ersetzung durchgeführt werden und die Methode gibt `false` zurück. Sollte an jeder zu ersetzenden Stelle auch ein Placeholder sein, gibt die Methode `true` zurück. 

Ist eine Ersetzung möglich, iteriert `placeIn` genauso wie `canReplaceIn` durch Zeile 5 und 6 über das Layout und führt in Zeile 7 und 8 an den jeweiligen Stellen eine Ersetzung durch. 

\begin{lstlisting}[language=java, label=placein_code, caption={Code um ein Replacment in eine spezifische Stelle im Room-Layout zu platzieren.}  ]
private boolean placeIn(final LevelElement[][] layout, final Replacement r, int xCor, int yCor) {
	if (!canReplaceIn(layout, r, xCor, yCor)) return false;
	else {
		LevelElement[][] rlayout = r.getLayout();
		for (int y = yCor; y < yCor + rlayout.length; y++)
		for (int x = xCor; x < xCor + rlayout[0].length; x++) 
			if (rlayout[y-yCor][x-xCor] != LevelElement.SKIP)
				layout[y][x] = rlayout[y - yCor][x - xCor];
			
		return true;
	}
}
\end{lstlisting}

\begin{lstlisting}[language=java, label=canplacein_code, caption={Prüfen ob das Replacement an die spezifische Stelle im Layout eingesetzt werden kann.}  ]
private boolean canReplaceIn(LevelElement[][] layout, final Replacement r, int xCor, int yCor) {
	LevelElement[][] rlayout = r.getLayout();
	for (int y = yCor; y < yCor + rlayout.length; y++)
		for (int x = xCor; x < xCor + rlayout[0].length; x++) 
			if (rlayout[y - yCor][x - xCor] != LevelElement.SKIP && layout[y][x] != LevelElement.WILDCARD) 
			return false;
			
	return true;
}
\end{lstlisting}

![Verschiedene von RoomG erzeugte Räume, die alle das selbe 8x8 Felder großes Raum-Template als Vorlage haben. \label{roomgex}](figs/chapter4/rooms.png){width=50%}

Abbildung \ref{roomgex} zeigt verschiedene Räume die auf den selben, 8x8 großen Raum-Template basieren und durch verschiedene Replacern verändert wurden. In Kapitel 6 wird weiter auf die Qualität und Abwechslung der Räume eingegangen. 

## Umsetzung LevelG

In diesem Abschnitt wird die Umsetzung von LevelG beschrieben. Da LevelG ein komplexer Baustein mit einer Vielzahl an Hilfsmethoden und kleineren Berechnungen ist, kann hier nicht die Funktionsweise in ihrer ganze erläutert werden. Daher konzentriert sich dieser Abschnitt auf die Beschreibung der wichtigsten Funktionalitäten. Dies umfasst die Aufteilung eines Graphen in Chains und das berechnen der configuration-spaces. 

Listing \ref{getLevel} zeigt repräsentativ eine vereinfachte Implementierung der Methode `LevelG#getLevel`. Dieser Implementierung wird die gewünschte Anzahl der Knoten und Kanten und das gewünschte Design übergeben, dann erstellt die Methode ein Level. In Zeile 2 wird dafür zuerst GraphG genutzt um ein Graphen zu generieren. In Zeile 3 und 4 werden die Templates und Replacements aus RoomG geladen. In Zeile 5 wird der Graph in Chains aufgeteilt. Die Funktionsweise von `splitInChains` wird in Listing \ref{dochain} erklärt. In  Zeile 6 werden die Chains genutzt, um die Reihenfolge zum auflösen der Knoten zu bestimmen. Zeile 7 ruft die in Listing \ref{backtrack} konzeptionierte Methode `getLevelCS` auf. Wie beschrieben verwendet diese Methode Backtracking um gültige configuration-spaces für ein Level zu berechnen. Die Berechnungen der configuration-spaces wird weiter unten erläutert. Configuration-Spaces sind eigene `CS` Objekte in LevelG. In einem `CS` ist der Knoten zu dem der configuration-space gehört, das verwendete Raum-Template und die Position des lokalen Referenzpunkt des Templates im globalen Raum abgespeichert. In Zeile 8 werden alle nötigen Durchgänge zwischen den jeweiligen Räumen platziert. In Zeile 9-12 werden die Raum-Templates der einzelnen configuration-spaces in konkrete Räume umgewandelt, das bedeutet die Wildcards werden ersetzt und die abstrakten Felder im Layout durch Tiles mit einer globalen Position und einer Textur ersetzt. In Zeile 13 wird nun das Level erstellt. In Zeile 14 wird mithilfe des Integrierten A*-Algorithmus geprüft, ob das Level lösbar ist. Nur lösbare Level werden zurückgegeben. 

\begin{lstlisting}[language=java, label=getLevel, caption={Vereinfache Darstellung der Methode um ein Level zu erzeugen.}  ]
Level getLevel(int nodeCounter, int edeCounter, DesignLabel design){
	Graph graph = graphg.getGraph(nodeCounter, edgeCounter, pathToGraph);
	List<RoomTemplate> templates = roomLoader.getRoomTemplates(design);
	List<Replacement> replacements = replacementLoader.getReplacements(design);
	List<Chain> chain = splitInChains(graph);
	List<Node> solveSeq = getSolveSequence(chains);
	List<CS> solution = getLevelCS(graph, solveSeq, new ArrayList<CS>(), templates);	
	placeDoors(solution, graph);
	for (CS cs : configurationSpaces) {
		RoomTemplate template = cs.getTemplate();
		rooms.add(template.replace(replacements, cs.getGlobalPosition()));
	}
    Level level = new Level(graph.getNodes(), rooms);
    if (checkIfCompletable(level)) return level;
    else return null;
}
\end{lstlisting}

Um den Graphen in Chains aufzuteilen, müssen die Back-Forward-Edges identifiziert werden. Wie oben beschrieben werden die Graphen zweiteilig Generiert. Erst wird wird ein Baum generiert und dann die extra Kanten in diesen Baum eingezeichnet. Diese extra Kanten sind die gesuchten Back-Forward-Edges. Daher müssen diese nicht gesucht werden sondern können bereits beim generieren des Graphen abgespeichert werden. Jedes mal wenn `Graph#connectNodes(index n1, index n2)` aufgerufen wird und die extra Kante eingezeichnet wird, speichert der Graph die Back-Forward-Edge zusätzlich in einer separaten Liste ab. In `LevelG#spliInChains` kann auf diese Liste zugegriffen werden und mithilfe der Graph-Search alle Zyklen im Graph gefunden werden. Dafür werden alle Pfade die von einem Knoten einer Back-Forward-Edge bis zum anderen Knoten führen gesucht, diejenigen entfernt welche die Back-Foward-Edge selbst enthalten und  dann der Pfad mit der geringsten Knotenanzahl genommen. Dies wird für alle Back-Forward-Edges im Graphen durchgeführt. 

Nachdem alle Zyklen im Graphen gefunden wurden und jeweils als Chain abgespeichert wurden, müssen die Knoten die nicht in einen Zyklus sind in Chains aufgeteilt werden. Dafür wird ein Knoten der nicht bereits in einer Chain ist ausgewählt. Vom ausgewählten Knoten wird nun ein Nachbar Knoten ausgewählt der sich nicht in einer Chain befindet. Für den Nachbar wird wiederum wieder ein Nachbar ausgewählt der in keiner Chain ist usw. Dieser Vorgang wird wiederholt, bis ein Knoten erreicht wurde, der keine Nachbarn mehr hat die in keiner Chain sind. In diesem Fall wurde vom Ursprünglichen Startknoten ein Weg bis zum Ende gegangen. Vom urspünglichen Startknoten kann dieser Vorgang noch einmal mit einen anderen Nachbarknoten wiederholt werden, da jeder Knoten in einer Chain bis zu zwei Nachbarknoten haben darf. Nachdem beide Richtungen bis zum Ende gegangen wurden, bilden alle betrachteten Knoten eine Chain. Dies wird wiederholt, bis alle Knoten Teil einer Chain sind. Sollte ein Startknoten keine Nachbarn haben die nicht bereits in einer Chain sind, bildet er eine Chain mit nur einen Knoten.

Listing \ref{getCS} zeigt wie alle gültigen configuration-spaces für einen Knoten gefunden werden. Der Methode werden die configuration-spaces für alle bereits platzierten Nachbarn übergeben. An diese configuration-spaces muss der platzierte Raum angebunden werden. Außerdem werden der Methode alle bereits gesetzten configuration-spaces übergeben. Bei der Platzierung des Raumes muss darauf geachtet werden, dass es zu keiner Überschneidung mit den anderen Räumen kommt. Die Methode gibt alle möglichen configuration-spaces für den Knoten mit dem zu verwendenden Template für die gegebene Umgebung zurück. Dafür wird mithilfe der Methode `calCS` jeder gültiger configuration-space berechnet bei dem der Knoten so platziert wird, dass er an den Nachbar anliegend ist. Diese Berechnung wird mit jedem Nachbar durchgeführt. Die Schnittmenge der gültigen configuration-spaces für die einzelnen Nachbarn, sind alle gültigen configuration-spaces für den Knoten, damit dieser so platziert werden kann, dass er mit allen seinen Nachbarn verbunden ist und zeitgleich keinen Raum überschneidet. 

\begin{lstlisting}[language=java, label=getCS, caption={Finden aller gültigen configuration-spaces für einen Knoten.}  ]
        private List<CS> getCS(
                List<CS> neighbourSpaces,
                Node node,
                List<RoomTemplate> templates,
                List<CS> partSolution) {
            List<CS> possibleSpaces = new ArrayList<>();
            for (CS cs : neighbourSpaces)
                if (possibleSpaces.isEmpty()) possibleSpaces = calCS(cs, node, templates, partSolution);
                else possibleSpaces.retainAll(calCS(cs, node, templates, partSolution));
            return possibleSpaces;
        }
\end{lstlisting}    

Listing \ref{calCS} zeigt wie die configuration-spaces für einen Knoten berechnet werden. Wie in Kapitel 3.1 beschrieben, wird dafür ein statischer und ein dynamischer Raum verwendet. Der statische Raum ist bereits im Level platziert und darf nicht bewegt werden, der dynamische Raum muss so platziert werden, dass er anliegend an den statischen Raum ist aber dabei keine anderen bereits gesetzten Räume überschneidet. Dafür wird in Zeile 7 bis 17 für jedes Raum-Template das verwendet werden kann geprüft ob es gültige configuration-spaces gibt. In Zeile 9 wird ein Sonderfall abgedeckt, bei dem es sich um den aktuellen Knoten zeitgleich um den ersten Knoten der aufgelöst wird, handelt. In diesem Fall wird für jedes Raum-Template die Position $(0|0)$ als globale Position bestimmt. Da es noch keine anderen gesetzten Räume gibt, müssen keine weiteren Bedingungen beachtet werden. Handelt es sich nicht um den ersten Knoten werden in Zeile 10 mithilfe der Methode `calAttachingPoints` alle Punkte berechnet, an den das Raum-Template platziert werden kann um mit den statischen Raum verbunden zu werden. in Zeile  11 bis 16 wird dann geprüft ob es bei den jeweiligen Punkte noch zu überschneidungen mit anderen bereits platzierten Räumen kommt, wenn des nicht der Fall ist, ist der gefundene Punkt ein güliger configuration-space.

\begin{lstlisting}[language=java, label=calCS, caption={Berechnen der configuration-spaces für einen statischen und einen bewegbaren Raum.}  ]
      private List<CS> calCS(
                ConfigurationSpace staticSpace,
                Node dynamicNode,
                List<RoomTemplate> template,
                List<CS> level) {
            List<CS> spaces = new ArrayList<>();
            for (RoomTemplate layout : template) {
                List<Point> possiblePoints = new ArrayList<>();
                if (level.isEmpty()) possiblePoints.add(new Point(0, 0));
                else possiblePoints = calAttachingPoints(staticSpace, layout);               
                for (Point position : possiblePoints) {
                    boolean isValid = true;
                    for (ConfigurationSpace sp : level)
                        if (sp.overlap(layout, position)) isValid = false;                
                    if (isValid) spaces.add(new CS(layout, dynamicNode, position));
                }
            }    
        return spaces;
        }
\end{lstlisting}

Die Methode `calAttachingPoints` findet alle Punkte im globalen Raum an den ein Template platziert werden kann um an einen statischen Template angebunden zu werden. Dafür wird von jedem äußeren Wandfeld des statischen Templates solange in eine Richtung gegangen bis das dynamische Tempalte so platziert werden kann, dass es angebunden ist oder soweit entfernt ist, dass es nicht mehr in Reichweite ist. Für alle Punkte an den das Template platziert werden kann und angebunden ist, wird ein Configuration Space erstellt. 

Abbildungen **TODO** zeigen verschiedene von LevelG generierte Level. In Kapitel 6 werden die Level analysiert und bewertet. 


## Anbindung an das PM-Dungeon und Schnittstellen

Abbildung \ref{intUML} zeigt wie DungeonG in das Framework integriert wurde. Die Klasse `LevelAPI` speichert eine Instanz eines Objektes vom Typen `IController` und führt, wenn von außen angefragt, die `getLevel` Methode aus. Ist DungeonG als Generator hinterlegt, wird der oben beschriebene Generierungsprozess in `LevelG` gestartet und ein Level erzeugt. Die Integration in das Framework wurde Modular durchgeführt. Im Package `levelStructure` sind alle, nicht auf den Generator spezifizierten, Klassen enthalten. Diese Klassen können direkt von der `LevelAPI` oder den Studierenden genutzt werden. Im Package `dungeong` sind die Generator spezifischen Klassen abgespeichert. Im Falle von DungeonG sind dies die Bausteine `GraphG`, `RoomG` und `LevelG`. Durch diese Aufteilung ist es möglich, DungeonG bei Bedarf durch einen neuen, graphenbasierten Levelgenerator zu ersetzen. 

![UML-Klassendiagramm mit Packages zeigt die Anbindung an das Framework. \label{intUML}](figs/chapter4/integration.png)

In Folgenden werden verschiedene Code-Snippets erläutert um zu präsentieren, wie die Studierenden die Schnittstellen nutzen können, um ihr Spiel zu gestalten. Alle Code-Snippets sind so abstrahiert, dass kein weiteres Verständnis für das PM-Dungeon-Framework von Nöten ist.

Listing \ref{api1} zeigt wie ein Monster zufällig im Level platziert werden kann. In Zeile 1 wird das aktuelle Level abgefragt. In Zeile 2 wird ein zufälliges Bodenfeld aus einem zufälligen Raum im Level ausgewählt. In Zeile 3 wird dem Monster `dino` die Globale-Position des Tiles zugewiesen.  

\begin{lstlisting}[language=java, label=api1, caption={Platziere ein Monster zufällig im Level.}  ]
 Level currentLevel = levelAPI.getCurrentLevel();
 Tile randomTile = currentLevel.getRandomRoom().getRandomFloorTile();
 dino.setPosition(randomTile.getGlobalPosition());
\end{lstlisting}

Listing \ref{api2} zeigt wie der Held auf dem Startpunkt des Level gesetzt werden kann. In Zeile 1 wird das aktuelle Level abgefragt. In Zeile 2 wird das als Startpunkt markierte Tile abgefragt. In Zeile 3 wird dem Helden die Position des Tiles zugewiesen. 

\begin{lstlisting}[language=java, label=api2, caption={Platziere den Helden am Startpunkt des Level.}  ]
 Level currentLevel = levelAPI.getCurrentLevel();
 Tile startTile = currentLevel.getStartTile();
 hero.setPosition(starTile.getGlobalPosition());
\end{lstlisting}

Listing \ref{api3} zeigt wie ein Monster in einem kritischen Raum platziert werden kann. In Zeile 1 wird das aktuelle Level abgefragt. In Zeile 2 werden alle kritischen Knoten abgefragt. In Zeile 3 wird ein zufälliger Knoten aus allen kritischen Knoten ausgewählt. In Zeile 4 wird der Raum gespeichert, der den Knoten im Level repräsentiert. Der Index des Knoten in `Level.rooms` ist identisch mit dem passenden Index des Knoten in `Level.nodes`. In Zeile 5 wird ein zufälliges Bodenfeld aus dem Raum ausgewählt. In Zeile 6 wird dem Monster `evilDuck` die Position des Tiles zugewiesen. 

\begin{lstlisting}[language=java, label=api3, caption={Platziere ein Monster in einen zufälligen kritischen Raum.}  ]
Level currentLevel = levelAPI.getCurrentLevel();
List <Node> criticalNodes = currentLevel.getCriticalNodes();
Node randomNode = criticalNodes.get(new Random(criticalNodes.size()));
Room randomRoom = currentLevel.getRoomToNode(randomNode);
Tile randomTile = randomRoom.getRandomFloorTile();
evilDuck.setPosition(randomTile.getGlobalPosition());
\end{lstlisting}

Listing \ref{api4} zeigt wie eine Schatztruhe in einen optionalen Raum platziert werden kann. In Zeile 1 wird das aktuelle Level abgefragt. In Zeile 2 werden alle optionalen Knoten abgefragt. In Zeile 3 wird ein zufälliger Knoten aus allen optionalen Knoten ausgewählt. In Zeile 4 wird der Raum gespeichert, der den Knoten im Level repräsentiert. In Zeile 5 wird ein zufälliges Bodenfeld aus dem Raum ausgewählt. In Zeile 6 wird der Schatzkiste `bigLoot` die Position des Tiles zugewiesen. 

\begin{lstlisting}[language=java, label=api4, caption={Platziere eine Schatzkiste in einen zufälligen optionalen Raum.}  ]
Level currentLevel = levelAPI.getCurrentLevel();
List <Node> optionalNodes = currentLevel.getOptionalNodes();
Node randomNode = optionalNodes.get(new Random(optionalNodes.size()));
Room randomRoom = currentLevel.getRoomToNode(randomNode);
Tile randomTile = randomRoom.getRandomFloorTile();
bigLoot.setPosition(randomTile.getGlobalPosition());
\end{lstlisting}

Listing \ref{api5} zeigt wie geprüft werden kann, ob ein bestimmter Raum umgangen werden kann. In Zeile 1 wird das aktuelle Level abgefragt. In Zeile 2 und 3 werden sowohl der Start- als auch der Endknoten des Levels abgefragt. In Zeile 4 wird der kürzeste Pfad vom Start- bis zum Endknoten abgefragt. In Zeile 5 wird ein zufälliger Knoten aus diesem Pfad ausgewählt, dieser Knoten dient zur Demonstration und gilt es zu vermeiden. In Zeile 6 wird geprüft ob der ausgewählte Knoten auf dem Weg vom Start- bis zum Endknoten umgangen werden kann. In diesem Beispiel würde das bedeuten, das es mehrere Wege zum Ziel gibt. In Zeile 7 und 8 finden Ausgaben entsprechend der Ergebnisse statt. Diese dienen nur der Demonstration und könnten durch spezifische Game-Designentscheidungen ersetzt werden. 

\begin{lstlisting}[language=java, label=api5, caption={Prüfe ob ein Raum betreten werden muss, um von Raum A nach Raum B zu gelangen.}  ]
Level currentLevel = levelAPI.getCurrentLevel();
Node start = currentLevel.getStartNode();
Node end = currentLevel.getEndNode();   
List<Node> path = currentLevel.getShortestPath(start,end);
Node toAvoid = path.get(new Random(path.size()));
if (currentLevel.isRoomReachableWithout(start,end,toAvoid))
	System.out.println("YES!")
else System.out.println("OH NO!"));   
\end{lstlisting}

# Evaluierung 

In diesem Kapitel werden die erreichten Ergebnisse mit den aufgestellten Anforderungen gegenübergestellt. Die Evaluierung wird in zwei Bereiche eingeteilt. Im ersten Teil werden der Generator und die erzeugten Level betrachtet und darauf untersucht, ob sie die bekannten Regeln für gutes Leveldesign erfüllen. Im zweiten Teil wird das Projekt selbst, insbesondere auf die Anforderungen für die Integration in das PM-Dungeon-Framework betrachtet. 

## Betrachtung eines Level

**TODO**

## Gute Level sind lösbar und fehlerfrei 

Als kritisches Kriterium wurde die Lösbarkeit und Fehlerfreiheit der Level genannt. Die Generierung und grafische Darstellung der Level funktioniert fehlerfrei, es sind keine Fehler in den Algorithmen und Schnittstellen bekannt.

Der Generator stellt sicher, dass alle erzeugten Level lösbar sind. Während der Graphengenerierung werden nur zusammenhängende Graphen erzeugt. So ist sichergestellt, dass jeder Raum im Level strukturell erreichbar ist. Durch die Verwendung verschiedener Raum-Layouts und Replacements, kann es passieren, dass unerreichbare Felder im Level entstehen. Mithilfe des integrierten A*-Algorithmus wird geprüft, ob ein Weg vom Startpunkt des Levels bis zum Endpunkt führt. Nur Level, die einen solche Weg haben, werden akzeptiert und als Lösung ausgegeben. In gültigen Level können aber unerreichbare Felder sein. Um zu prüfen, ob ein spezifisches Feld erreichbar ist, wird ebenfalls der A*-Algorithmus genutzt. So kann vor der Platzierung von Inhalten geprüft werden, ob die Inhalte dann auch erreichbar sind. Nicht erreichbare Felder müssen erst mithilfe von Spielelementen wie Bomben oder Spruchrollen freigelegt werden.  

Die Anforderung, dass nur lösbare und fehlerfreie Level erzeugt werden, kann als erfüllt betrachtet werden.

## Gute Level fordern die Mechaniken des Spiels

Die generierten Level können problemlos im PM-Dungeon genutzt werden. Sie bestehen aus den grundlegenden Spielelementen, dies beinhaltet Wände, Böden und ein Start- sowie Endpunkt. Die Level werden mithilfe des Frameworks und libGDX gezeichnet. Mithilfe der Schnittstellen können selbst implementierte Inhalte im Level platziert werden. Dabei können Objekte wie Monster, Items oder Fallen platziert werden, aber auch die Struktur der Level verändert werden, indem zum Beispiel Wände gesprengt werden. Auch haben die Studierenden die Möglichkeit von den grundlegenden Regeln des Spiels abzuweichen. Es können beispielhaft auch Gegner implementiert werden, die auch durch Wände gehen können oder sich wie der Springer eines Schachfeldes bewegen.

Die Anforderung, dass der Generator und die erzeugten Level die Mechaniken des Spieles fordern, kann als erfüllt betrachtet werden.

## Gute Level sind gut gebalanced

Das Balancing der Level hängt von den platzierten Inhalten ab. Mithilfe der Schnittstellen können Inhalte an spezifischen Orten gesetzt werden. Auch können Inhalte während des laufenden Spieles im Level platziert werden. Sollte der Spieler besonders gut sein, können also im laufenden Level mehr Gegner platziert werden, sollte der Spiele Probleme haben, können stärkere Items oder Heiltränke im aktuellen Level platziert werden. Die konkrete Konzeptionierung und Implementierung der Balancing-Funktionen ist von den Studierenden durchzuführen. 

Die Anforderung, dass die Level Möglichkeiten zum Balancen haben, kann als erfüllt betrachtet werden.

## Gute Level haben Risk and Reward Momente

Der Levelgenerator kann optionale Räume und Pfade erzeuge. Die Schnittstellen erlauben es, diese optionalen Räume abzufragen und gezielt Inhalte in diesen Räumen zu platzieren und somit Risk and Reward Momente zu erzeugen. Die Anzahl und Verteilung der optionalen Räume kann nicht festgelegt werden. Durch unterschiedliche Werte für die Knoten- und Kantenanzahl bei der Graphengenerierung kann jedoch Einfluss genommen werden. Graphen mit vielen Knoten haben eine höhere Chance optionale Räume zu haben als Graphen mit wenigen Knoten und Graphen mit vielen extra Kanten haben mehr optionale Pfade als Graphen mit wenigen extra Kanten. 

Die Anforderung, dass der Generator Level mit optionalen Pfaden erzeugen muss und dass auf diesen Pfaden gezielt Risk and Reward Momente erzeugt werden können, kann als erfüllt betrachtet werden.

## Gute Level steuern das Pacing des Spiels

Das Pacing der Level wird durch die Struktur der Level bestimmt. Die Struktur der Level wird von dem zugrunde legenden Graphen festgelegt. Bei der Generierung der Graphen kann keine konkrete Vorgabe gemacht werden, wie die Graphen aussehen sollen, aber mithilfe der Parameter kann Einfluss auf den Generierungsprozess genommen werden. Insbesondere die Größe der Graphen kann bestimmt werden. Ebenso können eigenständig neue Knoten und Kanten hinzugefügt werden. Ein Graph könnte daher auf Sackgassen untersucht werden und so verändert werden, dass Backtracking nicht nötig ist, um wieder auf den Hauptpfad zu gelangen. DungeonG bietet keine konkrete Schnittstelle mit verschiedenen Graphenanalyse-Algorithmen, diese müssen daher selbstständig implementiert werden.

Dennoch erfüllt DungeonG grundlegend die Anforderung, um die Steuerung des Pacing mithilfe der Level-Struktur zu ermöglichen. 

## Gute Level sind einzigartig

Die von DungeonG erzeugten Level unterscheiden sich sowohl strukturell als auch optisch stark voneinander. Das Layout der Level wird von den Graphen bestimmt. GraphG kann eine Vielzahl an unterschiedlichen planaren Graphen erzeugen und initial werden pro Konfiguration bis zu 1000 unterschiedliche Graphen angeboten. Die verwendeten Layouts der Räume werden durch die Replacements verändert, so können aus wenigen Layouts eine Vielzahl an unterschiedlichen fertigen Räume erzeugt werden. Der Generator unterstützt verschiedene Designs der Level. Dabei geben die Designs nicht nur an, welche Texturen für das Level verwendet werden sollen, sondern auch welche Raum-Layouts und Replacements. Je nach Design können auch unterschiedliche Monster und Items platziert werden. Neue Layouts können selbstständig hinzugefügt werden, um die Abwechslung zu vergrößern. Die erzeugten Level können sowohl strukturell als auch optisch als einzigartig bezeichnet werden. 

Die Anforderung, dass die erzeugten Level sich optisch und strukturell voneinander abgrenzen, kann als erfüllt betrachtet werden.

## Gute Level sind effizient in der Herstellung

DungeonG ist ein funktionsfähiger prozeduraler Level-Generator und ist daher eine effiziente Möglichkeit Level zu generieren. Aber auch für einen prozeduralen Generator, kann DungeonG als effizient betrachtet werden. DungeonG benötigt nur Raum-Layouts und Replacements, um Level zu generieren, alle anderen benötigten Inhalte erzeugt DungeonG eigenständig und erlaubt es, mit Parametern Einfluss auf den Prozess zu nehmen. Durch die Verbindung der Raum-Layouts und Replacements kann DungeonG auch aus einer geringen Anzahl an Inputdaten, eine Vielzahl an optisch und strukturell unterschiedlichen Level erzeugen. 


Auch wenn DungeonG nicht vollständig ohne Inputdaten auskommt, kann die Anforderung, dass Level effizient generiert werden sollen, als erfüllt betrachtet werden. Hierbei spielt der Speicher- und Rechenleistungsbedarf keine Rolle.


## Testabdeckung

Für die in dieser Arbeit implementierten Klassen und Funktionen sind zum aktuellen Stand keine Testfälle vorhanden. Dies stellt zwar kein Problem für die Funktionalität des Generators dar, es sollten dennoch Umfangreiche Tests konzeptioniert und umgesetzt werden damit etwaige Veränderungen am Framework oder am Generator keine unvorhergesehenen Fehlerfälle auslösen. 

## Integration in das PM-Dungeon-Framework

Neben den Regeln für gutes Leveldesign gibt es auch spezifische Anforderungen für die Integration in das PM-Dungeon-Framework. 

Da darauf geachtet wurde, dass dieselben Tools und Konfigurationen für das Projekt genutzt wurden wie für das Framework selbst, war die Anbindung des Generators an das Framework reibungslos möglich. Mithilfe von Spot-Bugs und dem Google-Java-Formatter wurde die Einhaltung der Code-Konvention sichergestellt. Alle von Spot-Bugs gefundenen Antipattern wurde beseitigt. Der Code ist angemessen dokumentiert und kommentiert. 

Ebenso ist DungeonG modular aufgebaut und angebunden. Die einzelnen Bausteine GraphG, RoomG und LevelG können einzeln oder vollständig ersetzt werden. Auch kann DungeonG selbst vollständig aus dem Framework entfernt und durch einen anderen Generator ersetzt werden (vgl. \ref{intUML}). 





