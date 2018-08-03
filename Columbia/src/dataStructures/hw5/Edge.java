package dataStructures.hw5;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Mavis Beacon
 */
public class Edge {
    
    private City city1;
    private City city2;
    private double distance = 0;
    private boolean display;

    public Edge(City city1, City city2){
        this.city1 = city1;
        this.city2 = city2;
        calculateDistance();
    }
    
    public double calculateDistance(){
        int x1 = city1.getX();
        int y1 = city1.getY();
        int x2 = city2.getX();
        int y2 = city2.getY();
        
        int diffX = Math.abs( x1 - x2 );
        int diffY = Math.abs( y1 - y2 );
        
        distance = Math.sqrt( Math.pow(diffX, 2) + Math.pow(diffY, 2));
        
        return distance;
    }
    
    public boolean contains(City city){
        if( city.getName().equals(city1.getName()))
            return true;
        if( city.getName().equals(city2.getName()))
            return true;
        return false;
    }
    
    
    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    
    
    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public double getDistance() {
        return distance;
    }
        @Override
    public String toString() {
        return city1.getName() + " " + city2.getName() + " " +  (int)distance ;
    }

   
    
}
