# Realisierung

<!--
*   Beschreibung der Umsetzung des Lösungskonzepts
*   Darstellung der aufgetretenen Probleme sowie deren Lösung bzw. daraus resultierende Einschränkungen des Ergebnisses (falls keine Lösung)
*   Auswertung und Interpretation der Ergebnisse
*   Vergleich mit der ursprünglichen Zielsetzung (ausführlich): Was wurde erreicht, was nicht (und warum)? (inkl. Begründung/Nachweis)

geschätzer umfang ca 20%
-->

## Projekt Konfiguration
- Build Script und targets
- github workflows/settings
- github actions
- package struktur

## Umsetzung GraphG

- Interessante Codezeilen

  - can be connected?

  - Alles wo ein Fehlergeworfen wird weil die generierung so nicht geht? 

    

- Problem: durch die zufällige auswahl von knoten zum erzeugen der extra verbindungen kann es theoretisch ewig dauern bis eine lösung gefunden wurde. Außerdem kann es sein, dass für die angeforderten Parameter garkeine Lösung existiert und der algorithmus endlos versucht eine Lösung zu finden die es nicht gibt (algorithmus traurig ;( ) 
- Lösung: Um unmögliche versuche zu reduzieren kann die Formel e<=3v-6 verwendet werden. Damit werden zumindest die ganz klar unlösbaren Parameter ignoriert (CantBePlanarException). Um endlos lange durchlaufe zu vermeiden kann eine Abbruchbedingung gesetzt werden die nach n Loops das ende einleitet (noSolutionFoundException). 
- Problem: Wie viele loops sind angebracht? Ab wann soll abgebrochen/neugestartet werden.
- Lösung: Messungen durchführen. Kombination aus Knoten und ExtraEdges in einer schleife durchlaufen lassen, n Versuche mit sehr vielen Loops durchführen und folgendes speichern
  - Parameter Knoten anzahl und Kanten anzahk
  - Anzahl der Versuche
  - Gefundene Lösungen
  - Gescheiterte Durchläufe
  - Geringste anzahl an loops die für eine Lösung gebraucht wurden
  - Maximale anzahl an loops die für eine Lösung gebraucht wurden
  - Durchschnittliche anzahl an loops die für eine Lösung gebraucht werden
- Daraus lässt sich dann eine Liste erstellen, die beim erzeugen eines Graphen eingelesen wird und die "optimale" loop anzahl einstellt. Kombinationen für die keine Lösung gefunden wurde können dann direkt am anfang mit `NoSoulutionFoundException` unterbunden werden.
- Darstellen der Messungen als Graphen mit guter beschreibung (nochmal rückfrage mit cagi und bc führen da die ba bei der auswertung der graphen schwächen hatte)

- beispielgraphen zeigen (ohne bewertung)

## Umsetzung RoomG

## Umsetzung LevelG

## Anbindung an das PM-Dungeon

## Implementieren der Schnittstellen

# Evaluierung 

- Zeigen von Ergebnissen

- Auswerten der Ergebnisse

  
  
  
  
  
  
  