/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks.ie2147.step_2.client.src;

import java.io.IOException;
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
    static int broadcastServerPort = 1452;
    static String serverAddr = "128.59.9.102";
    
    public static void main(String args []) throws IOException{
           
        System.out.println("BroadcastSocket attempting to connect on port: " + broadcastServerPort);
        final Socket broadcastSocket = new Socket(InetAddress.getByName(serverAddr),broadcastServerPort);
        if( broadcastSocket == null){
            System.out.println("Failed to connect to broadcast server..Failing");
            return;
        }
        System.out.println("BroadCastSocket connected!");
        
        /**Listening socket **/

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("LocalReadSocket attempting to connect on port: " + localReadPort);
                    Socket localRecv = new Socket(InetAddress.getLocalHost(),localReadPort);
                    System.out.println("LocalReadSocket connected!");
                    
                    PrintWriter broadcastOut = new PrintWriter(broadcastSocket.getOutputStream(),true);
                    System.out.println("Broadcast outputStream open!");
                    
                    System.out.println("LocalReadSocket waiting for input...");
                    Scanner scanner = new Scanner(localRecv.getInputStream());
                    String nextLine;
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine();
                        System.out.println("Next input to LocalReadSocket: " + nextLine);
                        String [] lineSplit = nextLine.split(" ");
                        String outputTextWithInfo = "UNI:ie2147" +
                            " Shape:" + lineSplit[0] + 
                            " X:"     + lineSplit[1] +
                            " Y:"     + lineSplit[2] +
                            " Color:"+ lineSplit[3]  +
                            " AdditionalInfo:" + System.currentTimeMillis();
                        broadcastOut.println(outputTextWithInfo);
                        System.out.println("Sent to broadcastServer, outputText: " + outputTextWithInfo);
                    }
                    System.out.println("Local InputStream closed!!!!!(GUI closed) Close program and restart");
                
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
                    Socket writeServerSock = new Socket(InetAddress.getLocalHost(),localWritePort);
                    PrintWriter localOut = new PrintWriter(writeServerSock.getOutputStream(),true);
                    System.out.println("WritingToLocalSocket connected!");

                    System.out.println("BroadcastRead waiting for input...");
                    Scanner scanner = new Scanner(broadcastSocket.getInputStream());
                    String nextLine;
                    while(scanner.hasNext()){
                        nextLine = scanner.nextLine(); 
                        System.out.println("Recieved from broadcast server: " + nextLine);
                        String [] split = nextLine.split("[ :]");
                        if( split.length < 10){
                            System.out.println("Received malformed message. Skipping!!!!!");
                            continue;
                        }
                        String textToGui = split[3] + " " +
                                split[5] + " " +
                                split[7] + " " + 
                                split[9];
                        localOut.println(textToGui);
                        System.out.println("Sent textToGui: " + textToGui);
                    }
                    
                    System.out.println("Broadcast InputStream Closed!!! Restart program");
                    
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }).start();
                  
        
    }
}
