package BondList;
        
import java.io.Serializable;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
/*
 * DateOf.java
 *
 * Created on August 31, 2008, 1:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class DateOf implements Serializable {
    int month,day,year;
    static int DAY=1;
    static int MONTH=2;
    static int YEAR=3;
    /** Creates a new instance of DateOf */
    public DateOf() {
        month=day=year=00;
    }
    public DateOf(int month, int day, int year){
        this.month=month;
        this.day=day;
        this.year=year;
    }
    public DateOf(Calendar cal){
        this.month=cal.get(cal.MONTH);
        this.day=cal.get(cal.DAY_OF_MONTH);
        this.year=cal.get(Calendar.YEAR);
    } 
    public void setDate(int month, int day, int year){
        if(validateDate(month,day,year)){
        this.month= month;
        this.day=day;
        this.year=year;
        }
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public int getYear(){
        return year;
    }
    public String toString(){
        return new String(month+"/"+day+"/"+year);
        
    }
    public int compareTo(DateOf date2){
     
      Integer monthInt= new Integer(month);
      Integer dayInt= new Integer(day);
      Integer yearInt= new Integer(year);
      
      Integer monthInt2= new Integer(date2.month);
      Integer dayInt2= new Integer(date2.day);
      Integer yearInt2= new Integer(date2.year);
      
      if(yearInt.equals(yearInt2)){
          if(monthInt.equals(monthInt2)){
              if(dayInt.equals(dayInt2)){
                  return 0;
              }
              else{
                  return dayInt.compareTo(dayInt2);
              }
          }
          else{
              return monthInt.compareTo(monthInt2);
          }
      }
      else{
          return yearInt.compareTo(yearInt2);
      }
    }
    public DateOf extract(String old){
        StringTokenizer token= new StringTokenizer(old,"/");
        int day;
        int month;
        int year;
        
        month=Integer.parseInt(token.nextToken());
        day=Integer.parseInt(token.nextToken());
        year=Integer.parseInt(token.nextToken());
        
        if(!validateDate(month,day,year))
            throw new NoSuchElementException();
        return new DateOf(month,day,year);
        
    }
    public boolean validateDate(int month, int day, int year){
        Integer monthLen=new Integer(month);
        if(monthLen.toString().length() > 2){
            JOptionPane.showMessageDialog(null,"The month must be entered in mm format(ex. 06 for June, 10 for September");
            return false;
        }
        
        Integer dayLen=new Integer(day);
        if(dayLen.toString().length() > 2){
            JOptionPane.showMessageDialog(null,"The day must be entered in dd format(ex. 06 for the 6th, 25 for the 25th");
            return false;
        }
        
        Integer yearLen=new Integer(year);
        if(yearLen.toString().length() != 4){
            JOptionPane.showMessageDialog(null,"The year must be entered in yyyy format(ex. 1989,2008");
            return false;
        }
        
        return true;
    }

    public void add(int field, int amount) {
        if(field==DateOf.DAY){
            this.day=this.day+amount;
        }
        else if(field==DateOf.MONTH){
            this.month=this.month+amount;
        }
        else if(field==DateOf.YEAR){
            this.year=this.year+amount;
        }
    }
}
