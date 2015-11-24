#!/bin/bash

function streAM {
	java -jar ../build/stream.jar $dataset $maxTimestamp false true true StreAM $1
}

function matrix {
	nodes=$1
	file=$2
	sep=$3

	edges=$(($((${nodes}-1))*${nodes}/2))
	keys=$((2**${edges}))

	matrix=()

	current='-1'
	for value in $(cat $file | grep -v 'sec'); do
		if [[ $current -eq '-1' ]]; then
			current=$value
		else
			prev=$current
			current=$value
			index=$((${prev}*${keys}+${current}))
			matrix[$index]=$((${matrix[$index]}+1))
		fi
	done

	line=''
	for i in $(seq 0 $((keys-1))); do
		line="$line$sep$i"
	done
	echo "$line"

	for i in $(seq 0 $((keys-1))); do
		line="$i"
		for j in $(seq 0 $((keys-1))); do
			index=$(($i*$keys+j))
			if test "${matrix[${index}]+isset}"; then
				line="${line}${sep}${matrix[$index]}"
			else
				line="${line}${sep} "
			fi
		done
		echo "$line"
	done
}


dataset="/Users/benni/TUD/datasets/dynamic/stream/loop/loop-7.stream"
maxTimestamp="66666"

nodes="4"
groups=("0_2_14_15" "2_5_16_6" "3_7_12_13" "4_12_6_13" "8_11_0_1")

for group in ${groups[@]}; do
	src="results/loop-7--${group}.txt"
	dst="results/loop-7--${group}--StreAM"
	echo "processing $group"
	if [[ ! -d "results/" ]]; then mkdir results; fi
	streAM $group > $src
	# matrix 4 $src "	" > "${dst}.txt"
	matrix $nodes $src ";" > "${dst}.csv"
done