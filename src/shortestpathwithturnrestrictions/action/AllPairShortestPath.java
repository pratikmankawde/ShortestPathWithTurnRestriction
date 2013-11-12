/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.action;

import java.util.Date;
import shortestpathwithturnrestrictions.model.Cost;
import shortestpathwithturnrestrictions.model.GraphModel;

/*
 * @author PprrATeekK
 */
public class AllPairShortestPath {

    GraphModel graph;
    Cost[][] matrix;
    String[] pathStr = null;
    int noOfNodes;
    int[] updatables;
    double totalCost=0.0;
    public AllPairShortestPath(GraphModel graph) {
        this.graph = graph;
        matrix = graph.getAdjMat();
        noOfNodes = graph.getNoOfNodes();
        updatables = new int[noOfNodes];
    }

    public String[] findShortestPath(int source, int destination) {

        /* use this function to get adjacency matrix, store it in some variable or
         use directly and calculate shortest path. Return it in the form of any data structure/object
         */
        System.out.println(new Date().toString());
        pathStr = new String[noOfNodes];

        for (int j = 0; j < noOfNodes; j++) {
            pathStr[j] = "";
        }

        for (int k = 0; k < graph.getNoOfNodes(); k++) {
            for (int i = 0; i < graph.getNoOfNodes(); i++) {
                for (int j = 0; j < graph.getNoOfNodes(); j++) {
                    if (graph.getTurnRestrictions().containsKey(k) && graph.getTurnRestrictions().get(k).getIncomingVertex() == i && graph.getTurnRestrictions().get(k).getOutgoingVertex() == j) {
                        continue;
                    }
                    if (matrix[i][k] == null) {
                        matrix[i][k] = new Cost(Double.MAX_VALUE);
                  //      matrix[i][k].setVia(i);
                    }
                    if (matrix[k][j] == null) {
                        matrix[k][j] = new Cost(Double.MAX_VALUE);
                  //      matrix[k][j].setVia(k);
                    }
                    if (matrix[i][j] == null) {
                        matrix[i][j] = new Cost(Double.MAX_VALUE);
                   //     matrix[i][j].setVia(i);
                    }

                    if(matrix[i][j].getVia()==-1 && matrix[i][j].getFizedCost()< Double.MAX_VALUE){
                    if(i==j)
                        matrix[i][j].setVia(i);
                    if(i==k || j==k)
                        matrix[i][j].setVia(k);
                    }
                        if (matrix[i][k].getFizedCost() + matrix[k][j].getFizedCost() < matrix[i][j].getFizedCost()) {
                        matrix[i][j].setFizedCost(matrix[i][k].getFizedCost() + matrix[k][j].getFizedCost());
                        matrix[i][j].setVia(k);
                        //    System.out.println("algo:source:"+i+" via:"+k+" destination:"+j);
                    }
                }
            }

        }


        //   System.out.println("path calculated");
      //  graph.printCostMatrix();
        System.out.println(new Date().toString());
        return pathStr;

    }

    public String[] getPathStr(int source, int destination) {
        
        totalCost = 0.0;
        
        if (pathStr == null) {
            findShortestPath(source, destination);
            System.gc();
            getIntermediatVertex(source, matrix[source][destination].getVia(), destination);
            System.out.println("Total Cost"+totalCost);
            return pathStr;
        } else {
            for (int j = 0; j < noOfNodes; j++) {
                pathStr[j] = "";
            }
            getIntermediatVertex(source, matrix[source][destination].getVia(), destination);
            System.out.println("Total Cost"+totalCost);
            return pathStr;
        }
    }

    public void setPathStr(String[] pathStr) {
        this.pathStr = pathStr;
    }

    public void getIntermediatVertex(int source, int via, int destination) {

        if (source == via || destination == via ) {

            //     if(matrix[source][destination].getFizedCost()< Double.MAX_VALUE)
            pathStr[destination] = ";" + source;
            totalCost+=matrix[source][destination].getFizedCost();
            //    System.out.println("updated");
            return;
        }
        if(via == -1)
            return;
//       
        //   System.out.println("source:"+source+" via:"+via+" destination:"+destination);
        getIntermediatVertex(source, matrix[source][via].getVia(), via);

        getIntermediatVertex(via, matrix[via][destination].getVia(), destination);

    }

    public void getIntermediatVertexOLD(int source, int via, int destination) {

        if(via==-1)
            return;
        
    }
}