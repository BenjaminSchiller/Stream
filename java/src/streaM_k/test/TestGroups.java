package streaM_k.test;

import java.util.HashSet;

import streaM_k.algorithm.util.VertexGroup;

public class TestGroups {

	public static void main(String[] args) {
		VertexGroup g1 = new VertexGroup(1, 2);
		VertexGroup g2 = new VertexGroup(3, 4);
		VertexGroup g3 = new VertexGroup(2, 5, 1, 6);

		System.out.println(g1);
		System.out.println(g2);
		System.out.println(g3);

		System.out.println();

		System.out.println(VertexGroup.combine(g1, g2));
		System.out.println(VertexGroup.combine(g2, g3));

		System.out.println();

		System.out.println(VertexGroup.combine(g1, g1));
		System.out.println(VertexGroup.combine(g2, g2));
		System.out.println(VertexGroup.combine(g3, g3));
		System.out.println(VertexGroup.combine(g1, g3));

		System.out.println();

		HashSet<VertexGroup> set = new HashSet<VertexGroup>();
		set.add(new VertexGroup(1,2));
		set.add(new VertexGroup(1,2));
		set.add(new VertexGroup(1,2));
		set.add(new VertexGroup(1));
		set.add(new VertexGroup(1));
		System.out.println(set);
		
		System.out.println();

		System.out.println((new VertexGroup(1,1)).hashCode());
		System.out.println((new VertexGroup(1,2)).hashCode());
		System.out.println((new VertexGroup(1,3)).hashCode());
	}

}
