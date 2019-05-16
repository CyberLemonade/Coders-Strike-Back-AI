import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

class Graphic extends JPanel {
    Image back;
    Image goal;
    GA genetic;
    final double mult;
    Font font = new Font("American Captain", Font.PLAIN, 30);

    Graphic() {
        mult = 0.02;
        setSize((int)(Map.width*mult),(int)(Map.height*mult));
        setFocusable(true);
        all();
    }

    Graphic(int targetW,int targetH) {
        mult = targetW/Map.width;
        Map.height = targetH/mult;
        setSize((int)(Map.width*mult),(int)(Map.height*mult));
        setFocusable(true);
        genetic = new GA();
        try{
            BufferedImage BACK_1=ImageIO.read(new File("Assets/back.png"));
            back=BACK_1;
            BufferedImage BACK_2=ImageIO.read(new File("Assets/goal.png"));
            goal=BACK_2;
        } catch (Exception e) {System.err.println("Image not found");}

        all();
    }

    void all() {
        genetic.simulate();
        try {Thread.sleep(10);}
        catch (Exception e) {}
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(font);
        g.drawImage(back,0,0,getWidth(),getHeight(),null);
        displayCP(g);
        //displayObstacles(g);
        displayPlayers(g);
        displayInfo(g);
        all();
    }

    void displayCP(Graphics g) {
        for (int i = 0; i < genetic.map.cp.length; i++) {
            //g.setColor(Color.RED);
            //drawUnit(g,genetic.map.cp[i],Color.RED);
            drawGoal(g,genetic.map.cp[i]);
            g.setColor(Color.WHITE);
            g.drawString(""+i, (int)(genetic.map.cp[i].x*mult) - 7, (int)(genetic.map.cp[i].y*mult) + 7);
        }
    }

    /*void displayObstacles(Graphics g) {
    for (int i = 0; i < genetic.map.obs.length; i++) {
    g.setColor(Color.BLACK);
    displayUnit(g,genetic.map.obs[i]);
    }
    }*/

    void displayPlayers(Graphics g) {
        /*for (int i = 1; i < genetic.pods.length; i++) {
        if (i == genetic.cntBest) continue;
        drawPod(g,genetic.pods[i],Color.BLUE);
        }*/
        drawPod(g,genetic.pods[genetic.cntBest],new Color(144,110,100));
        drawPod(g,genetic.pods[0],new Color(171,22,54));
    }

    void drawPod(Graphics g,Pod c,Color col) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        // LOCATOR:
        //g.setColor(Color.BLACK);
        //g.drawLine((int)((c.x)*mult), (int)((c.y)*mult), (int)((Map.width/2.0)*mult), (int)((Map.height/2.0)*mult));

        double xPod1 = (c.x + (Math.cos(c.angle+Math.PI/7.0)*c.r*3.5));
        double yPod1 = (c.y - (Math.sin(c.angle+Math.PI/7.0)*c.r*3.5));

        double xPod2 = (c.x + (Math.cos(c.angle-Math.PI/7.0)*c.r*3.5));
        double yPod2 = (c.y - (Math.sin(c.angle-Math.PI/7.0)*c.r*3.5));

        double xPod3 = (c.x - (Math.cos(c.angle-c.angularVelocity*10.0)*c.r*0.75));
        double yPod3 = (c.y + (Math.sin(c.angle-c.angularVelocity*10.0)*c.r*0.75));

        g.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g.drawLine((int)((c.x)*mult), (int)((c.y)*mult), (int)((xPod1)*mult), (int)((yPod1)*mult));
        g.drawLine((int)((c.x)*mult), (int)((c.y)*mult), (int)((xPod2)*mult), (int)((yPod2)*mult));

        g.setColor(new Color(0,255,255));
        g2.setStroke(new BasicStroke(3));
        g.drawLine((int)((xPod2)*mult), (int)((yPod2)*mult), (int)((xPod1)*mult), (int)((yPod1)*mult));

        g.setColor(col);
        g2.setStroke(new BasicStroke((int)(c.r*2.0*mult)));
        g.drawLine((int)((c.x)*mult), (int)((c.y)*mult), (int)((xPod3)*mult), (int)((yPod3)*mult));

        g2.setStroke(new BasicStroke(1));
        drawUnit(g, new Unit(xPod1, yPod1, c.r), col);
        drawUnit(g, new Unit(xPod2, yPod2, c.r), col);
        //drawUnit(g, new Unit(xPod3, yPod3, c.r), col);
        //drawUnit(g,c,col);
    }

    void drawUnit(Graphics g,Unit c,Color col) {
        g.setColor(col);
        g.fillOval((int)((c.x-c.r)*mult), (int)((c.y-c.r)*mult), (int)(c.r*2.0*mult), (int)(c.r*2.0*mult));
        //g.setColor(Color.BLACK);
        g.drawOval((int)((c.x-c.r)*mult), (int)((c.y-c.r)*mult), (int)(c.r*2.0*mult), (int)(c.r*2.0*mult));
    }
    
    void drawGoal(Graphics g,Unit c) {
        g.drawImage(goal, (int)((c.x-c.r)*mult), (int)((c.y-c.r)*mult), (int)(c.r*2.0*mult), (int)(c.r*2.0*mult), null);
    }

    void displayInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("GENERATION: "+genetic.generation, (int)(1250.0*mult), (int)(1850.0*mult));

        g.drawString("POD: "+0, (int)(1250.0*mult), (int)(4100.0*mult));
        g.drawString("TICKS: "+genetic.pods[0].sinceLast, (int)(1250.0*mult), (int)(5050.0*mult));
        g.drawString("NEXT CHECKPOINT: "+genetic.pods[0].next, (int)(1250.0*mult), (int)(6000.0*mult));

        g.drawString("POD: "+genetic.cntBest, (int)(1250.0*mult), (int)(8000.0*mult));
        g.drawString("TICKS: "+genetic.pods[genetic.cntBest].sinceLast, (int)(1250.0*mult), (int)(9000.0*mult));
        g.drawString("NEXT CHECKPOINT: "+genetic.pods[genetic.cntBest].next, (int)(1250.0*mult), (int)(10000.0*mult));
    }
}