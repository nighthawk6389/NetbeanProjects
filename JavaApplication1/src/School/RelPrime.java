/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package School;

/**
 *
 * @author Mavis Beacon
 */
public class RelPrime {

    public static void main(String args []){
        int y=7289;
        int x=8029;

        while(y>0){
            x=x%y;
            int temp=y;
            y=x;
            x=temp;

            System.out.println("X="+x+" Y="+y);
        }

        System.out.println(x);

    }

}
