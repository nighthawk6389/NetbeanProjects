/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

/**
 *
 * @author Ilan
 */

class Run{
        
}

public class Singleton { 

    
        public static void main(String args []){
            Singleton s = new Singleton();
            System.out.println(s.v1);
        }
        
        private final int v1 = 5;
        
        // Private constructor prevents instantiation from other classes
        private Singleton() { }
 
        /**
        * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
        * or the first access to SingletonHolder.INSTANCE, not before.
        */
        private static class SingletonHolder { 
                public static final Singleton INSTANCE = new Singleton();
                static{
                    System.out.println(INSTANCE.v1);
                }
        }
 
        public static Singleton getInstance() {
                System.out.println(v1);
                return SingletonHolder.INSTANCE;
        }

}