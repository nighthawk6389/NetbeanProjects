/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks.ie2147.step_3.client.src;

import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;



/**
 *
 * @author Mavis Beacon 
 */
public class Main {
    
    static int localReadPort = 1479;
    static int localWritePort = 1480;
    static int broadcastServerPort; 
    static String serverAddr = null;
    static Socket proxySock;
    
    public static void main(String args []){
                   
        try{
            if(args.length != 2){
                System.out.println("Usage: Main <IP> <Port>");
                return;
            }
            broadcastServerPort = Integer.parseInt(args[1]);
            serverAddr = args[0];
            
            System.out.println("ProxySocket attempting to connect on port: " + broadcastServerPort);
            proxySock = new Socket(InetAddress.getByName(serverAddr),broadcastServerPort);
            
        } catch(Exception ex){
            System.out.println("FATAL EXCEPTION: " + ex.toString());
            return;
        }

        /**Listening socket **/
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("LocalReadSocket attempting to connect on port: " + localReadPort);
                    Socket localRecv = new Socket(InetAddress.getLocalHost(),localReadPort);
                    System.out.println("LocalReadSocket connected!");
                    
                    PrintWriter broadcastOut = new PrintWriter(proxySock.getOutputStream(),true);
                    System.out.println("WritingToBroadCastSocket connected!");

                    System.out.println("LocalReadSocket waiting for input...");
                    Scanner scanner = new Scanner(localRecv.getInputStream());
                    String nextLine;
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine();
                        System.out.println("Next input to LocalReadSocket: " + nextLine);
                        String [] lineSplit = nextLine.split(" ");
                        if( lineSplit.length < 4){
                            System.out.println("Received malformed message. Skipping!!!!!");
                            continue;
                        }
                        String outputTextWithInfo = "UNI:ie2147" +
                            " Shape:" + lineSplit[0] + 
                            " X:"     + lineSplit[1] +
                            " Y:"     + lineSplit[2] +
                            " Color:"+ lineSplit[3]  +
                            " AdditionalInfo:" + System.currentTimeMillis();
                        broadcastOut.println(outputTextWithInfo);
                        System.out.println("Sent to broadcastServer, outputText: " + outputTextWithInfo);
                    }
                    System.out.println("\n\nLOCAL InputStream Done (GUI closed!). Please restart program....\n\n");
                
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }).start();

        /** Writing Thread **/
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    
                    System.out.println("WritingToLocalSocket attempting to connect on port: " + localWritePort);
                    Socket writeLocalSock = new Socket(InetAddress.getLocalHost(),localWritePort);
                    PrintWriter localOut = new PrintWriter(writeLocalSock.getOutputStream(),true);
                    System.out.println("WritingToLocalSocket connected!");

              
                    System.out.println("ProxyRead waiting for input...");
                    Scanner scanner = new Scanner(proxySock.getInputStream());
                    String nextLine;
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine(); 
                        System.out.println("Recieved from proxy server: " + nextLine);
                        String [] split = nextLine.split("[ :]");
                        String textToGui = split[3] + " " +
                                split[5] + " " +
                                split[7] + " " + 
                                split[9];
                        localOut.println(textToGui);
                        System.out.println("Sent textToGui: " + textToGui);
                    }
                    
                    System.out.println("\n\nBROADCAST InputStream Done (Proxy Closed!). Please restart program....\n\n");
                    
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }).start();
                  
        
    }
}
