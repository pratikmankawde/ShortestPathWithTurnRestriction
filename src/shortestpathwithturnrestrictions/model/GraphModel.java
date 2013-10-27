/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.model;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author PprrATeekK
 */
public class GraphModel {
    
    Cost[][] adjMat;
    int noOfNodes;
    Hashtable<Long, Integer> vertices =  new Hashtable<>();
    
    
    
    public Cost[][] getAdjMat() {
        return adjMat;
    }

    public void initAdjMat(int noOfNodes) {
        this.adjMat =  new Cost[noOfNodes][noOfNodes];
        this.noOfNodes = noOfNodes;
//      System.out.println(noOfNodes);
    }    
    
    public Cost[][] fillAdjMat(ArrayList<RoadFragment> edges) {
        
        int count = 0, indexi, indexj;
        
        NodeModel node1,node2;
        
        for(int i=0;i<edges.size();i++){
         //   System.out.println("Road"+i+"contains: "+edges.get(i).getPoints().size());
            for(int j=0;j<edges.get(i).getPoints().size()-1;j++){
                
                node1 = edges.get(i).getPoints().get(j);
                node2 = edges.get(i).getPoints().get(j+1);
                
          //      System.out.println("RoadFrag\t"+node1.getId()+"\t"+node2.getId());
                
            if(vertices.containsKey(node1.getId()))
                    indexi = vertices.get(node1.getId());
            else{
                vertices.put(node1.getId(), count);
                indexi = count++;
            }
            if(vertices.containsKey(node2.getId()))
                    indexj = vertices.get(node2.getId());
            else{
                vertices.put(node2.getId(), count);
                indexj = count++;
            }
           //     System.out.println(indexi +" "+ indexj);
            adjMat[indexi][indexj]=new Cost();
            adjMat[indexj][indexi]=new Cost();
            adjMat[indexi][indexj].setFizedCost( Math.sqrt(Math.pow((node1.getLatitude()- node2.getLatitude()),2.0) 
                                                         + Math.pow((node1.getLongitude()-node2.getLongitude()),2.0)));
            adjMat[indexj][indexi].setFizedCost(adjMat[indexi][indexj].getFizedCost());
            }
        }
        /*
        
        System.out.print("  ");
        count = 0;
        for(int j=0;j<noOfNodes;j++){
                 System.out.print(j+"\t");
            }
        System.out.println("Junction");
        for(int i=0;i<noOfNodes;i++){
            System.out.print(i+" ");
            for(int j=0;j<noOfNodes;j++){
                if(adjMat[i][j]!=null){
                System.out.print((int)(adjMat[i][j].getFizedCost()*10000)+"\t");
                count++;
                }
                else
                    System.out.print("*\t");
                
            }
        if(count>2)
        System.out.print("\t:"+count);
        System.out.println();
        count=0;
        }
        System.out.println(count);
         */
        
        return adjMat;
        
    }
    
}
