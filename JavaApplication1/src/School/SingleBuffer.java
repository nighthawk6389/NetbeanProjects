/*
 * SingleBuffer.java
 *
 * Created on September 24, 2009, 5:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package School;

import java.io.PrintWriter;
import java.sql.*;
import java.util.Iterator;

/**
 *
 * @author elkobi
 */
public class SingleBuffer {
    
    private Object item;
    /** Creates a new instance of SingleBuffer */

     
    public SingleBuffer() {
        item=null;
    }
    public SingleBuffer(Object item){
        this.item=item;
    }
    
    public boolean put(Object item){
        if(this.item!=null){
            return false;
        }
        this.item=item;
        return true;
    }
    public Object get(){
        if(this.item==null)
            return false;
        Object temp=this.item;
        this.item=null;
        return temp;
    }
    
}
