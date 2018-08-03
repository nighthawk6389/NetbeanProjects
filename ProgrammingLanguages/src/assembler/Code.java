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
public class Code {
    
    public static final Map<String,String> dest;
    static{
        Map<String,String> map=new HashMap<String,String>();
        map.put(null, "000");
        map.put("M","001");
        map.put("D","010");
        map.put("MD","011");
        map.put("A","100");
        map.put("AM","101");
        map.put("AM","110");
        map.put("AMD","111");
        dest=Collections.unmodifiableMap(map);
    }

    public static final Map<String,String> jump;
    static{
        Map<String,String> map=new HashMap<String,String>();
        map.put(null, "000");
        map.put("JGT","001");
        map.put("JEQ","010");
        map.put("JGE","011");
        map.put("JLT","100");
        map.put("JNE","101");
        map.put("JLE","110");
        map.put("JMP","111");
        jump=Collections.unmodifiableMap(map);
    }

    public static final Map<String,String> comp;
    static{
        Map<String,String> map=new HashMap<String,String>();
        map.put("0",  "0101010");
        map.put("1",  "0111111");
        map.put("-1", "0111010");
        map.put("D",  "0001100");
        map.put("A",  "0110000");
        map.put("!D", "0001101");
        map.put("!A", "0110001");
        map.put("-D", "0001111");
        map.put("-A", "0110011");
        map.put("D+1","0011111");
        map.put("A+1","0110111");
        map.put("D-1","0001110");
        map.put("A-1","0110010");
        map.put("D+A","0000010");
        map.put("D-A","0010011");
        map.put("A-D","0000111");
        map.put("D&A","0000000");
        map.put("D|A","0010101");
        map.put("M",  "1110000");
        map.put("!M", "1110001");
        map.put("-M", "1110011");
        map.put("M+1","1110111");
        map.put("M-1","1110010");
        map.put("D+M","1000010");
        map.put("D-M","1010011");
        map.put("M-D","1000111");
        map.put("D&M","1000000");
        map.put("D|M","1010101");
        comp=Collections.unmodifiableMap(map);
    }

    public static String dest(String s){
        String binary=dest.get(s);
       // System.out.println("Code: Dest: "+binary);
        return binary;
    }
    
    public static String jump(String s){
        String binary=jump.get(s);
       // System.out.println("Code: Jump: "+binary);
        return binary;
    }

    public static String comp(String s){
        String binary=comp.get(s);
        //System.out.println("Code: Comp: "+binary);
        return binary;
    }



}
