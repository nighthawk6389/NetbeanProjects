import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
/*
 * Snake.java
 *
 * Created on November 21, 2007, 6:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */  

/**   
 *     
 * @author Ilan Elkobi
 */
public class Snake extends JFrame{        
    JTextArea text= new JTextArea("Press Enter to Start");
    Picture pic=new Picture();   
      
    /** Creates a new instance of Snake */
    public static void main(String args []){
        Snake frame= new Snake(); 
        frame.setSize(400,400);      
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.ok();            
                                             
    } 
        
    public Snake() {
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pic,BorderLayout.CENTER);
        getContentPane().add(text,BorderLayout.SOUTH); 
    }     
    public void ok(){ 
        pic.toll();
          
    }  
       
} 


