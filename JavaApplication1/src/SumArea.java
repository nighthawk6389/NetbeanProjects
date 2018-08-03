public class SumArea{
    public static void main(String args []){
        Circle circle= new Circle(5);
        Cylinder cylinder = new Cylinder(5,2);
        GeometricObject rectangle= new Rectangle(4,3);
        
        GeometricObject [] a=new GeometricObject[3];
        a[0]=circle;
        a[1]=cylinder;
        a[2]=rectangle;
        
        System.out.println(GeometricObject.sumArea(a));
    }
}
