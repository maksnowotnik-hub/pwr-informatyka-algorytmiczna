#!/bin/bash

cd -- "${1:-.}" || exit 1

for f in *; do
    [ -f "$f" ] || continue
    nowa=$(printf '%s' "$f" | tr '[:upper:]' '[:lower:]')
    if [ "$f" != "$nowa" ]; then
        mv -n -- "$f" "$nowa"
    fi
done
