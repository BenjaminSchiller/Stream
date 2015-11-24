package streaM_k.test;

import streaM_k.util.Distribution;

public class TestDistribution {

	public static void main(String[] args) {
		Distribution d = new Distribution("name", 0);
		d.incr(3, 3);
		d.incr(3);
		d.incr(2);
		d.incr(5);
		d.incr(1);
		System.out.println(d);
		d.decr(5);
		System.out.println(d);
		d.trim();
		System.out.println(d);
		d.decr(3);
		d.decr(3, 3);
		d.decr(2);
		d.decr(1);
		System.out.println(d);
		d.trim();
		System.out.println(d);
	}

}
