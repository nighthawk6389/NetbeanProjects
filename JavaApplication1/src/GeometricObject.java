/*
 * GeometricObject.java
 *
 * Created on October 18, 2007, 10:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public abstract class GeometricObject{
    private String color="white";
    private boolean filled;
    
    /** Creates a new instance of GeometricObject */
    protected GeometricObject() {
    }
    
    protected GeometricObject(String color,boolean filled){
        this.color=color;
        this.filled=filled;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color=color;
    }
    public boolean isFilled(){
        return filled;
    }
    public void setFilled(boolean filled){
        this.filled=filled;
    }
    public abstract double findArea();
    
    public abstract double findPerimeter();
   
    
    public static double sumArea(GeometricObject[] a){
        double sum=0;
        for(int x=0;x<a.length;x++){
            sum+=a[x].findArea();
        }
        return sum;
    }
}
class Circle extends GeometricObject {
    private double radius;
    
    public Circle(){
        this.radius=1;
    }
    public Circle(double radius){
        super();
        this.radius=radius;
    }
    public Circle(double radius,String color,boolean filled){
        super(color,filled);
        this.radius=radius;
    }
    public double getRadius(){
        return radius;
    }
    public void setRadius(double radius){
        this.radius=radius;
    }
    public double findArea(){
        return radius*radius*Math.PI;
    }
    public double findPerimeter(){
        return 2*radius*Math.PI;
    }
    public String toString(){
        return "[Circle] radius= "+radius;
    }
}
class Rectangle extends GeometricObject{
    private double width;
    private double height;
    
    public Rectangle(){
        super();
        width=1;
        height=1;
    }
    public Rectangle(double width,double height){
        super();
        this.width=width;
        this.height=height;
    }
    public Rectangle(double width,double height,String color,boolean filled){
        super(color,filled);
        this.height=height;
        this.width=width;
    }
    public double getWidth(){
        return width;
    }
    public void setWidth(double width){
        this.width=width;
    }
    public double getHeight(){
        return height;
    }
    public void setHeight(double height){
        this.height=height;
    }
    public double findArea(){
        return width*height;
    }
    public double findPerimeter(){
        return 2*(width+height);
    }
    public String toString(){
        return "[Rectangle] width= "+width+" and height= "+height;
    }
}
class Cylinder extends Circle{
    private double length;
    
    public Cylinder(){
        this(1.0,1.0);
    }
    public Cylinder(double radius,double length){
        super(radius);
        this.length=length;
    }
    public Cylinder(double radius,String color,boolean filled,double length){
        super(radius,color,filled);
        this.length=length;
    }
    public double getLength(){
        return length;
    }
    public void setLength(double length){
        this.length=length;
    }
    public double findArea(){
        return 2*super.findArea() + 2*getRadius()*Math.PI*length;
    }
    public double findVolume(){
        return super.findArea()*length;
    }
    public String toString(){
        return "[Cylinder] radius= "+getRadius()+" and length "+length;
    }
}
    