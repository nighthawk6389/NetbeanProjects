/*
 * Snail.java
 *
 * Created on July 19, 2009, 5:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package codes;

import java.util.*;
/**
 *
 * @author Ilan Elkobi
 */
public class Snail {
    
    /** Creates a new instance of Snail */
    public Snail() {
    }
    public static void main(String args[]){
        Snail snail=new Snail();
        snail.decode("pyram","olxzb");
    }
    public String decode(String keyword, String message){
        Map map=getMap(keyword);
        Map mapValues=getMapValues(keyword);
        
        String decoded="";
        char[] chars=message.toCharArray();
        for(char let:chars){
            int number=(Integer)map.get(let);
            
            if(number>13)
                number=number-((number-13)*2);
            else if(number<13)
                number=number+((13-number)*2);
            decoded=decoded+""+mapValues.get(number);
        }//end for
        
        System.out.println("Decoded="+decoded);
        return decoded;
    }//end decode
    
    private Map getMap(String keyword){
        Map map=new HashMap();
        Set <Character> changedAlpha=createSet(keyword);
        int x=1;
        for(char let:changedAlpha){
            map.put(let,x++);
        }//end for
        map.put(' ',' ');
        return map;
    }//end getMap
    
    private Map getMapValues(String keyword){
        Map map=new HashMap();
        Set <Character> changedAlpha=createSet(keyword);
        int x=1;
        for(char let:changedAlpha){
            map.put(x++,let);
        }//end for
        map.put(' ',' ');
        return map;
    }//end getMap
    
    private Set createSet(String keyword){
        List <Character> regAlpha=LetterSwap.populateRegular();
        Set <Character> changedAlpha=new LinkedHashSet <Character> ();
        
        char chars[]=keyword.toCharArray();
        for(char let:chars){
            changedAlpha.add(let);
        }//end for
        for(char let:regAlpha){
            if(let=='j')
                continue;
            changedAlpha.add(let);
        }//end for
        return changedAlpha;
    }//end createSet
    
}//end class
