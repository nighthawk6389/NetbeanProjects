/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Mavis Beacon
 */
public class PossibleGroup {
    
    ArrayList<Position> positions = new ArrayList<Position>();
    
    public PossibleGroup(){
        
    }

    public PossibleGroup(ArrayList<Position> positions) {
        this.positions = new ArrayList<Position>(positions);
    }
    
    public void addPosition(Position p){
        positions.add(p);
    }
    public ArrayList<Position> getGroup(){
        return positions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(obj instanceof Solution){
            return isEqualToSolution(obj);
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PossibleGroup other = (PossibleGroup) obj;
        if (this.positions != other.positions && (this.positions == null || !this.positions.equals(other.positions))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.positions != null ? this.positions.hashCode() : 0);
        return hash;
    }

    Iterator<Position> iterator() {
        return positions.iterator();
    }

    @Override
    public String toString() {
        return "PossibleGroup{" + positions + '}';
    }

    private boolean isEqualToSolution(Object obj) {
        System.out.println("hello");
        return ((Solution)obj).squares.equals(this.positions);
    }
    
    
    
    
    
}
