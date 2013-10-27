package shortestpathwithturnrestrictions.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.event.MouseInputListener;
import shortestpathwithturnrestrictions.model.Constants;
import shortestpathwithturnrestrictions.model.RoadFragment;

public class DrawMap extends JPanel implements MouseInputListener, MouseWheelListener, KeyListener {

    Graphics2D graphicsObj;
    ArrayList<RoadFragment> roads;
    double zoom = 0.004;
    double angle = 0.0;
    double startX, startY, endX = 0.0, endY = 0.0, moveX = 0.0, moveY = 500.0;

    public DrawMap(ArrayList<RoadFragment> roads) {
        // TODO Auto-generated constructor stub
        this.roads = roads;
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphicsObj = (Graphics2D) g;
        graphicsObj.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        this.setBackground(Color.white);
        graphicsObj.setColor(Color.blue);


        graphicsObj.scale(zoom, zoom);

        graphicsObj.translate(moveX, moveY);

        //   graphicsObj.rotate(-1.55);

        int i = 0;
        graphicsObj.setStroke(new BasicStroke(700f));
        graphicsObj.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 2000));
        int radius = 1000;
        for (RoadFragment road : roads) {

            //   System.out.println("Road Drawn:"+road.getId());
            graphicsObj.drawPolyline(road.getX(), road.getY(), road.getX().length);
            //     graphicsObj.setColor(new Color((10*i)%255, (i*i)%255, (20*i)%255));               
            graphicsObj.setColor(Color.RED);
            for (i = 0; i < road.getX().length; i++) {
                graphicsObj.fillOval(road.getX()[i], road.getY()[i], radius, radius);
            }
          
            graphicsObj.setColor(Color.GREEN);
            graphicsObj.drawString("cost:" + (float) (road.getCostObj().getFizedCost() * Constants.scaler), road.getX()[i / 2], road.getY()[i / 2]);
       //     System.out.println("cost:"+(float)(road.getCostObj().getFizedCost()* Constants.scaler)+" "+road.getX()[i/2]+" "+road.getY()[i/2]);
            graphicsObj.setColor(new Color((10 * i) % 255, (i * i) % 255, (20 * i) % 255));

        }


    }

    public void draw() {
        JFrame frame = new JFrame("Selected Map");
        frame.add(this);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // if(e.>0){
        //        angle-=0.05;
        //          System.out.println(angle);
//        }
//        else if(e.getWheelRotation()<0){
//            zoom+=0.001;
//        }
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

        moveX += 300 * (endX - startX);
        moveY += 300 * (endY - startY);

        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (e.getWheelRotation() > 0) {
            zoom -= 0.001;
        } else if (e.getWheelRotation() < 0) {
            zoom += 0.001;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getKeyCode() == e.VK_KP_LEFT) {
            moveX += 1300;
        } else if (e.getKeyCode() == e.VK_KP_RIGHT) {
            moveX -= 1300;
        } else if (e.getKeyCode() == e.VK_KP_DOWN) {
            moveY += 1300;
        } else if (e.getKeyCode() == e.VK_KP_UP) {
            moveY -= 1300;
        }
        System.out.println("key pressed");
        //   repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_KP_LEFT) {
            moveX += 1300;
        } else if (e.getKeyCode() == e.VK_KP_RIGHT) {
            moveX -= 1300;
        } else if (e.getKeyCode() == e.VK_KP_DOWN) {
            moveY += 1300;
        } else if (e.getKeyCode() == e.VK_KP_UP) {
            moveY -= 1300;
        }
        System.out.println("key pressed");
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
