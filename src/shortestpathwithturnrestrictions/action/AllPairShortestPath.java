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
public class AllPairShortestPath {

    GraphModel graph;
    Cost[][] matrix;
    String[] pathStr=null;
    int noOfNodes;
    int[] updatables;

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
                    if(matrix[i][k] == null )
                        matrix[i][k] = new Cost(Double.MAX_VALUE);
                    if(matrix[k][j] == null )
                        matrix[k][j] = new Cost(Double.MAX_VALUE);
                    if(matrix[i][j] == null )
                        matrix[i][j] = new Cost(Double.MAX_VALUE);
                    
                    
                    
                    if (matrix[i][k].getFizedCost() + matrix[k][j].getFizedCost() < matrix[i][j].getFizedCost()) {
                        matrix[i][j].setFizedCost(matrix[i][k].getFizedCost() + matrix[k][j].getFizedCost());
                        matrix[i][j].setVia(k);
                    //    System.out.println("source:"+i+" via:"+k+" destination:"+j);
                    }
                }
            }

        }

        getIntermediatVertex(source, matrix[source][destination].getVia(), destination);

     //   System.out.println("path calculated");

        return pathStr;

    }

    public String[] getPathStr(int source, int destination) {
        if(pathStr==null)            
        return findShortestPath(source, destination);
        else{
            for (int j = 0; j < noOfNodes; j++) {
            pathStr[j] = "";
            }
            getIntermediatVertex(source, matrix[source][destination].getVia(), destination);
            return pathStr;
        }
    }

    public void setPathStr(String[] pathStr) {
        this.pathStr = pathStr;
    }
    
    
    
    
    
    public void getIntermediatVertex(int source, int via, int destination){
    
        if(source == via || destination == via || via == -1 ){
            
       //     if(matrix[source][destination].getFizedCost()< Double.MAX_VALUE)
            pathStr[destination] = ";"+source;
        //    System.out.println("updated");
        return ;
        }
//        if(via == -1)
//            return;
//       
    //    System.out.println("source:"+source+" via:"+via+" destination:"+destination);
      getIntermediatVertex(source,matrix[source][via].getVia(),via);
        
      getIntermediatVertex(via,matrix[via][destination].getVia(),destination);
      
    }
    
}