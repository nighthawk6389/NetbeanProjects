/*
 * Prime.java
 *
 * Created on September 28, 2007, 1:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import javax.swing.JOptionPane;
/**
 *
 * @author Ilan Elkobi
 */
public class Prime {
    public static void main(String args []){
        String num1= JOptionPane.showInputDialog(null,"Enter num","Num",JOptionPane.QUESTION_MESSAGE);
        int num= Integer.parseInt(num1);
        System.out.println(2%9+" "+3%9);
        
       // solve(num);
    }
    public static double [] solve(int num){
        int x=0;
        double array[]= new double[15];
        while(num%2==0 || num%3==0){
            if(num%2==0){
                num/=2;
                array[x]=2;
                x++;    
            }
            else if(num%3==0){
                num/=3;
                array[x]=3;
                x++;
            }
        }
        if(num==1){
            x--;
        }
        else
        array[x]=num;
        
        for(int z=0;z<=x;z++){
            System.out.print(array[z]+ " ");
        }    
        return array;
    }

    
    
    /** Creates a new instance of Prime */
    public Prime() {
    }
    
    
}
