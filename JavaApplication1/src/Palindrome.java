import javax.swing.JOptionPane;
/*
 * Palindrome.java
 *
 * Created on September 28, 2007, 4:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Palindrome {
   
   public static void main(String args []){
       String text= JOptionPane.showInputDialog(null,"Enter String","Num",JOptionPane.QUESTION_MESSAGE);
       
       JOptionPane.showMessageDialog(null,text +" is a palindrome? " +check(text),"Answer",JOptionPane.INFORMATION_MESSAGE);
       
   } 
   public static boolean check(String text){
           char ca;
           char cb;
          String reverse= s(text);
           //for(int x=0;x<=text.length()/2;x++){
               //ca=text.charAt(x);
               //cb=text.charAt(text.length()-1-x);
               //System.out.println(x+" "+ca+" "+cb);
           System.out.println(text+" "+reverse);
               if(text.equals(reverse)){
                   return true;
               }
          // }
           return false;
   }
   public static String s(String text){
       String reverse="";
       
       for(int x=0;x<text.length();x++){
           reverse+=text.charAt(text.length()-1-x);
       }
       return reverse;
   }
}
       
   

       
   
