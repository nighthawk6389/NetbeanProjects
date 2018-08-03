import javax.swing.*;
/*
 * RandomArrayExecute.java
 *
 * Created on April 22, 2008, 1:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class RandomArrayExecute extends JFrame {
    RandomArray panel = new RandomArray();
    
    /** Creates a new instance of RandomArrayExecute */
    public RandomArrayExecute() {
        getContentPane().add(panel);
    }
    
    public static void main(String args []){
        RandomArrayExecute frame = new RandomArrayExecute();
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }     
}
