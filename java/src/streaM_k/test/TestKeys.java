package streaM_k.test;

public class TestKeys {

	public static void main(String[] args) {
		System.out.println(get(7, 0, 1));
		System.out.println(get(7, 0, 2));
		System.out.println(get(7, 0, 3));
		System.out.println(get(7, 0, 4));
		System.out.println(get(7, 0, 5));
		System.out.println(get(7, 0, 6));
		System.out.println(get(7, 1, 2));
		System.out.println(get(7, 1, 3));
		System.out.println(get(7, 1, 4));
		System.out.println(get(7, 1, 5));
		System.out.println(get(7, 1, 6));
		System.out.println(get(7, 2, 3));
		System.out.println(get(7, 2, 4));
		System.out.println(get(7, 2, 5));
		System.out.println(get(7, 2, 6));
		System.out.println(get(7, 3, 4));
		System.out.println(get(7, 3, 5));
		System.out.println(get(7, 3, 6));
		System.out.println(get(7, 4, 5));
		System.out.println(get(7, 4, 6));
		System.out.println(get(7, 5, 6));
	}

	public static int get(int indexes, int x, int y) {
		return indexes * x - (x * (x + 3) / 2) + y - 1;
	}

}
