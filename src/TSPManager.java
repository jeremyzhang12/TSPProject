import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Main of main programs that fires TSP algorithms
 */
public class TSPManager {
	enum Method{
		BnB, MSTApprox, Heur, LS1, LS2;
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String[] cities = new String[]{"Atlanta", "Boston", "Champaign", "Cincinnati", "Denver",
				"NYC", "Philadelphia", "Roanoke", "SanFrancisco", "Toronto", "UKansasState", "UMissouri"};
		
		// a way to parse command line input and select the corresponding method
		Method m = Method.valueOf(args[0]);
		System.out.println(args[0]);
		switch(m) {
			case MSTApprox:
				//args[0] = "Heur";
				MSTApprox.main(args);
				break;
			case Heur:
				Heuristic.main(args);
				break;
		}
		//MST2Approx.main(args);
		//Heuristic.main(args);
	}

}
