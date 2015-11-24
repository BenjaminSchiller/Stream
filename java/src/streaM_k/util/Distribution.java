package streaM_k.util;

public class Distribution {
	protected String name;
	protected long[] values;
	protected long denominator;

	public Distribution(String name, int length) {
		this.name = name;
		this.values = new long[length];
	}

	public String getName() {
		return this.name;
	}

	public long[] getValues() {
		return this.values;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		// buff.append(this.getName() + " @ " + this.getDenominator());
		for (long count : this.values) {
			if (buff.length() == 0) {
				buff.append(count);
			} else {
				buff.append(" " + count);
			}
		}
		return buff.toString();
	}

	public String asString(String sep) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			if (buff.length() == 0) {
				buff.append(i + sep + values[i]);
			} else {
				buff.append("\n" + i + sep + values[i]);
			}
		}
		return buff.toString();
	}

	public long getDenominator() {
		return this.denominator;
	}

	public long incr(int index, int value) {
		try {
			this.values[index] += value;
		} catch (ArrayIndexOutOfBoundsException e) {
			long[] temp = new long[index + 1];
			System.arraycopy(values, 0, temp, 0, values.length);
			temp[index] = value;
			values = temp;
		}
		this.denominator += value;
		return this.values[index];
	}

	public long incr(int index) {
		return this.incr(index, 1);
	}

	public long decr(int index, int value) {
		try {
			this.values[index] -= value;
		} catch (ArrayIndexOutOfBoundsException e) {
			long[] temp = new long[index + 1];
			System.arraycopy(values, 0, temp, 0, values.length);
			temp[index] = -value;
			values = temp;
		}
		this.denominator -= value;
		return this.values[index];
	}

	public long decr(int index) {
		return this.decr(index, 1);
	}

	public void trim() {
		int maxNonZeroIndex = values.length - 1;
		while (values[maxNonZeroIndex] == 0) {
			maxNonZeroIndex--;
			if (maxNonZeroIndex == -1) {
				break;
			}
		}

		if (maxNonZeroIndex == -1) {
			values = new long[0];
			return;
		}

		if (maxNonZeroIndex < values.length) {
			long[] temp = new long[maxNonZeroIndex + 1];
			System.arraycopy(values, 0, temp, 0, maxNonZeroIndex + 1);
			values = temp;
		}
	}
}
