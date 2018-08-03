/*
 * Building.java
 *
 * Created on August 29, 2009, 9:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Building {
    int floors;
    int numOfElevator;
    Elevator elevators[];
    /** Creates a new instance of Building */
    public Building() {
        floors=10;
        numOfElevator=3;
        elevators=new Elevator[3];
        initializeBuilding();
    }
    public Building(int floors, int numOfElevator){
        this.floors=floors;
        this.numOfElevator=numOfElevator;
        elevators=new Elevator[numOfElevator];
        initializeBuilding();
    }
    
    public int getFloors(){
        return floors;
    }
    public int getNumOfElevator(){
        return numOfElevator;
    }
    public void setFloors(int floors){
        this.floors=floors;
    }
    public void setNumOfElevator(int numOfElevator){
        this.numOfElevator=numOfElevator;
    }
    public Elevator[] getElevators(){
        return this.elevators;
    } 
    public void initializeBuilding(){
        for(int x=0;x<elevators.length;x++)
            elevators[x]=new Elevator(x);
    }
}
