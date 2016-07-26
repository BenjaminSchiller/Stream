package stream.algorithms;

import stream.graph.Graph;

public abstract class Algorithm {
	protected String name;
	protected String arguments;
	protected Graph graph;

	public Algorithm(String name, String arguments) {
		this.name = name;
		this.arguments = arguments;
	}

	public Algorithm(String name) {
		this(name, null);
	}

	public String getName() {
		if (arguments == null) {
			return name;
		} else {
			return name + "-" + arguments;
		}
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public abstract void processAfterVertexAddition(int index);

	public abstract void processBeforeEdgeAddition(int a, int b);

	public abstract void processAfterEdgeAddition(int a, int b);

	public abstract void processBeforeEdgeRemoval(int a, int b);

	public abstract void processAfterEdgeRemoval(int a, int b);

	public abstract void printResults(int timestamp);
}
