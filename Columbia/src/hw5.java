
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan
 */
public class hw5 {
    
    public static void main(String args []){
        
        // Initialize left-end(a), right end(b) and midpoint(c)
        double a = 2;
        double b = 3;
        double c = (a + b)/2;
       
        // Continue to loop while the difference > 10E-4
        while( b - a > .0001){
            // f(c) = x^3 - 25
            double value = Math.pow(c,3) - 25;
            
            // Check if f(c) < 0 or f(c) > 0
            // if f(c) < 0, value too low, increase a to midpoint
            // if f(c) > 0, value too high, lower b to midpoint
            if( value < 0)
                a = c;          
            else if ( value > 0)
                b = c;
            else
                break;  // f(c) == 0 -- Found exact answer
            
            //Narrow bounds and use them to create new midpoint
            c = (a + b)/2;
        } 
        
        //Print approximation of 25^(1/3)
        System.out.println(c);
        
        boolean repeat = false;
        String name = "ilan";
        char [] array = name.toCharArray();
        char [] checkArray = new char [array.length];
        for(int i = 0; i < array.length; i++){
            char ch1 = array[i];
            for( int j = 0; j < i; j++){
                char ch2 = checkArray[j];
                if( ch2 == ch1 ){
                    repeat = true;
                    break;
                }
            }
            checkArray[i] = ch1;
        }
        
        if( repeat )
            return;
    }
    
}
