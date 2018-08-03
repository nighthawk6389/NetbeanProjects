/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructures;

import java.util.ArrayList;

/**
 *
 * @author Mavis Beacon
 */

//simple LinkedList class
public class SimpleLinkedList {
    protected Node header;
    public SimpleLinkedList() {
        header=null;
    }
    
    public int getSize(){
        int size = 0;
        Node nextNode = header;
        while(nextNode != null){
            size++;
            nextNode = nextNode.next;
        }
        return size;
    }
    
    public void printList(){
        System.out.print("[ ");
        Node nextNode = header;
        while(nextNode != null){
            System.out.print(nextNode.toString() + " ");
            nextNode = nextNode.next;
        }
        System.out.println("]");  
    }
    
    public boolean isInList(Object x){
        Node nextNode = header;
        while(nextNode != null){
            if(nextNode.data.equals(x)){
                return true;
            }
            nextNode = nextNode.next;
        }
        return false;
    }
    
    public boolean addToList(Object x){
        Node currentNode = header;
        if( header == null ){
            header = new Node(x,null);
            return true;
        }
        while(currentNode.next != null){
            if(currentNode.data.equals(x)){
                return false;
            }
            currentNode = currentNode.next;
        }
        currentNode.next = new Node(x,null);
        return true;
    }
    
    public boolean removeFromList(Object x){
        
        Node previousNode = null;
        Node currentNode = header;
        while( currentNode != null ){
            if( currentNode.data.equals(x) ){
                previousNode.next = currentNode.next;
                return true;
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        return false;
    }
    
    public void reverseList(){
        Node previousNode = null;
        Node nextNode = null;
        Node currentNode = header;
        while( currentNode != null ){
            nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }
        header = previousNode;
    }
    
       public static void main(String args []){
        SimpleLinkedList list = new SimpleLinkedList();
        list.addToList(new Integer(1));
        list.addToList(new Integer(2));
        list.addToList(new Integer(3));
        list.addToList(new Integer(4));
        list.addToList(new Integer(5));
        list.addToList(new Integer(6));
        list.addToList(new Integer(7));
        list.addToList(new Integer(8));
        
        list.printList();
        System.out.println(list.getSize());
        System.out.println(list.isInList(new Integer(1)));
        System.out.println(list.isInList(new Integer(11)));
        
        //list.removeFromList(new Integer(8));
        //list.removeFromList(new Integer(7));
        
        list.reverseList();
        
        list.printList();
        
        ArrayList<Integer> L = new ArrayList();
        L.add(1);
        L.add(4);
        L.add(423);
        L.add(23);
        L.add(45);
        L.add(567);
        
        ArrayList<Integer> P = new ArrayList();
        P.add(0);
        P.add(-1);
        P.add(10);
        P.add(2);
        P.add(5);

        
    }
}

/*
    public void printLots(ArrayList L, ArrayList<Integer> P){
       
        for(Integer i : P){
            if( i < 0 || i >= L.size())
                continue;
            System.out.println(L.get(i));
        }
    }
 * 
 */

