package streaM_k.graph;

public class Graph {
	public Vertex[] V;

	public boolean[][] am;

	public Graph(int vertices) {
		V = new Vertex[vertices];
		for (int i = 0; i < vertices; i++) {
			V[i] = new Vertex(i);
		}
		am = new boolean[vertices][vertices];
	}

	public boolean containsEdge(int a, int b) {
		return am[a][b];
	}

	public void addEdge(int a, int b) {
		am[a][b] = true;
		am[b][a] = true;
		V[a].addEdge(V[b]);
		V[b].addEdge(V[a]);
	}

	public void removeEdge(int a, int b) {
		am[a][b] = false;
		am[b][a] = false;
		V[a].removeEdge(V[b]);
		V[b].removeEdge(V[a]);
	}
}
