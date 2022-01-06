# Anhang

## Graphen

Hier werden verschiedene, von GraphG erzeugte, planare Graphen gezeigt. Diese Graphen können genutzt werden, um Level Strukturell darzustellen (vgl. Kapitel 2). 

![Von GraphG erzeugter Graph mit 12 Knoten und 2 extra Kanten. \label{graphex1}](figs/chapter4/graphgsol/example1.png)

![Von GraphG erzeugter Graph mit 15 Knoten und 4 extra Kanten. \label{graphex2}](figs/chapter4/graphgsol/example2.png)

![Von GraphG erzeugter Graph mit 20 Knoten und 4 extra Kanten. \label{graphex3}](figs/chapter4/graphgsol/example3.png)

![Von GraphG erzeugter Graph mit 7 Knoten und 1 extra Kanten. \label{graphex4}](figs/chapter4/graphgsol/example4.png)

## GitHub-Workflows 

\label{workflows}

Das folgende Listing zeigt wie der GitHub-Workflow für die Einhaltung die Code-Konvetion mithilfe von `google-java-formater` konfiguriert wurde. Nach jedem push auf den Branch `master` oder `codeBase` führt der Formatter automatisch eine Formatierung des Codes durch. 

```
name: Format
on:
  push:
    branches:
      - master
      - codeBase
jobs:
  formatting:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2 # v2 minimum required
      - uses: axel-op/googlejavaformat-action@v3
        with:
          args: "--skip-sorting-imports --aosp --replace"
          githubToken: ${{ secrets.GITHUB_TOKEN }}
```
Das folgende Listing zeigt wie der GitHub-Workflow konfiguriert wurde, um für jeden Pull-Request die Testsuits laufen zu lassen und zu prüfen ob alle Tests erfolgreich sind. In GitHub wurden die Pull-Requests so konfiguriert, das ein Mergen nur dann möglich ist, wenn alle Tests erfolgreich sind. 

```
name: Java CI

on: 
  pull_request:
    branches:
      - codeBase
jobs:
    build-and-test:
        name: Build and Test
        runs-on: ubuntu-latest
        steps:
            -   name: Checkout
                uses: actions/checkout@v2
            -   name: Set up JDK 11
                uses: actions/setup-java@v2
                with:
                    java-version: '11'
                    distribution: 'adopt'
            -   name: Validate Gradle wrapper
                uses: gradle/wrapper-validation-action@v1
            -   name: Build with Gradle
                run: ./gradlew build                
            -   name: Publish Test Report
                uses: mikepenz/action-junit-report@v2
                if: always() # always run even if the previous step fails
                with:
                    report_paths: '**/build/test-results/test/TEST-*.xml'
```


