/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_ls1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author 天予
 */
public class LS1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO code application logic here
        String instance = args[0];
        String method = args[1];
        String time = args[2];
        int seed = Integer.parseInt(args[3]);
        Graph graph = new Graph(instance);
        Anneal anneal = new Anneal(graph, Double.parseDouble(time), seed);
        anneal.run();
        
        String city = graph.info.name;
        String solutionFile = city + "_" + method + "_" + time + "_" + seed + ".sol";
        String traceFile = city + "_" + method + "_" + time + "_" + seed + ".trace";
        
        PrintWriter solutionOutput = new PrintWriter(solutionFile, "UTF-8");
        PrintWriter traceOutput = new PrintWriter(traceFile, "UTF-8");
        
        solutionOutput.println((int)anneal.optimal.length);
        for(int i = 0; i < graph.degree; i++) {
            int src = anneal.optimal.list.get(i).index;
            int tgt = anneal.optimal.list.get((i + 1) % graph.degree).index;
            int length = (int)graph.edges.get(src).get(tgt).length;
            solutionOutput.println(src + " " + tgt + " " + length);
        }
        
        for(int i = 0; i < anneal.record.size(); i++) {
            String finishTime = String.format("%.2f", anneal.record.get(i).finishTime / 1000000000.0);
            int quality = (int)anneal.record.get(i).length;
            traceOutput.println(finishTime + " " + quality);
        }        
        
        solutionOutput.close();
        traceOutput.close();
    }
    
}
