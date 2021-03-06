/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *@author PprrATeekK
 */
package shortestpathwithturnrestrictions.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import shortestpathwithturnrestrictions.action.ProcessData;
import shortestpathwithturnrestrictions.model.GraphModel;
import shortestpathwithturnrestrictions.model.NodeModel;
import shortestpathwithturnrestrictions.model.RoadFragment;
import shortestpathwithturnrestrictions.view.DrawMap;

public class MySAXParser extends DefaultHandler {

    Hashtable<Long, NodeModel> nodes = new Hashtable();
    ArrayList<RoadFragment> roads = new ArrayList<>();
    RoadFragment road;
    NodeModel node;
    double minLat, minLon;
    
    public MySAXParser(InputStream XmlStream) {

        parseDocument(XmlStream);

    }

    private void parseDocument(InputStream XmlStream) {
        // parse
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(XmlStream, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    @Override
    public void startElement(String uri, String localName, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("node")) {

            node = new NodeModel();

            node.setId(Long.parseLong(attributes.getValue(uri, "id")));
            node.setLatitude(Double.parseDouble(attributes.getValue(uri, "lat")));
            node.setLongitude(Double.parseDouble(attributes.getValue(uri, "lon")));

            nodes.put(Long.valueOf(node.getId()), node);

        }

        if (elementName.equalsIgnoreCase("way")) {

            road = new RoadFragment();
            road.setId(Long.parseLong(attributes.getValue(uri, "id")));
        }


        if (elementName.equalsIgnoreCase("bounds")) {

            minLon = Double.parseDouble(attributes.getValue(uri, "minlon"));
            minLat = Double.parseDouble(attributes.getValue(uri, "minlat"));
        }

        if (elementName.equalsIgnoreCase("nd")) {

            road.getPoints().add(nodes.get(Long.parseLong(attributes.getValue(uri, "ref"))));

        }

    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {


        if (element.equalsIgnoreCase("way")) {
            //    System.out.println("Road added"+road.getId());
            roads.add(road);

        }

    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
    }


    public void removeRedundantVertises() {

        System.out.println("Size of hashtable:" + nodes.size());
        for (int i = 0; i < roads.size(); i++) {
            //    System.out.println("No of nodes in road:"+i+"="+roads.get(i).getPoints().size());
            //       System.out.println("\n"+roads.get(i).getPoints().size());
            for (int j = 0; j < roads.get(i).getPoints().size(); j++) {
                node = roads.get(i).getPoints().get(j);
                if (j != 0 && j != roads.get(i).getPoints().size() - 1 && nodes.containsKey(node.getId())) {
                    if (!node.isKeep()) {
                        nodes.remove(node.getId());

                        //System.out.println("Node Removed:"+node.getId());
                    }
                } else {
                    node.setKeep(true);
                    if (!nodes.containsKey(node.getId())) {
                        nodes.put(node.getId(), node);
                        //                      System.out.println("Node added again:"+node.getId());
                    }
                }
                //        System.out.print(":"+j);
            }
        }
        System.out.println("size reduced to:" + nodes.size());
//
//        System.out.println("HashEnties:");
//        
//        Enumeration<Long> em = nodes.keys();
//        while(em.hasMoreElements())
//            System.out.println(em.nextElement());
//        

        for (int i = 0; i < roads.size(); i++) {
            for (int j = 0; j < roads.get(i).getPoints().size(); j++) {
                node = roads.get(i).getPoints().get(j);
                //          System.out.println(node.getId()+":"+nodes.containsKey(node.getId()));
                if (!nodes.containsKey(node.getId())) {

                    roads.get(i).getPoints().remove(j);
                    //                System.out.println("Node removed from roadFragment:\t"+node.getId());
                    j--; //because arraylist is updated, all elements got shifted towards left by 1
                }

            }
            roads.get(i).getPoints().trimToSize();
            //     System.out.println("No of nodes in road:"+i+"="+roads.get(i).getPoints().size());
        }

//        
//        for (int i = 0; i < roads.size(); i++) {
//            System.out.println("\nRoad:"+roads.get(i).getId());
//            for (int j = 0; j < roads.get(i).getPoints().size(); j++) {
//                node = roads.get(i).getPoints().get(j);
//                System.out.print(node.getId()+"->");
//            }
//        }

    }

    public void setIntRoadCoord() {

        int i = 0;
        
        for (RoadFragment road : roads) {
            //  System.out.println(road.getId());
            road.initArrays(road.getPoints().size());
            i = 0;
            for (NodeModel node : road.getPoints()) {
                road.fillArrays((int) ((node.getLatitude() - minLat) * 50000), (int) ((node.getLongitude() - minLon) * 50000), i++);
            //    System.out.println(road.getX()[i-1]+" "+road.getY()[i-1]);
               
            }
//            //System.out.println("\n\n");
//            i--;
//        while(i>=0){
//        road.fillArrays(road.getX()[i]-minX, road.getY()[i]-minY,i );
//        System.out.println(road.getX()[i]+" "+road.getY()[i]);
//        i--;
//        }
        }
       
        
    }

    

    public ArrayList<RoadFragment> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<RoadFragment> roads) {
        this.roads = roads;
    }

    public Hashtable<Long, NodeModel> getNodes() {
        return nodes;
    }

    public void setNodes(Hashtable<Long, NodeModel> nodes) {
        this.nodes = nodes;
    }
/*
    public static void main(String[] args) {

        File fl = new File("very small map.osm");
        try {
            FileInputStream fis = new FileInputStream(fl);
            MySAXParser msaxParcer = new MySAXParser(fis);

            new CostCalculations().setCosts(msaxParcer.getRoads());
            msaxParcer.removeRedundantVertises();
            msaxParcer.setIntRoadCoord();
            ProcessData proData = new ProcessData(fl);
            proData.initDataModels();
            DrawMap draw = new DrawMap(msaxParcer.getRoads(), proData);
            draw.draw();
            GraphModel gModel = new GraphModel(msaxParcer.getNodes().size());
            msaxParcer.getNodes().clear();
            System.gc();
            gModel.fillAdjMat(msaxParcer.getRoads());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

*/
}
