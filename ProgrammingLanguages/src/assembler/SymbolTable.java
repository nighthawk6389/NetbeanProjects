/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler;

import java.util.*;

/**
 *
 * @author Mavis Beacon
 */
public class SymbolTable {

    private Map<String,String> table;

    public SymbolTable(){
        table=new HashMap<String,String>();
        table.put("SP","0");
        table.put("SP","1");
        table.put("SP","2");
        table.put("SP","3");
        table.put("SP","4");
        for(int x=0;x<16;x++){
            table.put("R["+x+"]",x+"");
        }
        table.put("SCREEN","16384");
        table.put("KBD","24576");
    }

    public void addEntry(String key,String address){
        table.put(key, address);
    }
    public boolean contains(String key){
        return table.containsKey(key);
    }
    public String getAddress(String key){
        return table.get(key);
    }

    @Override
    public String toString(){
        return table.toString();
    }
}
