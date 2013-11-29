package shortestpathwithturnrestrictions.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.swing.JComponent;
import javax.swing.SwingWorker;
import javax.swing.event.MouseInputListener;
import shortestpathwithturnrestrictions.action.ProcessData;
import shortestpathwithturnrestrictions.model.RoadFragment;
import shortestpathwithturnrestrictions.model.Turn;

public class DrawMap extends JComponent implements MouseInputListener, MouseWheelListener {

    private Graphics2D graphicsObj;
    private ArrayList<RoadFragment> roads;
    private double zoom = 1.0;
    private double moveX = 0.0, moveY = 0.0, lastOffsetX = 0.0, lastOffsetY = 0.0;
    private ArrayList<Ellipse2D> vertexNodes;
    private ArrayList<Long> vertexIds;
    private Image sourceMarker, destinationMarker, turnRestrictionMarker;
    private ProcessData dataProcessor;
    private ArrayList<Integer> shortestPath = null;
    private int sourceVertex = -1;
    private int destinationVertex = -1;
    private int incomingVertex=-1, turnVertex=-1, outgoingVertex=-1;
    SwingWorker<String[], Void> pathComputer;
    private boolean pathCalculationDone = false;
    private Set<Integer> turns;
    private int prevPoint = -1;

    public DrawMap(ArrayList<RoadFragment> roads, ProcessData dataProcessor) {
        // TODO Auto-generated constructor stub
        this.roads = roads;
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.dataProcessor = dataProcessor;
    }

    @Override
    public void paint(Graphics g) {
        //   super.paint(g);

        graphicsObj = (Graphics2D) g;
        graphicsObj.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphicsObj.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphicsObj.setColor(Color.WHITE);
        graphicsObj.fillRect(0, 0, getWidth(), getHeight());
        graphicsObj.setColor(Color.ORANGE);
        graphicsObj.setStroke(new BasicStroke(2.0f));
        graphicsObj.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));

        graphicsObj.scale(zoom, zoom);
        graphicsObj.translate(moveX, moveY);

        int nodeNo = 0;

        for (RoadFragment road : roads) {
            graphicsObj.drawPolyline(road.getX(), road.getY(), road.getX().length);
            nodeNo++;
            //          graphicsObj.drawString(""+(int)(road.getCostObj().getFizedCost()*100), road.getX()[road.getX().length-1]+10, road.getY()[road.getX().length-1]+5);
            graphicsObj.setColor(new Color((47 * nodeNo) % 255, (3 * nodeNo * nodeNo) % 255, (73 * nodeNo) % 255));
        }


        if (shortestPath != null) {

            graphicsObj.setStroke(new BasicStroke(4f));
            graphicsObj.setColor(Color.black);

//      int i = destinationVertex;                
//        while (i != sourceVertex && shortestPathStr[i].length()!=0) {
//            System.out.println(shortestPathStr[i]);
//            int value = Integer.parseInt(shortestPathStr[i].substring(shortestPathStr[i].lastIndexOf(";")+1));
//            System.out.println("Value:"+value);
//            graphicsObj.drawLine((int)vertexNodes.get(i).getCenterX(),(int) vertexNodes.get(i).getCenterY(), (int)vertexNodes.get(value).getCenterX(),(int) vertexNodes.get(value).getCenterY());
//            shortestPathStr[i] = shortestPathStr[i].substring(0, shortestPathStr[i].lastIndexOf(";"));
//            i = value;
//        }
//        
            for (int k = 1; k < shortestPath.size(); k++) {
                //         System.out.println(k);
                graphicsObj.drawLine((int) vertexNodes.get(shortestPath.get(k - 1)).getCenterX(), (int) vertexNodes.get(shortestPath.get(k - 1)).getCenterY(), (int) vertexNodes.get(shortestPath.get(k)).getCenterX(), (int) vertexNodes.get(shortestPath.get(k)).getCenterY());
            }

        }


        for (int i = 0; i < vertexNodes.size(); i++) {
            graphicsObj.setColor(Color.RED);
            graphicsObj.fill(vertexNodes.get(i));
            if (turns.contains(i)) {
                graphicsObj.drawImage(turnRestrictionMarker, (int) (vertexNodes.get(i).getCenterX() - turnRestrictionMarker.getWidth(this) / 2), (int) (vertexNodes.get(i).getCenterY() - turnRestrictionMarker.getHeight(this)), this);
            }

            graphicsObj.setColor(Color.black);
            graphicsObj.drawString("" + i, (float) vertexNodes.get(i).getMaxX(), (float) vertexNodes.get(i).getMinY());
            
           
        }

         if(incomingVertex!=-1)
                    graphicsObj.drawString("I", (float) vertexNodes.get(incomingVertex).getMinX(), (float) vertexNodes.get(incomingVertex).getMinY());
            
         if(turnVertex!=-1)
                     graphicsObj.drawString("T", (float) vertexNodes.get(turnVertex).getMinX(), (float) vertexNodes.get(turnVertex).getMinY());
            
         if(outgoingVertex!=-1)
              graphicsObj.drawString("O", (float) vertexNodes.get(outgoingVertex).getMinX(), (float) vertexNodes.get(outgoingVertex).getMinY());
            



        if (sourceVertex != -1) {
            graphicsObj.drawImage(sourceMarker, (int) (vertexNodes.get(sourceVertex).getCenterX() - sourceMarker.getWidth(null) / 2), (int) (vertexNodes.get(sourceVertex).getCenterY() - sourceMarker.getHeight(this)), this);
        }
        if (destinationVertex != -1) {
            graphicsObj.drawImage(destinationMarker, (int) (vertexNodes.get(destinationVertex).getCenterX() - destinationMarker.getWidth(null) / 2), (int) (vertexNodes.get(destinationVertex).getCenterY() - destinationMarker.getHeight(this)), this);
        }

    }

    public void draw() {
        JFrame frame = new JFrame("Selected Map");

        frame.setBackground(Color.yellow);
        frame.setLayout(new CardLayout());
        frame.getContentPane().add(this);

        frame.setSize(800, 600);

        //  frame.add(this);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        init();
    }

    public void init() {

        vertexNodes = new ArrayList<Ellipse2D>();
        vertexIds = new ArrayList<Long>();
        long temp;
        float radius = 5.0f;
        turns = dataProcessor.getgModel().getTurnRestrictions().keySet();

        for (RoadFragment road : roads) {
            for (int i = 0; i < road.getX().length; i++) {
                temp = road.getPoints().get(i).getId();
                if (!vertexIds.contains(temp)) {
                    vertexNodes.add(new Ellipse2D.Float(road.getX()[i], road.getY()[i], radius, radius));
                    vertexIds.add(temp);
                }
            }
        }
        vertexIds.clear();

        sourceMarker = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/shortestpathwithturnrestrictions/images/sourceMarker.gif"));
        destinationMarker = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/shortestpathwithturnrestrictions/images/destinationMarker.gif"));
        turnRestrictionMarker = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/shortestpathwithturnrestrictions/images/turnRestrictionMarker.gif"));

        /**
         * SwingWorker instance to run a compute-intensive task Final result is
         * String[], no intermediate result (Void)
         */
        pathComputer = new SwingWorker<String[], Void>() {
            /**
             * Schedule a compute-intensive task in a background thread
             */
            @Override
            protected String[] doInBackground() throws Exception {

                return dataProcessor.calculateShortestPath(sourceVertex, destinationVertex);
            }

            /**
             * Run in event-dispatching thread after doInBackground() completes
             */
            @Override
            protected void done() {
                try {
                    // Use get() to get the result of doInBackground()
                    decodePath(get());
                    pathCalculationDone = true;
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };



    }

    @Override
    public void mouseClicked(MouseEvent e) {


        int i = 0;
        boolean pointSelected = false;


        Rectangle2D rect;
        //   System.out.println("Mouse Clicked at:" + e.getX() + "," + e.getY());
        while (i < vertexNodes.size()) {

            rect = vertexNodes.get(i).getBounds2D();
            rect.setRect((rect.getX() + moveX) * zoom, (rect.getY() + moveY) * zoom, rect.getWidth() * zoom, rect.getHeight() * zoom);

            //  System.out.println("Point " + i + ":" + rect.getMinX() + "," + rect.getMaxX());
            if (rect.contains(e.getX(), e.getY())) {
                pointSelected = true;
                break;
            }

            i++;
        }

        if (pointSelected) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                System.out.println(i + "th Point selected.");
                if (sourceVertex == i) {
                    sourceVertex = -1;
                    shortestPath = null;
                } else if (sourceVertex != -1) {
                    if (destinationVertex != i && destinationVertex != sourceVertex) {
                        destinationVertex = i;
                    } else {
                        destinationVertex = -1;
                        shortestPath = null;
                    }
                } else if (destinationVertex != i) {
                    sourceVertex = i;
                } else {
                    destinationVertex = -1;
                }

                if (sourceVertex != -1 && destinationVertex != -1) {
                    // shortestPathStr= dataProcessor.calculateShortestPath(sourceVertex, destinationVertex);
                    if (pathCalculationDone) {
                        decodePath(dataProcessor.calculateShortestPath(sourceVertex, destinationVertex));
                    } else {
                        pathComputer.execute();
                    }
                } else {
                    shortestPath = null;
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                System.out.println("turn setup");
                
                if(turns.contains(i)){
                dataProcessor.getgModel().getTurnRestrictions().remove(i);
                turns = dataProcessor.getgModel().getTurnRestrictions().keySet();
                 if (pathCalculationDone) {
                        decodePath(dataProcessor.calculateShortestPath(sourceVertex, destinationVertex));
                    } else {
                        pathComputer.execute();
                    }
                }
                else if(i==incomingVertex)
                    incomingVertex=-1;
                else if(i==outgoingVertex)
                    outgoingVertex=-1;
                else if(i==turnVertex)
                    turnVertex=-1;
                else if(incomingVertex==-1)
                    incomingVertex=i;
                else if(turnVertex==-1)
                    turnVertex=i;
                else
                    outgoingVertex=i;
                if(turnVertex!=-1 && incomingVertex!=-1 && outgoingVertex!=-1){
                dataProcessor.getgModel().getTurnRestrictions().put(turnVertex, new Turn(incomingVertex, outgoingVertex, null));
                turns = dataProcessor.getgModel().getTurnRestrictions().keySet();
                turnVertex = -1;
                incomingVertex = -1;
                outgoingVertex =-1;
                 if (pathCalculationDone) {
                        decodePath(dataProcessor.calculateShortestPath(sourceVertex, destinationVertex));
                    } else {
                        pathComputer.execute();
                    }
                }
            }
            
            
            repaint();
        }


    }

    public void decodePath(String[] pathStr) {

        int i = destinationVertex;

        if (shortestPath == null) {
            shortestPath = new ArrayList<Integer>();
        } else {
            shortestPath.clear();
        }
        shortestPath.add(i);
        while (i != sourceVertex && i != -1 && pathStr[i].length() != 0) {
            //    System.out.println(pathStr[i]);
            int value = Integer.parseInt(pathStr[i].substring(pathStr[i].lastIndexOf(";") + 1));
            //     System.out.println("Value:"+value);
            shortestPath.add(value);
            //     graphicsObj.drawLine((int)vertexNodes.get(i).getCenterX(),(int) vertexNodes.get(i).getCenterY(), (int)vertexNodes.get(value).getCenterX(),(int) vertexNodes.get(value).getCenterY());
            pathStr[i] = pathStr[i].substring(0, pathStr[i].lastIndexOf(";"));
            i = value;
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

        lastOffsetX = e.getX();
        lastOffsetY = e.getY();


    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        double newX = e.getX() - lastOffsetX;
        double newY = e.getY() - lastOffsetY;

        // increment last offset to last processed by drag event.
        lastOffsetX += newX;
        lastOffsetY += newY;

        // update the canvas locations
        moveX += newX;
        moveY += newY;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int i = 0;

        Rectangle2D rect;
        //   System.out.println("Mouse Clicked at:" + e.getX() + "," + e.getY());
        while (i < vertexNodes.size()) {

            rect = vertexNodes.get(i).getBounds2D();
            rect.setRect((rect.getX() + moveX) * zoom, (rect.getY() + moveY) * zoom, rect.getWidth() * zoom, rect.getHeight() * zoom);

            //  System.out.println("Point " + i + ":" + rect.getMinX() + "," + rect.getMaxX());
            if (rect.contains(e.getX(), e.getY())) {
                if (prevPoint != i) {
                    ((JFrame) getTopLevelAncestor()).setTitle("Selected Point:" + i);
                    // System.out.println(i + "th Point.");
                    prevPoint = i;
                }
                break;
            }

            i++;
        }


    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (e.getWheelRotation() > 0) {
            zoom -= 0.05;
        } else if (e.getWheelRotation() < 0) {
            zoom += 0.05;
        }
        repaint();
    }
}
