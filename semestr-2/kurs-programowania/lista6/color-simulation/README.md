# Color Simulation – Lista 6

Symulacja planszy n × m kolorowych pól napisana w Swingu z użyciem wątków.

## Co się tu znajduje

- każde pole planszy działa jako osobny wątek,
- pole co pewien czas losuje nowy kolor albo przyjmuje średni kolor swoich czterech sąsiadów (plansza zawija się na brzegach),
- synchronizacja wątków, żeby pola nie odczytywały koloru sąsiada w trakcie jego zmiany,
- kliknięcie pola wstrzymuje lub wznawia jego wątek,
- suwaki do zmiany szybkości symulacji i prawdopodobieństwa zmiany koloru,
- przyciski do dodawania nowego wiersza lub kolumny w trakcie działania.

## Pliki

- `ColorBoard.java` – okno, plansza i panel sterowania
- `SimulationCell.java` – pojedyncze pole (wątek)

## Uruchomienie

```bash
javac *.java
java ColorBoard
```
