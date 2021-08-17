# MasterThesis

## Zielsetzung 
- Die Sprache soll verwendet werden um den Aufbau des Dungeons zu beschreiben. Dies umfasst:
  - Struktur des Levels bestimmen
  - Objekte im Dungeon erstellen und verhalten bestimmen/kontrollieren
  - Subklassen erstellen (z.B spezifische Monsterklassen) 
- Sekundärziel: Die Sprache soll den Lehrnzielen von PM nicht im Wege stehen 

## Annahmen

- Die Eingabe wird nach Java interpretiert/kompiliert
- Die Sprache ist streng Objektorientiert. Alles ist ein Objekt. (?)
- Symboltabellen werden in folgender Reihenfolge aufgebaut
  1. Lokaler-Scope
  2. Klassen/Template-Scope
  3. Globaler-Scope
- Sollte ein Symbol nicht in der Tabelle enthalten sein wird davon ausgegangen, dass sie im Java-Scope liegt.   
- Sichtbarkeit und Zugriffskontrolle auf die Java Inhalte liegt in Verantwortung des Anwenders
- Jede Funktion mit Rückgabe gibt den letzten Wert im Funktionsbody zurück (Scala style) 
- Funktionen und Klassen können in andere Definitionen importiert werden


## Ideen

- Mit der Sprache selbst lässt sich ein eigener Levelgenerator/Graphgenerator schreiben (dann Edgar) 
