/*
 * Node.java
 *
 * Created on December 4, 2007, 8:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Node {
        Object element;
        Node next;
        int x;
        int y;
    /** Creates a new instance of Node */
    public Node(Object o) {
        element=o; 
    }
    public Node(int q,int w){
        this.x=q;
        this.y=w;
        System.out.println(q+" "+w+" "+x+" "+y);
    }
    
     public void moveOne(int q,int w,int p,int o){
        if(x!=q){
            x=x+p;
        }
        else{
            y=y+o;
        }
    }
    
    public void set(int x, int y){
        this.x=x;
        this.y=y;
    }
}
