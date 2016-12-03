/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_bnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author 天予
 */
public class BranchBound {
        
    List<Route> record = new ArrayList<>();
    Route optimal;
    Graph graph;
    double time;
    
    double MSTtotal = 0;
    long startMST = 0;
    long finishMST = 0;

    public BranchBound(Graph graph, double time) {
        this.graph = graph;
        this.time = time * 1000000000;
    }
    
    public void run() {
        Route route = new Route(graph);
        route.bound = Double.MAX_VALUE;
        optimal = new Route(graph);
        optimal.length = Double.MAX_VALUE;
        
        startMST = System.nanoTime();
        DFS(route);                      
    }
    
    private void DFS(Route route) {
        for (int i = 0; i < route.remainList.size(); i++) {
            finishMST = System.nanoTime();
            MSTtotal = (finishMST - startMST);
            if(MSTtotal >= time)
                break;
            
            route.chosenList.add(route.remainList.get(i));
            route.remainList.remove(i);

            if (1 < route.chosenList.size()) {
                int src = route.chosenList.get(route.chosenList.size() - 2).index;
                int tgt = route.chosenList.get(route.chosenList.size() - 1).index;
                Edge e = graph.edges.get(src).get(tgt);
                route.edges.add(e);
                route.length += e.length;
            }

            if (route.remainList.isEmpty()) {
                int src = route.chosenList.get(route.chosenList.size() - 1).index;
                int tgt = route.chosenList.get(0).index;
                Edge e = graph.edges.get(src).get(tgt);
                route.edges.add(e);
                route.length += e.length;
                if (route.length < optimal.length) {
                    finishMST = System.nanoTime();
                    MSTtotal = (finishMST - startMST);
                    Route solution = new Route(route);
                    solution.finishTime = MSTtotal;
                    record.add(solution);
                    optimal = solution;
                }
                //recover
                route.length -= route.edges.get(route.edges.size() - 1).length;
                route.edges.remove(route.edges.size() - 1);
            }
            else {
                route.bound = route.computeBound();
                if (route.bound < optimal.length) 
                    DFS(route);
            }
            
            //recover
            route.remainList.add(i, route.chosenList.get(route.chosenList.size() - 1));
            route.chosenList.remove(route.chosenList.size() - 1);
            if(0 < route.edges.size()) {
                route.length -= route.edges.get(route.edges.size() - 1).length;
                route.edges.remove(route.edges.size() - 1);  
            }
        }

    }
    
}
