/*
 * ZShape.java
 *
 * Created on November 19, 2009, 5:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author elkobi
 */
public class ZShape extends Shape {
    ShapeComponent [] sc;
    Orientation orient=new ZOrientation();
    /** Creates a new instance of ZShape */
    public ZShape() {
        sc=new ShapeComponent[4];
        sc[0]=new ShapeComponent(0,0);
        sc[1]=new ShapeComponent(5,0);
        sc[2]=new ShapeComponent(5,5);
        sc[3]=new ShapeComponent(10,5);
        size=5;
    }
    public ZShape(int size) {
        sc=new ShapeComponent[4];
        sc[0]=new ShapeComponent(0,0);
        sc[1]=new ShapeComponent(size,0);
        sc[2]=new ShapeComponent(size,size);
        sc[3]=new ShapeComponent(size*2,size);
        this.size=size;
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
