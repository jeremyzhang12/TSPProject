package tsp_mst;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
/*
 * This class implements the Minimum Spanning Tree that will be used to do 2 approximation
 * method for TSP
 * @author Jingtian Zhang
 */
public class MST {
	//ArrayList<Integer> path = new ArrayList<>(); // path of 
	HashSet<Integer> visited = new HashSet<>(); // visited vertices
	HashMap<Integer, List<Integer>> tree = new HashMap<>(); // MST
	int[] parent; // array of parent node where index is child, value is parent
	Vertex[] vertices;
	int mstCost;
	float[][] localTable;
	public double time;
	public int pathCost;
	/**
	 * Main function to calculate MST for current graph
	 * It also implements the path of MST.
	 * @param vset vertex container
	 * @param table lookup table for distance between any two vertices
	 */
	public void doMst(Vertex[] vset, float[][] table){
		localTable = table;
		vertices = vset;
		int currId = 0;
		parent = new int[vset.length];
		parent[0] = -1;
		mstCost = 0;
		int prev = 0;
		while(visited.size() != vset.length){
			visited.add(currId);
			//path.add(currId);
			prev = currId;
			//update the unvisited vertex key
			updateKey(currId,vertices,table);
			
			//pick minimum key from neighbor;
			currId = findMinNeighbor(vertices);
			mstCost += table[prev][currId];
			parent[currId] = vertices[currId].p;
			//update the tree as visited
			if(!tree.containsKey(vertices[currId].p)){
				ArrayList<Integer> arr = new ArrayList<>();
				arr.add(currId);
				tree.put(vertices[currId].p, arr);
			}else{
				tree.get(vertices[currId].p).add(currId);
			}
		}
		parent[currId] = prev;
		// debug message
		//System.out.println("the last visited city is " + last);
		//System.out.println("the parent of " + last + " is " + prev);
	}
	
	/**
	 * get the optimum cost of the tsp
	 * @return total cost for MST
	 */
	public int getMSTCost(){
		return mstCost;
	}
	
	/**
	 * get the parent array. index is current node and parent[index] is parent node.
	 * @return array of parent node
	 */
	public int[] getParent(){
		return parent;
	}
	
	/**
	 * to get the MST
	 * @return MST in a hashmap style
	 */
	public HashMap<Integer, List<Integer>> getTree(){
		return tree;
	}
	/**
	 * Used to update the key value of the vertex that is not visited
	 * @param id the source vertex
	 * @param vset the vertex container
	 * @param table the distance lookup table
	 */
	public void updateKey(int sourceId, Vertex[] vset, float[][] table){
		for(Vertex v : vset){
			if(v.id != sourceId && !visited.contains(v.id) && v.key > table[sourceId][v.id]){
				v.key = table[sourceId][v.id];
				v.p = sourceId;
			}
		}
	}
	
	/**
	 * Find the minimum vertex in the neighborhood 
	 * @param vset vertex container
	 * @param id source vertex
	 * @return the optimum neighbor that has least distance to source
	 */
	public int findMinNeighbor(Vertex[] vset){
		float key = Float.POSITIVE_INFINITY;
		int tempid = 0;
		for(Vertex v : vset){
			if(!visited.contains(v.id) && v.key < key){
				key = v.key;
				tempid = v.id;
			}
		}
		return tempid;
	}
	
	/**
	 * 
	 * @return the traversing order when constructing the MST
	 */
//	public ArrayList<Integer> getPath(){
//		return path;
//	}
	
	/**
	 * Preorder traversal of the MST built. Fulfill the 2 approximation MST requirement.
	 * @param tree MST built using Prim's algorithm
	 * @param curr parent point for current level
	 * @param res path memorization array
	 * @param size size of visited nodes on the path
	 * @return dummy variable in order to help implement preorder.
	 */
	public int runTSP(HashMap<Integer, List<Integer>> tree, int curr, int[] res, int size){
		if(size == res.length) return -1;
		if(!tree.containsKey(curr)){
			return curr;
		}
		if(tree.get(curr).isEmpty()){
			return curr;
		}
		for(Integer child : tree.get(curr)){
			res[curr] = child;
			size++;
			curr = runTSP(tree, child, res, size);
			if(curr == -1) return -1;
		}
		return curr;
	}
	
	public float getCost(int[] res, float[][] table){
		int count = 0;
		float pathLength = 0;
		int prev = 0, curr = 0;
		while(count != res.length){
			//System.out.println("from " + index + " to " + res[index]);
			curr = res[prev];
			pathLength += table[prev][curr];
			count++;
		}
		pathLength += table[res[0]][curr];
		return pathLength;
	}
	
	/**
	 * Print the TSP tour
	 * @param res result array from preorder traversal recording the sequence of visiting
	 * @throws FileNotFoundException 
	 */
	public void printPath(String outfile, int[] res) throws FileNotFoundException{
		PrintStream out = new PrintStream(outfile);
		pathCost = (int) getCost(res, localTable);
		out.println(pathCost);
		
		int index = 0;
		int count = 0;
		while(count != res.length){
			out.println(index + " " + res[index] + " " + localTable[index][res[index]]);
			index = res[index];
			count++;
		}
		out.close();
	}
}
