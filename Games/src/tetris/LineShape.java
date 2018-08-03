/*
 * LineShape.java
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
public class LineShape extends Shape {
    ShapeComponent [] sc;
    Orientation orient=new LineOrientation();
    /** Creates a new instance of LineShape */
    public LineShape() {
        sc=new ShapeComponent[4];
        sc[0]=new ShapeComponent(0,0);
        sc[1]=new ShapeComponent(0,5);
        sc[2]=new ShapeComponent(0,10);
        sc[3]=new ShapeComponent(0,15);
        size=5;
    }
     public LineShape(int size) {
        sc=new ShapeComponent[4];
        sc[0]=new ShapeComponent(0,0);
        sc[1]=new ShapeComponent(0,size);
        sc[2]=new ShapeComponent(0,size*2);
        sc[3]=new ShapeComponent(0,size*3);
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
