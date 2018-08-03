/*
 * TTTControl.java
 *
 * Created on December 1, 2009, 6:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tetris;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author elkobi
 */
public class TTTControl extends JPanel implements Runnable,KeyListener{
    JPanel game,buttons;
    JButton button;
    Block [] block;
    int index;
    int lowest;
    Shape [] shape=new Shape[4];
    private static int REGULAR=200;
    private static int FAST=50;
    private static int TIME=REGULAR;
    private static final int AMOUNTOFBLOCKS=10000;
    private static final int MOVE=15;
    private static final int SIZEOFBLOCK=15;
    /** Creates a new instance of TTTControl */
    public TTTControl() {
        index=0;
        block=new Block[AMOUNTOFBLOCKS];
        block[index]=new Block(SIZEOFBLOCK);

        resetShapes();
        
        this.addKeyListener(this);
        this.setFocusable(true);
        new Thread(this).start();
    }

    public void run() {
        while(index<AMOUNTOFBLOCKS){
          //  System.out.println("Index= "+index);
            if(checkBounds()!=false && checkOverlap()!=false){
              //  System.out.println("moving");
                block[index].moveDown(MOVE);
                System.out.println("Block: "+block[index].getShape().getShapeComponents()[0].x);
            }
            else{
                if(checkGameOver())
                    break;
                getNextBlock();
            }
            checkFullLine();
            repaint();
            try{ 
            Thread.sleep(TIME);
            }
            catch(InterruptedException e) { }
            }
        System.out.println("Game Over");
    }
    
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int x=0;x<=index;x++)
            for(ShapeComponent s:block[x].getShape().getShapeComponents()){
                if(s.visible){
                    g.setColor(block[x].getColor());
                    g.fillRect((int)s.getX(),(int)s.getY(),SIZEOFBLOCK,SIZEOFBLOCK);
                    g.setColor(Color.black);
                    g.drawRect((int)s.getX(),(int)s.getY(),SIZEOFBLOCK,SIZEOFBLOCK);
                    
                }
               // g.drawString(s.getX()+" "+s.getY(), (int)s.getX(), (int)s.getY());
                g.setColor(Color.WHITE);
                g.drawLine((int)s.getX(),(int)s.getY(), (int)s.getX(),(int)s.getY()+getHeight());
                g.drawLine((int)s.getX()+SIZEOFBLOCK,(int)s.getY(), (int)s.getX()+SIZEOFBLOCK,(int)s.getY()+getHeight());
        }
    }

    private boolean checkBounds() {
       ShapeComponent [] sc=block[index].getShape().getShapeComponents();
       int height=this.getHeight()>5?this.getHeight():SIZEOFBLOCK*10;
       int width=this.getWidth();
       
       for(ShapeComponent s:sc){
           int sY=(int)s.getY();
           if(sY>height-(SIZEOFBLOCK+MOVE)){
               System.out.println("Bad Y: "+s.getY()+" while container is"+height);
               if(index==0)
                   lowest=sY;
               return false;
           }
           if(s.getX()>width)
               block[index].moveLeft(MOVE);
           if(s.getX()<0)
               block[index].moveRight(MOVE);
       }
       //System.out.println("Checkbounds==true");
       return true;
    }//end checkBounds

   private boolean checkOverlap() {
       ShapeComponent [] i=block[index].getShape().getShapeComponents();
       for(int x=0;x<index;x++){
            for(ShapeComponent s:block[x].getShape().getShapeComponents()){
                if(s.visible==false)
                    break;
                for(ShapeComponent in:i){
                    if(Math.abs(in.getX()-s.getX())<=SIZEOFBLOCK-1 && Math.abs(in.getY()-s.getY())<=SIZEOFBLOCK){
                        System.out.println("Returning false from checkOverlap");
                        return false;
                    }
                }
            }
        }
      // System.out.println("Returning TRUE from checkOverlap");
       return true;
   }//end checkOverlap

    private void checkFullLine() {
        //System.out.println("The height is= "+getHeight()+" Lowest is"+lowest);
        int [] count=new int[getHeight()];
        for(int x=0;x<index;x++){
            for(ShapeComponent s:block[x].getShape().getShapeComponents()){
                if(s.visible && s.getY()<=getHeight())
                     count[(int)s.getY()]++;
            }
        }
        for(int x=0;x<count.length;x++){
            if(count[x]>=getWidth()/SIZEOFBLOCK){
                System.out.println("GOING TO DELETE LINE");
                deleteLine(x);
            }
            //System.out.println("Count= "+count[x]+"of index= "+x+" while width/size is "+(getWidth()/SIZEOFBLOCK));
        }
    }//end checkFullLine

     private void deleteLine(int line) {
        for(int x=0;x<index;x++){
            for(ShapeComponent s:block[x].getShape().getShapeComponents()){
                if(s.getY()==line)
                    s.visible=false;
            }
        }
        moveDownAll(line);
    }//end deleteLine

     public void moveDownAll(int line){
         System.out.println("MOVING LINE DOWN");
         for(int x=0;x<index;x++){
            for(ShapeComponent s:block[x].getShape().getShapeComponents()){
                if(s.getY()<=line)
                    s.moveDown(MOVE);
            }
        }
     }//end moveDownAll
     private void getNextBlock() {
        index++;
        resetShapes();
        int rand=(int)(Math.random()*4);
        if(rand==4)
            rand=1;
        block[index]=new Block(shape[rand]);
        if(rand==0)
            block[index].setColor(Color.black);
        else if(rand==1)
            block[index].setColor(Color.red);
        else if(rand==2)
            block[index].setColor(Color.green);
        else if(rand==3)
            block[index].setColor(Color.blue);

        int num=(this.getWidth()/2)/15;
          block[index].moveRight(15*num);
        ShapeComponent [] sc=block[index].getShape().getShapeComponents();
        
    }
      private void resetShapes() {
        shape[0]=new BlockShape(SIZEOFBLOCK);
        shape[1]=new LineShape(SIZEOFBLOCK);
        shape[2]=new TShape(SIZEOFBLOCK);
        shape[3]=new ZShape(SIZEOFBLOCK);
    }
      private boolean checkGameOver(){
           for(int x=0;x<index;x++){
            for(ShapeComponent s:block[x].getShape().getShapeComponents()){
               // System.out.println(s.getY());
                if(s.getY()<=0)
                    return true;
            }
           }
           return false;
      }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            block[index].moveRight(MOVE);
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
            block[index].moveLeft(MOVE);
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            TIME=FAST;
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
            block[index].rotate();
        if(e.getKeyCode()==KeyEvent.VK_Q)
            this.deleteLine(lowest);
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
            TIME= TIME==1000?REGULAR:1000;
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            TIME=REGULAR;
    }
  
}