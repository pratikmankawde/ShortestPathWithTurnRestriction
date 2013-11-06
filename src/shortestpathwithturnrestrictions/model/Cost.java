/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.model;

/**
 *
 * @author PprrATeekK
 */
public class Cost {
    
    private double fizedCost=0.0;
 //   private double variableCost=0.0;
//    private boolean restricted = false;
    private int via=-1;
    
    
    
    
    public Cost(double fixedCost){
    
        this.fizedCost = fixedCost;
    
    }
    
    public Cost(double fixedCost, double variableCost, boolean restricted){
    
    
    }
    
    public double getFizedCost() {
        return fizedCost;
    }

    public void setFizedCost(double fizedCost) {
        this.fizedCost = fizedCost;
    }
/*
 * 
 
    public double getVariableCost() {
        return variableCost;
    }

    public void setVariableCost(double variableCost) {
        this.variableCost = variableCost;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }
     
    */

    public int getVia() {
        return via;
    }

    public void setVia(int via) {
        this.via = via;
    }
    
}
