/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_bnb;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author 天予
 */
public class Bnb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO code application logic here
        String instance = args[0];
        String method = args[1];
        String time = args[2];
        Graph graph = new Graph(instance);
        BranchBound branchBound = new BranchBound(graph, Double.parseDouble(time));
        branchBound.run();
        
        String city = graph.info.name;
        String solutionFile = city + "_" + method + "_" + time + ".sol";
        String traceFile = city + "_" + method + "_" + time + ".trace";
        
        PrintWriter solutionOutput = new PrintWriter(solutionFile, "UTF-8");
        PrintWriter traceOutput = new PrintWriter(traceFile, "UTF-8");
        
        solutionOutput.println((int)branchBound.optimal.length);
        for(int i = 0; i < graph.degree; i++) {
            int src = branchBound.optimal.chosenList.get(i).index;
            int tgt = branchBound.optimal.chosenList.get((i + 1) % graph.degree).index;
            int length = (int)graph.edges.get(src).get(tgt).length;
            solutionOutput.println(src + " " + tgt + " " + length);
        }
        
        for(int i = 0; i < branchBound.record.size(); i++) {
            String finishTime = String.format("%.2f", branchBound.record.get(i).finishTime / 1000000000.0);
            int quality = (int)branchBound.record.get(i).length;
            traceOutput.println(finishTime + " " + quality);
        }        
        
        solutionOutput.close();
        traceOutput.close();
    }
    
}
