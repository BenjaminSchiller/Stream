package streaM_k.algorithm.util;

import streaM_k.graph.Graph;

public class AdjacencyMatrixKey {
	public static int getKey(Graph graph, int... vertices) {
		int key = 0;
		for (int i = 0; i < vertices.length; i++) {
			for (int j = i + 1; j < vertices.length; j++) {
				if (graph.containsEdge(vertices[i], vertices[j])) {
					key += 1;
				}
				key = key << 1;
			}
		}
		key = key >> 1;
		return key;
	}
}
