 import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
/*                                                                                    
 * PacManFrame.java
 *
 * Created on May 14, 2008, 5:46 PM
 *                    
 * To change this template, choose Tools | Template                                         
 * and open the template in the editor.
 */
  
/**                                        
 *                                      
 * @author Ilan Elkobi                                                                            
 */                         
public class PacManFrame extends JFrame{ 
    PacManControl panel;
    help panel1;     
                                                                                                                        
                             
    public static void main(String args []){
        PacManFrame frame= new PacManFrame();
        frame.setSize(400,400);
        frame.setVisible(true);                                                     
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }                             
                                      
    /** Creates a new instance of PacManFrame */
    public PacManFrame() {                                    
        panel= new PacManControl();            
        //panel1= new help();                                                                    
       getContentPane().add(panel);  
    } 
    
}                                                                                                                                                                                       
                                                                                                                             