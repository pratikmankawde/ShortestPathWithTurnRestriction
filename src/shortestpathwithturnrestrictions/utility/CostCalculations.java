/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.utility;

import java.util.ArrayList;
import shortestpathwithturnrestrictions.model.Constants;
import shortestpathwithturnrestrictions.model.Cost;
import shortestpathwithturnrestrictions.model.NodeModel;
import shortestpathwithturnrestrictions.model.RoadFragment;

/**
 *
 * @author PprrATeekK
 */
public class CostCalculations {

    public void setCosts(ArrayList<RoadFragment> roads) {

        double prevLat;
        double prevLon;

        NodeModel node;
        for (int i=0;i<roads.size();i++) {
            node = roads.get(i).getPoints().get(0);
            prevLat = node.getLatitude();
            prevLon = node.getLongitude();
            node.setCostObj(new Cost(0.0));
            for (int j=1;j<roads.get(i).getPoints().size();j++) {
                node = roads.get(i).getPoints().get(j);
                node.setCostObj(new Cost(roads.get(i).getPoints().get(j-1).getCostObj().getFizedCost()+
                                         getDistanceFromLatLonInKm(node.getLatitude(), node.getLongitude(), prevLat, prevLon)));
                prevLat = node.getLatitude();
                prevLon = node.getLongitude();
            }
            roads.get(i).getCostObj().setFizedCost(node.getCostObj().getFizedCost());
            //  System.out.println(road.getCostOfThisRoadFragment().getFizedCost());
        }

    }

    public double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the earth in km
        double differenceInLatitude = Math.toRadians(lat2 - lat1);
        double differenceInLongitude = Math.toRadians(lon2 - lon1);
        double a = Math.sin(differenceInLatitude / 2) * Math.sin(differenceInLatitude / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(differenceInLongitude / 2) * Math.sin(differenceInLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = Constants.radiusOfEarth * c; // Distance in km
        return distance;
    }

    public double getCostBetweenPoints(NodeModel node1, NodeModel node2) {

        //    return Math.sqrt(Math.pow((node1.getLatitude() - node2.getLatitude()), 2.0)
        //           + Math.pow((node1.getLongitude() - node2.getLongitude()), 2.0));


        return getDistanceFromLatLonInKm(node1.getLatitude(), node1.getLongitude(), node2.getLatitude(), node2.getLongitude());

    }
}
