/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.action;

import java.util.ArrayList;
import shortestpathwithturnrestrictions.model.Cost;
import shortestpathwithturnrestrictions.model.GraphModel;
import shortestpathwithturnrestrictions.utility.QueueImplementation;

/**
 *
 * @author PprrATeekK
 */
public class BellmanFordShortestPath {

    GraphModel graph;
    Cost[][] matrix;
    int[] path;
    int noOfNodes;
    
    public BellmanFordShortestPath(GraphModel graph) {

        this.graph = graph;
        matrix = graph.getAdjMat();
        noOfNodes = graph.getNoOfNodes();
        path = new int[noOfNodes];
    }

    public int[] findShortestPath(int source, int destination) {


        /* use this function to get adjacency matrix, store it in some variable or
         use directly and calculate shortest path. Return it in the form of any data structure/object
         */

        ArrayList<Double> distance = new ArrayList<Double>();
        for (int j = 0; j < noOfNodes; j++) {
            distance.add(new Double(Double.MAX_VALUE));
            path[j]=-1;
        }
        QueueImplementation<Integer> que = new QueueImplementation<Integer>();
        que.enqueue(source);
        distance.set(source, 0.0);

        Integer k;
        double dist;
        while (que.size() != 0) {
            k = que.dequeue();
            for (int x = 0; x < graph.getNoOfNodes(); x++) {
                if (matrix[k][x] != null && matrix[k][x].getFizedCost() != 0) {
                    dist = distance.get(k).doubleValue() + matrix[k][x].getFizedCost();
                    if (distance.get(x).doubleValue() > dist) {
                        distance.set(x, dist);
                        path[x] = k;
                        if (!que.checkEntry(new Integer(x)))
                            que.enqueue(x);
                    }
                }
            }

        }

        return path;
    }
}
