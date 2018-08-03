/*
 * Elevator.java
 *
 * Created on June 28, 2009, 5:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Elevator {
    boolean called;
    int floor;
    String direction;
    int id;
    int floorCalled;
    /** Creates a new instance of Elevator */
    public Elevator() {
        called=false;
        floor=0;
        direction="none";
        id=1;
        floorCalled=-1;
    }
    public Elevator(int id){
        called=false;
        floor=0;
        direction="none";
        floorCalled=-1;
        this.id=id;
    }
    public boolean getCalled(){
        return called;
    }
    public int getFloor(){
        return floor;
    }
    public String getDirection(){
        return direction;
    }
    public void setCalled(boolean called){
        this.called=called;
    }
    public void setFloor(int floor){
        this.floor=floor;
    }
    public void setDirection(String direction){
        this.direction=direction;
    }
    public int getId(){
        return this.id;
    }
    public int getFloorCalled(){
        return floorCalled;
    }
    public void setFloorCalled(int floorCalled){
        this.floorCalled=floorCalled;
    }
    public String toString(){
        return ("Elevator=[Id:"+id+" Called:"+called+" Direction:"+direction+" Floor:"+floor+" FloorCalled:"+floorCalled+"]");
    }
}
