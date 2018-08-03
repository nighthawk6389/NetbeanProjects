/*
 * HashName.java
 *
 * Created on April 22, 2008, 2:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */

import java.util.*;

public class HashName {
    
    /** Creates a new instance of HashName */
    public HashName() {
        
        List hash1= new ArrayList();
        List hash2= new ArrayList();

        String names1= "Jane John Julie Kevin";
        String names2= "Brian John Beth Kevin"; 
        StringTokenizer st= new StringTokenizer(names1, " .,");
        StringTokenizer st1= new StringTokenizer(names2, " .,"); 
        
        while(st.hasMoreTokens()){
            hash1.add(st.nextToken());
        }
        while(st1.hasMoreTokens()){
            hash2.add(st1.nextToken());
        }
        
        ListIterator it1= hash1.listIterator();
        ListIterator it2= hash2.listIterator();
        
        String text;
        String text1;
        while(it1.hasNext()){
            text=(String)it1.next();
            while(it2.hasNext()){
                text1=(String)(it2.next());
                if(text.equals(text1))
                    System.out.println(text);
                //text=(String)it1.next();
                //System.out.println(text+ " "+ text1);
            }
            it2=hash2.listIterator();
        }
        
       // System.out.println(text+ " text");
    }
    
    public static void main(String args []){
       HashName set = new HashName();
    }
}
