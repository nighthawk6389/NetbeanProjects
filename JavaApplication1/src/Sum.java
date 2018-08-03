import java.util.List;
import java.util.LinkedList;
import javax.swing.JOptionPane;
/*
 * Sum.java
 *
 * Created on September 22, 2007, 8:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Sum {
    
    /** Creates a new instance of Sum */
    public static void main(String args []){
       /*
        int n = 100;
        int counter = 0;
        while( n < 500000){
            n = n * 2;
            System.out.println(n);
            counter++;
        }
        System.out.println(counter+ " " + n);
        * */
        int g1 = 1 // test
                ;
        String s;
        isBoolean(null);
        List<Integer> l = new LinkedList<Integer>();
    }

    
       public final static boolean isBoolean(Object string){
		if(string.equals("true") || string.equals("false")){
			return true;
		}
		return false;
	}
   
    	public final static boolean isBoolean(double number){
		if(number == 1 || number == 0){
			return true;
		}
		return false;
	}
	public final static boolean isBoolean(boolean bool){
		return true;
	}
            
    public final static boolean isBoolean(String string){
		if(string.equals("true") || string.equals("false")){
			return true;
		}
		return false;
	}
    
    public static double sumNum(double num){
          double sum=0;
        
        while(num > 1){
            sum+=num%10;
            num=(int)(num/10);
            //System.out.println(num + " "+sum);
         
        }
        //sum+=num;
          return sum;
    }
    public Sum() {
    }
    
}
