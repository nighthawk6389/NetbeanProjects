package Pong;

import java.awt.*;
import java.awt.event.*; 
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/*
 * PongPanel.java
 *
 * Created on April 20, 2008, 11:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class PongPanel extends JPanel implements Runnable,KeyListener,ActionListener{
    JButton go= new JButton("Start");
     Point p1;
     Point p2;
     Point ball;
     int directionX=1;
     int directionY=0;
     int p1Y;
     int p2Y;
     int x;
     int y;
     int time=7;
     int lengthOfPanel = 50;
     boolean start =false;
     boolean suspend=false;
     Thread runner= new Thread(this);
     
    /** Creates a new instance of PongPanel */
    public PongPanel() {
        setLayout(new FlowLayout());
        add(go); 
        addKeyListener(this);
        go.addActionListener(this);
        setFocusable(true);
        System.out.println("Start");
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                time --;
                System.out.println("TIME NOW  "+time);
            }
        }, 60000, 60000);
        //repaint();
    }
    
    public boolean check(){
        if(ball.x==p1.x && (ball.y>=p1.y-55 && ball.y<=p1.y+5)){
            directionX=-(directionX);
            directionY= -(((p1.y-ball.y)-25)/5);
            System.out.println("DY1: "+directionY);
        }
        else if(ball.x==p2.x && (ball.y>=p2.y-55 && ball.y<=p2.y+5)){
            directionX=-(directionX);
            directionY= -(((p2.y-ball.y)-25)/5);
            System.out.println("DY2: "+directionY);
        }
        else if(ball.x<p1.x-10 || ball.x>p2.x+10){
            System.out.println("You lost"+ball.x+" "+ball.y);
            go.setText("Play again?");
            go.setVisible(true);
            go.setLocation(x/2,y/2);
            directionY=0;
            directionX=-directionX;
            suspend=true;
        }
        else if(ball.y>=y-10){
                directionY=-1;
        }
        else if(ball.y<=10){
               directionY=1;
        }
            
        return true;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(start){
            ball.x+=directionX;
            ball.y+=directionY;
            p1.y += p1Y;
            p2.y += p2Y;
            g.fillOval(ball.x,ball.y,10,10);
            g.drawLine(p1.x,p1.y,p1.x,p1.y-lengthOfPanel);
            g.drawLine(p2.x,p2.y,p2.x,p2.y-lengthOfPanel);
            //System.out.println("paint"+x+" "+y);
        }
    }

    public void run() {
            while(check()){
                 repaint();
              try{ 
                    Thread.sleep(time);
                    waitSuspend();
              }
               catch(InterruptedException e) { }
             }
    }

    public void keyPressed(KeyEvent e) {
       // if(directionX==1){
            if(e.getKeyCode()== KeyEvent.VK_UP) 
                p2Y = -1;
            if(e.getKeyCode() == KeyEvent.VK_DOWN)
                p2Y = 1;
        //}
        //else if(directionX==-1){
            if(e.getKeyChar()== 'w')
                p1Y = -1;
            if(e.getKeyChar()== 's')
                p1Y = 1;
        //}
        
        if(e.getKeyCode()== KeyEvent.VK_ENTER){
            suspend=true;
            String timee= JOptionPane.showInputDialog(null,"Enter Speeed");
            time=Integer.parseInt(timee);
            try {
                resume(); 
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } 
        }        
    }

    public void keyTyped(KeyEvent e){ 
       // System.out.println("key typed");

    }

    public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_UP) 
                p2Y = 0;
            if(e.getKeyCode() == KeyEvent.VK_DOWN)
                p2Y = 0;
        

            if(e.getKeyChar()== 'w')
                p1Y = 0;
            if(e.getKeyChar()== 's')
                p1Y = 0;
        
    }

    public void actionPerformed(ActionEvent e) {
        start=true;
        go.setVisible(false);
        this.setFocusable(true);
        x=getWidth();
        y=getHeight();
        p1= new Point(x-(x-10),y/2);
        p2= new Point(x-20,y/2);
        ball= new Point(x/2-50,y/2);
        System.out.println(x+" "+y);
        
        repaint();
        if(e.getActionCommand()== "Start")
            runner.start();

        else{
            
            try {
                resume();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public synchronized void waitSuspend() throws InterruptedException{
        while(suspend)
            wait();   
    }
    public synchronized void resume() throws InterruptedException{
        if(suspend){
            suspend=false;
            notify();
        }
    }

}
        
            
        
        
        
    

