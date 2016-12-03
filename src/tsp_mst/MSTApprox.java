package tsp_mst;

import java.util.*;
import java.io.*;

public class MSTApprox {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		runMain(args);
	}
	
	public static void runMain(String[] args) throws FileNotFoundException, IOException{
		String filename = args[0];
		// initialize the graph as a adjacency matrix stored in "table"
		Graph g = new Graph();
		float[][] table = g.getGraph(filename);
		String city = g.cityName;
		String outfileSol = city + "_" + args[1] + "_" + args[2] + ".sol";
		String outfileTrace = city + "_" + args[1] + "_" + args[2] + ".trace";
		//*************************** 2 APPROXIMATION MST METHOD **************************
		Vertex[] vset = g.getVertex();
		MST mst = new MST();
		
		// construct the MST
		double startTime = (double) System.currentTimeMillis() / 1000.0;
		mst.doMst(vset, table);
		double endTime = (double) System.currentTimeMillis() / 1000.0;
		//System.out.println(String.format("%.2f", endTime-startTime));
		HashMap<Integer, List<Integer>> tree = mst.getTree();
		
		// run test for the parameters above
		//runTestCase(tree, path, table, parent);
		
		// construct TSP and path
		int[] path = new int[table.length];
		int curr = mst.runTSP(tree, 0, path, 0);
		
		//System.out.println("MST Approx for " + city + " is " + mst.getCost(path, table));
		// print path
		mst.printPath(outfileSol, path);
		PrintStream outTrace = new PrintStream(outfileTrace);
		outTrace.println(String.format("%.2f", endTime-startTime) + ", " + mst.pathCost);
		outTrace.close();
	}
	
	/**
	 * Test function to see if table, path, and MST work well
	 * @param tree MST
	 * @param path path of visited order
	 * @param table adjacency matrix
	 * @param parent array recording the parent of vertices.
	 */
	public static void runTestCase(HashMap<Integer, List<Integer>> tree, List<Integer> path, 
			float[][] table, int[] parent){
		// test adjacency matrix
		for(int i = 0; i < table.length; i++){
			for(int j = 0; j < table.length; j++){
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
		// test for path
		for(int p : path){
			System.out.println(p);
		}
		// test for parent link
		for(int i = 0; i < parent.length; i++){
			System.out.println(i + "'s parent is " + parent[i]);
		}
		for(int key : tree.keySet()){
			System.out.println("parent vertex: " + key);
			for(int e : tree.get(key)){
				System.out.print(e + " ");
			}
			System.out.println();
		}
	}

}
