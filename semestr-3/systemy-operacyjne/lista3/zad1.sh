#!/bin/bash
# Zadanie 1 - menedzer procesow

shopt -s nullglob
format="%7s %7s %-15s %5s %7s %8s %7s %7s %6s %4s\n"

koniec() {
	stty "$stary"
	tput cnorm
	clear
	exit
}

pytaj() {
	stty "$stary"
	tput cnorm
	echo
	read -p "$1" odp
	stty -echo -icanon
	tput civis
}

stary=$(stty -g)
max=$(($(tput lines) - 3))
ed=$(tput ed)

trap koniec INT
clear
tput civis
stty -echo -icanon

while true; do
	tput cup 0 0
	printf "$format" PID PPID COMM STATE TTY RSS_KB PGID SID PLIKI NI
	n=0

	for katalog in /proc/[0-9]*; do
		[[ -O $katalog ]] || continue
		((++n > max)) && break
		pid=${katalog#/proc/}
		read linia 2>/dev/null <$katalog/stat || continue

		comm=${linia#*\(}
		comm=${comm%\)*}
		reszta=${linia##*\) }
		read state ppid pgid sid tty _ _ _ _ _ _ _ _ _ _ _ nice _ _ _ _ rss _ <<<"$reszta"

		pliki=($katalog/fd/*)

		printf "$format" $pid $ppid "$comm" $state $tty $((rss * 4)) $pgid $sid ${#pliki[@]} $nice
	done

	printf "q - wyjscie   k - kill   r - renice%s" "$ed"

	read -t 1 -n 1 klawisz
	case $klawisz in
	q) koniec ;;
	k)
		pytaj "PID: "
		p=$odp
		((p > 0)) || continue
		pytaj "sygnal (STOP/CONT/TERM/KILL): "
		kill -$odp $p
		sleep 1
		;;
	r)
		pytaj "PID: "
		p=$odp
		((p > 0)) || continue
		pytaj "nowy nice (0-19): "
		renice -n $odp -p $p
		sleep 1
		;;
	esac
done
