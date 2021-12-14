# Eigenes Konzept

In diesem Kapitel wird ein eigenes Konzept zur Implementierung eines prozeduralen graphenbasierten 2D-Levelgenerator vorgestellt. Der konzeptionierte Generator soll nicht nur gute Level erzeugen, sondern auch in das PM-Dungeon integriert werden können und für die Studierenden nutzbar gemacht werden. Der Generator soll die in Kapitel 3 vorgestellten Algorithmen verwenden und miteinander kombinieren, um effektiv gute Level zu generieren. Im nächsten Abschnitt werden weitere spezifische Anforderungen für den Generator aufgestellt, die sich vor allem auf die Integration des Generators in das PM-Dungeon fokussieren. Um die Besonderheiten des in dieser Arbeit konzeptionierten Algorithmus hervorzuheben, findet in Abschnitt 3.2 eine Abgrenzung zu anderen prozeduralen Algorithmen statt, bevor im letzten Abschnitt das modulare Bausteinkonzept für den Generator vorgestellt und das Konzept zur Implementierung des Generators präsentiert wird. 

## Zielsetzung und Anforderungen an das Projekt 

Ziel dieser Arbeit ist es, ein Generator zu Entwickler der abwechslungsreiche und spaßige Level erstellt und in das PM-Dungeon-Framework integriert werden kann. Daher sind die Anforderungen an den Generator zweigeteilt: Auf der einen Seite muss der Generator gute Level erzeugen, dafür wurden im Kapitel 2 verschiedene Regeln für gutes Leveldesign aufgestellt. 

1. Gute Level sind lösbar und fehlerfrei
2. Gute Level fordern die Mechaniken des Spiels
3. Gute Level sind gut gebalanced
4. Gute Level haben Risk and Reward Momente
5. Gute Level steuern das Pacing des Spiels
6. Gute Level sind einzigartig
7. Gute Level sind effizient in der Herstellung

Die vom Generator erzeugten Level sollten all diese Regeln beachten und der Generator muss Schnittstellen anbieten um die Einhaltung der Regeln zu gewährleisten. 

Auf der anderen Seite der Anforderungen steht die Integration in das PM-Dungeon-Framework. Das bedeutet, dass der Generator nahtlos in das bestehende Framework eingegliedert werden können muss und die Anforderungen des Frameworks berücksichtigt. Dies schließ neben dem einhalten der Code Konvention, Kompatibilität der verwendeten Buildtools und libaries vorallem auch die Testbarkeit des Generators mit ein. Der Code sollte so konzeptioniert werden, dass eine sehr gute Testabdeckung mithilfe von JUnit möglich ist. Tests sollten für alle essenziellen Bestandteile geschrieben und dokumentiert werden. Alle Codeteile sollten ausreichend Dokumentiert und Kommentiert sein. Schnittstellen müssen Umfangreich und Verständlich Dokumentiert sein. Der Grundsätzliche Projektaufbau des Frameworks muss beachtet werden. 

Da der eigentliche Fokus des PM-Dungeon nicht die Entwicklung eines Spiels ist, sondern das erlernen und vertiefen der Programmierkenntnisse, muss der Generator ohne Inputdaten der Anwender funktionieren. Parameterkonfigurationen sind zwar erwünscht, es sollte jedoch immer eine Standartkonfiguration angeboten werden. Der Code sollte wenmöglich Modular aufagbaut sein, damit zukünftige Verbesserungen und Änderungen schnell integriert werden können. Außerdem erlaubt eine Modulare Gestaltung den interessierten Studierenden eigene Inhalte in den Generator hinzuzufügen oder Teile auszutauschen. Der Generator muss weder Zeit- noch Speicherplatzeffizient sein. Sollte es der Lesbarkeit des Codes zugute kommen, ist langsamerer, speicheruneffizienter Code zu bevorziehen. Die generierung von Level im dreidimensionalen Raum ist nicht Ziel dieser arbeit und stellt keine Anforderung an den Generator dar. 

## Abgrenzung zu anderen Levelgeneratoren und prozeduralen Algorithmen 

Der Generator in dieser Arbeit unterscheidet sich vor allem durch die unterschiedlichen Anwenderzielgruppen zu den anderen in dieser Arbeit vorgestellten oder in der Praxis angewendeten Algorithmen. Während die bekannte Verfahren so konzeptioniert sind, dass erfahrene Game- und Leveldesigner sorgfältig gestaltete Inhalte erzeugen und mithilfe der Algorithmen abwandeln um effizient Level zu erzeugen, muss der Generator für das PM-Dungeon ohne Inhalte von Designern auskommen. Auch wenn die qualität der Level ein wichtiges Kriteriums ist, so ist die bedienbarkeit und integration des Generatos wichtiger als bei anderen Algorithmen. Während andere Generatoren nur Teilaspekte der Generierung abdecken, wie Levelstruktur oder gesaltung der Räume, deckt der Generator dieser Arbeit alle Aspekte der Levelgenerierung ab. Dieser Generator stellt weniger Konfigurationsmöglichkeiten zur verüfugung als andere Generatoren, ermöglicht es aber Anwender aus dem Programmierbereich einfach abwechslungsreiche Level zu erzeugen. Der Generator ist für die Anwendung durch Programmierer und nicht für die Anwendung durch Designer optimiert.

## Darstellung des Konzeptes

Der Dungeongenerator **DungeonG** wird mithilfe der im vorherigen Kapitel vorgestellten Algorithmen umgesetzt und besteht im Wesentlichen aus drei Bausteinen.
Baustein eins ist der Graphgenerator **GraphG**, welcher einen planaren Graphen generiert.
Baustein zwei ist der Raumgenerator **RoomG**, welcher verschiedene Räume aus vorgegebenen Templates erzeugt. 
Baustein drei ist der Levelgenerator **LevelG**, welcher den von GraphG erzeugten Graphen nimmt und diesen mithilfe der Räume von RoomG in ein spielbares Level verwandelt. 

Im Folgenden werden die konkreten Aufgaben und Konzepte zur Umsetzung der einzelnen Bausteine und deren Kombination miteinander beschrieben. Ebenso wird beschrieben, wie DungeonG in das PM-Dungeon integriert werden kann und welche Änderungen am bisherigen PM-Dungeon dafür notwendig sind. Da den Studierenden verschiedene Optionen zur Interaktion mit den erzeugten Level ermöglicht werden soll, wird auch eine umfangreiche Schnittstelle hierfür definiert. 

### GraphG

GraphG erzeugt planare Graphen. Dabei soll die Anzahl der Knoten und Kanten beim Aufruf bestimmbar sein. Um eine vom PM-Dungeon unabhängige Visualisierungsmöglichkeiten zu haben, soll der Graph in *DOT-Formatierung*\footnote{DOT ist eine Beschreibungssprache für die visuelle Darstellung von Graphen} exportierbar sein. 

Um sicherzustellen, dass die erzeugten Graphen planar sind, beachtet GraphG den Satz von Kuratowski und verhindert die Generierung von $K5$ und $K3,3$ Teilgraphen indem bei der Generierung maximal vier Knoten mit drei oder mehr Nachbarn erzeugt werden.

Abbildung \ref{graphgSeq} zeigt ein reduziertes Sequenzdiagramm zur Darstellung des grundlegenden Ablaufes von GraphG. Beim Aufruf von `GraphG#generateGraphs` wird die gewünschte Anzahl an Knoten und extra Kanten übergeben. Da GraphG nur Graphen erzeugt, in dem jeder Knoten verbunden ist, ergibt sich $Kantenanzahl=Knotenanzahl-1+Anzahl extra Kanten$. Dies liegt daran, dass die Generierung von GraphG zweigeteilt ist. Im ersten Teil `GraphG#calculateTrees` werden alle gültigen\footnote{ Ein Baum bzw. Graph ist genau dann gültig, wenn er maximal vier Knoten mit mehr als drei oder mehr Nachbarn hat.} Bäume mit der gewünschten Anzahl der Knoten erzeugt, auf die genauere Funktionsweise von `GraphG#calculateTrees` wird weiter unten eingegangen. Jeder erzeugte Baum hat $Knotenanzahl-1$ Kanten. Im zweiten Teil wird aus jedem Baum jeder gültiger Graph erzeugt, indem in `GraphG#calculateGraphs` die extra Kanten in die Bäume eingezeichnet werden, auf die genauere Funktionsweise von `GraphG#calculateGraphs` wird weiter unten eingegangen. Am Ende wird eine Liste mit allen gültigen Graphen mit der gewünschten Anzahl an Kanten und Knoten übergeben.

![UML-Sequenzdiagramm für GraphG ohne Einblick in die Rekursiven Methoden. \label{graphgSeq}](figs/chapter4/graphgSeq.png)

GraphG generiert die Graphen daher nicht per Zufall, sondern verwendet Breitensuche, um alle gültigen Lösungen zu finden. Im Vergleich zur Zufallssuche hat dies den Vorteil, dass sichergestellt werden kann, dass alle gültigen Lösungen generiert werden und der Rechen- und Speicheraufwand für gleiche Parameterkombination konstant bleibt. Die Zufallssuche kann zwar in der Theorie schneller eine gültige Lösung finden, dies kann aber nicht sichergestellt werden. Im Rahmen dieser Arbeit wurden auch mit der Zufallssuche experimentiert. Im Anhang **TODO** kann eine Tabelle mit Messdaten eingesehen werden. Es hat sich herausgestellt, dass die Zufallssuche zwar in Suchräumen mit vielen Lösungen schnell eine solche finden kann, jedoch in Suchräumen mit wenigen Lösungen nur sehr langsam eine Lösung findet. Daher eignet sich die Zufallssuche nicht für den Anwendungsfall PM-Dungeon. 

Da GraphG alle Lösungen für eine Parameterkombination findet, können diese abgespeichert werden und können beim nächsten Aufruf ohne Suche verwendet werden. GraphG speichert alle gefundenen Lösungen für eine Parameterkombination in einer JSON-Datei ab und mithilfe von `GraphG#getGraph(int knoten, int edges)` kann ein zufälliger, vorher gefundener, Graph aus der JSON abgefragt werden. Dieses Vorgehen spart vor allem Rechenleistung, welche im laufenden Spiel unter Umständen benötigt wird, setzt aber die JSON-Dateien voraus. Da GraphG beides ermöglicht, können die Studierenden ihr bevorzugtes Verfahren verwenden. 

Für planare zusammenhängende Graphen mit $v>=3$ gilt zusätzlich $e<=3v-6$ wobei $e$ die Anzahl der Kanten und $v$ die Anzahl der Knoten angibt.[@Volkmann1996] Dieser Satz erlaubt es, einige Parameterkombinationen bereits vor der Suche als unlösbar zu klassifizieren. GraphG führt daher keine Suche für Parameterkombinationen durch, für die diese Bedingung nicht hält. 

Listing \ref{ctpseudo} zeigt die Methode `GraphG#calculateTrees` welche mithilfe von Rekursion alle gültigen Bäume erzeugt. In jedem Durchlauf werden Teillösungen gefunden, dies sind Bäume die einen Knoten mehr haben als die Bäume in letzten durchlauf. Die Methode bekommt initial eine Liste mit einem Graphen, der aus zwei miteinander verbundene Knoten besteht, sowie die Anzahl der noch hinzuzufügenden Knoten übergeben. Für jeden Durchlauf wird eine neue Liste mit den neuen Teillösungen erstellt, in dieser Liste werden alle gültigen Bäume abgespeichert, die in diesem Durchlauf gefunden werden. Für die Suche wird über jeden Graphen in der übergebenen Liste iteriert. Dann wird über jeden Knoten im Graphen iteriert. Für jeden betrachteten Knoten im Graphen wird eine Kopie des Graphen erstellt und dann geprüft, ob in der Kopie des Graphen an der Kopie des betrachteten Knoten ein neuer Knoten hinzugefügt werden kann. Ist dies der Fall, wird der Knoten hinzugefügt und verbunden und die Kopie des Graphen in der Liste mit den Teillösungen hinzugefügt. Dies wird wiederholt, bis jede Möglichkeit einen neuen Knoten im Graphen anzubinden für jeden Graphen betrachtet wurde. Danach ruft sich die Methode rekursiv selbst auf und übergibt die gefundenen Teillösungen und reduziert den noch hinzuzufügenden Knotencounter um 1. Müssen keine Knoten mehr hinzugefügt werden, gibt die Methode die Liste mit allen gültigen Bäumen zurück. 

\begin{lstlisting}[language=python, label=ctpseudo, caption={Pseudocode calculateTrees}  ]
List<Graph> calculateTrees(List<Graph> trees, int nodesLeft){
    if(nodesLeft<=0) return trees;
    List<Graph> newTrees = new ArrayList<>();
    for each Graph tree in trees:
    	for each Node n in tree.getNodes():
    		Graph newTree= newTree(tree);
    		if neuer Node kann an n angebunden werden:
    			verbinde n mit einen neuen Node in newTree
    			newTrees.add(newTree);
    return calculateTrees(newTrees,nodesLeft-1);
}
\end{lstlisting}

Listing \ref{cgpseudo} zeigt die Methode `GraphG#calculateGraphs` welche ähnliche wie die Methode `GraphG#calculateTrees` funktioniert. Diese Methode bekommt initial eine Liste mit gültigen Bäumen sowie der Anzahl der noch hinzuzufügenden Kanten übergeben. Es wird wieder eine Liste für die neuen Teillösungen angelegt und über alle Graphen in der übergebenen Liste iteriert. Dieses Mal wird ebenfalls über jeden Knoten im Graphen iteriert, jedoch findet eine verschachtelte Iteration statt, in der für jeden Knoten im Graphen über jeden Knoten im Graphen iteriert wird. Dies wird getan, damit die verschiedenen Knoten im Graphen miteinander verbunden werden können. Es wird jede Möglichkeit eine Kante einzuzeichnen betrachtet und wenn dabei eine gültige Teillösung gefunden wurde, wird diese in der Liste gespeichert. Danach ruft sich die Methode rekursiv selbst auf und übergibt die Liste mit den neu gefundenen Teillösungen und reduziert die Anzahl der noch hinzuzufügenden Kanten um 1. Sind alle Kanten hinzugefügt, gibt die Methode die Liste mit allen Lösungen zurück. In der Liste stehen jetzt alle gültigen Graphen für die übergebene Parameterkombination aus Knoten und extra Kanten. 

\begin{lstlisting}[language=python, label=cgpseudo, caption={Pseudocode calculateGraphs}  ]
List<Graph> calculateGraphs(List<Graph> graphs, int edgesLeft){
    if(edgesLeft<=0) return graphs;
    List<Graph> newGraphs = new ArrayList<>();
    for each Graph graph in graphs:
    	for each Node n1 in graph.getNodes():
    		for each Node n2 in graph.getNodes():
    			Graph newGraph= new Graph(graph);
    			if neuer Node kann an n angebunden werden:
    				verbind n1 mit n2 in newGraph    							newGraphs.add(newGraph);
    return calculateGraphs(newGraphs,edgesLeft-1);
}
\end{lstlisting}



### RoomG

RoomG nutz Templates von Räumen und verändert diese mithilfe von Versatzstücken um abwechslungsreiche Räume zu erstellen. Dabei sind sowohl die Raum-Templates als auch die Versatzstücke per Hand erstellt und werden aus einer Json eingelesen. 

Raum-Templates halten das Layout des Raumes als zwei dimensionales Integer-Array. Jedes Feld im Array stellt ein Feld im Level dar. Der Wert des Feldes gibt um welchen Feldtypen es sich handelt. 

| Wert | Feldtyp  | Beschreibung                                                 |
| ---- | -------- | ------------------------------------------------------------ |
| 0    | Boden    | Auf diesen Feld können Gegenstände platziert werden und Monstern sowie Helden sich bewegen. |
| 1    | Wand     | Eine Wand im Level durch die der Held und Monster nicht durchlaufen können. |
| 2    | Ausgang  | Dieses Feld führt zum nächsten Level.                        |
| -1   | Wildcard | Dieses Feld muss durch Replacements ersetzt werden.          |

Wildcards erlauben es, aus einen Template mehrere unterschiedliche Räume zu erstellen und reduzieren daher den Aufwand bei der Erstellung von Räumen. 

Damit ein Room-Template als Raum im Dungeon verwendet werden kann, müssen alle Wildcards durch gültige Feldertypen ersetzt werden. Dafür werden Replacements verwendet.

Replacements halten ebenfalls Layouts bestehend aus den bekannten Feldertypen ab. Zusätzlich zu den bekannten Feldertypen haben sie einen weitern Typ `Skip = -2`. Dieser wird im ersetzungsprozess genutzt um anzugeben das dieses Feld im Replacment übersprungen werden muss. 


Beim Ersetzungsprozess wird das Room-Template nach Wildcards durchsucht, wurde eine Wildcard gefunden wird diese durch ein Replacement ersetzt. Dabei ersetzt ein Replacement nicht ein einziges Feld sondern einen gesamten Teilbereich des Room-Layouts. Daher muss beim Ersetzungsprozess geschaut werden, ob das Layout des Replacements an die entsprechende Stelle im Room-Layout passt. Ein Replacement passt genau dann, wenn die obere linke ecke des Replacements-Layouts auf die gefundene Wildcard im Room-Layout platziert werden kann und sich unter jedem Feld im Replacment-Layout, das nicht den Wert `Skip` hat eine Wildcard im Room-Layout befindet. Passt ein Replacement, können die Wildcards mit dem Werten aus dem Replacement-Layout ersetzt werden. Da in einem Replacement wiederum Wildcards enthalten sein können, muss die Suche nach Wildcards nach jedem Ersetzungsprozess neugestartet werden. In Ersetzungsprozess werden immer nur Wildcards ersetzt, niemals andere Feldtypen. Die Suche nach Wildcards und das Ersetzten dieser muss solange durchgeführt werden, bis es keine Wildcards mehr im Room-Layout gibt. Sollte es Wildcards geben, für die kein passendes Replacement gefunden werden kann, werden diese durch Bodenfelder ersetzt.

Um mehr Optische Abwechsung zu erlauben können sowohl RoomTeplates als auch Replacements mit DesignLabel markiert werden. Nur wenn das DesignLabel eines Replacment mit dem des RoomTemplates überseinsteimmen, können diese zusammen verwendet werden. DesignLabels können genutzt werden um bestimmte Layouts nur in bestimmten Regionen, wie Wald oder Gebirge, anzuwenden. So können sich die Verschiedenen Regionen nicht nur optisch sondern auch strukturel voneinander unterscheiden.  

Damit die Lösbarkeit der Level gewährleistet werden kann, ist bei der Erstellung von Templates und Replacments darauf zu achten, dass keine Unerreichbare Felder in den Layouts enthalten sind. 

### LevelG

- auch Tile erklären

### Integration in das PM-Dungeon

In diesen Abschnitt wird erläutert wie die vorher vorgestellten Inhalte in das PM-Dungeon-Framework integriert werden. Dabei geht es hier konkret darum, wie DungeonG in das Projekt eingebunden wird, wie die Level gezeichnet werden und wie Inhalte im Level platziert werden können. Im Abschnitt *Schnittstellen* werden dann weitere, über die Grundlagen hinausgehende, Funktionen vorgestellt. 

Im Rahmen dieser Arbeit ist es nicht möglich, auf alle Einzelheiten des PM-Dungeon-Frameworks einzugehen, daher wird die Integration nur grobgranular beschrieben und an nötigen stellen abstrahiert. Designentscheidungen des Frameworks werden hier nicht begründet. 

Das Framework ist Modular aufgebaut und besitzt ein Modul, welches für alle Level-Angelegenheiten zuständig ist. In diesem Modul kann DungeonG eingebunden werden. Als Schnittstelle zwischen DungeonG und Framework dient die Klasse `LevelAPI`. Diese Klasse verwaltet und lädt das Level und stößt Zeichenprozess an. 

Um das Level zu laden werden verschiedene Funktionen angeboten, mit dem Knotenanzahl, Kantenanzahl und Design des Level bestimmt werden können. Mit diesen Parametern startet `LevelAPI` den Generierungsprozess von `DugeonG` und speichert das erzeugte Level als `currentLevel` ab. 

Das Level muss in jedem Frame neu gezeichnet werden. Um das Level zu zeichnen benötigen die einzelnen Felder im Level eine Textur. Die Textur ist das aussehen des Feldes. Die Klasse `TileTextureFactory` sucht die passende Textur für ein Tile. Die Entscheidung welche Textur dem Tile zugeordnet wird, hängt von davon ab ob es sich um ein Boden- oder Wandfeld handelt und welche Typen von Felder sich über, unter, links und rechts vom betrachteten Tile befinden. So kann entschieden werden ob es sich um eine Ecke, eine grade Wand, ein Boden, eine T-Kreuzung etc. handelt. Je nachdem welches DesignLabel gesetzt ist, werden dann die entsprechenden Texturen ausgewählt. 

Um das Level zu zeichnen, iteriert die `LevelAPI` über jeden Raum im Level. In jedem Raum wird jedes Tile ausgewählt und die im Tile gespeicherte Textur an der Globalen-Koordinate des Tile gezeichnet. Felder im Level, die kein Tile halten, werden nicht gezeichnet und werden daher schwarz dargestellt. Dies sind Felder die außerhalb des Spielbereiches liegen.

Um Objekte im Level zu verteilen, kann ein zufälliges Tile in einen bestimmten oder einen zufälligen Raum ausgewählt werden und die Position des Objekt auf die Position des ausgewählten Tile zu setzten. Der Zeichenprozess des Level wird immer vor dem Zeichenprozess der Objekte ausgeführt, daher werden die Texturen der Objekte über die Texturen des Level gezeichnet und werden so nicht überdeckt. 

  

### Schnittstellen

Neben der fundamentalen Integration in das PM-Dungeon werden auch verschiedene Methoden zur Verfügung gestellt um das arbeiten mit dem Level zu vereinfachen. Diese Methoden dienen als Schnittstelle zwischen Levelstruktur und Studierenden. Im folgenden werden die Methoden der Schnittstelle, deren Funktionsweise sowie mögliche Anwendungsfälle skizziert. 

`getAllPath(start,goal)`

Diese Methoden findet mithilfe des Graphen alle Pfade vom übergebenen Startknoten bis zum übergebenen Zielknoten.   

Damit können Wege bestimmt werden. Denkbar wären zum Beispiel Monster die sich durch mehrere Räume des Level bewegen um den Spieler zu verfolgen oder aus verschiedenen Richtungen anzugreifen. Die Methode wird auch von weiteren Methoden der Schnittstelle genutzt. 

- **todo** Wie funktioniert die Theorie dahinter

`getCriticalNodes`

Diese Methode findet mithilfe des Graphen alle Knoten die betreten werden müssen um vom Start des Level bis zum Ende zu gelangen. Dieses unterschiedet sich insofern von `getOnePath` , dass mithilfe von `getAllPath` alle Wege vom Start zum Ziel gefunden werden und nur die Räume, die in jedem möglichen Pfad enthalten sind als krtisch betrachtet werden. So kann sichergestellt werden, dass der Spieler zum beenden des Level in jedem Fall diese Knoten betritt.

In kritischen Knoten könne Inhalte platziert werden, die der Spieler auf jeden Fall finden bzw. erleben soll. Dies können für die Geschichte Relveante Inhalte sein, ein Tutorial oder ein Bossgegner. Dies ermöglicht es, das Pacing des Spiels zu kontrollieren. 

`getOptionalNodes` funktioniert analog zu `getCriticalNodes` und liefert eine Liste mit allen Knoten, die nicht auf dem Weg zum Ziel liegen. Diese Knoten sind daher in keinem Pfad der von `getAllPath` vom Startraum bis zum Zielraum enthalten. 

In sicher optionalen Räumen können verschiedene Inhalte platziert werden die der Spieler finden bzw. erleben kann aber nicht muss. Dies können extra Schätze sein oder ein optionaler Bossgegner. Dies ermöglich das erzeugen von Risk and Reward Momenten und ermöglicht es das Balancing des Spiels zu kontrollieren.

`isRoomReachableWithout(start,goal,avoid)` 

Diese Methode prüft ob der Zielraum vom Startraum aus betreten werden kann, ohne den `avoid` Knoten zu betreten. Damit können Schlüsselrätsel implementiert werden. Es wäre denkbar, dass der `avoid` Raum verschlossen ist und der Schlüssel sich in einen anderen Raum im Dungeon befindet. Mithilfe dieser Methode kann sichergestellt werden, dass der Schlüssel gefunden werden kann ohne durch den verschlossenen Knoten gehen zu müssen. 

Die Methode verwendet das selbe Verfahren wie `getAllPath` verwendet aber eine Kopie des Level-Graphen aus dem der `avoid` Knoten und entsprechend alle Kanten die zu `avoid` führen, entfernt wurden. Wird ein oder mehrere Pfade gefunden, muss `avoid` nicht betreten werden um von `start` zu `goal` zu gelangen.




