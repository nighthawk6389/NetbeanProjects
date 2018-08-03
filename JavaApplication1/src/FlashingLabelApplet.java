import javax.swing.*;
import java.awt.*;
/*
 * FlashingLabelApplet.java
 *
 * Created on April 22, 2008, 5:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */                
public class FlashingLabelApplet extends JApplet{
    
    /** Creates a new instance of FlashingLabelApplet */
    public FlashingLabelApplet() {
        System.out.println("flash"); 
        setLayout(new BorderLayout());
        getContentPane().add(new FlashingLabel(),BorderLayout.CENTER);
    }          
                                                
        public static void main(String args []){            
        FlashingLabelApplet frame= new FlashingLabelApplet();
        JFrame jframe= new JFrame();
        jframe.setVisible(true);
        jframe.setSize(400,400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.getContentPane().add(frame);
    }
    
}      
