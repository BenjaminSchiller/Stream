#!/bin/bash

for i in $(ls | grep um); do
	entries=$(grep -v ':' $i | wc -l)
	echo "$i: $entries"
done