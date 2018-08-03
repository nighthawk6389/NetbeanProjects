/*
 * HundredThreads.java
 *
 * Created on May 12, 2008, 9:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class HundredThreads{
    Thread thread []= new Thread[100];
     int sum=0;
    
    /** Creates a new instance of HundredThreads */
    public HundredThreads() {
        ThreadGroup g= new ThreadGroup("Group");
        for(int x=0;x<100;x++){
            thread[x]=new Thread(g,new AddOne(),"t");
            thread[x].start();
        }
        System.out.println(sum);
    }
    public static void main(String args []){
        HundredThreads frame= new HundredThreads();
    }
    
    class AddOne extends Thread{
        public void run(){
        addOne();
        }
    }
        public  void addOne(){
            sum++;
        }
}
