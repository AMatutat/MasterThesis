# Grundlagen

<!--

*   Was ist Prozedurale Generierung
*   Was ist das PM Dungeon
*   Was ist gutes Level Design
*   Warum sind Graphen gut zur Analyse 

geschätzter Umfang 15% - 20%
-->

## Bewertungskriterien für gute Level

- Aufstellen von Bewertungskriterien 
- Fokusiert auf prozedurale Anwendungsfälle
- Fokusiert auf Rogue-Likes
- Fokusiert auf 2D-Spiele
- Kürzer als in der BA

## Graphen zur darstellung von Level

- Wie verwendet man Graphen zur darstellung von Level?
- Warum ist das gut?
- Welche Vortiele bietet das?
- Welche Nachteile und Grenzen gibt es?
- Warum mach ich das in dieser arbeit so?

## Prozedurale Generierung

- Begrifferläuterung

- Anwendungsfälle und Beispiele

- Anwendung zur Level generierung

- Beispiele welche NICHT im Kapitel Analyse verwendet werden, daher auch kurz und knall (z.B Random Walk)

- Granzen und Probleme

- Vor und Nachteile

  

## PM-Dungeon

Um ein Verständnis für das PM-Dungeon zu bekommen, erklärt dieses Kapitel das grundlegende Spielkonzept des Genres der Rogue-Like Rollenspiele sowie den Aufbau der Lehrveranstaltung und des PM-Dungeon-Frameworks. 

### Das Genre der Rogue Likes 

*Rogue: Exploring the Dungeons of Doom*, ist ein in den 1980er entwickeltes Dungeoncrawler-Rollenspiel. Dungeoncrawler sind Spiele, indem sich der Spieler durch ein Labyrinth gefüllt mit Gegnern und Rätseln kämpft und meist über mehrere Ebenen versucht das Spielziel (z .B. den Ausgang oder einen Schatz) zu erreichen.[@qrpg2020] In Rogue bewegt sich der Spieler rundenbasiert durch ein, durch ASCII-Zeichen dargestelltes, Levelsystem und versucht sich mithilfe von Gegenständen und Zaubersprüchen bis in die tiefste Ebene des Dungeons vorzukämpfen um dort das Amulett von Yendor zu erlangen.[@MattBarton2009]

Dieses Spielkonzept ist zu dieser Zeit kein unbekanntes, was Rogue hingegen bis heute als eine der relevantesten Videospiele der Geschichte herausstellt, ist die prozedurale Generierung der Level. 

> But I think Rogue’s biggest contribution, and one that still stands out to this day, is that the computer itself generated the adventure in Rogue. Every time you played, you got a new adventure. That’s really what made it so popular for all those years in the early eighties.[@Wichman1997]

 <span style="color:red">  **Frage: Macht es Sinn für relevante Persönlichkeiten eine Art Bio in den Anhang zu packen?** </span>

Bei jedem Neustart von Rogue werden die Level neu generiert. Das bedeutet der Aufbau der Level, die Anzahl und Positionierung von Monstern und Items unterscheiden sich mit jedem Spieldurchlauf. Man spielt also niemals zweimal dieselbe Partie von Rogue. Zusätzlich setzt Rogue auf dem sogenannten Permadeath. Stirbt der Spieler im Dungeon, verliert er all seinen Fortschritt und muss das Spiel von vorne beginnen, mit neu generiertem Level. Rogue zeichnet sich also vor allem dadurch aus, dass jeder Spieldurchlauf anders als der andere ist und dadurch ein besonders hohes Maß an Abwechslung und damit Wiederspielwert gegeben war.  

Rogue konnte sich schnell an einiger Beliebtheit erfreuen und es dauert nicht lange bis andere Entwickler ähnliche Spiele mit prozedural generierten Inhalten veröffentlichten. [@MattBarton2009] Es entwickelte sich das Genre der Rogue-Likes.

> [... ] Roguelikes are called Roguelikes, because the games are literally like Rogue [... ] [@Brown2017]

In den Jahren haben viele Entwickler versucht Regeln für das Genre aufzustellen, also Bedingungen, die ein Spiel erfüllen muss, um sich als Rogue-Like bezeichnen zu dürfen. 2008 wurde auf der Internationalen Rogue-Like Entwickler Konferenz eine Liste mit verschiedenen Faktoren veröffentlicht. Diese Liste ist als Berliner Interpretation bekannt. [@Conference2008] Über die Jahre wurde die Interpretation harsch kritisiert und sogar als *downright nonsense* bezeichnet. [@Grey2013] Die Berliner Interpretation schade der kreativen Freiheit.

Heute muss ein Spiel nur zwei wichtige Features implementieren um *like* Rogue zu sein, prozedural generierte Level und Permadeath. [@Brown2019] Das Genre ist daher nicht nur noch auf Rollenspiele in Labyrinthen begrenzt, sondern umfasst mittlerweile Spiele aus den unterschiedlichsten Genres wie Plattformer, Shooter oder Action-Adventures und vielen mehr. [@Wikipedia2020]

### PM-Dungeon

Im praktischen Anteil des Moduls Programmiermethoden sollen die Studenten das gelernte Wissen aus dem theoretischen Anteil anwenden und vertiefen, dafür bekommen sie in regelmäßigen Abständen Aufgaben gestellt. Um die Aufgaben in einen gemeinsamen Kontext zu bringen und zeitgleich die Motivation der Studenten zu steigern, wurde 2021 das PM-Dungeon eingeführt. Über den verlauf des Semesters entwickeln die Studenten ihr eigenes Rouge-Like Rollenspiel. Zwar stehen weiterhin die Lehrinhalte im Fokus, dennoch haben die Studenten viele Freiheiten um ihr Spiel nach ihren wünschen zu gestalten. Für die Entwicklung des Spiels bekommen die Studenten ein extra dafür entwickeltes Framework zur Verfügung gestellt, das PM-Dungeon-Framework. Das PM-Dungeon-Framework erweitert das libGDX-Framework \footnote{libGDX: https://libgdx.com} um vereinfachte Schnittstellen zur grafischen Darstellung. Die Studenten können sich daher rein auf die Implementierung der Spielfeatures konzentrieren.

![Ausschnitt aus dem PM-Dungeon \label{pmd}](figs/chapter2/pmd.png){width=100%}

Abbildung \ref{pmd} zeigt einen Ausschnitt aus dem Startlevel einer Beispiel Implementierung des PM-Dungeons. Die Spielfigur (grüner Kreis) muss mithilfe der Leiter (blauer Kreis) in die nächste Ebene gebracht werden. Auf den Weg dorthin kann der Spieler die Monster (roter Kreis) töten, um Erfahrungspunkte zu sammeln oder Items zu finden. Sowohl die Spielerposition als auch die Position der Monster und der Leiter werden zu Beginn des Levels zufällig bestimmt. 

Aktuell besitzt das PM-Dungeon keinen eigenen Level-Generator und die zur Verfügung gestellten Level sind in Anzahl und Abwechslung stark begrenzt. Die Level greifen auf einen gemeinsamen Texturenpool zu und unterscheiden sich daher optisch kaum voneinander. Auch bieten die generierten Level keine Schnittstelle, um die Struktur des Levels dynamisch im Code abzufragen. Die Studenten haben daher keine Möglichkeit ihre implementieren Inhalte strategisch im Level zu platzieren oder die Struktur der Level zu bestimmen. Inhalte wie Monster und Items wurden daher von den Studenten zufällig im Level verteilt. Dadurch das die Level nicht neu generiert werden, ist das PM-Dungeon streng genommen auch kein Rouge-Like.

<span style="color:red"> **ToDo Matrix mit Anforderungen und ob diese Erfüllt sind** </span>

Zwar genügen die bereitgestellten Level zum Erfüllen des Lernzieles, jedoch könnte ein eigener Generator die Studenten weiter motivieren ihr Spiel im inhaltlichen Teil auszubauen. Am Ende des Semesters würde so ein eigenes fertiges Spiel entstehen und nicht nur ein Prototyp eines Spiels. 





