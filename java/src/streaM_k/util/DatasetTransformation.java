package streaM_k.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import streaM_k.graph.GraphProcessing;
import argList.ArgList;
import argList.types.atomic.IntArg;
import argList.types.atomic.StringArg;

public class DatasetTransformation {

	public String dir;
	public String graphFilename;
	public String batchSuffix;
	public int batchIndexFrom;
	public int batchIndexTo;

	public DatasetTransformation(String dir, String graphFilename,
			String batchSuffix, Integer batchIndexFrom, Integer batchIndexTo) {
		this.dir = dir;
		this.graphFilename = graphFilename;
		this.batchSuffix = batchSuffix;
		this.batchIndexFrom = batchIndexFrom;
		this.batchIndexTo = batchIndexTo;
	}

	public static void main(String[] args) throws IOException {
		ArgList<DatasetTransformation> argList = new ArgList<DatasetTransformation>(
				DatasetTransformation.class, new StringArg("dir",
						"dataset dir (with / at the end)"), new StringArg(
						"graphFilename", "e.g., 0.dnag"), new StringArg(
						"batchSuffix", "e.g., .dnab"), new IntArg(
						"batchIndexFrom", "first batch index"), new IntArg(
						"batchIndexTo", "last batch index"));

		// args = new String[] {
		// "/Users/benni/TUD/datasets/dynamic/md/pnB_th_7_short/",
		// "0.dnag", ".dnab", "1", "10" };

		DatasetTransformation dst = argList.getInstance(args);
		dst.execute();
	}

	public void execute() throws IOException {
		this.processGraph(dir, graphFilename);
		for (int i = batchIndexFrom; i <= batchIndexTo; i++) {
			this.processBatch(dir, i + batchSuffix, i);
		}
	}

	protected void processGraph(String dir, String filename) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(dir + filename));
		String line = null;
		while ((line = r.readLine()) != null) {
			if (line.equals(">>> Nodes")) {
				int nodes = Integer.parseInt(r.readLine());
				System.out.println(nodes);
			}
			if (line.equals(">>> List of Edges")) {
				break;
			}
		}
		while ((line = r.readLine()) != null) {
			String[] temp = line.split("<->");
			System.out.println("0" + GraphProcessing.sep + temp[0]
					+ GraphProcessing.sep + temp[1] + GraphProcessing.sep
					+ GraphProcessing.keywordAdd);
		}
		r.close();
	}

	protected void processBatch(String dir, String filename, int timestamp)
			throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(dir + filename));
		String line = null;
		while ((line = r.readLine()) != null) {
			if (line.startsWith("EA_")) {
				String[] temp = line.replace("EA_", "").split("<->");
				System.out.println(timestamp + GraphProcessing.sep + temp[0]
						+ GraphProcessing.sep + temp[1] + GraphProcessing.sep
						+ GraphProcessing.keywordAdd);
			} else if (line.startsWith("ER_")) {
				String[] temp = line.replace("ER_", "").split("-");
				System.out.println(timestamp + GraphProcessing.sep + temp[0]
						+ GraphProcessing.sep + temp[1] + GraphProcessing.sep
						+ GraphProcessing.keywordRemove);
			}
		}
		r.close();
	}
}
