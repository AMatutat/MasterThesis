# MasterThesis

*Anmerkung: Zielsetzung A und B sind getrennt voneinander zu betrachten.* 

# Idee: Levelgenerator
- Weiterführung der Ansätze aus der BA. Analysieren von vorhandenen Algorithmen zur prozeduralen Generierung und aus den gewonnenen Kenntnissen einen eigenen Generator schreiben.
- Evtl mit Beschreibungssprache

# Idee: Gameplayfeatures
- Erweitern des Spiels um verschiedene Gameplayfeatures und Baukastenelemente.

# Idee: Logik
- Kann man was mit Logischerprogrammierung machen?

# Idee: Petri-Netzte
- Verwenden von Petrinetzten um Questsketten zu beschreiben. 


# Idee: Sprache

## Zielsetzung A
- Die Sprache soll verwendet werden um den Aufbau des Dungeons zu beschreiben. Dies umfasst:
  - Struktur des Levels bestimmen
  - Objekte im Dungeon erstellen und verhalten bestimmen/kontrollieren
  - Subklassen erstellen (z.B spezifische Monsterklassen) 
- Sekundärziel: Die Sprache soll den Lehrnzielen von PM nicht im Wege stehen 

## Annahmen zu A

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

## Zielsetzung B:
-  Die Implementierung wird vollständig in der Sprache umgesetzt. Die Sprache ersetzt den bisherigen Overhead. Dabei werden bestimmte Keywords wie `Hero`, `Monster`, `Item` etc. erkannt und beim Interpretieren/Kompilieren, um die benötigten Interfaces zu laden. Die Sprache kann ebenfalls das Level aufbauen.

Aus: 
  ```
//alles was jetzt kommt muss im MainController#setup ausgeführt werden  
Setup:
    var h:Hero= new Hero()
    h.hp=12

//Wie oben, ist nur als Beispiel da
BeginFrame:
  ...
  ```
 wird :
 ``` 
 public clas Hero implements IAnimatable, IEntity {
  private hp;
  
  public setHP(int i) hp=i;
  //animationen, bewegung, zeichnen etc.
  
 }
 
 public MainController() {
  //krams der da so aktuelle drinne steht

  public setup(){
    Hero h=new Hero();
    h.setHP=12;
    entityController.add(h)
  }
 
 } 
 ```



## Annahmen zu B
- Code wird nur in der Sprache geschrieben.
- Die Eingabe wird nach Java kompiliert 
- Die Sprache ist streng Objektorientiert
- Symboltabellen werden in folgender Reihenfolge aufgebaut
  1. Lokaler-Scope
  2. Klassen/Template-Scope
  3. Globaler-Scope
- Jede Funktion mit Rückgabe gibt den letzten Wert im Funktionsbody zurück (Scala style) 


## Ideen

- Mit der Sprache selbst lässt sich ein eigener Levelgenerator/Graphgenerator schreiben (dann Edgar) 
- Inline Java?
- Eventbasiert anstelle von OOP? Siehe GML 

## Fragen/Probleme

- Wie viele von den PM Inhalten sollten abgebildet werden? (Generics, Unit Tests etc.)

