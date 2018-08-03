import java.util.*;
import java.awt.*;
import javax.swing.*;
/*
 * ThreadOutput.java
 *
 * Created on April 25, 2008, 10:34 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class ThreadOutput{
    
    /** Creates a new instance of ThreadOutput */
    public ThreadOutput() {
        PrintNum num= new PrintNum(100);
        PrintNum a= new PrintNum('a',100);
        PrintNum b= new PrintNum('b',100);
        
        num.start();
        a.start();
        b.start();
    }
    
    public static void main(String args[]){
        ThreadOutput frame= new ThreadOutput();

    }
}
class PrintNum extends Thread{
    int times;
    char let;
    
    public PrintNum(int times){
        this.times=times;
    }
    public PrintNum(char let, int times){
        System.out.println(let);
        this.let=let;
        this.times=times;
    }
    
    public void run(){
        if(let !='a' && let !='b'){
           // System.out.println("no char");
        for(int x=0;x<times;x++){
            System.out.print(x); 
        }
        System.out.println();
        }
        else{
            //System.out.println("char");
            assert let=='a' || let=='b';
            for(int x=0;x<times;x++){
                System.out.print(let); 
            }
        }
    }
}
class PrintA extends Thread{
    int times;
    
    public PrintA(int times){
        this.times=times;
    }
    
    public void run(){
        for(int x=0;x<times;x++){
            System.out.print("A");
        }
        //System.out.println();
    }
}
class PrintB extends Thread{
    int times;
    
    public PrintB(int times){
        this.times=times;
    }
    
    public void run(){
        for(int x=0;x<times;x++){
            System.out.print("B");
        }
        //System.out.println();
    }
}
