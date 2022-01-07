# Eigenes Konzept

In diesem Kapitel wird ein eigenes Konzept zur Implementierung eines prozeduralen graphenbasierten 2D-Level-Generator vorgestellt. Der konzeptionierte Generator soll nicht nur gute Level erzeugen, sondern auch in das PM-Dungeon integriert werden können und für die Studierenden nutzbar gemacht werden. Der Generator soll die in Kapitel 3 vorgestellten Algorithmen verwenden und miteinander kombinieren, um effektiv gute Level zu generieren. Im nächsten Abschnitt werden weitere spezifische Anforderungen für den Generator aufgestellt, die sich vor allem auf die Integration des Generators in das PM-Dungeon fokussieren. Um die Besonderheiten des in dieser Arbeit konzeptionierten Algorithmus hervorzuheben, findet in Abschnitt 4.2 eine Abgrenzung zu anderen prozeduralen Algorithmen statt, bevor im letzten Abschnitt das modulare Bausteinkonzept für den Generator vorgestellt und das Konzept zur Implementierung des Generators präsentiert wird. 

## Zielsetzung und Anforderungen an das Projekt 

Ziel dieser Arbeit ist es, ein Generator zu entwickeln, der abwechslungsreiche und spaßige Level erstellt und in das PM-Dungeon-Framework integriert werden kann. Daher sind die Anforderungen an den Generator zweigeteilt: Auf der einen Seite muss der Generator gute Level erzeugen, dafür wurden im Kapitel 2.6 verschiedene Regeln für gutes Leveldesign aufgestellt. 

1. Gute Level sind lösbar und fehlerfrei
2. Gute Level fordern die Mechaniken des Spiels
3. Gute Level sind gut gebalanced
4. Gute Level haben Risk and Reward Momente
5. Gute Level steuern das Pacing des Spiels
6. Gute Level sind einzigartig
7. Gute Level sind effizient in der Herstellung

Die vom Generator erzeugten Level sollten all diese Regeln beachten und der Generator muss Schnittstellen anbieten, um die Einhaltung der Regeln zu gewährleisten. 

Auf der anderen Seite der Anforderungen steht die Integration in das PM-Dungeon-Framework. Das bedeutet, dass der Generator nahtlos in das bestehende Framework eingegliedert werden können muss und die Anforderungen des Frameworks berücksichtigt. Dies schließt neben dem einhalten der Code Konvention, Kompatibilität der verwendeten Buildtools und Libaries vor allem auch die Testbarkeit des Generators mit ein. Der Code sollte so konzeptioniert werden, dass eine sehr gute Testabdeckung mithilfe von JUnit möglich ist. Tests sollten für alle essenziellen Bestandteile geschrieben und dokumentiert werden. Alle Codeteile sollten ausreichend dokumentiert und kommentiert sein. Schnittstellen müssen umfangreich und verständlich dokumentiert sein. Der grundsätzliche Projektaufbau des Frameworks muss beachtet werden. 

Da der eigentliche Fokus des PM-Dungeon nicht die Entwicklung eines Spiels ist, sondern das erlernen und vertiefen der Programmierkenntnisse, muss der Generator ohne Inputdaten der Anwender funktionieren. Parameterkonfigurationen sind zwar erwünscht, es sollte jedoch immer eine Standardkonfiguration angeboten werden. Der Code sollte, wenn möglich modular aufgebaut sein, damit zukünftige Verbesserungen und Änderungen schnell integriert werden können. Außerdem erlaubt eine modulare Gestaltung den interessierten Studierenden eigene Inhalte in den Generator hinzuzufügen oder Teile auszutauschen. Der Generator muss weder Zeit- noch Speicherplatzeffizient sein. Sollte es der Lesbarkeit des Codes zugutekommen, ist langsamerer, speicheruneffizienter Code zu bevorziehen. Die Generierung von Level im dreidimensionalen Raum ist nicht Ziel dieser Arbeit und stellt keine Anforderung an den Generator dar. 

## Abgrenzung zu anderen Levelgeneratoren und prozeduralen Algorithmen 

Der Generator in dieser Arbeit unterscheidet sich vor allem durch die unterschiedlichen Anwenderzielgruppen zu den anderen in dieser Arbeit vorgestellten oder in der Praxis angewendeten Algorithmen. Während die bekannten Verfahren so konzeptioniert sind, dass erfahrene Game- und Level-Designer sorgfältig gestaltete Inhalte erzeugen und mithilfe der Algorithmen abwandeln, um effizient Level zu erzeugen, muss der Generator für das PM-Dungeon ohne Inhalte von Designern auskommen. Auch wenn die Qualität der Level ein wichtiges Kriterium ist, so ist die Bedienbarkeit und Integration des Generators wichtiger als bei anderen Algorithmen. Während andere Generatoren nur Teilaspekte der Generierung abdecken, wie Level-Struktur oder Gestaltung der Räume, deckt der Generator dieser Arbeit alle Aspekte der Generierung ab. Dieser Generator stellt weniger Konfigurationsmöglichkeiten zur Verfügung als andere Generatoren, ermöglicht es aber Anwender aus dem Programmierbereich einfach abwechslungsreiche Level zu erzeugen. Der Generator ist für die Anwendung durch Programmierer und nicht für die Anwendung durch Designer optimiert.

## Darstellung des Konzeptes

Der Dungeongenerator **DungeonG** wird mithilfe der im vorherigen Kapitel vorgestellten Algorithmen umgesetzt und besteht im Wesentlichen aus drei Bausteinen.
Baustein eins ist der Graphgenerator **GraphG**, welcher einen planaren Graphen generiert.
Baustein zwei ist der Raumgenerator **RoomG**, welcher verschiedene Räume aus vorgegebenen Templates erzeugt. 
Baustein drei ist der Levelgenerator **LevelG**, welcher den von GraphG erzeugten Graphen nimmt und diesen mithilfe der Räume von RoomG in ein spielbares Level verwandelt. 

Im Folgenden werden die konkreten Aufgaben und Konzepte zur Umsetzung der einzelnen Bausteine und deren Kombination miteinander beschrieben. Ebenso wird beschrieben, wie DungeonG in das PM-Dungeon integriert werden kann und welche Änderungen am bisherigen PM-Dungeon dafür notwendig sind. Da den Studierenden verschiedene Optionen zur Interaktion mit den erzeugten Level ermöglicht werden soll, wird auch eine umfangreiche Schnittstelle hierfür definiert. 

### GraphG

GraphG erzeugt planare Graphen. Dabei soll die Anzahl der Knoten und Kanten beim Aufruf bestimmbar sein. Um eine vom PM-Dungeon unabhängige Visualisierungsmöglichkeiten zu haben, soll der Graph in *DOT-Formatierung*\footnote{DOT ist eine Beschreibungssprache für die visuelle Darstellung von Graphen} exportierbar sein. 

Um sicherzustellen, dass die erzeugten Graphen planar sind, beachtet GraphG den Satz von Kuratowski und verhindert die Generierung von $K5$ und $K3,3$ Teilgraphen indem bei der Generierung maximal vier Knoten mit drei oder mehr Nachbarn erzeugt werden.

Abbildung \ref{graphgSeq} zeigt ein reduziertes Sequenzdiagramm zur Darstellung des grundlegenden Ablaufes von GraphG. Beim Aufruf von `GraphG#generateGraphs` wird die gewünschte Anzahl an Knoten und extra Kanten übergeben. Da GraphG nur Graphen erzeugt, in dem jeder Knoten verbunden ist, ergibt sich $Kantenanzahl=Knotenanzahl-1+Anzahl Extra Kanten$. Dies liegt daran, dass die Generierung von GraphG zweigeteilt ist. Im ersten Teil `GraphG#calculateTrees` werden alle gültigen\footnote{ Ein Baum bzw. Graph ist genau dann gültig, wenn er maximal vier Knoten mit mehr als drei oder mehr Nachbarn hat.} Bäume mit der gewünschten Anzahl der Knoten erzeugt, auf die genauere Funktionsweise von `GraphG#calculateTrees` wird weiter unten eingegangen. Jeder erzeugte Baum hat $Knotenanzahl-1$ Kanten. Im zweiten Teil wird aus jedem Baum jeder gültiger Graph erzeugt, indem in `GraphG#calculateGraphs` die extra Kanten in die Bäume eingezeichnet werden, auf die genauere Funktionsweise von `GraphG#calculateGraphs` wird weiter unten eingegangen. Am Ende wird eine Liste mit allen gültigen Graphen mit der gewünschten Anzahl an Kanten und Knoten übergeben.

![UML-Sequenzdiagramm für GraphG ohne Einblick in die Rekursiven Methoden. \label{graphgSeq}](figs/chapter4/graphgSeq.png)

GraphG generiert die Graphen daher nicht per Zufall, sondern verwendet Breitensuche, um alle gültigen Lösungen zu finden. Im Vergleich zur Zufallssuche hat dies den Vorteil, dass sichergestellt werden kann, dass alle gültigen Lösungen generiert werden und der Rechen- und Speicheraufwand für gleiche Parameterkombination konstant bleibt. Die Zufallssuche kann zwar in der Theorie schneller eine gültige Lösung finden, dies kann aber nicht sichergestellt werden. Im Rahmen dieser Arbeit wurden auch mit der Zufallssuche experimentiert. Es hat sich herausgestellt, dass die Zufallssuche zwar in Suchräumen mit vielen Lösungen schnell eine solche finden kann, jedoch in Suchräumen mit wenigen Lösungen nur sehr langsam eine Lösung findet. Daher eignet sich die Zufallssuche nicht für den Anwendungsfall PM-Dungeon. 

Da GraphG alle Lösungen für eine Parameterkombination findet, können diese abgespeichert werden und können beim nächsten Aufruf ohne Suche verwendet werden. GraphG speichert alle gefundenen Lösungen für eine Parameterkombination in einer JSON-Datei ab und mithilfe von `GraphG#getGraph(int knoten, int edges)` kann ein zufälliger, vorher gefundener, Graph aus der JSON-Datei abgefragt werden. Dieses Vorgehen spart vor allem Rechenleistung, welche im laufenden Spiel unter Umständen benötigt wird, setzt aber die JSON-Dateien voraus. Da GraphG beides ermöglicht, können die Studierenden ihr bevorzugtes Verfahren verwenden. 

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

RoomG nutzt Templates von Räumen und verändert diese mithilfe von Versatzstücken, um abwechslungsreiche Räume zu erstellen. Dabei sind sowohl die Raum-Templates als auch die Versatzstücke per Hand erstellt und werden aus einer JSON-Datei eingelesen.  

Raum-Templates halten das Layout des Raumes als zwei dimensionales Integer-Array. Jedes Feld im Array stellt ein Feld im Level dar. Der Wert des Feldes gibt an, um welchen Feldtypen es sich handelt. 

| Wert | Feldtyp  | Beschreibung                                                 |
| ---- | -------- | ------------------------------------------------------------ |
| 0    | Boden    | Auf diesem Feld können Gegenstände platziert werden und Monster sowie Helden sich bewegen. |
| 1    | Wand     | Eine Wand im Level durch die der Held und Monster nicht durchlaufen können. |
| 2    | Ausgang  | Dieses Feld führt zum nächsten Level.                        |
| -1   | Wildcard | Dieses Feld muss durch Replacements ersetzt werden.          |

Wildcards erlauben es, aus einem Template mehrere unterschiedliche Räume zu erstellen und reduzieren daher den Aufwand bei der Erstellung von Räumen. 

Damit ein Room-Template als Raum im Dungeon verwendet werden kann, müssen alle Wildcards durch gültige Feldertypen ersetzt werden. Dafür werden Replacements verwendet.

Replacements halten ebenfalls Layouts bestehend aus den bekannten Feldertypen ab. Zusätzlich zu den bekannten Feldertypen haben sie einen weiteren Typ `Skip = -2`. Dieser wird im Ersetzungsprozess genutzt, um anzugeben, dass dieses Feld im Replacment übersprungen werden muss. 


Beim Ersetzungsprozess wird das Room-Template nach Wildcards durchsucht, wurde eine Wildcard gefunden wird diese durch ein Replacement ersetzt. Dabei ersetzt ein Replacement nicht ein einziges Feld, sondern einen gesamten Teilbereich des Room-Layouts. Daher muss beim Ersetzungsprozess geschaut werden, ob das Layout des Replacements an die entsprechende Stelle im Room-Layout passt. Ein Replacement passt genau dann, wenn die obere linke Ecke des Replacement-Layouts auf die gefundene Wildcard im Room-Layout platziert werden kann und sich unter jedem Feld im Replacement-Layout, das nicht den Wert `Skip` hat, eine Wildcard im Room-Layout befindet. Passt ein Replacement, können die Wildcards mit den Werten aus dem Replacement-Layout ersetzt werden. Da in einem Replacement wiederum Wildcards enthalten sein können, muss die Suche nach Wildcards nach jedem Ersetzungsprozess neu gestartet werden. In Ersetzungsprozess werden immer nur Wildcards ersetzt, niemals andere Feldtypen. Die Suche nach Wildcards und das Ersetzten dieser Felder muss so lange durchgeführt werden, bis es keine Wildcards mehr im Room-Layout gibt. Sollte es Wildcards geben, für die kein passendes Replacement gefunden werden kann, werden diese durch Bodenfelder ersetzt.

Um mehr optische Abwechslung zu erlauben, können sowohl Room-Templates als auch Replacements mit Design-Label markiert werden. Nur wenn das Design-Label eines Replacment, mit dem des Room-Templates übereinstimmen, können diese zusammen verwendet werden. Design-Labels können genutzt werden, um bestimmte Layouts nur in bestimmten Regionen, wie Wald oder Gebirge, anzuwenden. So können sich die verschiedenen Regionen nicht nur optisch, sondern auch strukturell voneinander unterscheiden.  

Damit die Lösbarkeit der Level gewährleistet werden kann, ist bei der Erstellung von Templates und Replacments darauf zu achten, dass keine unerreichbaren Felder in den Layouts enthalten sind. 

### LevelG

LevelG verwendet GraphG und RoomG um die spielbaren Level zu erzeugen. Es nimmt die Raum-Templates und ordnet sie so an, dass die Anordnung der Struktur des Graphen entspricht. Dafür nutzt LevelG das aus Kapitel 3.1 bekannte Verfahren. 

Zuerst wird der Graph mithilfe des Algorithmus aus Kapitel 2.3.2 in Chains aufgeteilt. In einer Chain hat jeder Knoten maximal zwei Nachbarn. Mithilfe der Chains lassen sich die Knoten in eine Reihenfolge bringen, die der Reihenfolge entspricht, in der die Knoten aufgelöst\footnote{Auflösen meint, ein Raum im Level platzieren, der den Knoten im Graphen repräsentiert} werden. Zuerst werden die Knoten aus dem kleinsten Zyklus in die Liste gepackt, dann werden die benachbarten Chains in Breite betrachtet und in Reihenfolge der Zyklen-Größe dazu gepackt. Dabei gilt, zuerst kleine Zyklen, dann größere Zyklen und am Ende alle Chains, die keine Zyklen bilden. Dieser Vorgang wird wiederholt, bis alle Chains betrachtet wurden und alle Knoten in der Liste hinzugefügt wurden.

Um die Knoten aufzulösen werden die Configuration-Spaces berechnete. Ein Configuration-Space gibt dabei an, an welche Stelle im Level ein bestimmtes Raum-Template liegen kann und dabei alle  Bedingungen erfüllt. Eine Gruppe aus zusammengehörigen Configuration-Spaces bilden dann ein Level. Damit ein Configuration-Space gültig ist, darf das Raum-Template sich nicht mit einem anderen Template überschneiden und der Raum muss an alle im Graphen eingezeichneten Nachbarn anliegen. Anliegen tun zwei Raum-Templates genau dann, wenn die Außenwände aufeinanderliegen. Überschneiden tun sich zwei Templates, wenn Felder des einen Template im Layout des anderen Templates liegen.

Um die Position zu bestimmen werden lokale und globale Koordinaten genutzt. Jedes Raum-Template hat einen lokalen Referenzpunkt. Beim Platzieren des Templates im Level, wird dieser Referenzpunkt auf eine globale Koordinate gelegt, damit kann dann bestimmt werden, wo genau die Felder des Templates im Level liegen. Ein Configuration-Space gibt also an, wo der lokale Referenzpunkt eines bestimmten Raum-Templates im globalen System liegt.

Listing \ref{backtrack} zeigt in Pseudocode, wie alle Knoten mithilfe des inkrementellen Verfahren und Backtracking aufgelöst werden können. Der Methode wird zu einem die Liste der noch zu platzierenden Knoten als auch die bereits gefundenen Configuration-Spaces übergeben. In Zeile 2 wird geprüft, ob es noch Knoten zum Platzieren gibt, sind alle Knoten platziert, wurde eine gültige Zusammenstellung gefunden, welche sich aus der Liste der Configuration-Spaces ergibt. 
Sind noch Knoten übrig zum Auflösen, geht der Algorithmus weiter. In Zeile 3 wird sich der nächste Knoten zum Auflösen aus der Liste genommen. In Zeile 4 werden alle gültigen Configuration-Spaces für den Knoten abgefragt. In `getAllCF` werden dabei die Configuration-Spaces für alle verfügbaren Raum-Templates unter Berücksichtigung der oben genannten Bedingungen berechnet. In Zeile 5 wird geprüft, ob es mindestens einen gültigen Configuration-Space für den Knoten gibt, ist dies nicht der Fall, gibt die Methode `null` zurück und signalisiert damit, dass Backtracking durchgeführt werden muss, also dass andere Configuration-Spaces für die vorherigen Knoten benutzt werden müssen. Gibt es gültige Configuration-Spaces werden diese in Zeile 6 in eine zufällige Reihenfolge gebracht, um auch bei gleichbleibenden Graphen und Templates unterschiedliche Level erzeugen zu können. In Zeile 7 bis 11 wird über jeden gültigen Configuration-Space iteriert. Per Tiefensuche wird mit jedem Configuration-Space nach einer Endlösung gesucht. In Zeile 11 wird geprüft, ob im betrachteten Zweig eine Lösung gefunden wurde, wenn ja, ist dies die Endlösung, wenn nicht, wird der nächste Zweig betrachtet. Führt kein Zweig zu einer Lösung, wird in Zeile 12 `null` zurückgegeben und damit signalisiert, dass Backtracking durchgeführt werden muss.

\begin{lstlisting}[label=backtrack, caption={Pseudocode um inkrementell alle Knoten aufzulösen.}  ]
getLevelCS (List<Node> toPlace, List <CS> partSolution): 
	if (toPlace.isEmpty()) return partSolution; //this is the final solution
	Node thisNode = toPlace.get(0);
	List<CF> spaces = getAllCF(thisNode,partSolution);
	if(spaces.isEmpty()) return null; //no solution. Backtrack if possible. 
	spaces.shuffle();
	for (CS cs: spaces):
		List<CS> thisPartSolution=partSolution.copy();
		thisPartSolution.add(cs);
		List<CS> solution = getLevelCS(toPlace-thisNode,thisPartSolution)
		if(solution!=null) return solution; //this is the final solution
	return null; //no solution. Backtrack if possible. 
\end{lstlisting}


Die gelegten Räume müssen nun durch Durchgänge miteinander verbunden werden. Durchgänge werden zwischen jedem Raum platziert, die miteinander verbunden werden sollen. Der Graph gibt an, zwischen welchen Räumen es einen Durchgang geben soll. Ein Durchgang ist 3-Felder groß und besteht aus zwei Wandfeldern und einem Bodenfeld dazwischen. Es wird nach allen möglichen Positionen gesucht, an dem der Durchgang platziert werden kann und damit die Räume miteinander verbindet. Sind mehrere Positionen möglich, wird davon eine zufällige ausgewählt. Beim Platzieren des Durchgangs werden die Tiles der Räume durch die Tiles des Durchganges ersetzt. 

Das gelegte Level in Form der Menge an Configuration-Spaces muss nun noch in ein spielbares Level umgewandelt werden. Dafür werden die Raum-Templates der einzelnen Configuration-Spaces in konkrete Räume umgewandelt. Dazu werden zuerst die Wildcards im Template durch Replacer ersetzt (vgl. vorherigen Abschnitt). Danach werden die bisher noch abstrakten Felder in Tiles umgewandelt. Ein Tile ist ein bestimmter Feldtyp mit einer Textur und einer globalen Position im Level. Die globale Position wird anhand der Position des Configuration-Spaces sowie mithilfe des lokalen Referenzpunktes des Raum-Templates bestimmt.

Ein `Level` besteht aus der Liste an umgewandelten Räumen, der Knotenliste des Graphen um die Struktur des Levels darzustellen und einem Start- sowie Endfeld. Diese Felder werden durch zufällige Auswahl eines Bodenfeldes im Level bestimmt. 

Durch die zufällige Kombination aus Room-Templates und Replacern könnte es dazu kommen, dass ein Level nicht lösbar ist, also das End-Tile nicht vom Start-Tile aus erreichbar ist, weil eine Wand im Weg ist. Um diese Level nicht auszugeben, können verschiedene Wegfindungs-Algorithmen eingesetzt werden. LibGDX stellt  den A*-Algorithmus zur Verfügung, daher wird dieser genutzt, um jedes Level abschließend auf Lösbarkeit zu überprüfen. Nur lösbare Level werden zurückgegeben. 

### Integration in das PM-Dungeon

In diesen Abschnitt wird erläutert, wie die vorher vorgestellten Inhalte in das PM-Dungeon-Framework integriert werden. Dabei geht es hier konkret darum, wie DungeonG in das Projekt eingebunden wird, wie die Level gezeichnet werden und wie Inhalte im Level platziert werden können. Im Abschnitt *Schnittstellen* werden dann weitere, über die Grundlagen hinausgehende, Funktionen vorgestellt. 

Im Rahmen dieser Arbeit ist es nicht möglich, auf alle Einzelheiten des PM-Dungeon-Frameworks einzugehen, daher wird die Integration nur grob granular beschrieben und an nötigen Stellen abstrahiert. Designentscheidungen des Frameworks werden hier nicht begründet. 

Das Framework ist modular aufgebaut und besitzt ein Modul, welches für alle Level-Angelegenheiten zuständig ist. In diesem Modul kann DungeonG eingebunden werden. Als Schnittstelle zwischen DungeonG und Framework dient die Klasse `LevelAPI`. Diese Klasse verwaltet und lädt das Level und stößt Zeichenprozess an. Um DungeonG für `LevelAPI` verwendbar zu machen, muss `LevelG` das Interface `IGenerator`\footnote{*IGenerator* auf GitHub: https://github.com/PM-Dungeon/pmdungeon/blob/master/code/core/src/level/generator/IGenerator.java} implementieren. Das Interface gibt Methoden vor, um Level mit verschiedenen Parametern zu erstellen. 

Das Level muss in jedem Frame neu gezeichnet werden. Um das Level zu zeichnen, benötigen die einzelnen Felder im Level eine Textur. Die Textur ist das Aussehen des Feldes. Die Klasse `TileTextureFactory` sucht die passende Textur für ein Tile. Die Entscheidung welche Textur dem Tile zugeordnet wird, hängt davon ab, ob es sich um ein Boden- oder Wandfeld handelt und welche Typen von Felder sich über, unter, links und rechts vom betrachteten Tile befinden. So kann entschieden werden, ob es sich um eine Ecke, eine grade Wand, ein Boden, eine T-Kreuzung etc. handelt. Je nachdem welches Design-Label gesetzt ist, werden dann die entsprechenden Texturen ausgewählt. 

Um das Level zu zeichnen, iteriert die `LevelAPI` über jeden Raum im Level. In jedem Raum wird jedes Tile ausgewählt und die im Tile gespeicherte Textur an der Globalen-Koordinate des Tile gezeichnet. Felder im Level, die kein Tile halten, werden nicht gezeichnet und werden daher schwarz dargestellt. Dies sind Felder, die außerhalb des Spielbereiches liegen.

Um Objekte im Level zu verteilen, kann ein zufälliges Tile in einen bestimmten oder einen zufälligen Raum ausgewählt werden und die Position des Objektes auf die Position des ausgewählten Tile zu setzten. Der Zeichenprozess des Levels wird immer vor dem Zeichenprozess der Objekte ausgeführt, daher werden die Texturen der Objekte über die Texturen des Levels gezeichnet und werden so nicht überdeckt.    


### Schnittstellen

Neben der fundamentalen Integration in das PM-Dungeon werden auch verschiedene Methoden zur Verfügung gestellt, um das Arbeiten mit dem Level zu vereinfachen. Diese Methoden dienen als Schnittstelle zwischen Level-Struktur und Studierenden. Im Folgenden werden die Methoden der Schnittstelle, deren Funktionsweise sowie mögliche Anwendungsfälle skizziert. 

`Level#getAllPath(start,goal)`: 

Diese Methoden findet mithilfe des Graphen alle Pfade vom übergebenen Startknoten bis zum übergebenen Zielknoten. Damit können Wege bestimmt werden. Denkbar wären zum Beispiel Monster, die sich durch mehrere Räume des Levels bewegen, um den Spieler zu verfolgen oder um aus verschiedenen Richtungen anzugreifen. Die Methode wird von weiteren Methoden der Schnittstelle genutzt. Zur Durchführung wird der in Kapitel 2.3.1 vorgestellte Graph-Serach Algorithmus genutzt. Eine Abwandlung von `getAllPath` ist `getShortestPath(start,goal)` diese Methode gibt nur den kürzesten Pfad, also den Pfad mit dem wenigsten Knoten zurück. 

`Level#getCriticalNodes`:

Diese Methode findet mithilfe des Graphen alle Knoten, die betreten werden müssen, um vom Start des Levels bis zum Ende zu gelangen. Dieses unterscheidet sich insofern von `getShortestPath`, dass mithilfe von `getAllPath` alle Wege vom Start zum Ziel gefunden werden und nur die Räume, die in jedem möglichen Pfad enthalten sind, als kritisch betrachtet werden. So kann sichergestellt werden, dass der Spieler zum Beenden des Levels in jedem Fall diese Knoten betritt. In kritischen Knoten könne Inhalte platziert werden, die der Spieler auf jeden Fall finden bzw. erleben soll. Dies können für die Geschichte relevante Inhalte sein, ein Tutorial oder ein Bossgegner. Dies ermöglicht es, das Pacing des Spiels zu kontrollieren. 

`Level#getOptionalNodes` funktioniert analog zu `getCriticalNodes` und liefert eine Liste mit allen Knoten, die nicht auf dem Weg zum Ziel liegen. Diese Knoten sind daher in keinem Pfad der von `getAllPath` vom Startraum bis zum Zielraum enthalten. In sicher optionalen Räumen können verschiedene Inhalte platziert werden, die der Spieler finden bzw. erleben kann, aber nicht muss. Dies können extra Schätze sein oder ein optionaler Bossgegner. Dies ermöglicht das Erzeugen von Risk and Reward Momenten und ermöglicht es das Balancing des Spiels zu kontrollieren.

`Level#isRoomReachableWithout(start,goal,avoid)`: 

Diese Methode prüft, ob der Zielraum vom Startraum aus betreten werden kann, ohne den `avoid` Knoten zu betreten. Damit können Schlüsselrätsel implementiert werden. Es wäre denkbar, dass der `avoid` Raum verschlossen ist und der Schlüssel sich in einem anderen Raum im Dungeon befindet. Mithilfe dieser Methode kann sichergestellt werden, dass der Schlüssel gefunden werden kann, ohne durch den verschlossenen Knoten gehen zu müssen. Die Methode verwendet `getAllPaths` und entfernt alle Pfade die `avoid` beinhaltet. Sind am Ende noch Pfade übrig, kann der Knoten `goal` von `start` erreicht werden ohne `avoid` zu betreten. 

`Level#isTileReachable(start,goal)`

Prüft ob das Feld `goal` vom Feld `start` aus erreichbar ist. Dafür wird der, in libGDX integrierte, A*-Algorithmus genutzt. Durch die vielzahl an unterschiedlichen Raum-Layouts und Replacments, können Bereiche im Level entstehen, die nicht erreichbar sind. Um an diesen Stellen keine Objekte zu platzieren, kann diese Methode genutzt werden. Da es eventuell gewollt ist, Inhalte in erstmal unerreichbare Orte zu platzieren, welche man dann erst Freilegen muss, z.B durch das sprengen von Wänden oder das verwenden einer Spruchrolle, sind unerreichbare Felder nicht pauschal als Fehler zu betrachten. 

