# Zusammenfassung

In diesem Kapitel wird ein abschließendes Fazit über den Generator und die Arbeit im Allgemeinen gezogen. Abschließend wird ein Ausblick auf die Weiterentwicklung von DungeonG gegeben. 

## Fazit

Ziel dieser Arbeit war die Konzeptionierung und Implementierung eines prozeduralen graphenbasierten 2D-Level Generator für das PM-Dungeon-Framework. Um dieses Ziel zu erreichen, wurde zu Beginn der Arbeit das Konzept der graphenbasierten-Darstellung von Level sowie die Bedeutung der Begrifflichkeit der prozeduralen Generierung vorgestellt. Um die Qualität des Generators und der vom Generator erzeugten Level zu bewerten wurden Regeln für gutes Level-Design aufgestellt. Die generierten Level wurden dann anhand dieser Kriterien bewertet.

DungeonG ist ein voll funktionsfähiger prozeduraler graphenbasierter Generator und erzeugt abwechslungsreiche und spaßige Level und arbeitet dabei mit wenigen Inputdaten sowie Parameter. DungeonG erzeugt selbständig planare Graphen, um die Level-Struktur festzulegen. Inspiriert durch das Spiel Spelunky werden zur Verfügung gestellte Templates genutzt und miteinander kombiniert, um verschied aussehende und strukturierte Räume zu erzeugen. Durch einen inkrementellen prozeduralen Prozess werden die Graphen mithilfe der Räume zu spielbaren Level umgeformt. Die so erzeugten Level unterscheiden sich stark voneinander, können zusätzlich verschiedene Texturen benutzten und können daher als einzigartig bezeichnet werden. 

DungeonG konnte und wurde bereits problemlos in das Framework integriert werden und bietet verschiedene Schnittstellen, um mit dem Level zu arbeiten. So kann die Struktur der Level gezielt nach optionalen oder kritischen Pfaden untersucht werden und Inhalte wie Monster und Schätze entsprechend platziert werden. Die Schnittstellen sind speziell für Programmiere optimiert und erfordern keine tiefen Kenntnisse über Designregeln für Videospiele. 

DungeonG erfüllt alle an ihn gestellte Anforderungen. Mithilfe des A* Algorithmus kann sichergestellt werden, dass alle erzeugten Level lösbar sind. Die Studierenden können eigene Spielinhalte entwickeln und im Level verteilen, so können die Level die verschiedenen Mechaniken des Spieles nutzen und fordern. Mithilfe der Schnittstelle können Monster und Items so platziert werden, dass ein gute gebalancetes Spiel entsteht, welches dennoch spannende Risk and Reward Momente liefert. Das Erzeugen der Graphen kann mithilfe von Parametern beeinflusst werden, um so eine gute Level-Struktur mit gutem Pacing zu erzeugen. Der Generator benötigt nur eine Liste an Layouts für Räume und Replacements, ansonsten agiert er ohne Daten von außen. Das erlaubt es dem Generator effizient eine Vielzahl an einzigartigen Level herzustellen. 

Der in dieser Arbeit konzeptioniert und entwickelte Level-Generator wurde in das PM-Dungeon-Framework integriert, um die Studierenden mit mehr Abwechslung im Spiel zu motivieren und somit den Lernerfolg zu steigern. Ebenso hat diese Arbeit gezeigt, dass ein gut konzeptionierter Level-Generator auch mit wenigen Parametern und Inputdaten in der Lage ist, spaßige und einzigartige Level für 2D-Rollenspiele zu erschaffen. Der Generator ist zwar speziell an das PM-Dungeon-Framework angepasst, die zugrundeliegenden Algorithmen und Verfahren können aber auf andere Spiele übertragen werden. 

Das Ziel der Arbeit wurde vollständig erreicht. 

## Ausblick

DungeonG erfüllt zwar die Anforderungen an einen Level-Generator für das PM-Dungeon und erzeugt spaßige und abwechslungsreiche Level, bietet aber durchaus Potenzial für Erweiterungen und Verbesserungen. In diesen Abschnitt wird eine Auswahl an Ansatzpunkten für die Weiterentwicklung und Optimierung vorgestellt. 

### GraphG

Die von GraphG erzeugten Graphen hängen von der Parameterkombination aus Knoten- und Kantenanzahl ab. Daher gibt es Kombinationen, die schlechter oder besser als andere Kombinationen sind, um gute Level-Graphen zu erzeugen. Um die besten Konfigurationen herauszufinden, können Umfragen bei den Studierenden durchgeführt werden. Bei diesen Umfragen werden den Teilnehmer verschiedene Level mit verschiedener Konfiguration von GraphG präsentiert, die Teilnehmer sollen dann die Level nach ihrem Eindruck von Gut bis Schlecht sortieren. Aus den Ergebnissen der Umfrage könnten sich Anhaltspunkte für gute Konfigurationen ableiten lassen. Es sollte beachtet werden, dass Umfragen von äußeren Faktoren und von der Anzahl der Teilnehmer stark beeinflusst werden können. 

Mithilfe von Graphen Analyse können verschiedene Schwachstellen im Graphen identifiziert und behoben werden. So kann der Graph nach langen Pfaden untersucht werden, welche ohne Backtracking nicht zurück auf den Hauptpfad führen. Solche Pfade können dann gekürzt oder mithilfe von neuen Kanten an den Hauptpfad angeschlossen werden. So müssen Spieler nicht mehrfach durch dieselben, bereits erkundeten Räume laufen. 

Wie im Kapitel 5 erläutert, benötigt GraphG eine zu große Menge an Speicher, um alle möglichen gültigen Graphen abzuspeichern. Dies kann behoben werden, indem zueinander isomorphe Graphen nicht mehr als einzelne Lösungne betrachtet werden, sondern eine einzige Lösung darstellen. Da das Untersuchen von Graphen auf Isomorphie NP-Vollständig ist, wird eine große Menge an Rechenleistung benötigt. Diese Rechenleistung könnten von einer Serverfarm bereitgestellt werden. Die Server würden alle gültigen Lösungen finden, dabei isomorphe Graphen zusammenfassen, und die Lösungen als Json speichern. Diese Json kann an alle Nutzer von DungeonG ausgeliefert werden. Die Berechnung von Lösungen einer Konfiguration muss nur einmal durchgeführt werden, eine dauerhaft in Betrieb laufende Serverfarm ist daher nicht notwendig.  

### RoomG

RoomG profitiert davon, mehr Raum-Layouts sowie Replacement-Layout zu besitzen. Um das Erstellen von Layouts zu vereinfachen, könnte ein Editor entwickelt werden. Dieser Editor könnte grafische Elemente besitzen, um Layouts mittels Mausklicks zu erstellen und in das Dungeon zu laden. 

Da RoomG als einziger Baustein in DungeonG nicht ohne Inputdaten von außen auskommt, würde es die Effizienz des Generators stark verbessern, wenn RoomG prozedural generierte Layouts verwenden würde. RoomG könnte auch vollständig durch einen prozeduralen Raum-Generator ersetzt werden. Ebenso können auch Texturen prozedural generiert werden, um die optische Abwechslung noch weiter zu steigern.[@Dong2020]

### Schnittstelle

Die Qualität der Schnittstelle zwischen DungeonG und Studierenden lässt sich nur schwer im Vorhinein bewerten. Daher sollte im ersten aktiven Einsatz des Generators im Modul Programmier-Methoden insbesondere auf auftretende Probleme und Vorschläge zur Verbesserung geachtet werden. 

Eine eigene, speziell für DungeonG und das PM-Dungeon-Framework entwickelte, Beschreibungssprache könnte eine Vielzahl an neuen Möglichkeiten im Umgang mit dem Level und dem Framework liefern. Es wäre vorstellbar, eine Dot ähnliche Sprache zu verwenden, mit der die Studierenden eigene Graphen erstellen, den Knoten Raum-Layouts sowie Designs zuordnen und über die Sprache Monster und Items erstellen und im Level platzieren. 