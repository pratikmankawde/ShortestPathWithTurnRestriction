/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.model;

import java.util.ArrayList;
import java.util.Hashtable;
import shortestpathwithturnrestrictions.utility.CostCalculations;

/**
 *
 * @author PprrATeekK
 */
public class GraphModel {

    Cost[][] adjMat;
    int noOfNodes;
    CostCalculations costCalculator;
    Hashtable<Long, Integer> vertices;
    
    Hashtable<Integer, Turn> TurnRestrictions;
   
    public GraphModel(int noOfNodes ){

        this.noOfNodes = noOfNodes;
    }
    

    public Hashtable<Long, Integer> getVertices() {
        return vertices;
    }

    public void setVertices(Hashtable<Long, Integer> vertices) {
        this.vertices = vertices;
    }

    public Cost[][] fillAdjMat(ArrayList<RoadFragment> edges) {

        int count = 0, indexi, indexj;
        vertices = new Hashtable<Long, Integer>();
        NodeModel node1, node2;
        this.adjMat = new Cost[noOfNodes][noOfNodes];
        
        for (int i = 0; i < edges.size(); i++) {
            //   System.out.println("Road"+i+"contains: "+edges.get(i).getPoints().size());
            for (int j = 0; j < edges.get(i).getPoints().size() - 1; j++) {

                node1 = edges.get(i).getPoints().get(j);
                node2 = edges.get(i).getPoints().get(j + 1);

                //      System.out.println("RoadFrag\t"+node1.getId()+"\t"+node2.getId());

                if (vertices.containsKey(node1.getId())) {
                    indexi = vertices.get(node1.getId());
                } else {
                    vertices.put(node1.getId(), count);
                    indexi = count++;
                }
                if (vertices.containsKey(node2.getId())) {
                    indexj = vertices.get(node2.getId());
                } else {
                    vertices.put(node2.getId(), count);
                    indexj = count++;
                }
                //     System.out.println(indexi +" "+ indexj);

                setCostAtIndices(indexi, indexj, node1, node2);

            }
        }

        vertices.clear();

      //  printCostMatrix();

        return adjMat;

    }

    public boolean setCostAtIndices(int indexi, int indexj, NodeModel node1, NodeModel node2) {
        boolean status = false;

        adjMat[indexi][indexj] = new Cost(0.0f);
        adjMat[indexj][indexi] = new Cost(0.0f);
        
        double diff = node2.getCostObj().getFizedCost() - node1.getCostObj().getFizedCost();
        
        if(diff>0)
        diff*= Constants.scaler;
        else
        diff = diff * (-1)* Constants.scaler;
        
        adjMat[indexi][indexj].setFizedCost((float)diff);
        adjMat[indexj][indexi].setFizedCost((float)adjMat[indexi][indexj].getFizedCost());

        adjMat[indexi][indexi] = new Cost(0.0f);
        adjMat[indexj][indexj] = new Cost(0.0f);
        adjMat[indexi][indexi].setFizedCost(0.0f);
        adjMat[indexj][indexj].setFizedCost(0.0f);

        return status;
    }

    public void printCostMatrix() {

        //Print cost matrix
        System.out.print("\t");
        int count = 0;
        for (int j = 0; j < noOfNodes; j++) {
            System.out.print(j + "\t");
        }
        System.out.println("Junction");
        for (int i = 0; i < noOfNodes; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < noOfNodes; j++) {
                if (adjMat[i][j] != null) {
                    System.out.print((int) (adjMat[i][j].getFizedCost())+":"+adjMat[i][j].getVia() + "\t");
                    count++;
                } else {
                    System.out.print("*\t");
                }

            }
            if (count > 2) {
                System.out.print("\t:" + count);
            }
            System.out.println();
            count = 0;
        }
        //   System.out.println(count);



    }

    public Hashtable<Integer, Turn> getTurnRestrictions() {
        return TurnRestrictions;
    }

    public void setTurnRestrictions(Hashtable<Integer, Turn> TurnRestrictions) {
        this.TurnRestrictions = TurnRestrictions;
        this.TurnRestrictions.put(0, new Turn(5,1,null)); //5->1
        this.TurnRestrictions.put(42, new Turn(56,43,null)); //5->15
    }

    public Cost[][] getAdjMat() {
        return adjMat;
    }

    public int getNoOfNodes() {
        return noOfNodes;
    }

    public void setNoOfNodes(int noOfNodes) {
        this.noOfNodes = noOfNodes;
    }

    public CostCalculations getCostCalculator() {
        return costCalculator;
    }

    public void setCostCalculator(CostCalculations costCalculator) {
        this.costCalculator = costCalculator;
    }
}
