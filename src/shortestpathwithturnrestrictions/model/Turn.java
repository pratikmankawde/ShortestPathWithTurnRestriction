/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.model;

/*
 * @author PprrATeekK
 */
public class Turn {
 
    RoadFragment incomingRoadFragment;
    RoadFragment outgoingRoadFragment;
    
    NodeModel incomingNode;
    NodeModel outgoingNode;
    
    int incomingVertex;
    int outgoingVertex;
    
    Cost turnCostObj;

    public Turn(int incomingVertex, int outgoingVertex, Cost turnCost) {
        this.incomingVertex = incomingVertex;
        this.outgoingVertex = outgoingVertex;
        this.turnCostObj = turnCost;
    }

    public int getIncomingVertex() {
        return incomingVertex;
    }

    public void setIncomingVertex(int incomingVertex) {
        this.incomingVertex = incomingVertex;
    }

    public int getOutgoingVertex() {
        return outgoingVertex;
    }

    public void setOutgoingVertex(int outgoingVertex) {
        this.outgoingVertex = outgoingVertex;
    }
    
    
   
    
}
