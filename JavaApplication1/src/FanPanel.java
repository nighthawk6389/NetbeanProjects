import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * FanPanel.java
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
public class FanPanel extends JPanel implements ActionListener, AdjustmentListener{
    JButton start, stop, reverse;
    JScrollBar scroll;
    Fan fan= new Fan();
    JPanel panel1;
    JPanel panel2;
          
          
    public static void main(String args []){
        JFrame frame= new JFrame();
        FanPanel panel= new FanPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400,400);
        frame.getContentPane().add(panel);
    }
    
    /** Creates a new instance of FanPanel */
    public FanPanel() {
             
        setLayout(new BorderLayout());
        panel1= new JPanel();
        panel1.setBackground(Color.BLACK);
        //panel1.setLayout(new BorderLayout());
        start=new JButton("Start");
        stop= new JButton("Stop");
        reverse= new JButton("Reverse");
        scroll=new JScrollBar(JScrollBar.HORIZONTAL);
        scroll.setMaximum(10);
        scroll.setMinimum(1);
        scroll.setVisibleAmount(1);
        scroll.addAdjustmentListener(this);
       // scroll.setUnitIncrement(1);
        start.addActionListener(this);
        stop.addActionListener(this);
        reverse.addActionListener(this); 
        panel1.add(start,BorderLayout.WEST);
        panel1.add(stop,BorderLayout.CENTER);
        panel1.add(reverse,BorderLayout.EAST);
        
        panel2= new JPanel();
        //panel2.setLayout(new BorderLayout());
        panel2.add(scroll,BorderLayout.NORTH); 
        
        JPanel panelFan = new JPanel();
        panelFan.setBackground(Color.GREEN);
        panelFan.add(fan);
        
        JPanel whole= new JPanel();
        whole.setLayout(new BorderLayout());
        whole.add(fan,BorderLayout.CENTER);
        whole.add(panel1,BorderLayout.NORTH);
        whole.add(scroll,BorderLayout.SOUTH);      
                       
        add(whole);
        
                
    }
    public void start(){
        try{
            fan.resume();
        }         
        catch(InterruptedException e){
            e.printStackTrace();  
        }
        //System.out.println(panel2.getHeight()+ " "+ panel2.getWidth());
        //System.out.println(panel1.getHeight()+ " "+ panel1.getWidth());
    }
    public void stop(){
        fan.suspend=true;  
        
    }
    public void reverse(){
        fan.reverse();
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand()=="Start"){
            start();
        }
        else if(e.getActionCommand()=="Stop"){
            stop();
        }
        else if(e.getActionCommand()=="Reverse"){
            reverse();
        }
    }
    public void adjustmentValueChanged(AdjustmentEvent e) {
        System.out.println("scroll");  
        int value=scroll.getValue();
        fan.setSpeed(value);
    }
    
}