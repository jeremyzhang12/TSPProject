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
public class Vertex implements Comparable {
    int index;
    double x;
    double y;
    
    double priority;    
    Edge priorityEdge;

    public Vertex(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Object o) {
        Vertex temp = (Vertex)o;
        if(this.priority > temp.priority)
            return 1;
        else if(this.priority < temp.priority) 
            return -1;
        else
            return 0;
    }
}
