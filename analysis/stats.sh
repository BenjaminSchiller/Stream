#!/bin/bash

function stats {
	java -jar ../build/stats.jar "/Users/benni/TUD/datasets/dynamic/stream/$1/$2.stream" "All" > stats/$2.txt
}

function plot {
	gnuplot -e "dataFile='stats/$1.txt';outFile='stats/_$1.png'" stats.plg
}

function all {
	stats $1 $2
	plot $2
}

if [[ ! -d stats ]]; then mkdir stats; fi

all loop loop-7
all loop loop-8
all loop loop-9
all loop loop-10
all loop loop-11
all loop loop-12

all pnb pnb-7
all pnb pnb-8
all pnb pnb-9
all pnb pnb-10
all pnb pnb-11
all pnb pnb-12