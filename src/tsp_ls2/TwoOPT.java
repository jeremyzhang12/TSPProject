package tsp_ls2;

import java.io.*;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by XinZhang on 11/13/16.
 */
public class TwoOPT {
    public static void main(String[] args) throws IOException{
        int timecut = Integer.parseInt(args[2]);
        int seed = Integer.parseInt(args[3]);
        String filename = args[0];

        BufferedReader in = new BufferedReader(new FileReader(filename));
        String[] first = in.readLine().split(" ");
        String city = first[1];
        // define .sol output file name
        String fileoutSol = city + "_" + args[1] + "_" + args[2] + "_" + seed + ".sol";
        // define .trace output file name
        String fileoutTrace = city + "_" + args[1] + "_" + args[2] + "_" + seed + ".trace";
        String line = "";
        BufferedWriter out = new BufferedWriter(new FileWriter(fileoutSol));

        for(int ss = 0; ss < 5; ss++) {
            line = in.readLine();
        }
        ArrayList<Node> nlist = new ArrayList<Node>();
        String[] infolist;
        int label;
        double x;
        double y;
        Node node;
        String endrefernce = "EOF";
        /**
         * Create node list;
         */
        while(!line.equals(endrefernce)) {
            infolist = line.split(" ");
            //System.out.print(infolist[0]);
            label = Integer.parseInt(infolist[0]) -1;
            x = Double.parseDouble(infolist[1]);
            y = Double.parseDouble(infolist[2]);

            node = new Node(x,y,label);
            nlist.add(node);
            line = in.readLine();
            //System.out.print(line);
        }

        /**
         * Create distance map
         */

        int totalp = nlist.size();
        Double[][] dismap = new Double[totalp][totalp];

        double longestdis = 0;
        int longi = -1;
        int longj = -1;
        for(int i = 0; i < totalp; i++) {
            for(int j = 0; j < totalp; j++) {
                if(i == j) {
                    dismap[i][j] = 0.0;
                } else {
                    Node nodei = nlist.get(i);
                    Node nodej = nlist.get(j);
                    double dis = Math.sqrt(Math.pow(nodei.getX() - nodej.getX(),2) + Math.pow(nodei.getY() - nodej.getY(),2));
                    dismap[i][j] = dis;
                    dismap[j][i] = dis;
                    if(dis > longestdis) {
                        longi = i;
                        longj = j;
                    }
                }
            }

        }

        //System.out.println("A."+dismap[96][90] + dismap[88][10]);
        //System.out.println("B."+dismap[96][88] + dismap[90][10]);
//        for(int i = 0; i < totalp;i++){
//            System.out.println(i + " "+dismap[96][i]);
//        }
        double totalTime = 0.0;
        long startTime = System.nanoTime();
        /**
         * Furtherest Insertion
         */

        ArrayList<Integer> tours = new ArrayList<>();
        tours.add(longi);
        tours.add(longj);
        int p;

        while(tours.size() < totalp) {
            p = findnode(tours,totalp,dismap);
            //System.out.print(p+",");

            tours = insertTour(tours,p,dismap);

        }
        //System.out.println();
        tours.add(tours.get(0));
//        int[] reference = new int[totalp];



        /**
         * Set up reference
         */
        //for(int i = 0; i < totalp; i++) {
        //    if(i < )
        //}

        /**
         * Use normal order
         */
        // for(int i = 0; i < totalp; i++){
       //      tours.add(i);}
      //  tours.add(0);

//        for(int i:tours) {
//            System.out.print(i+",");
//        }

//        System.out.println();
//        System.out.println(tours.size());
//        System.out.println(totalp);
//        for(int i = 0; i < totalp; i++) {
//            if(!tours.contains(i)){
//                System.out.println(i);
//            }
//        }
/**
 * 2-opt exchange
 */

        //System.out.print("go to 2-opt");
         Boolean flag = true;
         totalTime = 1.0 * (System.nanoTime() - startTime) / 1000000000;
         while(flag && totalTime <= timecut) {
             //System.out.print("111111111");
             flag = false;
             double min = -0.0000001;
             outloop:
             for(int i = 0; i < totalp - 2; i++) {
                 for(int j = i + 2; j < totalp - 1; j++){
                     int node1 = tours.get(i);
                     int node2 = tours.get(i+1);
                     int node3 = tours.get(j);
                     int node4 = tours.get(j + 1);
                     double diff_value = 0.0;
                     diff_value = dismap[node1][node3] + dismap[node2][node4] - dismap[node1][node2] - dismap[node3][node4];
                     if(diff_value < min) {
                         //System.out.println(diff_value);
                         //System.out.println(min);
                         //System.out.println(""+tours.get(i)+" "+tours.get(i+1));
                         //System.out.println("" + tours.get(j) + " " + tours.get(j+1));
                         tours = swap(tours,i,j);
                         //System.out.println(""+tours.get(i)+" "+tours.get(i+1));
                         //System.out.println("-----------");
                         flag = true;
                         break outloop;

                     }
                 }
             }
             totalTime = 1.0 * (System.nanoTime() - startTime) / 1000000000;
         }
         System.out.print(totalTime);

         double totalcount = 0;
         ArrayList<String> alist = new ArrayList<>();
         for(int index = 0; index < tours.size()-1; index++) {
             int l1 = tours.get(index);
             int l2 = tours.get(index + 1);
             long d = java.lang.Math.round(dismap[l1][l2]);
             String s = l1 + " " + l2 + " " + d;
             totalcount = totalcount + dismap[l1][l2];
             alist.add(s);
         }
         out.write(""+java.lang.Math.round(totalcount));
         out.newLine();
         for(int index = 0; index < alist.size(); index++){
             out.write(alist.get(index));
             out.newLine();

         }


        in.close();
        out.close();

    }

    public static ArrayList<Integer> swap(ArrayList<Integer> tour, int i, int j) {
        ArrayList<Integer> newtour = new ArrayList<>();
        for(int index = 0; index <= i; index++ ) {
            newtour.add(tour.get(index));
        }

        for(int index = j; index >= i + 1;index--) {
            newtour.add(tour.get(index));
        }

        for(int index = j+1; index < tour.size(); index++){
            newtour.add(tour.get(index));
        }

        return newtour;
    }

    public static int findnode(ArrayList<Integer> exist, int n, Double[][] dismap) {
        int nodelabel = 0;
        double value = -1.0;
        for(int index = 0; index < n; index++){
            double c_value = Double.MAX_VALUE;
            if(exist.contains(index)) {
                continue;
            } else {
                for(int enode:exist) {
                    double dis = dismap[enode][index];
                    if(dis < c_value) {
                        c_value = dis;
                    }
                }
                if(c_value > value) {
                    value = c_value;
                    nodelabel = index;
                }

            }

        }
        return nodelabel;

    }

    public static ArrayList<Integer> insertTour(ArrayList<Integer> tour, int label, Double[][] dismap) {
        double cost = Double.MAX_VALUE;
        int n1 = -1;
        int n2 = -1;
        int postion_b = 0;
        double c_cost;
        for(int index = 0; index < tour.size()-1;index++) {
            int first = tour.get(index);
            int second = tour.get(index + 1);
            c_cost = dismap[label][first] + dismap[label][second] - dismap[first][second];
            if(c_cost < cost) {
                postion_b = index;
                n1 = first;
                n2 = second;
                cost = c_cost;
            }
        }
        ArrayList<Integer> copy = new ArrayList<>();
        for(int c = 0; c < postion_b; c++) {
            copy.add(tour.get(c));

        }
        copy.add(label);
        for(int c = postion_b; c < tour.size();c++) {
            copy.add(tour.get(c));
        }
        return copy;


    }



}
