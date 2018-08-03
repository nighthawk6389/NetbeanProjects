/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ecarnomics;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Mavis Beacon
 */
public class CountLinesSidebump {
    
    public static void main(String args []) throws FileNotFoundException, IOException{
        
        String listTheme = "C://DebateWebsite//listTheme";
        File dir = new File(listTheme);
        System.out.println(dir.isDirectory());
        
        File [] files = dir.listFiles(); 
        FileInputStream fstream;
        // Get the object of DataInputStream
        DataInputStream in;
        BufferedReader br;
        int counter = 0;
        for(File file : files){
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while( (line = br.readLine()) != null )
                counter++;
        }
        System.out.println(counter);
        
        
        
        String util = "C://DebateWebsite//util";
        dir = new File(util);
        System.out.println(dir.isDirectory());
        
        files = dir.listFiles(); 
        // Get the object of DataInputStream
        counter = 0;
        for(File file : files){
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while( (line = br.readLine()) != null )
                counter++;
        }
        System.out.println(counter);
    }
    
}
