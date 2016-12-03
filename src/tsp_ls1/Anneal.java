/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_ls1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 天予
 */
public class Anneal {
    
    class Route {
        double length = 0;
        double finishTime;
        List<Graph.Vertex> list;
        
        Route() {
            list = new ArrayList<>();
        }
        
        Route(Route r) {
           this.length = r.length;
           this.list = new ArrayList<>(r.list);
        }
        
        public void init() {
            List<Graph.Vertex> temp = new ArrayList<>(graph.vertices);
            for(int i = graph.degree; 0 < i; i--) {
                int num = random.nextInt(i);
                Graph.Vertex vertex = temp.remove(num);
                list.add(vertex);
            }
        }
        
        public void swap(int u,int v) {
            Graph.Vertex backup = list.get(u);
            list.set(u, list.get(v));
            list.set(v, backup);
        }

        private void computeLength() {
            length = 0;
            for(int i = 0; i < list.size(); i++) {
                int src = list.get(i).index;
                int tgt = list.get((i + 1) % list.size()).index;
                length += graph.edges.get(src).get(tgt).length;
            }
        }
    }
    
    List<Route> record = new ArrayList<>();
    Route optimal;
    Graph graph;
    double time;
    int seed;
    Random random;
    
    int iterations;
    double temperature;
    double delta;

    public Anneal(Graph graph, double time, int seed) {
        this.graph = graph;
        this.time = time * 1000000000;
        this.seed = seed;
        random = new Random(seed);
        
        iterations = 5000 * graph.degree;           
	temperature = 2000;            
	delta = 0.98;        
    }
    
    public void run() {
        double MSTtotal = 0;
        long startMST = System.nanoTime();
        long finishMST = 0;
        
        Route route = new Route();
        route.init();
        route.computeLength();
        optimal = new Route(route);
        finishMST = System.nanoTime();
        MSTtotal = (finishMST - startMST);
        optimal.finishTime = MSTtotal;
        record.add(optimal);
        
        while(0.005 < temperature) {         
            finishMST = System.nanoTime();
            MSTtotal = (finishMST - startMST);
            if(MSTtotal >= time)
                break;
            for(int k = 0; k < iterations; k++) {
                double lengthPrev = route.length;
                int u = random.nextInt(graph.degree);
                int v = random.nextInt(graph.degree);
                if(u == v)
                    continue;                
                route.swap(u,v);        
                route.computeLength();
                double lengthNext = route.length;
                
                if(route.length < optimal.length) {
                    finishMST = System.nanoTime();
                    MSTtotal = (finishMST - startMST);
                    optimal = new Route(route);
                    optimal.finishTime = MSTtotal;
                    record.add(optimal);
                }
			
		if(lengthPrev <= lengthNext && Math.exp(-(lengthNext-lengthPrev) / temperature) * 100000 < random.nextInt(100000)) {
                    route.swap(u,v);
                    route.length = lengthPrev;
                }
            }
            temperature *= delta;          
	}
    }
    
}
