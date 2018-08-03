import java.io.*;
import java.net.*;
import java.util.*;

public class EchoSocket{
    public static void main(String args []) throws IOException{
        Socket echoSocket=null;
        PrintWriter out=null;
        BufferedReader in=null;
        SortedSet s= new TreeSet();
       
        try{
            echoSocket=new Socket("Yaish",7);
            out= new PrintWriter(echoSocket.getOutputStream(),true);
            in= new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        }
        catch(MalformedURLException ex){
            System.out.println("Malformed");
            System.exit(0);
        }
        catch(IOException ex){
            System.out.println("IO"+ex);
            System.exit(0);
        }
        BufferedReader stdLine= new BufferedReader(new InputStreamReader(System.in));
        String lineIn;
        while((lineIn = (stdLine.readLine())) != null){
            out.println(lineIn);
            System.out.println("Echo: "+in.readLine());
        }
        echoSocket.close();
        in.close();
        out.close();
        stdLine.close();
    }
}
