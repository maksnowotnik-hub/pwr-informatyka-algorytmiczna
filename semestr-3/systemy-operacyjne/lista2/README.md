# Lista 2 – terminal, wywołania systemowe i deskryptory

| Zadanie | Plik | Opis |
|---------|------|------|
| 6 | [kolory.c](./kolory.c), [zad6.txt](./zad6.txt) | Wypisuje „Hello, World!” we wszystkich kolorach terminala: 16 podstawowych, 256 i true color (sekwencje ANSI `ESC[...m`) |
| 7 | [zad7.txt](./zad7.txt) | Analiza `strace`: przekierowania `>` i `>>`, pliki otwierane przez `ls -l`, porównanie `strace -c`, odczyt `/proc/[pid]/stat` przez `ps` |
| 8 | [male.sh](./male.sh) | Zmienia nazwy plików w katalogu na małe litery |
| 9 | [zad9.txt](./zad9.txt) | Przekierowanie standardowego wyjścia działającego procesu do pliku za pomocą `gdb` (`open`, `dup2`, `close`) |

## Uruchomienie

```bash
gcc -Wall -pedantic kolory.c -o kolory
./kolory
```

```bash
./male.sh [katalog]
```

Bez argumentu `male.sh` działa w bieżącym katalogu.
