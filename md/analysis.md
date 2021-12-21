# Algorithmen zur prozeduralen Generierung

In diesem Kapitel werden Algorithmen vorgestellt, die verschiedene Aspekte der prozeduralen Level-Generierung abdecken. Nicht jeder vorgestellter Algorithmus ist für die Generierung von Level konzeptioniert, kann aber für Teilaspekte verwendet werden. Es werden Algorithmen zur Generierung und Modifikation von planaren Graphen, Erzeugen von Level aus diesen Graphen sowie zum Erstellen von einzelnen Räumen vorgestellt. Ziel ist es, im nächsten Kapitel, die einzelnen Elemente der Algorithmen so zu kombinieren, dass das Resultat die in Kapitel 2 aufgestellten Anforderungen bestmöglich erfüllt. 


## Vom Graph zum Level 

Zwar kann das Layout eines Level mithilfe eines Graphen dargestellt werden, schlussendlich muss aus diesen Graphen aber eine physische Anordnung von Räumen und Strukturen generiert werden, die dann das eigentliche Level im Spiel ist. Die Arbeit von Ma et al stellt einen effizienten Algorithmus vor, um diese Umwandlung durchzuführen. [@Ma2014]

![Beispiel: Output für einen Graphen und Strukturen. \label{graph2level}[@Ma2014]](figs/chapter3/fromgraphtolevel.PNG)

Als Input werden dem Algorithmus zu einem der planare Level-Graph $G$ übergeben sowie ein Set aus 2D-Polygonalen Blöcken $B$ (vgl. Abbildung \ref{graph2level}). Diese Blöcke können als Räume des Level betrachtet werden und werden im Verlaufe des Algorithmus genutzt, um die Knoten im Graphen aufzulösen. Dabei ist zu beachten, dass der Algorithmus die Blöcke zufällig und mehrfach auswählt. Der Algorithmus erlaubt es nicht Bedingungen festzulegen, wie dass ein spezifischer Knoten durch einen spezifischen Block aufgelöst werden soll oder ein Block nur einmal verwendet werden soll. So lässt sich zum Beispiel kein spezifischer Bossraum mit einem einzigartigen Layout bestimmen. Wenn eine mögliche Lösung für $G$ mit $B$ gibt, ist diese der Output des Algorithmus. Durch sein inkrementelles Vorgehen, welches im weiteren Verlauf des Abschnittes beschrieben wird, ist der Algorithmus effizient in der Lage auch mehrere Lösungen für ein Input $G$ und $B$ zu finden. 

Der Algorithmus besteht im Wesentlichen aus zwei Schritten:

1. Zerlegen von $G$ in Subgraphen
2. Iteratives Auflösen der Knoten in den Subgraphen

Auflösung von Knoten meint, dass in der Darstellung von $G$ in physischer Anordnung der Blöcke aus $B$, also dem spielbaren Level, die Blöcke so platziert sind, dass die in $G$ dargestellten Verbindungen existieren, ohne dass sich die Blöcke überschneiden. Eine Verbindung existiert dann, wenn zwei Blöcke sich an einer Kante schneiden, ohne sich zu überlappen, und zusätzlich die Schnittlänge groß genug ist, um einen Durchgang zu erzeugen. Dazu wird ein *configuration Space* definiert. 

![Beispiel: configuration space. \label{confspace}[@Ma2014]](figs/chapter3/configurationspace.PNG)

Abbildung \ref{confspace} a) zeigt wie der configuration space für eine Verbindung von zwei Blöcken aussehen könnte. Beim Auflösen einer solchen Verbindung ist ein Block statisch, kann also nicht bewegt oder rotiert werden und der andere Block ist dynamisch, kann also bewegt und rotiert werden. In diesem Fall ist der mittlere (umgedrehtes L) Block statisch und der quadratische Block dynamisch. Im dynamischen Block wird ein Referenzpunkt bestimmt, in diesen Fall das Zentrum des Blocks. Die rote Linie ist der configuration space und zeigt nun alle möglichen Positionen, die der Referenzpunkt einnehmen kann, um die Verbindung gültig zu lösen. Abbildung \ref{confspace} b) zeigt wie eine Verbindung mit zwei statischen und einem dynamischen Block aufgelöst wird. Zuerst wird für jeden statischen Block der configuration space bestimmt, die Schnittpunkte beider configuration spaces (gelbe Punkte) sind die gültigen Positionen für den Referenzpunkt des dynamischen Blocks.

Das Berechnen des configuration spaces reduziert den lokalen Suchraum für die individuellen Knoten, jedoch ist der Suchraum für den gesamten Graphen weiterhin zu groß, um zuverlässig in einer angebrachten Zeit eine Lösung zu finden. Daher wird das Problem in kleinere, einfacher zu lösende, Teilprobleme aufteilt. Es hat sich herausgestellt, dass Graphen, in den jeder Knoten maximal zwei Nachbarn besitzt, einfacher aufzulösen sind, da die Anzahl der Kanten der Blöcke immer größer ist als die Anzahl der Kanten des Knotens im Graph. 

![Beispiel: Aufteilen eines Graphen in Chains. \label{how2chain}[@Nepozitek2019]](figs/chapter3/howtochain.PNG)

Der Input-Graph wird daher in kleinere Subgraphen aufteilt, sogenannten Chains. In einer Chain hat jeder Knoten maximal zwei Nachbarn. Abbildung \ref{how2chain} zeigt wie ein Graph in Chains aufgeteilt werden kann. Knoten mit derselben Nummerierung gehören zu einer Chain. Zuerst werden alle Faces im Graphen gesucht, da diese Kreise im Leveldesign darstellen. Kreise haben mehr Bedingungen als eine lineare Folge von Knoten, deswegen ist die Auflösung dieser komplexer. Daher werden Kreise bevorzugt am Anfang des Algorithmus aufgelöst, um späteres Backtracking zu vermeiden. Die erste Chain ist das kleinste Face im Graphen (Label "0"). Die weiteren Chains werden gebildet, indem per Breitensuche die weiteren Faces gesucht werden '(Label "1"). Stehen mehrere Faces zur Auswahl, wird zuerst das kleinere genommen. Ein Face ist kleiner als ein anderes Face, wenn weniger Kanten durchlaufen werden müssen, um es zu bilden. Wenn keine Faces mehr vorhanden sind, werden die restlichen Chains hinzugefügt (Label "2","3","4").  

Nachdem der Graph in Chains aufgeteilt wurde, können die einzelnen Chains inkrementell gelöst werden. Zuerst wird die erste Chain genommen und mit dem oben beschriebenen Verfahren aufgelöst. Der Algorithmus erzeugt jedoch nicht nur eine Lösung für die Chain, sondern mehrere Lösungen und speichert diese ab (vgl. Abbildung \ref{graphpartsolution}). Im nächsten Schritt wird die nächste Chain aus der Liste genommen, mit einer der Lösungen aus dem Vorschritt verbunden und aufgelöst. Sollte es keine Möglichkeit geben die Chain aufzulösen, wird per Backtracking eine andere Lösung aus dem Vorschritt ausgewählt und erneut versucht eine Lösung zu finden. Dieses Vorgehen wird wiederholt, bis alle Chains aufgelöst sind und dadurch eine Lösung für das vollständige Problem gefunden wurde. 

```python
Input: Planr graph G, bulding blocks B, layout stack S
procedure INCREMENTALLAYOUT(G,B,S)
	Push empty layout into S
	repeat
		s<-S.pop()
		Get the next chain c to add to s
		AddChain(c,s) //extend the layout to contain c
		if  extended partial layout were generated then
			Push new partial layouts into S
		end if 
	until target # of full layouts is generated or S is empty
end procedure
```
Peseudocode für das inkrementelle Erstellen des Level. 
Quelle [@Ma2014]

Zwar könnten die Chains auch separat aufgelöst werden und dann versucht werde die Teillösungen miteinander zu verbinden, jedoch würden dabei Lösungen erzeugt werden, die zwar die Chain auflösen aber nicht mit den gesamten Graphen kompatibel sind und daher unbrauchbar wären. Zusätzlich sind im Level alle miteinander verbunden, daher gibt es auch keine Vorteile die Chains einzeln zu lösen. 

Das Erstellen von mehren Lösungen für eine Chain hat mehrere Vorteile. Zu einem ermöglicht und erleichtert es das schrittweise Backtracking, falls eine Chain nicht in der aktuellen Lösung angeschlossen werden kann und zusätzlich können schneller mehrere gültige Lösungen für ein Graph gefunden werden, indem beispielsweise nach der dritten Chain eine andere Teillösungen verwendet wird. Abbildung \ref{graphsolution} zeigt wie aus verschieden Teillösungen unterschiedliche Gesamtlösungen entstehen. Dies unterstreicht noch einmal die Effizienz des Algorithmus, da bereits aus einem einzigen Input-Graphen viele vollkommen unterschiedliche Level entstehen könne. 

![Beispiel: Unterschiedliche Teillösungen für die selbe Chain. \label{graphpartsolution}[@Ma2014]](figs/chapter3/graphpatrsol.PNG)

![Beispiel: Unterschiedliche Teillösungen führen zu unterschiedlichen Gesamtlösungen. \label{graphsolution}[@Ma2014]](figs/chapter3/graphsolution.PNG)

### Vor- und Nachteile

Vorteil: Der Algorithmus kann viele Level aus einem Graphen und Set aus Räumen erstellen und ist daher sehr effizient in der Nutzung der Inputs.

Vorteil: Sofern der Input Graph einen kritischen Pfad zwischen Start und Ziel hat, kann der Algorithmus gewährleisten, dass alle ausgegebenen Level auch lösbar sind.

Vorteil: Die Level-Layouts unterscheiden sich selbst bei gleichen Input-Daten stark voneinander und können als einzigartig bezeichnet werden.

Nachteil: Der Algorithmus funktioniert nicht ohne Input-Daten, sowohl Graph als auch das Set aus Räumen müssen vorher erstellt werden, dies senkt die Effizienz des Algorithmus. 

Lösungsansatz: Ein weiterer Algorithmus zur Generierung planarer Graphen kann genutzt werden, um den Input-Grafen automatisch erstellen zu lassen. Auch ein Algorithmus zur automatischen Generierung von Input-Räumen wäre denkbar. Die vollständige automatisierte Generierung von Räumen stellt allerdings ein eigenes komplexes Problem dar, welches im Rahmen dieser Arbeit nicht besprochen wird. 

Nachteil: Es werden immer dieselben Räume verwendet, bei wenigen Input-Räumen oder einen Spieler der viel Zeit im Spiel verbringt wird diese sichtbar und die Einzigartigkeit der Level ist nicht mehr gegeben. 

Lösungsansatz: Auch hier könnte ein Algorithmus zur vollständigen Generierung von Räumen genutzt werden. Eine andere Möglichkeit wäre ein Algorithmus, der die Input-Räume anhand verschiedener Kriterien mutiert und so bereits aus wenigen Räume eine Vielzahl unterschiedlicher Räume erzeugt werden können. 

Nachteil: Der Algorithmus bietet keine Möglichkeit, um das Pacing zu kontrollieren, Risk and Reward Momente zu erzeugen oder das Spiel zu balancen. Es gibt keine Schnittstelle, um das Aussehen der Level oder das Level Layout abseits des Input-Graphen anzupassen. 

Lösungsansatz: Der Algorithmus kann als Grundlage für die Konzeptionierung eines Level-Generators genutzt werden. Die Konfiguration der einzelnen Aspekte findet dann bei der Erstellung des Graphen bzw. bei der Mutation der Räume statt.



## Planare Graphen generieren

Bekannte Verfahren zur Generierung von planaren Graphen wie das *plantri* Programm nutzen Triangulation oder Quadrangulation. [@Brinkmann] Die erzeugten Graphen zeichnen sich dadurch aus, dass jedes Face im Graph ein Dreieck bzw. ein Quadrat ist. Dieses Verfahren ist zwar besonders effizient unter Betrachtung der Rechenleistung, die erzeugten Graphen eignen sich aber kaum, um gute Videospiellevel darzustellen.

Abbildung \ref{plantri} zeigt zwei verschiedene Graphen, die mithilfe von *plantri* erzeugt werden können, einmal durch Triangulation und einmal durch Quadrangulation. Die Graphen zeichnen sich vor allem durch ihre zyklische Struktur aus, jeder Knoten kann auf mehrere Wege erreicht werden und es gibt keine klaren Endpunkte. Würde man zwei zufällige Punkte im Graphen als Start- und Endpunkt bestimmen und alle kritischen Pfade einzeichnen, wäre jede Kante markiert. Der Endpunkt kann daher durch viele verschiedenen Pfade erreicht werden. Was zunächst nach einem Vorteil klingt, da der Spieler so maximale Freiheit bei der Wahl seines Weges hat, stellt sich als Nachteil für das Pacing und Balancing heraus. Da (fast) jeder Knoten als kritisch und optional gleichzeitig betrachtet werden kann, ist es schwer Risk and Reward Momente zu platzieren, Monster gezielt auf kritischen bzw. optionalen Pfade zu platzieren oder sicherzustellen, dass der Spieler an kritischen Events vorbeimuss. Zusätzlich unterscheiden sich die Teilaspekte der Graphen im Grunde gar nicht, jedes Teilstück besteht entweder aus einem Dreieck oder einem Quadrat, daher würden die erzeugten Level sich überwiegend in der Größe der Level unterscheiden und nur wenig Variation im Layout aufweisen.

  ![Planare Graphen erzeugt durch Triangulation (links) und Quadrangulation (rechts) \label{plantri} [@Brinkmann] ](figs/chapter3/plantriexample.png)

Eine andere Möglichkeit zur Generierung von Planaren Graphen ist die kontrollierte Zufallssuche. Ein Algorithmus, der vollkommen zufällig einen Graphen erzeugt, würde auch planare Graphen erzeugen. Da dies weder ein zuverlässiger noch effizienter Weg zur Generierung ist, muss der Zufall so eingeschränkt werden, dass die Generierung von nicht planaren Graphen unmöglich wird.

Das Satz von Kuratowski sagt, dass ein Graph genau dann planar ist, wenn er keinen Teilgraph besitzt, der ein Unterteilungsgraph des $K5$ oder $K3,3$ ist. Ein Untereilungsgraph ist ein Graph, der dadurch entsteht, dass in einen Graphen $G$ neue Knoten durch Kantenunterteilung hinzugefügt werden. $K5$ und $K3,3$ sind zwei Graphen für die es keine planare Darstellung gibt (vgl. Abbildung \ref{k5} und Abbildung {k3}). [@Diestel2010]

Bei der Generierung eines planaren Graphen muss also darauf geachtet werden, dass weder $K5$ noch $K3,3$ enthalten sind. 

Die Untersuchung eines Graphen $G$ nach einem Teilgraphen der isomorph zu einem Graphen $H$ ist, ist ein NP-Vollständiges Problem.[@Cook1971] Besitzen $G$ und $H$ isomorphe Unterteilungsgraphen heißen diese homöomorph.[@wikipedia2016] Einen Graphen nach einem Teilgraphen zu durchsuchen der homöomorph zu $K5$ oder $K3,3$ ist, ist daher auch ein NP-Vollständiges Problem. Bei der Generierung des Graphen eine Untersuchung nach $K5$ oder $K3,3$ homöomorphen Teilgraphen durchzuführen ist daher kein effizientes Vorgehen. Es ist aber möglich, die Erzeugung von $K5$ oder $K3,3$ zu verhindern.

$K5$ besteht aus fünf Knoten mit jeweils vier Kanten. Ein Graph, indem es maximal vier Knoten mit vier oder mehr Kanten gibt, kann $K5$ daher nicht enthalten. 

$K3,3$ besteht aus sechs Knoten mit jeweils drei Kanten. Ein Graph, indem es maximal fünf Knoten mit drei oder mehr Kanten gibt, kann $K3,3$ daher nicht enthalten.

Daraus lässt sich ableiten, dass ein Graph, der maximal vier Knoten mit drei oder mehr Kanten hat, weder $K5$ noch $K3,3$ enthalten kann und daher planar ist. 

   ![Der Graph $K5$  \label{k5} [@wikipedia2021] ](figs/chapter3/k5.png)

  ![Der Graph $K3,3$ \label{k3} [@wikipedia2021] ](figs/chapter3/k33.png)

Abbildung \ref{mygenexample} zeigt einen Graphen, der nach den oben genannten Regeln generiert wurde. Die Knoten A und G wurden zufällig als Start- und Endpunkt festgelegt, die roten Kanten zeigen den kritischen Pfad, grüne Kanten optionale Pfade. Dieser Graph bietet im Vergleich zu den Graphen aus Abbildung \ref{plantri} einen klaren kritischen und mehrere klar optionale Pfade, wodurch die Platzierung von Risk and Reward Momenten, Monstern und anderen Elemente vereinfacht und kontrollierbarer wird. Negativ fällt auf, dass der Graph Backtracking enthält, Knoten K kann nur durch die Knoten A, H und J erreicht werden entsprechend nur über diese Knoten verlassen werden. Eine Kante zwischen K und I könnte dieses Problem beheben, da so ein Zyklus zwischen den Knoten entsteht.

  ![Graphen generiert nach dem Satz von Kuratowski. \label{mygenexample}](figs/chapter3/mygenexample.png)

### Vor- und Nachteile

Vorteil: Planare Graphen können effizient und ohne Inputdaten erzeugt werden.

Vorteil: Die Graphen unterscheiden sich merklich voneinander. 

Vorteil: Die Graphen können optionale Pfade für Risk and Reward Momente enthalten. 

Vorteil: Der Algorithmus kann um Schnittstellen ergänzt werden um Levelgröße, Pfadlänge etc. zu bestimmen und so das Pacing zu kontrollieren.

Vorteil: Die erzeugten Graphen können mit anderen planaren Graphen an genau einer zufälligen Stelle verbunden werden und behalten ihre planare Eigenschaft. Dies kann genutzt werden um bei bedarf manuell erzeugte Graphen einzubinden oder die erzeugten Graphen miteinander zu kombinieren. Dies steigert zusätzlich die Effizienz, da aus einer Handvoll Graphen durch verschiedene Kombination wieder neue Graphen erzeugt werden können ohne große Berechnungen durchführen zu müssen.

Nachteil: Der Algorithmus kann nicht jeden möglichen planaren Graphen erzeugen. Es gibt planare Graphen mit mehr als vier Knoten, die mehr als drei Nachbarn haben.

Lösungsansatz: Wie oben beschrieben können die erzeugten Graphen miteinander verbunden werden, um die Vielfalt der Graphen zu erhöhen. 

Nachteil: Die Struktur des Graphen ist größtenteils zufällig.

Lösungsansatz: Verschiedene Verfahren zur Graphenanalyse können genutzt werden, um Schwachstellen im Graphen zu erkennen und diese durch Veränderungen zu verbessern. 

## Spelunky

Spelunky ist ein 2D-Rogue-Like-Plattformer. Es verbindet das klassische Gameplay von Plattformern und erweitert sie um prozedural generierte Level und Permadeath aus dem Rogue-Like Genre. Derek Yu, der Entwickler von Spelunky beschreibt im gleichnamigen Buch "Spelunky" unter anderem wie die Level im Spiel generiert werden. [@Yu2016]

Die Aufgabe des Spielers in Spelunky ist es, vom Start des Levels bis zum Ausgang zu gelangen, dabei kann er zusätzlich Schätze sammeln, um Bonuspunkte zu erhalten. Auf dem Weg lauern verschiedene Gegner und Fallen. 

Alle  Level von Spelunky werden auf einem 4x4 Grid-System erzeugt. Jedes Feld im Grid stellt einen 10x8 Felder großen Raum dar. Zu Beginn der Generierung wird ein zufälliger Raum aus der obersten Reihe ausgewählt und als Startraum markiert. Von diesem Startraum aus wird in eine zufällige Richtung gegangen, dazu wird eine Zufallszahl von 1 bis 5 genutzt. Bei 1 oder 2 wird der Raum links vom aktuellen Raum ausgewählt, bei 3 oder 4 der Raum rechts vom aktuellen Raum. Bei einer 5 oder wenn es nicht möglich ist, in die entsprechende Richtung (links oder rechts) zu gehen, weil das Ende des Grids erreicht wurde, wird stattdessen der Raum unterhalb des aktuellen Raums ausgewählt. Wenn die unterste Reihe erreicht ist und versucht wird eine weitere Reihe nach unten zu gehen, wird der aktuelle Raum als Ausgang markiert und der Algorithmus endet. 

Jeder Raum im Grid ist mit einer Nummer markiert. Der Initialstatus jeden Raums ist 0 und gibt an, dass der Raum beim Erzeugen des kritischen Pfades nicht betreten wurde, er also optional ist. Beim Durchlaufen des Algorithmus werden die Markierungen entsprechend angepasst. Wenn ein Raum nach links oder rechts verlassen wird, wird er mit der Nummer 1 markiert, dieser Raum braucht daher einen Ausgang nach links und rechts (Minus-Form). Wird ein Raum nach unten verlassen, wird er mit einer 2 markiert und benötigt einen Ausgang nach links, rechts und unten (T-Kreuzung nach unten). Ist der darüberliegende Raum auch mit einer 2 markiert, so wird auch ein Ausgang nach oben benötigt (Kreuzung).  Wird ein Raum von oben betreten und nach links oder rechts verlassen, wird er mit einer 3 markiert und braucht einen Ausgang nach links, rechts und nach oben (T-Kreuzung nach oben). 

Durch dieses System kann sichergestellt werden, dass der kritische Pfad zwischen Start und Ende eine durchgehende Verbindung besitzt und das Level somit lösbar ist. Es werden keine Items wie Bomben oder Seile benötigt, um zum Ende zu kommen. Alle optionalen (mit 0 markiert) Räume werden abschließend zufällig mit 1,2 oder 3 markiert und können daher auch direkt am kritischen Pfad angeschlossen sein oder auch nicht. Alle nicht angeschlossene Räume können dann nur mit bestimmten Items erreicht werden. Abbildung \ref{spelunkylevel} zeigt ein erzeugtes Level mit den jeweiligen Raumnummerierungen in den Ecken sowie den kritischen Pfad in Form von roten Blöcken. 

Abhängig von der Markierung eines Raums wird er mit einem von mehreren per Hand gebauten Templates gefüllt. Diese 10x8 großen Templates geben an, an welcher Stelle im Raum welcher Art von Block platziert ist. Um die Variation an Räumen möglichst groß zu gestalten und zeitgleich nicht hunderte unterschiedlicher Templates eigenständig zu bauen, werden die Templates modifiziert. 

Jedes Template lässt sich als String darstellen und kann als 8x10 Matrix verstanden werden. In jedem Feld der Matrix steht ein Wert, dieser Wert gibt an, welche Art von Block an der jeweiligen Stelle zu platzieren ist (vgl. Tabelle \ref{spelunkytable}). Einige Felder, sogenannte Chunks, ersetzten eine 5x3 große Fläche durch eines von zehn vorgefertigten Chunk-Templates. Durch die Veränderung der Templates lassen sich viele unterschiedliche Räume generieren. 


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

### Vor- und Nachteile

Vorteile: Die erzeugten Level sind immer lösbar, da sichergestellt wird, dass der kritische Pfad immer begehbar ist und keine Items voraussetzt. 

Vorteil: Die einzelnen Räume sind effizient der Herstellung. Zwar müssen die Raumtemplates per Hand gebaut werden, danach können aber durch die Mutationen viele verschiedene, einzigartige Räume erstellt werden.

Vorteil: Die Räume unterstützten verschiedene Texturen, so kann die optische Abwechslung gewährleistet werden.

Vorteil: Die Räume unterstützen viele verschiedene Spielinhalte. Neue Inhalte wie Fallen können schnell und einfach hinzugefügt werden, indem die Ersetzungstabelle erweitert oder verändert wird. Dies erlaubt es die Räume für die Mechaniken des Spiels anzupassen.

Nachteil: Durch das Grid ist das Level-Layout nicht einzigartig.

Lösungsansatz: Das Erstellen das Level-Layout kann von einem anderen Algorithmus übernommen werden. Die Lösbarkeit der Level muss dann vom neuen Algorithmus sichergestellt werden. 
