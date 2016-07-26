package stream.util;

public class Value<T> {
	protected String name;
	protected T value;

	public Value(String name, T value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public T getValue() {
		return this.value;
	}
}
