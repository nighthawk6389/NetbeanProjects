/*
 * Shape.java
 *
 * Created on November 19, 2009, 5:39 PM
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
public abstract class Shape {
    ShapeComponent [] sc;
    Orientation orient;
    public int size;
    /** Creates a new instance of Shape */
    public Shape() { 
    }
    
    public abstract ShapeComponent[] getShapeComponents();
    public abstract void setShapeComponents(ShapeComponent[] sc);
    public abstract Orientation getOrientation();
    public abstract void setOrientation(Orientation o);
}
