/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Random;
import java.util.Scanner;


/**
 *
 * @author Mavis Beacon
 */
public class RandomMessageDropper {
    
    private static int localPort;
    private static int proxyPort;
    private static String serverAddr = "localhost";
   
    private static ServerSocket serverSock;
    
    private final Socket broadcastServerSock;
    private final Socket clientSock;
    private Thread t1;
    private Thread t2;
    
    private static int RAND_VAL = 4; 

    
    public static void main(String args []){
           
        
        try{
            if(args.length != 2){
                System.out.println("Usage: Main <Client Port> <Proxt Port>");
                return;
            }
            localPort = Integer.parseInt(args[0]); 
            proxyPort = Integer.parseInt(args[1]);
            
            System.out.println("ServerSock attempting to connect on port: " + localPort);
            serverSock = new ServerSocket(localPort);
            System.out.println("ServerSock connected. Listening for connections");
                   
            RandomMessageDropper m = null;
            while(true){
                    Socket clientSock = serverSock.accept();
                    if(m != null){
                        m.stopThreads();
                    }
                    m = new RandomMessageDropper(clientSock);
                    System.out.println("Client connected!!");
                }

          
            
        } catch(Exception ex){
            System.out.println("FATAL EXCEPTION: " + ex.toString());
            return;
        }
    }
        
    private RandomMessageDropper(Socket cs) throws Exception{
        
        this.clientSock = cs;
        System.out.println("\nBroadcastSock attempting to connect on port: " + proxyPort);
        broadcastServerSock = new Socket(InetAddress.getByName(serverAddr),proxyPort);
                    System.out.println("BroadcastSock connected!");
        
        final Random rand = new Random();

        try {
            
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
                            
                            int randNum = rand.nextInt(10);
                            if(randNum > RAND_VAL){
                                System.out.println("Dropping message from CLIENT: " + nextLine);
                                continue;
                            }
                            
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
                            
                            int randNum = rand.nextInt(10);
                            if(randNum > RAND_VAL){
                                System.out.println("Dropping message from PROXY: " + nextLine);
                                continue;
                            }
                            
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
