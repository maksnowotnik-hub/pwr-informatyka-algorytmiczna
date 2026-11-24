# Lista 3 – Bash, `/proc` i Xv6

| Zadanie | Plik | Opis |
|---------|------|------|
| 1 | [zad1.sh](./zad1.sh) | Menedżer procesów (uproszczony `htop`): procesy użytkownika z `/proc/PID/stat` odświeżane co sekundę, wysyłanie sygnałów i `renice` |
| 2 | [zad2.sh](./zad2.sh) | Menedżer zasobów: użycie i częstotliwość rdzeni, prędkość sieci z wykresem historii, uptime, obciążenie, pamięć, bateria |
| 3 | [zad3.sh](./zad3.sh) | Monitor katalogu w czasie rzeczywistym (`inotifywait`): kolorowe komunikaty i log w `monitor.log` |
| 4 | [zad4.sh](./zad4.sh) | Losowy kot z The Cat API jako ASCII art (`catimg`) i cytat z Chuck Norris API (`curl`, `jq`) |
| 5 | [expr.c](./expr.c) | Program `expr` dla Xv6: `expr 2 + 2` wypisuje `4` (operatory `+ - * /`) |

## Wymagane pakiety (Arch Linux)

```bash
sudo pacman -S inotify-tools jq catimg
```

## Uruchomienie

### Zadanie 1

```bash
./zad1.sh
```

Klawisze: `q` – wyjście, `k` – sygnał (`STOP`, `CONT`, `TERM`, `KILL`), `r` – zmiana nice.
Kolumny: `PID PPID COMM STATE TTY RSS_KB PGID SID PLIKI NI`.

### Zadanie 2

```bash
./zad2.sh
```

Wyjście: Ctrl+C.

### Zadanie 3

```bash
./zad3.sh <katalog>
```

Śledzi tworzenie, usuwanie, modyfikację i zmianę nazwy plików. Log zapisuje do `monitor.log` w bieżącym katalogu. Wyjście: Ctrl+C.

### Zadanie 4

```bash
./zad4.sh
```

### Zadanie 5 (Xv6)

1. Skopiuj `expr.c` do katalogu [xv6-public](https://github.com/mit-pdos/xv6-public).
2. W `Makefile` dopisz `_expr\` do listy `UPROGS`.
3. Uruchom:

```bash
make qemu-nox
```

W powłoce Xv6:

```
$ expr 2 + 2
4
$ expr 10 / 3
3
$ expr 10 - 2
8
```

Wyjście z QEMU: Ctrl+A, potem X.
