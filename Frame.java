import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

class Frame extends JFrame {
    Graphic g;// = new Graphic();

    Frame() {
        setTitle("Genetic Algorithm");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(true);
        setVisible(true);
        g = new Graphic(getWidth(),getHeight());
        setResizable(false);
        add(g);
    }    

    public static void main(String[] args) {
        try {
            GraphicsEnvironment ge = 
                GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Assets/American Captain.ttf")));
        } catch (IOException|FontFormatException e) {
            System.err.println("Could not create Font");
        }
        new Frame();
    }
}