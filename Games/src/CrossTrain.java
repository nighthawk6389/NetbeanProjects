
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan
 */
public class CrossTrain {
    
    public static void main(String args []){
        
        
        Scanner s = null;
        try {
            s = new Scanner(new FileReader("C:\\Users\\Ilan\\Documents\\MATLAB\\hw4\\trainfile.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrossTrain.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        s.useDelimiter("[\n]");
        
        Map<String,Integer> map = new HashMap<String,Integer>();
        Map<String,String> correct_map = new HashMap<String,String>();
        
        String [] array = new String[7];
        
        int index = 0;
        while( s.hasNext() ){
            String next = s.next();
            int pos = index % array.length;
            if( array[pos] == null)
                array[pos] = "";
            array[ pos ] += "" + next; 
            //System.out.println(pos + " " + next);
            index++;
       }
        
        for(String str: array){
            System.out.println(str);
            System.out.println();
        }
    }
    
}
