/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.model;

import java.util.ArrayList;

/**
 *
 * @author PprrATeekK
 */
public class RoadFragment {
    
    ArrayList<NodeModel> points =  new ArrayList<NodeModel>();
    int[] x;
    int[] y;
    
    double lenght;
    double width;
    long id;
    Cost costOfThisRoadFragment = new Cost();

    public Cost getCostOfThisRoadFragment() {
        return costOfThisRoadFragment;
    }

    public void setCostOfThisRoadFragment(Cost costOfThisRoadFragment) {
        this.costOfThisRoadFragment = costOfThisRoadFragment;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    public void initArrays(int size){
        x = new int[size];
        y = new int[size];
    }
    
    public void fillArrays(int xVal, int yVal, int index){
        x[index] = xVal;
        y[index] = yVal;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }
    
    
    
    public ArrayList<NodeModel> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<NodeModel> points) {
        this.points = points;
    }    

    public double getLenght() {
        return lenght;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    
    
    
}
