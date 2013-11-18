/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.action;

import java.util.Date;
import java.util.PriorityQueue;
import shortestpathwithturnrestrictions.model.Cost;
import shortestpathwithturnrestrictions.model.GraphModel;

/**
 *
 * @author PprrATeekK
 */
public class Dijkstra {

    GraphModel graph;
    Cost[][] matrix;
    String[] pathStr = null;
    Cost[] minCosts;
    int noOfNodes;
    int[] updatables;
    double totalCost = 0.0;

    public Dijkstra(GraphModel graph) {
        this.graph = graph;
        matrix = graph.getAdjMat();
        noOfNodes = graph.getNoOfNodes();
        updatables = new int[noOfNodes];
        minCosts = new Cost[noOfNodes];
    }

    public String[] findShortestPath(int source, int destination) {

        /* use this function to get adjacency matrix, store it in some variable or
         use directly and calculate shortest path. Return it in the form of any data structure/object
         */
        System.out.println(new Date().toString());
        pathStr = new String[noOfNodes];

        for (int j = 0; j < noOfNodes; j++) {
            pathStr[j] = "";
            minCosts[j] = new Cost(Double.POSITIVE_INFINITY);
        }


        minCosts[source].setFizedCost(0.0);
        PriorityQueue<vertex> vertexQueue = new PriorityQueue<vertex>();
        vertexQueue.add(new vertex(source, 0.0));

        while (!vertexQueue.isEmpty()) {
            vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (int i = 0; i < noOfNodes; i++) {
                if (matrix[u.id][i] != null) {
                    double weight = matrix[u.id][i].getFizedCost();
                    double distanceThroughU = minCosts[u.id].getFizedCost() + weight;
                    if (distanceThroughU < minCosts[i].getFizedCost()) {
                        vertexQueue.remove(new vertex(i, minCosts[i].getFizedCost()));
                        minCosts[i].setFizedCost(distanceThroughU);
                        minCosts[i].setVia(u.id);
                        vertexQueue.add(new vertex(i, minCosts[i].getFizedCost()));
                    }
                }
            }

        }
        setPathStr(source, destination);
        //   System.out.println("path calculated");
        //  graph.printCostMatrix();
        System.out.println(new Date().toString());
        return pathStr;


    }

    public String[] getPathStr(int source, int destination) {

        return pathStr;
    }

    public void setPathStr(int source, int destination) {

        int i = destination;
        while (i != source && i!=-1 ) {
            pathStr[i] = ";" + minCosts[i].getVia();
            i = minCosts[i].getVia();
        }
    }

    class vertex implements Comparable<vertex> {

        int id;
        double minCost = Double.POSITIVE_INFINITY;

        public vertex(int id, double minCost) {
            this.id = id;
            this.minCost = minCost;
        }

        @Override
        public int compareTo(vertex o) {
            return Double.compare(minCost, o.minCost);
        }
    }
}
