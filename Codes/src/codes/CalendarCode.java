/*
 * CalendarCode.java
 *
 * Created on July 19, 2009, 7:03 PM
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
public class CalendarCode {
    
    /** Creates a new instance of CalendarCode */
    public CalendarCode() { 
    }
    public static void main(String args []){
        //String array[]={"S1","M2","T3","W4","T#2","F4","S2"};
        char array[]={'d','f','n','v','i','x','k'};
        System.out.println(new CalendarCode().decode(array,6,2009,true));
    }
    public String decode(String [] message,int month, int year,boolean reverse){
        Calendar cal=getCalendar(month,year);
        Map daysOfMonth=getDaysOfMonth(cal,reverse);
        String decoded="";
        for(String code:message){
            decoded+=daysOfMonth.get(code);
        }//end for
        return decoded;
    }//end decode
    
    public String decode(char [] message,int month, int year,boolean reverse){
        Calendar cal=getCalendar(month,year);
        Map daysOfMonth=getDaysOfMonth(cal,reverse);
        String decoded="";
        for(char code:message){
            decoded+=daysOfMonth.get(code);
        }//end for
        return decoded;
    }//end decode
    
    private Calendar getCalendar(int month, int year){
        Calendar cal=Calendar.getInstance();
        cal.set(year,month,1);
        return cal;
    }//end getCalendar
    
    private Map getDaysOfMonth(Calendar cal,boolean reverse){
        Map map=new HashMap();
        Map daysOfWeek=getDaysOfWeek();
        List <Character> regAlpha=LetterSwap.populateRegular();
        String array[]=new String[26];
        int x=1;
        int week;
        
        for(char let:regAlpha){
            if(reverse){
            map.put(let,daysOfWeek.get(cal.get(Calendar.DAY_OF_WEEK))+""+cal.get(Calendar.WEEK_OF_MONTH));
            }
            else{
            map.put(daysOfWeek.get(cal.get(Calendar.DAY_OF_WEEK))+""+cal.get(Calendar.WEEK_OF_MONTH),let);
            }
            cal.set(Calendar.DAY_OF_MONTH,++x);
        }//end for
        return map;
    }//end getDaysOfMonth
    
    private Map getDaysOfWeek(){
    Map map=new HashMap();
    map.put(Calendar.SUNDAY,"S#");
    map.put(Calendar.MONDAY,"M");
    map.put(Calendar.TUESDAY,"T");
    map.put(Calendar.WEDNESDAY,"W");
    map.put(Calendar.THURSDAY,"T#");
    map.put(Calendar.FRIDAY,"F");
    map.put(Calendar.SATURDAY,"S");
    return map; 
    }//end getDaysOfweek
}//end class
