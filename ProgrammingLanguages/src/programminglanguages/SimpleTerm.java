/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programminglanguages;

/**
 *
 * @author Mavis Beacon
 */
public class SimpleTerm extends Term{

    String last;
    int type;

    public SimpleTerm(int type,String last){
        this.type=type;
        this.last=last;
    }

    @Override
    public void outputTerm(){
        System.out.println("Simple: "+type+" "+last);
        
    }

}
