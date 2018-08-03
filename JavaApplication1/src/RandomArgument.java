import java.util.*;
/*
 * RandomArgument.java
 *
 * Created on October 13, 2008, 5:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class RandomArgument {
    public static void main(String args []){
         int array[]={1,5,7,3,5,9};
        List list= new ArrayList();
        for(int a: array){
            list.add(a);
        }
        Collections.shuffle(list);
        System.out.println(list);
        
    }   
    /** Creates a new instance of RandomArgument */
    public RandomArgument() {
    }
}
