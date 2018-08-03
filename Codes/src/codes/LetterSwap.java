package codes;
import java.util.*;
/*
 * LetterSwap.java
 *
 * Created on July 13, 2009, 12:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class LetterSwap {
    
    /** Creates a new instance of LetterSwap */
    public LetterSwap() {
    }
    public static void main(String args[]){
        System.out.println(new LetterSwap().decode("pyram","olxzb",true));
    }//end main
    public String decode(char letter,String code,boolean reverse){
        Map map=getMapLetter(letter,reverse);
        char[] array=code.toCharArray();
        String decoded="";
        for(char let:array){
            decoded+=map.get(let);
        }
        return decoded;
    }//end decode
    public String decode(String keyword,String code,boolean reverse){
        Map map=getMapKeyword(keyword,reverse);
        char[] array=code.toCharArray();
        String decoded="";
        for(char let:array){
            decoded+=map.get(let);
        }
        if(reverse)
            decoded+="  Keyword="+keyword;
        return decoded;
    }//end decode
    
    public Map getMapLetter(char letter,boolean reverse){
        List regAlpha=populateRegular();
        Set changedAlpha=new LinkedHashSet();
        int index=regAlpha.indexOf(letter);
        Iterator it=regAlpha.listIterator(index);
        return getGeneralMap(letter,regAlpha,it,changedAlpha,reverse);
    }//end getMapLetter
    
    public Map getMapKeyword(String keyword,boolean reverse){
    List regAlpha=populateRegular();
    Iterator it=regAlpha.listIterator(0);
    Set changedAlpha=new LinkedHashSet();
    char array[]=keyword.toCharArray();
    char letter='z';
    for(char let:array)
        changedAlpha.add(let);
    return getGeneralMap(letter,regAlpha,it,changedAlpha,reverse);
    }//getMapKeyWord
    
    public Map getGeneralMap(char letter,List regAlpha, Iterator it,Set changedAlpha,boolean reverse){
        char next=(Character)it.next();
        do{
            if(!it.hasNext()){
                it=regAlpha.listIterator(0);
            }
            changedAlpha.add(next);
        }while((next=(Character)it.next()) != letter);//end while
        changedAlpha.add(letter);
        
        System.out.println("CHANGED ALPHA");
        System.out.println(changedAlpha);
        
        Iterator itReg=regAlpha.listIterator();
        Iterator itChanged=changedAlpha.iterator();
        Map map=new HashMap();
        while(itReg.hasNext() && itChanged.hasNext()){
            if(reverse)
                map.put(itChanged.next(),itReg.next());
            else
                map.put(itReg.next(),itChanged.next());
        }//end while
        map.put(' ',' ');
        System.out.println("MAP");
        System.out.println(map);
        return map;
    }//end getGeneralMap
    
    protected static List populateRegular(){
    List regAlpha=new ArrayList<Character>();
        regAlpha.add('a');
        regAlpha.add('b');
        regAlpha.add('c');
        regAlpha.add('d');
        regAlpha.add('e');
        regAlpha.add('f');
        regAlpha.add('g');
        regAlpha.add('h');
        regAlpha.add('i');
        regAlpha.add('j');
        regAlpha.add('k');
        regAlpha.add('l');
        regAlpha.add('m');
        regAlpha.add('n');
        regAlpha.add('o');
        regAlpha.add('p');
        regAlpha.add('q');
        regAlpha.add('r');
        regAlpha.add('s');
        regAlpha.add('t');
        regAlpha.add('u');
        regAlpha.add('v');
        regAlpha.add('w');
        regAlpha.add('x');
        regAlpha.add('y');
        regAlpha.add('z');
    return regAlpha;
    }//populateRegular
}//end class
