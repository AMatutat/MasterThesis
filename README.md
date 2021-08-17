# MasterThesis


## Annahmen

- Die Eingabe wird nach Java interpretiert/kompiliert 
- Symboltabellen werden in folgender Reihenfolge aufgebaut
  1. Lokaler-Scope
  2. Klassen/Template-Scope
  3. Globaler-Scope
- Sollte ein Symbol nicht in der Tabelle enthalten sein wird davon ausgegangen, dass sie im Java-Scope liegt.   
- Sichtbarkeit und Zugriffskontrolle auf die Java Inhalte liegt in Verantwortung des Anwenders
- Jede Funktion mit Rückgabe gibt den letzten Wert im Funktionsbody zurück (Scala style) 

