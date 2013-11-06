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
    String[] pathStr;
    int noOfNodes;
    int[] updatables;

    public BellmanFordShortestPath(GraphModel graph) {
        this.graph = graph;
        matrix = graph.getAdjMat();
        noOfNodes = graph.getNoOfNodes();
        pathStr = new String[noOfNodes];
        updatables = new int[noOfNodes];
    }

    public String[] findShortestPath(int source, int destination) {

        /* use this function to get adjacency matrix, store it in some variable or
         use directly and calculate shortest path. Return it in the form of any data structure/object
         */

        ArrayList<Double> distance = new ArrayList<Double>();
        for (int j = 0; j < noOfNodes; j++) {
            distance.add(new Double(Double.MAX_VALUE));
            pathStr[j] = "";
        }
        QueueImplementation<Integer> que = new QueueImplementation<Integer>();
        que.enqueue(source);
        distance.set(source, 0.0);

        Integer k;
        int skipVertex = -1;
        double dist;
        while (que.size() != 0) {
            k = que.dequeue();

            
            if (graph.getTurnRestrictions().containsKey(k) && graph.getTurnRestrictions().get(k).getIncomingVertex() == Integer.parseInt(pathStr[k].substring(pathStr[k].lastIndexOf(";")+1))) {
                skipVertex = graph.getTurnRestrictions().get(k).getOutgoingVertex();
                updatables[k] = 1;
            }

            for (int x = 0; x < graph.getNoOfNodes(); x++) {
                if (x != skipVertex && matrix[k][x] != null && matrix[k][x].getFizedCost() != 0) {
                       System.out.println("Processing:"+k+"->"+x);
                    dist = distance.get(k).doubleValue() + matrix[k][x].getFizedCost();
                    if (distance.get(x).doubleValue() > dist ) {
                        distance.set(x, dist);
                        //    path[x] = k;
                        if(pathStr[x].length()>0)
                        pathStr[x] = pathStr[x].substring(0,pathStr[x].lastIndexOf(";"))+";" + k;
                        else
                        pathStr[x] = ";" + k;
                        updatables[x] = 0;
                        if (!que.checkEntry(new Integer(x))) {
                            que.enqueue(x);
                        }
                                 System.out.println("Shorter path found, Updated:"+k+"->"+x);
                    }
                    else if (updatables[x] == 1) {
                        distance.set(x, dist);
                        //    path[x] = k;
                        pathStr[x] += ";" + k;
                        updatables[x] = 0;
                        if (!que.checkEntry(new Integer(x))) {
                            que.enqueue(x);
                        }
                                 System.out.println("Another option found, Updated:"+k+"->"+x);
                    }
                }
            }
            skipVertex = -1;

        }
        System.out.println("path calculated");

        return pathStr;
    }
}
