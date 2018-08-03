/*
 * EPrime.java
 *
 * Created on September 7, 2008, 3:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class EPrime{
    
    /** Creates a new instance of EPrime */
    public EPrime() {
        boolean count =true;
        float x=1.0f;
        float e=1.0f;
        while(count==true){
        e+= 1/(factorial(x));
        x++;
        if(x>100)
            count=false;
        }
        System.out.println(e);
       
    }
       
    public float factorial(float num){
        float temp=num;
        for(float z=num-1;z>0;z--){
            temp=temp*z;
        }
        //System.out.println("num= "+temp);
        return temp;
    }
    public static void main(String args []){
        EPrime p= new EPrime();
    }
    
}
