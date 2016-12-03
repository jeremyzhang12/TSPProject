/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_bnb;

/**
 *
 * @author 天予
 */
public class Edge {
    Vertex v1;
    Vertex v2;
    double length;

    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
        double xDiff = v1.x - v2.x;
        double yDiff = v1.y - v2.y;
        length = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }
        
    public Vertex neighbor(Vertex v) {
        if(v1 == v)
            return v2;
        else if(v2 == v)
            return v1;
        return null;
    }
}