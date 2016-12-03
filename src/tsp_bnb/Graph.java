/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp_bnb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 天予
 */
public class Graph {

    public class Info {
        String name;
        String comment;
        String dimension;
        String edgeWeightType;
    }
    
    int degree;
    Info info = new Info();
    List<Vertex> vertices = new ArrayList<>();
    List<List<Edge>> edges = new ArrayList<>();

    public Graph(String file) {
        parseGraph(file);
        computeEdges();
    }
    
    private void parseGraph(String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] split;
            
            line = br.readLine();
            split = line.split(" ");
            info.name = split[1];
            line = br.readLine();
            split = line.split(" ");
            info.comment = split[1];            
            line = br.readLine();
            split = line.split(" ");
            info.dimension = split[1];            
            line = br.readLine();
            split = line.split(" ");
            info.edgeWeightType = split[1];
            line = br.readLine();
            
            degree = Integer.parseInt(info.dimension);  
            for(int i = 0; i < degree; i++) {
                line = br.readLine();
                split = line.split(" ");
                double x = Double.parseDouble(split[1]);
                double y = Double.parseDouble(split[2]);
                vertices.add(new Vertex(i, x, y));
            }
            
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void computeEdges() {
        for(int i = 0; i < degree; i++) {
            List<Edge> list = new ArrayList<>();
            edges.add(list);
            for(int j = 0; j < degree; j++) {
                Edge edge = new Edge(vertices.get(i), vertices.get(j));
                if(i == j)
                    edge.length = Double.POSITIVE_INFINITY;
                edges.get(i).add(edge);
            }   
        }
    }
}
