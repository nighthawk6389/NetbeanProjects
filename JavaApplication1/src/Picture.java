/*
 * Picture.java
 *
 * Created on December 3, 2007, 8:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Picture extends JPanel implements KeyListener{
    int x1=0;
    int y1=0;
    int y=200;
    int x=200;
    int ax=0;
    int ay=0;
    Point apple[]= new Point[3];
    int count=3;
    int high=count;
    boolean p=false;
    boolean ate=false;
    int direction=1;
    LinkedList list= new LinkedList();
    int points []=new int[40];
    int speed=550;
    int sh=1;
    String name;
    String size=" ";
    /** Creates a new instance of Picture */
    public Picture() {
        addKeyListener(this);
        setFocusable(true);
        //System.out.println(getHeight()+" height");
        int s=10;
        for(int i=0;i<40;i++){
            points[i]=s;
            s+=10;
        }
        //name= JOptionPane.showInputDialog("Enter Your Name");
    }
    
    
    public void moveSnake(int dir){
        if(!list.check()){
            p=false;
            //toll();
        }


        if(opposite(direction) == dir)
            System.out.println("same direction");
        else{
          // System.out.println("moving "+ count );
           bound();
        switch(dir){
            case 1:y-=10;break; // up
            case 2:y+=10;break;  //down
            case 3:x-=10;break;  //left
            case 4:x+=10;break;  //right
            default:dir=1;
        }
        list.move(x,y);
        

        for(int i=0;i<sh;i++){//System.out.println("applex "+apple.x+"x "+list.getLastNode().x+"appley "+apple.y+"y "+list.getLastNode().y);
            if(apple[i].x==list.getLastNode().x && apple[i].y==list.getLastNode().y){
           // System.out.println("apple");
            count++;
        if(count==25)
            speed-=100;
        if(count==35)
            speed-=100;
                list.addFirst(list.getFirstNode().x,list.getFirstNode().y);
            disp();
            repaint();
            break;
            }
        }
        
        //System.out.println("before move "+x+" "+y );
        direction=dir;
        
         repaint();
        }
    }
    
    public void bound(){
        if(x >= 380)
            x=0;
        else if(x <=0)
            x=380;
        else if(y >=350)
            y=0;
        else if(y <=0)
            y=350;
        //else
            //System.out.println("no change");
    }
    
    public int opposite(int dir){
       
        if(dir==1)
            return 2;
        else if(dir==2)
            return 1;
        else if(dir==3)
            return 4;
        else if(dir==4)
            return 3;
        else{
            System.out.println("Error in Opposite");
            return 1;
        }
    }
    
        public void toll(){
        //System.out.println("toll");
        list=new LinkedList();
        if(!p){
        count=10;
        x=200;
        y=180;
        }
        for(int i=0;i<=count;i++){
            list.addFirst(x,y+i*10);
          }
        disp(); 
        repaint();
    }
    
    public void disp(){
       // System.out.println("disp");
        for(int i=0;i<apple.length;i++){
        ax=(int)(Math.random()*35);
        ay=(int)(Math.random()*32);
        System.out.println(ax+" "+ay);
        apple[i]=new Point(points[ax],points[ay]);
        }
    }  
    
    public void change(){
        String speeds= JOptionPane.showInputDialog(null,"Enter speed","Enter",JOptionPane.QUESTION_MESSAGE);
        speed=Integer.parseInt(speeds);
        p=true;
    }
    public void fun(){
        JOptionPane.showMessageDialog(null,"Congradulations "+name+" Your snake is "+size,"Snake",JOptionPane.INFORMATION_MESSAGE);
    }
        
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //System.out.println(ax+" "+ay+" repaint");
       // g.setColor(Color.RED);
         Node current=list.getFirstNode();
        for(int i=0;i<count;i++){
            x1=current.x;
            y1=current.y;
            //System.out.println(list.getCount()+" "+x1+" "+y1);
            g.fillOval(x1,y1,10,10);
        current=current.next;
        //System.out.println(x +" "+ y);
        }
         if(count>15){
             for(int i=0;i<sh;i++){
             g.fillRect(apple[i].x,apple[i].y,10,10);
             }
             sh=apple.length;
         }
         else
             g.fillRect(apple[0].x,apple[0].y,10,10);
        if(count>=high)
            high=count;
        g.drawString("Score= "+count*100+" High Score= "+high+"              Speed= "+speed,10,10);
        //setFocusable(true);
                for(int i=0;i<speed;i++)
            System.out.println(i);
        
        if(p)
            moveSnake(direction);
    }
  
    public void keyTyped(KeyEvent e) {
       // System.out.println("key1q");
      
    }

    public void keyPressed(KeyEvent e) {
       // System.out.println("keyq");
        int dir=1;
        char keyChar='A';
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:dir=1;break; //up
            case KeyEvent.VK_DOWN:dir=2;break; //down
            case KeyEvent.VK_LEFT:dir=3;break;  // left
            case KeyEvent.VK_RIGHT:dir=4;break; //right
            case KeyEvent.VK_ENTER:p=!p;break;
            case KeyEvent.VK_BACK_SPACE:p=false;change();break;
            default:keyChar=e.getKeyChar();
        }
                for(int i=0;i<speed;i++)
            System.out.println(i);
        if(p)
            moveSnake(dir);
    }

    public void keyReleased(KeyEvent e) {
        //System.out.println("key2q");
    }
    
}
    