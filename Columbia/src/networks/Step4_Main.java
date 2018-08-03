/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 *
 * @author Mavis Beacon
 */
public class Step4_Main {
    
    private static int localPort;
    private static int broadcastServerPort = 1452;
    private static String serverAddr = "128.59.9.102";
    
    private Socket broadcastServerSock = null;
    private static ServerSocket serverSock;
    private final Socket clientSock;
    
    private PrintWriter clientOut;
    private PrintWriter broadcastOut;
    
    private Thread t1;
    private Thread t2;
    
    static Set<String> blocked = new HashSet<String>();
    
    static final String handshake_request_add = "0:add";
    static final String handshake_request_remove = "0:remove";
    static final String handshake_response = "READY";
    static final String handshake_done = "FINISHED";
    static final String handshake_done_response = "END";
    static final String reset = "RESET";
    
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
                   
            Step4_Main m = null;
            while(true){
                    Socket clientSock = serverSock.accept();
                    if(m != null)
                        m.stopThreads();
                    m = new Step4_Main(clientSock);
                    System.out.println("Client connected!!");
                }

          
            
        } catch(Exception ex){
            System.out.println("FATAL EXCEPTION: " + ex.toString());
            return;
        }
    }
        
    private Step4_Main(Socket cs){
        
        this.clientSock = cs;
        
        try {
            
            System.out.println("BroadcastSock attempting to connect to "+ serverAddr +" on port: " + broadcastServerPort);
            try{
                this.broadcastServerSock = new Socket(InetAddress.getByName(serverAddr),broadcastServerPort);
            } catch(Exception ex){
                System.out.println("Broadcast server cant connect");
            }
            
            if(this.broadcastServerSock == null){
                System.out.println("\nBroadcast Server cannot be reached. Proxy is shutting down!!!\n");
                return;
            }
            
            clientOut = new PrintWriter(clientSock.getOutputStream(),true);
            System.out.println("ClientOutSocket connected!");
            
            broadcastOut = new PrintWriter(broadcastServerSock.getOutputStream(),true);
            System.out.println("BroadcastOutSocket connected!");
            
            /**Client->Broadcast Thread **/
            t1 = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {

                        System.out.println("ClientSock  waiting for input...");
                        Scanner scanner = new Scanner(clientSock.getInputStream());
                        String nextLine;
                        while(scanner.hasNext()){
                            nextLine = scanner.nextLine();
                            System.out.println("Next input to ClientSock : " + nextLine);
                            if( nextLine.equalsIgnoreCase(handshake_request_add) || nextLine.equalsIgnoreCase(handshake_request_remove) ){
                                checkForUpdateFromClient(nextLine);
                                continue;
                            }
                            if(nextLine.contains(handshake_done)){
                                System.out.println("Received "+ handshake_done +", sending response of "+handshake_done_response);
                                clientOut.println(handshake_done_response+":-1");
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

                        System.out.println("BroadcastSock waiting for input...");
                        Scanner scanner = new Scanner(broadcastServerSock.getInputStream());
                        String nextLine;
                        while(scanner.hasNext()){
                            nextLine = scanner.nextLine(); 
                            System.out.println("\nRecieved from BroadcastServer: " + nextLine);
                            
                            //Check for blocked UNI
                            String [] nextLineArray = nextLine.split("[: ]");
                            if(nextLineArray.length < 2){
                                System.out.println("No UNI, Skipping...");
                                continue;
                            }
                            String uni = nextLineArray[1];
                            System.out.println("Checking UNI: " + uni);
                            boolean isBlocked = blocked.contains(uni);
                            System.out.println("isBlocked: " + isBlocked);
                            if(isBlocked){
                                System.out.println("UNI: " + uni + " is blocked. Skipping...");
                                continue;
                            }
                            
                            clientOut.println(nextLine);
                            System.out.println("Broadcast->Client Thread: Sent nextLine: " + nextLine);
                        }
                        System.out.println("Broadcast->Client Thread Done");

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

    private void checkForUpdateFromClient(String messageWithOperation) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        
        System.out.println("\nIn check for update....");
        Scanner scanner = new Scanner(clientSock.getInputStream());
        
        String operation = messageWithOperation.substring(messageWithOperation.indexOf(":")+1);
        System.out.println("FOUND HANDSHAKE. Operation: "+operation+". Sending response...");
        clientOut.println(handshake_response);
        
        String nextLine;
        int messageNum;
        String content;
        int nextMessage = 1;
        while(scanner.hasNext()){
            nextLine = scanner.nextLine();
            
            if(nextLine.equalsIgnoreCase(handshake_request_add) || nextLine.equalsIgnoreCase(handshake_request_remove)){
                System.out.println("Recieved handshake AGAIN. Handshake response to CLIENT was not recieved..Resending...");
                clientOut.println(handshake_response);
                continue;
            }
            
            int index = nextLine.indexOf(":");
            if(index == -1){
                System.out.println("Malformed update. Message: " + nextLine);
                clientOut.println("End of update not found");
                continue;
            }
            messageNum = Integer.parseInt(nextLine.substring(0,index));
            content = nextLine.substring(index+1);
            
            if(content.equalsIgnoreCase(reset)){
                nextMessage = 1;
                continue;
            }
            
            //Out of order. Request Repeat
            if( messageNum != nextMessage ){
                System.out.println("Out of order(MessageNum="+messageNum+", Expecting="+nextMessage+"). Message=("+ nextLine +") "
                        + "\nRequesting messageNum: " + nextMessage);
                clientOut.println("REPEAT:"+nextMessage);
                continue;
            }
            
            //End of update message
            if(content.equalsIgnoreCase(handshake_done)){
                System.out.println("Found " + handshake_done);
                clientOut.println("END:"+messageNum);
                break;
            }
            //Regular add to list
            System.out.println("Adding to list : " + content);
            list.add(content);
            nextMessage++;
        }
        
        if(operation.equalsIgnoreCase("ADD")){
            blocked.addAll(list);
            System.out.println("Adding to blocked: " + list + ".\nBlocked is now: " + blocked);
        }
        else if(operation.equalsIgnoreCase("REMOVE")){
            blocked.removeAll(list);
            System.out.println("Removing from blocked: " + list + ".\nBlocked is now: " + blocked);
        }
        else{
            System.out.println("OPERATION NOT FOUND. Operation was: " + operation);
        }
                        
    }
}
