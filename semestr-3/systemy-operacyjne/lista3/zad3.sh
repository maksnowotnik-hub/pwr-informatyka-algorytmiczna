#!/bin/bash
# Zadanie 3 - monitor katalogu (inotify)

katalog=$1

inotifywait -m -q -e create,delete,modify,moved_from,moved_to "$katalog" |
	while read sciezka zdarzenie plik; do
		czas=$(date '+%Y-%m-%d %H:%M:%S')

		case $zdarzenie in
		CREATE*)
			kolor=32
			opis="dodano"
			;;
		DELETE*)
			kolor=31
			opis="usunieto"
			;;
		MODIFY*)
			kolor=33
			opis="zmodyfikowano"
			;;
		MOVED_FROM*)
			kolor=36
			opis="zmiana nazwy z"
			;;
		MOVED_TO*)
			kolor=36
			opis="zmiana nazwy na"
			;;
		esac

		echo -e "\e[${kolor}m$czas $opis: $plik\e[0m"
		echo "$czas $opis: $plik" >>monitor.log
	done
