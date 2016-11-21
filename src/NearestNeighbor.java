import java.util.*;
public class NearestNeighbor{
	public float cost;
	public int[] path;
	public Set<Integer> vset;
	public void runNearestNeighbor(float[][] table){
		vset = new HashSet<>();
		path = new int[table.length];
		//int start = 0;
		cost = Float.POSITIVE_INFINITY;
		float tempCost;
		//vset.add(start);
		for(int start = 0; start < table.length; start++){
			tempCost = 0;
			vset.clear();
			vset.add(start);
			int tempStart = start;
			while(vset.size() != table.length){
				int v = findNearest(table, tempStart, vset);
				//path[tempStart] = v;
				tempCost += table[tempStart][v];
				tempStart = v;
				vset.add(v);
			}
			tempCost += table[start][tempStart];
			if(tempCost < cost){
				cost = tempCost;
			}
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
	public int[] getPath(){
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
	 */
	public void printPath(){
		int count = 0, start = 0;
		while(count != path.length){
			System.out.println("from " + start + " to " + path[start]);
			start = path[start];
			count++;
		}
	}
}
