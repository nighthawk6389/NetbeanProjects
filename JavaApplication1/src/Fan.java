import java.awt.*;
import javax.swing.*;
/*
 * Fan.java
 *
 * Created on September 28, 2007, 12:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Fan extends JPanel implements Runnable{
        int speed;
        boolean on;
        double radius;
        String color;
        int go;
        int direction;
        boolean suspend;
        
        
    public static void main(String args []){
        JFrame frame= new JFrame();
        Fan panel= new Fan();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400,400);
        frame.getContentPane().add(panel);
                
      
    }
    /** Creates a new instance of Fan */
    public Fan() {
        speed=100;
        on=true;
        radius=1;
        color="red";
        go=10;
        direction=10;
        suspend=false;
        new Thread(this).start();
       // runner.start();
        //repaint();
    }
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        System.out.println(speed);
        this.speed=100/speed;
    }
    public boolean isOn(){
        return on;
    }
    public void setOn(boolean on){
        this.on=on;
    }
    public double getRadius(){
        return radius;
    }
    public void setRadius(double radius){
        this.radius=radius;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color=color;
    }
    public String toString(){
        return "Speed= "+speed+" on= "+on+" radius= "+radius+" color= "+color;
    }
    
    public void reverse(){
       direction=-(direction);
    }
    
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int y=getHeight()-getHeight()/4;
        int x= getWidth()-getWidth()/4;;
        int b=(int)(getHeight()-getHeight()*(.90));
        int e=(int)(getWidth()-getWidth()*(.87)); 
        int mult=(int)(2.7*Math.abs(go));
        int random=(int)(Math.random()*250);
        int random1=(int)(Math.random()*250);
        Color color= new Color(mult,random,random1);
       // g.fillOval(e-5,b-5,x+10,y+10);
        g.setColor(color);
        //System.out.println("main");
        //System.out.println("paint"+go+" "+30+go);   
        g.fillArc(e,b,x,y,0+go,30);
        g.fillArc(e,b,x,y,90+go,30);
        g.fillArc(e,b,x,y,180+go,30);
        g.fillArc(e,b,x,y,270+go,30);
        g.drawOval(e-5,b-5,x+10,y+10);
        g.fillOval(10,10,10,10);
        if(go==90 || go==-90)
            go=0;
       // g.drawLine(0,0,50,50); 
        
    } 

    public void run() {
        while(on){
            go+=direction;
            repaint();
            try{
                Thread.sleep(speed);
                waitSuspend();
            }
            catch(InterruptedException e){ }
        }
        System.out.println("finished");
    }
    public synchronized void waitSuspend() throws InterruptedException{
        if(suspend)
            wait();
    }
    public synchronized void resume() throws InterruptedException{
        if(suspend){
            suspend=false;
            notify();
        }
    }
}
    
    
    
        
        
    
    

