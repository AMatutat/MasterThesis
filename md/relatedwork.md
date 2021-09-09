# Grundlagen

**TODO EINLEITUNG** 

## Prozedurale Generierung

Der Begriff prozedurale Generierung, auch prozedurale Synthese genannt, beschreibt im Kontext der Videospielentwicklung die Kombination aus handgebauten Inhalten und Algorithmen, um verfahrensmäßig verschiedene Inhalte zu generieren.[@Wikipedia2019] Prozedurale Generierung kann unter anderem genutzt werden, um Texturen, Items, Musik, Soundeffekte oder Level zu erzeugen. Dabei erzeugen die Algorithmen die Inhalte nicht zufällig, vielmehr nehmen sie die vorgegeben Mustern und verändern diese mithilfe von Zufallswerten. Prozedurale Algorithmen sind daher durchaus deterministisch.[@Beca2017] [@Lee2014] [@Remo2008].

> In general computing terms, procedural generation is any technique that
> creates data algorithmically as opposed to manually. It may also be called
> random generation, but this is an over-simplification: although procedural
> algorithms incorporate random numbers, they are never truly random, or
> at least not as random as that term implies.[@Beca2017]

Die Umsetzung von prozeduralen Algorithmen zur Erzeugung von Level ist ein komplexes Unterfangen. Während das klassischen erstellen von Level ein designtechnisches Problem ist, ist die Implementierung eines Algorithmus ein programmiertechnisches Problem. Viele Aspekte, die beim manuellen Erstellen von Level einfach zu überprüfen sind, wie die Lösbarkeit der Level, müssen gewährleistet und automatisch getestet werden. Die Behebung von Fehlern kann sich zu einen Aufwendigen Prozess entwickeln. Werden Level manuell gebaut, können fehlerhaft platzierte Objekte schnell und einfach unplatziert werden, bei prozeduraler Generierung muss der Algorithmus umgeschrieben werden, was ein tiefes Verständnis für diesen erfordert und das Risiko erhöht einen neuen Fehler einzubauen. Schon das auffinden von Fehlern kann zu einen mühsamen Prozess werden. Bei Millionen an unterschiedlichen Kombinationen von Parametern kann es durchaus sein, dass ein Fehler keinen der Tester begegnet. Behobenen Fehler sind vielleicht nur in den überprüften Kombinationen entfernt aber in ungetesteten Kombinationen noch vorhanden. Die Ausarbeitung eines umfangreichen Testkonzept und das Loggen von möglichst vielen Daten ist daher eine Notwendigkeit. Prozedurale Algorithmen können durch die verwendeten Muster schnell repetitiv wirken, bei der Implementierung ist also auch darauf zu achten, dass die Muster gut verändert werden und es eine Vielzahl an unterschiedlichen Zufallsvariablen gibt. [@Beca2017]

Ein gut konzeptionierter prozedurale Algorithmus kann auf Knopfdruck hunderte verschiedener Level erzeugen, wodurch die Wiederspielbarkeit nahezu unendlich ist. Da die Entwicklung und Konzeptionierung eines solchen Algorithmus komplex ist, lohnt er sich vor allem bei Spielen die auf viel Abwechslung bei zeitgleichen großen Spielumfang wert legen um die Kosten und den Zeitaufwand zu reduzieren.[@Software2007] 

Ein simpler prozeduraler Algorithmus zur Erzeugung von Level ist der *Random Walk* oder auch *Drunkard's Walk*. Eigentlich wird dieser zur Generierung nicht deterministischer Zeitreihen, wie Aktienkurse in der Finanzmathematik verwendet, kann aber auch höhlenartige Level erzeugen.[@Wikipedia2020b] **\ref{imdrunk}** zeigt den Algorithmus als Pseudocode. Das Level wird als 2D-Array dargestellt und jeder Index im Array ist ein Feld im Level. Es gibt nicht betretbare Felder (Wände) und betretbare Felder (Böden). Zu Beginn wird ein zufälliges Feld als Startpunkt ausgewählt und zu einem Bodenfeld gemacht. Von diesem Feld aus wird nun in eine zufällige Richtung gegangen und das neue Feld wird wieder zu einem Bodenfeld umgewandelt. Dieser Prozess wird so lange wiederholt, bis die gewünschte Anzahl an Bodenfeldern vorhanden ist.  Der Vorteil des Random Walk Algorithmus liegt darin, dass sichergestellt werden kann, dass alle Bodenfelder auch erreichbar sind, da sie gezwungenermaßen alle miteinander verbunden sind. Allerdings bietet der Algorithmus abseits der gewünschten Bodenfelder keine Konfigurationsmöglichkeiten und die erzeugten Level ähneln sich von der Struktur sehr. \ref{drunkexample} zeigt ein Beispiel wie ein Dungeon aussehen könnte welches durch den Random Walk erzeugt wurde.

\begin{lstlisting}[language=python, label=imdrunk, caption={Pseudocode Random Walk}  ]
	erstelle ein Level in dem alle Felder Wände sind
	wähle ein Feld als Startpunkt aus
	verwandel das gewählte Feld in einen Boden
	solange noch nicht genügen Boden im Level existiert
    	mache ein Schritt in eine zufällige Richtung
    	wenn das neue Feld eine Wand ist
        	verwandel das Feld in einen Boden
\end{lstlisting}

**TODO QUELLE  [@roguebasin2014] **

![Beispielergebnis eines Random Walk. Schwarze Flächen sind Wände, weiße Flächen sind Böden. [@Hagen2019] \label{drunkexample}](figs/chapter2/drunk.png){width=100%}

## Graphen zur Darstellung von Level

In dieser Arbeit werden Level mithilfe von Graphen dargestellt, daher erläutert dieser Abschnitt die verwendete Notation und Annahmen. 

Ein Level-Graph $G$ besteht aus einer Menge an Knoten $V(G)$ und Kanten zwischen diesen Knoten $E(G)$. Knoten sind Räume, dabei spielt die Größe, der Inhalt und die Geometrie des Raumes keine Rolle. Eine Kante zwischen zwei Knotne gibt an, dass die Räume aneinander anliegend sind und mit einen Durchgang verbunden sind. Kanten können entweder gerichtet sein, dann kann ein Weg zwar von einem Raum in den anderen Raum führen, aber nicht zurück oder ungerichtet, dann kann ein Weg beidseitig passiert werden. Gerichtete Verbindungen werden durch einen Pfeil dargestellt, ungerichtete durch eine blanke Linie. Ein Level ist also eine Menge an Räumen, die miteinander verbunden sind. 

Da diese Arbeit sich auf 2D-Level konzentriert, müssen alle Level-Graphen planar sein. Ist ein Graph nicht planar, können die Räume nicht auf einer zweidimensionalen Fläche angeordnet werden ohne neue, nicht dargestellte, Verbindungen zu erzeugen. 

\ref{dam} zeigt ein Level aus dem Spiel *Dragon Age: Origins* in der Kartenansicht aus dem Spiel. Dieses Level wurde in \ref{dag} in Graphendarstellung gebracht. Da das Themenfeld der Graphentheorie besonders gut erforscht ist, können nun viele der bekannten Verfahren genutzt werden, um den Level-Graph zu analysieren.

**TODO: evlt verfahren benennen? Andererseits werden die vermutlich auch in der Modellierung zum tragen kommen und da passen die dann irgendwie besser hin** 

Die roten Kanten in \ref{dag} zeigen den kritischen Pfad. Der kritische Pfad ist der Weg, den der Spieler vom Start des Level (Knoten 1) bis zum Ende des Level (Knoten 20) gehen muss. Alle Knoten, die der kritische Pfad passiert, müssen also vom Spieler passiert werden. Mit diesem Wissen können Entscheidungen im Design getroffen werden, welche Inhalte der Spieler passieren muss und welche Hindernisse ihn auf den kritischen Pfad erwarten. Daraus ergibt sich auch, dass alle anderen Knoten optional sind, entsprechend können auch hier Designentscheidungen getroffen werden. Anhand des Graphen lässt sich auch feststellen, ob ein bestimmter Raum erreichbar ist ohne einen anderen zu betreten. So könnte der Zugang zu Raum 6 verschlossen sein und der Schlüssel in Raum 3 liegen. Mittels der Graphen-Analyse kann geschaut werden, ob Raum 3 betreten werden kann, ohne dabei Raum 6 zu passieren. Die Pfadlänge kann auch ermittelt werden, so können besonders lange Nebenpfade erkannt werden, bei Bedarf vermieden werden oder durch Abkürzungen wieder mit dem Hauptpfad verbunden werden. [@Mourato2013]

![Level aus dem Spiel Dragon Age: Origins. [@Ma2014] \label{dam}](figs/chapter2/damap.png){width=100%}

![\ref{dam} in Graphendarstellung \label{dag}](figs/chapter2/dragonageasgraph.png){width=100%}

Diese Art der Darstellung eignet sich vor allem für zweidimensionale Spiele, da auch der Graph zweidimensional ist. Zwar lassen sich auch 3D-Level mit Graphen darstellen, dies erhöht jedoch die Komplexität und erschwert die Übersicht. Auch zu beachten ist, dass der Graph nur die Struktur des Levels darstellt, jedoch nicht die Geometrie, das Aussehen oder den Inhalt.

## Rogue-Like

*Rogue: Exploring the Dungeons of Doom* \footnote{Rogue on Steam https://store.steampowered.com/app/1443430/Rogue/}, ist ein in den 1980er entwickeltes Dungeoncrawler-Rollenspiel. Dungeoncrawler sind Spiele, indem sich der Spieler durch ein Labyrinth gefüllt mit Gegnern und Rätseln kämpft und meist über mehrere Ebenen versucht das Spielziel (z .B. den Ausgang oder einen Schatz) zu erreichen.[@qrpg2020] In Rogue bewegt sich der Spieler rundenbasiert durch ein, durch ASCII-Zeichen dargestelltes, Levelsystem und versucht sich mithilfe von Gegenständen und Zaubersprüchen bis in die tiefste Ebene des Dungeons vorzukämpfen, um dort das Amulett von Yendor zu erlangen.[@MattBarton2009]

Dieses Spielkonzept ist zu dieser Zeit kein unbekanntes, was Rogue hingegen bis heute als eine der relevantesten Videospiele der Geschichte herausstellt, ist die prozedurale Generierung der Level. 

> But I think Rogue’s biggest contribution, and one that still stands out to this day, is that the computer itself generated the adventure in Rogue. Every time you played, you got a new adventure. That’s really what made it so popular for all those years in the early eighties.[@Wichman1997]

Bei jedem Neustart von Rogue werden die Level neu generiert. Das bedeutet der Aufbau der Level, die Anzahl und Positionierung von Monstern und Items unterscheiden sich mit jedem Spieldurchlauf. Man spielt also niemals zweimal dieselbe Partie von Rogue. Zusätzlich setzt Rogue auf dem sogenannten Permadeath. Stirbt der Spieler im Dungeon, verliert er all seinen Fortschritt und muss das Spiel von vorne beginnen, mit neu generiertem Level. Rogue zeichnet sich also vor allem dadurch aus, dass jeder Spieldurchlauf anders als der andere ist und dadurch ein besonders hohes Maß an Abwechslung und damit Wiederspielwert gegeben ist.  

Rogue konnte sich schnell an einiger Beliebtheit erfreuen und es dauert nicht lange bis andere Entwickler ähnliche Spiele mit prozedural generierten Inhalten veröffentlichten. [@MattBarton2009] Es entwickelte sich das Genre der Rogue-Likes.

> [... ] Rogue likes are called Rogue likes, because the games are literally like Rogue [... ] [@Brown2017]

In den Jahren haben viele Entwickler versucht Regeln für das Genre aufzustellen, also Bedingungen, die ein Spiel erfüllen muss, um sich als Rogue-Like bezeichnen zu dürfen. 2008 wurde auf der Internationalen Rogue-Like Entwickler Konferenz eine Liste mit verschiedenen Faktoren veröffentlicht. Diese Liste ist als *Berliner Interpretation* bekannt. [@Conference2008] Über die Jahre wurde die Interpretation harsch kritisiert und sogar als "downright nonsense"  bezeichnet. [@Grey2013] Die Berliner Interpretation schade der kreativen Freiheit.

Heute muss ein Spiel nur zwei wichtige Features implementieren, um *like* Rogue zu sein, prozedural generierte Level und Permadeath. [@Brown2019] Das Genre ist daher nicht nur noch auf Rollenspiele in Labyrinthen begrenzt, sondern umfasst mittlerweile Spiele aus den unterschiedlichsten Genres wie Platformer, Shooter oder Action-Adventures und vielen mehr. [@Wikipedia2020]

## PM-Dungeon

Im praktischen Anteil des Moduls Programmiermethoden sollen die Studenten das gelernte Wissen aus dem theoretischen Anteil anwenden und vertiefen, dafür bekommen sie in regelmäßigen Abständen Aufgaben gestellt. Um die Aufgaben in einen gemeinsamen Kontext zu bringen und zeitgleich die Motivation der Studenten zu steigern, wurde 2021 das PM-Dungeon eingeführt. Über den Verlauf des Semesters entwickeln die Studenten ihr eigenes Rogue-Like Rollenspiel. Zwar stehen weiterhin die Lehrinhalte im Fokus, dennoch haben die Studenten viele Freiheiten um ihr Spiel nach ihren Wünschen zu gestalten. 

Sie konzeptionieren eigenständig das Verhalten von Monstern, implementieren Schatztruhen und Items sowie unterschiedliche Fähigkeiten, die der Spieler im Laufe des Spiels freischalten kann. Für die Entwicklung des Spiels bekommen die Studenten ein extra dafür entwickeltes Framework zur Verfügung gestellt, das PM-Dungeon-Framework. Das PM-Dungeon-Framework erweitert das libGDX-Framework \footnote{libGDX https://libgdx.com} um vereinfachte Schnittstellen zur grafischen Darstellung. Die Studenten können sich daher rein auf die Implementierung der Spielfeatures konzentrieren.

![Ausschnitt aus dem PM-Dungeon \label{pmd}](figs/chapter2/pmd.png){width=100%}

Abbildung \ref{pmd} zeigt einen Ausschnitt aus dem Startlevel einer Beispielimplementierung des PM-Dungeons. Die Spielfigur (grüner Kreis) muss mithilfe der Leiter (blauer Kreis) in die nächste Ebene gebracht werden. Auf den Weg dorthin kann der Spieler die Monster (roter Kreis) töten, um Erfahrungspunkte zu sammeln oder Items zu finden. Sowohl die Spielerposition als auch die Position der Monster werden zu Beginn des Levels zufällig bestimmt. Das Spielziel des PM-Dungeon ist an den Highscore-Automaten angelehnt, es geht also nicht darum ein bestimmten Boss zu besiegen oder ein Item zu sichern sondern darum möglichst tief in das endlose Dungeon vorzudringen und mit jedem durchlauf seinen persönlichen Rekord zu brechen. Jedoch kann auch das Spielziel kann von den Studenten frei verändert werden.

## Regeln für gutes Level-Design

Der Begriff Level-Design kann unterschiedlich interpretiert werden. Im Allgemeinen beschreibt der Begriff die Erstellung und Bearbeitungen von Spielwelten für Videospiele. [@Wikipedia2020a] Es gibt keine festen vorgaben dafür, welche Aspekte Teil des Level-Designs sind und welche bereits darüber hinausgehen. [@DevPlay2019] Im Rahmen dieser Arbeit beschreibt Level-Design den örtlichen Aufbau der Spielwelt, die Platzierung von Gegnern, Items und anderen Objekten sowie die optische Gestaltung der Level, dabei ist nicht das Erstellen von Texturen gemeint, sondern die Verwendung dieser. 

Auch wenn es viel Diskussion darüber gibt, ob Videospiele Kunst sind oder nicht, muss man den kreativen Schaffensprozess respektieren.[@Petzold2019] Daher lassen sich für Videospiele auch keine festen Regeln definieren, wie bestimmte Designelemente zu sein haben. 

In diesem Abschnitt werden Regeln für gutes Level-Design präsentiert. Sie sollten weder als objektive Maßstäbe noch als verpflichtende Gesetzte betrachtet werden, sondern vielmehr als Leitpfaden. Die Liste erhebt keinen Anspruch auf Allgemeingültigkeit, Korrektheit oder Vollständigkeit Sie wurde gezielt für die in dieser Arbeit betrachteten Probleme zusammengestellt. Zwar beschäftigt sich die Wissenschaft sehr wohl mit Videospielen und deren Level, jedoch nicht auf die Ausarbeitung objektiver Bewertungskriterien, daher basieren die Regeln dieser Liste auf Aussagen verschiedener Persönlichkeiten der Videospielbranche. 

Die Grundlagen der Regeln stammen aus Dan Taylors *Ten Principles of Good Level-Design* [@Taylor2013] [@Taylor2018] und Tim Ryans *Beginning Level-Design* [@Ryan1999] und wurden bei Bedarf um weitere Aspekte, Meinungen und Beispielen ergänzt. 

### Lösbarkeit und Fehlerfreiheit

Ein Level muss lösbar sein. Es darf keine Fehler geben, die das Voranschreiten verhindern. Es sollte zusätzlich darauf geachtet werden, dass Spieler nicht in Sackgassen geraten könne. Braucht der Spieler beispielsweise ein bestimmtes Item, um weiterzukommen, sollte er das Item an diesem Punkt immer noch erreichen können. Bei Spielen, die Lebensenergie verwenden, sollte das Level gelöst werden können, ohne Lebensenergie zu verlieren, so bleiben Spieler, die im vorherigen Abschnitt viel Schaden bekommen haben, nicht stecken. Gibt es im Spiel optionale Wege oder Ziele, müssen auch diese lösbar sein und dürfen nicht durch einen Fehler unlösbar werden. Bei optionalen Inhalten liegt es in der kreativen Entscheidungsfreiheit des Entwicklers, ob diese auch immer erreichbar sein müssen oder ob zum Beispiel ein optionales Item benötigt wird, welches nicht mehr erreichbar ist. Zusätzlich sollten die Level frei von Fehlern sein, die Spielern Möglichkeiten wie das ungewollte Überspringen von Abschnitten erlauben.

**Regel 1: Gute Level sind lösbar und fehlerfrei.** 

### Driven by  game’s mechanics

> Always remember that interactivity is what makes videogames different from any other form of entertainment: books have stories, movies have visuals, games have interaction. If your Level-Design isn’t showcasing your game mechanics, your players might as well be watching a movie or reading a book. [@Taylor2013]

> Above all else, great Level-Design is driven by interaction - the game’s mechanics. Game levels don’t just provide context for mechanics, they provide the very reality in which they exist. [@Taylor2013]

Das Level ist der Spielplatz für den Spieler, in dem er die verschiedenen Mechaniken des Spiels anwenden kann. Daher müssen alle Level so gebaut werden, dass sie die Mechaniken beanspruchen und es Spaß macht sie zu verwenden. Die Kernmechaniken des Spiels sollten in jedem Level gefordert sein. Ein Shooter braucht gute Kampfareale, ein Rennspiel spaßige Rennstrecken und ein Platformer gute Hindernisse. Ein Rogue-Like braucht daher abwechslungsreiche Dungeons die zum erkunden einladen und mit Monstern und Items gefüllt sind. Gute Level bieten den Spieler verschiedene Möglichkeiten die Herausforderungen auf unterschiedliche Weise unter Einbeziehung der Mechaniken zu lösen.

**Regel 2: Gute Level fordern die Mechaniken des Spiels.** 

### Balancing

Beim balancen geht es darum den Schwierigkeitsgrad der Herausforderungen zu bestimmen. Ist ein Spiel zu einfach kann es schnell anspruchslos und langweilig werden, ist es zu schwer kann es frustrierend werden. 

> The trick to good Level-Design is to present challenges that are difficult enough to merit the players’ attention and make their heart or mind race, but not so difficult as to always leave them failing and disappointed. [@Ryan1999] 

Da nicht jeder Spieler gleich gut beim spielen ist und die Vorlieben der Spieler sich unterscheiden, ist ein fest vorgegebener Schwierigkeitsgrad oft nicht zielführend. Oft lassen Spiele den Spieler selbst entscheiden, wie schwer das Spiel werden soll, indem sie vor dem Spielstart die Auswahl verschiedener Schwierigkeitsgrade anbieten. Je nach gewählter Option werden dann stärkere oder schwächere Monster platziert, die Lebenspunkte der Spielfigur angepasst oder andere Hilfestellung gegeben oder neue Hindernisse platziert. [@DevPlay2019a] Diese Variante kann sehr effektiv sein, setzt aber voraus, dass der Spieler seine eigenen Fähigkeiten richtig einschätzt, was vor allem dadurch erschwert wird, wenn der Spieler noch keine Erfahrung mit dem Spiel gemacht hat. Da sich die Auswirkungen des Schwierigkeitsgrades auch von Spiel zu Spiel unterscheiden, kann eine gute Selbsteinschätzung auch daran scheitern.

> [... ] they (the players) might pick the easier option even though they could handle more challenge, and rob themselves of the best, and designer-intended experience [@Brown2016]

Eine andere Möglichkeit zum balancen ist die Verwendung von dynamischen Schwierigkeitsgraden. Im Spiel *Resident Evil 4*\footnote{Resident Evil 4 Homepage https://www.residentevil.com/4/} werden mehr oder weniger Gegner platziert, abhängig davon ob der Spieler bisher besonders gut durch die Level gekommen ist oder oft gestorben. [@Brown2015] Im Spiel *Half-Life* \footnote{Half-Life on Wikipedia https://de.wikipedia.org/wiki/Half-Life} werden mehr Medikits platziert, wenn der Spieler wenig Lebenspunkte hat. [@Brown2016a] 

Es sollte beachtet werden, dass Spieler mit voranschreiten immer besser werden, daher sollten frühe Level deutlich einfacher sein als Level gegen Ende des Spiels. Dabei muss die Schwierigkeit der Level nicht linear steigen, es bietet sich an nach einem besonders schweren Level ein einfacheres Level einzubauen, um den Spieler Zeit zum Aufatmen zu lassen.

**Regel 3: Gute Level sind gut gebalanced.** 

### Risk and Reward

Risk and Reward beschreibt Momente im Spiel, bei den der Spieler eine besonders schwere Herausforderung lösen muss, dafür aber auch entsprechend belohnt wird. Diese stets optionalen Momente sollten in regelmäßigen Abständen auftauchen, um den Spieler vor eine interessante Entscheidung zu stellen, ob sie die Herausforderung annehmen oder lieber ablehnen möchten.

Vor allem bei Rogue-Like Spielen sollte es ständig zu solchen Momenten kommen, da diese hier einen erhöhten Nervenkitzel bieten. Jede Herausforderung könnte den Tod bedeuten und damit den permanenten Verlust des Fortschrittes. Die Frage, ob sich der Kampf wirklich lohnt und ob man das Risiko eingehen sollte, fällt hier umso schwerer. 

**Regel 4: Gute Level haben Risk and Reward Momente.** 

### Pacing

![Pacing im Wüstenpalast. \label{zelda3pacing}](figs/chapter2/Zelda3Pacing.png){width=100%} 

**TODO \ref{zelda3pacing} in schön** 

Der Begriff Pacing entstammt der Filmbranche und beschreibt die Spannungskurve des Films. Aus dem Film lassen sich drei Regeln für gutes Pacing ableiten, welche auch für Videospiele Anwendung finden können.[@Wesowski2009]

1. Pacing verläuft nicht linear, nach Hochpunkten sollte ein Tiefpunkt folgen 
2. Zu Beginn sollte viel Spannung erzeugt werden, um den Zuschauer bei Stange zu halten 
3. Kurz vor Ende sollte der spannendste Moment sein, der sich am Ende entlädt

Auch Videospiele sollten darauf achten ihre Spannungskurve zu kontrollieren. Gutes Pacing sorgt dafür, dass ein Spiel nicht langweilig oder repetitiv wird.  [@Brown2018]

\ref{zelda3} zeigt den Level-Graph für den Wüstenpalast aus dem Spiel *The Legend of Zelda: A Link to the past* \footnote{A Link to the past on Zeldachronicles https://zeldachronicles.de/spiele/alttp/}. Um das Level zu bestehen, muss der Spieler den Boss im letzten Raum besiegen. 

Das Level startet mit einem kurzen linearen Abschnitt (blau).Der Spieler sieht zum ersten mal wie das Dungeon von innen gestalten ist, hört die extra für das Level komponierte Musik und wir mit einen neuen Gegner Typ, den Lastertürmen konfrontiert. Die Neugier des Spielers ist geweckt. 

Im nächsten Abschnitt (grün) werden keine neuen Gegner eingeführt. Dieser Abschnitt ist zum Erkunden gedacht. Der Spieler muss den Weg zum Boss finden, kann aber zeitgleich verschiedene Nebenpfade erkunden um Schätze zu finden. Einige Bereiche sind mit Türen verschlossen für die der Spieler die Schlüssel in anderen Räumen finden muss. Die anfänglich aufgebaute Spannung fällt hier etwas ab, steigt aber dann wieder, wenn der Spieler sich auf den finalen Weg zum Boss macht. 

Der letzte Abschnitt (rot) ist maximal linear. Betritt der Spieler diesen Weg werden sich die Türen hinter ihn schließen und der Weg führt nur noch nach vorne, es gibt kein zurück mehr. Die Musik wird schneller und es tauchen deutlich mehr Gegner als vorher auf. Es geht nicht mehr darum das Level zu erkunden sondern darum sich zum Boss vorzukämpfen. Rätsel oder verschlossen Türen gibt es keine mehr. Je nähr der Spieler den Boss kommt, desto näher neigt sich auch die Spannungskurve ihrem Höhepunkt. Im letzten Raum vor den Boss befinden sich noch Items um die Lebensenergie aufzufüllen, die Tür zum Endgegner ist deutlich markiert und der Spieler weiß, dass es jetzt soweit ist. In den Moment in dem der Spieler den Bossraum betritt und diesen zum ersten mal sieht hat die Spannungskurve ihren Höhepunkt erreicht und wird diesen bis zum Ende des Kampes auch nicht mehr verlassen. Der Boss ist besiegt, der Spieler hat gesiegt und die Spannungskurve fällt ab. Das Level wurde erfolgreich absolviert. \ref{zelda3pacing} zeigt eine exemplarische Darstellung der Spannungskurve für dieses Level. Die einzelnen Abschnitte sind passen zu \ref{zelda3} gefärbt. 

![Wüstenpalast aus The Legend of Zelda 3 in Graphendarstellung \label{zelda3}](figs/chapter2/Zelda3Dungeon.png){width=100%}

Die Entwickler von Zelda haben im Wüstenpalast also drei unterschiedliche Strukturen genutzt um das Pacing zu kontrollieren. Ein kurzen schlauchartigen Abschnitt um die Atmosphäre aufzubauen, einen offen Abschnitt zu Erkunden und am Ende einen streng linearen Abschnitt bis zum Boss. Durch diesen Wechsel der Strukturen konnten sie das Pacing nach ihren belieben kontrollieren. 

**Regel 5: Gute Level steuern das Pacing des Spiels.** 

### Einzigartigkeit

Die einzelnen Level sollten sich stark voneinander unterscheiden. Auch wenn das überliegende Designkonzept über den gesamten Spielverlauf konstant sein sollte.

> [...] people don’t like playing the same level twice.[@Ryan1999]

Variation in Gegner, Texturen und Strukturen helfen dabei die Abwechslung im Level zu gewährleisten und keine Langeweile aufkommen zu lassen. Dies gilt auch wenn der Spieler einen Abschnitt im Spiel erneut ablaufen muss, sogenanntes Backtracking. Wenn es längere Nebenpfade gibt, sollte das Ende des Nebenpfades eine Abkürzung zurück zum Hauptpfad bieten. 

**Regel 6: Gute Level sind einzigartig.** 

### Effizienz 

Spieleentwicklung ist ein kostspieliges Unterfangen und bereits kleinere Produktionen können mehrere Millionen Dollar kosten. [@DevPlay2017] Daher ist die effiziente Nutzung von Ressourcen unabdingbar. Gute Level-Designer erstellen ein Set aus Modulen, mit verschiedenen Assets und Events. Diese Module können dann miteinander kombiniert und bei Bedarf angepasst werden. Aus einer Handvoll solcher Module lassen sich bereits viele verschiedene Level und Situationen erzeugen. Effizienz ist der größte Vorteil von guten prozedurale Algorithmen.

**Regel 7: Gute Level sind effizient in der Herstellung.** 

## Bewertungsschema

Um im weiteren Verlauf der Arbeit die verschiedenen Algorithmen zur prozeduralen Generierung bewerten und miteinander vergleichen zu können, wird anhand der vorgestellten Regeln ein Bewertungsschema erstellt. Für jede Regel werden verschiedene Kriterien aufgestellt, die angeben, ob und inwiefern diese erfüllt sind. Die Kriterien werden so gewählt, dass sie auf das Anwendungsszenario optimiert sind. Die Tabelle \ref{bkt} listet die Kriterien auf. Für jedes erfüllte Kriterium wird ein Punkt verteilt, die Summe der Punkte gibt die Güte des Algorithmus im Vergleich zu den anderen Algorithmen an (vgl. \ref{bkfk}). 

| Regel                   | Kriterium                                           | Anmerkung                                                    |
| ----------------------- | --------------------------------------------------- | ------------------------------------------------------------ |
| Gute Level sind lösbar. | Sind alle Orte und Gegenstände im Level erreichbar? | Dies ist eine kritische Anforderung. Level, die dieses Kriterium nicht erfüllen, sind inakzeptable und mit null Punkten zu bewerten. |
| Gute Level fordern die Mechaniken des Spiels. | Sind die Level für die grundlegenden Spielmechaniken der Anwendungsumgebung ausgelegt? |Monster und Items müssen im Dungeon platzierbar sein.|
|  | Sind die Level für weitere Mechaniken ausgelegt? |Können Türen, Hebel, Schlüssel, Schlösser, Rätsel, Shops o.ä platziert werden?|
|  | Können die Level manipuliert werden? |Kann die Struktur des Levels im Spiel manipuliert werden, zum Beispiel durch Bomben?|
| Gute Level sind gut gebalanced | Kann das Balancing vom Entwickler angepasst werden? |Kann der Entwickler im laufenden Spiel bestimmen wie schwer das Level sein soll?|
| Gute Level haben Risk and Reward Momente | Sind Nebenpfade möglich? |Erzeugt der Generator Level, die Wege abseits des kritischen Pfades haben?|
|  | Können Items und Monster gezielt platziert werden? |Nebenpfade und kritische Pfade müssen für den Entwickler unterscheidbar sein. Jeder Knoten muss gezielt ansprechbar sein.|
| Gute Level steuern das Pacing des Spiels | Erzeugt der Algorithmus Level mit abwechslungsreichen Pacing? |Haben die Level unterschiedliche Strukturen, die das Pacing beeinflusse (vgl. \ref{zelda3})?|
|  | Kann das Pacing kontrolliert werden? |Kann der Entwickler gezielt bestimmte Strukturen konfigurieren?|
| Gute Level sind einzigartig | Unterscheiden die Level sich ausreichend im Aufbau? ||
| | Unterscheiden die Level sich ausreichend im Aussehen? ||
|  | Kann Backtracking vermieden werden? ||
| Gute Level sind effizient in der Herstellung. | Kommt der Algorithmus ohne Input-Daten aus? |Input Daten sind in diesem Sinne Graphen oder Layouts für Räume.|
| | Können verschiedene einzigartige Level aus denselben Input-Daten generiert werden? |Wenn keine Input-Daten benötigt werden, 1 Punkt.|

: Tabelle mit den Bewertungskriterien für prozedurale Algorithmen. \label{bkt}

Die Güte $G$ eines Level lässt sich durch die Summe aller vergebenen Punkte
$P\backslash L$ multipliziert mit dem Bewertungspunkt Lösbarkeit $L$ der Bewertungskriterien berechnen. 
$$
\label{bkfk} 
G = L * ( \sum P_{i})
$$

**TODO CAPTION Formel zur Berechnung der Güte eines Level.**  



## Analyse der Ausgangssituation 

**TODO Besser erklären, wirkt noch total "ja ist halt so"** 

Aktuell besitzt das PM-Dungeon keinen eigenen Level-Generator, stattdessen werden vier Level mitgeliefert. Um die Notwendigkeit eines eigenen prozeduralen Generators für das PM-Dungeon zu begründen, wird im Folgenden die Güte der aktuellen Level Situation bewertet. 

Kriterium: Sind alle Orte und Gegenstände im Level erreichbar? $\newline$Begründung:  In allen Level kann jeder Raum betreten werden. Objekte werden im Raum verteilt und sind daher auch erreichbar.$\newline$Bewertung: 1

Kriterium: Sind die Level für die grundlegenden Spielmechaniken der Anwendungsumgebung ausgelegt?$\newline$Begründung: Die Level können mit Monstern und Gegenständen gefüllt werden. Die Level haben alle einen Ausgang.<br>Bewertung: 1

Kriterium: Sind die Level für weitere Mechaniken ausgelegt?$\newline$Begründung: Das Dungeon unterscheidet nicht zwischen unterschiedlichen Objekten, daher können auch Händler, Hebel etc. zufällig im Level platziert werden. Bewertung: 1

Kriterium: Können die Level manipuliert werden?$\newline$Begründung: Die Level sind statisch und sind nicht zur Manipulation gedacht.$\newline$Bewertung: 0

Kriterium: Kann das Balancing vom Entwickler angepasst werden?$\newline$Begründung: Objekte und Monster werden nach dem laden des Level platziert. Entwickler platzieren diese selber und können daher verschiedenen Parameter beim Verteilen berücksichtigen. $\newline$Bewertung: 1 

Kriterium: Sind Nebenpfade möglich?$\newline$Begründung: Einige Level bieten Nebenpfade.$\newline$Bewertung: 1

Kriterium:   Können Items und Monster gezielt platziert werden?$\newline$Begründung: Entwickler können die Struktur des Dungeons nicht abfragen. Objekte können nur zufällig im Level platziert werden.$\newline$Bewertung: 0 

Kriterium: Erzeugt der Algorithmus Level mit abwechslungsreichen Pacing?$\newline$Begründung: Kein eigener Generator vorhanden.$\newline$Bewertung: 0

Kriterium:   Kann das Pacing kontrolliert werden?$\newline$Begründung: Die Level sind fest vorgegeben und können nicht verändert werden.$\newline$Bewertung: 0

Kriterium: Unterscheiden die Level sich ausreichend im Aufbau?$\newline$Begründung: Die einzelnen Level unterscheiden sich im Aufbau, sind aber bei jeden Spieldurchlauf gleich und können daher nicht als einzigartig bezeichnet werden.$\newline$Bewertung: 0

Kriterium: Unterscheiden die Level sich ausreichend im Aussehen?$\newline$Begründung: Die Level greifen alle auf denselben Texturenpool zu und unterscheiden sich optisch nicht voneinander. $\newline$Bewertung: 0

Kriterium: Kann Backtracking vermieden werden?$\newline$Begründung: Die zur Verfügung gestellten Level sind klein genug, so das Backtracking kein Problem ist. $\newline$Bewertung: 1

Kriterium: Kommt der Algorithmus ohne Input-Daten aus?$\newline$Begründung: Die Level müssen im JSON Format vorliegen.$\newline$Bewertung: 0

Kriterium:   Können verschiedene einzigartige Level aus denselben Input-Daten generiert werden?$\newline$Begründung: Die Levelstruktur ist durch die JSON Datei fest vorgegeben. Dieselbe Datei erzeugt immer dasselbe Level.  $\newline$ Bewertung: 0

Nach \ref{bkfk} ergibt sich daher eine Güte von **5**. 

Zwar genügen die bereitgestellten Level zum Erfüllen des Lernzieles, jedoch könnte ein eigener Generator die Studenten weiter motivieren ihr Spiel im inhaltlichen Teil auszubauen. Am Ende des Semesters würde so ein eigenes fertiges Spiel entstehen und nicht nur ein Prototyp eines Spiels. 





