package stream.algorithms;

import stream.util.Distribution;
import stream.util.Results;

public class DegreeDistribution extends Algorithm {

	protected Distribution distr;

	public DegreeDistribution() {
		super("DegreeDistribution");
		this.distr = new Distribution("DegreeDistribution", 0);
	}

	@Override
	public void processAfterVertexAddition(int index) {
		distr.incr(0);
	}

	@Override
	public void processBeforeEdgeAddition(int a, int b) {
		distr.decr(this.graph.V[a].getDegree());
		distr.decr(this.graph.V[b].getDegree());
		distr.incr(this.graph.V[a].getDegree() + 1);
		distr.incr(this.graph.V[b].getDegree() + 1);
	}

	@Override
	public void processAfterEdgeAddition(int a, int b) {
	}

	@Override
	public void processBeforeEdgeRemoval(int a, int b) {
	}

	@Override
	public void processAfterEdgeRemoval(int a, int b) {
		distr.decr(this.graph.V[a].getDegree() + 1);
		distr.decr(this.graph.V[b].getDegree() + 1);
		distr.incr(this.graph.V[a].getDegree());
		distr.incr(this.graph.V[b].getDegree());
	}

	@Override
	public void printResults(int timestamp) {
		System.out.println(distr);
	}

	@Override
	public Results getResults() {
		return new Results(this.distr);
	}

}
