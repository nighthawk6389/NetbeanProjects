/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ecarnomics;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mavis Beacon
 */
public class InfoFromWebsite {
    
    public static void main(String args []) throws IOException{
        
        //JSONObject obj ;
        URL url = null;
        try {
            url = new URL("http://sidebump.com/ecarnomics/main/getManufacturers.php");
        } catch (MalformedURLException ex) {
            System.out.println("MaformedURLException: " + ex.getMessage());
        }
        
        BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String line;
        while( (line = stream.readLine()) != null ){
            System.out.println(line);
        }
    }
    
}
