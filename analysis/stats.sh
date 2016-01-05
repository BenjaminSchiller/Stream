#!/bin/bash

datasetDir="/Users/benni/TUD/datasets/dynamic/stream"

function stats_single {
	if [[ ! -d "$datasetDir/$1/_stats/" ]]; then mkdir "$datasetDir/$1/_stats/"; fi
	java -jar ../build/streamStats.jar "$datasetDir/$1/$1-$2.stream" "All" > "$datasetDir/$1/_stats/$1-$2.dat"
}

function plot_single {
	if [[ ! -d "$datasetDir/$1/_plots/" ]]; then mkdir "$datasetDir/$1/_plots/"; fi
	gnuplot -e "dataFile='$datasetDir/$1/_stats/$1-$2.dat';outFile='$datasetDir/$1/_plots/_$1-$2-stats';datasetName='$1-$2'" stats-single.plg
}

function stats_multi {
	dataset=$1
	file="$datasetDir/$dataset/_stats/$dataset.dat"
	echo "# th	nodes	edge	states	firstTimestamp	lastTimestamp	additions	removals" > $file
	for th in ${ths[@]}; do
		nodes=$(head -n 1 "$datasetDir/$dataset/_stats/$dataset-$th.dat" | tail -n 1 | awk '{print $NF}')
		edges=$(head -n 2 "$datasetDir/$dataset/_stats/$dataset-$th.dat" | tail -n 1 | awk '{print $NF}')
		states=$(head -n 3 "$datasetDir/$dataset/_stats/$dataset-$th.dat" | tail -n 1 | awk '{print $NF}')
		firstTimestamp=$(head -n 4 "$datasetDir/$dataset/_stats/$dataset-$th.dat" | tail -n 1 | awk '{print $NF}')
		lastTimestamp=$(head -n 5 "$datasetDir/$dataset/_stats/$dataset-$th.dat" | tail -n 1 | awk '{print $NF}')
		additions=$(head -n 6 "$datasetDir/$dataset/_stats/$dataset-$th.dat" | tail -n 1 | awk '{print $NF}')
		removals=$(head -n 7 "$datasetDir/$dataset/_stats/$dataset-$th.dat" | tail -n 1 | awk '{print $NF}')
		echo "$th	$nodes	$edges	$states	$firstTimestamp	$lastTimestamp	$additions	$removals" >> $file
	done
}

function plot_multi {
	file="$datasetDir/$dataset/_stats/$1.dat"
	if [[ ! -d "$datasetDir/$1/_plots/" ]]; then mkdir "$datasetDir/$1/_plots/"; fi
	gnuplot -e "dataFile='$datasetDir/$1/_stats/$1.dat';outFile='$datasetDir/$1/_plots/__$1-stats';datasetName='$1'" stats-multi.plg
}

if [[ ! -d stats ]]; then mkdir stats; fi

datasets=(loop complex pnb ans-short ans-long)
datasets=(complex)
ths=(7 8 9 10 11 12)

for dataset in ${datasets[@]}; do
	for th in ${ths[@]}; do
		stats_single $dataset $th
		plot_single $dataset $th
	done
	stats_multi $dataset
	plot_multi $dataset
done
