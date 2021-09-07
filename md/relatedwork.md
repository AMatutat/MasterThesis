# Grundlagen

In diesem Kapitel werden die benötigten Grundlagen für die Arbeit geschaffen. Es wird das PM-Dungeon und das Genre Rouge-Like als Anwendungsumgebung beschrieben. Danach wird die Graphendarstellung für Level eingeführt und anschließend sechs Regeln für gutes Level Design erläutert. Der Begriff der Prozeduralen Generierung wird umfangreicher definiert und mit Beispielen erläutert. Mithilfe der vorgestellten Regeln werden dann Bewertungskriterien für prozedurale Level-Generatoren für Rouge-Like Spiele aufgestellt. Das Kapitel endet mit der Analyse und Bewertung der aktuellen Level des PM-Dungeons. 

## Rouge-Like

*Rogue: Exploring the Dungeons of Doom*, ist ein in den 1980er entwickeltes Dungeoncrawler-Rollenspiel. Dungeoncrawler sind Spiele, indem sich der Spieler durch ein Labyrinth gefüllt mit Gegnern und Rätseln kämpft und meist über mehrere Ebenen versucht das Spielziel (z .B. den Ausgang oder einen Schatz) zu erreichen.[@qrpg2020] In Rogue bewegt sich der Spieler rundenbasiert durch ein, durch ASCII-Zeichen dargestelltes, Levelsystem und versucht sich mithilfe von Gegenständen und Zaubersprüchen bis in die tiefste Ebene des Dungeons vorzukämpfen, um dort das Amulett von Yendor zu erlangen.[@MattBarton2009]

Dieses Spielkonzept ist zu dieser Zeit kein unbekanntes, was Rogue hingegen bis heute als eine der relevantesten Videospiele der Geschichte herausstellt, ist die prozedurale Generierung der Level. 

> But I think Rogue’s biggest contribution, and one that still stands out to this day, is that the computer itself generated the adventure in Rogue. Every time you played, you got a new adventure. That’s really what made it so popular for all those years in the early eighties.[@Wichman1997]

Bei jedem Neustart von Rogue werden die Level neu generiert. Das bedeutet der Aufbau der Level, die Anzahl und Positionierung von Monstern und Items unterscheiden sich mit jedem Spieldurchlauf. Man spielt also niemals zweimal dieselbe Partie von Rogue. Zusätzlich setzt Rogue auf dem sogenannten Permadeath. Stirbt der Spieler im Dungeon, verliert er all seinen Fortschritt und muss das Spiel von vorne beginnen, mit neu generiertem Level. Rogue zeichnet sich also vor allem dadurch aus, dass jeder Spieldurchlauf anders als der andere ist und dadurch ein besonders hohes Maß an Abwechslung und damit Wiederspielwert gegeben war.  

Rogue konnte sich schnell an einiger Beliebtheit erfreuen und es dauert nicht lange bis andere Entwickler ähnliche Spiele mit prozedural generierten Inhalten veröffentlichten. [@MattBarton2009] Es entwickelte sich das Genre der Rogue-Likes.

> [... ] Rogue likes are called Rogue likes, because the games are literally like Rogue [... ] [@Brown2017]

In den Jahren haben viele Entwickler versucht Regeln für das Genre aufzustellen, also Bedingungen, die ein Spiel erfüllen muss, um sich als Rogue-Like bezeichnen zu dürfen. 2008 wurde auf der Internationalen Rogue-Like Entwickler Konferenz eine Liste mit verschiedenen Faktoren veröffentlicht. Diese Liste ist als Berliner Interpretation bekannt. [@Conference2008] Über die Jahre wurde die Interpretation harsch kritisiert und sogar als *downright nonsense* bezeichnet. [@Grey2013] Die Berliner Interpretation schade der kreativen Freiheit.

Heute muss ein Spiel nur zwei wichtige Features implementieren, um *like* Rogue zu sein, prozedural generierte Level und Permadeath. [@Brown2019] Das Genre ist daher nicht nur noch auf Rollenspiele in Labyrinthen begrenzt, sondern umfasst mittlerweile Spiele aus den unterschiedlichsten Genres wie Plattformer, Shooter oder Action-Adventures und vielen mehr. [@Wikipedia2020]

## Die Anwendungsumgebung 

Im praktischen Anteil des Moduls Programmiermethoden sollen die Studenten das gelernte Wissen aus dem theoretischen Anteil anwenden und vertiefen, dafür bekommen sie in regelmäßigen Abständen Aufgaben gestellt. Um die Aufgaben in einen gemeinsamen Kontext zu bringen und zeitgleich die Motivation der Studenten zu steigern, wurde 2021 das PM-Dungeon eingeführt. Über den Verlauf des Semesters entwickeln die Studenten ihr eigenes Rouge-Like Rollenspiel. Zwar stehen weiterhin die Lehrinhalte im Fokus, dennoch haben die Studenten viele Freiheiten um ihr Spiel nach ihren Wünschen zu gestalten. Sie konzeptionieren eigenständig das Verhalten von Monstern, implementieren Schatztruhen und Items sowie unterschiedliche Fähigkeiten, die der Spieler im Laufe des Spiels freischalten kann. Für die Entwicklung des Spiels bekommen die Studenten ein extra dafür entwickeltes Framework zur Verfügung gestellt, das PM-Dungeon-Framework. Das PM-Dungeon-Framework erweitert das libGDX-Framework \footnote{libGDX: https://libgdx.com} um vereinfachte Schnittstellen zur grafischen Darstellung. Die Studenten können sich daher rein auf die Implementierung der Spielfeatures konzentrieren.

![Ausschnitt aus dem PM-Dungeon \label{pmd}](figs/chapter2/pmd.png){width=100%}

Abbildung \ref{pmd} zeigt einen Ausschnitt aus dem Startlevel einer Beispielimplementierung des PM-Dungeons. Die Spielfigur (grüner Kreis) muss mithilfe der Leiter (blauer Kreis) in die nächste Ebene gebracht werden. Auf den Weg dorthin kann der Spieler die Monster (roter Kreis) töten, um Erfahrungspunkte zu sammeln oder Items zu finden. Sowohl die Spielerposition als auch die Position der Monster und der Leiter werden zu Beginn des Levels zufällig bestimmt. 

## Graphen zur Darstellung von Level

- Wie verwendet man Graphen zur darstellung von Level?
- Warum ist das gut?
- Welche Vortiele bietet das?
- Welche Nachteile und Grenzen gibt es?
- Warum mach ich das in dieser arbeit so?

## Regeln für gutes Level Design

Der Begriff Level Design kann unterschiedlich interpretiert werden. Im Allgemeinen beschreibt der Begriff die Erstellung und Bearbeitungen von Spielwelten für Videospiele. [@Wikipedia2020a] Es gibt keine festen vorgaben dafür, welche Aspekte Teil des Level-Designs sind und welche bereits darüber hinausgehen. [@DevPlay2019] Im Rahmen dieser Arbeit beschreibt Level Design den örtlichen Aufbau der Spielwelt, die Platzierung von Gegnern, Items und anderen Objekten sowie die optische Gestaltung der Level, dabei ist nicht das Erstellen von Texturen gemeint ist, sondern die Verwendung dieser. 

Auch wenn es viel Diskussion darüber gibt, ob Videospiele Kunst sind oder nicht, muss man den kreativen Schaffensprozess eingestehen.[@Petzold2019] Daher lassen sich für Videospiele auch keine festen Regeln definieren, wie bestimmte Designelemente zu sein haben. In diesem Abschnitt werden Regeln für gutes Level Design präsentiert. Sie sollten weder als objektive Maßstäbe noch als verpflichtende Gesetzte betrachtet werden, sondern vielmehr als Leitpfaden. Die Liste erhebt keinen Anspruch auf Allgemeingültigkeit, Korrektheit oder Vollständigkeit Sie wurde gezielt für die in dieser Arbeit betrachteten Probleme zusammengestellt. Zwar beschäftigt sich die Wissenschaft sehr wohl mit Videospielen und deren Level, jedoch nicht auf die Ausarbeitung objektiver Bewertungskriterien, daher basieren die Regeln dieser Liste auf Aussagen verschiedener Persönlichkeiten der Videospielbranche. Dabei ist zu bedenken, dass diese Branche dazu neigt einen Personenkult, um einige Entwickler aufzubauen. Auch wenn sich bei der Aufarbeitung der Regeln bemüht wurde, Aspekte zu wählen, die von unterschiedlichen Entwicklern als relevant betrachtet werden, muss in Betracht gezogen werden, dass viele Entwickler sich von den bekannten Persönlichkeiten beeinflusst haben lassen.

Die Grundlagen der Regeln stammen aus Dan Taylors *Ten Principles of Good Level Design* [@Taylor2013] [@Taylor2018] und Tim Ryans *Beginning Level Design* [@Ryan1999] und wurden bei Bedarf um weitere Aspekte, Meinungen und Beispielen ergänzt. 

### Lösbarkeit und Fehlerfreiheit

Ein Level muss lösbar sein. Es darf keine Fehler geben, die das Voranschreiten verhindern. Es sollte zusätzlich darauf geachtet werden, dass Spieler nicht in Sackgassen geraten könne. Braucht der Spieler beispielsweise ein bestimmtes Item, um weiterzukommen, sollte er das Item an diesem Punkt immer noch erreichen können. Bei Spielen, die Lebensenergie verwenden, sollte das Level gelöst werden können, ohne Lebensenergie zu verlieren, so bleiben Spieler, die im vorherigen Abschnitt viel Schaden bekommen haben, nicht stecken. Gibt es im Spiel optionale Wege oder Ziele, müssen auch diese lösbar sein und dürfen nicht durch einen Fehler unlösbar werden. Bei optionalen Inhalten liegt es in der kreativen Entscheidungsfreiheit des Entwicklers, ob diese auch immer erreichbar sein müssen oder ob zum Beispiel ein optionales Item benötigt wird, welches nicht mehr erreichbar ist. Zusätzlich sollten die Level frei von Fehlern sein, die Spielern Möglichkeiten wie das ungewollte Überspringen von Abschnitten erlauben.

**Regel 1: Gute Level sind lösbar und fehlerfrei.** 

### Driven by  game’s mechanics

> Always remember that interactivity is what makes videogames different from any other form of entertainment: books have stories, movies have visuals, games have interaction. If your level design isn’t showcasing your game mechanics, your players might as well be watching a movie or reading a book. [@Taylor2013]

> Above all else, great level design is driven by interaction - the game’s mechanics. Game levels don’t just provide context for mechanics, they provide the very reality in which they exist. [@Taylor2013]

Das Level ist der Spielplatz für den Spieler, in dem er die verschiedenen Mechaniken des Spiels anwenden kann. Daher müssen alle Level so gebaut werden, dass sie die Mechaniken beanspruchen und es Spaß macht sie zu verwenden. Die Kernmechaniken des Spiels sollten in jedem Level gefordert sein. Ein Shooter braucht gute Kampfareale, ein Rennspiel spaßige Rennstrecken und ein Platformer gute Hindernisse. Gute Level bieten den Spieler verschiedene Möglichkeiten die Herausforderungen auf unterschiedliche Weise unter Einbeziehung der Mechaniken zu lösen.

**Regel 2: Gute Level fordern die Mechaniken des Spiels.** 

### Balancing

Beim balancen geht es darum den Schwierigkeitsgrad der Herausforderungen zu bestimmen. Ist ein Spiel zu einfach kann es schnell anspruchslos und langweilig werden, ist es zu schwer kann es frustrierend werden. 

> The trick to good level design is to present challenges that are difficult enough to merit the players’ attention and make their heart or mind race, but not so difficult as to always leave them failing and disappointed. [@Ryan1999] 

Da nicht jeder Spieler gleich gut beim spielen ist und die Vorlieben der Spieler sich unterscheiden, ist ein fest vorgegebener Schwierigkeitsgrad oft nicht zielführend. Oft lassen Spiele den Spieler selbst entscheiden, wie schwer das Spiel werden soll, indem sie vor dem Spielstart die Auswahl verschiedener Schwierigkeitsgrade anbieten. Je nach gewählter Option werden dann stärkere oder schwächere Monster platziert, die Lebenspunkte der Spielfigur angepasst oder andere Hilfestellung gegeben oder neue Hindernisse platziert. [@DevPlay2019a] Diese Variante kann sehr effektiv sein, setzt aber voraus, dass der Spieler seine eigenen Fähigkeiten richtig einschätzt, was vor allem dadurch erschwert wird, wenn der Spieler noch keine Erfahrung mit dem Spiel gemacht hat. Da sich die Auswirkungen des Schwierigkeitsgrades auch von Spiel zu Spiel unterscheiden, kann eine gute Selbsteinschätzung auch daran scheitern.

> [... ] they (the players) might pick the easier option even though they could handle more challenge, and rob themselves of the best, and designer-intended experience [@Brown2016]

Eine andere Möglichkeit zum balancen ist die Verwendung von dynamischen Schwierigkeitsgraden. Im Spiel *Resident Evil 4* werden mehr oder weniger Gegner platziert, abhängig davon ob der Spieler bisher besonders gut durch die Level gekommen ist oder oft gestorben ist und viel daneben schießt. [@Brown2015] Im Spiel Half-Life werden mehr Medikits platziert, wenn der Gegner wenig Lebenspunkte hat. [@Brown2016a] 

Es sollte beachtet werden, dass Spieler mit voranschreiten immer besser werden, daher sollten frühe Level deutlich einfacher sein als Level gegen Ende des Spiels. Dabei muss die Schwierigkeit der Level nicht linear steigen, es bietet sich an nach einem besonders schweren Level ein einfacheres Level einzubauen, um den Spieler Zeit zum Aufatmen zu lassen.

**Regel 3: Gute Level sind gut gebalanced.** 

### Risk and Reward

Risk and Reward beschreibt Momente im Spiel, bei den der Spieler eine besonders schwere Herausforderung lösen muss, dafür aber auch entsprechend belohnt wird. Diese stets optionalen Momente sollten in regelmäßigen Abständen auftauchen, um den Spieler vor eine interessante Entscheidung zu stellen, ob sie die Herausforderung annehmen oder lieber ablehnen möchten.

Vor allem bei Rouge Like Spielen sollte es ständig zu solchen Momenten kommen, da diese hier einen erhöhten Nervenkitzel bieten. Jede Herausforderung könnte den Tod bedeuten und damit den permanenten Verlust des Fortschrittes. Die Frage, ob sich der Kampf wirklich lohnt und ob man das Risiko eingehen sollte, fällt hier umso schwerer. 

**Regel 4: Gute Level haben Risk and Reward Momente.** 

### Pacing

Der Begriff Pacing entstammt der Filmbranche und beschreibt die Spannungskurve des Films. Aus dem Film lassen sich drei Regeln für gutes Pacing ableiten, welche auch für Videospiele Anwendung finden können. 

1. Pacing verläuft nicht linear, nach Hochpunkten sollte ein Tiefpunkt folgen 
2. Zu Beginn sollte viel Spannung erzeugt werden, um den Zuschauer bei Stange zu halten 
3. Kurz vor Ende sollte der spannendste Moment sein, der sich am Ende entlädt

Auch Videospiele sollten darauf achten ihre Spannungskurve zu kontrollieren. Gutes Pacing sorgt dafür, dass ein Spiel nicht langweilig oder repetitiv wird.  [@Brown2018]

\ref{zelda3} zeigt den Wüstenpalast aus dem Spiel *Zelda: A Link to the past*, wobei jeder Knoten ein eigener Raum ist. Um das Level zu bestehen, muss der Spieler den Boss im letzten Raum besiegen. Um zum Boss zu gelangen benötigt der Spieler das Item aus der großen Truhe die sich im Raum 'BigChest' befindet, um diese Truhe zu öffnen braucht er den großen Schlüssel aus dem 'BigKey' Raum. Das Level beginnt am Eingang. Das Level startet mit einem kurzen linearen Abschnitt (Knoten A und A1) um die Neugier des Spielers zu wecken. Danach folgt ein größeres Areal, in dem der Spieler sich frei bewegen und den Palast erkunden kann. (Knoten die mit B oder C gelabelt sind). Während dieser Erkundungsphase ist der Spannungsstechnische Tiefpunkt dieses Levels. Wenn der Spieler das Item aus der Truhe genommen hat, uns sich auf dem Weg zum Boss macht, muss er durch den Raum D gehen. Von nun an gibt es keine Abzweigungen mehr, die Räume sind kleiner und die Spannung nährt sich ihrem Höhepunkt an, bis sie schlussendlich beim Bosskampf ihren Peak erreicht und nach dem bezwingen des Bosses abfällt. 


![Wüstenpalast aus Zelda 3 in Graphendarstellung \label{zelda3}](figs/chapter2/Zelda3Dungeon.png){width=100%}

**Regel 5: Gute Level steuern das Pacing des Spiels.** 

### Einzigartigkeit

Die einzelnen Level sollten sich stark voneinander unterscheiden. Auch wenn das überliegende Designkonzept über den gesamten Spielverlauf konstant sein sollte.

> [...] people don’t like playing the same level twice.[@Ryan1999]

Variation in Gegner, Texturen und Strukturen helfen dabei die Abwechslung im Level zu gewährleisten und keine Langeweile aufkommen zu lassen. Wenn es längere Nebenpfade gibt, sollten es vermieden werden den Spieler dazu zu zwingen den gesamten Pfad zurückzulaufen (Backtracking), stattdessen sollte das Ende des Nebenpfades eine Abkürzung zurück zum Hauptpfad bieten. 

**Regel 6: Gute Level sind einzigartig.** 

### Effizienz 

Spieleentwicklung ist ein kostspieliges Unterfangen und bereits kleinere Produktionen können mehrere Millionen Dollar kosten. [@DevPlay2017] Daher ist die effiziente Nutzung von Ressourcen unabdingbar. Gute Level-Designer erstellen ein Set aus Modulen, verschiedenen Assets und Events. Diese Module können dann miteinander kombiniert und bei Bedarf angepasst werden. Aus einer Handvoll solcher Module lassen sich bereits viele verschiedene Level und Situationen erzeugen. 

**Regel 7: Gute Level sind effizient in der Herstellung.** 


## Prozedurale Generierung

- Begrifferläuterung
- Anwendungsfälle und Beispiele
- Anwendung zur Level generierung
- Beispiele welche NICHT im Kapitel Analyse verwendet werden, daher auch kurz und knall (z.B Random Walk)
- Granzen und Probleme
- Vor und Nachteile



## Bewertungsschema

Um im weiteren Verlauf der Arbeit die verschiedenen Algorithmen zur prozeduralen Generierung bewerten und miteinander vergleichen zu können, wird anhand der vorgestellten Regeln ein Bewertungsschema erstellt. Für jede Regel werden verschiedene Kriterien aufgestellt, die angeben, ob und inwiefern diese erfüllt sind. Die Kriterien werden so gewählt, dass sie auf das Anwendungsszenario optimiert sind. Die Tabelle \ref{bkt} listet die Kriterien auf. Für jedes erfüllte Kriterium wird ein Punkt verteilt, die Summe der Punkte gibt die Güte des Algorithmus im Vergleich zu den anderen Algorithmen an (vgl. \ref{bkfk). 



| Regel                   | Kriterium                                           | Anmerkung                                                    |
| ----------------------- | --------------------------------------------------- | ------------------------------------------------------------ |
| Gute Level sind lösbar. | Sind alle Orte und Gegenstände im Level erreichbar? | Dies ist eine kritische Anforderung. Level, die dieses Kriterium nicht erfüllen, sind inakzeptable und mit null Punkten zu bewerten. |
| Gute Level fordern die Mechaniken des Spiels. | Sind die Level für die grundlegenden Spielmechaniken der Anwendungsumgebung ausgelegt? |Monster und Items müssen im Dungeon platzierbar sein.|
|  | Sind die Level für weitere Mechaniken ausgelegt? |Können Türen, Hebel, Schlüssel, Schlösser, Rätsel, Shops o.ä platziert werden?|
|  | Können die Level manipuliert werden? |Kann die Struktur des Levels im Spiel manipuliert werden, zum Beispiel durch Bomben?|
| Gute Level sind gut gebalanced | Kann das Balancing vom Entwickler angepasst werden? |Kann der Entwickler im laufenden Spiel bestimmen wie schwer das Level sein soll?|
| Gute Level haben Risk and Reward Momente | Sind Nebenpfade möglich? |Erzeugt der Generator Level, die Wege abseits des kritischen Pfades haben?|
|  | Können Items und Monster gezielt platziert werden? |Nebenpfade und kritische Pfade müssen für den Entwickler unterscheidbar sein.|
| Gute Level steuern das Pacing des Spiels | Erzeugt der Algorithmus Level mit abwechslungsreichen Pacing? |Haben die Level unterschiedliche Strukturen, die das Pacing beeinflusse (vgl. \ref{zelda3})?|
|  | Kann das Pacing kontrolliert werden? |Kann der Entwickler gezielt bestimmte Strukturen konfigurieren?|
| Gute Level sind einzigartig | Unterscheiden die Level sich ausreichend im Aufbau? ||
| | Unterscheiden die Level sich ausreichend im Aussehen? ||
|  | Kann Backtracking vermieden werden? ||
| Gute Level sind effizient in der Herstellung. | Kommt der Algorithmus ohne Input Daten aus? |Input Daten sind in diesem Sinne Graphen oder Layouts für Räume.|
| | Können verschiedene einzigartige Level aus denselben Input Daten generiert werden? |Wenn keine Input-Daten benötigt werden, 1 Punkt.|

: Tabelle mit den Bewertungskriterien für prozedurale Algorithmen. \label{bkt}




$$
\label{bkfk}
\text{Die Güte G eines Level lässt sich durch die Summe aller vergebenen Punkte}\\
\text{P$\backslash$ L multipliziert mit dem Bewertungspunkt Lösbarkeit L der Bewertungskriterien berechnen.}\\
G = L * ( \sum P_{i})
$$



## Analyse der Ausgangssituation 

Aktuell besitzt das PM-Dungeon keinen eigenen Level-Generator und die zur Verfügung gestellten Level sind in Anzahl und Abwechslung stark begrenzt. Die Level greifen auf einen gemeinsamen Texturenpool zu und unterscheiden sich daher optisch kaum voneinander. Auch bieten die generierten Level keine Schnittstelle, um die Struktur des Levels dynamisch im Code abzufragen. Die Studenten haben daher keine Möglichkeit ihre implementieren Inhalte strategisch im Level zu platzieren oder die Struktur der Level zu bestimmen. Inhalte wie Monster und Items wurden daher von den Studenten zufällig im Level verteilt. Dadurch das die Level nicht neu generiert werden, ist das PM-Dungeon streng genommen auch kein Rouge-Like.

![Ausschnitt aus dem PM-Dungeon in Graphendarstellung \label{pmdAsGraph}](figs/chapter2/PMDExampleLevel1.png){width=40%}

Abbildung \ref{pmdAsGraph} zeigt das Level aus Abbidlung \ref{pmd} in Graphendarstellung. 

<span style="color:red"> **ToDo Matrix mit Anforderungen an Level für das PM-Dungeon und ob diese Erfüllt sind. PM-Dungeon level als Graph. Erläutern der Probleme** </span>

Zwar genügen die bereitgestellten Level zum Erfüllen des Lernzieles, jedoch könnte ein eigener Generator die Studenten weiter motivieren ihr Spiel im inhaltlichen Teil auszubauen. Am Ende des Semesters würde so ein eigenes fertiges Spiel entstehen und nicht nur ein Prototyp eines Spiels. 





