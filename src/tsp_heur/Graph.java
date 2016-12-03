package tsp_heur;

import java.util.*;
import java.lang.Math;
import java.io.*;

/**
 * Graph initializer class that will read the file in and prepare the adjacency matrix
 * @author Jingtian
 *
 */
public class Graph {
	float[][] graph;
	Vertex[] vset;
	public String cityName;
	/**
	 * helper function to get graph
	 * @param filename whole name of input file
	 * @return adjacency matrix
	 * @throws FileNotFoundException when the input file is not found
	 * @throws IOException when the IO is not working
	 */
	public float[][] getGraph(String filename) throws FileNotFoundException, IOException{
		readFile(filename);
		return graph;
	}
	
	/**
	 * to get all vertices
	 * @return array of vertices
	 */
	public Vertex[] getVertex(){
		return vset;
	}
	
	/**
	 * helper function to get the distance
	 * @param x1 from point x
	 * @param y1 from point y
	 * @param x2 to point x
	 * @param y2 to point y
	 * @return relative distance between from and to point
	 */
	private float getDist(float x1, float y1, float x2, float y2){
		return (float) Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
	}
	
	/**
	 * main function to read input file in and build adjacency matrix
	 * @param filename input file name
	 * @throws FileNotFoundException when filename is invalid or path not existing
	 * @throws IOException when IO not working
	 */
	public void readFile(String filename) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String[] firstLine = br.readLine().split(" ");
		cityName = firstLine[1];
		br.readLine();
		String[] dimension = br.readLine().split(" ");
		int d = Integer.parseInt(dimension[1]);
		br.readLine();
		br.readLine();
		graph = new float[d][d];
		vset = new Vertex[d]; 
		for(int i = 0; i < d; i++){
			String[] line = br.readLine().split(" ");
			int id = Integer.parseInt(line[0]) - 1;
			float x = Float.parseFloat(line[1]);
			float y = Float.parseFloat(line[2]);
			Vertex v = new Vertex(x,y,Float.POSITIVE_INFINITY,id);
			vset[i] = v;
		}
		for(int i = 0; i < d; i++){
			for(int j = i+1; j < d; j++){
				graph[i][j] = getDist(vset[i].x, vset[i].y, vset[j].x, vset[j].y);
				graph[j][i] = graph[i][j];
			}
		}
		br.close();
	}
}
