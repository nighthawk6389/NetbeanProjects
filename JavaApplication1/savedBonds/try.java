public class Triangle{ 
    private double side1,side2,side3; 
  
     public static void main(String args []){ 
        Triangle tri = null; 
        try { 
            tri = new Triangle(6, 2, 4); 
        } catch (IllegalTriangleException ex) { 
            ex.printStackTrace();  
        } 
        //} 
        //catch(IllegalTriangleException ex){ 
          //  System.out.println(ex); 
        //} 
        System.out.println(tri); 
    } 
     
    
    public Triangle(double side1,double side2,double side3) throws IllegalTriangleException{ 
        if(side1+side2>side3&&side1+side3>side2&&side2+side3>side1){ 
            System.out.println("good"); 
        this.side1=side1; 
        this.side2=side2; 
        this.side3=side3; 
        } 
        else{ 
           System.out.println("error");  
             throw new IllegalTriangleException("Illegal Triangle"); 
        } 
    } 
    public double findArea(){ 
        double s=(side1+side2+side3)/2; 
        double area=(Math.sqrt(s*(s-side1)*(s-side2)*(s-side3))); 
        return area; 
    } 
    public double findPerimeter(){ 
        return side1+side2+side3; 
    } 
    public String toString(){ 
        return side1+" "+side2+" "+side3; 
    } 
} 
