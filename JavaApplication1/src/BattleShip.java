import java.awt.Point;
/*
 * BattleShip.java
 *
 * Created on November 24, 2008, 3:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class BattleShip {
    int size;
    boolean used;
    boolean destroyed;
    Hole place[];
    /** Creates a new instance of BattleShip */
    public BattleShip() {
        size=1;
        used=false;
        destroyed=false;
        place=new Hole[1];
    }
    public BattleShip(int size){
        this.size=size;
        used=false;
        destroyed=false;
        place=new Hole[size];
    }
    public void setSize(int size){
        this.size=size;
    }
    public int getSize(){
        return size;
    }
    public void setUsed(boolean used){
        this.used=used;
    }
    public boolean getUsed(){
        return used;
    }
    public void setPlace(Hole[] place){
        this.place=place;
    }
    public Hole[] getPlace(){
        return place;
    }

    public boolean isDestroyed(boolean output){
        for(int x=0;x<place.length;x++){
            if(!place[x].getHit()){
                if(output)
                    System.out.println("IsDestroyed false #"+x);
                return false;
            }//end if
        }//end for
        if(output)
                    System.out.println("IsDestroyed true");
        this.destroyed=true;
        return true;
    }//end isDestroyed
    
}
