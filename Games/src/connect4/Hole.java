
package connect4;

import java.awt.*;
import java.awt.event.*;
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
public class Hole extends JPanel implements MouseListener{
   private int x;
   private int y;
   private  int filled;
   private boolean inUse;
   Board b;
    /** Creates a new instance of Hole */
    public Hole(int filled,int x,int y) {
        this.x=x;
        this.y=y;
        this.filled=filled;
        //this.b=b;
        inUse=false;
        this.setBorder(new LineBorder(Color.BLACK,1));
        addMouseListener(this);

        repaint();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(filled==1){
            g.setColor(Color.white);
            g.fillOval(0, 0, this.getSize().width, this.getSize().height);
            g.setColor(Color.black);
            g.drawOval(0, 0, this.getSize().width, this.getSize().height);
        }
        else if(filled==2){
            g.setColor(Color.black);
            g.fillOval(0, 0, this.getSize().width, this.getSize().height);
        }
        else{
            g.drawOval(0, 0, this.getSize().width, this.getSize().height);

        }
        //g.drawString(id+"",10,20);
    }
    public void setFilled(int filled){
       this.filled=filled;
       repaint();
    }
    public int getFilled(){
        return filled;
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
