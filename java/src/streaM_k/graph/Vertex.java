package streaM_k.graph;

import java.util.ArrayList;

public class Vertex {
	public int index;

	public ArrayList<Vertex> edges;

	public Vertex(int index) {
		this.index = index;
		this.edges = new ArrayList<Vertex>();
	}

	public int getDegree() {
		return this.edges.size();
	}

	public Iterable<Vertex> getEdges() {
		return this.edges;
	}

	public boolean addEdge(Vertex v) {
		return this.edges.add(v);
	}

	public boolean removeEdge(Vertex v) {
		return this.edges.remove(v);
	}

	public String toString() {
		return this.index + "";
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Vertex
				&& ((Vertex) obj).index == this.index;
	}
}
