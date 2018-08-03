  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 */

package WordGame;                                              

import javax.swing.JFrame;             

/**
 *
 * @author Mavis Beacon                                                                        
 */
public class Main {    

    public static void main(String args [] ){ 
      JFrame frame=new JFrame();            
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);               
      WordControl bj=new WordControl();
       
      frame.getContentPane().add(bj);
      frame.setSize(700,330);
    }             
}                       
