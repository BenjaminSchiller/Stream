package stream.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import stream.graph.GraphProcessing;
import argList.ArgList;
import argList.types.atomic.EnumArg;
import argList.types.atomic.StringArg;

public class DatasetStats {

	public static enum PrintType {
		StatsOnly, DataOnly, All
	}

	public String dataset;
	public PrintType printType;

	public DatasetStats(String dataset, String printType) {
		this.dataset = dataset;
		this.printType = PrintType.valueOf(printType);
	}

	public static void main(String[] args) throws IOException {
		ArgList<DatasetStats> argList = new ArgList<DatasetStats>(
				DatasetStats.class, new StringArg("dataset",
						"path & filename to dataset"), new EnumArg("printtype",
						"what kind of data should be printed?",
						PrintType.values()));

		// args = new String[] {
		// "/Users/benni/TUD/datasets/dynamic/stream/loop/loop-7.stream",
		// "StatsOnly" };

		DatasetStats stats = argList.getInstance(args);
		stats.print();
	}

	public void print() throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(dataset));
		int nodes = Integer.parseInt(r.readLine());
		String line = null;
		LinkedList<int[]> states = new LinkedList<int[]>();
		long add = 0;
		long remove = 0;
		while ((line = r.readLine()) != null) {
			String[] temp = line.split(GraphProcessing.sep);
			int timestamp = Integer.parseInt(temp[0]);
			int from = Integer.parseInt(temp[1]);
			int to = Integer.parseInt(temp[2]);
			String keyword = temp[3];

			if (states.size() == 0 || timestamp != states.getLast()[0]) {
				states.add(new int[] { timestamp, 0, 0 });
			}

			if (keyword.equals(GraphProcessing.keywordAdd)) {
				states.getLast()[1]++;
				add++;
			} else if (keyword.equals(GraphProcessing.keywordRemove)) {
				states.getLast()[2]++;
				remove++;
			} else {
				r.close();
				throw new IllegalArgumentException("unknown keyword: "
						+ keyword);
			}
		}
		r.close();

		if (printType.equals(PrintType.All)
				|| printType.equals(PrintType.StatsOnly)) {
			System.out.println("# nodes:            " + nodes);
			System.out.println("# initial edges     " + states.getFirst()[1]);
			System.out.println("# states:           " + states.size());
			System.out.println("# first timestamp:  " + states.getFirst()[0]);
			System.out.println("# last timestamp:   " + states.getLast()[0]);
			System.out.println("# total additions:  " + add);
			System.out.println("# total removals:   " + remove);
			System.out.println("# add per state:    "
					+ ((double) add / states.size()));
			System.out.println("# remove per state: "
					+ ((double) remove / states.size()));
		}
		if (printType.equals(PrintType.All)) {
			System.out.println("# ");
		}
		if (printType.equals(PrintType.All)
				|| printType.equals(PrintType.DataOnly)) {
			String sep = "	";
			int index = 0;
			System.out.println("# index	timestamp	additions	removals	nodes	edges");
			int edges = 0;
			for (int[] stats : states) {
				edges += stats[1];
				edges -= stats[2];
				System.out.println(index + sep + stats[0] + sep + stats[1]
						+ sep + stats[2] + sep + nodes + sep + edges);
				index++;
			}
		}
	}
}
