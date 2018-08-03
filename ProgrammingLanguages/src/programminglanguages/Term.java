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
public class Term {

    ArrayList<Term> list;

    public Term(){
        list=new ArrayList();
    }
    public Term(ArrayList<Term> list){
        this.list=list;
    }
    public void outputTerm(){
        Iterator i=list.iterator();
        while(i.hasNext()){
            Term t=(Term)i.next();
            t.outputTerm();
        }
    }

}
