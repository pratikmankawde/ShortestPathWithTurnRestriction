/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.utility;

import java.util.ArrayList;
import shortestpathwithturnrestrictions.model.NodeModel;
import shortestpathwithturnrestrictions.model.RoadFragment;

/**
 *
 * @author PprrATeekK
 */
public class CostCalculations {
    
    public void setCosts(ArrayList<RoadFragment> roads){
    
        
        double prevLat = roads.get(0).getPoints().get(0).getLatitude();
        double prevLon = roads.get(0).getPoints().get(0).getLongitude();
        
        for (RoadFragment road : roads ) {

            for( NodeModel node : road.getPoints() ){
               road.getCostOfThisRoadFragment().setFizedCost(
                       road.getCostOfThisRoadFragment().getFizedCost()
                       +Math.sqrt(Math.pow((node.getLatitude()-prevLat),2.0) 
                                + Math.pow((node.getLongitude()-prevLon),2.0)
                                  )
                       );
               prevLat = node.getLatitude();
               prevLon = node.getLongitude();
            }
          //  System.out.println(road.getCostOfThisRoadFragment().getFizedCost());
        }
    
    }
    
}
