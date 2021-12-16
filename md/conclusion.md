# Zusammenfassung

<!--
*   Fazit: Vergleich mit der ursprünglichen Zielsetzung (komprimiert/zusammengefasst)
*   Ausblick: Darstellung ungelöster Probleme und weiterer relevanter Ideen

Umfang: typisch ca. 5% ... 10% der Arbeit
-->

## Fazit

- Wurde das Ziel erreicht? 
  - Vergleich mit der ursprünglichen Zielsetzung
  - Was kann ich jetzt genau mit dem Algo machen?
- Sachen die gut gelaufen sind
  - Warum sind die gut?
- Sachen die schlecht gelaufen sind
  - Warum sind die schlecht gelaufen?
- Welchen Beitrag hat diese Arbeit für das "Forschungsfeld" prozedurale Generierung geboten?

## Ausblick

DungeonG erfüllt zwar die Anforderungen an einen Level-Generator für das PM-Dungeon und erzeugt spaßige und abwechslungsreiche Level, bietet aber durchaus potenzial für Erweiterungen und Verbesserungen. In diesen Abschnitt wird eine Auswahl an Ansatzpunkten für die Weiterentwicklung und Optimierung vorgestellt

Die von GraphG erzeugten Graphen hängen von der Parameterkombination aus Knoten- und Kantenanzahl ab. Daher gibt es Kombinationen die schlechter oder besser als andere Kombinationen sind um gute Level-Graphen zu erzeugen. Um die besten Konfigurationen herauszufinden können Umfragen bei den Studierenden durchgeführt werden. Bei diesen Umfragen werden den Teilnehmer verschiedene Level mit verschiedener Konfiguration von GraphG präsentiert, die Teilnehmer sollen dann die Level nach ihren Eindruck von Gut bis Schlecht sortieren. Aus den Ergebnissen der Umfrage könnten sich Anhaltspunkte für gute Konfigurationen ableiten lassen. Es sollte beachtet werden, dass Umfragen von äußeren Faktoren und von der Anzahl der Teilnehmer stark beeinflusst werden können. 

Mithilfe von Graphen Analyse können verschiedene Schwachstellen im Graphen identifiziert und behoben werden. So kann der Graph nach langen Pfaden untersucht werden, welche ohne Backtracking nicht zurück auf den Hauptpfad führen. Solche Pfade können dann gekürzt oder mithilfe von neuen Kanten an den Hauptpfad angeschlossen werden. So müssen Spieler nicht mehrfach durch die selben, bereits erkundeten Räume laufen. 

Wie im Kapitel **TODO** erläutert, benötigt GraphG eine zu große Menge an Speicher um alle Möglichen gültigen Graphen abzuspeichern. Dies kann behoben werden, indem zueinander isomorphe Graphen nicht mehr als einzel Kösungne betrachtet werden sondern eine eingige Lösung darstellen. Da das Untersuchen von Graphen auf Isomorphie NP-vollständig ist, wird eine große Menge an Rechenleistung benötigt. Diese Rechenleistung könnten von einer Serverfarm bereitgestellt werden. Die Server würden alle gültigen Lösungen finden, dabei isomorphe Graphen zusammenfassen, und die Lösungen als json speichern. Diese json kann an alle Nutzer von DungeonG ausgeliefert werden. Die berechnung von Lösungen einer Konfiguration muss nur einmal durchgeführt werden, eine dauerhaft in betrieb laufende Serverfarm ist daher nicht notwendig. 

**TODO Überleitung**

RoomG profitiert davon, mehr Raum-Layouts sowie Replacment-Layout zu besitzen. Um das Erstellen von Layouts zu vereinfachen, könnte ein Editor entwickelt werden. Dieser Editor könnte Graphische Elemente besitzen um Layouts mittels Mausklicks zu erstellen und in das Dungeon zu laden. 

Da RoomG als einziger Baustein in DungeonG nicht ohne Inputdaten von außen auskommt, würde es die effizienz des Generators stark verbessern, wenn RoomG prozedural Generierte Layouts verwenden würde. RoomG könnte auch vollständig durch einen prozeduralen Raum-Generator ersetzt werden. Ebenso können auch Texturen prozedural generiert werden um die optische Abwechlsung noch weiter zu steigern. **TODO QUELLE** 

**TODO Überleitung**

Die qualtität der Schnittselle zwischen DungeonG und Studierenden lässt sich nur schwer im vorhinein bewerten. Daher sollte im ersten aktiven Einsatz des Generators im Modul Programmier-Methoden insbesondere auf auftretende Probleme und Vorschläge zur Verbesserung geachtet werden. 

Alle Änderungen am Generator die im laufenden Semester durchgeführt werden, sollten keine Inkompatibilität mit älteren Versionen haben. Große Änderungen sollten daher nur außerhalb des Semesters integriert werden.

**TODO Überleitung**

Eine eigene, speziell für DungeonG und das PM-Dungeon-Framwork entwickelte, Beschreibungssprache könnte eine Vielzahl an neuen Möglichkeiten im Umgang mit dem Level und dem Framework liefern. Es wäre Vorstellbar, eine Dot ähnliche Sprache zu verwenden, mit der die Studierenden eigene Graphen erstellen, den Knoten Raum-Layouts sowie Desings zuordnen und über die Sprache Monster und Items erstellen und im Level platzieren. 