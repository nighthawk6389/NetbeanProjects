/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SlimeBall;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Mavis Beacon
 */
public class AnimatedObject {

    int xPos;
    int yPos;
    int xVelocity;
    int yVelocity;
    int currentYVelocity;
    double time=0;
    double timeSinceLast=0;
    double timeDiff=0;
    int acceleration=5;
    int size=50;
    int yOffset=size;
    Color color=Color.BLACK;
    private final double SPEED=.05;
    public AnimatedObject(){
        xPos=10;
        yPos=10;
        xVelocity=0;
        yVelocity=0;
    }//end animatedOBject

    public AnimatedObject(int xPos,int yPos){
        this.xPos=xPos;
        this.yPos=yPos;
    }

    public Point getPosition(){
        return new Point(xPos,yPos);
    }//end getPos

    public void setPosition(int xPos,int yPos){
        this.xPos=xPos;
        this.yPos=yPos;
    }
    public void setPosition(Point p){
        this.xPos=p.x;
        this.yPos=p.y;
    }

    public int getXVelocity(){
        return this.xVelocity;
    }
    public void setXVelocity(int xVelocity){
        this.xVelocity=xVelocity;
    }
    public void setDecreaseXVelocity(int newXVel){
        if(this.xVelocity<0)
            this.xVelocity+=newXVel;
        else if(this.xVelocity>0){
            this.xVelocity-=newXVel;
        }
        else
            this.xVelocity=0;
    }//end SDXV
    public int getYVelocity(){
        return this.currentYVelocity;
    }
    public void setYVelocity(int yVelocity){
        this.yVelocity=yVelocity;
    }
     public int getYOffset(){
        return this.yOffset;
    }
    public void setYOffset(int yOffset){
        this.yOffset=yOffset;
    }
    public void setAcceleration(int acceleration){
        this.acceleration=acceleration;
    }
    public int getAcceleration(){
        return this.acceleration;
    }
    public void setSize(int size){
        this.size=size;
    }
    public int getSize(){
        return this.size;
    }
    public void setTime(double time){
        this.time=time;
    }
    public double getTime(){
        return this.time;
    }
    public void setColor(Color color){
        this.color=color;
    }
    public boolean checkCollision(AnimatedObject obj){
        if(this.size>obj.size && this.xPos>obj.xPos){
            return checkLeftSideCollision(obj);
        }
        else if(this.size>obj.size && this.xPos<obj.xPos){
            return checkRightSideCollision(obj);
        }
        else if(this.size<obj.size && this.xPos>obj.xPos){
            return checkRightSideCollision(obj);
        }
        else if(this.size<obj.size && this.xPos<obj.xPos){
            return checkLeftSideCollision(obj);
        }
        return false;
    }//end checkCollision

    private boolean checkLeftSideCollision(AnimatedObject obj){
        if(Math.abs(this.xPos-obj.xPos)<this.size && Math.abs(this.yPos-obj.yPos)<obj.size/2)
            return true;
        return false;
    }//end checkLeftCollision

    private boolean checkRightSideCollision(AnimatedObject obj){
        if(Math.abs(this.xPos-obj.xPos)<obj.size && Math.abs(this.yPos-obj.yPos)<obj.size/2)
            return true;
        return false;
    }//end checkLeftCollision

    public boolean move(){
        boolean h=true;
        boolean v=true;
        if(xVelocity!=0)
            h=moveHorizontal();
        //if(yVelocity!=0)
            v=moveVertical();
        if(yVelocity==0 && currentYVelocity==0)
            this.xVelocity=0;
        return (v && h);
    }
    public boolean moveHorizontal(){
        if(xVelocity==0)
            return false;
        this.xPos=this.xPos+this.xVelocity;
        return true;
    }//end moveHorizontal

    public boolean moveVertical(){
        /*
        if(checkTimeReversal()){
            this.time=1.5;
            this.yVelocity=0;
            System.out.println("killing time");
        }
         * */
        //this.timeDiff=this.time-this.timeSinceLast;
        this.yPos=(int)(this.yPos+(this.yVelocity*this.time)+(this.acceleration*(this.time*this.time)));
        this.currentYVelocity=(int)(this.yVelocity+this.acceleration*this.time);
        //System.out.println("YPos: "+this.yPos+" yVelocity: "+this.yVelocity+" Time: "+this.time+" CurrYVel: "+this.currentYVelocity);
        this.time=this.time+SPEED;
        return true;
    }//end move vertical
    
   private boolean checkTimeReversal(){
        if(Math.abs(this.currentYVelocity)<5 && this.yVelocity<0)
            return true;
        return false;
    }//end checkTimeReversal



}
