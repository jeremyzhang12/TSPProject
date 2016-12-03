package tsp_heur;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
public class NearestNeighbor{
	float[][] localTable;
	public float cost;
	public ArrayList<Integer> path;
	public Set<Integer> vset;
	public void runNearestNeighbor(float[][] table){
		localTable = table;
		vset = new HashSet<>();
		cost = Float.POSITIVE_INFINITY;
		float tempCost;
		path = new ArrayList<>();
		int best = 0;
		//pick the best starting point
		for(int start = 0; start < table.length; start++){
			tempCost = 0;
			vset.clear();
			vset.add(start);
			int tempStart = start;
			while(vset.size() != table.length){
				int v = findNearest(table, tempStart, vset);
				tempCost += table[tempStart][v];
				tempStart = v;
				vset.add(v);
			}
			tempCost += table[start][tempStart];

			if(tempCost < cost){
				cost = tempCost;
				best = start;
			}
		}
		vset.clear();
		vset.add(best);
		int tempstart = best;
		path.add(best);
		while(vset.size() != table.length){
			int v = findNearest(table, tempstart, vset);
			path.add(v);
			tempstart = v;
			vset.add(v);
		}
	}
	
	/**
	 * helper function to find next best place to go
	 * @param table adjacency matrix storing all edge costs
	 * @param u the "from" location
	 * @param vset set of vertices, used to check duplicate
	 * @return the target "to" location
	 */
	public int findNearest(float[][] table, int u, Set<Integer> vset){
		float temp = Float.POSITIVE_INFINITY;
		int res = 0;
		for(int i = 0; i < table[u].length; i++){
			if(table[u][i] < temp && !vset.contains(i)){
				temp = table[u][i];
				res = i;
			}
		}
		return res;
	}
	
	/**
	 * get the path of TSP tour
	 * @return path of TSP tour as an array.
	 */
	public ArrayList<Integer> getPath(){
		return path;
	}
	
	/**
	 * get cost of the whole TSP tour
	 * @return cost of TSP tour
	 */
	public float getCost(){
		return this.cost;
	}
	
	/**
	 * Print the TSP path
	 * @throws FileNotFoundException 
	 */
	public void printPath(String outfile) throws FileNotFoundException{
		PrintStream out = new PrintStream(outfile);
		out.println((int)cost);
		int prev = path.get(0);
		int curr;
		for(int i = 1; i < path.size(); i++){
			curr = path.get(i);
			out.println(prev + " " + curr + " " + localTable[prev][curr]);
			prev = curr;
		}
		out.println(path.get(path.size()-1) + " " + path.get(0) + " " + localTable[path.get(0)][path.get(path.size()-1)]);
		out.close();
	}
}
