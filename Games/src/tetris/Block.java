/*
 * Block.java
 *
 * Created on November 19, 2009, 5:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;

/**
 *
 * @author elkobi
 */
public class Block {
    Color color=Color.black;
    Shape shape;
    /** Creates a new instance of Block */
    public Block() {
        shape=new LineShape();
    }
    public Block(int size) {
        shape=new LineShape(size);
    }
    public Block(Shape s) {
        this.shape=s;
    }
    public Color getColor(){
        return this.color;
    }
    public void setColor(Color c){
        this.color=c;
    }
    public Shape getShape(){
        return this.shape;
    }
    public void setShape(Shape s){
        this.shape=s;
    }
    public void rotate(){
        if(shape.getOrientation()!=null)
            this.shape=shape.getOrientation().rotateNext(shape);
    }

    public void moveLeft(int amount){
        ShapeComponent sc[]=shape.getShapeComponents();
        for(ShapeComponent s:sc)
            s.moveLeft(amount);
    }
    public void moveRight(int amount){
        ShapeComponent sc[]=shape.getShapeComponents();
        for(ShapeComponent s:sc)
            s.moveRight(amount);
    }
    public void moveDown(int amount){
        ShapeComponent sc[]=shape.getShapeComponents();
        for(ShapeComponent s:sc)
            s.moveDown(amount);
    }
    
}//end block
