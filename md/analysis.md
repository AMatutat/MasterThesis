# Analyse

<!--
*   Analysieren und vergleichen verschiedener Algorithmen
*   Ableiten von Algorithmen aus Spielen

*Für jeden Algo*rithmus

- Wie funktioniert der Algorithmus?
  - evtl. Technologie erklären (hier oder wo anders?)
- Zeigen von Beispielen
- Bewerten das Algorithmus anhand der Kriterien
  - Was macht der Algo gut?
  - Was macht der Algo schlecht?
  - Welche grenzen gibt es?



geschätzter Umfang ca: 40%
-->

In diesem Kapitel werden verschiedene Algorithmen zur prozeduralen Generierung vorgestellt und mithilfe der in Kapitel 2 präsentierten Bewertungskriterien analysiert und miteinander verglichen. Nicht alle vorgestellten Algorithmen sind speziell für die Generierung von Level vorgesehen, können aber mit Anpassungen oder durch Kombination dazu genutzt werden. Ziel dieses Kapitel ist es, Bausteine aus verschiedenen Algorithmen herauszufiltern, um im nächsten Kapitel daraus einen neuen Algorithmus zur Generierung von Level für das PM-Dungeon zu konzeptionieren.  

## Spelunky

- Was ist Spelunky

  - 2D Rogue-Like Plattformer

- Wie werden Level generiert

  - 4x4 Grid 

  - jedes Feld im Grid 10x8 felder groß 

  - Legen eines kritischen Pfades

    ```
    1. Wähle einen zufälligen Raum aus der ersten Reihe und markiere
    diesen als Eingang
    2 2. Wähle eine zufällige Zahl von 1 bis 5
    	1. Ist die Zahl 1 oder 2, gehe einen Raum nach links
    	2. Ist die Zahl 3 oder 4, gehe einen Raum nach rechts
    	3. Ist die Zahl eine 5, gehe einen Raum nach unten
    	4. Wird die Levelgrenze überschritten, gehe einen Raum nach
    		unten
    3. Wiederhole Schritt zwei solange, bis die Levelgrenze nach unten
    	überschritten wird
    4. Markiere den letzten Raum als Ausgang
    ```

    - markiere die Räume mit Nummern 
    - \0. Raum wurde nicht passiert 1. Raum braucht einen Ausgang links und rechts. 2. Raum braucht einen Ausgang links, rechts und nach unten. Liegt ein weiterer 2er Raum darüber, benötigt er zusätzlich einen Ausgang nach oben. 3. Raum braucht einen Ausgang links, rechts und nach oben
    - verteile Templates je nach nummerierung
    - mutiere die Templates
      - 0 Freie Fläche 1 Solider Block 2 zufällig Freie Fläche oder Solide Block 4 schiebbarer Steinblock 6 5x3 Chunk L Leiterteil P Leiterteil mit Fläche zum Stehen
    - verteile Monster und Schätze 

    

```
This system doesn‘t create the most natural-looking caves ever, and players will quickly
begin to recognize certain repeating landmarks and perhaps even sense that the levels
are generated on a grid. But with enough templates and random mutations, there‘s still
plenty of variability. More importantly, it creates fun and engaging levels that the player
can‘t easily get stuck in, something much more valuable than realism when it comes to
making an immersive experience 
```

### Analyse 



## Edgar DotNet 

https://ondra.nepozitek.cz/blog/42/dungeon-generator-part-1-node-based-approach/

http://chongyangma.com/publications/gl/2014_gl_preprint.pdf



## Cycle Dungeon

https://ctrl500.com/tech/handcrafted-feel-dungeon-generation-unexplored-explores-cyclic-dungeon-generation/

## Fast generation of planar graphs

https://users.cecs.anu.edu.au/~bdm/papers/plantri-full.pdf

## Graph of Game Worlds

https://folk.idi.ntnu.no/alfw/publications/gwg-acm-cie-2012.pdf

## Vergleich der Algorithmen

- als Matrix?

