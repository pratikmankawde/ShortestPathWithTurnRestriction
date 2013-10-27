/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.action;

import shortestpathwithturnrestrictions.model.GraphModel;

/**
 *
 * @author PprrATeekK
 */
public class BellmanFordShortestPath {
    
    GraphModel graph;

    
    public BellmanFordShortestPath(GraphModel graph){
    
        this.graph = graph;
    }
    
    
    public void findShortestPath(int source, int destination){
    
        
      /*   use this function to get adjacency matrix, store it in some variable or
           use directly and calculate shortest path. Return it in the form of any data structure/object
      */
        graph.getAdjMat();
        
    
    }   
    
    
}
