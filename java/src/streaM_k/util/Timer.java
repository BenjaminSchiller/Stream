package streaM_k.util;

public class Timer {
	private String name;

	private long start;

	private long duration;

	public Timer(String name) {
		this.name = name;
		// this.start = System.currentTimeMillis();
		this.start = System.nanoTime();
		this.duration = 0;
	}

	public Timer() {
		this(null);
	}

	public void restart() {
		this.start = System.nanoTime();
	}

	public String end() {
		this.duration += System.nanoTime() - this.start;
		return this.toString();
	}

	public long getDuration() {
		return this.duration;
	}

	public void reset() {
		this.duration = 0;
		this.start = System.nanoTime();
	}

	public String toString() {
		return (this.duration / 1000.0 / 1000.0) + " msec / "
				+ (this.duration / 1000.0 / 1000.0 / 1000.0) + " sec";
	}

	public String getName() {
		return this.name;
	}
}
