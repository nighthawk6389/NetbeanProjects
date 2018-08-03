/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programminglanguages;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Mavis Beacon
 */
public class ComplexTerm extends Term{

    int operator;
    
    public ComplexTerm(int operator,ArrayList<Term> list){
        super(list);
        this.operator=operator;
    }

    @Override
    public void outputTerm(){
        System.out.println("Complex: "+operator);
        Iterator i=list.iterator();
        while(i.hasNext()){
            Term t=(Term)i.next();
            t.outputTerm();
        }
    }
    
}
