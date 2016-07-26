package stream.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import stream.algorithms.Algorithm;
import stream.util.Timer;

public class GraphProcessing {
	public static final String sep = "	";
	public static final String keywordAdd = "+";
	public static final String keywordRemove = "-";

	public static void process(String dataset, int maxTimestamp,
			boolean printTimestamps, boolean printResults,
			boolean printForEachTimestamp, Algorithm... algorithms)
			throws IOException {

		BufferedReader r = new BufferedReader(new FileReader(dataset));

		int nodes = Integer.parseInt(r.readLine());

		Graph graph = new Graph(nodes);
		for (Algorithm alg : algorithms) {
			alg.setGraph(graph);
			for (int i = 0; i < graph.V.length; i++) {
				alg.processAfterVertexAddition(i);
			}
		}

		int previousTimestamp = -1;
		int currentTimestamp = -1;

		Timer timer = new Timer();

		String line = null;
		while ((line = r.readLine()) != null) {
			previousTimestamp = currentTimestamp;
			String[] temp = line.split(sep);
			currentTimestamp = Integer.parseInt(temp[0]);
			int a = Integer.parseInt(temp[1]);
			int b = Integer.parseInt(temp[2]);
			boolean add = temp[3].equals(keywordAdd);

			if (currentTimestamp > maxTimestamp) {
				break;
			}

			// System.out.println("p = " + previousTimestamp + "   c = "
			// + currentTimestamp);

			if (previousTimestamp != -1
					&& currentTimestamp != previousTimestamp) {
				if (printForEachTimestamp) {
					print(printTimestamps, printResults, algorithms,
							previousTimestamp, currentTimestamp - 1, timer);
				} else {
					print(printTimestamps, printResults, algorithms,
							previousTimestamp, previousTimestamp, timer);
				}
			}

			if (add) {
				for (Algorithm alg : algorithms) {
					alg.processBeforeEdgeAddition(a, b);
				}
				graph.addEdge(a, b);
				for (Algorithm alg : algorithms) {
					alg.processAfterEdgeAddition(a, b);
				}
			} else {
				for (Algorithm alg : algorithms) {
					alg.processBeforeEdgeRemoval(a, b);
				}
				graph.removeEdge(a, b);
				for (Algorithm alg : algorithms) {
					alg.processAfterEdgeRemoval(a, b);
				}
			}
		}

		if (printForEachTimestamp) {
			print(printTimestamps, printResults, algorithms, previousTimestamp,
					Math.min(currentTimestamp, maxTimestamp), timer);
		} else {
			print(printTimestamps, printResults, algorithms, previousTimestamp,
					previousTimestamp, timer);
		}

		r.close();
	}

	protected static void print(boolean printTimestamps, boolean printResults,
			Algorithm[] algorithms, int from, int to, Timer timer) {
		for (int i = from; i <= to; i++) {
			if (printTimestamps) {
				printTimestamp(i, timer);
			}
			if (printResults) {
				printResults(algorithms, i);
			}
		}
	}

	protected static void printTimestamp(int timestamp, Timer timer) {
		timer.end();
		System.out.println(timestamp + ": " + timer.getDuration());
		timer.reset();
	}

	protected static void printResults(Algorithm[] algorithms, int timestamp) {
		for (Algorithm alg : algorithms) {
			alg.printResults(timestamp);
		}
	}
}
