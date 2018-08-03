/*
 * BlockShape.java
 *
 * Created on November 19, 2009, 5:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author elkobi
 */
public class BlockShape extends Shape {
    ShapeComponent [] sc;
    Orientation orient=null;
    /** Creates a new instance of BlockShape */
    public BlockShape() {
        sc=new ShapeComponent[4];
        sc[0]=new ShapeComponent(0,0);
        sc[1]=new ShapeComponent(5,0);
        sc[2]=new ShapeComponent(0,5);
        sc[3]=new ShapeComponent(5,5);
    }
    public BlockShape(int size) {
        sc=new ShapeComponent[4];
        sc[0]=new ShapeComponent(0,0);
        sc[1]=new ShapeComponent(size,0);
        sc[2]=new ShapeComponent(0,size);
        sc[3]=new ShapeComponent(size,size);
    }

    public ShapeComponent[] getShapeComponents() {
        return this.sc;
    }

    public void setShapeComponents(ShapeComponent[] sc) {
        this.sc=sc;
    }

    public Orientation getOrientation() {
        return orient;
    }

    public void setOrientation(Orientation o) {
        this.orient=o;
    }
    
}
