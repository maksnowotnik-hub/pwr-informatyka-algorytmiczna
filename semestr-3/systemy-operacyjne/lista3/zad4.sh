#!/bin/bash
# Zadanie 4 - kot z The Cat API (ASCII art) + losowy cytat Chucka Norrisa

# 1. adres losowego zdjecia kota (API zwraca tablice -> pierwszy element)
url=$(curl -s https://api.thecatapi.com/v1/images/search | jq -r '.[0].url')

# 2. pobranie zdjecia do pliku i wyswietlenie go w terminalu
curl -s "$url" -o /tmp/kot
catimg /tmp/kot

# 3. losowy cytat (API zwraca obiekt -> pole value)
cytat=$(curl -s https://api.chucknorris.io/jokes/random | jq -r '.value')
echo
echo "$cytat"
