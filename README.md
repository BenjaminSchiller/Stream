# StreaM_k



## Repo structure

	analysis/
		|- stream.sh (StreAM - adjacency matrix transitions as markov chains)
	build/
		|- build.xml (ant build file)
		|- datasets.jar
		|- stream.jar
	datasets/
		(examplary datasets of the stream file format)
	java/
		(implementation of StreaM_k, StreAM, etc.)
	rules/
		|- um-* (adjacency matrix to motif mapping for multiple motif sizes)


## Programs - jar files

### datasets.jar

The basic data format we use for our work is the [DNA](http://dynamic-networks.org/dna/) format, described [here](https://github.com/BenjaminSchiller/DNA/blob/master/doc/FORMATS.md).

Here, a dynamic graph is represented as an initial graph (`.dnag`) and a list of batches (`.dnab`) that contains the changes that occurr in the graph over time.

This program allows us to transform a dynamic graph given in the DNA format into the stream file format explained below.

	expecting 5 arguments
		0: dir - dataset dir (with / at the end) (String)
		1: graphFilename - e.g., 0.dnag (String)
		2: batchSuffix - e.g., .dnab (String)
		3: batchIndexFrom - first batch index (Integer)
		4: batchIndexTo - last batch index (Integer)

An example of the usage of the program:

	java -jar datasets.jar datasets/test/ 0.dnag .dnab 10 230 > test.stream

### stream.jar

This program contains various metrics that were implemented and developed in DNA.
We re-implemented them here as a standalone version to remove the overhead of using a framework around the computation.

The program takes as input the path to the dataset to be analyzed and the maximum timestamp until which the analysis should be performed.
All results are output on standard out and can be enaled / disabled by setting the respective flag:

- *printTimestamps*: timestamp (+ runtime for analysis from the last step)
- *printResults*: results of algorithms

Results are only output for timestamps for which edge additions or removals are contained in the dataset.
Using the *printForEachTimestamp* flag, it can be enabled that output is created for each timestamp in between as well (the results are sinply repeated).

Finally, the algorithms that should be executed are specified with their parameters.

	expecting 7 arguments
		0: dataset - path & filename to dataset (String)
		1: maxTimestamp - maximum timestamp to analyze (Integer)
		2: printTimestamps - should timestamps be printed? (Boolean)
		3: printResults - should results be printed? (Boolean)
		4: printForEachTimestamp - if set true, the result will be output for every timestamp until maxTimestamp (Boolean)
		5: algorithms - algorithms to execute (String[]) sep. by '__'
			from: StreAM StreaM_k Degree
		6: parameters - parameters for each algorithm (sep. by '_' (String[]) sep. by '__'

Examples for the usage of the program:

	java -jar stream.jar test.stream 100 false true true StreaM_k 5
	java -jar stream.jar test.stream 100 true true false StreAM__Degree 5_14_20__-

#### Rules (!)

Note that the program expects the rules to be available in the directory where the jar is executed, i.e., `./rules/`.

## The `stream` file format

A `stream` file starts with a single number which is the total number of vertices that occurr during the complete lifetime of the graph.

Each further line consists of the addition or removal of an edge identified by the index of the two vertices connected by that edge.
Here, vertices are identified by indexes starting at `0`.
Each line consists of four entries: the timestamp when the change happens, the index of the first vertex, the index of the second vertex, and the action (add: + or remove: -).

graphs stored in `stream` do not specify if the represented dynamic graph is directed or undirected.
In case a directed graph is modeled, the first vertex is considered as source and the second as destination.

### Txamples of the `stream` file format

	4
	0	0	1	+
	1	0	2	+
	2	0	3	+
	3	1	2	+
	4	1	3	+
	5	2	3	+

	500
	12	10	50 +
	12	10	51 +
	12	10	52 +
	12	23	100 +
	12	24	201 +
	17	10	52 -
	17	3	6	+
	17	23	100 -

Additional example scan be found in `datasets/`.