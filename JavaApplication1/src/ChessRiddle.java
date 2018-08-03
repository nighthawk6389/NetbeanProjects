
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan
 */
public class ChessRiddle {
    
    public static void main(String args[]){
        
        int inc = 0;
        int i = 0;
        ArrayList<String> list = new ArrayList<String>();
        HashMap<String, Integer> map = new HashMap<String,Integer>();
        while(i != 32){
            for(int j = 0; j != 8; ++j){
                int t = j + inc;
                t = t % 8;
                int key = (i*8 + t)%8;
                String bin = Integer.toBinaryString(i*8 + j);
                while(bin.length() < 8){
                    bin = "0" + bin;
                }
                list.add(""+ key + " " + bin );
                map.put(bin, key);
            }
            //inc--;
            i++;
        }
        
        //Collections.sort(list);
        
        for(String s: list){
            System.out.println(s);
        }
    }
    
}
