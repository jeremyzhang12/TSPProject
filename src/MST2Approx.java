import java.util.*;
import java.io.*;

public class MST2Approx {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//System.out.println(args[0]);
		String[] cities = new String[]{"Atlanta", "Boston", "Champaign", "Cincinnati", "Denver",
				"NYC", "Philadelphia", "Roanoke", "SanFrancisco", "Toronto", "UKansasState", "UMissouri"};
		
		// generate the adjacency matrix containing distances
		for(String city : cities){
			runMain(city);
			System.out.println();
		}
	}
	
	public static void runMain(String city) throws FileNotFoundException, IOException{
		long start = System.currentTimeMillis();
		long end  = start + 10;
		String filename = "./DATA/" + city + ".tsp";
		
		// initialize the graph as a adjacency matrix stored in "table"
		Graph g = new Graph();
		float[][] table = g.getGraph(filename);
		
		//*************************** 2 APPROXIMATION MST METHOD **************************
		Vertex[] vset = g.getVertex();
		MST mst = new MST();
		
		// construct the MST
		mst.doMst(vset, table);
		HashMap<Integer, List<Integer>> tree = mst.getTree();
		
		// run test for the parameters above
		//runTestCase(tree, path, table, parent);
		
		// construct TSP and path
		int[] path = new int[table.length];
		int curr = mst.runTSP(tree, 0, path, 0);
		
		System.out.println("2 Approx MST path length is " + mst.getCost(path, table));
		// print path
		//mst.printPath(path);
		
		// to see if program exceeds run time limit
		if(System.currentTimeMillis() < end){
			System.out.println("program finishes on time");
		}else{
			System.out.println("**********************program does not finish on time********************");
		}
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
