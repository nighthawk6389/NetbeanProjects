package dataStructures.hw3;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class EBinaryNode {
    
    Object element = null;
    EBinaryNode left = null;
    EBinaryNode right = null;
    int xPos, yPos;
    String nodetype;
            
    
    public EBinaryNode(Object data){
        this.element = data;
    }
    public EBinaryNode(Object data, EBinaryNode left, EBinaryNode right){
        this(data);
        this.left = left;
        this.right = right;
    }
    
    
    public String toString(){
        return "\n("+ element +")\n  "+ ( left == null? "" : left.toString())+"     "+ (right == null? "" : right.toString());
    }
    
}
