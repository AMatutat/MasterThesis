# Theoretisches Konzept

<!--

geschätzter Umfang ca: 40%
-->

In diesem Kapitel werden Algorithmen vorgestellt, die verschiedene Aspekte der prozeduralen Levelgenerierung abdecken. Nicht jeder vorgestellter Algorithmus ist für die Generierung von Level konzeptioniert, kann aber für Teilaspekte verwendet werden. Es werden Algorithmen zur Generierung und Modifikation von planaren Graphen, Erzeugen von Level aus diesen Graphen sowie zum Erstellen von einzelnen Räumen vorgestellt. Ziel ist es, die einzelne Elemente der Algorithmen so zu kombinieren, dass das Resultat die in Kapitel 2 aufgestellten Anforderungen bestmöglich erfüllt. Dieses Kapitel fokussiert sich auf die Vorstellungen der Bausteine und präsentiert nur ein theoretisches Konzept der Kombination, das nächste Kapitel stellt ein konkretes, technisches Konzept vor. 


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

Das Berechnen des configuration spaces reduziert den lokalen Suchraum für die individuellen Knoten, jedoch ist der Suchraum für den gesamten Graphen weiterhin zu groß um zuverlässig in einer angebrachten Zeit eine Lösung zu finden. Daher wird das Problem in kleinere, einfacher zu lösende, Teilprobleme aufteilt. Es hat sich herausgestellt, dass Graphen, in den jeder Knoten maximal zwei Nachbarn besitzt, einfacher aufzulösen sind, da die Anzahl der Kanten der Blöcke immer größer ist als die Anzahl der Kanten des Knotens im Graph.  

![Beispiel: Aufteilen eines Graphen in Chains. \label{how2chain}[@Nepozitek2019]](figs/chapter3/howtochain.PNG)

Der Input Graph wird daher in kleinere Subgraphen aufteilt, sogenannten Chains. In einer Chain hat jeder Knoten maximal zwei Nachbarn. Abbildung \ref{howtochain} zeigt wie ein Graph in Chains aufgeteilt werden kann. Knoten mit derselben Nummerierung gehören zu einer Chain. Zuerst werden alle Faces im Graphen gesucht, da diese Kreise im Leveldesign darstellen. Da solche Kreise mehr Bedingungen haben als eine lineare Folge von Knoten, ist die Auflösung dieser komplexer. Daher werden Kreise bevorzugt am Anfang des Algorithmus aufgelöst, um späteres Backtracking zu vermeiden. Die erste Chain ist das kleinste Face im Graphen (Label "0"). Die weiteren Chains werden gebildet, indem per Breitensuche die weiteren Faces gesucht werden **??** '(Label "1"). Stehen mehrere Faces zur Auswahl, wird zuerst das kleinere genommen. Wenn keine Faces mehr vorhanden sind, werden die restlichen Knoten hinzugefügt (Label "2","3","4").  

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

Das Erstellen von mehren Lösungen für eine Chain hat mehrere Vorteile. Zu einem ermöglicht und erleichtert es das schrittweise Backtracking, falls eine Chain nicht in der aktuellen Lösung angeschlossen werden kann und zusätzlich können schneller mehrere gültige Lösungen für ein Graph gefunden werden, indem beispielsweise nach der dritten Chain eine andere Teillösungen verwendet wird. Abbildung \ref{graphsolution} zeigt wie aus verschieden Teillösungen unterschiedliche Gesamtlösungen entstehen. Dies unterstreicht noch einmal die Effizienz des Algorithmus, da bereits aus einem einzigen Inputgraphen viele vollkommen unterschiedliche Level entstehen könne. 

![Beispiel: Unterschiedliche Teillösungen für die selbe Chain. \label{graphpartsolution}[@Ma2014]](figs/chapter3/graphpatrsol.PNG)

![Beispiel: Unterschiedliche Teillösungen führen zu unterschiedlichen Gesamtlösungen. \label{graphsolution}[@Ma2014]](figs/chapter3/graphsolution.PNG)

### Vor- und Nachteile

Vorteil: Der Algorithmus kann viele Level aus einem Graphen und Set aus Räumen erstellen und ist daher sehr effizient in der Nutzung der Inputs.

Vorteil: Sofern der Input Graph einen kritischen Pfad zwischen Start und Ziel hat, kann der Algorithmus gewährleisten, dass alle ausgegebenen Level auch lösbar sind.

Vorteil: Die Level-Layouts unterscheiden sich selbst bei gleichen Input-Daten stark voneinander und können als einzigartig bezeichnet werden.

Nachteil: Der Algorithmus funktioniert nicht ohne Input Daten, sowohl Graph als auch das Set aus Räumen müssen vorher erstellt werden, dies senkt die Effizienz des Algorithmus. 

Lösungsansatz: Ein weiterer Algorithmus zur Generierung planarer Graphen kann genutzt werden, um den Input Grafen automatisch erstellen zu lassen. Auch ein Algorithmus zur automatischen Generierung von Input Räumen wäre denkbar. Die vollständige automatisierte Generierung von Räumen stellt allerdings ein eigenes komplexes Problem dar, welches im Rahmen dieser Arbeit nicht besprochen wird. 

Nachteil: Es werden immer dieselben Räume verwendet, bei wenigen Input-Räumen oder einen Spieler der viel Zeit im spiel verbringt wird diese sichtbar und die Einzigartigkeit der Level ist nicht mehr gegeben. 

Lösungsansatz: Auch hier könnte ein Algorithmus zur vollständigen Generierung von Räumen genutzt werden. Eine andere Möglichkeit wäre ein Algorithmus, der die Input-Räume anhand verschiedener Kriterien mutiert und so bereits aus wenigen Räume eine Vielzahl unterschiedlicher Räume erzeugt werden können.

Nachteil: Der Algorithmus bietet keine Möglichkeit, um das Pacing zu kontrollieren, Risk and Reward Momente zu erzeugen oder das Spiel zu balancen. Es gibt keine Schnittstelle um das Aussehen der Level oder das Level Layout abseits des Input-Graphen anzupassen.

Lösungsansatz: Der Algorithmus kann als Grundlage für die Konzeptionierung eines Level Generators genutzt werden. Die Konfiguration der einzelnen Aspekte findet dann bei der Erstellung des Graphen bzw. bei der Mutation der Räume statt. 



## Fast generation of planar graphs

https://users.cecs.anu.edu.au/~bdm/papers/plantri-full.pdf

### Vor- und Nachteile

- erzeugt Graphen die als input genutzt werden könne, steigert daher die effizienz des gesamten generators
- Bietet Schnittstellen um die Anzahl der Knoten/ größe der Level zu bestimmen
- Kann alternative Pfade erzeugen für Risk and Reward momente
- Durch ergänzungne von Graphanalyse verfahren können Schnittstellen zur verfügugng gestellt werden (balancing)

## Cycle Dungeon

https://ctrl500.com/tech/handcrafted-feel-dungeon-generation-unexplored-explores-cyclic-dungeon-generation/

### Vor- und Nachteile

- Hilft dabei das pacing zu steuern indem backtracking vermieden wird

## Spelunky

Spelunky ist ein 2D-Rogue-Like Plattformer. Es verbindet das klassische Gameplay von Plattformern und erweitert sie um prozedural generierte Level und Permadeath aus dem Rogue-Like Genre. Derek Yu, der Entwickler von Spelunky beschreibt im gleichnamigen Buch "Spelunky" unter anderem wie die Level im Spiel generiert werden. [@Yu2016]

Die Aufgabe des Spielers in Spelunky ist es, vom Start des Levels bis zum Ausgang zu gelangen, dabei kann er zusätzlich Schätze sammeln, um Bonuspunkte zu erhalten. Auf dem Weg lauern verschiedene Gegner und Fallen. 

Alle  Level von Spelunky werden auf einem 4x4 Grid-System erzeugt. Jedes Feld im Grid stellt einen 10x8 Felder großen Raum dar. Zu Beginn der Generierung wird ein zufälliger Raum aus der obersten Reihe ausgewählt und als Startraum markiert. Von diesem Startraum aus wird in eine zufällige Richtung gegangen, dazu wird eine Zufallszahl von 1 bis 5 genutzt. Bei 1 oder 2 wird der Raum links vom aktuellen Raum ausgewählt, bei 3 oder 4 der Raum rechts vom aktuellen Raum. Bei einer 5 oder wenn es nicht möglich ist, in die entsprechende Richtung (links oder rechts) zu gehen, weil das Ende des Grids erreicht wurde, wird stattdessen der Raum unterhalb des aktuellen Raums ausgewählt. Wenn die unterste Reihe erreicht ist und versucht wird eine weitere Reihe nach unten zu gehen, wird der aktuelle Raum als Ausgang markiert und der Algorithmus endet. 

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

### Vor- und Nachteile

- Level im gesamten sind nicht einzigartig da das Grid gut erkennbar ist
- Die einzelnen Räume sind effizient in der Herstellung, da aus ein paar Handgebaute level durch mutationen viele unterschiedliche Level entstehen
- Diesen Aspekt nehme ich um die Inout Blöcke im Dungeon zu mutieren und mehr abwechslung zu haben
- Räume ermöglichen unterschiedliche Texturen etc. um das aussehen einzigartig zu machen
- Durch die verwendung von Platzhaltern im Raum können sowas wie Fallen relativ einfach integriert werden, "Gute Level fordern die Mechaniken des Spiels"

## Zusammenfassung des Konzeptes

- Graph 2 Level wird als basis genommen

- Planarer Graph wird automatisch generiert

  - hat parameter um die größe des Levels anzugeben
  - wird mithilfe von mutationen verändert um pacing zu omptimieren
  - Knoten werden gelabelt
    - unterschiedliche Label sind unterschiedliche input blöcke und texturen
  - Hat Schnittstellen um Graphen zu analysieren
    - get CriticalPath
    - get OptionalPath
    - get end of Path
    - etc.

- Set aus Input Blöcke wird manuell konzeptioniert

  - besteht aber aus template räume die spelunky style aufgebaut sind

  - hat schnittstellen um die ersetzung anzupassen (z.B alle '5' können Fallen sein)

    