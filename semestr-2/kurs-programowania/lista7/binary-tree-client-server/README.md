# Binary Tree Client-Server – Lista 7

Drzewo poszukiwań binarnych (BST) zaimplementowane w Javie i C++ z użyciem typów generycznych.

## Co się tu znajduje

- operacje na drzewie: wstawianie, wyszukiwanie, usuwanie i rysowanie,
- drzewo działa dla liczb całkowitych, liczb zmiennoprzecinkowych, tekstu oraz własnego typu `Rectangle` (porównywanego po polu),
- **Java** (`java/`): serwer TCP obsługujący wielu klientów naraz oraz klient z interfejsem w JavaFX, w którym wybiera się typ drzewa i wysyła polecenia,
- **C++** (`cpp/`): drzewo jako szablon klasy, obsługiwane z konsoli.

## Uruchomienie

Java (klient wymaga [JavaFX SDK](https://openjfx.io/)):

```bash
cd java
javac --module-path <ścieżka-do-javafx>/lib --add-modules javafx.controls *.java
java Server
java --module-path <ścieżka-do-javafx>/lib --add-modules javafx.controls Client
```

C++:

```bash
cd cpp
g++ BinaryTree.cpp -o BinaryTree
./BinaryTree
```
