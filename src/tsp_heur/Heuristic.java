package tsp_heur;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/*
 * Heuristic method for TSP.
 * It uses Nearest Neighbor method.
 */
public class Heuristic {
	public static void main(String[] args) throws FileNotFoundException, IOException{
		// TODO Auto-generated method stub
		runMain(args);
		System.out.println("Program terminates normally");
	}
	
	/**
	 * Main function to run the algorithm
	 * @param city specific instance of experiment
	 * @throws FileNotFoundException when there is no input file in the directory
	 * @throws IOException when the IO not working
	 */
	public static void runMain(String[] args) throws FileNotFoundException, IOException{
		String filename = args[0];
		Graph g = new Graph();
		float[][] table = g.getGraph(filename);
		String city = g.cityName;
		//**************************** NEAREST NEIGHBOR METHOD ****************************
		NearestNeighbor n = new NearestNeighbor();
		double startTime = (double) System.currentTimeMillis() / 1000.0;
		n.runNearestNeighbor(table);
		double endTime = (double) System.currentTimeMillis() / 1000.0;
		// get path length
		//System.out.println("Nearest Neighbor of " + city + " is " + n.getCost());
		
		String outfileSol = city + "_" + "Heur_" + args[2] + ".sol";
		String outfileTrace = city + "_" + "Heur_" + args[2] + ".trace";
		// print path
		n.printPath(outfileSol);
		PrintStream outTrace = new PrintStream(outfileTrace);
		outTrace.println(String.format("%.2f", endTime-startTime) + ", " + (int)n.cost);
		outTrace.close();
	}

}
