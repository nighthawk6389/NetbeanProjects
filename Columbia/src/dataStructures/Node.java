/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructures;

/**
 *
 * @author Mavis Beacon
 */
public class Node {
    
    protected Object data;
    protected Node next;
    public Node(Object x, Node n){
        data=x;
        next=n;
    }
    public Node(){
        data=null;
        next=null;
    }
    
    @Override
    public String toString(){
        return data.toString();
    }
    
}
