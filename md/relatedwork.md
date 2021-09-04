# Grundlagen

<!--

*   Was ist Prozedurale Generierung
*   Was ist das PM Dungeon
*   Was ist gutes Level Design
*   Warum sind Graphen gut zur Analyse 

geschätzter Umfang 15% - 20%
-->

<span style="color:red"> **ToDo Kapitel Einleitung** </span>

## Rouge und Rouge Like

*Rogue: Exploring the Dungeons of Doom*, ist ein in den 1980er entwickeltes Dungeoncrawler-Rollenspiel. Dungeoncrawler sind Spiele, indem sich der Spieler durch ein Labyrinth gefüllt mit Gegnern und Rätseln kämpft und meist über mehrere Ebenen versucht das Spielziel (z .B. den Ausgang oder einen Schatz) zu erreichen.[@qrpg2020] In Rogue bewegt sich der Spieler rundenbasiert durch ein, durch ASCII-Zeichen dargestelltes, Levelsystem und versucht sich mithilfe von Gegenständen und Zaubersprüchen bis in die tiefste Ebene des Dungeons vorzukämpfen um dort das Amulett von Yendor zu erlangen.[@MattBarton2009]

Dieses Spielkonzept ist zu dieser Zeit kein unbekanntes, was Rogue hingegen bis heute als eine der relevantesten Videospiele der Geschichte herausstellt, ist die prozedurale Generierung der Level. 

> But I think Rogue’s biggest contribution, and one that still stands out to this day, is that the computer itself generated the adventure in Rogue. Every time you played, you got a new adventure. That’s really what made it so popular for all those years in the early eighties.[@Wichman1997]

Bei jedem Neustart von Rogue werden die Level neu generiert. Das bedeutet der Aufbau der Level, die Anzahl und Positionierung von Monstern und Items unterscheiden sich mit jedem Spieldurchlauf. Man spielt also niemals zweimal dieselbe Partie von Rogue. Zusätzlich setzt Rogue auf dem sogenannten Permadeath. Stirbt der Spieler im Dungeon, verliert er all seinen Fortschritt und muss das Spiel von vorne beginnen, mit neu generiertem Level. Rogue zeichnet sich also vor allem dadurch aus, dass jeder Spieldurchlauf anders als der andere ist und dadurch ein besonders hohes Maß an Abwechslung und damit Wiederspielwert gegeben war.  

Rogue konnte sich schnell an einiger Beliebtheit erfreuen und es dauert nicht lange bis andere Entwickler ähnliche Spiele mit prozedural generierten Inhalten veröffentlichten. [@MattBarton2009] Es entwickelte sich das Genre der Rogue-Likes.

> [... ] Roguelikes are called Roguelikes, because the games are literally like Rogue [... ] [@Brown2017]

In den Jahren haben viele Entwickler versucht Regeln für das Genre aufzustellen, also Bedingungen, die ein Spiel erfüllen muss, um sich als Rogue-Like bezeichnen zu dürfen. 2008 wurde auf der Internationalen Rogue-Like Entwickler Konferenz eine Liste mit verschiedenen Faktoren veröffentlicht. Diese Liste ist als Berliner Interpretation bekannt. [@Conference2008] Über die Jahre wurde die Interpretation harsch kritisiert und sogar als *downright nonsense* bezeichnet. [@Grey2013] Die Berliner Interpretation schade der kreativen Freiheit.

Heute muss ein Spiel nur zwei wichtige Features implementieren um *like* Rogue zu sein, prozedural generierte Level und Permadeath. [@Brown2019] Das Genre ist daher nicht nur noch auf Rollenspiele in Labyrinthen begrenzt, sondern umfasst mittlerweile Spiele aus den unterschiedlichsten Genres wie Plattformer, Shooter oder Action-Adventures und vielen mehr. [@Wikipedia2020]


## Graphen zur Darstellung von Level

- Wie verwendet man Graphen zur darstellung von Level?
- Warum ist das gut?
- Welche Vortiele bietet das?
- Welche Nachteile und Grenzen gibt es?
- Warum mach ich das in dieser arbeit so?

## Bewertungskriterien für gute Level

Auch wenn es viel Diskussion darüber gibt, ob Videospiele Kunst sind oder nicht, muss man den kreativen Schaffensprozess eingestehen.[@Petzold2019] Daher lassen sich für Videospiele auch keine festen Regeln definieren, wie bestimmte Designelemente zu sein haben. In diesem Abschnitt werden Regeln für gutes Level Design präsentiert. Sie sollten weder als objektive Maßstäbe noch als verpflichtende Gesetzte betrachtet werden, sondern vielmehr als Leitpfaden. Die Liste erhebt keinen Anspruch auf Allgemeingültigkeit, Korrektheit oder Vollständigkeit Sie wurde gezielt für die in dieser Arbeit betrachteten Probleme zusammengestellt. Zwar beschäftigt sich die Wissenschaft sehr wohl mit Videospielen und deren Level, jedoch nicht auf die Ausarbeitung objektiver Bewertungskriterien, daher basieren die Regeln dieser Liste auf Aussagen verschiedener Persönlichkeiten der Videospielbranche. Dabei ist zu bedenken, dass diese Branche dazu neigt einen Personenkult, um einige Entwickler aufzubauen. Auch wenn sich bei der Aufarbeitung der Regeln bemüht wurde, Aspekte zu wählen, die von unterschiedlichen Entwicklern als relevant betrachtet werden, muss in Betracht gezogen werden, dass viele Entwickler sich von den bekannten Persönlichkeiten beeinflusst haben lassen.

Die Grundlagen der Regeln stammen aus Dan Taylors *Ten Principles of Good Level Design* [@Taylor2013] [@Taylor2018] und Tim Ryans *Beginning Level Design* [@Ryan1999] und wurden bei Bedarf um weitere Aspekte, Meinungen und Beispielen ergänzt. 



### Lösbarkeit und Fehlerfreiheit

Ein Level muss lösbar sein. Es darf keine Fehler geben, die das Voranschreiten verhindern. Es sollte zusätzlich darauf geachtet werden, dass Spieler nicht in Sackgassen geraten könne. Braucht der Spieler beispielsweise ein bestimmtes Item, um weiterzukommen sollte er das Item an diesem Punkt immer noch erreichen können. Bei Spielen die Lebensenergie verwenden, sollte das Level gelöst werden können ohne Lebensenergie zu verlieren, so bleiben Spieler, die im vorherigen Abschnitt viel Schaden bekommen haben, nicht stecken. Gibt es im Spiel optionale Wege oder Ziele müssen auch diese lösbar sein und dürfen nicht durch einen Fehler unlösbar werden. Bei optionalen Inhalten liegt es in der kreativen Entscheidungsfreiheit des Entwicklers, ob diese auch immer erreichbar sein müssen oder ob zum Beispiel ein optionales Item benötigt wird, welches nicht mehr erreichbar ist. Zusätzlich sollten die Level frei von Fehlern sein, die Spielern Möglichkeiten wie das ungewollte Überspringen von Abschnitten erlauben.

### Gameplay First

> Above all else, great level design is driven by interaction - the game’s mechanics. Game levels don’t just provide context for mechanics, they provide the very reality in which they exist.[@Taylor2013]

> Always remember that interactivity is what makes videogames different from any other form of entertainment: books have stories, movies have visuals, games have interaction. If your level design isn’t showcasing your game mechanics, your players might as well be watching a movie or reading a book. [@Taylor2013]



### Balancing

### Pacing
- Star Wars Kurve nochmal verwenden

- Zelda a Link to the past Wüstentempel kann also gutes Beispiel genutzt werden, erst Erkunden dann "Boss rush" zum ende
 
![Zelda 3 Dungeon](figs/chapter2/Zelda3Dungeon.png){width=100%}

### Risk and Reward

### Einzigartigkeit

### Effizienz 
- da kann man dann auch ne gute überleitung zu pcg machen


## Prozedurale Generierung

- Begrifferläuterung

- Anwendungsfälle und Beispiele

- Anwendung zur Level generierung

- Beispiele welche NICHT im Kapitel Analyse verwendet werden, daher auch kurz und knall (z.B Random Walk)

- Granzen und Probleme

- Vor und Nachteile

## Die Anwendungsumgebung  PM-Dungeon

Im praktischen Anteil des Moduls Programmiermethoden sollen die Studenten das gelernte Wissen aus dem theoretischen Anteil anwenden und vertiefen, dafür bekommen sie in regelmäßigen Abständen Aufgaben gestellt. Um die Aufgaben in einen gemeinsamen Kontext zu bringen und zeitgleich die Motivation der Studenten zu steigern, wurde 2021 das PM-Dungeon eingeführt. Über den verlauf des Semesters entwickeln die Studenten ihr eigenes Rouge-Like Rollenspiel. Zwar stehen weiterhin die Lehrinhalte im Fokus, dennoch haben die Studenten viele Freiheiten um ihr Spiel nach ihren wünschen zu gestalten. Sie konzeptionieren eigenständig das Verhalten von Monstern, implementieren Schatztruhen und Items sowie unterschiedliche Fähigkeiten die der Spieler im laufe des Spiels freischalten kann. Für die Entwicklung des Spiels bekommen die Studenten ein extra dafür entwickeltes Framework zur Verfügung gestellt, das PM-Dungeon-Framework. Das PM-Dungeon-Framework erweitert das libGDX-Framework \footnote{libGDX: https://libgdx.com} um vereinfachte Schnittstellen zur grafischen Darstellung. Die Studenten können sich daher rein auf die Implementierung der Spielfeatures konzentrieren.

![Ausschnitt aus dem PM-Dungeon \label{pmd}](figs/chapter2/pmd.png){width=100%}

Abbildung \ref{pmd} zeigt einen Ausschnitt aus dem Startlevel einer Beispiel Implementierung des PM-Dungeons. Die Spielfigur (grüner Kreis) muss mithilfe der Leiter (blauer Kreis) in die nächste Ebene gebracht werden. Auf den Weg dorthin kann der Spieler die Monster (roter Kreis) töten, um Erfahrungspunkte zu sammeln oder Items zu finden. Sowohl die Spielerposition als auch die Position der Monster und der Leiter werden zu Beginn des Levels zufällig bestimmt. 

Aktuell besitzt das PM-Dungeon keinen eigenen Level-Generator und die zur Verfügung gestellten Level sind in Anzahl und Abwechslung stark begrenzt. Die Level greifen auf einen gemeinsamen Texturenpool zu und unterscheiden sich daher optisch kaum voneinander. Auch bieten die generierten Level keine Schnittstelle, um die Struktur des Levels dynamisch im Code abzufragen. Die Studenten haben daher keine Möglichkeit ihre implementieren Inhalte strategisch im Level zu platzieren oder die Struktur der Level zu bestimmen. Inhalte wie Monster und Items wurden daher von den Studenten zufällig im Level verteilt. Dadurch das die Level nicht neu generiert werden, ist das PM-Dungeon streng genommen auch kein Rouge-Like.

![Ausschnitt aus dem PM-Dungeon in Graphendarstellung \label{pmdAsGraph}](figs/chapter2/PMDExampleLevel1.png){width=100%}

Abbildung \ref{pmdAsGraph} zeigt das Level aus Abbidlung \ref{pmd} in Graphendarstellung. 

<span style="color:red"> **ToDo Matrix mit Anforderungen an Level für das PM-Dungeon und ob diese Erfüllt sind. PM-Dungeon level als Graph. Erläutern der Probleme** </span>







Zwar genügen die bereitgestellten Level zum Erfüllen des Lernzieles, jedoch könnte ein eigener Generator die Studenten weiter motivieren ihr Spiel im inhaltlichen Teil auszubauen. Am Ende des Semesters würde so ein eigenes fertiges Spiel entstehen und nicht nur ein Prototyp eines Spiels. 





