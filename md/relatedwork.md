# Grundlagen

In diesem Kapitel werden die verschiedenen Grundlagen zum Verstehen dieser Arbeit erläutert. Zuerst wird der Begriff der prozeduralen Generierung weiter ausgeführt und im Kontext dieser Arbeit spezifiziert. Es folgt eine Erklärung der graphenbasierten Darstellung von Videospielleveln. Um den Anwendungsfall für diese Arbeit besser nachvollziehen zu können, wird danach das Genre der Rogue-Like Videospiele und das PM-Dungeon als Vertreter dieses Genres vorgestellt. Da sich diese Arbeit auf die Generierung von Leveln fokussiert, werden in diesem Kapitel verschiedene Regeln für gutes Level-Design aufgestellt. Aus diesen Regeln werden Kriterien für Level-Generatoren abgeleitet, die im weiteren Verlauf der Arbeit genutzt werden, um Aspekte verschiedener Algorithmen im Kontext der prozeduralen Level-Generierung zu bewerten. Außerdem dienen sie als Anforderungen für den in dieser Arbeit erstellten Generator.

## Prozedurale Generierung

Der Begriff prozedurale Generierung, auch prozedurale Synthese genannt, beschreibt im Kontext der Videospielentwicklung die Kombination aus handgebauten Inhalten und Algorithmen, um verfahrensmäßig verschiedene Inhalte zu generieren.[@Wikipedia2019] Prozedurale Generierung kann unter anderem genutzt werden, um Texturen, Items, Musik, Soundeffekte oder Level zu erzeugen. Dabei erzeugen die Algorithmen die Inhalte nicht zufällig, vielmehr nehmen sie die vorgegebenen Muster und verändern diese mithilfe von Zufallswerten. Prozedurale Algorithmen sind daher durchaus deterministisch.[@Beca2017] [@Lee2014] [@Remo2008].

> In general computing terms, procedural generation is any technique that
> creates data algorithmically as opposed to manually. It may also be called
> random generation, but this is an over-simplification: although procedural
> algorithms incorporate random numbers, they are never truly random, or
> at least not as random as that term implies.[@Beca2017]

Die Umsetzung von prozeduralen Algorithmen zur Erzeugung von Leveln ist ein komplexes Unterfangen. Während das klassische Erstellen von Leveln ein designtechnisches Problem ist, ist die Implementierung eines Algorithmus ein programmiertechnisches Problem. Viele Aspekte, die beim manuellen Erstellen von Leveln einfach zu überprüfen sind, wie die Lösbarkeit der Level, müssen gewährleistet und automatisch getestet werden. Die Behebung von Fehlern kann sich zu einem aufwendigen Prozess entwickeln. Werden Level manuell gebaut, können fehlerhaft platzierte Objekte schnell und einfach umplatziert werden, bei prozeduraler Generierung muss der Algorithmus umgeschrieben werden, was ein tiefes Verständnis für diesen erfordert und das Risiko erhöht einen neuen Fehler einzubauen. Schon das Auffinden von Fehlern kann zu einem mühsamen Prozess werden. Bei Millionen an unterschiedlichen Kombinationen von Parametern kann es sein, dass ein Fehler keinem der Tester begegnet. Behobenen Fehler sind vielleicht nur in den überprüften Kombinationen entfernt, aber in ungetesteten Kombinationen noch vorhanden. Die Ausarbeitung eines umfangreichen Testkonzeptes und das Loggen von möglichst vielen Daten ist daher eine Notwendigkeit. Prozedurale Algorithmen können durch die verwendeten Muster schnell repetitiv wirken, bei der Implementierung ist also auch darauf zu achten, dass die Muster gut verändert werden und es eine Vielzahl an unterschiedlichen Zufallsvariablen gibt.[@Beca2017]

Ein gut konzeptionierter prozeduraler Algorithmus kann auf Knopfdruck hunderte verschiedene Level erzeugen, wodurch die Wiederspielbarkeit nahezu unendlich ist. Da die Entwicklung und Konzeptionierung eines solchen Algorithmus komplex ist, lohnt er sich vor allem bei Spielen, die Wert auf viel Abwechslung bei zeitgleichem großen Spielumfang legen, um die Kosten und den Zeitaufwand zu reduzieren.[@Software2007]

Ein simpler prozeduraler Algorithmus zur Erzeugung von Leveln ist der *Random Walk* oder auch *Drunkard's Walk*. Eigentlich wird dieser zur Generierung nicht deterministischer Zeitreihen, wie Aktienkurse in der Finanzmathematik, verwendet, kann aber auch höhlenartige Level erzeugen.[@Wikipedia2020b] Listing **\ref{imdrunk}** zeigt den Algorithmus als Pseudocode.[@roguebasin2014]  Das Level wird als 2D-Array dargestellt und jeder Index im Array ist ein Feld im Level. Es gibt nicht betretbare Felder (Wände) und betretbare Felder (Böden). Zu Beginn wird ein zufälliges Feld als Startpunkt ausgewählt und zu einem Bodenfeld gemacht. Von diesem Feld aus wird nun in eine zufällige Richtung gegangen und das neue Feld wird wieder zu einem Bodenfeld umgewandelt. Dieser Prozess wird so lange wiederholt, bis die gewünschte Anzahl an Bodenfeldern vorhanden ist.  Der Vorteil des Random Walk Algorithmus liegt darin, dass sichergestellt werden kann, dass alle Bodenfelder auch erreichbar sind, da sie gezwungenermaßen alle miteinander verbunden sind. Allerdings bietet der Algorithmus abseits der gewünschten Bodenfelder keine Konfigurationsmöglichkeiten und die erzeugten Level ähneln sich von der Struktur sehr. Abbildung \ref{drunkexample} zeigt ein Beispiel wie ein Level aussehen könnte, welches durch den Random Walk erzeugt wurde.

\begin{lstlisting}[label=imdrunk, caption={Random Walk in Pseudocode.}  ]
erstelle ein Level in dem alle Felder Wände sind
wähle ein Feld als Startpunkt aus
verwandle das gewählte Feld in einen Boden
solange noch nicht genügen Boden im Level existiert
mache ein Schritt in eine zufällige Richtung
wenn das neue Feld eine Wand ist
	verwandle das Feld in einen Boden
\end{lstlisting}

![Beispielergebnis eines Random Walk. Schwarze Flächen sind Wände, weiße Flächen sind Böden.[@Hagen2019] \label{drunkexample}](figs/chapter2/drunk.png){width=100%}

## Graphen zur Darstellung von Leveln

In dieser Arbeit werden Level mithilfe von Graphen dargestellt, daher erläutert dieser Abschnitt die verwendete Notation und Annahmen. 

Ein Level-Graph $G$ besteht aus einer Menge an Knoten $V(G)$ und Kanten zwischen diesen Knoten $E(G)$. Knoten sind Räume, dabei spielt die Größe, der Inhalt und die Geometrie des Raumes keine Rolle. Eine Kante zwischen zwei Knoten gibt an, dass die Räume aneinander anliegend und mit einem Durchgang verbunden sind. Kanten können entweder gerichtet sein, dann kann ein Weg zwar von einem Raum in den anderen Raum führen, aber nicht zurück oder Kanten können ungerichtet sein, dann kann ein Weg beidseitig passiert werden. Gerichtete Verbindungen werden durch einen Pfeil dargestellt, ungerichtete durch eine blanke Linie. Ein Level ist also eine Menge an Räumen, die miteinander verbunden sind.

Da diese Arbeit 2D-Level betrachtet, müssen alle Level-Graphen planar sein. Ist ein Graph nicht planar, können die Räume nicht auf einer zweidimensionalen Fläche angeordnet werden, ohne sich zu überschneiden und neue, im Graphen nicht dargestellte, Verbindungen zu erzeugen. Für die Darstellung von 3D-Leveln ist es nicht nötig, dass der Level-Graph planar ist, da hier Räume und Verbindungen auch übereinander oder untereinander liegen können.

Abbildung \ref{dam} zeigt ein Level aus dem Spiel *Dragon Age: Origins* in der Kartenansicht aus dem Spiel. Dieses Level wurde in Abbildung \ref{dag} in Graphendarstellung gebracht. Da das Themenfeld der Graphentheorie besonders gut erforscht ist, können nun viele der bekannten Verfahren genutzt werden, um den Level-Graph zu analysieren. Im nächsten Abschnitt werden die für diese Arbeit relevanten Algorithmen der Graphenanalyse erläutert. 

Die roten Kanten in Abbildung \ref{dag} zeigen den kritischen Pfad. Der kritische Pfad ist der Weg, den der Spieler vom Start des Level (Knoten 1) bis zum Ende des Level (Knoten 20) gehen muss. Alle Knoten, die auf dem kritischen Pfad liegen, müssen also vom Spieler passiert werden. Mit diesem Wissen können Entscheidungen im Design getroffen werden, welche Inhalte der Spieler passieren muss und welche Hindernisse ihn auf dem kritischen Pfad erwarten. Daraus ergibt sich auch, dass alle anderen Knoten optional sind, entsprechend können auch hier Designentscheidungen getroffen werden. Anhand des Graphen lässt sich auch feststellen, ob ein bestimmter Raum erreichbar ist, ohne einen anderen zu betreten. So könnte der Zugang zu Raum 6 verschlossen sein und der Schlüssel in Raum 3 liegen. Mittels der Graphenanalyse kann geschaut werden, ob Raum 3 betreten werden kann, ohne dabei Raum 6 zu passieren. Die Pfadlänge kann auch ermittelt werden, so können besonders lange Nebenpfade erkannt werden, bei Bedarf vermieden werden oder durch Abkürzungen wieder mit dem Hauptpfad verbunden werden.[@Mourato2013]

![Ein Level aus dem Spiel Dragon Age: Origins. [@Ma2014] \label{dam}](figs/chapter2/damap.png){width=100%}

![Das Level aus Abbildung \ref{dam} in Graphendarstellung \label{dag}](figs/chapter2/dragonageasgraph.png){width=100%}

Diese Art der Darstellung eignet sich vor allem für zweidimensionale Spiele, da auch der Graph zweidimensional ist. Zu beachten ist, dass der Graph nur die Struktur des Levels darstellt, jedoch nicht die Geometrie, das Aussehen oder den Inhalt.

## Graphenanalyse 

Im laufe dieser Arbeit werden verschiedene Verfahren der Graphenanalyse verwendet, in diesem Abschnitt werden diese Verfahren vorgestellt und erläutert. 

### Graph-Search

Beim Graph-Search-Verfahren werden, mithilfe von Breiten- oder Tiefensuche Pfade von einem Knoten im Graphen zu einem anderen Knoten im Graphen gesucht. Um Zyklen zu vermeiden, werden bei der Suche alle bereits betrachteten Knoten markiert. Anders als bei Verfahren wie `A*` oder dem *Dijkstra-Algorithmus*, werden keine gewichteten Kanten benötigt. Das Graph-Search-Verfahren findet daher nicht den kürzesten oder längsten Pfad im Graphen, sondern alle Pfade. In Kapitel 4 wird der spezifische Anwendungsfall in dieser Arbeit dafür beschrieben. In diesen Abschnitt wird nur das Theoretische-Verfahren betrachtet. 

Listing \ref{graphsearch_pseudo} zeigt eine Implementierung der Graph-Search mithilfe von Tiefensuche in Pseudocode. Zeile 1 dient dabei als Einstiegspunkt, der Methode werden Start- und Zielknoten übergeben. In Zeile 2 wird dann eine Liste erstellt, in der alle Pfade abgespeichert werden, ein Pfad ist dabei selbst eine Liste aus Knoten. In Zeile 3 wird die Suche gestartet und in Zeile 4 werden alle gefunden Pfade zurückgegeben. Die Methode in Zeile 6 bekommt den aktuell betrachteten Knoten, eine Liste mit bereits betrachteten Knoten, den Zielknoten sowie die Liste mit den bereits gefunden Pfaden übergeben. In Zeile 7 wird der aktuell betrachtete Knoten markiert und in Zeile 8 wird geprüft, ob es ich bei diesem Knoten um den Zielknoten handelt. Ist dies der Fall wird die Liste mit den betrachteten Knoten als Pfad in die Lösungsliste abgespeichert. Sollte es sich nicht um den Zielknoten handeln, wird in Zeile 10-12 die Methode rekursiv für jedes Kind des Knotens erneut aufgerufen. Dabei wird die Liste mit dem bereits betrachteten Knoten kopiert, damit jede Suche unabhängig von den anderen Suchen ist. Sollte der aktuell betrachtete Knoten weder der Zielknoten sein und auch keine Kinder haben bzw. keine Kinder, die noch nicht betrachtet wurden, wurde in diesem Zweig der Tiefensuche kein Pfad zum Ziel gefunden und der Zweig wird nicht weiter betrachtet. 

\begin{lstlisting}[language=python, label=graphsearch_pseudo, caption={Graph-Search Tiefensuche in Pseudocode}  ]
List<List<Node>> graph_search(Node start, Node goal):
	List<List<Node>> paths;
	graph_search(start,new List<Node>(),goal,paths);
	return paths;

void graph_search(Node currentNode, List<Node> marked, Node goal, List<List<Node>> paths):
	marked.add(currentNode);
	if(currentNode==goal): 
		paths.add(marked); 
	else forEach(Node child: currentNode.child):
		if(!marked.contains(child)):
			graph_search(child,marked.copy(),goal,paths);   
\end{lstlisting}



### Faces 

Als Face werden Flächen in einem planaren Graphen bezeichnet, wenn dieser grafisch auf einer Ebene dargestellt wird.[@Eyraud2016] Abbildung \ref{facesex} zeigt einen solchen planaren Graphen mit eingezeichneten Faces. Das Face $f1$ ist die äußerste Fläche und umfasst alles was nicht in $f2$ und $f3$ liegt. Face $f2$ wird durch den zyklischen Durchlauf der Kanten zwischen Knoten $1->2->4->3->1$ gebildet. Face $f3$ wird durch die Kanten $3->4->5->3$ gebildet. Knoten $6$ liegt in $f3$, ist aber kein Bestandteil der Fläche oder Flächenbildung. 

![Planarer Graph mit eingezeichneten Faces. [@Eyraud2016] \label{facesex}](figs/chapter2/faces.png){width=100%}

Im weiteren Verlauf dieser Arbeit sind nur Faces, die durch Kanten gebildet werden, wichtig (so wie $f2$,$f3$). Ebenso ist die Position der Fläche sowie deren Inhalt nicht von Bedeutung in dieser Arbeit. Für die Arbeit ist lediglich wichtig festzustellen, dass Faces im Graphen existieren und welche Knoten/Kanten sie bilden. Dafür muss der Graph auf Zyklen untersucht werden.

Um Zyklen in einen ungerichteten Graphen zu finden, können Spannbäume genutzt werden. Ein Spannbaum ist ein Teilgraph des Graphen, der alle Knoten des Graphen enthält und dabei ein Baum ist.[@Tittmann2021] Jede Kante die zwar im Graphen vorhanden ist, aber nicht im Spannbaum, ist eine Kante, welche einen Zyklus bildet. Im weiteren Verlauf werden solche Kanten als *Back-Forward-Edge* bezeichnet. Um mithilfe einer Back-Forward-Edge einen Zyklus zu finden, muss der Pfad zwischen den beiden Knoten, die von der Back-Forward-Edge verbunden werden, gefunden werden, welcher nicht die Back-Forward-Edge enthält. Dafür kann unter anderen der oben beschriebene Graph-Search Algorithmus genutzt werden. 

Abbildung \ref{bfe} zeigt einen Graphen mit rot eingezeichneten Back-Forward-Edges. Die Zahlen in der Beschriftung der Knoten geben den entsprechenden Level an, auf den sich der Knoten im Spannbaum befindet. Die Back-Forward Edge $X$ bildet einen Zyklus zwischen den Knoten $B1->C2->E3->B1$ die Back-Forward-Edge $Y$ bildet einen Zyklus zwischen $A0->B1->C2->E3->A0$. Zusammen Bilden sie zusätzlich noch den Zyklus $A0->B1->E3->A0$. 

![Graph mit eingezeichneten Back-Forward-Edges.\label{bfe}](figs/chapter2/bfe.png){width=50%}

## Rogue-Like

*Rogue: Exploring the Dungeons of Doom* \footnote{Rogue on Steam https://store.steampowered.com/app/1443430/Rogue/} ist ein in den 1980er-Jahren entwickeltes Dungeoncrawler-Rollenspiel. Dungeoncrawler sind Spiele, in denen sich der Spieler durch ein Labyrinth gefüllt mit Gegnern und Rätseln kämpft und meist über mehrere Ebenen versucht, das Spielziel (z.B. den Ausgang oder einen Schatz) zu erreichen.[@qrpg2020] In Rogue bewegt sich der Spieler rundenbasiert durch ein durch ASCII-Zeichen dargestelltes Levelsystem und versucht sich mithilfe von Gegenständen und Zaubersprüchen bis in die tiefste Ebene des Dungeons vorzukämpfen, um dort das Amulett von Yendor zu erlangen.[@MattBarton2009]

Dieses Spielkonzept ist zu dieser Zeit kein unbekanntes, was Rogue hingegen bis heute als eine der relevantesten Videospiele der Geschichte herausstellt, ist die prozedurale Generierung der Level. 

> But I think Rogue’s biggest contribution, and one that still stands out to this day, is that the computer itself generated the adventure in Rogue. Every time you played, you got a new adventure. That’s really what made it so popular for all those years in the early eighties.[@Wichman1997]

Bei jedem Neustart von Rogue werden die Level neu generiert. Das bedeutet der Aufbau der Level und die Anzahl und Positionierung von Monstern und Items\footnote{Items bezeichnen Gegenstände die der Spiele ausrüsten oder benutzen kann.} unterscheiden sich mit jedem Spieldurchlauf. Man spielt also niemals zweimal dieselbe Partie von Rogue. Zusätzlich setzt Rogue auf den sogenannten Permadeath. Stirbt der Spieler im Dungeon, verliert er seinen kompletten Fortschritt und muss das Spiel mit neu generiertem Level von vorne beginnen. Rogue zeichnet sich also vor allem dadurch aus, dass jeder Spieldurchlauf anders als der andere ist und dadurch ein besonders hohes Maß an Abwechslung und damit Wiederspielwert gegeben ist.  

Rogue konnte sich schnell an einiger Beliebtheit erfreuen und es dauert nicht lange bis andere Entwickler ähnliche Spiele mit prozedural generierten Inhalten veröffentlichten.[@MattBarton2009] Es entwickelte sich das Genre der Rogue-Likes.

> [... ] Rogue likes are called Rogue likes, because the games are literally like Rogue [... ] [@Brown2017]

In den Jahren haben viele Entwickler versucht Regeln für das Genre aufzustellen, also Bedingungen, die ein Spiel erfüllen muss, um sich als Rogue-Like bezeichnen zu dürfen. 2008 wurde auf der Internationalen Rogue-Like Entwickler Konferenz eine Liste mit verschiedenen Faktoren veröffentlicht. Diese Liste ist als *Berliner Interpretation* bekannt.[@Conference2008] Über die Jahre wurde die Interpretation harsch kritisiert und sogar als "downright nonsense"  bezeichnet.[@Grey2013] Die Berliner Interpretation schade der kreativen Freiheit.

Heute muss ein Spiel nur zwei wichtige Features implementieren, um *like* Rogue zu sein, prozedural generierte Level und Permadeath. [@Brown2019] Das Genre ist daher nicht nur noch auf Rollenspiele in Labyrinthen begrenzt, sondern umfasst mittlerweile Spiele aus den unterschiedlichsten Genres wie Plattformer, Shooter oder Action-Adventures und vielen mehr.[@Wikipedia2020]

## PM-Dungeon

Im praktischen Anteil des Moduls Programmiermethoden sollen die Studierenden das gelernte Wissen aus dem theoretischen Anteil anwenden und vertiefen, dafür bekommen sie in regelmäßigen Abständen Aufgaben gestellt. Um die Aufgaben in einen gemeinsamen Kontext zu bringen und zeitgleich die Motivation der Studierenden  zu steigern, wurde 2021 das PM-Dungeon eingeführt. Über den Verlauf des Semesters entwickeln die Studierenden ihr eigenes Rogue-Like-Rollenspiel. Zwar stehen weiterhin die Lehrinhalte im Fokus, dennoch haben die Studierenden viele Freiheiten um ihr Spiel nach ihren Wünschen zu gestalten. 

Sie konzeptionieren eigenständig das Verhalten von Monstern, implementieren Schatztruhen und Items sowie unterschiedliche Fähigkeiten, die der Spieler im Laufe des Spiels freischalten kann. Für die Entwicklung des Spiels bekommen die Studierenden ein extra dafür entwickeltes Framework zur Verfügung gestellt, das PM-Dungeon-Framework. Das PM-Dungeon-Framework erweitert das libGDX-Framework \footnote{libGDX https://libgdx.com} um vereinfachte Schnittstellen zur grafischen Darstellung. Die Studierenden können sich daher rein auf die Implementierung der Spielfeatures konzentrieren.

![Ausschnitt aus dem PM-Dungeon. \label{pmd}](figs/chapter2/pmd.png){width=100%}

Abbildung \ref{pmd} zeigt einen Ausschnitt aus dem Startlevel einer Beispielimplementierung des PM-Dungeons. Die Spielfigur (grüner Kreis) muss mithilfe der Leiter (blauer Kreis) in die nächste Ebene gebracht werden. Auf dem Weg dorthin kann der Spieler die Monster (roter Kreis) töten, um Erfahrungspunkte zu sammeln oder Items zu finden. Sowohl die Spielerposition als auch die Position der Monster werden zu Beginn des Levels zufällig bestimmt. Das Spielziel des PM-Dungeon ist an den Highscore-Automaten angelehnt, es geht also nicht darum einen bestimmten Boss zu besiegen oder ein Item zu sichern, sondern darum möglichst tief in das endlose Dungeon vorzudringen und mit jedem Durchlauf seinen persönlichen Rekord zu brechen. Jedoch kann auch das Spielziel von den Studierenden frei verändert werden. 

Zum aktuellen Zeitpunkt ist kein eigener Level-Generator im PM-Dungeon integriert. Das Dungeon wird mit einer Handvoll vorgenerierten Level ausgeliefert. Die Studierenden haben keine direkte Möglichkeit die Level zu verändern oder eigene Level zu erstellen.

## Regeln für gutes Level-Design

Der Begriff Level-Design kann unterschiedlich interpretiert werden. Im Allgemeinen beschreibt der Begriff die Erstellung und Bearbeitungen von Spielwelten für Videospiele.[@Wikipedia2020a] Es gibt keine festen Vorgaben dafür, welche Aspekte Teil des Level-Designs sind und welche bereits darüber hinausgehen. [@DevPlay2019] Im Rahmen dieser Arbeit beschreibt Level-Design den örtlichen Aufbau der Spielwelt, die Platzierung von Gegnern, Items und anderen Objekten sowie die optische Gestaltung der Level, dabei ist nicht das Erstellen von Texturen gemeint, sondern die Verwendung dieser. 

Auch wenn es viel Diskussion darüber gibt, ob Videospiele Kunst sind oder nicht, muss man den kreativen Schaffensprozess respektieren.[@Petzold2019] Daher lassen sich für Videospiele auch keine festen Regeln definieren, wie bestimmte Designelemente zu sein haben. 

In diesem Abschnitt werden Regeln für gutes Level-Design präsentiert. Sie sollten weder als objektive Maßstäbe noch als verpflichtende Gesetzte betrachtet werden, sondern vielmehr als Leitfaden. Die Liste erhebt keinen Anspruch auf Allgemeingültigkeit, Korrektheit oder Vollständigkeit. Sie wurde gezielt für die in dieser Arbeit betrachteten Probleme zusammengestellt. Zwar beschäftigt sich die Wissenschaft sehr wohl mit Videospielen und deren Level, jedoch nicht auf die Ausarbeitung objektiver Bewertungskriterien, daher basieren die Regeln dieser Liste auf Aussagen verschiedener Persönlichkeiten der Videospielbranche.

Die Grundlagen der Regeln stammen aus Dan Taylors *Ten Principles of Good Level-Design* [@Taylor2013] [@Taylor2018] und Tim Ryans *Beginning Level-Design* [@Ryan1999] und wurden bei Bedarf um weitere Aspekte, Meinungen und Beispielen ergänzt. 

### Lösbarkeit und Fehlerfreiheit

Ein Level muss lösbar sein. Es darf keine Fehler geben, die das Voranschreiten verhindern. Es sollte zusätzlich darauf geachtet werden, dass Spieler nicht in Sackgassen geraten können. Braucht der Spieler beispielsweise ein bestimmtes Item um weiterzukommen, sollte er das Item an diesem Punkt immer noch erreichen können. Bei Spielen, die Lebensenergie verwenden, sollte das Level gelöst werden können, ohne Lebensenergie zu verlieren. So bleiben Spieler, die im vorherigen Abschnitt viel Schaden bekommen haben, nicht stecken. Gibt es im Spiel optionale Wege oder Ziele, müssen auch diese lösbar sein und dürfen nicht durch einen Fehler unlösbar werden. Bei optionalen Inhalten liegt es in der kreativen Entscheidungsfreiheit des Entwicklers, ob diese auch immer erreichbar sein müssen oder ob zum Beispiel ein optionales Item benötigt wird, welches nicht mehr erreichbar ist. Zusätzlich sollten die Level frei von Fehlern sein, die Spielern die Möglichkeit geben, das Spiel oder dessen Mechaniken zu manipulieren oder auszunutzen, wie das ungewollte Überspringen von Abschnitten.

**Regel 1: Gute Level sind lösbar und fehlerfrei.** 

### Driven by  game’s mechanics

> Always remember that interactivity is what makes videogames different from any other form of entertainment: books have stories, movies have visuals, games have interaction. If your Level-Design isn’t showcasing your game mechanics, your players might as well be watching a movie or reading a book.[@Taylor2013]

> Above all else, great Level-Design is driven by interaction - the game’s mechanics. Game levels don’t just provide context for mechanics, they provide the very reality in which they exist.[@Taylor2013]

Das Level ist der Spielplatz für den Spieler, in dem er die verschiedenen Mechaniken des Spiels anwenden kann. Daher müssen alle Level so gebaut werden, dass sie die Mechaniken beanspruchen und es Spaß macht sie zu verwenden. Die Kernmechaniken des Spiels sollten in jedem Level gefordert sein. Ein Shooter braucht gute Kampfareale, ein Rennspiel spaßige Rennstrecken und ein Plattformer gute Hindernisse. Ein Rogue-Like braucht daher abwechslungsreiche Dungeons, die zum Erkunden einladen und mit Monstern und Items gefüllt sind. Gute Level bieten dem Spieler verschiedene Möglichkeiten die Herausforderungen auf unterschiedliche Weise unter Einbeziehung der Mechaniken zu lösen.

**Regel 2: Gute Level fordern die Mechaniken des Spiels.** 

### Balancing

Beim Balancen geht es darum den Schwierigkeitsgrad der Herausforderungen zu bestimmen. Ist ein Spiel zu einfach, kann es schnell anspruchslos und langweilig werden. Ist es zu schwer, kann es frustrierend werden.

> The trick to good Level-Design is to present challenges that are difficult enough to merit the players’ attention and make their heart or mind race, but not so difficult as to always leave them failing and disappointed.[@Ryan1999] 

Da nicht jeder Spieler gleich gut beim Spielen ist und die Vorlieben der Spieler sich unterscheiden, ist ein fest vorgegebener Schwierigkeitsgrad oft nicht zielführend. Oft lassen Spiele den Spieler selbst entscheiden, wie schwer das Spiel werden soll, indem sie vor dem Spielstart die Auswahl verschiedener Schwierigkeitsgrade anbieten. Je nach gewählter Option werden dann stärkere oder schwächere Monster platziert, die Lebenspunkte der Spielfigur angepasst oder andere Hilfestellung gegeben oder neue Hindernisse platziert.[@DevPlay2019a] Diese Variante kann sehr effektiv sein, setzt aber voraus, dass der Spieler seine eigenen Fähigkeiten richtig einschätzt, was vor allem dadurch erschwert wird, wenn der Spieler noch keine Erfahrung mit dem Spiel gemacht hat. Da sich die Auswirkungen des Schwierigkeitsgrades auch von Spiel zu Spiel unterscheiden, kann eine gute Selbsteinschätzung auch daran scheitern.

> [... ] they (the players) might pick the easier option even though they could handle more challenge, and rob themselves of the best, and designer-intended experience.[@Brown2016]

Eine andere Möglichkeit zum Balancen ist die Verwendung von dynamischen Schwierigkeitsgraden. Im Spiel *Resident Evil 4*\footnote{Resident Evil 4 Homepage https://www.residentevil.com/4/} werden mehr oder weniger Gegner platziert, abhängig davon ob der Spieler bisher besonders gut durch die Level gekommen ist oder oft gestorben ist.[@Brown2015] Im Spiel *Half-Life* \footnote{Half-Life on Wikipedia https://de.wikipedia.org/wiki/Half-Life} werden mehr Medikits platziert, wenn der Spieler wenig Lebenspunkte hat.[@Brown2016a] 

Es sollte beachtet werden, dass Spieler mit voranschreiten immer besser werden, daher sollten frühe Level deutlich einfacher sein als Level gegen Ende des Spiels. Dabei muss die Schwierigkeit der Level nicht linear steigen, es bietet sich an nach einem besonders schweren Level ein einfacheres Level einzubauen, um den Spieler Zeit zum Aufatmen zu lassen.

**Regel 3: Gute Level sind gut gebalanced.** 

### Risk and Reward

Risk and Reward beschreibt Momente im Spiel, bei denen der Spieler eine besonders schwere Herausforderung lösen muss, dafür aber auch entsprechend belohnt wird. Diese stets optionalen Momente sollten in regelmäßigen Abständen auftauchen, um den Spieler vor eine interessante Entscheidung zu stellen, ob sie die Herausforderung annehmen oder lieber ablehnen möchten.

Vor allem bei Rogue-Like Spielen sollte es ständig zu solchen Momenten kommen, da diese hier einen erhöhten Nervenkitzel bieten. Jede Herausforderung könnte den Tod bedeuten und damit den permanenten Verlust des Fortschrittes. Die Frage, ob sich der Kampf wirklich lohnt und ob man das Risiko eingehen sollte, fällt hier umso schwerer. 

**Regel 4: Gute Level haben Risk and Reward Momente.** 

### Pacing

![Pacing im Wüstenpalast von The Legend of Zelda 3. \label{zelda3pacing}](figs/chapter2/Zelda3Pacing.png){width=70%} 

Der Begriff Pacing entstammt der Filmbranche und beschreibt die Spannungskurve des Films. Aus dem Film lassen sich drei Regeln für gutes Pacing ableiten, welche auch für Videospiele Anwendung finden können.[@Wesowski2009]

1. Pacing verläuft nicht linear, nach Hochpunkten sollte ein Tiefpunkt folgen.
2. Zu Beginn sollte viel Spannung erzeugt werden, um den Zuschauer bei Stange zu halten.
3. Kurz vor Ende sollte der spannendste Moment sein, der sich am Ende entlädt.

Auch Videospiele sollten darauf achten ihre Spannungskurve zu kontrollieren. Gutes Pacing sorgt dafür, dass ein Spiel nicht langweilig oder repetitiv wird.  [@Brown2018]

Abbildung \ref{zelda3} zeigt den Level-Graph für den Wüstenpalast aus dem Spiel *The Legend of Zelda: A Link to the past* \footnote{A Link to the past on Zeldachronicles https://zeldachronicles.de/spiele/alttp/}. Um das Level zu bestehen, muss der Spieler den Boss im letzten Raum besiegen. 

Das Level startet mit einem kurzen linearen Abschnitt (blau). Der Spieler sieht zum ersten Mal wie das Dungeon von innen gestalten ist, hört die extra für das Level komponierte Musik und wird mit einem neuen Gegner-Typen, den Lastertürmen, konfrontiert. Die Neugier des Spielers ist geweckt.

Im nächsten Abschnitt (grün) werden keine neuen Gegner eingeführt. Dieser Abschnitt ist zum Erkunden gedacht. Der Spieler muss den Weg zum Boss finden, kann aber zeitgleich verschiedene Nebenpfade erkunden, um Schätze zu finden. Einige Bereiche sind mit Türen verschlossen, für die der Spieler die Schlüssel in anderen Räumen finden muss. Die anfänglich aufgebaute Spannung fällt hier etwas ab, steigt aber dann wieder, wenn der Spieler sich auf den finalen Weg zum Boss macht. 

Der letzte Abschnitt (rot) ist linear. Betritt der Spieler diesen Weg, werden sich die Türen hinter ihm schließen und der Weg führt nur noch nach vorne, es gibt kein Zurück mehr. Die Musik wird schneller und es tauchen deutlich mehr Gegner als vorher auf. Es geht nicht mehr darum das Level zu erkunden, sondern darum sich zum Boss vorzukämpfen. Rätsel oder verschlossen Türen gibt es keine mehr. Je näher der Spieler dem Boss kommt, desto näher neigt sich auch die Spannungskurve ihrem Höhepunkt. Im letzten Raum vor dem Boss befinden sich noch Items um die Lebensenergie aufzufüllen, die Tür zum Endgegner ist deutlich markiert und der Spieler weiß, dass es jetzt so weit ist. In dem Moment, in dem der Spieler den Bossraum betritt und diesen zum ersten Mal sieht, hat die Spannungskurve ihren Höhepunkt erreicht und wird diesen bis zum Ende des Kampfes auch nicht mehr verlassen. Der Boss ist besiegt, der Spieler hat gesiegt und die Spannungskurve fällt ab. Das Level wurde erfolgreich absolviert. Abbildung \ref{zelda3pacing} zeigt eine exemplarische Darstellung der Spannungskurve für dieses Level. Die einzelnen Abschnitte sind passend zu Abbildung \ref{zelda3} gefärbt.

![Wüstenpalast aus The Legend of Zelda 3 in Graphendarstellung \label{zelda3}](figs/chapter2/Zelda3Dungeon.png){width=100%}

Die Entwickler von Zelda haben im Wüstenpalast also drei unterschiedliche Strukturen genutzt um das Pacing zu kontrollieren. Ein kurzer schlauchartiger Abschnitt, um die Atmosphäre aufzubauen, einen offenen Abschnitt, um zu erkunden und am Ende einen streng linearen Abschnitt bis zum Boss. Durch diesen Wechsel der Strukturen konnten sie das Pacing nach ihrem Belieben kontrollieren.

**Regel 5: Gute Level steuern das Pacing des Spiels.** 

### Einzigartigkeit

Die einzelnen Level sollten sich stark voneinander unterscheiden. Auch wenn das überliegende Designkonzept über den gesamten Spielverlauf konstant sein sollte.

> [...] people don’t like playing the same level twice.[@Ryan1999]

Variation in Gegner, Texturen und Strukturen helfen dabei die Abwechslung im Level zu gewährleisten und keine Langeweile aufkommen zu lassen. Dies gilt auch wenn der Spieler einen Abschnitt im Spiel erneut ablaufen muss, sogenanntes Backtracking. Wenn es längere Nebenpfade gibt, sollte das Ende des Nebenpfades eine Abkürzung zurück zum Hauptpfad bieten. 

**Regel 6: Gute Level sind einzigartig.** 

### Effizienz 

Spieleentwicklung ist ein kostspieliges Unterfangen und bereits kleinere Produktionen können mehrere Millionen Dollar kosten.[@DevPlay2017] Daher ist die effiziente Nutzung von Ressourcen unabdingbar. Gute Level-Designer erstellen ein Set aus Modulen mit verschiedenen Assets und Events. Diese Module können dann miteinander kombiniert und bei Bedarf angepasst werden. Aus einer Handvoll solcher Module lassen sich bereits viele verschiedene Level und Situationen erzeugen. Effizienz ist der größte Vorteil von guten prozeduralen Algorithmen.

**Regel 7: Gute Level sind effizient in der Herstellung.** 

## Anforderungen an einen Level-Generator 

Die vorgestellten Regeln geben zwar eine Idee davon, wie gute Level auszusehen haben, sie geben aber keine klaren Kriterien vor, die ein Level-Generator erfüllen sollte.
In diesem Abschnitt werden aus den vorgestellten Regeln Kriterien abgeleitet, die ein Level-Generator erfüllen sollte um gute Level zu erzeugen. Dabei stellen die Kriterien sowohl Anforderungen an die erzeugten Level als auch an den Algorithmus und seine Input- sowie Output-Daten. Die Kriterien sollen dabei helfen, festzustellen, ob die obengenannten Regeln eingehalten wurden. Sie dienen als Anforderung für den in dieser Arbeit erzeugten Generator und werden in Kapitel 3 genutzt um einzelne Bausteine aus anderen Algorithmen zu bewerten. 

### Regel 1: Gute Level sind lösbar

Dies ist eine kritische Anforderung. Level, die nicht gelöst werden können, sind nicht akzeptabel. Ein Generator muss daher gewährleisten können, dass alle Level, die generiert werden, lösbar sind. Da theoretisch unendlich viele Level generiert werden können, ist es nicht ausreichend, wenn die manuell getesteten Level lösbar sind. Daher muss der Generator Verfahren zur Sicherstellung der Lösbarkeit implementieren. Dies kann entweder in Form einer Analyse nach der Generierung geschehen oder durch andere Möglichkeiten, die das Erzeugen von nicht lösbaren Leveln unmöglich machen.

### Regel 2: Gute Level fordern die Mechanik des Spiels

Es ist wichtig, dass die erzeugten Level für das Spiel verwendbar sind. Ein guter Generator bietet Schnittstellen um neben den Standardelementen (Wände, Böden, Türen) auch andere Elemente in den Generierungsprozess einfließen zu lassen (Fallen, sprengbare Wände etc.).

### Regel 3: Gute Level sind gut gebalanced

Das Balancing im Spiel wird stark von den Gamedesignentscheidungen gesteuert und hat daher nur indirekt mit dem Generator zu tun. Ein guter Generator bietet jedoch Schnittstellen für den Designer um die Anzahl, Stärke und Position von Monstern und Hindernisse zu bestimmen.

### Regel 4: Gute Level haben Risk and Reward Momente

Um Risk and Reward Momente zu erzeugen, müssen alternative Pfade vom Generator erstellt werden können. Ein guter Generator bietet Schnittstellen für den Designer, um gezielt auf alternative Pfade zugreifen zu können um dort ein besonders schweres Hindernis und eine entsprechende Belohnung zu platzieren. Die Anzahl der alternativen Pfade sollte vom Designer konfigurierbar sein.

### Regel 5: Gute Level steuern das Pacing des Spiels

Das Pacing wird durch das Layout der Level bestimmt. Ein guter Generator erzeugt Level mit abwechslungsreichem Layout, implementiert Verfahren um Backtracking zu vermeiden und bietet Schnittstellen für den Designer, um das Layout des Levels zu manipulieren oder sogar ganz zu bestimmen.

### Regel 6: Gute Level sind einzigartig

Die Einzigartigkeit der Level entsteht durch das Layout, ein guter Generator erzeugt daher Level, die sich vom Layout unterscheiden. Auch das Aussehen der Level ist entscheidend, ob die Level als einzigartig wahrgenommen werden, daher verwendet ein guter Generator verschiedene Texturen für die Level. Konfigurationsmöglichkeiten für verschiedene Layouts oder Texturen sind wünschenswert.

### Regel 7: Gute Level sind effizient in der Herstellung

Die prozedurale Generierung ist ein effizienter Weg zur Erstellung von Leveln. Alle funktionsfähigen Generatoren erfüllen dieses Kriterium. Die meisten Algorithmen benötigen Inputdaten, um Level zu erzeugen, ein besonders effizienter Generator kann aus wenigen Inputdaten eine Vielzahl an unterschiedlichen Leveln erzeugen.



