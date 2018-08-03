import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
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
     int x;
     int y;
     int time=7;
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
        //repaint();
    }
    
    public boolean check(){
        if(ball.x==p1.x && (ball.y>=p1.y-55 && ball.y<=p1.y+5)){
            directionX=-(directionX);
            directionY= -(((p1.y-ball.y)-30)/10);
        }
        else if(ball.x==p2.x && (ball.y>=p2.y-55 && ball.y<=p2.y+5)){
            directionX=-(directionX);
            directionY= -(((p2.y-ball.y)-30)/10);
        }
        
       // System.out.println("Check"+" "+ball.x+" "+ball.y+" "+p1.y+" "+p2.y);
       // x=getWidth();
       // y=getHeight();
        /*
        if(ball.x==p1.x && (ball.y>=p1.y-55 && ball.y<=p1.y-30)){
            directionX=-(directionX);
            directionY=-1;
        }
        else if(ball.x==p1.x && (ball.y>=p1.y-30 && ball.y<=p1.y-20)){
            directionX=-(directionX);
            directionY=0;
        }
        else if(ball.x==p1.x && (ball.y>=p1.y-20 && ball.y<=p1.y-5)){
            directionX=-(directionX);
            directionY=1;
        }
        else if(ball.x==p2.x &&  (ball.y>=p2.y-55 && ball.y<=p2.y-30)){
            directionX=-(directionX);
            directionY=-1;
        }
        else if(ball.x==p2.x &&  (ball.y>=p2.y-30 && ball.y<=p2.y-20)){
            directionX=-(directionX);
            directionY=0;
        }
        else if(ball.x==p2.x &&  (ball.y>=p2.y-20 && ball.y<=p2.y-5)){
            directionX=-(directionX);
            directionY=1;
        } 
         **/       
        else if(ball.x<p1.x || ball.x>p2.x){
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
        ball.x+=directionX;
        ball.y+=directionY;
            
        return true;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(start){
        g.fillOval(ball.x,ball.y,10,10);
        g.drawLine(p1.x,p1.y,p1.x,p1.y-50);
        g.drawLine(p2.x,p2.y,p2.x,p2.y-50);
        //System.out.println("paint"+x+" "+y);
        }
    }

    public void run() {
            while(check()){
                 repaint();
              try{ Thread.sleep(time);
              waitSuspend();
              }
               catch(InterruptedException e) { }
             }
    }

    public void keyPressed(KeyEvent e) {
        if(directionX==1){
            assert directionX==1;
        if(e.getKeyCode()== KeyEvent.VK_UP) 
            p2.y-=10;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            p2.y+=10;
        }
        else if(directionX==-1){
            assert directionX==-1;
        if(e.getKeyChar()== 'w')
            p1.y-=10;
        if(e.getKeyChar()== 's')
            p1.y+=10;
        }
        else
            System.out.println("None Worked");
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
        
            
        
        
        
    

