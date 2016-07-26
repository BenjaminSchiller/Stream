#!/bin/bash

function example {
	# 1: number of vertices
	# 2: direction [d,u]
	# 3: diagonal [define,weight,*]
	# 4: names to use [index,char,none]
	# 5: label
	# 6: caption
	# 7: type [key,list]
	# 8: definition (depending on type)
	#    key: number
	#    list: list of entries (sep by ,)
	# 9: placement [line,circle]

	/Users/benni/TUD/Projects/LaTeX-Graphs/scripts/tikzGraph.py $2 u - index '' '' list $3 circle > $1-graph.tex
	/Users/benni/TUD/Projects/LaTeX-Graphs/scripts/adjacencyMatrix.py $2 u - index '' '' list $3 > $1-am.tex
}

example "example-4" 4 0,1,1,0,1,0
example "example-5" 5 0,1,1,0,1,0,1,0,0,1
example "example-6" 6 0,1,1,0,1,0,1,0,0,1,1,0,0,1,0

example "example-4-A" 4 0,1,1,0,1,0
example "example-4-B" 4 1,0,0,0,1,1
example "example-4-C" 4 1,1,0,1,1,1