/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpathwithturnrestrictions.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import shortestpathwithturnrestrictions.model.GraphModel;
import shortestpathwithturnrestrictions.model.Turn;
import shortestpathwithturnrestrictions.utility.CostCalculations;
import shortestpathwithturnrestrictions.utility.MySAXParser;
import shortestpathwithturnrestrictions.view.DrawMap;

/**
 *
 * @author PprrATeekK
 */
public class ProcessData {

    private File mapDataFile;
    private MySAXParser fileLoader;
    private GraphModel gModel;
    private int noOfNodes;

    public ProcessData(File mapDataFile) {

        this.mapDataFile = mapDataFile;

    }

    public void initDataModels() {

        try {
            fileLoader = new MySAXParser(new FileInputStream(mapDataFile));
            new CostCalculations().setCosts(fileLoader.getRoads());     // Find cost of road fragments
            fileLoader.removeRedundantVertises(); //Remove all vertices except junctions and end points
            fileLoader.setIntRoadCoord();   // initialize integer vertices to draw maps
            noOfNodes = fileLoader.getNodes().size();
            fileLoader.getNodes().clear();

            gModel = new GraphModel(noOfNodes);
            gModel.fillAdjMat(fileLoader.getRoads());
            gModel.setTurnRestrictions(new Hashtable<Integer, Turn>());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void drawMap() {

        DrawMap draw = new DrawMap(fileLoader.getRoads(), this);    //
        draw.draw();    // Draw map

    }

    public String[] calculateShortestPath(int source, int destination) {


     //   BellmanFordShortestPath bfsp = new BellmanFordShortestPath(gModel);
        AllPairShortestPath apsp = new AllPairShortestPath(gModel);
       // int[] shortestPath = bfsp.findShortestPath(source, destination);
      //  String[] shortestPathStr = bfsp.findShortestPath(source, destination);
//        int i = destination;
//        System.out.print(gModel.getNoOfNodes() + ":" + i + "->");
//        while (i != source && shortestPath[i]!=-1 ) {
//            System.out.print(shortestPath[i] + "->");
//            i = shortestPath[i];
//        }
        
        
        return apsp.getPathStr(source, destination);
       // return bfsp.findShortestPath(source, destination);
    }
}
