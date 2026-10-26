# ShapesFX – Lista 5

Prosty edytor graficzny napisany w JavaFX.

## Co się tu znajduje

- rysowanie myszką okręgu, prostokąta i wielokąta,
- zaznaczanie figury i jej przesuwanie, powiększanie scrollem oraz obracanie,
- zmiana koloru figury z menu pod prawym przyciskiem myszy,
- zapis figur do pliku i ich ponowne wczytanie,
- okno z informacjami o programie i instrukcja obsługi w menu.

## Pliki

- `ShapesFX.java` – okno aplikacji i menu
- `ShapeManager.java` – rysowanie i edycja figur
- `FileManager.java` – zapis i odczyt z pliku

## Uruchomienie

Wymaga [JavaFX SDK](https://openjfx.io/):

```bash
javac --module-path <ścieżka-do-javafx>/lib --add-modules javafx.controls *.java
java --module-path <ścieżka-do-javafx>/lib --add-modules javafx.controls ShapesFX
```
