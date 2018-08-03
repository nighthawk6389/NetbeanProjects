/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.util.ArrayList;

/**
 *
 * @author Mavis Beacon
 */
public class Position {
    
    int x;
    int y;
    Constants type;

    public Constants getType() {
        return type;
    }

    public void setType(Constants type) {
        this.type = type;
    }
    
    public Position(){
        x=0;
        y=0;
    }
    
    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }
    
    public Position(Position p){
        this.x=p.x;
        this.y=p.y;
    }
    
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.x;
        hash = 47 * hash + this.y;
        return hash;
    }
    
    @Override
    public boolean equals(Object o){
        if( o instanceof Solution)
           return getIsEqualToSolution(o);
        
        if( !(o instanceof Position) )
            return false;
        Position p=(Position)o;
        if(this.x==p.x && this.y==p.y)
            return true;
        return false;
    }

    private boolean getIsEqualToSolution(Object o) {
        System.out.println("Postion - getIsEqualToSolution - ALWAYS RETURNS FALSE");
         return false;
    }
}
