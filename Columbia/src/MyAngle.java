
import java.awt.Point;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class MyAngle implements Comparable{
    
    double angle;
    Point p;
    
    public MyAngle(double angle, Point p){
        this.angle = angle;
        this.p = p;
    }

    @Override
    public int compareTo(Object o) {
        if( o.getClass() != MyAngle.class)
            return 0;
        MyAngle ang = (MyAngle)o;
        if( this.angle < ang.angle)
            return -1;
        if( this.angle > ang.angle)
            return 1;
        return 0;
    }
    
    public String toString(){
        return this.p + " "; 
    }
    
    

}
