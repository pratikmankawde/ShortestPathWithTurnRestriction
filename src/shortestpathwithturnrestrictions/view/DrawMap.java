package shortestpathwithturnrestrictions.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import javax.swing.event.MouseInputListener;
import shortestpathwithturnrestrictions.model.RoadFragment;

public class DrawMap extends JPanel implements MouseInputListener, MouseWheelListener, KeyListener {

    Graphics2D graphicsObj;
    ArrayList<RoadFragment> roads;
    double zoom = 1;
    double angle = 0.0;
    double startX, startY, endX = 0.0, endY = 0.0, moveX, moveY;
    ArrayList<Ellipse2D> vertexNodes;
    Image sourceMarker, destinationMarker;
    
    int sourceVertex = -1;
    int destinationVertext = -1;

    public DrawMap(ArrayList<RoadFragment> roads) {
        // TODO Auto-generated constructor stub
        this.roads = roads;
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        init();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphicsObj = (Graphics2D) g;
        graphicsObj.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        this.setBackground(Color.white);

        graphicsObj.setColor(Color.ORANGE);


        graphicsObj.scale(zoom, zoom);

        graphicsObj.translate(moveX, moveY);

        //   graphicsObj.rotate(-1.55);


        int i = 0;
        graphicsObj.setStroke(new BasicStroke(2f));
        graphicsObj.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        int nodeNo = 0;
        for (RoadFragment road : roads) {

            //   System.out.println("Road Drawn:"+road.getId());
            graphicsObj.drawPolyline(road.getX(), road.getY(), road.getX().length);
            //     graphicsObj.setColor(new Color((10*i)%255, (i*i)%255, (20*i)%255));               
            // graphicsObj.setColor(Color.RED);
            // for (i = 0; i < road.getX().length; i++) {
            //     graphicsObj.setColor(Color.RED);
            //     graphicsObj.fillOval(road.getX()[i], road.getY()[i], radius, radius);
            // graphicsObj.drawString((j++)+":"+road.getPoints().get(i).getId(),road.getX()[i], road.getY()[i]);
            //    graphicsObj.setColor(Color.BLACK);
            //     graphicsObj.drawString(String.valueOf(nodeNo),road.getX()[i],nodeNo+ road.getY()[i]);


            nodeNo++;

            //   }

            //    graphicsObj.setColor(Color.GREEN);
            //  graphicsObj.drawString("cost:" + (float) (road.getCostObj().getFizedCost() * Constants.scaler),road.getX()[i / 2], 100+road.getY()[i / 2]);
            //   System.out.println("cost:"+(float)(road.getCostObj().getFizedCost()* Constants.scaler)+" "+road.getX()[i/2]+" "+road.getY()[i/2]);
            graphicsObj.setColor(new Color((47 * nodeNo) % 255, (3 * nodeNo * nodeNo) % 255, (73 * nodeNo) % 255));

        }
        //  graphicsObj.setStroke(new BasicStroke(2000f));
        graphicsObj.setColor(Color.RED);
        for (i = 0; i < vertexNodes.size(); i++) {
            graphicsObj.fill(vertexNodes.get(i));
        }
        if (sourceVertex != -1) {
            graphicsObj.drawImage(sourceMarker, (int) (vertexNodes.get(sourceVertex).getCenterX() - sourceMarker.getWidth(null) / 2), (int) (vertexNodes.get(sourceVertex).getCenterY() - sourceMarker.getHeight(null) / 2), null);
        }
        if (destinationVertext != -1) {
            graphicsObj.drawImage(destinationMarker, (int) (vertexNodes.get(destinationVertext).getCenterX() - destinationMarker.getWidth(null) / 2), (int) (vertexNodes.get(destinationVertext).getCenterY() - destinationMarker.getHeight(null) / 2), null);
        }
    }

    public void draw() {
        JFrame frame = new JFrame("Selected Map");
        frame.add(this);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setFocusable(true);
        this.setFocusable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        moveX = this.getWidth() / 2;
        moveY = this.getHeight() / 2;
        Toolkit.getDefaultToolkit().sync();
        sourceMarker = Toolkit.getDefaultToolkit().getImage("sourceMarker.png");
        destinationMarker = Toolkit.getDefaultToolkit().getImage("destinationMarker.png");

    }

    public void init() {

        int i = 0;
        vertexNodes = new ArrayList<Ellipse2D>();
        float radius = 5;
        for (RoadFragment road : roads) {
            for (i = 0; i < road.getX().length; i++) {
                vertexNodes.add(new Ellipse2D.Float(road.getX()[i], road.getY()[i], radius, radius));

            }
        }

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
            System.out.println(i + "th Point selected.");
            if (sourceVertex == i) {
                sourceVertex = -1;
            } else if (sourceVertex != -1) {
                if (destinationVertext != i && destinationVertext!=sourceVertex) {
                    destinationVertext = i;
                } else {
                    destinationVertext = -1;
                }
            } else if(destinationVertext != i) {
                sourceVertex = i;
            }
        } else {
            System.out.println("No point selected.");

        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
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


        startX = endX;
        startY = endY;
        endX = e.getX();
        endY = e.getY();

        moveX += (endX - startX);
        moveY += (endY - startY);

        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (e.getWheelRotation() > 0) {
            zoom -= 0.01;
        } else if (e.getWheelRotation() < 0) {
            zoom += 0.01;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_KP_LEFT) {
            moveX += 1300;
        } else if (e.getKeyCode() == KeyEvent.VK_KP_RIGHT) {
            moveX -= 1300;
        } else if (e.getKeyCode() == KeyEvent.VK_KP_DOWN) {
            moveY += 1300;
        } else if (e.getKeyCode() == KeyEvent.VK_KP_UP) {
            moveY -= 1300;
        }
        System.out.println("key pressed");
        //   repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_KP_LEFT) {
            moveX += 1300;
        } else if (e.getKeyCode() == KeyEvent.VK_KP_RIGHT) {
            moveX -= 1300;
        } else if (e.getKeyCode() == KeyEvent.VK_KP_DOWN) {
            moveY += 1300;
        } else if (e.getKeyCode() == KeyEvent.VK_KP_UP) {
            moveY -= 1300;
        }
        System.out.println("key pressed");
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
