# Prozedurale graphenbasierte 2D-Level-Generierung

**Abgabedatum:  27.01.2022**

Alles für den schriftlichen Teil der Masterarbeit.

## Ideen
- Arbeiten auf 3 Ebenen
  - Macro-Ebene
    - Stellt die gesamte Welt dar
    - Ein Knoten sind größere Gebiete 
    - Darstellung als Graph
  - Micro-Ebene
    - Stellt Gebiete der Welt da (z.B Waldgebiet, Feuergebiet etc.) 
    - Besteht aus mehreren Knoten
    - Knoten stellen einzelne Räume dar
    - Hat kritische Wege und Räume (diese müssen betreten werden um weiter zu kommen)
    - Hat optionale Wege und Räume
    - Darstellung als Graph
  - Nano-Ebene
    - Sind die einzelnen Räume
    - Darstellung als Graph
    - Sind "Teil" Prozedural? 



## Sollte man mal angucken

### Generatoren und Algorithmen

- [Edgar](https://ondra.nepozitek.cz/blog/42/dungeon-generator-part-1-node-based-approach/)
- [Game Level Layout from Design Specification](http://chongyangma.com/publications/gl/index.html)
- [Procedural Level Generation for a 2D Platformer](https://digitalcommons.calpoly.edu/cgi/viewcontent.cgi?article=1156&context=cscsp)]
- [Enhancing level difficulty and additional content in platform videogames through graph analysis](https://comum.rcaap.pt/bitstream/10400.26/6089/1/Enhancing%20level%20difficulty%20and%20additional%20content%20in%20platform%20videogames....pdf)
- [Constrained Level Generation through Grammar-Based Evolutionary Algorithms](http://julian.togelius.com/Font2016Constrained.pdf)
- [Graph of Game Worlds: New Perspectives on Video Game Architectures](https://folk.idi.ntnu.no/alfw/publications/gwg-acm-cie-2012.pdf)
- [Spelunky Generator Lesson](http://tinysubversions.com/spelunkyGen/)
- [Random Walk](https://de.wikipedia.org/w/index.php?title=Random_Walk&oldid)
- [Random Walk Cave Generation](http://www.roguebasin.com/index.php?title=Random_Walk_Cave_Generation)
- [Dungeon Generator – Graph-Based Approach](https://ondra.nepozitek.cz/blog/42/dungeon-generator-part-1-node-based-approach/)
- Synthese generierter und handgebauter Welten mittels WaveFunctionCollapse
- [Automatic Puzzle Level Generation: A General Approach using a Description Language](http://www.akhalifa.com/documents/AutomaticPuzzleLevelGeneration.pdf)
- [Design Patterns and General Video Game Level Generation](https://www.researchgate.net/profile/Adeel-Zafar/publication/320129531_Design_Patterns_and_General_Video_Game_Level_Generation/links/5d5138a4299bf1995b784bbd/Design-Patterns-and-General-Video-Game-Level-Generation.pdf)
- [Graph of Game Worlds: New Perspectives on Video Game Architectures](https://folk.idi.ntnu.no/alfw/publications/gwg-acm-cie-2012.pdf)
- [Using Graph-Based Analysis to Enhanced Autmoatic Level Generation for Platform Videogames](https://www.researchgate.net/profile/Fausto-Mourato/publication/262209767_Using_Graph-Based_Analysis_to_Enhance_Automatic_Level_Generation_for_Platform_Videogames/links/5748cf5208ae5f7899b9db8b/Using-Graph-Based-Analysis-to-Enhance-Automatic-Level-Generation-for-Platform-Videogames.pdf)
- [A Hierarchical MdMC Approach to 2D Video Game Map Generation](https://ojs.aaai.org/index.php/AIIDE/article/download/12794/12642/16311)

### Leveldesign

- [Ten Principles of Good Level Design](https://www.gamasutra.com/blogs/DanTaylor/20130929/196791/Ten_Principles_of_Good_Level_Design_Part_1.php)
- [Ten Principles for Good Level Design](https://www.youtube.com/watch?v=iNEe3KhMvXM)
- [Beginning Level Design, Part 1](https://www.gamasutra.com/view/feature/131736/beginning_level_design_part_1.php?page=2)
- [Hi, I am Warren Spector, a game developer from Origin, Ion Storm and Junction Point. I worked on Deus Ex and Disney Epic Mickey and a lot of other games. AMA!](https://www.reddit.com/r/IAmA/comments/34fdjb/hi_i_am_warren_spector_a_game_developer_from/)
- [Beyond Pacing: Games Aren't Hollywood](https://www.gamasutra.com/view/feature/132423/beyond_pacing_games_arent_.php)
- [Analysing Mario to Master Super Mario Maker | Game Maker's Toolkit](https://www.youtube.com/watch?v=e0c5Le1vGp4)
- [Following the Little Dotted Line | Game Maker's Toolkit](https://www.youtube.com/watch?v=FzOCkXsyIqo)
- [What Capcom Didn't Tell You About Resident Evil 4 | Game Maker's Toolkit](https://www.youtube.com/watch?v=zFv6KAdQ5SE)
- [GameStar-Podcast - Folge 50: Psycho-Tricks beim Weltenbau - Wie uns Spiele unterschwellig lenken](https://www.gamestar.de/artikel/gamestar-podcast-folge-50,3338909.html)
- [Why Nathan Drake Doesn't Need a Compass | Game Maker's Toolkit](https://www.youtube.com/watch?v=k70_jvVOcG0)
- [Das Weltdesign von Dark Souls | Boss Keys](https://www.youtube.com/watch?v=QhWdBhc3Wjc&t)
- [How to Keep Players Engaged (Without Being Evil) | Game Maker's Toolkit](https://www.youtube.com/watch?v=hbzGO_Qonu0)

### Other

- [A Brief History of Rogue](http://www.digital-eel.com/deep/A_Brief_History_of_Rogue.htm)

- [International Roguelike Development Conference](http://roguebasin.roguelikedevelopment.org/index.php?title=Berlin_Interpretation)

- Spelunky. Boss Fight Books ISBN: 9781940535111

- [Roguelikes, Persistency, and Progression | Game Maker's Toolkit](https://www.youtube.com/watch?v=G9FB5R4wVno&t=1s)

- [Procedural generation: a primer for game devs](https://www.gamasutra.com/blogs/ScottBeca/20170223/292255/Procedural_generation_a_primer_for_game_devs.php)

- [Procedural Content Generation](https://www.gamecareerguide.com/features/336/procedural_content_.php?page=1)

- [MIGS: Far Cry 2's Guay On The Importance Of Procedural Content](https://users.informatik.haw-hamburg.de/~schumann/BachelorArbeitKevinHagen.pdf#page=88&zoom=100,72,584)
- [Zufallsgenerierte Spielwelten - Das Spiel mit dem Zufall](https://www.gamestar.de/artikel/zufallsgenerierte-spielwelten-das-spiel-mit-dem-zufall,3025323,seite1.html)

### Spiele mit prozedurale Generierung
- Hades
  - nach ersten Eindruck: Fest gebaute Räume die random verknüpft werden   
- Spelunky
  - GridLayout
  - Feste Räume die nochmal random etwas angepast werden
   - das könnte man gut auf Nano-Ebene verwenden 
  - Constraints zum verteilen von Monstern und Items     
- Rogue
- Shattered Pixel Dungeon
- Anno (die Inseln)
- Returnal
  - nach ersten Eindruck: Fest gebaute Räume die random verknüpft werden    
- Dead Cells
- Minecraft (3D) 
- Rogue Legacy
- The Binding of Isaac
 - nach ersten Eindruck: Fest gebaute Räume die random verknüpft werden    


## Questions
- Extra Kapitel für die Analyse?
- Zitierstile?
- Reverse engeneering? 
- Auswertung im Fazit oder Realsierung? 


