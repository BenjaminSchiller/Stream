package streaM_k.algorithm;

import java.util.HashMap;

import streaM_k.algorithm.util.AdjacencyMatrixKey;

public class StreAM extends Algorithm {

	protected int[] indexes;
	protected HashMap<Integer, Integer> map;
	protected int key;
	protected int key2;

	public StreAM(int... indexes) {
		super("StreAM", asString(indexes));
		this.indexes = indexes;
		this.map = new HashMap<Integer, Integer>();
		for (int i = 0; i < indexes.length; i++) {
			map.put(indexes[i], i);
		}
		this.key = 0;
		this.key2 = -1;
	}

	protected static String asString(int[] values) {
		StringBuffer buff = new StringBuffer();
		for (int v : values) {
			if (buff.length() == 0) {
				buff.append(v);
			} else {
				buff.append("-" + v);
			}
		}
		return buff.toString();
	}

	@Override
	public void processAfterVertexAddition(int index) {
	}

	private int getIndex(int n, int x, int y) {
		int index = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (i == x && j == y) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}

	protected int getOffset(int a, int b) {
		int indexA = map.get(a);
		int indexB = map.get(b);

		int x = Math.min(indexA, indexB);
		int y = Math.max(indexA, indexB);

		int index = indexes.length * x - (x * (x + 3) / 2) + y - 1;

		index = getIndex(indexes.length, x, y);

		int max = (indexes.length - 1) * (indexes.length) / 2;
		int value = max - index - 1;

		// System.out.println("v = " + value + " @ index = " + index);
		int offset = 1 << value;
		return offset;
	}

	@Override
	public void processBeforeEdgeAddition(int a, int b) {
		if (!map.containsKey(a) || !map.containsKey(b)) {
			return;
		}
		key += this.getOffset(a, b);
	}

	@Override
	public void processAfterEdgeAddition(int a, int b) {
		key2 = AdjacencyMatrixKey.getKey(this.graph, this.indexes);
	}

	@Override
	public void processBeforeEdgeRemoval(int a, int b) {
	}

	@Override
	public void processAfterEdgeRemoval(int a, int b) {
		if (!map.containsKey(a) || !map.containsKey(b)) {
			return;
		}
		key -= this.getOffset(a, b);
		key2 = AdjacencyMatrixKey.getKey(this.graph, this.indexes);
	}

	@Override
	public void printResults(int timestamp) {
		System.out.println(key);
		// System.out.println(key2);
		// System.out.println(key + "	" + key2);
	}

}
