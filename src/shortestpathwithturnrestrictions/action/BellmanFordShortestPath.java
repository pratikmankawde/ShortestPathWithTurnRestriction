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

    public BellmanFordShortestPath(GraphModel graph) {

        this.graph = graph;

    }

    public int[] findShortestPath(int source, int destination) {


        /*   use this function to get adjacency matrix, store it in some variable or
         use directly and calculate shortest path. Return it in the form of any data structure/object
         */

        Cost[][] matrix = graph.getAdjMat();
        int n = graph.getNoOfNodes();
        int[] path = new int[n];
        ArrayList<Integer> distance = new ArrayList<Integer>();
        for (int j = 0; j < n; j++) {
            Integer i = new Integer(Integer.MAX_VALUE);
            distance.add(i);
        }
        QueueImplementation<Integer> que = new QueueImplementation<Integer>();
        que.enqueue(source);
        distance.set(source, 0);
        
        Integer k;
        int dis ;
        while (que.size() != 0) {
            k = que.dequeue();
            for (int x = 0; x < graph.getNoOfNodes(); x++) {
                if (matrix[k][x]!= null && matrix[k][x].getFizedCost() != 0) {
                    dis = distance.get(k).intValue() + (int) matrix[k][x].getFizedCost();
                    if (distance.get(x).intValue() > dis) {
                        distance.set(x, dis);
                        
                        path[x] = k;
                        if (que.checkEntry(new Integer(x))) {
                        que.enqueue(x);
                        }
                    }
                }
            }

        }
        
        return path;
    }
}
