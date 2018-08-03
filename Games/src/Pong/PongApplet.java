package Pong;

   import java.awt.*;
import javax.swing.*;
/*
 * PongApplet.java
 *
 * Created on April 20, 2008, 11:59 PM
 *               
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**   
 *
 * @author Ilan Elkobi
 */                                    
public class PongApplet extends JApplet{
   // PongPanel panel= new PongPanel();
      
    /** Creates a new instance of PongApplet */
    public PongApplet() {
        setLayout(new BorderLayout());
        getContentPane().add(new PongPanel(),BorderLayout.CENTER);
    }  
               
    public static void main(String args []){
        JFrame frame= new JFrame();
        PongApplet applet = new PongApplet();
        frame.setSize(400,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
    }
}
                                                                                          