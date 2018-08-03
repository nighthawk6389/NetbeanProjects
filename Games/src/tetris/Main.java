      /*
 * Main.java
 *
 * Created on December 1, 2009, 7:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tetris;

import javax.swing.JFrame;                                         

/**
 *
 * @author elkobi
 */
public class Main extends JFrame {
    
    /** Creates a new instance of Main */                                            
    public Main() {
    this.getContentPane().add(new TTTControl());   
    }                                                                                                                                                         
                                        
    public static void main(String args []){
        Main frame= new Main();                      
        frame.setSize(400,400);      
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
    
}
