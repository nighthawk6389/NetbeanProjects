/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ecarnomics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mavis Beacon
 */
public class Parser {
    
    public static void main(String args []){
        File file = new File("C:\\websites\\Ecarnomics\\data\\Cost-Calculations-20111103-csv.csv");
        //File file = new File("C:\\websites\\Ecarnomics\\data\\practice.txt");
        Set set = new LinkedHashSet();
        Scanner s = null;
        try {
            s = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(s);
        s.useDelimiter("\n");
        int i = 0;
        int max = 0;
        char c;
        while( s.hasNext() ){
            String str = s.next();
            if (str.length() > max)
                max = str.length();
        }
        System.out.println("max = " + max );
        int x = -1;
        for(Object string: set){
            System.out.println("$man["+(x++)+"] = \""+string+"\";");
        }   
    }
    
}
