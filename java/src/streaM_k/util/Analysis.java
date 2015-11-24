package streaM_k.util;

import java.io.IOException;

import streaM_k.algorithm.Algorithm;
import streaM_k.algorithm.DegreeDistribution;
import streaM_k.algorithm.StreAM;
import streaM_k.algorithm.StreaM_k;
import streaM_k.graph.GraphProcessing;
import argList.ArgList;
import argList.types.array.EnumArrayArg;
import argList.types.array.StringArrayArg;
import argList.types.atomic.BooleanArg;
import argList.types.atomic.IntArg;
import argList.types.atomic.StringArg;

public class Analysis {

	public String dataset;
	public int maxTimestamp;
	public boolean printTimestamps;
	public boolean printResults;
	public boolean printForEachTimestamp;
	public Algorithm[] algorithms;

	public static enum AlgorithmType {
		StreAM, StreaM_k, Degree
	}

	public static final String sep1 = "__";
	public static final String sep2 = "_";

	public Analysis(String dataset, Integer maxTimestamp,
			Boolean printTimestamps, Boolean printResults,
			Boolean printForEachTimestamp, String[] algorithms,
			String[] parameters) {
		this.dataset = dataset;
		this.maxTimestamp = maxTimestamp;
		this.printTimestamps = printTimestamps;
		this.printResults = printResults;
		this.printForEachTimestamp = printForEachTimestamp;
		this.algorithms = new Algorithm[algorithms.length];
		for (int i = 0; i < algorithms.length; i++) {
			this.algorithms[i] = getAlgorithm(
					AlgorithmType.valueOf(algorithms[i]), parameters[i]);
		}
	}

	protected static Algorithm getAlgorithm(AlgorithmType t, String p) {
		switch (t) {
		case StreAM:
			String[] temp = p.split(sep2);
			int[] values = new int[temp.length];
			for (int i = 0; i < values.length; i++) {
				values[i] = Integer.parseInt(temp[i]);
			}
			return new StreAM(values);
		case StreaM_k:
			return new StreaM_k(Integer.parseInt(p));
		case Degree:
			return new DegreeDistribution();
		default:
			throw new IllegalArgumentException("unknown algorithm type: " + t);
		}
	}

	public static void main(String[] args) throws IOException {
		ArgList<Analysis> argList = new ArgList<Analysis>(
				Analysis.class,
				new StringArg("dataset", "path & filename to dataset"),
				new IntArg("maxTimestamp", "maximum timestamp to analyze"),
				new BooleanArg("printTimestamps",
						"should timestamps be printed?"),
				new BooleanArg("printResults", "should results be printed?"),
				new BooleanArg("printForEachTimestamp",
						"if set true, the result will be output for every timestamp until maxTimestamp"),
				new EnumArrayArg("algorithms", "algorithms to execute", sep1,
						AlgorithmType.values()),
				new StringArrayArg(
						"parameters",
						"parameters for each algorithm (sep. by '" + sep2 + "'",
						sep1));

		// args = new String[] {
		// "/Users/benni/TUD/datasets/dynamic/stream/loop/loop-7.stream",
		// "1000", "false", "true", "false", "StreaM_k", "6" };
		// args = new String[] {
		// "/Users/benni/TUD/datasets/dynamic/stream/pnb/pnb-7.stream",
		// "100", "true", "false", "false", "StreaM_k", "4" };

		Analysis a = argList.getInstance(args);
		Timer t = new Timer();
		a.execute();
		System.out.println(t.end());
	}

	public void execute() throws IOException {
		GraphProcessing.process(dataset, maxTimestamp, printTimestamps,
				printResults, printForEachTimestamp, algorithms);
	}

}
