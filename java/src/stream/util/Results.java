package stream.util;

import java.util.ArrayList;

public class Results {
	protected ArrayList<Distribution> distributions = new ArrayList<Distribution>();
	@SuppressWarnings("rawtypes")
	protected ArrayList<Value> values = new ArrayList<Value>();

	public Results(Distribution... distributions) {
		for (Distribution d : distributions) {
			this.distributions.add(d);
		}
	}

	@SuppressWarnings("rawtypes")
	public Results(Value... values) {
		for (Value v : values) {
			this.values.add(v);
		}
	}

	public ArrayList<Distribution> getDistributions() {
		return this.distributions;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Value> getValues() {
		return this.values;
	}
}
