import javax.swing.JApplet;
import javax.swing.JFrame;
/*
 * FanControl.java
 *
 * Created on May 12, 2008, 9:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class FanControl extends JApplet{
    
    /** Creates a new instance of FanControl */
    public FanControl() {
        FanPanel panel= new FanPanel();
        getContentPane().add(panel);
    }
    public static void main(String args []){
        JFrame frame= new JFrame();
        FanControl fan= new FanControl();
        frame.getContentPane().add(fan);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
    }
}
