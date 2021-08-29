# Einleitung

<!--
*   Worum geht es hier? Was ist das betrachtete Problem bzw. die Fragestellung der Arbeit?
*   Darstellung der Bedeutung und Relevanz: Warum sollte die Fragestellung bearbeitet werden?
*   Einordnung in den Kontext
*   Abgrenzung: Welche Probleme werden im Rahmen der Arbeit *nicht* gelöst?
*   Zielsetzung: Möglichst genaue Beschreibung der Ziele der Arbeit, etwa erwarteter Nutzen oder wissenschaftlicher Beitrag

Umfang: typisch ca. 8% ... 10% der Arbeit
-->

Ein essenzieller Bestandteil eines jeden Videospiels sind seine Level. Level gehören zum Kernpunkt der Interaktion, sie sind die Spielwiese, auf der sich der Spieler mithilfe der Spielmechaniken austobt. Daher sind die Konzeptionierung und Gestaltung guter Level ein Zeit- und damit kostenintensives Unterfangen. Da Videospiele immer umfangreicher werden, müssen auch mehr Level produziert werden. Eine Möglichkeit, um kostengünstig eine Vielzahl an unterschiedlichen Level zu produzieren ist die Verwendung von Level-Generatoren, welche eigenständig unter Betrachtung verschiedener vorher definierter Kriterien, Level erzeugen können. Da jedes Videospiel sich von anderen unterscheidet und daher eigenen Anforderungen an seine Level stellt, müssen Level-Generatoren für jedes Projekt angepasst werden, um den Anforderungen des Spiels gerecht zu werden. Die Entwicklung eines solchen Generators kann je nach Anforderungen anspruchsvoller und teurer sein als die manuelle Gestaltung von Level. Ein gut entworfener Algorithmus ist jedoch in der Lage nahezu unendlich viele unterschiedliche Level zu generieren und so den Spielspaß aufrechtzuerhalten, vor allem das Genre der Rogue Like macht sich dies zu nutzen.

Im Rahmen des Moduls Programmiermethoden des Studienganges Informatik der FH-Bielefeld sollen die Studenten ihre Fähigkeiten mit der Programmiersprache Java und verschiedenen Konzepten und Tools der Softwareentwicklung verbessern. Hierzu findet neben den theoretischen Vorlesungsteil ein praktischer Programmierteil statt. In diesem praktischen Anteil entwickeln die Studenten ein eigenes 2D-Rollenspiel, das PM-Dungeon. [@Prüfungsordnung] Hierfür bekommen sie das PM-Dungeon-Framework zur Verfügung gestellt.[@DungeonGIT] Das Framework entlastet die Studenten, indem es die für das Lernziel des Moduls irrelevanten Inhalte übernimmt. Dazu zählt nebst der grafischen Darstellung auch das Laden der Level. In der Version *1.1.5.1* unterstützt das Framework nur Level welche durch den **Edgar-DotNet** Generator in der veralteten Version *1.0.6* erzeugt wurden.[@EdgarGIT] Da dieser Generator nicht auf die spezifischen Anforderungen des PM-Dungeons optimiert wurde, beschäftigt sich diese Arbeit mit der Entwicklung eines neuen Generators. 

Ziel dieser Arbeit ist es, bekannte und bereits etablierte Verfahren zur Generierung von 2D-Level zu analysieren und die jeweils besten Elemente der verschiedenen Algorithmen in einen neuen Graphen basierten Generator zu kombinieren. Die erzeugten Level sollen gängigen Kriterien für gutes Leveldesign erfüllen, daher werden im Rahmen dieser Arbeit solche Kriterien erläutert und ausgearbeitet. Der implementierte Algorithmus soll dann in das PM-Dungeon-Framework integriert werden und den Studenten verschiedene Möglichkeiten bieten, mit diesem Level zu arbeiten und ihre eigenen Spielelemente wie Items oder Monster mit dem Level zu verbinden. Die Studenten sollen auch in der Lage sein, den Algorithmus so zu konfigurieren, dass sie Level nach eigenem Belieben gestalten können. Das bedeutet, die Studenten müssen in der Lage sein, den Aufbau der Level dynamisch abfragen zu können um Beispielhaft den kritischen Pfad, also den Pfad vom Spieler bis zum Ziel bestimmen zu können, um entsprechend Monster zu platzieren. Der Generator soll möglichst spaßige und abwechslungsreiche Level erzeugen.

Die Implementation der Spiellogik ist nicht Bestandteil dieser Arbeit. Ebenso konzentriert sich diese Arbeit nicht darauf, einen Laufzeit oder Speicherplatz optimierten Algorithmus zu entwickeln. Diese Arbeit beschäftigt sich ausschließlich mit der Generierung von Level im zweidimensionalen Raum, 3D-Level und Spiele werden aufgrund ihrer stark abweichenden Anforderungen zu 2D-Level nicht betrachtet. Grafiken und Soundeffekte werden nicht generiert, jedoch soll der Generator in der Lage sein zur Verfügung gestellte Texturen zu verwenden. 

*todo aufbau der arbeit* 





