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
public class SymbolTable {

    ArrayList<Symbol> table=new ArrayList<Symbol>();

    public SymbolTable(){
    }

    public SymbolTable(Symbol sym){
        table.add(sym);
    }

    public void addToTable(Symbol sym){
        table.add(sym);
    }

    public Symbol getFirstSymbol(){
       return table.get(0);
    }

    public Symbol getSymbolWithName(String name){
        Iterator it=getTableIterator();
        Symbol sym;
        while(it.hasNext()){
            sym=(Symbol)it.next();
            if(sym.getName().equals(name))
                return sym;
        }
        return null;
    }

    public Iterator getTableIterator(){
        return table.iterator();
    }

    @Override
    public String toString(){
        Iterator it=getTableIterator();
        String s="";
        while(it.hasNext()){
            s+=(Symbol)it.next()+"\n";
        }
        return s;
    }

}
