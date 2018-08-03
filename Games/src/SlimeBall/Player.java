/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SlimeBall;

import java.awt.Point;

/**
 *
 * @author Mavis Beacon
 */
public class Player extends AnimatedObject{
    final private String shape="Semi";

    public Player(){
        super();
    }//end constructor

    public Player(int x,int y){
        super(x,y);
    }

     public String getShape(){
        return shape;
    }
     public void setYCollision(Point p){
       super.yPos=p.y;
       this.yVelocity=0;
       this.currentYVelocity=0;
       this.setTime(0);
     }
     public void setYMoving(int yVelocity){
         if(this.yVelocity!=0)
               return;
         this.setTime(1.5);
         this.yPos=this.yPos-this.yOffset;
         this.setYVelocity(yVelocity);
     }//end set Moving
}
