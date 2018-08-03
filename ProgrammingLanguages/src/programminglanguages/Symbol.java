/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programminglanguages;

/**
 *
 * @author Mavis Beacon
 */
public class Symbol {

    private int type;
    private String name;
    private String value;

    public Symbol(int type,String name,String value){
        this.type=type;
        this.name=name;
        this.value=value;
    }

    public String getName(){
        return name;
    }

    public void setValue(String value){
        this.value=value;
    }
    public String getValue(){
        return this.value;
    }
    public int getType(){
        return type;
    }

    @Override
    public String toString(){
        return "Type: "+type+" Name: "+name+" Value: "+value;
    }

}
