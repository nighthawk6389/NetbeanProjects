    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SlimeBall;

import javax.swing.JFrame;                      

/**
 *
 * @author Mavis Beacon
 */
public class Main {

    public static void main(String args []){
        JFrame frame= new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BasketBallControl());
        frame.setSize(1000,400);
    } 
}
