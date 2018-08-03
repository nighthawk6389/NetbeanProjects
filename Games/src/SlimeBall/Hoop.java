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
class Hoop {
    int xPos;
    int yPos;
    int sizeOfHoop=10;

    public Hoop(){
        xPos=0;yPos=0;
    }//end hoop

    public Hoop(int x,int y){
        this.xPos=x;
        this.yPos=y;
    }
    public void setPoint(int x,int y){
        this.xPos=x;
        this.yPos=y;
    }
    
    public void setSizeOfHoop(int size){
        this.sizeOfHoop=size;
    }//end setSizeOfHoop

    public boolean checkScored(Point p){
        if( Math.abs(p.x-this.xPos)<this.sizeOfHoop && Math.abs(p.y-this.yPos)<this.sizeOfHoop )
            return true;
        return false;
    }//end checkScored

}
