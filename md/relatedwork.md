# Grundlagen

<!--

*   Was ist Prozedurale Generierung
*   Was ist das PM Dungeon
*   Was ist gutes Level Design
*   Warum sind Graphen gut zur Analyse 

geschätzter Umfang 15% - 20%
-->

## PM-Dungeon

Um ein Verständnis für das PM-Dungeon zu bekommen, erklärt dieses Kapitel das grundlegende Spielkonzept des Genres der Rogue-Like Rollenspiele sowie den Aufbau der Lehrveranstaltung und des PM-Dungeon-Frameworks. 

### Das Genre der Rogue Likes 

*Rogue: Exploring the Dungeons of Doom*, ist ein in den 1980er entwickeltes Dungeoncrawler-Rollenspiel. Dungeoncrawler sind Spiele, indem sich der Spieler durch ein Labyrinth gefüllt mit Gegnern und Rätseln kämpft und meist über mehrere Ebenen versucht das Spielziel (z .B. den Ausgang oder einen Schatz) zu erreichen.[@qrpg2020] In Rogue bewegt sich der Spieler rundenbasiert durch ein, durch ASCII-Zeichen dargestelltes, Levelsystem und versucht sich mithilfe von Gegenständen und Zaubersprüchen bis in die tiefste Ebene des Dungeons vorzukämpfen um dort das Amulett von Yendor zu erlangen.[@MattBarton2009]

Dieses Spielkonzept ist zu dieser Zeit kein unbekanntes, was Rogue hingegen bis heute als eine der relevantesten Videospiele der Geschichte herausstellt, ist die prozedurale Generierung der Level. 

> But I think Rogue’s biggest contribution, and one that still stands out to this day, is that the computer itself generated the adventure in Rogue. Every time you played, you got a new adventure. That’s really what made it so popular for all those years in the early eighties.[@Wichman1997]

**Frage: Macht es Sinn für relevante Persönlichkeiten eine Art Bio in den Anhang zu packen?** 

Bei jedem Neustart von Rogue werden die Level neu generiert. Das bedeutet der Aufbau der Level, die Anzahl und Positionierung von Monstern und Items unterscheiden sich mit jedem Spieldurchlauf. Man spielt also niemals zweimal dieselbe Partie von Rogue. Zusätzlich setzt Rogue auf dem sogenannten Permadeath. Stirbt der Spieler im Dungeon, verliert er all seinen Fortschritt und muss das Spiel von vorne beginnen, mit neu generiertem Level. Rogue zeichnet sich also vor allem dadurch aus, dass jeder Spieldurchlauf anders als der andere ist und dadurch ein besonders hohes Maß an Abwechslung und damit Wiederspielwert gegeben war.  

Rogue konnte sich schnell an einiger Beliebtheit erfreuen und es dauert nicht lange bis andere Entwickler ähnliche Spiele mit prozedural generierten Inhalten veröffentlichten. [@MattBarton2009] Es entwickelte sich das Genre der Rogue-Likes.

> [... ] Roguelikes are called Roguelikes, because the games are literally like Rogue [... ] [@Brown2017]

In den Jahren haben viele Entwickler versucht Regeln für das Genre aufzustellen, also Bedingungen, die ein Spiel erfüllen muss, um sich als Rogue-Like bezeichnen zu dürfen. 2008 wurde auf der Internationalen Rogue-Like Entwickler Konferenz eine Liste mit verschiedenen Faktoren veröffentlicht. Diese Liste ist als Berliner Interpretation bekannt. [@Conference2008] Über die Jahre wurde die Interpretation harsch kritisiert und sogar als *downright nonsense* bezeichnet. [@Grey2013] Die Berliner Interpretation schade der kreativen Freiheit.

Heute muss ein Spiel nur zwei wichtige Features implementieren um *like* Rogue zu sein, prozedural generierte Level und Permadeath. [@Brown2019] Das Genre ist daher nicht nur noch auf Rollenspiele in Labyrinthen begrenzt, sondern umfasst mittlerweile Spiele aus den unterschiedlichsten Genres wie Plattformer, Shooter oder Action-Adventures und vielen mehr. [@Wikipedia2020]

### PM-Dungeon

- Was ist das PM-Dungeon
- Welches Ziel soll das PM-Dungeon erfüllen
- Was ist das PM-Dungeon-Framework?
- Welche Probleme hat das Framework aktuell noch bezogen auf Level

## Prozedurale Generierung
- Begrifferläuterung
- Anwendungsfälle und Beispiele
- Anwendung zur Level generierung
- Beispiele welche NICHT im Kapitel Analyse verwendet werden, daher auch kurz und knall (z.B Random Walk)
- Granzen und Probleme
- Vor und Nachteile

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





