/*
 * Orientation.java
 *
 * Created on November 19, 2009, 5:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author elkobi
 */
public abstract class Orientation {
    public static final int UP=0;
    public static final int RIGHT=1;
    public static final int DOWN=2;
    public static final int LEFT=3;
    /** Creates a new instance of Orientation */
    public Orientation() {
    }

    public abstract Shape rotate(int o,Shape shape);
    public abstract Shape rotateNext(Shape shape);

    
}
