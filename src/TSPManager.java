import java.io.FileNotFoundException;
import java.io.IOException;

import tsp_bnb.Bnb;
import tsp_heur.Heuristic;
import tsp_ls1.LS1;
import tsp_ls2.TwoOPT;
import tsp_mst.MSTApprox;

/*
 * Main of main programs that fires TSP algorithms
 */
public class TSPManager {
	enum Method{
		BnB, MSTApprox, Heur, LS1, LS2;
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub	
		// a way to parse command line input and select the corresponding method
		String[] arg = new String[4];
		arg[0] = args[1];
		arg[1] = args[3];
		arg[2] = args[5];
		arg[3] = args[7];
		Method m = Method.valueOf(arg[1]);
		switch(m) {
			case MSTApprox:
				MSTApprox.main(arg);
				break;
			case Heur:
				Heuristic.main(arg);
				break;
			case LS1:
				LS1.main(arg);
				break;
			case LS2:
				TwoOPT.main(arg);
				break;
			case BnB:
				Bnb.main(arg);
				break;
		}
	}

}
