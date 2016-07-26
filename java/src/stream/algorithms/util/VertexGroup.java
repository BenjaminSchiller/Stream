package stream.algorithms.util;

import java.util.Arrays;

public class VertexGroup {
	public int[] vertices;

	public VertexGroup(int... vertices) {
		this.vertices = vertices;
		Arrays.sort(this.vertices);
	}

	public boolean contains(int vertex) {
		for (int i : this.vertices) {
			if (i == vertex) {
				return true;
			}
		}
		return false;
	}

	public static VertexGroup combine(VertexGroup g1, VertexGroup g2) {
		int[] g3 = new int[g1.vertices.length + g2.vertices.length];
		System.arraycopy(g1.vertices, 0, g3, 0, g1.vertices.length);
		System.arraycopy(g2.vertices, 0, g3, g1.vertices.length,
				g2.vertices.length);
		Arrays.sort(g3);
		for (int i = 1; i < g3.length; i++) {
			if (g3[i] == g3[i - 1]) {
				return null;
			}
		}
		return new VertexGroup(g3);
	}

	public static VertexGroup extend(VertexGroup g1, int index) {
		if (g1.contains(index)) {
			return null;
		}
		int[] g = new int[g1.vertices.length + 1];
		System.arraycopy(g1.vertices, 0, g, 0, g1.vertices.length);
		g[g.length - 1] = index;
		return new VertexGroup(g);
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		for (int i : this.vertices) {
			if (buff.length() == 0) {
				buff.append(i);
			} else {
				buff.append(" " + i);
			}
		}
		return buff.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof VertexGroup)) {
			return false;
		}
		VertexGroup g = (VertexGroup) obj;
		if (g.vertices.length != this.vertices.length) {
			return false;
		}
		for (int i = 0; i < this.vertices.length; i++) {
			if (g.vertices[i] != this.vertices[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		if (vertices.length == 1) {
			return vertices[0];
		} else if (vertices.length == 2) {
			return vertices[0] + vertices[1] * 65536;
		} else if (vertices.length == 3) {
			return vertices[0] + vertices[1] * 1024 + vertices[2] * 1048576;
		} else if (vertices.length == 4) {
			return vertices[0] + vertices[1] * 256 + vertices[2] * 65536
					+ vertices[3] * 16777216;
		} else if (vertices.length == 5) {
			return vertices[0] + vertices[1] * 64 + vertices[2] * 4096
					+ vertices[3] * 262144 + vertices[4] * 16777216;
		} else if (vertices.length == 6) {
			return vertices[0] + vertices[1] * 32 + vertices[2] * 1024
					+ vertices[3] * 32768 + vertices[4] * 1048576 + vertices[5]
					* 33554432;
		} else {
			throw new IllegalArgumentException("hshCode only defined up to k=8");
		}
	}

	public boolean containsDuplicate() {
		for (int i = 1; i < this.vertices.length; i++) {
			if (this.vertices[i] == this.vertices[i - 1]) {
				return true;
			}
		}
		return false;
	}
}
