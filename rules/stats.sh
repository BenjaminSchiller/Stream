#!/bin/bash

if [[ ! -f stats.dat ]]; then
	# 1: i
	# 2: nodes
	# 3: ams
	# 4: motifs
	# 5: maxKey
	echo "#i	nodes	ams	motifs	maxKey" > stats.dat
	for i in $(ls | grep um); do
		nodes=$(head -n 1 $i | tail -n 1 | cut -d ":" -f 2 | xargs)
		maxKey=$(head -n 2 $i | tail -n 1 | cut -d ":" -f 2 | xargs)
		motifs=$(head -n 3 $i | tail -n 1 | cut -d ":" -f 2 | xargs)
		ams=$(grep -v ':' $i | wc -l | xargs)
		echo "$i	$nodes	$ams	$motifs	$maxKey" >> stats.dat
	done
fi

# if [[ ! -d plots ]]; then mkdir plots; fi

printf "
	set terminal png
	set output 'um-stats.png'
	set logscale y
	set xlabel 'motif size'
	set key top left
	set style fill solid
	set boxwidth 0.3
	plot 'stats.dat'	using 2:5 with linespoint lw 2 title 'maximum key', \\
			'' 			using 2:3 with linespoint lw 2 title 'connected sub-graphs', \\
			'' 			using 2:4 with linespoint lw 2 title 'motifs'" | gnuplot

printf "
	set terminal png
	set output 'um-stats-labeled.png'
	set logscale y
	set xlabel 'motif size'
	set key top left
	set style fill solid
	set boxwidth 0.3
	plot 'stats.dat'	using (\$2+0.0):5 with boxes lw 2 title 'maximum key', \\
			'' 			using (\$2+0.3):3 with boxes lw 2 title 'connected sub-graphs', \\
			'' 			using (\$2+0.6):4 with boxes lw 2 title 'motifs', \\
			''			using (\$2+0.0):5:5 with labels offset char 0,1.0 notitle, \\
			''			using (\$2+0.3):3:3 with labels offset char 0,0.5 notitle, \\
			''			using (\$2+0.6):4:4 with labels offset char 0,0.5 notitle" | gnuplot