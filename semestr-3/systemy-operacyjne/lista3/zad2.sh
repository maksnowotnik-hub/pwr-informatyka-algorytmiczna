#!/bin/bash
# Zadanie 2 - menedzer zasobow

bloki=($'\u2581' $'\u2582' $'\u2583' $'\u2584' $'\u2585' $'\u2586' $'\u2587' $'\u2588')

czytaj_cpu() {
	local i=0
	while read nazwa u n s id io irq sirq st reszta; do
		[[ $nazwa == cpu[0-9]* ]] || continue
		cal[i]=$((u + n + s + id + io + irq + sirq + st))
		bez[i]=$((id + io))
		((i++))
	done </proc/stat
}

czytaj_siec() {
	rx=0
	tx=0
	while read nazwa r _ _ _ _ _ _ _ t reszta; do
		[[ $nazwa == *: && $nazwa != lo: ]] || continue
		rx=$((rx + r))
		tx=$((tx + t))
	done </proc/net/dev
}

pokaz() {
	if (($1 >= 1048576)); then
		echo "$(($1 / 1048576)) MB/s"
	elif (($1 >= 1024)); then
		echo "$(($1 / 1024)) KB/s"
	else
		echo "$1 B/s"
	fi
}

wykres() {
	local max=1 v linia=""
	for v in "$@"; do
		((v > max)) && max=$v
	done
	for v in "$@"; do
		linia+=${bloki[v * 7 / max]}
	done
	echo "$linia"
}

czytaj_cpu
pcal=("${cal[@]}")
pbez=("${bez[@]}")

czytaj_siec
rx0=$rx
tx0=$tx
prx=$rx
ptx=$tx

clear
tput civis
trap 'tput cnorm; exit' INT
el=$(tput el)

while true; do
	sleep 1
	tput cup 0 0
	read up reszta </proc/uptime
	up=${up%.*}

	dni=$((up / 86400))
	godz=$(((up % 86400) / 3600))
	min=$(((up % 3600) / 60))
	sek=$((up % 60))

	echo "${el}Uptime: ${dni}d ${godz}h ${min}m ${sek}s"

	read l1 l5 l15 reszta </proc/loadavg
	echo "${el}Load: $l1 $l5 $l15"

	{
		read _ mtot _
		read _ mfree _
		read _ mavail _
	} </proc/meminfo
	uzyte=$((mtot - mavail))

	echo "${el}Pamiec: uzyte $((uzyte / 1024)) MB / $((mtot / 1024)) MB (wolne $((mfree / 1024)) MB)"

	b=/sys/class/power_supply/BAT0/uevent
	if [ -r $b ]; then bat="$(grep POWER_SUPPLY_CAPACITY= $b | cut -d= -f2)%"; else bat="brak baterii"; fi
	echo "${el}Bateria: $bat"

	czytaj_cpu
	echo "${el}"
	echo "${el}CPU:"
	mhz=($(grep 'cpu MHz' /proc/cpuinfo | cut -d: -f2))
	for i in "${!cal[@]}"; do
		dc=$((cal[i] - pcal[i]))
		db=$((bez[i] - pbez[i]))
		echo "${el}  cpu$i: $((100 * (dc - db) / dc))%  ${mhz[i]%.*} MHz"
	done
	pcal=("${cal[@]}")
	pbez=("${bez[@]}")

	czytaj_siec
	drx=$((rx - prx))
	dtx=$((tx - ptx))
	prx=$rx
	ptx=$tx
	hrx+=($drx)
	htx+=($dtx)
	if ((${#hrx[@]} > 40)); then
		hrx=("${hrx[@]:1}")
		htx=("${htx[@]:1}")
	fi
	echo "${el}"
	echo "${el}Siec:"
	echo "${el}  pobieranie: $(pokaz $drx)  (srednio $(pokaz $(((rx - rx0) / SECONDS))))"
	echo "${el}  $(wykres "${hrx[@]}")"
	echo "${el}  wysylanie:  $(pokaz $dtx)  (srednio $(pokaz $(((tx - tx0) / SECONDS))))"
	echo "${el}  $(wykres "${htx[@]}")"
done
