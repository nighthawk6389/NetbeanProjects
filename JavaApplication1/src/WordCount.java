/*
 * WordCount.java
 *
 * Created on April 22, 2008, 4:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
import java.util.*;
public class WordCount {
     String text=" ";
     Map map= new HashMap();
    /** Creates a new instance of WordCount */
    public WordCount() {
        text= "I am i am today today i "; 
        StringTokenizer st= new StringTokenizer(text);
        while(st.hasMoreTokens()){
            if(map.containsKey(st.toString())){
                int value= ((Integer)map.get(st.toString())).intValue();
                value++;
                map.put(st.toString(),value);
            }
            else
                map.put(st.toString(),new Integer(1));
        }
       Set set= map.entrySet();
       Iterator it= set.iterator();
       
       while(it.hasNext()){
           System.out.println(it.next());
       }
    }
    
}
