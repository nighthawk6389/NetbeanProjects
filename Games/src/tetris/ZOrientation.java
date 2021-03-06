/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author Mavis Beacon
 */
public class ZOrientation extends Orientation{
 int r;
    public ZOrientation(){
        r=Orientation.UP;
    }
    public ZOrientation(int r){
        this.r=r;
    }
    @Override
    public Shape rotate(int o,Shape shape) {
        if(o==Orientation.UP){
            shape=rotateUp(shape);
        }
        else if(o==Orientation.RIGHT){
            shape=rotateRight(shape);
        }
        r=o;
        return shape;
    }

    public Shape rotateNext(Shape shape){
        if(r==Orientation.UP){
            return rotate(Orientation.RIGHT,shape);
        }
        else{
            return rotate(Orientation.UP,shape);
        }
    }

    public int getR(){
        return this.r;
    }
    private Shape rotateUp(Shape shape) {
        ShapeComponent [] sc=shape.getShapeComponents();
        sc[1].setCorner((int)(sc[0].getX()+shape.size), (int)(sc[0].getY()));
        sc[2].setCorner((int)sc[0].getX()+shape.size, (int)(sc[0].getY()+shape.size));
        sc[3].setCorner((int)sc[0].getX()+shape.size*2, (int)(sc[0].getY()+shape.size));
        shape.setShapeComponents(sc);
        return shape;
    }

    private Shape rotateRight(Shape shape) {
        ShapeComponent [] sc=shape.getShapeComponents();
        sc[1].setCorner((int)(sc[0].getX()+shape.size), (int)(sc[0].getY()));
        sc[2].setCorner((int)sc[0].getX(), (int)(sc[0].getY()+shape.size));
        sc[3].setCorner((int)sc[0].getX()+shape.size, (int)(sc[0].getY()-shape.size));
        shape.setShapeComponents(sc);
        return shape;
    }
}
