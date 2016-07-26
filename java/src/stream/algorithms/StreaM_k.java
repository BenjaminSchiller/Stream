package stream.algorithms;

import java.io.IOException;
import java.util.HashSet;

import stream.algorithms.util.AdjacencyMatrixKey;
import stream.algorithms.util.UndirectedMotifsMapping;
import stream.algorithms.util.VertexGroup;
import stream.graph.Vertex;
import stream.util.Distribution;
import stream.util.Results;

public class StreaM_k extends Algorithm {

	protected final int k;
	protected Distribution motifs;
	protected UndirectedMotifsMapping umm;

	protected final int offset;

	public static final String dir = "rules/";

	public StreaM_k(int k) {
		super("StreaM_k", "" + k);
		this.k = k;
		try {
			this.umm = UndirectedMotifsMapping.read(dir, "um-" + k);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.motifs = new Distribution("motifs", this.umm.getMotifsCount());
		this.offset = (1 << (k * (k - 1) / 2 - 1));
	}

	@Override
	public void processAfterVertexAddition(int index) {
	}

	protected void process(int a, int b, boolean add) {
		if (k == 3) {
			HashSet<Integer> neighbors = this.getNeighbors(a, b);
			for (int neighbor : neighbors) {
				int key = AdjacencyMatrixKey.getKey(this.graph, a, b, neighbor);
				process(key, add);
			}
		} else if (k == 4) {
			HashSet<Integer> neighbors = this.getNeighbors(a, b);
			HashSet<VertexGroup> groups4 = this
					.extendNeighbors(a, b, neighbors);
			for (VertexGroup group : groups4) {
				int c = group.vertices[0];
				int d = group.vertices[1];
				int key = AdjacencyMatrixKey.getKey(this.graph, a, b, c, d);
				process(key, add);
			}
		} else if (k == 5) {
			HashSet<Integer> neighbors = this.getNeighbors(a, b);
			HashSet<VertexGroup> groups4 = this
					.extendNeighbors(a, b, neighbors);
			HashSet<VertexGroup> groups5 = this.extendGroups(a, b, neighbors,
					groups4);
			for (VertexGroup group : groups5) {
				int c = group.vertices[0];
				int d = group.vertices[1];
				int e = group.vertices[2];
				int key = AdjacencyMatrixKey.getKey(this.graph, a, b, c, d, e);
				process(key, add);
			}
		} else if (k == 6) {
			HashSet<Integer> neighbors = this.getNeighbors(a, b);
			HashSet<VertexGroup> groups4 = this
					.extendNeighbors(a, b, neighbors);
			HashSet<VertexGroup> groups5 = this.extendGroups(a, b, neighbors,
					groups4);
			HashSet<VertexGroup> groups6 = this.extendGroups(a, b, neighbors,
					groups5);
			for (VertexGroup group : groups6) {
				int c = group.vertices[0];
				int d = group.vertices[1];
				int e = group.vertices[2];
				int f = group.vertices[3];
				int key = AdjacencyMatrixKey.getKey(this.graph, a, b, c, d, e,
						f);
				process(key, add);
			}
		} else if (k == 7) {
			HashSet<Integer> neighbors = this.getNeighbors(a, b);
			HashSet<VertexGroup> groups4 = this
					.extendNeighbors(a, b, neighbors);
			HashSet<VertexGroup> groups5 = this.extendGroups(a, b, neighbors,
					groups4);
			HashSet<VertexGroup> groups6 = this.extendGroups(a, b, neighbors,
					groups5);
			HashSet<VertexGroup> groups7 = this.extendGroups(a, b, neighbors,
					groups6);
			for (VertexGroup group : groups7) {
				int c = group.vertices[0];
				int d = group.vertices[1];
				int e = group.vertices[2];
				int f = group.vertices[3];
				int g = group.vertices[4];
				int key = AdjacencyMatrixKey.getKey(this.graph, a, b, c, d, e,
						f, g);
				process(key, add);
			}
		} else {
			throw new IllegalArgumentException(
					"StreaM_k not implemented for k > 7");
		}
	}

	protected void process(int key, boolean add) {
		int m1 = umm.getMotif(key);
		int m2 = umm.getMotif(key + offset);

		if (add) {
			if (m1 != 0) {
				// System.out.println("+ decr " + m1 + " @ key=" + key);
				if (this.motifs.decr(m1) == -1) {
					System.out.println("m(" + m1 + ") = "
							+ this.motifs.getValues()[m1]);
				}
			}
			if (m2 != 0) {
				// System.out.println("+ incr " + m2 + " @ key=" + key);
				this.motifs.incr(m2);
			}
		} else {
			if (m1 != 0) {
				// System.out.println("- incr " + m1 + " @ key=" + key);
				this.motifs.incr(m1);
			}
			if (m2 != 0) {
				// System.out.println("- decr " + m2 + " @ key=" + key);
				if (this.motifs.decr(m2) == -1) {
					System.out.println("m(" + m2 + ") = "
							+ this.motifs.getValues()[m2]);
				}
			}
		}
	}

	protected HashSet<Integer> getNeighbors(int a, int b) {
		HashSet<Integer> g = new HashSet<Integer>();

		for (Vertex v : this.graph.V[a].edges) {
			g.add(v.index);
		}
		for (Vertex v : this.graph.V[b].edges) {
			g.add(v.index);
		}

		return g;
	}

	protected HashSet<VertexGroup> extendNeighbors(int a, int b,
			HashSet<Integer> neighbors) {
		HashSet<VertexGroup> groups = new HashSet<VertexGroup>();

		for (int n1 : neighbors) {
			for (int n2 : neighbors) {
				if (n1 != n2) {
					groups.add(new VertexGroup(n1, n2));
				}
			}
		}

		for (int n : neighbors) {
			for (Vertex nn : this.graph.V[n].edges) {
				if (a != nn.index && b != nn.index) {
					groups.add(new VertexGroup(n, nn.index));
				}
			}
		}

		return groups;
	}

	protected HashSet<VertexGroup> extendGroups(int a, int b,
			HashSet<Integer> neighbors, HashSet<VertexGroup> groups) {
		HashSet<VertexGroup> extended = new HashSet<VertexGroup>();
		for (VertexGroup g : groups) {
			for (int v : g.vertices) {
				for (Vertex e : this.graph.V[v].edges) {
					if (e.index != a && e.index != b) {
						VertexGroup g_ = VertexGroup.extend(g, e.index);
						if (g_ != null) {
							extended.add(g_);
						}
					}
				}
				for (int neighbor : neighbors) {
					VertexGroup g_ = VertexGroup.extend(g, neighbor);
					if (g_ != null) {
						extended.add(g_);
					}
				}
			}
		}
		return extended;
	}

	@Override
	public void processBeforeEdgeAddition(int a, int b) {
		this.process(a, b, true);
	}

	@Override
	public void processAfterEdgeAddition(int a, int b) {
	}

	@Override
	public void processBeforeEdgeRemoval(int a, int b) {
	}

	@Override
	public void processAfterEdgeRemoval(int a, int b) {
		this.process(a, b, false);
	}

	@Override
	public void printResults(int timestamp) {
		System.out.println(this.motifs);
	}

	@Override
	public Results getResults() {
		return new Results(this.motifs);
	}
}
