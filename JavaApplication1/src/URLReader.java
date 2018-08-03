import java.io.*;
import java.net.*;  
/*
 * URLReader.java
 *
 * Created on October 11, 2008, 6:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class URLReader {
    
    public static void main(String args []) throws MalformedURLException, IOException{
        URL yahoo= new URL("http://www.yahoo.com");
        BufferedReader in= new BufferedReader(new InputStreamReader(yahoo.openStream()));
        String line;
        while((line =in.readLine()) !=null)
            System.out.println(line);
        in.close();
    }
    /** Creates a new instance of URLReader */
    public URLReader() {
    
    }
}
