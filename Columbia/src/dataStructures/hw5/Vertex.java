package dataStructures.hw5;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author Mavis Beacon
 */
public class Vertex {
    
    char letter;
    int x;
    int y;
    int z;

    ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
    
    public Vertex(char letter, int x, int y, int z){
        this.letter = letter;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vertex(char letter,String x, String y, String z){
        this.letter = letter;
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.z = Integer.parseInt(z);
    }
    
    public void addNeighbor(int x, int y, int z){
        Vertex v = new Vertex('\0',x,y,z);
        neighbors.add(v);
    }
    public void addNeighbor(String x, String y, String z){
        Vertex v = new Vertex('\0',x,y,z);
        neighbors.add(v);
    }
    public boolean isNeighbor(Vertex v){
        if( neighbors.contains(v) )
            return true;
        return false;
    }
    public boolean replaceNeighbor(Vertex v){
        int index = neighbors.indexOf(v);
        if( index == -1)
            return false; 
        neighbors.set(index, v);
        return true;
    }

    public char getLetter() {
        return letter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.x;
        hash = 41 * hash + this.y;
        hash = 41 * hash + this.z;
        return hash;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }
    
    public String toString(){
        return letter + ": " + x + " " + y + " " + z;
    }
    
    
}
