import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.LineBorder;
/*
 * Hole.java
 *
 * Created on November 24, 2008, 3:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Hole extends JPanel{
   private int id;
   private  boolean filled;
   private boolean inUse;
   private boolean isAI;
   private boolean isHit;
   private boolean isMiss;
   private boolean isFriendlyHit;
   private boolean isComputerMiss;
    /** Creates a new instance of Hole */
    public Hole(int id) {
        this.id= id;
        filled=false;
        inUse=false;
        isAI=false;
        isHit=false;
        isFriendlyHit=false;
        isMiss=false;
        this.setBorder(new LineBorder(Color.BLACK,1));
        //addMouseListener(this);
    }

    public void resetHole(){
        filled=false;
        inUse=false;
        isAI=false;
        isHit=false;
        isFriendlyHit=false;
        isMiss=false;
        repaint();
    }//end resetHole
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(inUse && !isAI)
            g.setColor(Color.red);
        else if (inUse && isAI)
            g.setColor(Color.green);

        if(isHit){
            g.setColor(Color.GREEN);
        }
        if(isFriendlyHit){
            g.setColor(Color.black);
        }

        if(filled || (inUse && isHit) ){
            g.fillRect(0, 0, this.getSize().width, this.getSize().height);
            //g.fillOval(((int)(getSize().height/2)*8/10), ((int)(getSize().width/2)*8/10),5,5);
        }
        if(isMiss){
            //System.out.println("In miss");
            g.setColor(Color.YELLOW);
            g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        }
        if(isComputerMiss){
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        }
            //g.drawOval((int)(getSize().height/2)*8/10, (int)(getSize().width/2)*8/10,5,5);
        //g.drawString(id+"",10,20);
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setFilled(boolean filled){
        if(inUse)
            filled=true;
        else
            this.filled=filled;
       repaint();
    }
    public boolean getFilled(){
        return filled;
    }
    public void setInUse(boolean inUse){
        this.inUse=inUse;
    }
    public boolean getInUse(){
        return inUse;
    }
    public void setAI(boolean isAI){
        this.isAI=isAI;
    }
    public boolean getAI(){
        return this.isAI;
    }
    public void setHit(boolean isHit){
        this.isHit=isHit;
        repaint();
    }
    public boolean getHit(){
        return isHit;
    }
    public void setFriendlyHit(boolean isFriendlyHit){
        this.isFriendlyHit=isFriendlyHit;
        repaint();
    }
    public boolean getFriendlyHit(){
        return isFriendlyHit;
    }
    public void setComputerMiss(boolean isComputerMiss){
        this.isComputerMiss=isComputerMiss;
        repaint();
    }
    public boolean getComputerMiss(){
        return isComputerMiss;
    }
    public void setMiss(boolean miss){
        this.isMiss=miss;
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("You clicked me! "+getLocation()+" My size is: "+this.getSize()); 
        //filled=true;
       // repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
      //  filled=false;
        //repaint();
    }
    
}
