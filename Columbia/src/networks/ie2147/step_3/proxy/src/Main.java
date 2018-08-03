/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks.ie2147.step_3.proxy.src;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;


/**
 *
 * @author Mavis Beacon
 */
public class Main {
    
    private static int localPort;
    private static int broadcastServerPort = 1452;
    private static String serverAddr = "128.59.9.102";
   
    private static ServerSocket serverSock;
    
    private final Socket broadcastServerSock;
    private final Socket clientSock;
    private Thread t1;
    private Thread t2;

    
    public static void main(String args []){
           
        
        try{
            if(args.length != 1){
                System.out.println("Usage: Main <Port>");
                return;
            }
            localPort = Integer.parseInt(args[0]); 
            
            System.out.println("ServerSock attempting to connect on port: " + localPort);
            serverSock = new ServerSocket(localPort);
            System.out.println("ServerSock connected. Listening for connections");
                   
            Main m = null;
            while(true){
                    Socket clientSock = serverSock.accept();
                    if(m != null){
                        m.stopThreads();
                    }
                    m = new Main(clientSock);
                    System.out.println("Client connected!!");
                }

          
            
        } catch(Exception ex){
            System.out.println("FATAL EXCEPTION: " + ex.toString());
            return;
        }
    }
        
    private Main(Socket cs) throws Exception{
        
        this.clientSock = cs;
        broadcastServerSock = new Socket(InetAddress.getByName(serverAddr),broadcastServerPort);

        try {
            System.out.println("\nBroadcastSock attempting to connect on port: " + broadcastServerPort);
            System.out.println("BroadcastSock connected!");
            
            /**Client->Broadcast Thread **/
            t1 = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {

                        PrintWriter broadcastOut = new PrintWriter(broadcastServerSock.getOutputStream(),true);
                        System.out.println("BroadcastOutSocket connected!");

                        System.out.println("ClientSock  waiting for input...");
                        Scanner scanner = new Scanner(clientSock.getInputStream());
                        String nextLine;
                        while(scanner.hasNext()){
                            nextLine = scanner.nextLine();
                            System.out.println("Next input to ClientSock : " + nextLine);
                            broadcastOut.println(nextLine);
                            System.out.println("\nClient->Broadcast Thread: outputText: " + nextLine);
                        }
                        
                        System.out.println("Client->BroadcastThread Done!");

                    } catch (Exception ex) {
                        System.out.println("Thread1: "+ex.toString());
                    }
                }
            });
            t1.start();

            /** Broadcast->Client Thread **/
            t2 = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {

                        PrintWriter clientOut = new PrintWriter(clientSock.getOutputStream(),true);
                        System.out.println("ClientOutSocket connected!");

                        System.out.println("BroadcastSock waiting for input...");
                        Scanner scanner = new Scanner(broadcastServerSock.getInputStream());
                        String nextLine;
                        while(scanner.hasNext()){
                            nextLine = scanner.nextLine(); 
                            System.out.println("Recieved from BroadcastServer: " + nextLine);
                            clientOut.println(nextLine);
                            System.out.println("\nBroadcast->Client Thread: Sent nextLine: " + nextLine);
                        }
                        System.out.println("Broadcast->Client Thread Done!\n");

                    } catch (Exception ex) {
                        System.out.println("Thread2:" +ex.toString());
                    }
                }
            });
            t2.start();
        } catch (Exception ex) {
           System.out.println("In constructor: " + ex.toString());
        }
    } 
    
    private void stopThreads() throws IOException{
        broadcastServerSock.close();
        clientSock.close();
        if(t1 != null && t1.isAlive()){
            t1.interrupt();
        }
        if(t2 != null && t2.isAlive()){
            t2.interrupt();
        }
    }
}
