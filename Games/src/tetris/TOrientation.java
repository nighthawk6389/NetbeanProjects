/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author Mavis Beacon
 */
public class TOrientation extends Orientation {
    int r;
    public TOrientation(){
        r=Orientation.UP;
    }
    public TOrientation(int r){
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
        else if(o==Orientation.DOWN){
            shape=rotateDown(shape);
        }
        else if(o==Orientation.LEFT){
            shape=rotateLeft(shape);
        }
        r=o;
        return shape;
    }

    public Shape rotateNext(Shape shape) {
        if(r==Orientation.UP){
            return rotate(Orientation.RIGHT,shape);
        }
        else if(r==Orientation.RIGHT){
            return rotate(Orientation.DOWN,shape);
        }
        else if(r==Orientation.DOWN){
            return rotate(Orientation.LEFT,shape);
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
        sc[1].setCorner((int)sc[0].getX()-shape.size, (int)(sc[0].getY()));
        sc[2].setCorner((int)sc[0].getX()+shape.size, (int)(sc[0].getY()));
        sc[3].setCorner((int)sc[0].getX(), (int)(sc[0].getY()-shape.size));
        shape.setShapeComponents(sc);
        return shape;
    }

    private Shape rotateRight(Shape shape) {
        ShapeComponent [] sc=shape.getShapeComponents();
        sc[1].setCorner((int)sc[0].getX(), (int)(sc[0].getY()+shape.size));
        sc[2].setCorner((int)sc[0].getX(), (int)(sc[0].getY()-shape.size));
        sc[3].setCorner((int)sc[0].getX()+shape.size, (int)(sc[0].getY()));
        shape.setShapeComponents(sc);
        return shape;
    }

    private Shape rotateDown(Shape shape) {
        ShapeComponent [] sc=shape.getShapeComponents();
        sc[1].setCorner((int)sc[0].getX()-shape.size, (int)(sc[0].getY()));
        sc[2].setCorner((int)sc[0].getX()+shape.size, (int)(sc[0].getY()));
        sc[3].setCorner((int)sc[0].getX(), (int)(sc[0].getY()+shape.size));
        shape.setShapeComponents(sc);
        return shape;
    }

    private Shape rotateLeft(Shape shape) {
        ShapeComponent [] sc=shape.getShapeComponents();
        sc[1].setCorner((int)sc[0].getX(), (int)(sc[0].getY()+shape.size));
        sc[2].setCorner((int)sc[0].getX(), (int)(sc[0].getY()-shape.size));
        sc[3].setCorner((int)sc[0].getX()-shape.size, (int)(sc[0].getY()));
        shape.setShapeComponents(sc);
        return shape;
    }
}
