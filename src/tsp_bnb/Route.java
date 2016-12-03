/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_bnb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author 天予
 */
class Route implements Comparable{

    double length;
    double bound;
    double finishTime;
    List<Vertex> chosenList;
    List<Vertex> remainList;
    List<Edge> edges;
    Graph graph;

    Route(Graph graph) {
        length = 0;
        chosenList = new ArrayList<>();
        remainList = new ArrayList<>(graph.vertices);
        edges = new ArrayList<>();
        this.graph = graph;
    }

    Route(Route r) {
        this.length = r.length;
        this.chosenList = new ArrayList<>(r.chosenList);
        this.remainList = new ArrayList<>(r.remainList);
        edges = new ArrayList<>(r.edges);
        this.graph = r.graph;
    }

    public double computeBound() {
        double l1 = length;
        double l2 = conputeShortestEdge();
        double l3 = computeMST();
        return l1 + l2 + l3;
    }
    
    /*
    public double computeBound() {
        double l1 = length;
        double l2 = conputeMinSum();
        return l1 + l2;
    }
    
    private double conputeMinSum() {
        double sum = 0;
        for(int i = 0; i < remainList.size(); i++) {
            int src = remainList.get(i).index;
            double minLength = Double.MAX_VALUE;
            for(int tgt = 0; tgt < graph.degree; tgt++) {
                if(src == tgt)
                    continue;
                double length = graph.edges.get(src).get(tgt).length;
                if(length < minLength)
                    minLength = length;
            }
            sum += minLength;
        }
        return sum;
    }
    */
    
    public double computeLength() {
        double length = 0;
        for (int i = 0; i < chosenList.size(); i++) {
            int src = chosenList.get(i).index;
            int tgt = chosenList.get((i + 1) % chosenList.size()).index;
            length += graph.edges.get(src).get(tgt).length;
        }
        return length;
    }

    private double conputeShortestEdge() {
        double shortestEdge = Double.MAX_VALUE;
        for (int i = 0; i < remainList.size(); i++) {
            int src1 = chosenList.get(0).index;
            int src2 = chosenList.get(chosenList.size() - 1).index;
            int tgt = remainList.get(i).index;
            double length1 = graph.edges.get(src1).get(tgt).length;
            double length2 = graph.edges.get(src2).get(tgt).length;
            if (length1 < shortestEdge) {
                shortestEdge = length1;
            }
            if (length2 < shortestEdge) {
                shortestEdge = length2;
            }
        }
        return shortestEdge;
    }

    private double computeMST() {
        Queue<Vertex> Q = new PriorityQueue<>();
        Set<Vertex> S = new HashSet<>();
        for (int i = 0; i < remainList.size(); i++) {
            remainList.get(i).priority = Integer.MAX_VALUE;
            remainList.get(i).priorityEdge = null;
            Q.add(remainList.get(i));
        }

        List<Edge> MSTedges = new ArrayList<>();
        while (!Q.isEmpty()) {
            Vertex u = Q.remove();
            S.add(u);

            if (u.priorityEdge != null) {
                Edge e = u.priorityEdge;
                MSTedges.add(e);
            }

            for (int i = 0; i < remainList.size(); i++) {
                Vertex v = remainList.get(i);
                if (v == u || S.contains(v)) {
                    continue;
                }

                Edge e = graph.edges.get(u.index).get(v.index);
                if (e.length < v.priority) {
                    v.priority = e.length;
                    v.priorityEdge = e;
                    Q.remove(v);
                    Q.add(v);
                }
            }
        }

        double lengthAll = 0;
        for (int i = 0; i < MSTedges.size(); i++) {
            lengthAll += MSTedges.get(i).length;
        }
        return lengthAll;
    }

    @Override
    public int compareTo(Object o) {
        Route temp = (Route)o;
        if(this.bound > temp.bound)
            return 1;
        else if(this.bound < temp.bound) 
            return -1;
        else
            return 0;
    }
}
