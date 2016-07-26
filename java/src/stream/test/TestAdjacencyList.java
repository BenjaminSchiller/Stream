package stream.test;

import java.util.ArrayList;

import stream.graph.Vertex;

public class TestAdjacencyList {

	public static void main(String[] args) {
		ArrayList<Vertex> list = new ArrayList<Vertex>();
		list.add(new Vertex(2));
		list.add(new Vertex(3));
		list.add(new Vertex(9));
		list.add(new Vertex(5));
		System.out.println(list);
		list.remove(new Vertex(3));
		System.out.println(list);
	}

}
