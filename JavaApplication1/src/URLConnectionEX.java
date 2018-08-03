import java.io.*;
import java.net.*;

public class URLConnectionEX {
    public static void main(String args []) throws MalformedURLException, IOException{
        String toReverse= "Read This";
        
        URL yahoo= new URL("http://www.sidebump.com/listTheme/homePage.php");
        URLConnection connection =yahoo.openConnection();
        connection.setDoInput(true);
        
        OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
        out.write("String= "+toReverse);
        out.close();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        String decoded;
        while((decoded = in.readLine())!=null)
            System.out.println(decoded);
        in.close();
    }
}
