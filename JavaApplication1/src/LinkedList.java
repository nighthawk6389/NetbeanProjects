/*
 * LinkedList.java
 *
 * Created on December 13, 2007, 12:18 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class LinkedList {
    private Node first,last;
    private int count=0;
    /** Creates a new instance of LinkedList */
    public LinkedList() {
    }
    
    public int getCount(){
        return count;
    }
    public Object getFirst(){
        if(count==0)
            return null;
        else
            return first.element;
    }
    public Node getFirstNode(){
        if(count==0)
            return null;
        else
            return first;
    }
    
    public Object getLast(){
        if(count==0)
            return null;
        else
            return last.element;
    }
    
    public Node getLastNode(){
        if(count==0)
            return null;
        else
            return last;
    }
    
    public void addFirst(Object element){
        Node newNode = new Node(element);
        newNode.next=first;
        first=newNode;
        count++;
        
        if(last==null)
            last=first;
    }
    
    public void addLast(Object element){
        if(last==null)
            first=last=new Node(element);
        else{
            last.next=new Node(element);
            last=last.next;
        }
        count++;
    }
    
  /*  public void addFirst(int x, int y){
        if(last==null)
            first=last=new Node(x,y);
        else{
            last.next= new Node(x,y);
            last=last.next;
        }
        count++;
   }*/
    
    public void addFirst(int x, int y){
        Node newNode = new Node(x,y);
        newNode.next=first;
        first=newNode;
        count++;
        
        if(last==null)
            last=first;
    }
    
    public void move(int q,int w){
        Node current=first;
        //System.out.println(first.x+" x,y "+first.y);
        for(int i=1;i<count;i++){
            current.x=(current.next).x;
            current.y=(current.next).y;
           // System.out.println("c.x= "+current.x+"c.y= "+current.y);
            current=current.next;
        }
        last.x=q;
        last.y=w; 
        //System.out.println("After change "+first.x+" "+first.y);
    }
    
    public boolean check(){
        //System.out.println("check");
        Node current=first;
         for(int i=1;i<count;i++){
            if(last.x==current.x && last.y==current.y)
                return false;
            else
                current=current.next;
         }
        return true;
    }
    
    
    public boolean removeFirst(){
        if(count==0)
            return false;
        else{
            first=first.next;
            count--;
            return true;
        }
    }
    
    public boolean removeLast(){
        if(count==0)
            return false;
        else{
            Node current=first;
            for(int x=0;x<count-2;x++)
                current=current.next;
            last=current;
            last.next=null;
            count--;
            return true;
        }
    }
       
    public boolean remove(Object element){
        Node previous= first;
        Node current;
        
        if(first!= null){
            if(element.equals(first.element)){
                first=first.next;
                count--;
                return true;
            }
            else
                current=first.next;
        }
        else
            return false;
        
        for(int x=0;x<count-1;x++){
            if(element.equals(current.element)){
                previous.next=current.next;
                count--;
                return true;
            }
            else{
                previous=current;
                current=current.next;
            }
        }
        return false;
    }
    
    public  boolean remove(int index){
        if((index<0) || index>=count )
            return false;
        else if(index ==0)
            return removeFirst();
        else if(index== count-1)
            return removeLast();
        else{
            Node current=first;
            
            for(int x=1;x<index;x++)
                current=current.next;
            
            current.next=(current.next).next;
            count--;
            return true;
        }
    }
    
    
    public String toString(){
        StringBuffer result= new StringBuffer("[");
        
        Node current=first;
        for(int x=0;x<count;x++){
            result.append(current.element);
            current=current.next;
            if(current!=null){
                result.append(", ");
            }
            else
                result.append("]");
        }
        return result.toString();
    }
}
    
   
