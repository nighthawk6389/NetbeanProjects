/*
 * ShapeComponent.java
 *
 * Created on November 19, 2009, 6:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tetris;

import java.awt.Point;

/**
 *
 * @author elkobi
 */
public class ShapeComponent extends Point{
    /** Creates a new instance of ShapeComponent */
    int x;
    int y;
    boolean visible;
    public ShapeComponent() {
        x=0;
        y=0;
        visible=true;
    }
    public ShapeComponent(Point corner) {
        this.setCorner(corner);
        visible=true;
    }
    public ShapeComponent(int x, int y) {
        this.x=x;
        this.y=y;
        visible=true;
    }
    public Point getCorner(){
        return this;
    }
    public void setCorner(Point corner){
        this.x=(int)corner.getX();
        this.y=(int)corner.getY();
    }
     public void setCorner(int x,int y){
        this.x=x;
        this.y=y;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
       this.y=y;
    }
    public void moveRight(int amount){
        this.x=this.x+amount;
    }
    public void moveLeft(int amount){
        this.x=this.x-amount;
    }
    public void moveDown(int amount){
        this.y=this.y+amount;
    }

}
