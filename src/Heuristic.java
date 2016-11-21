import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Heuristic method for TSP.
 * It uses Nearest Neighbor method.
 */
public class Heuristic {
	public static void main(String[] args) throws FileNotFoundException, IOException{
		// TODO Auto-generated method stub
		String[] cities = new String[]{"Atlanta", "Boston", "Champaign", "Cincinnati", "Denver",
				"NYC", "Philadelphia", "Roanoke", "SanFrancisco", "Toronto", "UKansasState", "UMissouri"};
		for(String city : cities){
			runMain(city);
		}
		System.out.println("Program terminates normally");
	}
	
	/**
	 * Main function to run the algorithm
	 * @param city specific instance of experiment
	 * @throws FileNotFoundException when there is no input file in the directory
	 * @throws IOException when the IO not working
	 */
	public static void runMain(String city) throws FileNotFoundException, IOException{
		long start = System.currentTimeMillis();
		long end  = start + 10;
		String filename = "./DATA/" + city + ".tsp";
		Graph g = new Graph();
		float[][] table = g.getGraph(filename);
		//**************************** NEAREST NEIGHBOR METHOD ****************************
		NearestNeighbor n = new NearestNeighbor();
		n.runNearestNeighbor(table);
		
		// get path length
		System.out.println("Nearest Neighbor of " + city + " is " + n.getCost());
		
		// print path
		//n.printPath();
		
		// to see if program exceeds run time limit
//		if(System.currentTimeMillis() < end){
//			System.out.println("program finishes on time");
//		}else{
//			System.out.println("**********************program does not finish on time********************");
//		}
	}

}
